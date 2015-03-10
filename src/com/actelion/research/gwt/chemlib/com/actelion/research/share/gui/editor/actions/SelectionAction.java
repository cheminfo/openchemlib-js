/*
* Copyright (c) 1997 - 2015
* Actelion Pharmaceuticals Ltd.
* Gewerbestrasse 16
* CH-4123 Allschwil, Switzerland
*
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice, this
*    list of conditions and the following disclaimer.
* 2. Redistributions in binary form must reproduce the above copyright notice,
*    this list of conditions and the following disclaimer in the documentation
*    and/or other materials provided with the distribution.
* 3. Neither the name of the the copyright holder nor the
*    names of its contributors may be used to endorse or promote products
*    derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*/




package com.actelion.research.share.gui.editor.actions;

import com.actelion.research.chem.Molecule;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.share.gui.DialogResult;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.chem.IDrawingObject;
import com.actelion.research.share.gui.editor.dialogs.IAtomQueryFeaturesDialog;
import com.actelion.research.share.gui.editor.dialogs.IBondQueryFeaturesDialog;
import com.actelion.research.share.gui.editor.geom.ICursor;
import com.actelion.research.share.gui.editor.geom.IDrawContext;
import com.actelion.research.share.gui.editor.geom.IPolygon;
import com.actelion.research.share.gui.editor.io.IMouseEvent;

import java.awt.geom.Point2D;


/**
 * Project:
 * User: rufenec
 * Date: 1/24/13
 * Time: 5:57 PM
 */
public class SelectionAction extends BondHighlightAction//DrawAction
{
    private volatile IPolygon polygon = builder.createPolygon();
    boolean rectangular = false;
    int atom = -1;
    int bond = -1;
//    private float[] mX, mY;
    IDrawingObject selectedOne;
    boolean shift = false,duplicate = false;
    public SelectionAction(Model model)
    {
        super(model);
    }

    @Override
    public boolean onMouseDown(IMouseEvent evt)
    {
        java.awt.geom.Point2D pt = new Point2D.Double(evt.getX(), evt.getY());
        StereoMolecule mol = model.getMoleculeAt(pt, true);

        shift = evt.isShiftDown();
        rectangular = evt.isControlDown();
        polygon = builder.createPolygon();
        polygon.add(pt);

        duplicate = false;
        selectedOne = null;

        last = origin = new Point2D.Double(pt.getX(), pt.getY());
        atom = getAtomAt(mol, origin);
        bond = getBondAt(mol, origin);
        if (atom != -1) {
            if (!mol.isSelectedAtom(atom)) {
                if (!shift)
                    deselectAllAtoms();
                mol.setAtomSelection(atom, true);
            }
        }
        return false;
    }

    @Override
    public boolean onMouseUp(IMouseEvent ev)
    {
        polygon = builder.createPolygon();
        atom = bond = -1;
        origin = last = null;
        duplicate = false;
        return true;
    }

    @Override
    public boolean onMouseMove(IMouseEvent evt, boolean drag)
    {
//        System.out.println("Atoms and Bonds moving " + atom + " " + bond);
        boolean ok = false;
        java.awt.geom.Point2D pt = new Point2D.Double(evt.getX(), evt.getY());
        if (drag) {
            double dx = last.getX() - pt.getX();
            double dy = last.getY() - pt.getY();
            if (!shift || duplicate) {
                ok = moveAtomsAndBonds(dx, dy);
                if (!ok) {
                    ok = moveSelected(dx, dy);
                }
                if (!ok) {
                    ok = selectItems(pt);
                }
            } else {
                duplicate = true;
                duplicateSelected();
            }
            model.analyzeReaction();
        } else {
            ok = trackHighLight(pt);
        }
        last = pt;
        return ok;
    }

    private boolean moveAtomsAndBonds(double dx, double dy)
    {
        boolean ok = false;
//        for (StereoMolecule mol : model.getMols()) {
        StereoMolecule mol = model.getMolecule();
      {
            if (mol != null) {
                if (mol != null && atom != -1) {
                    translateAtoms(mol, dx, dy, true);
                    ok = true;
                } else if (mol != null && bond != -1) {
                    translateBond(mol, dx, dy, true);
                    ok = true;
                }
            }
        }
        return ok;
    }

    private boolean selectItems(java.awt.geom.Point2D pt)
    {
        boolean ok = false;
        if (rectangular) {
            selectRectanglarRegion(null);
            ok = true;
        } else {
            if (selectPolygonRegion(null, pt)) {
                ok = true;
            }
        }
        return ok;
    }

    private boolean moveSelected(double dx, double dy)
    {
        boolean ok = false;
        if (selectedOne != null) {
            selectedOne.move((float) -dx, (float) -dy);
            ok = true;
        }
        return ok;
    }

    private void duplicateSelected()
    {
        StereoMolecule mol = model.getSelectedMolecule();

        int originalAtoms = mol.getAllAtoms();
        int originalBonds = mol.getAllBonds();
        int[] atomMap = new int[mol.getAllAtoms()];
        int esrGroupCountAND = mol.renumberESRGroups(Molecule.cESRTypeAnd);
        int esrGroupCountOR = mol.renumberESRGroups(Molecule.cESRTypeOr);
        for (int atom = 0; atom < originalAtoms; atom++) {
            if (mol.isSelectedAtom(atom)) {
                int newAtom = mol.getAllAtoms();
                atomMap[atom] = newAtom;
                mol.copyAtom(mol, atom, esrGroupCountAND, esrGroupCountOR);
            }
        }
        for (int bond = 0; bond < originalBonds; bond++) {
            if (mol.isSelectedBond(bond)) {
                mol.copyBond(mol, bond, esrGroupCountAND, esrGroupCountOR, atomMap, false);
            }
        }
        for (int atom = 0; atom < originalAtoms; atom++) {
            mol.setAtomSelection(atom, false);
        }
        for (int atom = originalAtoms; atom < mol.getAllAtoms(); atom++) {
            mol.setAtomMapNo(atom, 0, false);
        }
    }


    private boolean selectPolygonRegion(StereoMolecule m, java.awt.geom.Point2D pt)
    {
        if (polygon.size() > 1 && Math.abs(pt.getX() - polygon.get(polygon.size() - 1).getX()) < 10
            && Math.abs(pt.getY() - polygon.get(polygon.size() - 1).getY()) < 10) {
            return false;
        }
        if (origin == null) {
            throw new RuntimeException("NUll DOWN Point!");
        }
        polygon.remove(origin);
        polygon.add(pt);
        polygon.add(origin);

        deselectAllAtoms();
        selectFromPolygonRegion();
        return true;
    }

    private void selectFromPolygonRegion()
    {
//        StereoMolecule[] mols = model.getMols();
//        for (StereoMolecule mol : mols) {
          StereoMolecule mol = model.getMolecule();
        {
            for (int i = 0; i < mol.getAllAtoms(); i++) {
                boolean isSelected = polygon.contains(mol.getAtomX(i), mol.getAtomY(i));
                mol.setAtomSelection(i, isSelected);
                model.setSelectedMolecule(mol);
            }
        }
    }

    private void deselectAllAtoms()
    {
//        StereoMolecule[] mols = model.getMols();
//        for (StereoMolecule mol : mols) {
        StereoMolecule mol = model.getMolecule();
      {

            deselectAtoms(mol);
        }
    }

    private void selectRectanglarRegion(StereoMolecule mol)
    {
        java.awt.geom.Rectangle2D rc = makeRect(origin, last);
        boolean selected = false;
        if (mol != null) {
//            deselectAllAtoms();
            selectAtomsInRectangle(mol, rc);
            selected = true;
        } else {
//            StereoMolecule[] mols = model.getMols();
//            for (StereoMolecule m : mols) {
            StereoMolecule m = model.getMolecule();
          {
                deselectAtoms(m);
                java.awt.geom.Rectangle2D bounds = builder.getBoundingRect(m);
                if (bounds != null && bounds.intersects(rc.getX(),rc.getY(),rc.getWidth(),rc.getHeight())) {
                    selectRectanglarRegion(m);
                    //break;
                }
            }
        }
        if (!selected) {
            selectDrawingObjectsInRectangle(rc);
        }
    }

    private void deselectAtoms(StereoMolecule mol)
    {
        for (int i = 0; i < mol.getAllAtoms(); i++) {
            mol.setAtomSelection(i, false);
        }
    }

    private void selectDrawingObjectsInRectangle(java.awt.geom.Rectangle2D rc)
    {
//        for (IDrawingObject dw : model.getDrawingObjectList()) {
//            dw.setSelected(false);
//            java.awt.geom.Rectangle2D r = dw.getBoundingRect();
//            java.awt.geom.Rectangle2D bounds = new java.awt.geom.Rectangle2D.Double(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
////            IRectangle2D bounds = builder.createRectangle(r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
//            if (bounds.intersects(rc)) {
//                dw.setSelected(true);
//            }
//        }
    }

    private void selectAtomsInRectangle(StereoMolecule mol, java.awt.geom.Rectangle2D rc)
    {
        for (int i = 0; i < mol.getAllAtoms(); i++) {
            boolean isSelected = rc.contains(mol.getAtomX(i), mol.getAtomY(i));
            mol.setAtomSelection(i, isSelected);
        }
        model.setSelectedMolecule(mol);
    }

    private void translateBond(StereoMolecule mol, double dx, double dy, boolean selected)
    {
        int a1 = mol.getBondAtom(0, bond);
        int a2 = mol.getBondAtom(1, bond);
        if (selected) {
            translateAtoms(mol, dx, dy, true);
        } else {
            translateAtom(mol, a1, dx, dy);
            translateAtom(mol, a2, dx, dy);
        }
    }

    private void translateAtom(StereoMolecule mol, int a, double dx, double dy)
    {
        mol.setAtomX(a, mol.getAtomX(a) - dx);
        mol.setAtomY(a, mol.getAtomY(a) - dy);
    }

    private void translateAtoms(StereoMolecule mol, double dx, double dy, boolean allSelected)
    {
        if (allSelected) {
            for (int i = 0; i < mol.getAllAtoms(); i++) {
                if (mol.isSelectedAtom(i)) {
                    translateAtom(mol, i, dx, dy);
                }
            }
        } else {
            translateAtom(mol, atom, dx, dy);
        }
    }

    @Override
    public boolean onDoubleClick(IMouseEvent evt)
    {
//        StereoMolecule mol = model.getSelectedMolecule();
        java.awt.geom.Point2D pt = new Point2D.Double(evt.getX(), evt.getY());
        StereoMolecule mol = model.getMoleculeAt(pt, true);
        if (mol != null) {
            int atom = mol.findAtom((float) pt.getX(), (float) pt.getY());
            int bond = mol.findBond((float) pt.getX(), (float) pt.getY());
            boolean mShiftIsDown = evt.isShiftDown();
            int mMode = model.getMode();

            if (mol.isFragment()) {
                if (atom != -1) {
                    return showAtomQFDialog(atom);
                } else if (bond != -1) {
                    return showBondQFDialog(bond);
                }
//             else if (mCurrentHiliteObject != null) {
//                if (!mShiftIsDown) {
//                    for (int i = 0; i < mol.getAllAtoms(); i++)
//                        mol.setAtomSelection(i, false);
//                    for (int i = 0; i < mDrawingObjectList.size(); i++)
//                        ((AbstractDrawingObject) mDrawingObjectList.get(i)).setSelected(false);
//                }
//
//                mCurrentHiliteObject.setSelected(true);
//                update(UPDATE_REDRAW);
//            }
            } else {
                int rootAtom = -1;
                if (atom != -1) {
                    rootAtom = atom;
                } else if (bond != -1) {
                    rootAtom = mol.getBondAtom(0, bond);
                }

                if (rootAtom != -1 /*|| mCurrentHiliteObject != null*/) {
                    if (!mShiftIsDown) {
                        deselectAllAtoms();
                        for (int i = 0; i < mol.getAllAtoms(); i++) {
                            mol.setAtomSelection(i, true);
                        }
//                    if (mDrawingObjectList != null)
//                        for (AbstractDrawingObject drawingObject : mDrawingObjectList)
//                            drawingObject.setSelected(false);
                    }

//                if (rootAtom != -1) {
//                    model.setSelectedFragment(rootAtom);
//                } else {
////                    mCurrentHiliteObject.setSelected(true);
//                }
                    return true;
                }
            }
        }
        return false;
    }


    private boolean showAtomQFDialog(int atom)
    {
        StereoMolecule mol = model.getSelectedMolecule();
        if (mol != null) {
            IAtomQueryFeaturesDialog dlg = builder.createAtomQueryFeatureDialog(/* new AtomQueryFeaturesDialog*/mol, atom);
            return dlg.doModal() == DialogResult.IDOK;
        }
        return false;
    }

    private boolean showBondQFDialog(int bond)
    {
        StereoMolecule mol = model.getSelectedMolecule();
        if (mol != null) {
            IBondQueryFeaturesDialog dlg = builder.createBondFeaturesDialog( /*new BondQueryFeaturesDialog(*/mol, bond);
            return dlg.doModal() == DialogResult.IDOK;
        }
        return false;
    }


    java.awt.geom.Rectangle2D makeRect(java.awt.geom.Point2D origin, java.awt.geom.Point2D pt)
    {
//        return Geom.makeRect(origin, pt);
        double x = Math.min(origin.getX(), pt.getX());
        double y = Math.min(origin.getY(), pt.getY());
        double w = Math.abs(origin.getX() - pt.getX());
        double h = Math.abs(origin.getY() - pt.getY());
        return new java.awt.geom.Rectangle2D.Double(x,y,w,h);
//        return builder.createRectangle(x, y, w, h);
//        return new Geom.Rectangle2D(x, y, w, h);
    }

    @Override
    public boolean paint(IDrawContext ctx)
    {
        super.paint(ctx);
        if (rectangular && origin != null && last != null) {
            drawDashedRect(ctx);
            return true;
        } else if (polygon != null && polygon.size() > 1) {
            drawPolygon(ctx);
            return true;
        }
        return false;
    }

    private void drawDashedRect(IDrawContext ctx)
    {
        java.awt.geom.Rectangle2D rc = makeRect(origin, last);
        if (rc.getWidth() > 5 && rc.getHeight() > 5) {
            drawDashedRect(ctx, rc.getMinX(), rc.getMinY(), rc.getWidth(), rc.getHeight(), new int[]{
                    5,
                    2
            });
        }
    }

    private void drawPolygon(IDrawContext ctx)
    {
        ctx.drawPolygon(polygon);
    }

    @Override
    public int getCursor()
    {
        if (rectangular) {
            return ICursor.SE_RESIZE;
        }
        return ICursor.CROSSHAIR;
    }

    private void drawDashedLine(IDrawContext context, double srcx, double srcy, double targetx, double targety, int[] dashPattern)
      {
          context.drawDashedLine(srcx,srcy,targetx,targety,dashPattern);
      }

      private void drawDashedRect(IDrawContext ctx, double x, double y, double w, double h,int[] pattern)
      {
          drawDashedLine(ctx,x,y,x+w,y,pattern);
          drawDashedLine(ctx,x,y,x,y+h,pattern);
          drawDashedLine(ctx,x,y+h,x+w,y+h,pattern);
          drawDashedLine(ctx,x+w,y+h,x+w,y,pattern);
      }


}


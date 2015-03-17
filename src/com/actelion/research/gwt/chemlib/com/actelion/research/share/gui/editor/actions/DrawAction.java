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
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.geom.GeomFactory;
import com.actelion.research.share.gui.editor.geom.IColor;
import com.actelion.research.share.gui.editor.geom.ICursor;
import com.actelion.research.share.gui.editor.geom.IDrawContext;
import com.actelion.research.share.gui.editor.io.IKeyEvent;
import com.actelion.research.share.gui.editor.io.IMouseEvent;

import java.awt.geom.Point2D;

/**
 * Basic class which handles all actions which interact with the drawing surface
 */
public abstract class DrawAction implements Action
{
    protected static GeomFactory builder = GeomFactory.getGeomFactory() ;

    public static final double HIGHLIGHT_ATOM_DIAMETER = 20;
    public static final long HIGHLIGHT_STYLE = builder.createColor(.7, .8, 1, 0.8);// new Color(.7, .8, 1, 0.8);
    public static final int MAX_CONNATOMS = 8;

    protected Model model;

    public DrawAction(Model m)
    {
        model = m;
    }



    @Override
    public void onCommand()
    {
        // NOOP
    }

    @Override
    public final boolean isCommand()
    {
        return false;
    }

    @Override
    public boolean onKeyPressed(IKeyEvent evt)
    {
        return false;
    }

    @Override
    public int getCursor()
    {
        return ICursor.DEFAULT;
    }

    @Override
    public boolean onDoubleClick(IMouseEvent ev)
    {
        return false;
    }

    public void onActionLeave()
    {

    }

    public void onActionEnter()
    {

    }

    /**
     * returns the atom at the current point
     * @param pt
     * @return atom no or -1 if no atom there
     */
    int getAtomAt(java.awt.geom.Point2D pt)
    {
        StereoMolecule mol = model.getMoleculeAt(pt, false);
        return getAtomAt(mol, pt);
    }

    int getBondAt(java.awt.geom.Point2D pt)
    {
        StereoMolecule mol = model.getMoleculeAt(pt, true);
        return getBondAt(mol, pt);
    }

    int getBondAt(StereoMolecule mol, java.awt.geom.Point2D pt)
    {
        if (mol != null) {
            return mol.findBond((float) pt.getX(), (float) pt.getY());
        }
        return -1;
    }

    int getAtomAt(StereoMolecule mol, java.awt.geom.Point2D pt)
    {
        if (mol != null) {
            return mol.findAtom((float) pt.getX(), (float) pt.getY());
        }
        return -1;
    }


//    int getBondAt(Point2D pt)
//    {
//        System.out.println(">>.getBondAt: " + pt);
//        StereoMolecule mol = model.getMol(pt);
//        if (mol != null) {
////            for (int i = 0; i < mol.getAllBonds(); i++) {
////                int source = mol.getBondAtom(0,i);
////                int target = mol.getBondAtom(1,i);
////                java.awt.geom.Line2D line =
////                    new java.awt.geom.Line2D.Float (mol.getAtomX(source),mol.getAtomY(source),mol.getAtomX(target),mol.getAtomY(target));
////                double dist = line.ptLineDist(pt.getX(),pt.getY());
////                if (dist < )
////                System.out.printf("Atom %d x=%f y=%f\n",System.identityHashCode(mol), mol.getAtomX(i),mol.getAtomY(i));
////            }
//////            System.out.println("<<<GetAtomAtomAt:");
//////
//            return mol.findBond((float) pt.getX(), (float) pt.getY());
//        }
//        return -1;
//    }

    protected void drawBondHighlight(IDrawContext ctx, StereoMolecule mol, int theBond)
    {
        //        int theBond = model.getSelectedBond();
        double x1 = mol.getAtomX(mol.getBondAtom(0, theBond));
        double y1 = mol.getAtomY(mol.getBondAtom(0, theBond));
        double x2 = mol.getAtomX(mol.getBondAtom(1, theBond));
        double y2 = mol.getAtomY(mol.getBondAtom(1, theBond));


        ctx.save();
        ctx.setLineWidth(8);
        ctx.setStroke(HIGHLIGHT_STYLE);
//        ctx.setStroke(IColor.BLUE);
        ctx.drawLine(x1,y1,x2,y2);
//        ctx.setLineWidth(8);
//        ctx.beginPath();
//        ctx.moveTo(x1, y1);
//        ctx.lineTo(x2, y2);
//        ctx.stroke();
//        ctx.closePath();
        ctx.restore();
    }

//    protected void highlightBond(StereoMolecule mol, int bond)
//    {
////        System.out.println("Hightight bond " + bond);
//        model.setSelectedBond(bond);
//        model.setSelectedMolecule(mol);
//    }

    protected void highlightAtom(StereoMolecule mol, int atom)
    {
//        System.out.println("Hightight atom " + atom);
        model.setSelectedMolecule(mol);
        model.setSelectedAtom(atom);
    }


    protected void drawAtomHighlight(IDrawContext _ctx, StereoMolecule mol, int theAtom)
    {
        drawAtomHighlightElement(_ctx, mol, theAtom);
        if (model.getKeyStrokeBuffer().length()>0) {
            drawAtomKeyStrokes(_ctx,mol,theAtom);
        }
    }

    private void drawAtomHighlightElement(IDrawContext _ctx, StereoMolecule mol, int theAtom)
    {
        java.awt.geom.Point2D highlightPoint = new Point2D.Double(mol.getAtomX(theAtom), mol.getAtomY(theAtom));

//        _ctx.drawAtomHighlight(highlightPoint.getX() - HIGHLIGHT_ATOM_DIAMETER / 2, highlightPoint.getY() - HIGHLIGHT_ATOM_DIAMETER / 2);

        _ctx.save();
        _ctx.setFill(HIGHLIGHT_STYLE);
        _ctx.fillElipse(
            highlightPoint.getX() - HIGHLIGHT_ATOM_DIAMETER / 2, highlightPoint.getY() - HIGHLIGHT_ATOM_DIAMETER / 2,
            HIGHLIGHT_ATOM_DIAMETER, HIGHLIGHT_ATOM_DIAMETER
);
        _ctx.restore();
    }


    protected void drawAtomKeyStrokes(IDrawContext _ctx, StereoMolecule mol, int theAtom)
    {

        String s = model.getKeyStrokeBuffer().toString();
        int validity = model.getAtomKeyStrokeValidity(s);
        java.awt.geom.Point2D highlightPoint = new Point2D.Double(mol.getAtomX(theAtom), mol.getAtomY(theAtom));

//        _ctx.drawAtomKeyStrokes(highlightPoint.getX(),highlightPoint.getY(),validity);

        _ctx.save();
        _ctx.setFill((
            validity == Model.KEY_IS_ATOM_LABEL) ? IColor.BLACK
            : (validity == Model.KEY_IS_SUBSTITUENT) ? IColor.BLUE
            : (validity == Model.KEY_IS_VALID_START) ? IColor.GRAY : IColor.RED);

        if (validity == Model.KEY_IS_INVALID) {
            s = s + "<unknown>";
        }
//        _ctx.setTextSize(24);
        _ctx.setFont("Helvetica", 24);
        _ctx.fillText(s, highlightPoint.getX(), highlightPoint.getY());
        _ctx.restore();
    }

    protected java.awt.geom.Point2D suggestNewX2AndY2(int atom)
    {
        StereoMolecule mol = model.getSelectedMolecule();
        mol.ensureHelperArrays(Molecule.cHelperNeighbours);

        double newAngle = Math.PI * 2 / 3;
        if (atom != -1) {
            double[] angle = new double[DrawAction.MAX_CONNATOMS + 1];
            for (int i = 0; i < mol.getAllConnAtoms(atom); i++) {
                angle[i] = mol.getBondAngle(atom, mol.getConnAtom(atom, i));
            }

            if (mol.getAllConnAtoms(atom) == 1) {
                if (angle[0] < -Math.PI * 5 / 6) {
                    newAngle = Math.PI / 3;
                } else if (angle[0] < -Math.PI / 2) {
                    newAngle = Math.PI * 2 / 3;
                } else if (angle[0] < -Math.PI / 6) {
                    newAngle = Math.PI / 3;
                } else if (angle[0] < 0.0) {
                    newAngle = Math.PI * 2 / 3;
                } else if (angle[0] < Math.PI / 6) {
                    newAngle = -Math.PI * 2 / 3;
                } else if (angle[0] < Math.PI / 2) {
                    newAngle = -Math.PI / 3;
                } else if (angle[0] < Math.PI * 5 / 6) {
                    newAngle = -Math.PI * 2 / 3;
                } else {
                    newAngle = -Math.PI / 3;
                }
            } else {
                for (int i = mol.getAllConnAtoms(atom) - 1; i > 0; i--) {  // bubble sort
                    for (int j = 0; j < i; j++) {
                        if (angle[j] > angle[j + 1]) {
                            double temp = angle[j];
                            angle[j] = angle[j + 1];
                            angle[j + 1] = temp;
                        }
                    }
                }
                angle[mol.getAllConnAtoms(atom)] = angle[0] + Math.PI * 2;

                int largestNo = 0;
                double largestDiff = 0.0;
                for (int i = 0; i < mol.getAllConnAtoms(atom); i++) {
                    double angleDiff = angle[i + 1] - angle[i];
                    if (largestDiff < angleDiff) {
                        largestDiff = angleDiff;
                        largestNo = i;
                    }
                }
                newAngle = (angle[largestNo] + angle[largestNo + 1]) / 2;
            }
        }
        double avbl = mol.getAverageBondLength();
        return new Point2D.Double(
            mol.getAtomX(atom) + avbl * Math.sin(newAngle),
            mol.getAtomY(atom) + avbl * Math.cos(newAngle)
        );
    }

}

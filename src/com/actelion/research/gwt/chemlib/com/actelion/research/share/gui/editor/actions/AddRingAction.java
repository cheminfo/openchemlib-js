/*
 * Project: DD_jfx
 * @(#)AddRingAction.java
 *
 * Copyright (c) 1997- 2015
 * Actelion Pharmaceuticals Ltd.
 * Gewerbestrasse 16
 * CH-4123 Allschwil, Switzerland
 *
 * All Rights Reserved.
 *
 * This software is the proprietary information of Actelion Pharmaceuticals, Ltd.
 * Use is subject to license terms.
 *
 * Author: Christian Rufener
 */

package com.actelion.research.share.gui.editor.actions;

import com.actelion.research.chem.Molecule;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.io.IMouseEvent;

import java.awt.geom.Point2D;

/**
 * Project:
 * User: rufenec
 * Date: 2/1/13
 * Time: 4:03 PM
 */
public class AddRingAction extends BondHighlightAction
        //DrawAction
{
    Model model;
    int ringSize = 0;
    boolean aromatic = false;
//    int theBond = -1;
//    int theAtom = -1;

    public AddRingAction(Model m, int ringSize, boolean aromatic)
    {
        super(m);
        this.model = m;
        this.ringSize = ringSize;
        this.aromatic = aromatic;
    }

    public boolean onMouseDown(IMouseEvent evt)
    {
        return false;
    }

    public boolean onMouseUp(IMouseEvent evt)
    {
        model.pushUndo();
        StereoMolecule mol = model.getMolecule();//.getSelectedMolecule();
        java.awt.geom.Point2D pt = new Point2D.Double(evt.getX(), evt.getY());
        boolean ok = false;
        if (mol != null)
        {
            int atom = getAtomAt(mol,pt);
            int bond = getBondAt(mol,pt);
//            model.setSelectedBond(-1);
            if (atom != -1) {
                //theBond = -1;
                ok = mol.addRing((float) pt.getX(), (float) pt.getY(), ringSize, aromatic);
                model.setSelectedBond(-1);
            } else if (bond != -1) {
                ok = mol.addRing((float) pt.getX(), (float) pt.getY(), ringSize, aromatic);
                model.setSelectedAtom(-1);
            } else {
//                model.setSelectedMolecule(mol);
                ok = mol.addRing((float) pt.getX(), (float) pt.getY(), ringSize, aromatic);
                model.needsLayout(true);
            }
        } else {
            mol = new StereoMolecule();
            ok= mol.addRing((float) pt.getX(), (float) pt.getY(), ringSize, aromatic);
            model.setValue(mol, true);
//            model.setSelectedMolecule(mol);
        }
        if (ok)
            mol.ensureHelperArrays(Molecule.cHelperNeighbours);
        return ok;
    }

    @Override
    protected boolean onDrag(java.awt.geom.Point2D pt)
    {
        return true;
    }


//    public boolean onMouseMove(ACTMouseEvent evt, boolean drag)
//    {
//        if (!drag) {
//            Point2D pt = new Point2D(evt.getX(), evt.getY());
//            return trackHighLight(pt, drag);
//        }
//        return false;
//
//    }

//    @Override
//    public boolean draw(GraphicsContext _ctx)
//    {
//        StereoMolecule mol = model.getSelectedMolecule();
//        if (mol != null) {
//            int theBond = model.getSelectedBond();
//            int theAtom = model.getSelectedAtom();
//            if (theBond != -1) {
////      print("AtomAction draw");
//                double x1 = mol.getAtomX(mol.getBondAtom(0, theBond));
//                double y1 = mol.getAtomY(mol.getBondAtom(0, theBond));
//                double x2 = mol.getAtomX(mol.getBondAtom(1, theBond));
//                double y2 = mol.getAtomY(mol.getBondAtom(1, theBond));
//
//                _ctx.save();
//                _ctx.setStroke(HIGHLIGHT_STYLE);
//                _ctx.setLineWidth(10);
//                _ctx.setLineCap(StrokeLineCap.ROUND);
//                _ctx.setLineJoin(StrokeLineJoin.ROUND);
//                _ctx.beginPath();
//                _ctx.moveTo(x1, y1);
//                _ctx.lineTo(x2, y2);
//                _ctx.stroke();
//                _ctx.closePath();
//                _ctx.restore();
//                return true;
//            } else if (theAtom != -1) {
//                super.draw(_ctx);
////                drawAtomHighlight(_ctx,mol,theAtom);
////                Point2D highlightPoint = new Point2D(mol.getAtomX(theAtom), mol.getAtomY(theAtom));
////                _ctx.save();
////                _ctx.setFill(HIGHLIGHT_STYLE);
////                _ctx.fillArc(highlightPoint.getX() - 5, highlightPoint.getY() - 5, 10, 10, 0, 360, ArcType.ROUND);
////                _ctx.fill();
////                _ctx.restore();
//                return true;
//            }
//        }
//        return false;
//    }

//    boolean trackHighLight(Point2D pt, boolean drag)
//    {
//        StereoMolecule mol = model.getMol(pt,true);
//        int theBond = model.getSelectedBond();
//        int theAtom = model.getSelectedAtom();
//        int atom = getAtomAt(mol, pt);
//        if (atom != -1) {
//            highlightAtom(mol,atom);
//            highlightBond(mol,-1);
//            return true;
//        } else {
//            int bond = getBondAt(mol, pt);
//            if (bond >= 0) {
//                highlightBond(mol,bond);
//                highlightAtom(mol,-1);
//                return true;
//            }
//        }
//        boolean update = theBond != -1 || theAtom != -1;
//        model.setSelectedAtom(-1);
//        model.setSelectedBond(-1);
////        theBond = -1;
////        theAtom = -1;
//        return update;
//    }

//    @Override
//    public boolean onKeyPressed(ACTKeyEvent evt)
//    {
//        int theBond = model.getSelectedBond();
//        int theAtom = model.getSelectedAtom();
//        StereoMolecule mol = model.getSelectedMolecule();
//        if (mol != null) {
//            if (evt.getCode().equals(KeyCode.DELETE)) {
//                if (theBond != -1) {
//                    mol.deleteBondAndSurrounding(theBond);
//                    return true;
//                } else if (theAtom != -1) {
//                    mol.deleteAtom(theAtom);
//                    return true;
//                }
//            } else {
//
//            }
//        }
//        return super.onKeyPressed(evt);
//    }
}

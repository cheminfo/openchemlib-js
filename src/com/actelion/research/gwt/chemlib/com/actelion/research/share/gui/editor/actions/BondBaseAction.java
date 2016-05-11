/*
 * Project: DD_jfx
 * @(#)BondBaseAction.java
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
import com.actelion.research.share.gui.editor.geom.IColor;
import com.actelion.research.share.gui.editor.geom.IDrawContext;
import com.actelion.research.share.gui.editor.io.IMouseEvent;

import java.awt.geom.Point2D;

/**
 * Project:
 * User: rufenec
 * Date: 1/28/13
 * Time: 1:07 PM
 */
public abstract class BondBaseAction extends BondHighlightAction
    //DrawAction
{
//    int theBond = -1;
//    Point2D origin = null;
//    Point2D last = null;
//    boolean dragging = false;
//    private static final int MIN_BOND_LENGTH_SQUARE = 100;

    protected BondBaseAction(Model model)
    {
        super(model);
    }

    public abstract int getBondType();

    //    public abstract void onAddBond(int src, int target);
//    public abstract void onChangeBond(int bnd);
//
    public void onAddBond(int srcAtom, int targetAtom)
    {
        StereoMolecule mol = model.getMolecule();//.getSelectedMolecule();
        if (mol != null) {
            mol.addBond(srcAtom, targetAtom, getBondType());
            mol.ensureHelperArrays(Molecule.cHelperNeighbours);
        } else {
//            mol = new StereoMolecule();
//            model.addMolecule(mol, null);
//            model.setSelectedMolecule(mol);
        }
    }

    public void onChangeBond(int bond)
    {
        StereoMolecule mol = model.getMolecule();//.getSelectedMolecule();
        if (mol != null) {
            mol.changeBond(bond, Molecule.cBondTypeIncreaseOrder);
            mol.ensureHelperArrays(Molecule.cHelperNeighbours);
        }
    }
//

    public boolean onMouseUp(IMouseEvent evt)
    {

        boolean ok = true;
        java.awt.geom.Point2D pt = new Point2D.Double(evt.getX(), evt.getY());
        model.pushUndo();
        int sourceAtom = getAtomAt(origin);
        int selectedAtom = model.getSelectedAtom();
        model.setSelectedBond(-1);
        StereoMolecule mol = model.getMoleculeAt(origin, true);
        model.setSelectedAtom(sourceAtom);
//        model.setSelectedMolecule(mol);
//        theBond = -1;
        if (!dragging) {
            if (mol != null && sourceAtom != -1) {
                if (mol.getAllConnAtoms(sourceAtom) != Model.MAX_CONNATOMS) {
//                System.out.printf("Molecule has %d atoms before adding bond\n",mol.getAllAtoms());
                    java.awt.geom.Point2D p = suggestNewX2AndY2(sourceAtom);
                    int stopAtom = mol.findAtom((float) p.getX(), (float) p.getY());
                    if (stopAtom != -1) {
                        mol.addOrChangeBond(sourceAtom, stopAtom, getBondType());
                    } else {
                        int t = mol.addAtom((float) p.getX(), (float) p.getY(), 0.0f);
                        onAddBond(sourceAtom, t);
                    }
                    ok = true;
                }
//                System.out.printf("Molecule has %d atoms after adding bond\n",mol.getAllAtoms());
            } else if (mol != null) {
                int bond = getBondAt(mol, pt);
                if (bond != -1) {
                    onChangeBond(bond);
                } else {
//                    StereoMolecule mol = model.getSelectedMolecule();
                    sourceAtom = mol.addAtom((float) pt.getX(), (float) pt.getY());
                    java.awt.geom.Point2D p = suggestNewX2AndY2(sourceAtom);
                    int a2 = mol.addAtom((float) p.getX(), (float) p.getY(), 0.0f);
                    onAddBond(sourceAtom, a2);
                }
                ok = true;
            } else if (mol == null) {
                mol = model.getMolecule();
//                model.setSelectedMolecule(mol);
                sourceAtom = mol.addAtom((float) evt.getX(), (float) evt.getY());
                java.awt.geom.Point2D p = suggestNewX2AndY2(sourceAtom);
                int t = mol.addAtom((float) p.getX() + mol.getAverageBondLength(), (float) pt.getY());
                onAddBond(sourceAtom, t);
                // This creates a new Fragment, so make sure scheme gets layouted (if in RXN mode)
                model.needsLayout(true);
                ok = true;
            }
        } else {
            if (mol != null) {
                if (sourceAtom != -1) {
                    int t = selectedAtom;
                    if (t == -1) {
                        double dx = origin.getX() - pt.getX();
                        double dy = origin.getY() - pt.getY();
                        java.awt.geom.Point2D p = pt;
                        if (dx * dx + dy * dy < Model.MIN_BOND_LENGTH_SQUARE) {
                            p = suggestNewX2AndY2(sourceAtom);
                        }
                        t = mol.addAtom((float) p.getX(), (float) p.getY(), 0.0f);
                    }
                    StereoMolecule tm = model.getMoleculeAt(pt, true);
                    if (mol == tm) {
                        onAddBond(sourceAtom, t);
                    } else if (tm != null) {
                        mol.addMolecule(tm);
                        t = mol.findAtom((float) pt.getX(), (float) pt.getY());
                        model.deleteMolecule(tm);
                        onAddBond(sourceAtom, t);
                    }
                    ok = true;
                } else { // mol == null
                    int atom1 = mol.addAtom((float) origin.getX(), (float) origin.getY());
                    int atom2 = mol.addAtom((float) pt.getX(), (float) pt.getY());
                    onAddBond(atom1, atom2);
                    // This creates a new Fragment, so make sure scheme gets layouted (if in RXN mode)
                    model.needsLayout(true);
                    ok = true;
                }
            } else {
                mol = model.getMolecule();
                int atom1 = mol.addAtom((float) origin.getX(), (float) origin.getY());
                int atom2 = mol.addAtom((float) pt.getX(), (float) pt.getY());
                onAddBond(atom1, atom2);
                ok = true;
            }
        }
//        theAtom = -1;
        dragging = false;
        return ok;

    }


//    public boolean onMouseMove(Point2D pt, boolean drag)
//    {
//        dragging = drag;
//        if (!drag) {
//            return trackHighLight(pt, true);
//        } else {
//            return onDrag(pt);
//        }
//    }
//
//    boolean onDrag(Point2D pt)
//    {
//        double dx = Math.abs(pt.getX()-origin.getX());
//        double dy = Math.abs(pt.getY()-origin.getY());
//        if(dx > 5 || dy > 5) {
//            trackHighLight(pt, false);
//            last = pt;
//        } else
//            last = null;
//        return true;
//    }


    public boolean paint(IDrawContext _ctx)
    {
        boolean ok = super.paint(_ctx);
        if (dragging) {
            drawBondLine(_ctx);
            ok = true;
        }

        return ok;

    }

    private void drawBondLine(IDrawContext ctx)
    {
        java.awt.geom.Point2D p = origin;
        System.out.println("Origin " + origin + " last " + last);
        if (p != null && last != null) {
            int atom = getAtomAt(p);
            StereoMolecule mol = model.getMoleculeAt(p, true);
            if (mol != null && atom != -1) {
                p = new Point2D.Double(mol.getAtomX(atom), mol.getAtomY(atom));
            }
            ctx.save();
//            ctx.setStroke(IColor.BLACK);
            ctx.drawLine(p.getX(), p.getY(), last.getX(), last.getY());
            ctx.restore();
        }
    }


}

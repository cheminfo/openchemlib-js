/*
 * Project: DD_jfx
 * @(#)NewChainAction.java
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
import com.actelion.research.share.gui.editor.geom.ICursor;
import com.actelion.research.share.gui.editor.geom.IDrawContext;
import com.actelion.research.share.gui.editor.io.IMouseEvent;

import java.awt.geom.Point2D;

/**
 * Project:
 * User: rufenec
 * Date: 3/26/13
 * Time: 3:42 PM
 */
public class NewChainAction extends BondHighlightAction
        //DrawAction
{

    //    private int theAtom = -1;
    private int sourceAtom = -1;
    //    private boolean isDragging = false;
//    private Point2D origin = null;
    private int numChainAtoms = 0;
    private double[] mChainAtomX = null;
    private double[] mChainAtomY = null;
    private int[] mChainAtom = null;

    public NewChainAction(Model model)
    {
        super(model);
    }

    public boolean onMouseDown(IMouseEvent evt)
    {
        java.awt.geom.Point2D pt = new Point2D.Double(evt.getX(), evt.getY());
        boolean update = false;
        origin = pt;
        sourceAtom = getAtomAt(pt);
        if (sourceAtom != -1) {
            StereoMolecule mol = model.getMolecule();
            //if (mol != null)
            {
                if (mol.getAllConnAtoms(sourceAtom) == Model.MAX_CONNATOMS) {
                    return false;
                }
                origin = new Point2D.Double(mol.getAtomX(sourceAtom), mol.getAtomY(sourceAtom));
                update = true;
                numChainAtoms = 0;
                mChainAtomX = null;
                mChainAtomY = null;
                mChainAtom = null;
            }
        } else {
            StereoMolecule mol = model.getMolecule();
//            model.setSelectedMolecule(mol);
            origin = new Point2D.Double(evt.getX(), evt.getY());
            update = true;
            numChainAtoms = 0;
            mChainAtomX = null;
            mChainAtomY = null;
            mChainAtom = null;
//            isDragging = true;
        }

        return update;
    }

    public boolean onMouseUp(IMouseEvent evt)
    {
        boolean ok = false;
        model.pushUndo();
        java.awt.geom.Point2D pt = new Point2D.Double(evt.getX(), evt.getY());
        StereoMolecule mol = model.getMolecule();//.getSelectedMolecule();
        //if (mol != null)
        {
            if (numChainAtoms == 0) {
                mol = model.getMoleculeAt(pt, false);
                if (mol != null) {
                    int atom = model.getSelectedAtom();
                    if (atom != -1) {
                        java.awt.geom.Point2D p = suggestNewX2AndY2(atom);
                        int targetAtom = mol.findAtom((float) p.getX(), (float) p.getY());
                        if (targetAtom != -1) {
                            mol.addOrChangeBond(atom, targetAtom, Molecule.cBondTypeSingle);
                        } else {
                            targetAtom = mol.addAtom((float) p.getX(), (float) p.getY(), 0.0f);
                            mol.addBond(atom, targetAtom, Molecule.cBondTypeSingle);
                            mol.ensureHelperArrays(Molecule.cHelperNeighbours);
                        }
                    }
                }

            } else if (numChainAtoms > 0) {
                if (sourceAtom == -1) {
                    sourceAtom = mol.addAtom((float) origin.getX(), (float) origin.getY());
                }

                if (mChainAtom[0] == -1) {
                    mChainAtom[0] = mol.addAtom((float) mChainAtomX[0], (float) mChainAtomY[0]);
                }

                if (mChainAtom[0] != -1) {
                    mol.addBond(sourceAtom, mChainAtom[0], Molecule.cBondTypeSingle);
                }
                model.needsLayout(true);
            }

            if (numChainAtoms > 1) {
//                System.out.printf("Connecting %d bonds \n", numChainAtoms);
                for (int i = 1; i < numChainAtoms; i++) {
                    if (mChainAtom[i] == -1) {
                        mChainAtom[i] = mol.addAtom((float) mChainAtomX[i], (float) mChainAtomY[i]);
                    }
                    if (mChainAtom[i] != -1) {
                        mol.addBond(mChainAtom[i - 1], mChainAtom[i], Molecule.cBondTypeSingle);
                    }
                }
                model.needsLayout(true);
            }
            highlightAtom(mol, -1);
            ok = true;

        }
        dragging = false;
        return ok;

    }


    @Override
    protected boolean onDrag(java.awt.geom.Point2D pt)
    {
        StereoMolecule mol = model.getMolecule();//.getSelectedMolecule();
        boolean repaintNeeded = false;
        if (mol != null) {
            double lastX, lastY;
            if (numChainAtoms > 0) {
                lastX = mChainAtomX[numChainAtoms - 1];
                lastY = mChainAtomY[numChainAtoms - 1];
            } else {
                lastX = 0.0;
                lastY = 0.0;
            }
            double avbl = mol.getAverageBondLength();
            double s0 = avbl;//.floor();
            double s1 = (0.866 * avbl);//.floor();
            double s2 = (0.5 * avbl);//.floor();
            double dx = pt.getX() - origin.getX();
            double dy = pt.getY() - origin.getY();
            double a = 1.0;// sqrt(avbl/2*avbl/2);
            double b = 1.0;//sqrt(avbl/2*avbl/2);


            if (Math.abs(dy) > Math.abs(dx)) {
                numChainAtoms = (int) (2 * Math.abs(dy) / (s0 + s2));
                if ((int) Math.abs(dy) % (int) (s0 + s2) > s0) {
                    numChainAtoms++;
                }
                mChainAtomX = new double[numChainAtoms];
                mChainAtomY = new double[numChainAtoms];
                if (pt.getX() < origin.getX()) {
                    b = -b;
                }
                if (pt.getY() < origin.getY()) {
                    a = -a;
                }
                if (numChainAtoms > 0) {
                    mChainAtomX[0] = origin.getX() + s1 * b;
                    mChainAtomY[0] = origin.getY() + s2 * a;
                    for (int i = 1; i < numChainAtoms; i++) {
                        if ((i & 1) == 0) {
                            mChainAtomX[i] = mChainAtomX[i - 1] + s0 * b;
                            mChainAtomY[i] = mChainAtomY[i - 1] + s2 * a;
                        } else {
                            mChainAtomX[i] = mChainAtomX[i - 1];
                            mChainAtomY[i] = mChainAtomY[i - 1] + s0 * a;
                        }
                    }
                }
            } else {
                numChainAtoms = (int) (Math.abs(dx) / s1);
                mChainAtomX = new double[numChainAtoms];
                mChainAtomY = new double[numChainAtoms];
                if (pt.getX() < origin.getX()) {
                    s1 = -s1;
                }
                if (pt.getY() < origin.getY()) {
                    s2 = -s2;
                }
                for (int i = 0; i < numChainAtoms; i++) {
                    mChainAtomX[i] = origin.getX() + (i + 1) * s1;
                    mChainAtomY[i] = origin.getY();
                    if ((i & 1) == 0) {
                        mChainAtomY[i] += s2;
                    }
                }
            }
            if (numChainAtoms > 0) {
                mChainAtom = new int[numChainAtoms];
                for (int i = 0; i < numChainAtoms; i++) {
                    mChainAtom[i] = mol.findAtom((float) mChainAtomX[i], (float) mChainAtomY[i]);
                    if (mChainAtom[i] != -1) {
                        mChainAtomX[i] = mol.getAtomX(mChainAtom[i]);
                        mChainAtomY[i] = mol.getAtomY(mChainAtom[i]);
                    }
                }
                if (mChainAtomX[numChainAtoms - 1] != lastX
                        || mChainAtomY[numChainAtoms - 1] != lastY) {
                    repaintNeeded = true;
                }
            } else if (lastX != 0 || lastY != 0) {
                repaintNeeded = true;
            }
        }
        return repaintNeeded;

    }

    @Override
    public boolean paint(IDrawContext ctx)
    {
        StereoMolecule mol = model.getMolecule();//.getSelectedMolecule();
        if (mol != null) {
            if (!dragging) {
                super.paint(ctx);
            } else {
                int theAtom = model.getSelectedAtom();
                drawChain(ctx, theAtom != -1 ? new Point2D.Double(mol.getAtomX(theAtom), mol.getAtomY(theAtom)) : origin);
//                drawChain(ctx);
            }
        }
        return false;
    }

    private void drawChain(IDrawContext ctx, java.awt.geom.Point2D pt)
    {
        if (numChainAtoms > 0) {
            drawLine(ctx, pt.getX(), pt.getY(), mChainAtomX[0], mChainAtomY[0]);
        }
        if (numChainAtoms > 1) {
            for (int i = 1; i < numChainAtoms; i++) {
                drawLine(ctx, mChainAtomX[i - 1], mChainAtomY[i - 1], mChainAtomX[i], mChainAtomY[i]);
            }
        }
    }


    void drawLine(IDrawContext _ctx, double x1, double y1, double x2, double y2)
    {
        _ctx.drawLine(x1, y1, x2, y2);
    }

    @Override
    public int getCursor()
    {
        return ICursor.TOOL_CHAINCURSOR;
    }

}

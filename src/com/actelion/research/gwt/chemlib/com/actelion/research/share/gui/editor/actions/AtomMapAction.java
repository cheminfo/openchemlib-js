/*
 * Project: DD_jfx
 * @(#)AtomMapAction.java
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

import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.geom.IColor;
import com.actelion.research.share.gui.editor.geom.IDrawContext;
import com.actelion.research.share.gui.editor.io.IKeyEvent;
import com.actelion.research.share.gui.editor.io.IMouseEvent;

import java.awt.geom.Point2D;

/**
 * Project:
 * User: rufenec
 * Date: 5/22/13
 * Time: 4:00 PM
 */
public class AtomMapAction extends AtomHighlightAction
{

    private java.awt.geom.Point2D firstPoint = null;
    private java.awt.geom.Point2D lastPoint = null;
    private int secondAtom = -1;

    public AtomMapAction(Model model)
    {
        super(model);
    }

    public  void onActionEnter()
    {
        int mode = model.getDisplayMode();
        model.setDisplayMode(mode | AbstractDepictor.cDModeShowMapping| AbstractDepictor.cDModeSuppressCIPParity);
    }

    public void onActionLeave()
    {
        int mode = model.getDisplayMode();
        if ((mode & AbstractDepictor.cDModeShowMapping) != 0) {
            mode &= ~(AbstractDepictor.cDModeShowMapping | AbstractDepictor.cDModeSuppressCIPParity);
            model.setDisplayMode(mode);
        }
    }

//    @Override
//    public boolean onMouseDown(ACTMouseEvent evt)
//    {
//        origin = new Point2D.Double(evt.getX(),evt.getY());
//        return super.onMouseDown(evt);
//    }


    @Override
    public boolean onKeyPressed(IKeyEvent evt)
    {
        if (evt.getCode().equals(builder.getDeleteKey())) {
            StereoMolecule mMol = model.getMolecule();//.getSelectedMolecule();
            boolean found = false;
            for (int atom = 0; atom < mMol.getAllAtoms(); atom++) {
                if (mMol.getAtomMapNo(atom) != 0) {
                    mMol.setAtomMapNo(atom, 0, false);
                    found = true;
                }
            }
            return found;
        }
        return super.onKeyPressed(evt);
    }

    @Override
    public boolean onMouseMove(IMouseEvent evt, boolean drag)
    {
        firstPoint = lastPoint = null;
        if (model.isReaction()) {
            StereoMolecule mol = model.getMolecule();//.getSelectedMolecule();
            if (!drag) {
                java.awt.geom.Point2D pt = new Point2D.Double(evt.getX(), evt.getY());
                secondAtom = -1;
                if(trackHighLight(pt)) {
                    int mCurrentHiliteAtom = model.getSelectedAtom();
                    if (mCurrentHiliteAtom != -1) {
                        int mapNo = mol.getAtomMapNo(mCurrentHiliteAtom);
                        if (mapNo != 0) {
                            for (int atom = 0; atom < mol.getAtoms(); atom++) {
                                if (atom != mCurrentHiliteAtom
                                    && mol.getAtomMapNo(atom) == mapNo) {
                                    secondAtom = atom;
                                    break;
                                }
                            }
                        }
                    }
                    return true;
                }
            } else {
                int atom = model.getSelectedAtom();
                if (mol != null && atom != -1) {
                    java.awt.geom.Point2D pt = new Point2D.Double(evt.getX(), evt.getY());
                    firstPoint = new Point2D.Double(mol.getAtomX(atom), mol.getAtomY(atom));
                    lastPoint = pt;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    boolean trackHighLight(java.awt.geom.Point2D pt) {
        int lastAtom = model.getSelectedAtom();
        boolean ok = super.trackHighLight(pt);
        int theAtom = model.getSelectedAtom();
        return ok || lastAtom != theAtom;
    }

    @Override
    public boolean onMouseUp(IMouseEvent ev)
    {
        int mode = model.getDisplayMode();
        if ((mode & AbstractDepictor.cDModeShowMapping) == 0) {
            mode |= AbstractDepictor.cDModeShowMapping;
            //System.out.println("Display Mode " + mode);
            model.setDisplayMode(mode);
        }
        int atom = model.getSelectedAtom();
        if (atom != -1) {
            model.mapReaction(atom,firstPoint,lastPoint);
            //assistedMap(atom);
        }
        model.setSelectedAtom(-1);
        firstPoint = lastPoint = null;
        return true;
    }

//    private void assistedMap(int atom)
//    {
//        StereoMolecule mol = model.getSelectedMolecule();
//        int freeMapNo = model.getNextMapNo();
//        if (mol != null) {
//            StereoMolecule source = model.getFragmentAt(firstPoint, false);
//            StereoMolecule target = model.getFragmentAt(lastPoint, false);
//            if (target != null && target != source) {
//                int dest = mol.findAtom((int) lastPoint.getX(), (int) lastPoint.getY());
//                if (dest != -1) {
//                    mol.setAtomMapNo(atom, freeMapNo, false);
//                    mol.setAtomMapNo(dest, freeMapNo, false);
//                }
//                model.tryAutoMapReaction();
//            }
//        }
//    }
//

    @Override
    public boolean paint(IDrawContext ctx)
    {
        boolean ok = false;
        ctx.save();
        if (model.isReaction()) {
            StereoMolecule mol = model.getMolecule();//.getSelectedMolecule();
            if (firstPoint != null && lastPoint != null) {
                StereoMolecule source = model.getFragmentAt(firstPoint, false);
                StereoMolecule target = model.getFragmentAt(lastPoint, false);
                if (target != null && target != source) {
                    int theAtom = mol.findAtom((float)lastPoint.getX(),(float)lastPoint.getY());
                    if (theAtom != -1)
                        drawAtomHighlight(ctx, mol, theAtom);
                }
//                ctx.setStroke(IColor.RED);
                ctx.setStroke(builder.getMapToolColor());
                ctx.drawLine(firstPoint.getX(), firstPoint.getY(), lastPoint.getX(), lastPoint.getY());
            } else if( secondAtom != -1) {
                drawAtomHighlight(ctx, mol, secondAtom);
            }
        }
        ok = super.paint(ctx);
        ctx.restore();
        return ok;
    }

}

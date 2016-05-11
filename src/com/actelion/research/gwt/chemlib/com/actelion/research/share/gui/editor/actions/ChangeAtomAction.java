/*
 * Project: DD_jfx
 * @(#)ChangeAtomAction.java
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

import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.io.IMouseEvent;

import java.awt.geom.Point2D;

/**
 * Project:
 * User: rufenec
 * Date: 2/1/13
 * Time: 4:13 PM
 */
public class ChangeAtomAction extends AtomHighlightAction {

    int theAtomNo = 6;

    public ChangeAtomAction(Model model, int atomNo) {
        super(model);
        theAtomNo = atomNo;
        //        this.ctx = ctx;
    }


    @Override
    public boolean onMouseUp(IMouseEvent evt) {
        model.pushUndo();
        int theAtom = model.getSelectedAtom();
        java.awt.geom.Point2D pt = new Point2D.Double(evt.getX(), evt.getY());
        StereoMolecule mol = model.getMoleculeAt(pt, false);
        if (mol != null && theAtom != -1) {
            mol.setAtomicNo(theAtom, theAtomNo);
        } else {
            if (mol == null) {
                mol = model.getMolecule();
//                model.setSelectedMolecule(mol);
                model.needsLayout(true);
            }
            int atom = mol.addAtom((float) pt.getX(), (float) pt.getY());
            mol.setAtomicNo(atom, theAtomNo);
        }
        setHighlightAtom(mol, -1);
        return true;
    }

    @Override
    boolean trackHighLight(java.awt.geom.Point2D pt) {
        int lastAtom = model.getSelectedAtom();
        boolean ok = super.trackHighLight(pt);
        int theAtom = model.getSelectedAtom();
        return ok || lastAtom != theAtom;
    }
}

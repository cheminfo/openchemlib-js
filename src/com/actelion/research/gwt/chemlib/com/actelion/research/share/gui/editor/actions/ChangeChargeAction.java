/*
 * Project: DD_jfx
 * @(#)ChangeChargeAction.java
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

/**
 * Project:
 * User: rufenec
 * Date: 2/1/13
 * Time: 4:13 PM
 */
public class ChangeChargeAction extends AtomHighlightAction
{

    boolean plus;

    public ChangeChargeAction(Model model, boolean positive)
    {
        super(model);
        this.plus = positive;
    }


    @Override
    public boolean onMouseUp(IMouseEvent evt)
    {
        model.pushUndo();
        int theAtom = model.getSelectedAtom();
        if (theAtom != -1) {
            StereoMolecule mol = model.getMolecule();//.getSelectedMolecule();
            int charge = mol.getAtomCharge(theAtom);
            if (plus)
                charge++;
            else
                charge--;
            mol.setAtomCharge(theAtom,charge);
            return true;
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

}

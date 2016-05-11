/*
 * Project: DD_jfx
 * @(#)UnknownParityAction.java
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
 * Date: 3/26/13
 * Time: 4:33 PM
 */
public class UnknownParityAction extends AtomHighlightAction
{

    public UnknownParityAction(Model model)
    {
        super(model);
    }

    @Override
    public boolean onMouseUp(IMouseEvent evt)
    {
        model.pushUndo();
        boolean ok = false;

        int theAtom = model.getSelectedAtom();
        StereoMolecule mol = model.getMolecule();//.getSelectedMolecule();
        if (mol != null && theAtom != -1) {
            mol.setAtomConfigurationUnknown(theAtom,!mol.isAtomConfigurationUnknown(theAtom));
            ok = true;
        }

        return  ok;
    }
}

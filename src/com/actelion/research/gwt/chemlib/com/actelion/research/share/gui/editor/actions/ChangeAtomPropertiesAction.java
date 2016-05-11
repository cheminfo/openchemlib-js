/*
 * Project: DD_jfx
 * @(#)ChangeAtomPropertiesAction.java
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
import com.actelion.research.share.gui.DialogResult;
import com.actelion.research.share.gui.editor.dialogs.IAtomPropertiesDialog;
import com.actelion.research.share.gui.editor.io.IMouseEvent;

/**
 * Project:
 * User: rufenec
 * Date: 2/1/13
 * Time: 4:13 PM
 */
public class ChangeAtomPropertiesAction extends AtomHighlightAction
{

    public ChangeAtomPropertiesAction(Model model)
    {
        super(model);
    }

    @Override
    public boolean onMouseUp(IMouseEvent evt)
    {
        model.pushUndo();
        int theAtom = model.getSelectedAtom();
        StereoMolecule mol = model.getMolecule();//.getSelectedMolecule();
        if (mol != null && theAtom != -1) {
            IAtomPropertiesDialog dlg = builder.createAtomPropertiesDialog(mol,theAtom);
            if (dlg.doModalAt(evt.getX(),evt.getY()) == DialogResult.IDOK) {
                return true;
            }
        }
        return false;
    }


    public static boolean isEmptyString(String s)
    {
        return s == null || s.trim().length()==0;
    }
}

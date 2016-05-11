/*
 * Project: DD_jfx
 * @(#)DeleteAction.java
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
import com.actelion.research.share.gui.editor.geom.ICursor;
import com.actelion.research.share.gui.editor.io.IMouseEvent;


/**
 * Project:
 * User: rufenec
 * Date: 3/26/13
 * Time: 4:15 PM
 */
public class DeleteAction extends BondHighlightAction
{
    public DeleteAction(Model model)
    {
        super(model);
    }

    @Override
    public boolean onMouseUp(IMouseEvent evt)
    {
        boolean ok = false;
        model.pushUndo();
        StereoMolecule mol = model.getMolecule();//.getSelectedMolecule();
        int theAtom = model.getSelectedAtom();
        int theBond = model.getSelectedBond();
        if (mol != null && theAtom != -1) {
            if (mol.isSelectedAtom(theAtom)) {
                mol.deleteSelectedAtoms();
            } else {
                mol.deleteAtom(theAtom);
            }
            ok = true;
        } else if (mol != null && theBond != -1) {
            if (mol.isSelectedBond(theBond)) {
                mol.deleteSelectedAtoms();
            } else {
                mol.deleteBondAndSurrounding(theBond);
            }
            ok = true;
        }
        setHighlightAtom(null,-1);
        setHighlightBond(null,-1);
        return ok;
    }



    @Override
    public int getCursor()
    {
        return ICursor.TOOL_DELETECURSOR;
    }
//    @Override
//    public boolean onKeyPressed(KeyEvent evt)
//    {
//        if (evt.getCode() == KeyCode.DELETE) {
//            model.pushUndo();
//            StereoMolecule mol = model.getMol();
//            if (mol.deleteSelectedAtoms()) {
//                return true;
//            }
//        }
//        return false;
//    }
//

}

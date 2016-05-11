/*
 * Project: DD_jfx
 * @(#)CutAction.java
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

import com.actelion.research.share.gui.editor.Model;

/**
 * Project:
 * User: rufenec
 * Date: 5/16/13
 * Time: 3:43 PM
 */
public class CutAction extends CommandAction
{
    public CutAction(Model model)
    {
        super(model);
    }

    @Override
    public void onCommand()
    {
//        ClipboardHandler handler = new ClipboardHandler();
//        handler.copyMolecule(model.getMol());
//        model.pushUndo();
//        model.setValue(new StereoMolecule());

    }
}


/*
 * Project: DD_jfx
 * @(#)UndoAction.java
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
 * Date: 3/26/13
 * Time: 4:39 PM
 */
public class UndoAction extends CommandAction
{

    public UndoAction(Model m)
    {
        super(m);
    }

    @Override
    public void onCommand()
    {
        model.popUndo();
    }
}

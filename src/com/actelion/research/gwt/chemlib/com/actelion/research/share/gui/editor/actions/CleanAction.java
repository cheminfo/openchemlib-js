/*
 * Project: DD_jfx
 * @(#)CleanAction.java
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
 * Date: 1/28/13
 * Time: 10:09 AM
 */
public class CleanAction extends CommandAction
{

    public CleanAction(Model model)
    {
        super(model);
    }

    @Override
    public void onCommand()
    {
//        model.pushUndo();
        if (model.isReaction()) {
            model.cleanReaction(true);
        } else {
            model.cleanMolecule(true);
        }
        model.needsLayout(true);
    }


}

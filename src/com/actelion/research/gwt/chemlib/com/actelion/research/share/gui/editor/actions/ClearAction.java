/*
 * Project: DD_jfx
 * @(#)ClearAction.java
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
 * Date: 1/24/13
 * Time: 5:09 PM
 */
public class ClearAction extends CommandAction
{

  public ClearAction(Model model)
  {
      super(model);
  }

    public void onCommand()
  {
      model.pushUndo();
      model.setNewMolecule();
  }

}

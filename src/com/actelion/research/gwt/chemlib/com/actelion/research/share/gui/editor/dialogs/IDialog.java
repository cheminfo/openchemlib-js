/*
 * Project: DD_jfx
 * @(#)IDialog.java
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

package com.actelion.research.share.gui.editor.dialogs;

import com.actelion.research.share.gui.DialogResult;

public interface IDialog
{
    DialogResult doModal();
    DialogResult doModalAt(double x, double y);

}

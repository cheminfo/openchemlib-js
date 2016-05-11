/*
 * Project: DD_jfx
 * @(#)IMouseEvent.java
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

package com.actelion.research.share.gui.editor.io;

/**
 * Project:
 * User: rufenec
 * Date: 11/24/2014
 * Time: 3:19 PM
 */
public interface IMouseEvent
{
    double getX();

    double getY();

    boolean isShiftDown();

    boolean isControlDown();

    boolean isAltDown();

}
/*
 * Project: DD_jfx
 * @(#)ICursor.java
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

package com.actelion.research.share.gui.editor.geom;

import java.awt.*;

/**
 * Project:
 * User: rufenec
 * Date: 11/24/2014
 * Time: 3:21 PM
 */
public interface ICursor
{

    int DEFAULT = java.awt.Cursor.DEFAULT_CURSOR;
    int SE_RESIZE = java.awt.Cursor.SW_RESIZE_CURSOR;
    int CROSSHAIR = java.awt.Cursor.CROSSHAIR_CURSOR;

    int MAX_DEFAULT_CURSOR =  Cursor.MOVE_CURSOR;
    int TOOL_CURSOR_BASE = Cursor.MOVE_CURSOR + 32;

    int TOOL_CHAINCURSOR = TOOL_CURSOR_BASE + 0;
    int TOOL_DELETECURSOR = TOOL_CURSOR_BASE + 1;
    int TOOL_HANDCURSOR = TOOL_CURSOR_BASE + 2;
    int TOOL_HANDPLUSCURSOR = TOOL_CURSOR_BASE + 3;
    int TOOL_FISTCURSOR = TOOL_CURSOR_BASE + 4;
    int TOOL_LASSOCURSOR = TOOL_CURSOR_BASE + 5;
    int TOOL_LASSOPLUSCURSOR = TOOL_CURSOR_BASE + 6;
    int TOOL_SELECTRECTCURSOR = TOOL_CURSOR_BASE + 7;
    int TOOL_SELECTRECTPLUSCURSOR = TOOL_CURSOR_BASE + 8;
    int TOOL_ZOOMCURSOR = TOOL_CURSOR_BASE + 9;

    int TOOL_POINTERCURSOR = Cursor.DEFAULT_CURSOR;
    int TOOL_CTEXTCURSOR = Cursor.TEXT_CURSOR;
}

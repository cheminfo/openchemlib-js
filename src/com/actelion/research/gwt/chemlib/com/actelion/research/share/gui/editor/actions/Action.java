/*
 * Project: DD_jfx
 * @(#)Action.java
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
import com.actelion.research.share.gui.editor.geom.ICursor;
import com.actelion.research.share.gui.editor.geom.GeomFactory;
import com.actelion.research.share.gui.editor.geom.IDrawContext;
import com.actelion.research.share.gui.editor.io.IKeyEvent;
import com.actelion.research.share.gui.editor.io.IMouseEvent;


/**
 * Basic Interface for all editor actions
 */
public interface Action
{
    /**
     * Handles Mouse down events
     * @param ev
     * @return true if the action handles the event
     */
    boolean onMouseDown(IMouseEvent ev);

    /**
     * Handles the MouseUp event
     * @param ev
     * @return true if the action handles the event
     */
    boolean onMouseUp(IMouseEvent ev);

    boolean onMouseMove(IMouseEvent ev, boolean drag);

    boolean onKeyPressed(IKeyEvent evt);

    boolean onKeyReleased(IKeyEvent evt);

    boolean onDoubleClick(IMouseEvent ev);

    boolean isCommand();

    void onCommand();

    boolean paint(IDrawContext ctx);

    int getCursor();

    void onActionLeave();

    void onActionEnter();
}



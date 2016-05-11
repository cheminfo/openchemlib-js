/*
 * Project: DD_jfx
 * @(#)CommandAction.java
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
import com.actelion.research.share.gui.editor.geom.GeomFactory;
import com.actelion.research.share.gui.editor.geom.ICursor;
import com.actelion.research.share.gui.editor.geom.IDrawContext;
import com.actelion.research.share.gui.editor.io.IKeyEvent;
import com.actelion.research.share.gui.editor.io.IMouseEvent;

/**
 * Abstract class for handling of all the actions which
 * handle single type of actions: the action is executed when
 * pressing the button and the current drawing action will remain unchanged
 */
public abstract class CommandAction implements Action
{
    protected Model model;
    protected static GeomFactory builder = GeomFactory.getGeomFactory() ;

    public CommandAction(Model m)
    {
        model = m;
    }

//    protected abstract void onCommand();

    @Override
    public final boolean onMouseDown(IMouseEvent evt)
    {
        return false;
    }

    @Override
    public final boolean onMouseUp(IMouseEvent evt)
    {
        onCommand();
        return true;
    }

    @Override
    public final boolean onMouseMove(IMouseEvent evt, boolean drag)
    {
        return false;
    }

    @Override
    public boolean onDoubleClick(IMouseEvent ev)
    {
        return false;
    }

//    public boolean onSelection(boolean selected)
//    {
//        return false;
//    }

    @Override
    public boolean paint(IDrawContext ctx)
    {
        return false;
    }

    @Override
    public final boolean isCommand()
    {
        return true;
    }

    @Override
    public boolean onKeyPressed(IKeyEvent evt)
    {
        return false;
    }

    @Override
    public boolean onKeyReleased(IKeyEvent evt)
    {
        return false;
    }

    @Override
    public int getCursor()
    {
        return ICursor.DEFAULT;
    }


    public void onActionLeave()
    {

    }

    public void onActionEnter()
    {

    }

}

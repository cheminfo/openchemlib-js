/*
 * Project: DD_jfx
 * @(#)ArrowAction.java
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
import com.actelion.research.share.gui.editor.chem.IArrow;
import com.actelion.research.share.gui.editor.geom.IDrawContext;
import com.actelion.research.share.gui.editor.io.IMouseEvent;

import java.awt.geom.Point2D;

/**
 * Project:
 * User: rufenec
 * Date: 5/16/13
 * Time: 3:46 PM
 */
public class ArrowAction extends DrawAction
{
    java.awt.geom.Point2D origin,last;
    IArrow arrow = null;

    public ArrowAction(Model model)
    {
        super(model);
    }

    @Override
    public boolean onMouseDown(IMouseEvent ev)
    {
        origin = new Point2D.Double(ev.getX(),ev.getY());
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean onMouseUp(IMouseEvent ev)
    {
        if (arrow != null) {
            model.addDrawingObject(arrow);
        }
        return true;
    }

    @Override
    public boolean onMouseMove(IMouseEvent ev, boolean drag)
    {
        if (drag) {
            last = new Point2D.Double(ev.getX(),ev.getY());
            java.awt.geom.Rectangle2D r = new java.awt.geom.Rectangle2D.Double(
                (int) Math.min(last.getX(), origin.getX()),
                 (int) last.getY(),
                 (int) Math.abs(last.getX() - origin.getX()),
                 2);
//            IRectangle2D r = builder.createRectangle(
//                (int) Math.min(last.getX(), origin.getX()),
//                (int) last.getY(),
//                (int) Math.abs(last.getX() - origin.getX()),
//                2);
            arrow = builder.createArrow(r);
            return true;
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean paint(IDrawContext ctx)
    {
//        if (arrow != null) {
//            arrow.draw(ctx);
//            return true;
//        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

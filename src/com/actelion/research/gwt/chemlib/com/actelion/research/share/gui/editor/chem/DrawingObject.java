/*
 * Project: DD_jfx
 * @(#)DrawingObject.java
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

package com.actelion.research.share.gui.editor.chem;


import com.actelion.research.share.gui.editor.geom.IDrawContext;

/**
 * Project:
 * User: rufenec
 * Date: 5/16/13
 * Time: 12:31 PM
 */

public abstract class DrawingObject
        implements Cloneable,IDrawingObject
{

//    Object o;
    private boolean selected = false;

    public final boolean isSelected()
    {
        return selected;
    }
    public final void setSelected(boolean set)
    {
        selected = set;
    }

    private boolean layouted =false;
    public boolean isLayouted()
    {
        return layouted;
    }

    public void setLayouted(boolean layouted)
    {
        this.layouted = layouted;
    }

    public abstract void draw(IDrawContext ctx);
//    public abstract boolean pointWouldSelect(Point2D pt);
	public abstract java.awt.geom.Rectangle2D getBoundingRect();
    public abstract void move(float dx, float dy);
   	public abstract void scale(float f);

    public Object clone()
    {
        try {
            return super.clone();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}


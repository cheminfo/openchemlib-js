/*
 * Project: DD_jfx
 * @(#)IDrawingObject.java
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

import com.actelion.research.chem.DepictorTransformation;
import com.actelion.research.share.gui.editor.geom.IDrawContext;

/**
 * Project:
 * User: rufenec
 * Date: 11/24/2014
 * Time: 3:28 PM
 */
public interface IDrawingObject
{
    void setSelected(boolean b);
    boolean isSelected();
    void move(float v, float v1);
    java.awt.geom.Rectangle2D getBoundingRect();
    void setRect(float x,float y, float w, float h);
    void scale(float scaling);
    void draw(IDrawContext ctx,DepictorTransformation t);
}

package com.actelion.research.share.gui.editor.chem;

import com.actelion.research.share.gui.editor.geom.IDrawContext;

/**
 * Project:
 * User: rufenec
 * Date: 11/24/2014
 * Time: 3:28 PM
 */
public interface IDrawingObject
{
//    IRectangle2D getBoundingRect();

    void setSelected(boolean b);

    void move(float v, float v1);

    java.awt.geom.Rectangle2D getBoundingRect();

    void scale(float scaling);

    void paint(IDrawContext ctx);
}

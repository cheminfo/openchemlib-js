package com.actelion.research.share.gui.editor.geom;

/**
 * Project:
 * User: rufenec
 * Date: 11/24/2014
 * Time: 3:17 PM
 */
public interface IRectangle2D
{
    boolean contains(double x, double y);

    boolean intersects(IRectangle2D rc);

    double getMinX();

    double getMinY();

    double getWidth();

    double getHeight();

    double getMaxX();

    double getMaxY();
}

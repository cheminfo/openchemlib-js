package com.actelion.research.share.gui.editor.geom;

/**
 * Project:
 * User: rufenec
 * Date: 11/24/2014
 * Time: 4:45 PM
 */
public interface IPolygon
{
    void add(java.awt.geom.Point2D pt);

    int size();

    java.awt.geom.Point2D get(int i);


    void remove(java.awt.geom.Point2D origin);

    boolean contains(double atomX, double atomY);
}

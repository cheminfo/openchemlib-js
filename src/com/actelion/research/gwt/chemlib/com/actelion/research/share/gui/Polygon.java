/*
 * Project: DD_jfx
 * @(#)Polygon.java
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

package com.actelion.research.share.gui;

import com.actelion.research.share.gui.editor.geom.IPolygon;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;


public class Polygon implements IPolygon
{
    List<java.awt.geom.Point2D> list = new ArrayList<java.awt.geom.Point2D>();

    public void add(java.awt.geom.Point2D o)
    {
        list.add(o);
    }

    public void remove(java.awt.geom.Point2D o)
    {
        int idx = list.lastIndexOf(o);
        if (idx >= 0)
            list.remove(idx);
    }

    public int size()
    {
        return list.size();
    }

    public java.awt.geom.Point2D get(int i)
    {
        return list.get(i);
    }

    Rectangle2D calculateBounds()
    {
        double boundsMinX = Double.MAX_VALUE;
        double boundsMinY = Double.MIN_VALUE;
        double boundsMaxX = 0.0;
        double boundsMaxY = 0.0;

        for (int i = 0; i < list.size(); i++) {
            double x = list.get(i).getX();
            boundsMinX = Math.min(boundsMinX, x);
            boundsMaxX = Math.max(boundsMaxX, x);
            double y = list.get(i).getY();
            boundsMinY = Math.min(boundsMinY, y);
            boundsMaxY = Math.max(boundsMaxY, y);
        }
        Rectangle2D r = new Rectangle2D.Double(boundsMinX, boundsMinY,
            boundsMaxX - boundsMinX,
            boundsMaxY - boundsMinY);
        return r;
    }

    public Rectangle2D getBoundingBox()
    {
        if (list.size() == 0) {
            return new Rectangle2D.Double(0.0, 0.0, 0.0, 0.0);
        }
        return calculateBounds();
    }

    public boolean contains(java.awt.geom.Point2D pt)
    {
        return contains(pt.getX(), pt.getX());
    }

    public boolean contains(double x, double y)
    {
        Rectangle2D r = getBoundingBox();
        boolean contains = r.contains(x, y);
        if (list.size() <= 2 || !contains) {
            return false;
        }
        int hits = 0;
        double lastx = list.get(list.size() - 1).getX();
        double lasty = list.get(list.size() - 1).getY();
        double curx;
        double cury;

        // Walk the edges of the polygon
        for (int i = 0; i < list.size(); lastx = curx, lasty = cury, i++) {
            curx = list.get(i).getX();
            cury = list.get(i).getY();

            if (cury == lasty) {
                continue;
            }

            double leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1 = 0.0;
            double test2 = 0.0;
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }
        return ((hits & 1) != 0);
    }

}

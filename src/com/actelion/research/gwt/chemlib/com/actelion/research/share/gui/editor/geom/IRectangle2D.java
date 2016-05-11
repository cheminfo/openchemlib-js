/*
 * Project: DD_jfx
 * @(#)IRectangle2D.java
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

/*
 * Project: GWTEditor
 * @(#)FontMetrics.java
 *
 * Copyright (c) 1997- 2014
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

package java.awt;

import java.awt.font.LineMetrics;

public interface FontMetrics
{
    public int getHeight();
    public LineMetrics getLineMetrics( String str, Graphics context);
    public int getStringWidth(String s);

}

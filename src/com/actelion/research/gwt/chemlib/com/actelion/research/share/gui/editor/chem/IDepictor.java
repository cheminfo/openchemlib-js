/*
 * Project: DD_jfx
 * @(#)IDepictor.java
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

import java.awt.geom.Rectangle2D;

/**
 * Project:
 * User: rufenec
 * Date: 11/24/2014
 * Time: 3:26 PM
 */
public interface IDepictor<T>
{
    DepictorTransformation updateCoords(IDrawContext<T> g, Rectangle2D.Double aFloat, int cModeInflateToMaxAVBL);

    DepictorTransformation simpleValidateView(Rectangle2D.Double viewRect, int mode);

    void setDisplayMode(int displayMode);

    void paint(IDrawContext<T> ctx);

    void setFragmentNoColor(long color);
}

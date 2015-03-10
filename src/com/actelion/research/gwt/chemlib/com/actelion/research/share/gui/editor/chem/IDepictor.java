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
    DepictorTransformation updateCoords(IDrawContext<T> g, Rectangle2D.Float aFloat, int cModeInflateToMaxAVBL);
    void setDisplayMode(int displayMode);
    void paint(IDrawContext<T> ctx);
    void setFragmentNoColor(long color);
}

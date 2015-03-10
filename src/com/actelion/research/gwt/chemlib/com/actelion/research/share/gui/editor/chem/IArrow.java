package com.actelion.research.share.gui.editor.chem;

/**
 * Project:
 * User: rufenec
 * Date: 11/24/2014
 * Time: 3:28 PM
 */
public interface IArrow extends IDrawingObject
{
    int getLength();

    void setCoordinates(float v, float v1, float v2, float v3);

    boolean isOnProductSide(float x, float y);
}

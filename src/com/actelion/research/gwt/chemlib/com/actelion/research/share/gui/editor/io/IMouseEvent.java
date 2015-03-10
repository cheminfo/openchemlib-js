package com.actelion.research.share.gui.editor.io;

/**
 * Project:
 * User: rufenec
 * Date: 11/24/2014
 * Time: 3:19 PM
 */
public interface IMouseEvent
{
    double getX();

    double getY();

    boolean isShiftDown();

    boolean isControlDown();
}
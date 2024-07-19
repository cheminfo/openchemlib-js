package com.actelion.research.gwt.gui.editor;

import com.actelion.research.share.gui.editor.io.IMouseEvent;
import com.google.gwt.event.dom.client.TouchEvent;

public class ACTTouchEvent<T extends TouchEvent> implements IMouseEvent {
  T _evt;
  MousePoint _mousePoint;

  public ACTTouchEvent(T evt, MousePoint mousePoint) {
    _evt = evt;
    _mousePoint = mousePoint;
  }

  public double getX() {
    return _mousePoint.getX();
  }

  public double getY() {
    return _mousePoint.getY();
  }

  public boolean isShiftDown() {
    return _evt.isShiftKeyDown();
  }

  public boolean isControlDown() {
    return _evt.isControlKeyDown();
  }

  @Override
  public boolean isAltDown() {
    return _evt.isAltKeyDown();
  }
}

package com.actelion.research.gwt.js.api.generic.internal;

import com.actelion.research.gui.generic.*;

public class JSRectangle {
  private GenericRectangle mRectangle;

  JSRectangle() {
    mRectangle = new GenericRectangle();
  }

  public void set(double x, double y, double w, double h) {
    mRectangle.set(x, y, w, h);
  }

  public GenericRectangle getRectangle() {
    return mRectangle;
  }
}

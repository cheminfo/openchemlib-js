package com.actelion.research.gwt.gui.generic;

import com.actelion.research.gui.generic.GenericRectangle;

import jsinterop.annotations.*;

@JsType(name = "Rectangle")
public class JSRectangle {
  private GenericRectangle mRectangle;

  JSRectangle() {
    mRectangle = new GenericRectangle();
  }

	public void set(double x, double y, double w, double h) {
    mRectangle.set(x, y, w, h);
  }

  @JsIgnore()
  public GenericRectangle getRectangle() {
    return mRectangle;
  }
}

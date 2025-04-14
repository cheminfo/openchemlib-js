package com.actelion.research.gwt.js.api.generic.internal;

import jsinterop.annotations.*;

import com.actelion.research.gui.generic.*;

@JsType
public class JSPolygon {
  private GenericPolygon mPolygon;

  @JsIgnore
  JSPolygon(GenericPolygon polygon) {
    mPolygon = polygon;
  }

  public int getSize() {
    return mPolygon.getSize();
  }

  public double getX(int i) {
    return mPolygon.getX(i);
  }

  public double getY(int i) {
    return mPolygon.getY(i);
  }
}

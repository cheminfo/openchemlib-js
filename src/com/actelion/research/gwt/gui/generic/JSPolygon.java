package com.actelion.research.gwt.gui.generic;

import com.actelion.research.gui.generic.GenericPolygon;

import jsinterop.annotations.*;

@JsType(name = "Polygon")
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

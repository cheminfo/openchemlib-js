package com.actelion.research.gwt.js.api.generic.internal;

import com.google.gwt.core.client.JavaScriptObject;

import com.actelion.research.gui.generic.*;

public class JSImage implements GenericImage {
  private JavaScriptObject mJsImage;

  public JSImage(JavaScriptObject jsImage) {
    mJsImage = jsImage;
  }

  public JavaScriptObject getJsImage() {
    return mJsImage;
  }

  @Override
  public native void scale(int width, int height)
  /*-{
    var jsImage = this.@com.actelion.research.gwt.js.api.generic.internal.JSImage::getJsImage()();
    jsImage.scale(width, height);
  }-*/;

  @Override
  public native Object get()
  /*-{
    var jsImage = this.@com.actelion.research.gwt.js.api.generic.internal.JSImage::getJsImage()();
    return jsImage;
  }-*/;

  @Override
  public native int getWidth()
  /*-{
    var jsImage = this.@com.actelion.research.gwt.js.api.generic.internal.JSImage::getJsImage()();
    return jsImage.getWidth();
  }-*/;

  @Override
  public native int getHeight()
  /*-{
    var jsImage = this.@com.actelion.research.gwt.js.api.generic.internal.JSImage::getJsImage()();
    return jsImage.getHeight();
  }-*/;

  @Override
  public native int getRGB(int x, int y)
  /*-{
    var jsImage = this.@com.actelion.research.gwt.js.api.generic.internal.JSImage::getJsImage()();
    return jsImage.getRGB(x, y);
  }-*/;

  @Override
  public native void setRGB(int x, int y, int argb)
  /*-{
    var jsImage = this.@com.actelion.research.gwt.js.api.generic.internal.JSImage::getJsImage()();
    jsImage.setRGB(x, y, argb);
  }-*/;
}

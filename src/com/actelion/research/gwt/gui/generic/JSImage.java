package com.actelion.research.gwt.gui.generic;

import com.actelion.research.gui.generic.GenericImage;
import com.google.gwt.core.client.JavaScriptObject;

public class JSImage implements GenericImage {
	private JavaScriptObject mJsImage;

  public JSImage(JavaScriptObject jsImage) {
		mJsImage = jsImage;
  }

	private JavaScriptObject getJsImage() {
		return mJsImage;
	}

  @Override
  public native void scale(int width, int height)
  /*-{
		var jsImage = this.@com.actelion.research.gwt.gui.generic.JSImage::getJsImage()();
		jsImage.scale(width, height);
  }-*/;

  @Override
  public native Object get()
  /*-{
		var jsImage = this.@com.actelion.research.gwt.gui.generic.JSImage::getJsImage()();
    return jsImage;
  }-*/;

  @Override
  public native int getWidth()
  /*-{
		var jsImage = this.@com.actelion.research.gwt.gui.generic.JSImage::getJsImage()();
		return jsImage.getWidth();
  }-*/;

  @Override
  public native int getHeight()
  /*-{
		var jsImage = this.@com.actelion.research.gwt.gui.generic.JSImage::getJsImage()();
    return jsImage.getHeight();
  }-*/;

  @Override
  public native int getRGB(int x, int y)
  /*-{
		var jsImage = this.@com.actelion.research.gwt.gui.generic.JSImage::getJsImage()();
		return jsImage.getRGB(x, y);
  }-*/;

  @Override
  public native void setRGB(int x, int y, int argb)
  /*-{
		var jsImage = this.@com.actelion.research.gwt.gui.generic.JSImage::getJsImage()();
		jsImage.setRGB(x, y, argb);
  }-*/;
}

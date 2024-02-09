package com.actelion.research.gwt.gui.generic;

import com.actelion.research.gui.generic.*;
import com.google.gwt.core.client.JavaScriptObject;

public class JSDrawContext implements GenericDrawContext {
	private JavaScriptObject mJsContext;

	public JSDrawContext(JavaScriptObject jsContext) {
		mJsContext = jsContext;
	}

	private JavaScriptObject getJsContext() {
		return mJsContext;
	}

  public native void clearRect(double x, double y, double w, double h)
  /*-{
    var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return ctx.clearRect(x, y, w, h);
  }-*/;

  @Override
  public native GenericImage createARGBImage(int width, int height)
  /*-{
    var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    var image = ctx.createARGBImage(width, height);
    return @com.actelion.research.gwt.gui.generic.JSImage::new(Lcom/google/gwt/core/client/JavaScriptObject;)(image);
  }-*/;

  @Override
  public native void drawCenteredString(double x, double y, String s)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.drawCenteredString(x, y, s);
  }-*/;

  @Override
  public native void drawCircle(double x, double y, double d)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.drawCircle(x, y, d);
  }-*/;

  @Override
  public native void drawDottedLine(double x1, double y1, double x2, double y2)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.drawDottedLine(x1, y1, x2, y2);
  }-*/;

  @Override
  public native void drawImage(GenericImage image, double x, double y)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    var jsImage = image.@com.actelion.research.gui.generic.GenericImage::get()();
		return ctx.drawImage(jsImage, x, y);
  }-*/;

  @Override
  public native void drawImage(GenericImage image, double sx, double sy, double dx, double dy, double w, double h)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    var jsImage = image.@com.actelion.research.gui.generic.GenericImage::get()();
		return ctx.drawImage(jsImage, sx, sy, dx, dy, w, h);
  }-*/;

  @Override
  public native void drawImage(GenericImage image, double sx, double sy, double sw, double sh, double dx, double dy, double dw, double dh)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    var jsImage = image.@com.actelion.research.gui.generic.GenericImage::get()();
		return ctx.drawImage(jsImage, sx, sy, sw, sh, dx, dy, dw, dh);
  }-*/;

  @Override
  public native void drawLine(double x1, double y1, double x2, double y2)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.drawLine(x1, y1, x2, y2);
  }-*/;

  @Override
  public void drawPolygon(GenericPolygon p) {
    drawPolygon(new JSPolygon(p));
  }

  public native void drawPolygon(JSPolygon jsPolygon)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.drawPolygon(jsPolygon);
  }-*/;

  @Override
  public native void drawRectangle(double x, double y, double w, double h)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.drawRectangle(x, y, w, h);
  }-*/;

  @Override
  public native void drawString(double x, double y, String s)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.drawString(x, y, s);
  }-*/;

  @Override
  public native void fillCircle(double x, double y, double d)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.fillCircle(x, y, d);
  }-*/;

  @Override
  public void fillPolygon(GenericPolygon p) {
    fillPolygon(new JSPolygon(p));
  }

  public native void fillPolygon(JSPolygon jsPolygon)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.fillPolygon(jsPolygon);
  }-*/;

  @Override
  public native void fillRectangle(double x, double y, double w, double h)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.fillRectangle(x, y, w, h);
  }-*/;

  @Override
  public native int getBackgroundRGB()
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.getBackgroundRGB();
  }-*/;

  @Override
  public GenericRectangle getBounds(String s) {
    JSRectangle jsRect = new JSRectangle();
    getBounds(s, jsRect);
    return jsRect.getRectangle();
  }

  public native GenericRectangle getBounds(String s, JSRectangle jsRect)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.getBounds(s, jsRect);
  }-*/;

  @Override
  public native int getFontSize()
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.getFontSize();
  }-*/;

  @Override
  public native int getForegroundRGB()
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.getForegroundRGB();
  }-*/;

  @Override
  public native float getLineWidth()
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.getLineWidth();
  }-*/;

  @Override
  public native int getRGB()
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.getRGB();
  }-*/;

  @Override
  public native int getSelectionBackgroundRGB()
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.getSelectionBackgroundRGB();
  }-*/;

  @Override
  public native boolean isDarkBackground()
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.isDarkBackground();
  }-*/;

  @Override
  public native void setFont(int size, boolean isBold, boolean isItalic)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.setFont(size, isBold, isItalic);
  }-*/;

  @Override
  public native void setLineWidth(float lineWidth)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.setLineWidth(lineWidth);
  }-*/;

  @Override
  public native void setRGB(int rgb)
  /*-{
		var ctx = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
		return ctx.setRGB(rgb);
  }-*/;
   
}

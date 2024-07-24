package com.actelion.research.gwt.gui.generic;

import com.actelion.research.gui.generic.GenericDrawContext;
import com.actelion.research.gui.generic.GenericImage;
import com.actelion.research.gui.generic.GenericPolygon;
import com.actelion.research.gui.generic.GenericRectangle;
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
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.clearRect(x, y, w, h);
  }-*/;

  @Override
  public native GenericImage createARGBImage(int width, int height)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    var image = ctx.createARGBImage(width, height);
    return @com.actelion.research.gwt.gui.generic.JSImage::new(Lcom/google/gwt/core/client/JavaScriptObject;)(image);
  }-*/;

  @Override
  public native void drawCenteredString(double x, double y, String s)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.drawCenteredString(x, y, s);
  }-*/;

  @Override
  public native void drawCircle(double x, double y, double d)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.drawCircle(x, y, d);
  }-*/;

  @Override
  public native void drawDottedLine(double x1, double y1, double x2, double y2)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.drawDottedLine(x1, y1, x2, y2);
  }-*/;

  @Override
  public native void drawImage(GenericImage image, double x, double y)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    var jsImage = image.@com.actelion.research.gui.generic.GenericImage::get()();
    return jsContext.drawImage(jsImage, x, y);
  }-*/;

  @Override
  public native void drawImage(GenericImage image, double sx, double sy, double dx, double dy, double w, double h)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    var jsImage = image.@com.actelion.research.gui.generic.GenericImage::get()();
    return jsContext.drawImage(jsImage, sx, sy, dx, dy, w, h);
  }-*/;

  @Override
  public native void drawImage(GenericImage image, double sx, double sy, double sw, double sh, double dx, double dy, double dw, double dh)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    var jsImage = image.@com.actelion.research.gui.generic.GenericImage::get()();
    return jsContext.drawImage(jsImage, sx, sy, sw, sh, dx, dy, dw, dh);
  }-*/;

  @Override
  public native void drawLine(double x1, double y1, double x2, double y2)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.drawLine(x1, y1, x2, y2);
  }-*/;

  @Override
  public void drawPolygon(GenericPolygon p) {
    drawPolygon(new JSPolygon(p));
  }

  public native void drawPolygon(JSPolygon jsPolygon)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.drawPolygon(jsPolygon);
  }-*/;

  @Override
  public native void drawRectangle(double x, double y, double w, double h)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.drawRectangle(x, y, w, h);
  }-*/;

  @Override
  public native void drawString(double x, double y, String s)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.drawString(x, y, s);
  }-*/;

  @Override
  public native void fillCircle(double x, double y, double d)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.fillCircle(x, y, d);
  }-*/;

  @Override
  public void fillPolygon(GenericPolygon p) {
    fillPolygon(new JSPolygon(p));
  }

  public native void fillPolygon(JSPolygon jsPolygon)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.fillPolygon(jsPolygon);
  }-*/;

  @Override
  public native void fillRectangle(double x, double y, double w, double h)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.fillRectangle(x, y, w, h);
  }-*/;

  @Override
  public native int getBackgroundRGB()
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.getBackgroundRGB();
  }-*/;

  @Override
  public GenericRectangle getBounds(String s) {
    JSRectangle jsRect = new JSRectangle();
    getBounds(s, jsRect);
    return jsRect.getRectangle();
  }

  public native GenericRectangle getBounds(String s, JSRectangle jsRect)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.getBounds(s, jsRect);
  }-*/;

  @Override
  public native int getFontSize()
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.getFontSize();
  }-*/;

  @Override
  public native int getForegroundRGB()
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.getForegroundRGB();
  }-*/;

  @Override
  public native float getLineWidth()
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.getLineWidth();
  }-*/;

  @Override
  public native int getRGB()
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.getRGB();
  }-*/;

  @Override
  public native int getSelectionBackgroundRGB()
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.getSelectionBackgroundRGB();
  }-*/;

  @Override
  public native boolean isDarkBackground()
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.isDarkBackground();
  }-*/;

  @Override
  public native void setFont(int size, boolean isBold, boolean isItalic)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.setFont(size, isBold, isItalic);
  }-*/;

  @Override
  public native void setLineWidth(float lineWidth)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.setLineWidth(lineWidth);
  }-*/;

  @Override
  public native void setRGB(int rgb)
  /*-{
    var jsContext = this.@com.actelion.research.gwt.gui.generic.JSDrawContext::getJsContext()();
    return jsContext.setRGB(rgb);
  }-*/;
   
}

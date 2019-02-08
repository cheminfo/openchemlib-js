package com.actelion.research.gwt.gui.editor;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;

public class Util {
  public static Canvas createScaledCanvas(int width, int height) {
    Canvas canvas = Canvas.createIfSupported();
    
    scaleCanvas(canvas, width, height);

    return canvas;
  }

  public static void scaleCanvas(Canvas canvas, int width, int height) {
    canvas.setWidth(width + "px");
    canvas.setHeight(height + "px");

    Context2d ctx = canvas.getContext2d();

    double ratio = Util.getDevicePixelRatio();
    canvas.setCoordinateSpaceWidth((int)(width * ratio));
    canvas.setCoordinateSpaceHeight((int)(height * ratio));
    ctx.scale(ratio, ratio);
  }

  private static native double getDevicePixelRatio()
  /*-{
  	return window.devicePixelRatio || 1;
  }-*/;
}

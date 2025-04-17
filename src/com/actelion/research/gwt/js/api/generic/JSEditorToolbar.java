package com.actelion.research.gwt.js.api.generic;

import jsinterop.annotations.*;
import com.google.gwt.core.client.JavaScriptObject;

import com.actelion.research.gui.editor.GenericEditorToolbar;
import com.actelion.research.gui.generic.*;
import com.actelion.research.gwt.js.api.generic.internal.*;

@JsType(name = "GenericEditorToolbar")
public class JSEditorToolbar implements GenericCanvas {
  private GenericEditorToolbar mGenericToolbar;
  private JavaScriptObject mJsObject;
  private JSMouseHandler mMouseHandler;
  private JSUIHelper mUIHelper;
  private boolean mDrawPending;

  public JSEditorToolbar(JSEditorArea jsEditorArea, JavaScriptObject jsObject, JSUIHelper uiHelper) {
    mGenericToolbar = new GenericEditorToolbar(this, jsEditorArea.getGenericEditorArea());
    mJsObject = jsObject;
    mUIHelper = uiHelper;

    mMouseHandler = new JSMouseHandler(mGenericToolbar);
    mMouseHandler.addListener(mGenericToolbar);

    setDimensions(mGenericToolbar.getWidth(), mGenericToolbar.getHeight());
    draw();
  }

  public int getWidth() {
    return mGenericToolbar.getWidth();
  }

  public int getHeight() {
    return mGenericToolbar.getHeight();
  }

  private void draw() {
    mDrawPending = false;
    JSDrawContext ctx = getDrawContext();
    ctx.clearRect(0, 0, getWidth(), getHeight());
    mGenericToolbar.paintContent(getDrawContext());
  }

  public void fireMouseEvent(int what, int button, int clickCount, int x, int y, boolean shiftDown, boolean ctrlDown, boolean altDown, boolean isPopupTrigger) {
    GenericMouseEvent gme = new GenericMouseEvent(what, button, clickCount, x, y, shiftDown, ctrlDown, altDown, isPopupTrigger, mGenericToolbar);
    mMouseHandler.fireEvent(gme);
  }

  private JavaScriptObject getJsObject() {
    return mJsObject;
  }
  
  public native void setDimensions(int width, int height)
  /*-{
    var jsObject = this.@com.actelion.research.gwt.js.api.generic.JSEditorToolbar::getJsObject()();
    return jsObject.setDimensions(width, height);
  }-*/;

  @Override
  @JsIgnore
  public native int getBackgroundRGB()
  /*-{
    var jsObject = this.@com.actelion.research.gwt.js.api.generic.JSEditorToolbar::getJsObject()();
    return jsObject.getBackgroundRGB();
  }-*/;

  @Override
  @JsIgnore
  public native double getCanvasHeight()
  /*-{
    var jsObject = this.@com.actelion.research.gwt.js.api.generic.JSEditorToolbar::getJsObject()();
    return jsObject.getCanvasHeight();
  }-*/;

  @Override
  @JsIgnore
  public native double getCanvasWidth()
  /*-{
    var jsObject = this.@com.actelion.research.gwt.js.api.generic.JSEditorToolbar::getJsObject()();
    return jsObject.getCanvasWidth();
  }-*/;

  @Override
  @JsIgnore
  public JSDrawContext getDrawContext() {
    return new JSDrawContext(getDrawContextFromJsObject());
  }

  private native JavaScriptObject getDrawContextFromJsObject()
  /*-{
    var jsObject = this.@com.actelion.research.gwt.js.api.generic.JSEditorToolbar::getJsObject()();
    return jsObject.getDrawContext();
  }-*/;

  @Override
  @JsIgnore
  public void repaint() {
    if (!mDrawPending) {
      mDrawPending = true;
      mUIHelper.runLater(() -> draw());
    }
  }
}

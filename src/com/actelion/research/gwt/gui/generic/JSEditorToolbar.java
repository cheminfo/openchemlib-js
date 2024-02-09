package com.actelion.research.gwt.gui.generic;

import com.actelion.research.gui.editor.GenericEditorArea;
import com.actelion.research.gui.editor.GenericEditorToolbar;
import com.actelion.research.gui.generic.*;
import com.actelion.research.gwt.minimal.JSMolecule;
import com.google.gwt.core.client.JavaScriptObject;

import jsinterop.annotations.*;

@JsType(name = "EditorToolbar")
public class JSEditorToolbar implements GenericCanvas {
	private GenericEditorToolbar mGenericToolbar;
	private JavaScriptObject mOptions;
	private JSMouseHandler mMouseHandler;

	public JSEditorToolbar(JSEditorArea jsEditorArea, JavaScriptObject options) {
		mGenericToolbar = new GenericEditorToolbar(this, jsEditorArea.getGenericEditorArea());
		mOptions = options;

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

	public void draw() {
		JSDrawContext ctx = getDrawContext();
		ctx.clearRect(0, 0, getWidth(), getHeight());
		mGenericToolbar.paintContent(getDrawContext());
	}

	public void fireMouseEvent(int what, int button, int clickCount, int x, int y, boolean shiftDown, boolean ctrlDown, boolean altDown, boolean isPopupTrigger) {
		GenericMouseEvent gme = new GenericMouseEvent(what, button, clickCount, x, y, shiftDown, ctrlDown, altDown, isPopupTrigger, mGenericToolbar);
		mMouseHandler.fireEvent(gme);
	}

	private JavaScriptObject getOptions() {
		return mOptions;
	}
	
	public native void setDimensions(int width, int height)
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSEditorToolbar::getOptions()();
		return options.setDimensions(width, height);
  }-*/;

	@Override
	@JsIgnore
	public native int getBackgroundRGB()
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSEditorToolbar::getOptions()();
		return options.getBackgroundRGB();
  }-*/;

	@Override
	@JsIgnore
	public native double getCanvasHeight()
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSEditorToolbar::getOptions()();
		return options.getCanvasHeight();
  }-*/;

	@Override
	@JsIgnore
	public native double getCanvasWidth()
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSEditorToolbar::getOptions()();
		return options.getCanvasWidth();
  }-*/;

	@Override
	@JsIgnore
	public JSDrawContext getDrawContext() {
		return new JSDrawContext(getDrawContextFromOptions());
	}

	private native JavaScriptObject getDrawContextFromOptions()
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSEditorToolbar::getOptions()();
		return options.getDrawContext();
  }-*/;

	@Override
	@JsIgnore
	public void repaint() {
		draw();
	}
}

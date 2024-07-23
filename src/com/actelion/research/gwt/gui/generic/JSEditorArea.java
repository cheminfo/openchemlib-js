package com.actelion.research.gwt.gui.generic;

import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.gui.editor.EditorEvent;
import com.actelion.research.gui.editor.GenericEditorArea;
import com.actelion.research.gui.generic.*;
import com.actelion.research.gwt.minimal.JSMolecule;
import com.actelion.research.gwt.minimal.JSReaction;
import com.google.gwt.core.client.JavaScriptObject;
import info.clearthought.layout.TableLayout;
import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;

@JsType(name = "GenericEditorArea")
public class JSEditorArea implements GenericCanvas {
	private GenericEditorArea mDrawArea;
	private JavaScriptObject mOptions;
	private JSMouseHandler mMouseHandler;
	private JSKeyHandler mKeyHandler;

	public static final int EDITOR_EVENT_MOLECULE_CHANGED = EditorEvent.WHAT_MOLECULE_CHANGED;
	public static final int EDITOR_EVENT_SELECTION_CHANGED = EditorEvent.WHAT_SELECTION_CHANGED;
	public static final int EDITOR_EVENT_HIGHLIGHT_ATOM_CHANGED = EditorEvent.WHAT_HILITE_ATOM_CHANGED;
	public static final int EDITOR_EVENT_HIGHLIGHT_BOND_CHANGED = EditorEvent.WHAT_HILITE_BOND_CHANGED;

	public static final int MOUSE_EVENT_PRESSED = GenericMouseEvent.MOUSE_PRESSED;
	public static final int MOUSE_EVENT_RELEASED = GenericMouseEvent.MOUSE_RELEASED;
	public static final int MOUSE_EVENT_CLICKED = GenericMouseEvent.MOUSE_CLICKED;
	public static final int MOUSE_EVENT_ENTERED = GenericMouseEvent.MOUSE_ENTERED;
	public static final int MOUSE_EVENT_EXITED = GenericMouseEvent.MOUSE_EXITED;
	public static final int MOUSE_EVENT_MOVED = GenericMouseEvent.MOUSE_MOVED;
	public static final int MOUSE_EVENT_DRAGGED = GenericMouseEvent.MOUSE_DRAGGED;

	public static final int KEY_CTRL = GenericKeyEvent.KEY_CTRL;
	public static final int KEY_ALT = GenericKeyEvent.KEY_ALT;
	public static final int KEY_SHIFT = GenericKeyEvent.KEY_SHIFT;
	public static final int KEY_DELETE = GenericKeyEvent.KEY_DELETE;
	public static final int KEY_BACK_SPACE = GenericKeyEvent.KEY_BACK_SPACE;
	public static final int KEY_HELP = GenericKeyEvent.KEY_HELP;
	public static final int KEY_ESCAPE = GenericKeyEvent.KEY_ESCAPE;

	public static final int KEY_EVENT_PRESSED = GenericKeyEvent.KEY_PRESSED;
	public static final int KEY_EVENT_RELEASED = GenericKeyEvent.KEY_RELEASED;

	public static final int cChainCursor = GenericCursorHelper.cChainCursor;
	public static final int cDeleteCursor = GenericCursorHelper.cDeleteCursor;
	public static final int cHandCursor = GenericCursorHelper.cHandCursor;
	public static final int cHandPlusCursor = GenericCursorHelper.cHandPlusCursor;
	public static final int cFistCursor = GenericCursorHelper.cFistCursor;
	public static final int cLassoCursor = GenericCursorHelper.cLassoCursor;
	public static final int cLassoPlusCursor = GenericCursorHelper.cLassoPlusCursor;
	public static final int cSelectRectCursor = GenericCursorHelper.cSelectRectCursor;
	public static final int cSelectRectPlusCursor = GenericCursorHelper.cSelectRectPlusCursor;
	public static final int cZoomCursor = GenericCursorHelper.cZoomCursor;
	public static final int cInvisibleCursor = GenericCursorHelper.cInvisibleCursor;
	public static final int cPointerCursor = GenericCursorHelper.cPointerCursor;
	public static final int cTextCursor = GenericCursorHelper.cTextCursor;
	public static final int cPointedHandCursor = GenericCursorHelper.cPointedHandCursor;
	public static final int[][] IMAGE_DATA_16 = GenericCursorHelper.IMAGE_DATA_16;
	public static final int[] HOTSPOT_16 = GenericCursorHelper.HOTSPOT_16;
	public static final int[] HOTSPOT_32 = GenericCursorHelper.HOTSPOT_32;

	public static final int TableLayoutPreferred = (int)TableLayout.PREFERRED;
	public static final int TableLayoutFill = (int)TableLayout.FILL;

	public JSEditorArea(JavaScriptObject options, JSUIHelper uiHelper) {
		mDrawArea = new GenericEditorArea(new StereoMolecule(), 0, uiHelper, this);
		mDrawArea.addDrawAreaListener(new GenericEventListener<EditorEvent>() {
			@Override
			public void eventHappened(EditorEvent e) {
				callJsEventListener(e.getWhat(), e.isUserChange());
			}
		});
		mOptions = options;

		mMouseHandler = new JSMouseHandler(mDrawArea);
		mMouseHandler.addListener(mDrawArea);

		mKeyHandler = new JSKeyHandler(mDrawArea);
		mKeyHandler.addListener(mDrawArea);

		JavaScriptObject clipboardHandler = getClipboardHandler();
	  mDrawArea.setClipboardHandler(new JSClipboardHandler(clipboardHandler));
	}

	public void setMolecule(JSMolecule molecule) {
		mDrawArea.setMolecule(molecule.getStereoMolecule());
	}

	public JSMolecule getMolecule() {
		return new JSMolecule(mDrawArea.getMolecule());
	}

	public void setReaction(JSReaction reaction) {
		mDrawArea.setReaction(reaction.getReaction());
	}

	public JSReaction getReaction() {
		return new JSReaction(mDrawArea.getReaction());
	}
 
	private native JavaScriptObject getClipboardHandler()
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSEditorArea::getOptions()();
		return options.getClipboardHandler();
  }-*/;

  private native JavaScriptObject callJsEventListener(int what, boolean isUserChange)
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSEditorArea::getOptions()();
		return options.onChange(what, isUserChange);
  }-*/;

	public void draw() {
		mDrawArea.paintContent(getDrawContext());
	}

	public GenericEditorArea getGenericEditorArea() {
		return mDrawArea;
	}

	public void fireMouseEvent(int what, int button, int clickCount, int x, int y, boolean shiftDown, boolean ctrlDown, boolean altDown, boolean isPopupTrigger) {
		GenericMouseEvent gme = new GenericMouseEvent(what, button, clickCount, x, y, shiftDown, ctrlDown, altDown, isPopupTrigger, mDrawArea);
		mMouseHandler.fireEvent(gme);
	}

	public void fireKeyEvent(int what, int key, boolean isAltDown, boolean isCtrlDown, boolean isShiftDown, boolean isMenuShortcut) {
		GenericKeyEvent gke = new GenericKeyEvent(what, key, isAltDown, isCtrlDown, isShiftDown, isMenuShortcut, mDrawArea);
		mKeyHandler.fireEvent(gke);
	}

	public void toolChanged(int newTool) {
		mDrawArea.toolChanged(newTool);
	}

	private JavaScriptObject getOptions() {
		return mOptions;
	}

	@Override
	@JsIgnore
	public native int getBackgroundRGB()
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSEditorArea::getOptions()();
		return options.getBackgroundRGB();
  }-*/;

	@Override
	@JsIgnore
	public native double getCanvasHeight()
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSEditorArea::getOptions()();
		return options.getCanvasHeight();
  }-*/;

	@Override
	@JsIgnore
	public native double getCanvasWidth()
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSEditorArea::getOptions()();
		return options.getCanvasWidth();
  }-*/;

	@Override
	@JsIgnore
	public GenericDrawContext getDrawContext() {
		return new JSDrawContext(getDrawContextFromOptions());
	}

	private native JavaScriptObject getDrawContextFromOptions()
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSEditorArea::getOptions()();
		return options.getDrawContext();
  }-*/;

	@Override
	public void repaint() {
		draw();
	}
}

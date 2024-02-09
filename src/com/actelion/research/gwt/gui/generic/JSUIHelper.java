package com.actelion.research.gwt.gui.generic;

import java.io.File;

import com.actelion.research.gui.generic.*;
import com.google.gwt.core.client.JavaScriptObject;

public class JSUIHelper implements GenericUIHelper {
	private JavaScriptObject mOptions;

  public JSUIHelper(JavaScriptObject options) {
		mOptions = options;
  }

	private JavaScriptObject getOptions() {
		return mOptions;
	}

  @Override
  public GenericDialog createDialog(String title, GenericEventListener<GenericActionEvent> consumer) {
    JSDialog dialog = new JSDialog(createNativeDialog(title));
    dialog.setEventConsumer(consumer);
    return dialog;
  }
  
  public native JavaScriptObject createNativeDialog(String title)
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSUIHelper::getOptions()();
		return options.createDialog(title);
  }-*/;

  @Override
  public GenericImage createImage(String name) {
    if (name.equals("editorButtons.png")) {
      String data = ImageData.editorButtonsData0 + ImageData.editorButtonsData1 + ImageData.editorButtonsData2 + ImageData.editorButtonsData3 + ImageData.editorButtonsData4 + ImageData.editorButtonsData5;
      return new JSImage(createImageFromBase64(ImageData.editorButtonsWidth, ImageData.editorButtonsHeight, data));
    } else if (name.equals("esrButtons.png")) {
      return new JSImage(createImageFromBase64(ImageData.esrButtonsWidth, ImageData.esrButtonsHeight, ImageData.esrButtonsData0));
    } else {
      return null;
    }
  }

  private native JavaScriptObject createImageFromBase64(int width, int height, String base64)
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSUIHelper::getOptions()();
    return options.createImageFromBase64(width, height, base64);
  }-*/;

  @Override
  public native GenericImage createImage(int width, int height)
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSUIHelper::getOptions()();
    var image = options.createImage(width, height);
    return @com.actelion.research.gwt.gui.generic.JSImage::new(Lcom/google/gwt/core/client/JavaScriptObject;)(image);
  }-*/;

  @Override
  public GenericPopupMenu createPopupMenu(GenericEventListener<GenericActionEvent> consumer) {
    // TODO: implement popup menu.
    return null;
  }

  @Override
  public native void grabFocus()
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSUIHelper::getOptions()();
		return options.grabFocus();
  }-*/;

  @Override
  public File openChemistryFile(boolean isReaction) {
    return null;
  }

  @Override
  public native void runLater(Runnable r)
  /*-{
    var that = this;
    $wnd.requestAnimationFrame(function(){
      that.@com.actelion.research.gwt.gui.generic.JSUIHelper::runNow(Ljava/lang/Runnable;)(r);
    });
  }-*/;

  private void runNow(Runnable r) {
    r.run();
  }

  @Override
  public native void setCursor(int cursor)
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSUIHelper::getOptions()();
		return options.setCursor(cursor);
  }-*/;

  @Override
  public native void showHelpDialog(String url, String title)
  /*-{
		var options = this.@com.actelion.research.gwt.gui.generic.JSUIHelper::getOptions()();
		return options.showHelpDialog(url, title);
  }-*/;

  @Override
  public void showMessage(String message) {}
  
}

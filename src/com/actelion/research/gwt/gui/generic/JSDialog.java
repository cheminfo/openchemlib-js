package com.actelion.research.gwt.gui.generic;

import com.actelion.research.gui.generic.*;
import com.google.gwt.core.client.JavaScriptObject;

public class JSDialog implements GenericDialog {
  private JavaScriptObject mJsDialog;
  private GenericEventListener<GenericActionEvent> mConsumer;
  private JSEditorArea mEditorArea;

  public JSDialog(JavaScriptObject jsDialog, JSEditorArea editorArea) {
    mJsDialog = jsDialog;
    mEditorArea = editorArea;
  }

  private JavaScriptObject getJsDialog() {
    return mJsDialog;
  }

  @Override
  public native void setLayout(int[] hLayout, int[] vLayout)
  /*-{
    var dialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    return dialog.setLayout(hLayout, vLayout);
  }-*/;
  
  @Override
  public native void add(GenericComponent c, int x, int y)
  /*-{
    var dialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    return dialog.add(c, x, y);
  }-*/;

  @Override
  public native void add(GenericComponent c, int x1, int y1, int x2, int y2)
  /*-{
    var dialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    return dialog.add(c, x1, y1, x2, y2);
  }-*/;

  @Override
  public native GenericCheckBox createCheckBox(String text)
  /*-{
    var dialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    var checkBox = dialog.createCheckBox(text);
    return @com.actelion.research.gwt.gui.generic.JSCheckBox::new(Lcom/google/gwt/core/client/JavaScriptObject;)(checkBox);
  }-*/;

  @Override
  public native GenericComboBox createComboBox()
  /*-{
    var dialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    var comboBox = dialog.createComboBox();
    return @com.actelion.research.gwt.gui.generic.JSComboBox::new(Lcom/google/gwt/core/client/JavaScriptObject;)(comboBox);
  }-*/;

  @Override
	public native GenericLabel createLabel(String text)
  /*-{
    var dialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    var label = dialog.createLabel(text);
    return @com.actelion.research.gwt.gui.generic.JSLabel::new(Lcom/google/gwt/core/client/JavaScriptObject;)(label);
  }-*/;

  @Override
	public native GenericTextField createTextField(int width, int height)
  /*-{
    var dialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    var textField = dialog.createTextField(width, height);
    return @com.actelion.research.gwt.gui.generic.JSTextField::new(Lcom/google/gwt/core/client/JavaScriptObject;)(textField);
  }-*/;

  @Override
  public native void setEventConsumer(GenericEventListener<GenericActionEvent> consumer)
  /*-{
    this.@com.actelion.research.gwt.gui.generic.JSDialog::mConsumer = consumer;
    var dialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    var that = this;
    var jsConsumer = {
      fireOk: function ok() {
        that.@com.actelion.research.gwt.gui.generic.JSDialog::fireOk()();
      },
      fireCancel: function cancel() {
        that.@com.actelion.research.gwt.gui.generic.JSDialog::fireCancel()();
      }
    };
    dialog.setEventConsumer(jsConsumer);
  }-*/;

  @Override
  public native void showDialog()
  /*-{
    var dialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    dialog.showDialog();
  }-*/;

  @Override
  public native void disposeDialog()
  /*-{
    var dialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    dialog.disposeDialog();
  }-*/;

  @Override
  public native void showMessage(String message)
  /*-{
    var dialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    dialog.showMessage(message);
  }-*/;

  private void fireOk() {
    if (mConsumer != null) {
      mConsumer.eventHappened(new GenericActionEvent(this, GenericActionEvent.WHAT_OK, 0));
    }
    mEditorArea.repaint();
  }

  private void fireCancel() {
    if (mConsumer != null) {
      mConsumer.eventHappened(new GenericActionEvent(this, GenericActionEvent.WHAT_CANCEL, 0));
    }
  }
}

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
    var jsDialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    return jsDialog.setLayout(hLayout, vLayout);
  }-*/;
  
  @Override
  public native void add(GenericComponent c, int x, int y)
  /*-{
    var jsDialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    return jsDialog.add(c, x, y);
  }-*/;

  @Override
  public native void add(GenericComponent c, int x1, int y1, int x2, int y2)
  /*-{
    var jsDialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    return jsDialog.add(c, x1, y1, x2, y2);
  }-*/;

  @Override
  public native GenericCheckBox createCheckBox(String text)
  /*-{
    var jsDialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    var checkBox = jsDialog.createCheckBox(text);
    return @com.actelion.research.gwt.gui.generic.JSCheckBox::new(Lcom/google/gwt/core/client/JavaScriptObject;)(checkBox);
  }-*/;

  @Override
  public native GenericComboBox createComboBox()
  /*-{
    var jsDialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    var comboBox = jsDialog.createComboBox();
    return @com.actelion.research.gwt.gui.generic.JSComboBox::new(Lcom/google/gwt/core/client/JavaScriptObject;)(comboBox);
  }-*/;

  @Override
  public native GenericLabel createLabel(String text)
  /*-{
    var jsDialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    var label = jsDialog.createLabel(text);
    return @com.actelion.research.gwt.gui.generic.JSLabel::new(Lcom/google/gwt/core/client/JavaScriptObject;)(label);
  }-*/;

  @Override
  public native GenericTextField createTextField(int width, int height)
  /*-{
    var jsDialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    var textField = jsDialog.createTextField(width, height);
    return @com.actelion.research.gwt.gui.generic.JSTextField::new(Lcom/google/gwt/core/client/JavaScriptObject;)(textField);
  }-*/;

  @Override
  public native void setEventConsumer(GenericEventListener<GenericActionEvent> consumer)
  /*-{
    this.@com.actelion.research.gwt.gui.generic.JSDialog::mConsumer = consumer;
    var jsDialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    var that = this;
    var jsConsumer = {
      fireOk: function ok() {
        that.@com.actelion.research.gwt.gui.generic.JSDialog::fireOk()();
      },
      fireCancel: function cancel() {
        that.@com.actelion.research.gwt.gui.generic.JSDialog::fireCancel()();
      }
    };
    jsDialog.setEventConsumer(jsConsumer);
  }-*/;

  @Override
  public native void showDialog()
  /*-{
    var jsDialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    jsDialog.showDialog();
  }-*/;

  @Override
  public native void disposeDialog()
  /*-{
    var jsDialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    jsDialog.disposeDialog();
  }-*/;

  @Override
  public native void showMessage(String message)
  /*-{
    var jsDialog = this.@com.actelion.research.gwt.gui.generic.JSDialog::getJsDialog()();
    jsDialog.showMessage(message);
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

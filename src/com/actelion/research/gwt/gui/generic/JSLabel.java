package com.actelion.research.gwt.gui.generic;

import com.actelion.research.gui.generic.GenericLabel;
import com.google.gwt.core.client.JavaScriptObject;

public class JSLabel extends JSComponent implements GenericLabel {
  public JSLabel(JavaScriptObject jsLabel) {
    super(jsLabel);
  }

  @Override
  public native void setText(String text)
  /*-{
    var component = this.@com.actelion.research.gwt.gui.generic.JSLabel::getJsComponent()();
    return component.setText(text);
  }-*/;
}

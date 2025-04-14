package com.actelion.research.gwt.js.api.generic.internal;

import com.google.gwt.core.client.JavaScriptObject;

import com.actelion.research.gui.generic.*;

public class JSLabel extends JSComponent implements GenericLabel {
  public JSLabel(JavaScriptObject jsLabel) {
    super(jsLabel);
  }

  @Override
  public native void setText(String text)
  /*-{
    var component = this.@com.actelion.research.gwt.js.api.generic.internal.JSLabel::getJsComponent()();
    return component.setText(text);
  }-*/;
}

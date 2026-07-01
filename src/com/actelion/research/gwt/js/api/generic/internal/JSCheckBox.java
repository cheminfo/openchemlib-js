package com.actelion.research.gwt.js.api.generic.internal;

import com.actelion.research.gui.generic.*;
import com.google.gwt.core.client.JavaScriptObject;

public class JSCheckBox extends JSComponent implements GenericCheckBox {

  public JSCheckBox(JavaScriptObject jsCheckBox) {
    super(jsCheckBox);
  }

  @Override
  public native boolean isSelected() /*-{
    var component = this.@com.actelion.research.gwt.js.api.generic.internal.JSCheckBox::getJsComponent()();
    return component.isSelected();
  }-*/;

  @Override
  public native void setSelected(
    boolean b
  ) /*-{
    var component = this.@com.actelion.research.gwt.js.api.generic.internal.JSCheckBox::getJsComponent()();
    return component.setSelected(b);
  }-*/;
}

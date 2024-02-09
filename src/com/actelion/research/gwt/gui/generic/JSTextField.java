package com.actelion.research.gwt.gui.generic;

import com.actelion.research.gui.generic.*;
import com.google.gwt.core.client.JavaScriptObject;

public class JSTextField extends JSComponent implements GenericTextField {
  public JSTextField(JavaScriptObject jsTextField) {
    super(jsTextField);
  }

  @Override
  public native String getText()
  /*-{
    var component = this.@com.actelion.research.gwt.gui.generic.JSTextField::getJsComponent()();
    return component.getText();
  }-*/;

  @Override
	public native void setText(String text)
  /*-{
    var component = this.@com.actelion.research.gwt.gui.generic.JSTextField::getJsComponent()();
    return component.setText(text);
  }-*/;
}

package com.actelion.research.gwt.gui.generic;

import com.actelion.research.gui.generic.GenericComboBox;
import com.google.gwt.core.client.JavaScriptObject;

public class JSComboBox extends JSComponent implements GenericComboBox {
  public JSComboBox(JavaScriptObject jsComboBox) {
    super(jsComboBox);
  }

  @Override
  public native void addItem(String item)
  /*-{
    var component = this.@com.actelion.research.gwt.gui.generic.JSComboBox::getJsComponent()();
    return component.addItem(item);
  }-*/;

  @Override
  public native void removeAllItems()
  /*-{
    var component = this.@com.actelion.research.gwt.gui.generic.JSComboBox::getJsComponent()();
    return component.removeAllItems();
  }-*/;

  @Override
  public native int getSelectedIndex()
  /*-{
    var component = this.@com.actelion.research.gwt.gui.generic.JSComboBox::getJsComponent()();
    return component.getSelectedIndex();
  }-*/;

  @Override
  public native String getSelectedItem()
  /*-{
    var component = this.@com.actelion.research.gwt.gui.generic.JSComboBox::getJsComponent()();
    return component.getSelectedItem();
  }-*/;

  @Override
  public native void setSelectedIndex(int index)
  /*-{
    var component = this.@com.actelion.research.gwt.gui.generic.JSComboBox::getJsComponent()();
    return component.setSelectedIndex(index);
  }-*/;

  @Override
  public native void setSelectedItem(String item)
  /*-{
    var component = this.@com.actelion.research.gwt.gui.generic.JSComboBox::getJsComponent()();
    return component.setSelectedItem(item);
  }-*/;

  @Override
  public native void setEditable(boolean b)
  /*-{
    var component = this.@com.actelion.research.gwt.gui.generic.JSComboBox::getJsComponent()();
    return component.setEditable(b);
  }-*/;
}

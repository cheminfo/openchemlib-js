package org.cheminfo.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class PlainJSObject extends JavaScriptObject {
  protected PlainJSObject() {}

  public static PlainJSObject create() {
    return (PlainJSObject) JavaScriptObject.createObject().cast();
  }

  public native final String getPropertyString(String key)/*-{
    var value = this[key];
    return typeof value === 'string' ? value : null;
  }-*/;

  public native final void setPropertyString(String key, String value)/*-{
    this[key] = value;
  }-*/;

  public native final void setPropertyInt(String key, int value)/*-{
    this[key] = value;
  }-*/;

  public native final void setPropertyBoolean(String key, boolean value)/*-{
    this[key] = value;
  }-*/;
}

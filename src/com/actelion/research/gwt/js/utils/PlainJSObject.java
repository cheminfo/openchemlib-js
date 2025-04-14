package com.actelion.research.gwt.js.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class PlainJSObject extends JavaScriptObject {
  protected PlainJSObject() {}

  public static PlainJSObject create() {
    return (PlainJSObject) JavaScriptObject.createObject().cast();
  }

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

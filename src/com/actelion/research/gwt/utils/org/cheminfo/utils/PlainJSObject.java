package org.cheminfo.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class PlainJSObject extends JavaScriptObject {

  protected PlainJSObject() {}

  public static PlainJSObject create() {
    return (PlainJSObject) JavaScriptObject.createObject().cast();
  }

  public final native JavaScriptObject getProperty(
    String key
  ) /*-{
    var value = this[key];
    return typeof value === 'object' ? value : null;
  }-*/;

  public final native JavaScriptObject setProperty(
    String key,
    JavaScriptObject value
  ) /*-{
    this[key] = value;
  }-*/;

  public final native void setPropertyString(
    String key,
    String value
  ) /*-{
    this[key] = value;
  }-*/;

  public final native void setPropertyInt(
    String key,
    int value
  ) /*-{
    this[key] = value;
  }-*/;

  public final native void setPropertyBoolean(
    String key,
    boolean value
  ) /*-{
    this[key] = value;
  }-*/;
}

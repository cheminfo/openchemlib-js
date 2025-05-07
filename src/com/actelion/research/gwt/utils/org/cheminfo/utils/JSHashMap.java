package org.cheminfo.utils;

import com.google.gwt.core.client.JavaScriptObject;
import java.util.Enumeration;

public class JSHashMap {
  private JavaScriptObject data;

  public JSHashMap() {
    init();
  }

  public native void init() /*-{
    this.@org.cheminfo.utils.JSHashMap::data = new Map();
  }-*/;

  public native boolean containsKey(String key) /*-{
    var data = this.@org.cheminfo.utils.JSHashMap::data;
    return data.has(key);
  }-*/;

  public native String get(String key) /*-{
    var data = this.@org.cheminfo.utils.JSHashMap::data;
    var hasKey = data.has(key);
    return hasKey ? data.get(key) : null;
  }-*/;

  public native String put(String key, String value) /*-{
    var data = this.@org.cheminfo.utils.JSHashMap::data;
    var hasKey = data.has(key);
    var previous = hasKey ? data.get(key) : null;
    data.set(key, value);
    return previous;
  }-*/;

  public Enumeration elements() {
    JSException.throwUnimplemented();
    return null;
  };
}

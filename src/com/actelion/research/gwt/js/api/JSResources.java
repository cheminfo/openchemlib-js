package com.actelion.research.gwt.js.api;

import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

import org.cheminfo.utils.FakeFileInputStream;

@JsType(name = "Resources")
public class JSResources {
  private static boolean hasRegistered = false;

  public static void _register(JavaScriptObject data) {
    FakeFileInputStream.registerResources(data);
    hasRegistered = true;
  }

  @JsIgnore
  public static void checkHasRegistered() {
    if (!hasRegistered) {
      throwUnregistered();
    }
  }

  private static native void throwUnregistered()
  /*-{
    throw new Error('static resources must be registered first');
  }-*/;
}

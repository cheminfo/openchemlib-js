package org.cheminfo.utils;

public class JSException {
  public static native void throwError(String message) /*-{
    throw new Error(message);
  }-*/;

  public static void throwUnimplemented() {
    throwError("unimplemented");
  }
}

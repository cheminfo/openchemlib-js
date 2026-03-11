package org.cheminfo.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class JSRegexMatchResult {
  private JavaScriptObject jsResult;

  public JSRegexMatchResult(JavaScriptObject jsResult) {
    this.jsResult = jsResult;
  }

  public String group(int group) {
    return groupNative(jsResult, group);
  }

  public native String groupNative(JavaScriptObject result, int group)
  /*-{
    return result[group] || null;
  }-*/;
}

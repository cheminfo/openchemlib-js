package org.cheminfo.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class JSRegexMatcher {
  private JavaScriptObject regex;
  private String input;

  public JSRegexMatcher(JavaScriptObject regex, String input) {
    this.regex = regex;
    this.input = input;
  }

  public JSRegexMatchResult find() {
    JavaScriptObject result = findNative(regex, input);
    if (result == null) {
      return null;
    } else {
      return new JSRegexMatchResult(result);
    }
  }

  public native JavaScriptObject findNative(JavaScriptObject regex, String input)
  /*-{
    return regex.exec(input);
  }-*/;
}

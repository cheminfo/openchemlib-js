package org.cheminfo.utils;

import com.google.gwt.core.client.JavaScriptObject;

public class JSRegexPattern {
  private JavaScriptObject regex;

  private JSRegexPattern(JavaScriptObject regex) {
    this.regex = regex;
  }

  public static JSRegexPattern compile(String pattern)  {
    JavaScriptObject regex = compileNative(pattern);
    if (regex == null) {
      return null;
    } else {
      return new JSRegexPattern(regex);
    }
  }

  private native static JavaScriptObject compileNative(String pattern)
  /*-{
    try {
      return new RegExp(pattern);
    } catch (err) {
      return null;
    }
  }-*/;

  public JSRegexMatcher getMatcher(String input) {
    return new JSRegexMatcher(this.regex, input);
  }
}

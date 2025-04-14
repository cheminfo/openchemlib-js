package com.actelion.research.gwt.js.utils;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import com.actelion.research.chem.prediction.ParameterizedStringList;

public class Util2 {
  public static JavaScriptObject convertParameterizedStringList(ParameterizedStringList list) {
    int size = list.getSize();
    JsArray<JavaScriptObject> array = newJsArray(size);
    for (int i = 0; i < size; i++) {
      array.set(i, getParameterizedString(list.getStringAt(i), list.getStringTypeAt(i)));
    }
    return array;
  }

  public static native JsArray<JavaScriptObject> newJsArray(int length)
  /*-{
  	return new Array(length);
  }-*/;

  private static native JavaScriptObject getParameterizedString(String string, int stringType)
  /*-{
  	return {
  		type: stringType,
  		value: string
  	};
  }-*/;
}

package com.actelion.research.gwt.core;

import com.actelion.research.chem.prediction.ParameterizedStringList;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Util {
	public static JavaScriptObject convertParameterizedStringList(ParameterizedStringList list) {
		int size = list.getSize();
		JsArray<JavaScriptObject> array = newJsArray(size);
		for (int i = 0; i < size; i++) {
			array.set(i, getParameterizedString(list.getStringAt(i), list.getStringTypeAt(i)));
		}
		return array;
	}

	public static native JsArray<JavaScriptObject> newJsArray(int length) /*-{
		return new Array(length);
	}-*/;

	private static native JavaScriptObject getParameterizedString(String string, int stringType) /*-{
		return {
			type: stringType,
			value: string
		};
	}-*/;
}

package com.actelion.research.gwt.core;

import com.actelion.research.chem.contrib.HoseCodeCreator;
import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

@JsType(name = "Util")
public class JSUtil {
	public static native String[] getHoseCodesFromDiastereotopicID(String diastereotopicID, JavaScriptObject options) /*-{
		options = options || {};
		var maxSphereSize = (typeof options.maxSphereSize === 'undefined' ? 5 : options.maxSphereSize) | 0;
		var type = (typeof options.type === 'undefined' ? 0 : options.type) | 0;
		return @com.actelion.research.chem.contrib.HoseCodeCreator::getHoseCodesFromDiaID(Ljava/lang/String;II)(diastereotopicID, maxSphereSize, type);
	}-*/;
}

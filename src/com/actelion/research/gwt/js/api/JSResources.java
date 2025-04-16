package com.actelion.research.gwt.js.api;

import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

import org.cheminfo.utils.FakeFileInputStream;

@JsType(name = "Resources")
public class JSResources {
  public static void registerResource(String path, JavaScriptObject contents) {
    FakeFileInputStream.registerResource(path, contents);
  }
}

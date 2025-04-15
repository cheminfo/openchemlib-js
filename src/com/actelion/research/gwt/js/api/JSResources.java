package com.actelion.research.gwt.js.api;

import jsinterop.annotations.*;

import org.cheminfo.utils.FakeFileInputStream;

@JsType(name = "Resources")
public class JSResources {
  public static void registerResource(String path, String contents) {
    FakeFileInputStream.registerResource(path, contents);
  }
}

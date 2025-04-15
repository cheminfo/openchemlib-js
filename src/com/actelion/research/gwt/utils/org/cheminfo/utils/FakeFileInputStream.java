package org.cheminfo.utils;

import java.io.*;

public class FakeFileInputStream extends InputStream {
  private static PlainJSObject registeredResources = PlainJSObject.create();

  public static FakeFileInputStream getResourceAsStream(String path) throws IOException {
    if (path.contains("/csd/")) {
      return null;
    }
    String contents = registeredResources.getPropertyString(path);
    if (contents == null) {
      throwError(path);
    }
    return new FakeFileInputStream(contents);
  }

  private static native void throwError(String path)
  /*-{
    throw new Error('Missing resource (forgot to call Resources.registerResource?): ' + path);
  }-*/;

  public static void registerResource(String path, String contents) {
    registeredResources.setPropertyString(path, contents);
  }

  private String mContents;

  public FakeFileInputStream(String contents) throws IOException {
    mContents = contents;
  }
}

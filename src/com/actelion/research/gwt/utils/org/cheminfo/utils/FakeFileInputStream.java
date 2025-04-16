package org.cheminfo.utils;

import com.google.gwt.core.client.JavaScriptObject;
import java.io.*;
import java.lang.Math;

public class FakeFileInputStream extends InputStream {
  private static PlainJSObject registeredResources = PlainJSObject.create();

  public static FakeFileInputStream getResourceAsStream(String path) throws IOException {
    if (path.contains("/csd/")) {
      return null;
    }
    JavaScriptObject contents = registeredResources.getProperty(path);
    if (contents == null) {
      throwError(path);
    }
    return new FakeFileInputStream(contents);
  }

  private static native void throwError(String path)
  /*-{
    throw new Error('Missing resource (forgot to call Resources.registerResource?): ' + path);
  }-*/;

  public static void registerResource(String path, JavaScriptObject contents) {
    registeredResources.setProperty(path, contents);
  }

  // Should be a Uint8Array.
  private JavaScriptObject mContents;
  private int currentByte = 0;
  private int length;

  public FakeFileInputStream(JavaScriptObject contents) throws IOException {
    mContents = contents;
    length = getLength(contents);
  }

  private native int getLength(JavaScriptObject contents)
  /*-{
    return contents.length;
  }-*/;

  public int available() {
    return length - currentByte;
  }

  public void close() {
    mContents = null;
  }

  public int read() {
    if (currentByte == length) {
      return -1;
    }
    return readByte(mContents, currentByte++);
  }

  private native int readByte(JavaScriptObject contents, int index)
  /*-{
    return contents[index];
  }-*/;

  public int read(byte[] b) {
    if (currentByte == length) {
      return -1;
    }
    int bytes = Math.min(b.length, length - currentByte);
    readBytes(b, mContents, currentByte, bytes);
    currentByte += bytes;
    return bytes;
  }

  private native void readBytes(byte[] b, JavaScriptObject contents, int offset, int bytes)
  /*-{
    for (var i = 0; i <=  bytes; i++) {
      b[i] = contents[offset + i];
    }
  }-*/;
}

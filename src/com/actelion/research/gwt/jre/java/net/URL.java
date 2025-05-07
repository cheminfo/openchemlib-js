package java.net;

import java.io.InputStream;

import org.cheminfo.utils.JSException;

public class URL {
  public URL(String url) {}

  public URLConnection openConnection() {
    return new URLConnection();
  }

  public InputStream openStream() {
    JSException.throwUnimplemented();
    return null;
  }
}

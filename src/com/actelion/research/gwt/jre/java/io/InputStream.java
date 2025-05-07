package java.io;

import org.cheminfo.utils.JSException;

public abstract class InputStream {

  public int read() throws IOException {
    JSException.throwUnimplemented();
    return -1;
  }

  public int read(byte[] b, int off, int len) throws IOException {
    JSException.throwUnimplemented();
    return -1;
  }

  public int read(byte[] b) throws IOException {
    JSException.throwUnimplemented();
    return -1;
  }

  public void close() throws IOException {
    JSException.throwUnimplemented();
  }
}

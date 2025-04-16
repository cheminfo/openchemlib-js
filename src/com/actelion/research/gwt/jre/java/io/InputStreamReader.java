package java.io;

import java.nio.charset.Charset;

public class InputStreamReader extends Reader {
  private InputStream mStream;

  public InputStreamReader(InputStream in, Charset cs) {
    this(in);
  }

  public InputStreamReader(InputStream in, String charsetName) {
    this(in);
  }

  public InputStreamReader(InputStream in) {
    mStream = in;
  }

  public int read() throws IOException {
    return mStream.read();
  }

  public int read(char[] cbuf, int offset, int length) throws IOException {
    byte[] b = new byte[length];
    int read = mStream.read(b);
    for (int i = 0; i < read; i++) {
      cbuf[offset + i] = (char)b[i];
    }
    return read;
  }

  public int read(char[] cbuf) throws IOException {
    return read(cbuf, 0, cbuf.length);
  }

  public void close() throws IOException {
    mStream.close();
  }
}

package java.io;

import java.nio.charset.Charset;

public class OutputStreamWriter extends Writer {
  public OutputStreamWriter(OutputStream out) {}
  public OutputStreamWriter(OutputStream out, Charset cs) {}
  public void close() {}
  public void write(char[] cbuf, int off, int len) {}
  public void flush() {}
}

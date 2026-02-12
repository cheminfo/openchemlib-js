package java.io;

import org.cheminfo.utils.JSException;

public class BufferedWriter extends Writer {
  public BufferedWriter(Writer writer) {
  }

  public void newLine() {
    JSException.throwUnimplemented();
  }

  public void close() {}

  public void write(char[] cbuf, int off, int len) {}

  public void flush() {}
}

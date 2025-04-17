package java.io;

public class StringWriter extends Writer {

  private StringBuffer buf;

  public StringWriter() {
    buf = new StringBuffer();
  }

  public StringWriter(int initialSize) {
    if (initialSize < 0) {
      throw new IllegalArgumentException("Negative buffer size");
    }
    buf = new StringBuffer(initialSize);
  }

  public void write(int c) {
    buf.append((char) c);
  }

  public void write(char cbuf[], int off, int len) {
    if ((off < 0) || (off > cbuf.length) || (len < 0) || ((off + len) > cbuf.length) || ((off + len) < 0)) {
      throw new IndexOutOfBoundsException();
    } else if (len == 0) {
      return;
    }
    buf.append(cbuf, off, len);
  }

  public void write(String str) {
    buf.append(str);
  }

  public void write(String str, int off, int len) {
    buf.append(str.substring(off, off + len));
  }

  public StringWriter append(CharSequence csq) {
    if (csq == null)
      write("null");
    else
      write(csq.toString());
    return this;
  }

  public StringWriter append(CharSequence csq, int start, int end) {
    CharSequence cs = (csq == null ? "null" : csq);
    write(cs.subSequence(start, end).toString());
    return this;
  }

  public StringWriter append(char c) {
    write(c);
    return this;
  }

  public String toString() {
    return buf.toString();
  }

  public StringBuffer getBuffer() {
    return buf;
  }

  public void flush() {
  }

  public void close() throws IOException {
  }

}
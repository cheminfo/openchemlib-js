package java.io;

public abstract class Writer implements Appendable, Closeable, Flushable {

  private char[] writeBuffer;

  private final int writeBufferSize = 1024;

  protected Writer() {}

  public void write(int c) throws IOException {
    if (writeBuffer == null) {
      writeBuffer = new char[writeBufferSize];
    }
    writeBuffer[0] = (char) c;
    write(writeBuffer, 0, 1);
  }

  /**
   * Writes an array of characters.
   * 
   * @param cbuf Array of characters to be written
   * 
   * @throws IOException If an I/O error occurs
   */
  public void write(char cbuf[]) throws IOException {
    write(cbuf, 0, cbuf.length);
  }

  /**
   * Writes a portion of an array of characters.
   * 
   * @param cbuf Array of characters
   * 
   * @param off  Offset from which to start writing characters
   * 
   * @param len  Number of characters to write
   * 
   * @throws IOException If an I/O error occurs
   */
  public void write(char cbuf[], int off, int len) throws IOException {};

  public void write(String str) throws IOException {
    write(str, 0, str.length());
  }

  public void write(String str, int off, int len) throws IOException {
    char cbuf[];
    if (len <= writeBufferSize) {
      if (writeBuffer == null) {
        writeBuffer = new char[writeBufferSize];
      }
      cbuf = writeBuffer;
    } else { // Don't permanently allocate very large buffers.
      cbuf = new char[len];
    }
    str.getChars(off, (off + len), cbuf, 0);
    write(cbuf, 0, len);
  }

  public Writer append(CharSequence csq) throws IOException {
    if (csq == null)
      write("null");
    else
      write(csq.toString());
    return this;
  }

  public Writer append(CharSequence csq, int start, int end) throws IOException {
    CharSequence cs = (csq == null ? "null" : csq);
    write(cs.subSequence(start, end).toString());
    return this;
  }

  public Writer append(char c) throws IOException {
    write(c);
    return this;
  }

  public void flush() throws IOException {};

  public void close() throws IOException {};

}

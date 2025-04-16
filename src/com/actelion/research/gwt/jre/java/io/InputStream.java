package java.io;

public abstract class InputStream {

  public int read() throws IOException { return -1; }
  public int read(byte[] b, int off, int len) throws IOException { return -1; }
  public int read(byte[] b) throws IOException { return -1; }
  public void close() throws IOException {}
}

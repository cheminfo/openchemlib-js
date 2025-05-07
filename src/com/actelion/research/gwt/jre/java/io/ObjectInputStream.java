package java.io;

import org.cheminfo.utils.JSException;

public class ObjectInputStream extends InputStream {

  public ObjectInputStream(FileInputStream fos) {}

  public int readInt() throws IOException {
    JSException.throwUnimplemented();
    return 0;
  }

  public boolean readBoolean() throws IOException {
    JSException.throwUnimplemented();
    return true;
  }

  public double readDouble() throws IOException {
    JSException.throwUnimplemented();
    return 0;
  }

  public byte readByte() throws IOException {
    JSException.throwUnimplemented();
    return 0;
  }

  public Object readObject() throws IOException {
    JSException.throwUnimplemented();
    return "";
  }

  public void close() {
    JSException.throwUnimplemented();
  }

}

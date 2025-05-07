package java.io;

import org.cheminfo.utils.JSException;

public class FileOutputStream extends OutputStream {
  public FileOutputStream(String name) {}

  public FileOutputStream(File file) {}

  public FileOutputStream(File file, boolean bool) {}

  public void write(int b) {
    JSException.throwUnimplemented();
  }
}

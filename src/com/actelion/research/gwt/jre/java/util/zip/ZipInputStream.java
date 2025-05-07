package java.util.zip;

import java.io.InputStream;

import org.cheminfo.utils.JSException;

public class ZipInputStream extends InputStream {
  public ZipInputStream(InputStream is) {}

  public ZipEntry getNextEntry() {
    JSException.throwUnimplemented();
    return null;
  }
}

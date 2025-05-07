package java.awt;

import java.io.File;

import org.cheminfo.utils.JSException;

public class Desktop {
  public static boolean isDesktopSupported() {
    JSException.throwUnimplemented();
    return false;
  }

  public static Desktop getDesktop() {
    JSException.throwUnimplemented();
    return null;
  }

  public void open(File file) {
    JSException.throwUnimplemented();
  }
}

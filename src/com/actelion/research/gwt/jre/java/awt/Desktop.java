package java.awt;

import java.io.File;

public class Desktop {
  public static boolean isDesktopSupported() {
    return false;
  }

  public static Desktop getDesktop() {
    return null;
  }

  public void open(File file) {}
}

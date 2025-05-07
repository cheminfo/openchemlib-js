package java.io;

import org.cheminfo.utils.JSException;

public class File {
  public static char separatorChar = 10;

  public File(String sFile) {}

  public void write(String str) {
    JSException.throwUnimplemented();
  }

  public String getPath() {
    JSException.throwUnimplemented();
    return "";
  }

  public String getName() {
    JSException.throwUnimplemented();
    return "";
  }

  public String getAbsolutePath() {
    JSException.throwUnimplemented();
    return "";
  }

  public String getAbsoluteFile() {
    JSException.throwUnimplemented();
    return "";
  }

  public boolean mkdir() {
    JSException.throwUnimplemented();
    return false;
  }

  public boolean exists() {
    JSException.throwUnimplemented();
    return false;
  }

  public boolean isFile() {
    JSException.throwUnimplemented();
    return false;
  }

  public boolean isDirectory() {
    JSException.throwUnimplemented();
    return false;
  }
}

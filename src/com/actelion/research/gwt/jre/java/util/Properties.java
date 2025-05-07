package java.util;

import java.io.InputStream;

import org.cheminfo.utils.JSException;
import org.cheminfo.utils.JSHashMap;

public class Properties {
  private JSHashMap map = new JSHashMap();

  public String getProperty(String key) {
    return map.get(key);
  }

  public String setProperty(String key, String value) {
    return map.put(key, value);
  }

  public void load(InputStream inStream) {
    JSException.throwUnimplemented();
  }
}

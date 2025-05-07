package java.util;

import org.cheminfo.utils.JSException;

public class Calendar {
  public static final int YEAR = 1;
  public static final int MONTH = 2;
  public static final int DAY_OF_MONTH = 5;
  public static final int HOUR_OF_DAY = 11;
  public static final int MINUTE = 12;
  public static final int SECOND = 13;

  public static Calendar getInstance() {
    JSException.throwUnimplemented();
    return null;
  }

  public void setTime(Date d) {
    JSException.throwUnimplemented();
  }

  public int get(int field) {
    JSException.throwUnimplemented();
    return -1;
  }

  public void set(int field, int value) {
    JSException.throwUnimplemented();
  }

  public final Date getTime() {
    JSException.throwUnimplemented();
    return null;
  }
}
package java.text;

import java.math.RoundingMode;

import org.cheminfo.utils.JSException;

public class DecimalFormat extends java.text.NumberFormat {
  private int numDigits;

  public DecimalFormat() {
    this("0.000");
  }

  public DecimalFormat(String pattern, DecimalFormatSymbols symbols) {
    this(pattern);
  }

  public DecimalFormat(String pattern) {
    numDigits = parsePattern(pattern);
  }

  private native int parsePattern(String pattern)
  /*-{
    var result = /^#?0\.(0+)$/.exec(pattern);
    if (!result) {
      throw new Error('unimplemented DecimalFormat with pattern ' + pattern);
    }
    return result[1].length;
  }-*/;

  public String format(double number) {
    return formatNative(number, numDigits);
  }

  public String format(float number) {
    return formatNative((double) number, numDigits);
  }

  private native String formatNative(double number, int numDigits)
  /*-{
    return number.toFixed(numDigits);
  }-*/;

  public void setRoundingMode(RoundingMode mode) {
    if (mode != RoundingMode.HALF_UP) {
      JSException.throwError("Only HALF_UP rounding mode is supported");
    }
  }
}

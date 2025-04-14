package java.text;

public class DecimalFormat extends java.text.NumberFormat {

  public DecimalFormat(String pattern, DecimalFormatSymbols symbols) {
    this.throwError("constructor(pattern, symbols)");
//     this(pattern);
  }

  public DecimalFormat(String pattern) {
    this.throwError("constructor(pattern)");
//     formatter = NumberFormat.getFormat(pattern);
  }

  public String format(double number) {
    this.throwError("format(double)");
    return "";
  }

  public String format(float number) {
    this.throwError("format(float)");
    return "";
  }

  private native void throwError(String name)
  /*-{
    throw new Error('unimplemented DecimalFormat ' + name);
  }-*/;

}

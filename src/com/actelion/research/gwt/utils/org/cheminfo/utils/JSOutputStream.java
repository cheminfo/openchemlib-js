package org.cheminfo.utils;

import com.google.gwt.core.client.JavaScriptObject;
import java.io.OutputStream;

public class JSOutputStream extends OutputStream {
  private JavaScriptObject cb;

  public JSOutputStream(JavaScriptObject cb) {
    this.cb = cb;
  }

  private JavaScriptObject getCb() {
    return this.cb;
  }

  public native void close()
  /*-{
    var cb = this.@org.cheminfo.utils.JSOutputStream::getCb()();
    cb('close');
  }-*/;

  public native void flush()
  /*-{
    var cb = this.@org.cheminfo.utils.JSOutputStream::getCb()();
    cb('flush');
  }-*/;

  public native void write(byte[] b)
  /*-{
    var cb = this.@org.cheminfo.utils.JSOutputStream::getCb()();
    cb(b);
  }-*/;

  public native void write(byte[] b, int off, int len)
  /*-{
    var cb = this.@org.cheminfo.utils.JSOutputStream::getCb()();
    cb(b, off, len);
  }-*/;

  public native void write(int v)
  /*-{
    var cb = this.@org.cheminfo.utils.JSOutputStream::getCb()();
    cb(v);
  }-*/;
}

package com.actelion.research.gwt.js.api;

import com.google.gwt.core.client.JavaScriptObject;
import java.io.PrintStream;
import jsinterop.annotations.*;
import org.cheminfo.utils.JSOutputStream;

@JsType(name = "System")
public class JSSystem {
  public static void printDebugOut(String str) {
    System.out.println(str);
  }

  public static void printDebugErr(String str) {
    System.err.println(str);
  }

  public static void setOut(JavaScriptObject outFn) {
    System.setOut(new PrintStream(new JSOutputStream(outFn)));
  }

  public static void setErr(JavaScriptObject errFn) {
    System.setErr(new PrintStream(new JSOutputStream(errFn)));
  }
}

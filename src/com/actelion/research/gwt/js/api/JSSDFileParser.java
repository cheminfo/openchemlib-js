package com.actelion.research.gwt.js.api;

import com.google.gwt.core.client.JavaScriptObject;
import java.io.StringReader;
import jsinterop.annotations.*;

import com.actelion.research.chem.io.SDFileParser;

@JsType(name = "SDFileParser")
public class JSSDFileParser {

  private SDFileParser parser;

  public JSSDFileParser(String sdf, String[] fields) {
    parser = new SDFileParser(new StringReader(sdf), fields);
  }

  public boolean next() {
    return parser.next();
  }

  public JSMolecule getMolecule() {
    return new JSMolecule(parser.getMolecule());
  }

  public String getNextMolFile() {
    return parser.getNextMolFile();
  }

  public String getNextFieldData() {
    return parser.getNextFieldData();
  }

  public String[] getFieldNames(int recordsToInspect) {
    return parser.getFieldNames(recordsToInspect);
  }

  public String getFieldData(int idx) {
    return parser.getFieldData(idx);
  }

  public String getField(String name) {
    String[] names = parser.getFieldNames();
    for (int i = 0; i < names.length; i++) {
      if (names[i].equals(name)) {
        return parser.getFieldData(i);
      }
    }
    return null;
  }
}

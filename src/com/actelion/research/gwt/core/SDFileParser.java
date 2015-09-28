package com.actelion.research.gwt.core;

import java.io.StringReader;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.*;

@JsType
@JsNamespace("$wnd.OCL")
@JsExport
public class SDFileParser {

	private com.actelion.research.chem.io.SDFileParser parser;
	
	@JsExport
	public SDFileParser(String sdf, String[] fields) {
		parser = new com.actelion.research.chem.io.SDFileParser(new StringReader(sdf), fields);
	}
	
	public boolean next() {
		return parser.next();
	}
	
	public Molecule getMolecule() {
		return new Molecule(parser.getMolecule());
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
		for(int i = 0; i < names.length; i++) {
			if(names[i].equals(name)) {
				return parser.getFieldData(i);
			}
		}
		return null;
	}
}

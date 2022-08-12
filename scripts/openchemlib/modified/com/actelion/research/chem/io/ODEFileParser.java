package com.actelion.research.chem.io;

import java.io.*;

public class ODEFileParser extends CompoundFileParser {

	public ODEFileParser(String fileName) {}
	public ODEFileParser(File file) {}

	public String[] getFieldNames() {
		return null;
	}

	public String getFieldData(int column) {
		return null;
	}

	public int getRowCount() {
		return -1;
	}

	protected boolean advanceToNext() {
		return false;
	}

	public String getMoleculeName() {
		return null;
	}
}

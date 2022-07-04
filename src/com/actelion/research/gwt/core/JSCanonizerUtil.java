package com.actelion.research.gwt.core;

import com.actelion.research.chem.CanonizerUtil;
import com.actelion.research.gwt.minimal.JSMolecule;
import jsinterop.annotations.*;

@JsType(name = "CanonizerUtil")
public class JSCanonizerUtil {

	public static int NORMAL = CanonizerUtil.NORMAL;
	public static int NOSTEREO = CanonizerUtil.NOSTEREO;
	public static int BACKBONE = CanonizerUtil.BACKBONE;
	public static int TAUTOMER = CanonizerUtil.TAUTOMER;
	public static int NOSTEREO_TAUTOMER = CanonizerUtil.NOSTEREO_TAUTOMER;

	public static String getIDCode(JSMolecule mol, IDCODE_TYPE type) {
		return CanonizerUtil.getIDCode(mol, false);
	}

}

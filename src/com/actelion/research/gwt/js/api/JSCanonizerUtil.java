package com.actelion.research.gwt.js.api;

import jsinterop.annotations.*;

import com.actelion.research.chem.CanonizerUtil;

@JsType(name = "CanonizerUtil")
public class JSCanonizerUtil {

  public static int NORMAL = 0;
  public static int NOSTEREO = 1;
  public static int BACKBONE = 2;
  public static int TAUTOMER = 3;
  public static int NOSTEREO_TAUTOMER = 4;

  public static String getIDCode(JSMolecule mol, int type) {
    switch (type) {
      case 0:
        return CanonizerUtil.getIDCode(mol.getStereoMolecule(), CanonizerUtil.IDCODE_TYPE.NORMAL,
            false);
      case 1:
        return CanonizerUtil.getIDCode(mol.getStereoMolecule(), CanonizerUtil.IDCODE_TYPE.NOSTEREO,
            false);
      case 2:
        return CanonizerUtil.getIDCode(mol.getStereoMolecule(), CanonizerUtil.IDCODE_TYPE.BACKBONE,
            false);
      case 3:
        return CanonizerUtil.getIDCode(mol.getStereoMolecule(), CanonizerUtil.IDCODE_TYPE.TAUTOMER,
            false);
      case 4:
        return CanonizerUtil.getIDCode(mol.getStereoMolecule(),
            CanonizerUtil.IDCODE_TYPE.NOSTEREO_TAUTOMER, false);
      default:
        return CanonizerUtil.getIDCode(mol.getStereoMolecule(), CanonizerUtil.IDCODE_TYPE.NORMAL,
            false);
    }
  }
}

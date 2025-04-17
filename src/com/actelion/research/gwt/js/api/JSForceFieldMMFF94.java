package com.actelion.research.gwt.js.api;

import com.google.gwt.core.client.JavaScriptObject;
import java.util.HashMap;
import jsinterop.annotations.*;

import com.actelion.research.chem.*;
import com.actelion.research.chem.forcefield.mmff.ForceFieldMMFF94;

@JsType(name = "ForceFieldMMFF94")
public class JSForceFieldMMFF94 {
  private ForceFieldMMFF94 oclMmff;

  public static final String MMFF94 = "MMFF94";
  public static final String MMFF94S = "MMFF94s";
  public static final String MMFF94SPLUS = "MMFF94s+";

  private static boolean isMMFF94Init = false;
  private static boolean isMMFF94SInit = false;
  private static boolean isMMFF94SPLUSInit = false;

  public JSForceFieldMMFF94(JSMolecule molecule, String tablename, JavaScriptObject options) {
    JSResources.checkHasRegistered();
    initializeTables(tablename);
    oclMmff = new ForceFieldMMFF94(molecule.getStereoMolecule(), tablename, new HashMap<String, Object>());
  }

  public int size() {
    return oclMmff.size();
  }

  public double getTotalEnergy() {
    return oclMmff.getTotalEnergy();
  }

  public int _minimise(int maxIts, double gradTol, double funcTol) {
    return oclMmff.minimise(maxIts, gradTol, funcTol);
  }

  private static void initializeTables(String tablename) {
    if (tablename.equals(MMFF94) && !isMMFF94Init) {
      ForceFieldMMFF94.initialize(MMFF94);
      isMMFF94Init = true;
    } else if (tablename.equals(MMFF94S) && !isMMFF94SInit) {
      ForceFieldMMFF94.initialize(MMFF94S);
      isMMFF94SInit = true;
    } else if (tablename.equals(MMFF94SPLUS) && !isMMFF94SPLUSInit) {
      ForceFieldMMFF94.initialize(MMFF94SPLUS);
      isMMFF94SPLUSInit = true;
    }
  }
}

package com.actelion.research.gwt.core;

import com.actelion.research.chem.prediction.DruglikenessPredictor;
import com.actelion.research.gwt.minimal.JSMolecule;
import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

@JsType(name = "DruglikenessPredictor")
public class JSDruglikenessPredictor {
  private static Services services = Services.getInstance();
  private DruglikenessPredictor predictor;

  public static double DRUGLIKENESS_UNKNOWN = DruglikenessPredictor.cDruglikenessUnknown;

  public JSDruglikenessPredictor() {
    predictor = new DruglikenessPredictor();
  }

  public double assessDruglikeness(JSMolecule molecule) {
    return predictor.assessDruglikeness(molecule.getStereoMolecule(), services.getThreadMaster());
  }

  public String getDruglikenessString(JSMolecule molecule) {
    return predictor.getDruglikenessString(molecule.getStereoMolecule());
  }

  public JavaScriptObject getDetail() {
    return Util.convertParameterizedStringList(predictor.getDetail());
  }
}

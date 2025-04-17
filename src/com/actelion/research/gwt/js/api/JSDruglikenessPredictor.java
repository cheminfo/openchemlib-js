package com.actelion.research.gwt.js.api;

import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

import com.actelion.research.chem.prediction.DruglikenessPredictor;
import com.actelion.research.chem.prediction.ParameterizedStringList;
import com.actelion.research.gwt.js.utils.*;

@JsType(name = "DruglikenessPredictor")
public class JSDruglikenessPredictor {
  private DruglikenessPredictor predictor;

  public static double DRUGLIKENESS_UNKNOWN = DruglikenessPredictor.cDruglikenessUnknown;

  public JSDruglikenessPredictor() {
    JSResources.checkHasRegistered();
    predictor = new DruglikenessPredictor();
  }

  public double assessDruglikeness(JSMolecule molecule) {
    return predictor.assessDruglikeness(molecule.getStereoMolecule(), ThreadMaster.getInstance());
  }

  public String getDruglikenessString(JSMolecule molecule) {
    return predictor.getDruglikenessString(molecule.getStereoMolecule());
  }

  public JavaScriptObject getDetail() {
    ParameterizedStringList detail = predictor.getDetail();
    if (detail == null) {
      throwDetailError();
    }
    return Util.convertParameterizedStringList(detail);
  }

  private native void throwDetailError()
  /*-{
    throw new Error('drug likeness must be assessed first');
  }-*/;
}

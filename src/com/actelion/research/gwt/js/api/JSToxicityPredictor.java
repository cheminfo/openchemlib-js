package com.actelion.research.gwt.js.api;

import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

import com.actelion.research.chem.prediction.ToxicityPredictor;
import com.actelion.research.gwt.js.utils.ThreadMaster;
import com.actelion.research.gwt.js.utils.Util;

@JsType(name = "ToxicityPredictor")
public class JSToxicityPredictor {
  private ToxicityPredictor predictor;

  public static int RISK_UNKNOWN = ToxicityPredictor.cUnknownRisk;
  public static int RISK_NO = ToxicityPredictor.cNoRisk;
  public static int RISK_LOW = ToxicityPredictor.cLowRisk;
  public static int RISK_HIGH = ToxicityPredictor.cHighRisk;

  public static int TYPE_MUTAGENIC = ToxicityPredictor.cRiskTypeMutagenic;
  public static int TYPE_TUMORIGENIC = ToxicityPredictor.cRiskTypeTumorigenic;
  public static int TYPE_IRRITANT = ToxicityPredictor.cRiskTypeIrritant;
  public static int TYPE_REPRODUCTIVE_EFFECTIVE = ToxicityPredictor.cRiskTypeReproductiveEffective;

  public static final String[] RISK_NAMES = ToxicityPredictor.cRiskNameN;

  public JSToxicityPredictor() {
    JSResources.checkHasRegistered();
    predictor = new ToxicityPredictor();
  }

  public int assessRisk(JSMolecule molecule, int riskType) {
    return predictor.assessRisk(molecule.getStereoMolecule(), riskType, ThreadMaster.getInstance());
  }

  public JavaScriptObject getDetail(JSMolecule molecule, int riskType) {
    return Util.convertParameterizedStringList(predictor.getDetail(molecule.getStereoMolecule(), riskType));
  }
}

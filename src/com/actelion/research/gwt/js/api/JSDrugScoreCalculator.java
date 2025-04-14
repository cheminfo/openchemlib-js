package com.actelion.research.gwt.js.api;

import jsinterop.annotations.*;

import com.actelion.research.chem.prediction.DrugScoreCalculator;

@JsType(name = "DrugScoreCalculator")
public class JSDrugScoreCalculator {
  public static double calculate(double mCLogP, double mSolubility, double mMolweight, double mDruglikeness,
      int[] toxRisks) {
    return DrugScoreCalculator.calculate(mCLogP, mSolubility, mMolweight, mDruglikeness, toxRisks);
  }
}

package com.actelion.research.gwt.core;

import com.actelion.research.chem.prediction.DrugScoreCalculator;
import com.actelion.research.gwt.minimal.JSMolecule;
import jsinterop.annotations.*;

@JsType(name = "DrugScoreCalculator")
public class JSDrugScoreCalculator {
	public static double calculate(double mCLogP, double mSolubility, double mMolweight, double mDruglikeness, int[] toxRisks) {
	    return DrugScoreCalculator.calculate(mCLogP, mSolubility, mMolweight, mDruglikeness, toxRisks);
	}
}

package com.actelion.research.gwt.core;

import com.actelion.research.chem.ExtendedMolecule;
import com.google.gwt.core.client.js.*;

@JsType
public class MolecularFormula extends com.actelion.research.chem.MolecularFormula {

	public MolecularFormula(ExtendedMolecule mol) {
		super(mol);
	}
	
	@Override
	@JsProperty
	public double getAbsoluteWeight() {
		return super.getAbsoluteWeight();
	}
	
	@Override
	@JsProperty
	public double getRelativeWeight() {
		return super.getRelativeWeight();
	}
	
	@Override
	@JsProperty
	public String getFormula() {
		return super.getFormula();
	}

}

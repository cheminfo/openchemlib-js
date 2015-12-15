package com.actelion.research.gwt.core;

import com.actelion.research.chem.ExtendedMolecule;
import jsinterop.annotations.*;

@JsType
public class MolecularFormula extends com.actelion.research.chem.MolecularFormula {

	public MolecularFormula(ExtendedMolecule mol) {
		super(mol);
	}
	
	@Override
	public double getAbsoluteWeight() {
		return super.getAbsoluteWeight();
	}
	
	@Override
	public double getRelativeWeight() {
		return super.getRelativeWeight();
	}
	
	@Override
	public String getFormula() {
		return super.getFormula();
	}

}

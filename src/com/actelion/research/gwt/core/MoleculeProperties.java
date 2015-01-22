package com.actelion.research.gwt.core;

import com.actelion.research.chem.PropertyCalculator;
import com.actelion.research.chem.StereoMolecule;
import com.google.gwt.core.client.js.*;

@JsType
public class MoleculeProperties extends PropertyCalculator {

	public MoleculeProperties(StereoMolecule mol) {
		super(mol);
	}
	
	@Override
	@JsProperty
	public int getAcceptorCount() {
		return super.getAcceptorCount();
	}
	
	@Override
	@JsProperty
	public int getDonorCount() {
		return super.getDonorCount();
	}
	
	@Override
	@JsProperty
	public double getLogP() {
		return super.getLogP();
	}
	
	@Override
	@JsProperty
	public double getLogS() {
		return super.getLogS();
	}
	
	@Override
	@JsProperty
	public double getPolarSurfaceArea() {
		return super.getPolarSurfaceArea();
	}
	
	@Override
	@JsProperty
	public int getRotatableBondCount() {
		return super.getRotatableBondCount();
	}
	
	@Override
	@JsProperty
	public int getStereoCenterCount() {
		return super.getStereoCenterCount();
	}

}

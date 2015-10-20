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
	public int getAcceptorCount() {
		return super.getAcceptorCount();
	}
	
	@Override
	public int getDonorCount() {
		return super.getDonorCount();
	}
	
	@Override
	public double getLogP() {
		return super.getLogP();
	}
	
	@Override
	public double getLogS() {
		return super.getLogS();
	}
	
	@Override
	public double getPolarSurfaceArea() {
		return super.getPolarSurfaceArea();
	}
	
	@Override
	public int getRotatableBondCount() {
		return super.getRotatableBondCount();
	}
	
	@Override
	public int getStereoCenterCount() {
		return super.getStereoCenterCount();
	}

}

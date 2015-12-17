package com.actelion.research.gwt.core;

import com.actelion.research.chem.PropertyCalculator;
import com.actelion.research.chem.StereoMolecule;
import jsinterop.annotations.*;

@JsType
public class MoleculeProperties extends PropertyCalculator {

	@JsIgnore
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

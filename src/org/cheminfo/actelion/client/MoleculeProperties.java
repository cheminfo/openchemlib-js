package org.cheminfo.actelion.client;

import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.Getter;

import com.actelion.research.chem.PropertyCalculator;
import com.actelion.research.chem.StereoMolecule;

public class MoleculeProperties extends PropertyCalculator implements Exportable {

	public MoleculeProperties(StereoMolecule mol) {
		super(mol);
	}
	
	@Override
	@Getter
	public int getAcceptorCount() {
		return super.getAcceptorCount();
	}
	
	@Override
	@Getter
	public int getDonorCount() {
		return super.getDonorCount();
	}
	
	@Override
	@Getter
	public double getLogP() {
		return super.getLogP();
	}
	
	@Override
	@Getter
	public double getLogS() {
		return super.getLogS();
	}
	
	@Override
	@Getter
	public double getPolarSurfaceArea() {
		return super.getPolarSurfaceArea();
	}
	
	@Override
	@Getter
	public int getRotatableBondCount() {
		return super.getRotatableBondCount();
	}
	
	@Override
	@Getter
	public int getStereoCenterCount() {
		return super.getStereoCenterCount();
	}

}

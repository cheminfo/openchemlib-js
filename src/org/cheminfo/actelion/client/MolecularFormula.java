package org.cheminfo.actelion.client;

import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.Getter;

import com.actelion.research.chem.ExtendedMolecule;

public class MolecularFormula extends com.actelion.research.chem.MolecularFormula implements Exportable {

	public MolecularFormula(ExtendedMolecule mol) {
		super(mol);
	}
	
	@Override
	@Getter
	public double getAbsoluteWeight() {
		return super.getAbsoluteWeight();
	}
	
	@Override
	@Getter
	public double getRelativeWeight() {
		return super.getRelativeWeight();
	}
	
	@Override
	@Getter
	public String getFormula() {
		return super.getFormula();
	}

}

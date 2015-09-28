package com.actelion.research.gwt.core;

import com.google.gwt.core.client.js.*;

@JsType
@JsNamespace("$wnd.OCL")
@JsExport
public class SSSearcher {
	
	private com.actelion.research.chem.SSSearcher searcher = new com.actelion.research.chem.SSSearcher();
	
	public SSSearcher() {}

	public void setMol(Molecule fragment, Molecule molecule) {
	    setMolecule(molecule);
	    setFragment(fragment);
	}
	
	public void setFragment(Molecule fragment) {
		this.searcher.setFragment(fragment.getStereoMolecule());
	}
	
	public void setMolecule(Molecule molecule) {
		this.searcher.setMolecule(molecule.getStereoMolecule());
	}
	
	public boolean isFragmentInMolecule() {
		return this.searcher.isFragmentInMolecule();
	}
	
}

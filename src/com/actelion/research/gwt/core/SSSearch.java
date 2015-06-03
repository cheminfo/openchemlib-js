package com.actelion.research.gwt.core;

import com.actelion.research.chem.SSSearcher;
import com.google.gwt.core.client.js.*;

@JsType
@JsNamespace("$wnd.OCL")
@JsExport
public class SSSearch {
	
	private SSSearcher searcher = new SSSearcher();
	
	public SSSearch() {}
	
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

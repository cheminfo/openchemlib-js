package com.actelion.research.gwt.core;

import com.actelion.research.chem.SSSearcher;
import com.google.gwt.core.client.js.*;

@JsType
@JsNamespace("OCL")
@JsExport("SSSearcher")
public class JSSSSearcher {
	
	private SSSearcher searcher = SSSearcher();
	
	public JSSSSearcher() {}

	public void setMol(JSMolecule fragment, JSMolecule molecule) {
	    setMolecule(molecule);
	    setFragment(fragment);
	}
	
	public void setFragment(JSMolecule fragment) {
		this.searcher.setFragment(fragment.getStereoMolecule());
	}
	
	public void setMolecule(JSMolecule molecule) {
		this.searcher.setMolecule(molecule.getStereoMolecule());
	}
	
	public boolean isFragmentInMolecule() {
		return this.searcher.isFragmentInMolecule();
	}
	
}

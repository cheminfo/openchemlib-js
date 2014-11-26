package org.cheminfo.actelion.client;

import com.actelion.research.chem.SSSearcher;
import com.google.gwt.core.client.js.*;

@JsType
public class SSSearch {
	
	private SSSearcher searcher = new SSSearcher();
	
	@JsExport("actelion.SSSearch")
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

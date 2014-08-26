package org.cheminfo.actelion.client;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import com.actelion.research.chem.SSSearcher;

@ExportPackage(value="actelion")
@Export
public class SSSearch implements Exportable {
	
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

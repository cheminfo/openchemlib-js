package org.cheminfo.actelion.client;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;

import com.actelion.research.chem.SSSearcherWithIndex;
import com.actelion.research.chem.StereoMolecule;

@ExportPackage(value="actelion")
@Export
public class SSSearchWithIndex implements Exportable {
	
	private SSSearcherWithIndex searcher = new SSSearcherWithIndex();
	
	public SSSearchWithIndex() {}
	
	public static String[] getKeyIDCode() {
		return SSSearcherWithIndex.cKeyIDCode;
	}
	
	public void setFragment(Molecule fragment) {
		StereoMolecule mol = fragment.getStereoMolecule();
		this.searcher.setFragment(mol, searcher.createIndex(mol));
	}
	
	public void setFragment(Molecule fragment, int[] index) {
		this.searcher.setFragment(fragment.getStereoMolecule(), index);
	}
	
	public void setMolecule(Molecule molecule) {
		StereoMolecule mol = molecule.getStereoMolecule();
		this.searcher.setMolecule(mol, searcher.createIndex(mol));
	}
	
	public void setMolecule(Molecule molecule, int[] index) {
		this.searcher.setMolecule(molecule.getStereoMolecule(), index);
	}
	
	public boolean isFragmentInMolecule() {
		return this.searcher.isFragmentInMolecule();
	}
	
	public int[] getIndex(Molecule molecule) {
		return searcher.createIndex(molecule.getStereoMolecule());
	}
	
	public float getTanimotoSimilarity(int[] index1, int[] index2) {
		return SSSearcherWithIndex.getSimilarityTanimoto(index1, index2);
	}
	
	public float getTanimotoSimilarity(Molecule mol1, Molecule mol2) {
		return SSSearcherWithIndex.getSimilarityTanimoto(getIndex(mol1), getIndex(mol2));
	}
}

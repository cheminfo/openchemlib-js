package org.cheminfo.actelion.client;

import com.actelion.research.chem.SSSearcherWithIndex;
import com.actelion.research.chem.StereoMolecule;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.*;

@JsType
@JsNamespace("$wnd.actelion")
@JsExport
public class SSSearchWithIndex {
	
	private SSSearcherWithIndex searcher = new SSSearcherWithIndex();
	
	public SSSearchWithIndex() {}
	
	public static String[] getKeyIDCode() {
		return SSSearcherWithIndex.cKeyIDCode;
	}
	
	public native void setFragment(JavaScriptObject fragment, JavaScriptObject index) /*-{
		if (index) {
			return this.@org.cheminfo.actelion.client.SSSearchWithIndex::setFragment(Lorg/cheminfo/actelion/client/Molecule;[I)(fragment, index);
		} else {
			return this.@org.cheminfo.actelion.client.SSSearchWithIndex::setFragment(Lorg/cheminfo/actelion/client/Molecule;)(fragment);
		}
	}-*/;
	
	@JsNoExport
	public void setFragment(Molecule fragment) {
		StereoMolecule mol = fragment.getStereoMolecule();
		this.searcher.setFragment(mol, searcher.createIndex(mol));
	}
	
	@JsNoExport
	public void setFragment(Molecule fragment, int[] index) {
		this.searcher.setFragment(fragment.getStereoMolecule(), index);
	}
	
	public native void setMolecule(JavaScriptObject molecule, JavaScriptObject index) /*-{
		if (index) {
			return this.@org.cheminfo.actelion.client.SSSearchWithIndex::setMolecule(Lorg/cheminfo/actelion/client/Molecule;[I)(molecule, index);
		} else {
			return this.@org.cheminfo.actelion.client.SSSearchWithIndex::setMolecule(Lorg/cheminfo/actelion/client/Molecule;)(molecule);
		}
	}-*/;
	
	@JsNoExport
	public void setMolecule(Molecule molecule) {
		StereoMolecule mol = molecule.getStereoMolecule();
		this.searcher.setMolecule(mol, searcher.createIndex(mol));
	}
	
	@JsNoExport
	public void setMolecule(Molecule molecule, int[] index) {
		this.searcher.setMolecule(molecule.getStereoMolecule(), index);
	}
	
	public boolean isFragmentInMolecule() {
		return this.searcher.isFragmentInMolecule();
	}
	
	public int[] getIndex(Molecule molecule) {
		return searcher.createIndex(molecule.getStereoMolecule());
	}
	
	public static float getTanimotoSimilarity(int[] index1, int[] index2) {
		return SSSearcherWithIndex.getSimilarityTanimoto(index1, index2);
	}

}
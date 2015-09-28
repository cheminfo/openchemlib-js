package com.actelion.research.gwt.core;

import com.actelion.research.chem.StereoMolecule;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.*;

@JsType
@JsNamespace("$wnd.OCL")
@JsExport
public class SSSearcherWithIndex {
	
	private com.actelion.research.chem.SSSearcherWithIndex searcher = new com.actelion.research.chem.SSSearcherWithIndex();
	
	public SSSearcherWithIndex() {}
	
	public static String[] getKeyIDCode() {
		return com.actelion.research.chem.SSSearcherWithIndex.cKeyIDCode;
	}

	public void setFragment(Molecule fragment, int[] index) {
		this.searcher.setFragment(fragment.getStereoMolecule(), index);
	}

	public void setMolecule(Molecule molecule, int[] index) {
		this.searcher.setMolecule(molecule.getStereoMolecule(), index);
	}
	
	public boolean isFragmentInMolecule() {
		return this.searcher.isFragmentInMolecule();
	}
	
	public int[] createIndex(Molecule molecule) {
		return searcher.createIndex(molecule.getStereoMolecule());
	}
	
	public static float getSimilarityTanimoto(int[] index1, int[] index2) {
		return com.actelion.research.chem.SSSearcherWithIndex.getSimilarityTanimoto(index1, index2);
	}

	public static float getSimilarityAngleCosine(int[] index1, int[] index2) {
		return com.actelion.research.chem.SSSearcherWithIndex.getSimilarityAngleCosine(index1, index2);
	}

	public static int[] getIndexFromHexString(String hex) {
		return com.actelion.research.chem.SSSearcherWithIndex.getIndexFromHexString(hex);
	}

	public static String getHexStringFromIndex(int[] index) {
		return com.actelion.research.chem.SSSearcherWithIndex.getHexStringFromIndex(index);
	}

	public static int bitCount(int x) {
	    return com.actelion.research.chem.SSSearcherWithIndex.bitCount(x);
	}

}

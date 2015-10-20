package com.actelion.research.gwt.core;

import com.actelion.research.chem.SSSearcherWithIndex;
import com.actelion.research.chem.StereoMolecule;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.*;

@JsType
@JsNamespace("OCL")
@JsExport("SSSearcherWithIndex")
public class JSSSSearcherWithIndex {
	
	private SSSearcherWithIndex searcher = new SSSearcherWithIndex();
	
	public JSSSSearcherWithIndex() {}
	
	public static String[] getKeyIDCode() {
		return com.actelion.research.chem.SSSearcherWithIndex.cKeyIDCode;
	}

	public void setFragment(JSMolecule fragment, int[] index) {
		this.searcher.setFragment(fragment.getStereoMolecule(), index);
	}

	public void setMolecule(JSMolecule molecule, int[] index) {
		this.searcher.setMolecule(molecule.getStereoMolecule(), index);
	}
	
	public boolean isFragmentInMolecule() {
		return this.searcher.isFragmentInMolecule();
	}
	
	public int[] createIndex(JSMolecule molecule) {
		return searcher.createIndex(molecule.getStereoMolecule());
	}
	
	public static float getSimilarityTanimoto(int[] index1, int[] index2) {
		return SSSearcherWithIndex.getSimilarityTanimoto(index1, index2);
	}

	public static float getSimilarityAngleCosine(int[] index1, int[] index2) {
		return SSSearcherWithIndex.getSimilarityAngleCosine(index1, index2);
	}

	public static int[] getIndexFromHexString(String hex) {
		return SSSearcherWithIndex.getIndexFromHexString(hex);
	}

	public static String getHexStringFromIndex(int[] index) {
		return SSSearcherWithIndex.getHexStringFromIndex(index);
	}

	public static int bitCount(int x) {
	    return SSSearcherWithIndex.bitCount(x);
	}

}

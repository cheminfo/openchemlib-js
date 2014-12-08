package org.cheminfo.actelion.test;

import com.actelion.research.chem.IDCodeParser;
import com.actelion.research.chem.SSSearcherWithIndex;
import com.actelion.research.chem.StereoMolecule;

public class ActelionBugTest {

	public static void test2() throws Exception {
		//String idcode = "gmhMHx@@D@bpDj]@RKUV`rXnVjuzkPG@ptaXeQYln@bTlwHhdmLhhlhhdhjhhheDd]ihlhhdhdTeEIhfhhXmDhdh\\hcBE@iT^@jWKMv|aVjfjZijfjZBZBJZ`hFbjjfjjZjjfZii``@@";
		String idcode = "eF@Hp@";
		IDCodeParser parser = new IDCodeParser(false);
		StereoMolecule mol = parser.getCompactMolecule(idcode);
//		CoordinateInventor inv = new CoordinateInventor();
//		inv.invent(mol);
		mol.getIDCode();
	}
	
	public static native void test3() /*-{
		
		var idcode1 = "gmhMHx@@D@bpDj]@RKUV`rXnVjuzkPG@ptaXeQYln@bTlwHhdmLhhlhhdhjhhheDd]ihlhhdhdTeEIhfhhXmDhdh\\hcBE@iT^@jWKMv|aVjfjZijfjZBZBJZ`hFbjjfjjZjjfZii``@@";
		var idcode2 = "eF@Hp@";
		
		var searcher = new actelion.SSSearchWithIndex;
		
		var queryMol = actelion.Molecule.fromIDCode(idcode2, true);
		queryMol.setFragment(true);
		var queryIndex = queryMol.getIndex();
		
		var actmol = actelion.Molecule.fromIDCode(idcode1, false);
		var act_idx = actmol.getIndex().slice();
		
		searcher.setFragment(queryMol, queryIndex);
		
		searcher.setMolecule(actmol, act_idx);
		
		console.log(searcher.isFragmentInMolecule());
		
	}-*/;
	
	public static void test() {
		String idcode1 = "gmhMHx@@D@bpDj]@RKUV`rXnVjuzkPG@ptaXeQYln@bTlwHhdmLhhlhhdhjhhheDd]ihlhhdhdTeEIhfhhXmDhdh\\hcBE@iT^@jWKMv|aVjfjZijfjZBZBJZ`hFbjjfjjZjjfZii``@@";
		String idcode2 = "eF@Hp@";
		
		IDCodeParser parser1 = new IDCodeParser(false);
		StereoMolecule mol1 = parser1.getCompactMolecule(idcode1);
		
		IDCodeParser parser2 = new IDCodeParser(true);
		StereoMolecule mol2 = parser2.getCompactMolecule(idcode2);
		mol2.setFragment(true);
		
		SSSearcherWithIndex searcher = new SSSearcherWithIndex();
		
		searcher.setFragment(mol2, searcher.createIndex(mol2));
		
		searcher.setMolecule(mol1, searcher.createIndex(mol1));
		
		System.out.println(searcher.isFragmentInMolecule());
		
	}

	public static void main(String[] args) throws Exception {
		test();
		//test2();
	}
	
}

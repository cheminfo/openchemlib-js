package org.cheminfo.actelion.test;

import org.cheminfo.actelion.client.Molecule;
import org.cheminfo.actelion.client.SSSearchWithIndex;

public class SSSearchWithIndexTest {
	
	private static void test() throws Exception {
		
		SSSearchWithIndex s = new SSSearchWithIndex();
		
		Molecule mol = Molecule.fromSmiles("CCO");
		Molecule mol2 = Molecule.fromSmiles("CCO");
		
		mol.setFragment(true);
		s.setFragment(mol, mol.getIndex());
		s.setMolecule(mol2, mol2.getIndex());
		
		System.out.println(s.isFragmentInMolecule());
		
	}

	public static void main(String[] args) throws Exception {

		test();

	}

}

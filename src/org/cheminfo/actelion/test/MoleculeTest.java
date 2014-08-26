package org.cheminfo.actelion.test;

import org.cheminfo.actelion.client.Molecule;

public class MoleculeTest {
	
	public static void testFromTo() throws Exception {
		
		String smiles = "CCc(c1)ccc2[n+]1ccc3c2Nc4c3cccc4";
		
		Molecule mol = Molecule.fromSmiles(smiles);
		System.out.println(mol.getIDCode());
		System.out.println(mol.toSmiles());
		
		String molfile = mol.toMolfile();
		System.out.println(molfile);
		
		Molecule mol2 = Molecule.fromMolfile(molfile);
		System.out.println(mol2.getIDCode());
		System.out.println(mol2.toSmiles());
		System.out.println(mol2.toMolfile());
		
	}

	public static void main(String[] args) throws Exception {
		testFromTo();
	}

}

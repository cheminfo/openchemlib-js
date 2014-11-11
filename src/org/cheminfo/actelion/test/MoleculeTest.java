package org.cheminfo.actelion.test;

import org.cheminfo.actelion.client.Molecule;
import org.cheminfo.actelion.client.Services;

import com.actelion.research.chem.MolfileCreator;
import com.actelion.research.chem.StereoMolecule;

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
	
	public static void testIDCode() throws Exception {
		
		String smiles = "CCO";
		
		System.out.println("Actelion-JS");
		
		Molecule mol1 = Molecule.fromSmiles(smiles);
		String idcode1 = mol1.getIDCode();
		String idcoords1 = mol1.getIDCoordinates();
		
		System.out.println(idcode1);
		System.out.println(idcoords1);
		System.out.println(mol1.toMolfile());
		
		Molecule mol2 = Molecule.fromIDCode(idcode1, idcoords1);
		String idcode2 = mol1.getIDCode();
		String idcoords2 = mol1.getIDCoordinates();
		
		System.out.println(idcode2);
		System.out.println(idcoords2);
		System.out.println(mol2.toMolfile());
		
		
		System.out.println();
		System.out.println("Actelion-Core");
		
		StereoMolecule mola1 = new StereoMolecule();
		Services.getInstance().getSmilesParser().parse(mola1, smiles);
		String idcodea1 = mola1.getIDCode();
		String idcoordsa1 = mola1.getIDCoordinates();
		
		System.out.println(idcodea1);
		System.out.println(idcoordsa1);
		System.out.println(getMolfile(mola1));
		
		StereoMolecule mola2 = new StereoMolecule();
		Services.getInstance().getIDCodeParser(false).parse(mola2, idcodea1, idcoordsa1);
		String idcodea2 = mola2.getIDCode();
		String idcoordsa2 = mola2.getIDCoordinates();
		
		System.out.println(idcodea2);
		System.out.println(idcoordsa2);
		System.out.println(getMolfile(mola2));
		
	}
	
	public static String getMolfile(StereoMolecule mol) {
		MolfileCreator creator = new MolfileCreator(mol);
		return creator.getMolfile();
	}

	public static void main(String[] args) throws Exception {
		testIDCode();
	}

}

package org.cheminfo.actelion.client;

import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.Getter;
import org.timepedia.exporter.client.NoExport;

import com.actelion.research.chem.*;

@ExportPackage(value="actelion")
@Export
public class Molecule implements Exportable {
	
	private StereoMolecule act_mol;
	private MoleculeProperties properties;
	
	public Molecule() {
		this(new StereoMolecule());
	}
	
	public Molecule(StereoMolecule mol) {
		act_mol = mol;
		properties = new MoleculeProperties(act_mol);
	}
	
	public static Molecule fromSmiles(String smiles) throws Exception {
		Molecule mol = new Molecule();
		SmilesParser parser = new SmilesParser();
		parser.parse(mol.act_mol, smiles);
		return mol;
	}
	
	public static Molecule fromMolfile(String molfile) throws Exception {
		Molecule mol = new Molecule();
		MolfileParser parser = new MolfileParser();
		parser.parse(mol.act_mol, molfile);
		return mol;
	}
	
	public static Molecule fromIDCode(String idcode, boolean ensure2DCoordinates) {
		Molecule mol = new Molecule();
		IDCodeParser parser = new IDCodeParser(ensure2DCoordinates);
		parser.parse(mol.act_mol, idcode);
		return mol;
	}
	
	public String toSmiles() {
		SmilesCreator creator = new SmilesCreator();
		return creator.generateSmiles(act_mol);
	}
	
	public String toMolfile() {
		MolfileCreator creator = new MolfileCreator(act_mol);
		return creator.getMolfile();
	}
	
	@Getter
	public String getIDCode() {
		return act_mol.getIDCode();
	}
	
	@Getter
	public MolecularFormula getMolecularFormula() {
		return new MolecularFormula(act_mol);
	}
	
	@Getter
	public MoleculeProperties getProperties() {
		return properties;
	}
	
	@Getter
	public int[] getIndex() {
		SSSearcherWithIndex searcher = new SSSearcherWithIndex();
		return searcher.createIndex(act_mol);
	}
	
	public void inventCoordinates() {
		CoordinateInventor inventor = new CoordinateInventor();
		inventor.invent(act_mol);
	}
	
	public boolean isFragment() {
		return act_mol.isFragment();
	}
	
	public void setFragment(boolean isFragment) {
		act_mol.setFragment(isFragment);
	}
	
	public int getFragmentNumbers() {
		return act_mol.getFragmentNumbers(new int[act_mol.getAllAtoms()], false);
	}
	
	public Molecule[] getFragments() {
		StereoMolecule[] fragments = act_mol.getFragments();
		Molecule[] newFragments = new Molecule[fragments.length];
		for(int i = 0; i < fragments.length; i++) {
			newFragments[i] = new Molecule(fragments[i]);
		}
		return newFragments;
	}
	
	/* public methods after this line will not be accessible from javascript */
	@NoExport
	public StereoMolecule getStereoMolecule() {
		return act_mol;
	}
}

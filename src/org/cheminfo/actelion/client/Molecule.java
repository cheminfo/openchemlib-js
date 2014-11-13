package org.cheminfo.actelion.client;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.cheminfo.chem.DiastereotopicAtomID;
import org.cheminfo.chem.HydrogenHandler;
import org.timepedia.exporter.client.Export;
import org.timepedia.exporter.client.ExportPackage;
import org.timepedia.exporter.client.Exportable;
import org.timepedia.exporter.client.Getter;
import org.timepedia.exporter.client.NoExport;

import com.actelion.research.chem.*;
import com.google.gwt.core.client.JavaScriptObject;

@ExportPackage(value="actelion")
@Export
public class Molecule implements Exportable {
	
	private static Services services = Services.getInstance();
	
	private StereoMolecule act_mol;
	private MoleculeProperties properties = null;
	private MolecularFormula formula = null;
	
	public Molecule() {
		this(new StereoMolecule());
	}
	
	public Molecule(StereoMolecule mol) {
		act_mol = mol;
	}
	
	public static Molecule fromSmiles(String smiles) throws Exception {
		Molecule mol = new Molecule();
		services.getSmilesParser().parse(mol.act_mol, smiles);
		return mol;
	}
	
	public static Molecule fromMolfile(String molfile) throws Exception {
		Molecule mol = new Molecule();
		services.getMolfileParser().parse(mol.act_mol, molfile);
		return mol;
	}
	
	public static Molecule fromIDCode(String idcode) {
		return fromIDCode(idcode, true);
	}
	
	public static Molecule fromIDCode(String idcode, boolean ensure2DCoordinates) {
		Molecule mol = new Molecule();
		services.getIDCodeParser(ensure2DCoordinates).parse(mol.act_mol, idcode);
		return mol;
	}
	
	public static Molecule fromIDCode(String idcode, String coordinates) {
		Molecule mol = new Molecule();
		services.getIDCodeParser(false).parse(mol.act_mol, idcode, coordinates);
		return mol;
	}
	
	public String toSmiles() {
		return services.getSmilesCreator().generateSmiles(act_mol);
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
	public String getIDCoordinates() {
		return act_mol.getIDCoordinates();
	}
	
	public native JavaScriptObject getIDCodeAndCoordinates() /*-{
		var mol = this.@org.cheminfo.actelion.client.Molecule::act_mol;
		return {
			idCode: mol.@com.actelion.research.chem.StereoMolecule::getIDCode()(),
			coordinates: mol.@com.actelion.research.chem.StereoMolecule::getIDCoordinates()()
		};
	}-*/;
	
	@Getter
	public MolecularFormula getMolecularFormula() {
		if(formula == null) {
			formula = new MolecularFormula(act_mol);
		}
		return formula;
	}
	
	@Getter
	public MoleculeProperties getProperties() {
		if(properties == null) {
			properties = new MoleculeProperties(act_mol);
		}
		return properties;
	}
	
	@Getter
	public int[] getIndex() {
		return services.getSSSearcherWithIndex().createIndex(act_mol);
	}
	
	public void inventCoordinates() {
		services.getCoordinateInventor().invent(act_mol);
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
	
	public void expandHydrogens() {
		HydrogenHandler.expandAllHydrogens(act_mol);
	}
	
	public JavaScriptObject[] getDiastereotopicAtomIDs() {
		return getDiastereotopicAtomIDs(null);
	}
	
	public JavaScriptObject[] getDiastereotopicAtomIDs(String element) {
		String[] diaIDs = getDiastereotopicAtomIDsArray();
		HashMap<String, Vector<Integer>> result=new HashMap<String, Vector<Integer>>();
		for (int i=0; i<diaIDs.length; i++) {
			if (element==null || element.equals("") || act_mol.getAtomLabel(i).equals(element)) {
				String diaID=diaIDs[i];
				if (result.containsKey(diaID)) {
					result.get(diaID).add(i);
				} else {
					Vector<Integer> atomIDs=new Vector<Integer>();
					atomIDs.add(i);
					result.put(diaID, atomIDs);
				}
			}
		}
		Set<String> keySet = result.keySet();
		JavaScriptObject[] toReturn = new JavaScriptObject[keySet.size()];
		int i = 0;
		for(String diaID : keySet) {
			Vector<Integer> linked=result.get(diaID);
			int jj = linked.size();
			int[] linkedAtoms = new int[jj];
			for(int j = 0; j < jj; j++) {
				linkedAtoms[j] = linked.get(j);
			}
			toReturn[i++] = getDiastereotopicAtomID(diaID, linkedAtoms, act_mol.getAtomLabel(linked.get(0)));
		}
		return toReturn;
	}
	
	/* public methods after this line will not be accessible from javascript */
	@NoExport
	public StereoMolecule getStereoMolecule() {
		return act_mol;
	}
	
	@NoExport
	public String[] getDiastereotopicAtomIDsArray() {
		return DiastereotopicAtomID.getAtomIds(act_mol);
	}
	
	@NoExport
	public native JavaScriptObject getDiastereotopicAtomID(String diaID, int[] linkedAtoms, String element) /*-{
		return {
			id: diaID,
			atoms: linkedAtoms,
			element: element
		};
	}-*/;
}

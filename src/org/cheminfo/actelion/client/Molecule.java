package org.cheminfo.actelion.client;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import org.cheminfo.chem.DiastereotopicAtomID;
import org.cheminfo.chem.HydrogenHandler;

import com.actelion.research.chem.*;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.*;

@JsType
@JsNamespace("$wnd.actelion")
@JsExport
public class Molecule {
	
	private static Services services = Services.getInstance();
	
	private StereoMolecule act_mol;
	private MoleculeProperties properties = null;
	private MolecularFormula formula = null;
	
	public Molecule() {
		this(new StereoMolecule(32, 32));
	}
	
	public Molecule(StereoMolecule mol) {
		act_mol = mol;
	}
	
	public static Molecule fromSmiles(String smiles) throws Exception {
		Molecule mol = new Molecule();
		services.getSmilesParser().parse(mol.act_mol, smiles.getBytes(), false, true);
		mol.inventCoordinates();
		return mol;
	}
	
	public static Molecule fromMolfile(String molfile) throws Exception {
		Molecule mol = new Molecule();
		services.getMolfileParser().parse(mol.act_mol, molfile);
		return mol;
	}
	
	public static native Molecule fromIDCode(String idcode, JavaScriptObject coordinates) /*-{
		var mol;
		if (typeof coordinates === 'undefined') {
			coordinates = true;
		}
		if (typeof coordinates === 'boolean') {
			mol = @org.cheminfo.actelion.client.Molecule::fromIDCode(Ljava/lang/String;Z)(idcode, false);
			if (coordinates === true) {
				mol.@org.cheminfo.actelion.client.Molecule::inventCoordinates()();
			}
		} else if(typeof coordinates === 'string') {
			mol = @org.cheminfo.actelion.client.Molecule::fromIDCode(Ljava/lang/String;Ljava/lang/String;)(idcode, coordinates);
		}
		return mol;
	}-*/;
	
	public String toSmiles() {
		return services.getSmilesCreator().generateSmiles(act_mol);
	}
	
	public String toMolfile() {
		MolfileCreator creator = new MolfileCreator(act_mol);
		return creator.getMolfile();
	}
	
	@JsProperty
	public String getIDCode() {
		return act_mol.getIDCode();
	}
	
	@JsProperty
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
	
	@JsProperty
	public MolecularFormula getMolecularFormula() {
		if(formula == null) {
			formula = new MolecularFormula(act_mol);
		}
		return formula;
	}
	
	@JsProperty
	public MoleculeProperties getProperties() {
		if(properties == null) {
			properties = new MoleculeProperties(act_mol);
		}
		return properties;
	}
	
	@JsProperty
	public int[] getIndex() {
		return services.getSSSearcherWithIndex().createIndex(act_mol);
	}
	
	public void inventCoordinates() {
		CoordinateInventor inventor = services.getCoordinateInventor();
		inventor.setRandomSeed(0);
		inventor.invent(act_mol);
		act_mol.setStereoBondsFromParity();
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
	
	public void ensureHelperArrays(int required) {
		act_mol.ensureHelperArrays(required);
	}
	
	public void expandHydrogens() {
		HydrogenHandler.expandAllHydrogens(act_mol);
	}
	
	public native JavaScriptObject[] getDiastereotopicAtomIDs(JavaScriptObject element) /*-{
		element = element || '';
		return this.@org.cheminfo.actelion.client.Molecule::getDiastereotopicAtomIDs(Ljava/lang/String;)(element);
	}-*/;
	
	/* public methods after this line will not be accessible from javascript */
	
	@JsNoExport
	public static Molecule fromIDCode(String idcode, boolean ensure2DCoordinates) {
		return new Molecule(services.getIDCodeParser(ensure2DCoordinates).getCompactMolecule(idcode));
	}
	
	@JsNoExport
	public static Molecule fromIDCode(String idcode, String coordinates) {
		return new Molecule(services.getIDCodeParser(false).getCompactMolecule(idcode, coordinates));
	}
	
	@JsNoExport
	public JavaScriptObject[] getDiastereotopicAtomIDs(String element) {
		String[] diaIDs = getDiastereotopicAtomIDsArray();
		HashMap<String, Vector<Integer>> result=new HashMap<String, Vector<Integer>>();
		for (int i=0; i<diaIDs.length; i++) {
			if (element == null || element.equals("") || act_mol.getAtomLabel(i).equals(element)) {
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
	
	@JsNoExport
	public StereoMolecule getStereoMolecule() {
		return act_mol;
	}
	
	@JsNoExport
	public String[] getDiastereotopicAtomIDsArray() {
		return DiastereotopicAtomID.getAtomIds(act_mol);
	}
	
	@JsNoExport
	public native JavaScriptObject getDiastereotopicAtomID(String diaID, int[] linkedAtoms, String element) /*-{
		return {
			id: diaID,
			atoms: linkedAtoms,
			element: element
		};
	}-*/;
}

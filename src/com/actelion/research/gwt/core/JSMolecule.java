package com.actelion.research.gwt.core;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import com.actelion.research.chem.*;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.*;

@JsType
@JsNamespace("OCL")
@JsExport("Molecule")
public class JSMolecule {
	
	private static Services services = Services.getInstance();
	
	private StereoMolecule act_mol;
	private MoleculeProperties properties = null;
	private MolecularFormula formula = null;
	
	public JSMolecule() {
		this(new StereoMolecule(32, 32));
	}
	
	public JSMolecule(StereoMolecule mol) {
		act_mol = mol;
	}
	
	public static native JSMolecule fromSmiles(String smiles, JavaScriptObject options) throws Exception /*-{
		options = options || {};
		var coordinates = !options.noCoordinates;
		var stereo = !options.noStereo;
		return @com.actelion.research.gwt.core.JSMolecule::fromSmiles(Ljava/lang/String;ZZ)(smiles, coordinates, stereo);
	}-*/;
	
	public static JSMolecule fromMolfile(String molfile) throws Exception {
		JSMolecule mol = new JSMolecule();
		services.getMolfileParser().parse(mol.act_mol, molfile);
		return mol;
	}
	
	public static native JSMolecule fromIDCode(String idcode, JavaScriptObject coordinates) /*-{
		var mol;
		if (typeof coordinates === 'undefined') {
			coordinates = true;
		}
		if (typeof coordinates === 'boolean') {
			mol = @com.actelion.research.gwt.core.JSMolecule::fromIDCode(Ljava/lang/String;Z)(idcode, false);
			if (coordinates === true) {
				mol.@com.actelion.research.gwt.core.JSMolecule::inventCoordinates()();
			}
		} else if(typeof coordinates === 'string') {
			mol = @com.actelion.research.gwt.core.JSMolecule::fromIDCode(Ljava/lang/String;Ljava/lang/String;)(idcode, coordinates);
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
	
	public String toSVG(int width, int height, String id) {
		SVGDepictor d = new SVGDepictor(act_mol, id);
		d.validateView(null, new Rectangle2D.Float(0, 0, width, height), AbstractDepictor.cModeInflateToMaxAVBL);
		d.paint(null);
		return d.toString();
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
		return {
			idCode: this.@com.actelion.research.gwt.core.JSMolecule::getIDCode()(),
			coordinates: this.@com.actelion.research.gwt.core.JSMolecule::getIDCoordinates()()
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
	
	public JSMolecule[] getFragments() {
		StereoMolecule[] fragments = act_mol.getFragments();
		JSMolecule[] newFragments = new JSMolecule[fragments.length];
		for(int i = 0; i < fragments.length; i++) {
			newFragments[i] = new JSMolecule(fragments[i]);
		}
		return newFragments;
	}
	
	public void ensureHelperArrays(int required) {
		act_mol.ensureHelperArrays(required);
	}
	
	/* public methods after this line will not be accessible from javascript */
	
	@JsNoExport
	public static JSMolecule fromSmiles(String smiles, boolean ensure2DCoordinates, boolean readStereoFeatures) throws Exception {
		JSMolecule mol = new JSMolecule();
		services.getSmilesParser().parse(mol.act_mol, smiles.getBytes(), false, readStereoFeatures);
		if (ensure2DCoordinates) {
			mol.inventCoordinates();
		}
		return mol;
	}
	
	@JsNoExport
	public static JSMolecule fromIDCode(String idcode, boolean ensure2DCoordinates) {
		return new JSMolecule(services.getIDCodeParser(ensure2DCoordinates).getCompactMolecule(idcode));
	}
	
	@JsNoExport
	public static JSMolecule fromIDCode(String idcode, String coordinates) {
		return new JSMolecule(services.getIDCodeParser(false).getCompactMolecule(idcode, coordinates));
	}
	
	@JsNoExport
	public StereoMolecule getStereoMolecule() {
		return act_mol;
	}
}

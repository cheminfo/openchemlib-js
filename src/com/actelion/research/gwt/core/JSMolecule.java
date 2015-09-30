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
	
	private StereoMolecule oclMolecule;
	private MoleculeProperties properties = null;
	private MolecularFormula formula = null;

	@JsNoExport
	public JSMolecule(int atoms, int bonds) {
		oclMolecule = new StereoMolecule(atoms, bonds);
	}

	@JsNoExport
	public JSMolecule(StereoMolecule mol) {
		oclMolecule = mol;
	}

	@JsNoExport
	public JSMolecule() {
		this(32, 32);
	}
	
	public static native JSMolecule fromSmiles(String smiles, JavaScriptObject options) throws Exception /*-{
		options = options || {};
		var coordinates = !options.noCoordinates;
		var stereo = !options.noStereo;
		return @com.actelion.research.gwt.core.JSMolecule::fromSmiles(Ljava/lang/String;ZZ)(smiles, coordinates, stereo);
	}-*/;
	
	public static JSMolecule fromMolfile(String molfile) throws Exception {
		JSMolecule mol = new JSMolecule();
		services.getMolfileParser().parse(mol.oclMolecule, molfile);
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
		return services.getSmilesCreator().generateSmiles(oclMolecule);
	}
	
	public String toMolfile() {
		MolfileCreator creator = new MolfileCreator(oclMolecule);
		return creator.getMolfile();
	}
	
	public String toSVG(int width, int height, String id) {
		SVGDepictor d = new SVGDepictor(oclMolecule, id);
		d.validateView(null, new Rectangle2D.Float(0, 0, width, height), AbstractDepictor.cModeInflateToMaxAVBL);
		d.paint(null);
		return d.toString();
	}
	
	public String getIDCode() {
		return oclMolecule.getIDCode();
	}
	
	public String getIDCoordinates() {
		return oclMolecule.getIDCoordinates();
	}
	
	public native JavaScriptObject getIDCodeAndCoordinates() /*-{
		return {
			idCode: this.@com.actelion.research.gwt.core.JSMolecule::getIDCode()(),
			coordinates: this.@com.actelion.research.gwt.core.JSMolecule::getIDCoordinates()()
		};
	}-*/;
	
	public MolecularFormula getMolecularFormula() {
		if(formula == null) {
			formula = new MolecularFormula(oclMolecule);
		}
		return formula;
	}
	
	public MoleculeProperties getProperties() {
		if(properties == null) {
			properties = new MoleculeProperties(oclMolecule);
		}
		return properties;
	}
	
	public int[] getIndex() {
		return services.getSSSearcherWithIndex().createIndex(oclMolecule);
	}
	
	public void inventCoordinates() {
		CoordinateInventor inventor = services.getCoordinateInventor();
		inventor.setRandomSeed(0);
		inventor.invent(oclMolecule);
		oclMolecule.setStereoBondsFromParity();
	}
	
	public boolean isFragment() {
		return oclMolecule.isFragment();
	}
	
	public void setFragment(boolean isFragment) {
		oclMolecule.setFragment(isFragment);
	}
	
	public int getFragmentNumbers() {
		return oclMolecule.getFragmentNumbers(new int[oclMolecule.getAllAtoms()], false);
	}
	
	public JSMolecule[] getFragments() {
		StereoMolecule[] fragments = oclMolecule.getFragments();
		JSMolecule[] newFragments = new JSMolecule[fragments.length];
		for(int i = 0; i < fragments.length; i++) {
			newFragments[i] = new JSMolecule(fragments[i]);
		}
		return newFragments;
	}
	
	public void ensureHelperArrays(int required) {
		oclMolecule.ensureHelperArrays(required);
	}
	
	/* public methods after this line will not be accessible from javascript */
	
	@JsNoExport
	public static JSMolecule fromSmiles(String smiles, boolean ensure2DCoordinates, boolean readStereoFeatures) throws Exception {
		JSMolecule mol = new JSMolecule();
		services.getSmilesParser().parse(mol.oclMolecule, smiles.getBytes(), false, readStereoFeatures);
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
		return oclMolecule;
	}
}

package com.actelion.research.gwt.minimal;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import com.actelion.research.chem.*;
import com.actelion.research.chem.contrib.*;
import com.actelion.research.chem.coords.CoordinateInventor;
import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

@JsType(name = "Molecule")
public class JSMolecule {

  private static Services services = Services.getInstance();
  private static Rectangle2D.Double rectangle = new Rectangle2D.Double();

  private StereoMolecule oclMolecule;

  public JSMolecule(int maxAtoms, int maxBonds, StereoMolecule mol) {
    if (mol != null) {
      oclMolecule = mol;
    } else {
      oclMolecule = new StereoMolecule(maxAtoms, maxBonds);
    }
  }

  @JsIgnore
  public JSMolecule(StereoMolecule mol) {
    this(0, 0, mol);
  }

  @JsIgnore
  public JSMolecule() {
    this(32, 32, null);
  }

  public static native JSMolecule fromSmiles(String smiles, JavaScriptObject options) throws Exception
  /*-{
  	options = options || {};
  	var coordinates = !options.noCoordinates;
  	var stereo = !options.noStereo;
  	return @com.actelion.research.gwt.minimal.JSMolecule::fromSmiles(Ljava/lang/String;ZZ)(smiles, coordinates, stereo);
  }-*/;

  public static JSMolecule fromMolfile(String molfile) throws Exception {
    return new JSMolecule(new MolfileParser().getCompactMolecule(molfile));
  }

  public static JavaScriptObject fromMolfileWithAtomMap(String molfile) throws Exception {
    MolfileParser parser = new MolfileParser(MolfileParser.MODE_KEEP_HYDROGEN_MAP);
    StereoMolecule mol = parser.getCompactMolecule(molfile);
    int[] map = parser.getHandleHydrogenMap();
    return createMolfileWithAtomMap(new JSMolecule(mol), map);
  }

  private static native JavaScriptObject createMolfileWithAtomMap(JSMolecule mol, int[] map)
  /*-{
  	return {molecule: mol, map: map};
  }-*/;

  public static native JSMolecule fromIDCode(String idcode, JavaScriptObject coordinates)
  /*-{
  	var mol;
  	if (typeof coordinates === 'undefined') {
  		coordinates = true;
  	}
  	if (typeof coordinates === 'boolean') {
  		mol = @com.actelion.research.gwt.minimal.JSMolecule::fromIDCode(Ljava/lang/String;Z)(idcode, false);
  		if (coordinates === true) {
  			mol.@com.actelion.research.gwt.minimal.JSMolecule::inventCoordinates()();
  		}
  	} else if(typeof coordinates === 'string') {
  		mol = @com.actelion.research.gwt.minimal.JSMolecule::fromIDCode(Ljava/lang/String;Ljava/lang/String;)(idcode, coordinates);
  	}
  	return mol;
  }-*/;
  
  public native Object getOCL()
  /*-{
    return $wnd.OCL;
  }-*/;

  public String toSmiles() {
    return new SmilesCreator().generateSmiles(oclMolecule);
  }

  public String toIsomericSmiles(boolean includeAtomMapping) {
    return new IsomericSmilesCreator(oclMolecule, includeAtomMapping).getSmiles();
  }

  public String toMolfile() {
    MolfileCreator creator = new MolfileCreator(oclMolecule);
    return creator.getMolfile();
  }

  public String toMolfileV3() {
    MolfileV3Creator creator = new MolfileV3Creator(oclMolecule);
    return creator.getMolfile();
  }

  public native String toSVG(int width, int height, String id, JavaScriptObject options)
  /*-{
  	//todo: re-enable this check once it becomes possible to change the font
  	//if (!$doc.createElement) {
  	//	throw new Error('Molecule#toSVG cannot be used outside of a browser\'s Window environment');
  	//}
  	options = options || {};
  	var factorTextSize = options.factorTextSize || 1;
    var autoCrop = options.autoCrop === true;
    var autoCropMargin = typeof options.autoCropMargin === 'undefined' ? 5 : options.autoCropMargin;
  	var svg =  this.@com.actelion.research.gwt.minimal.JSMolecule::getSVG(IIFZILjava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;)(width, height, factorTextSize, autoCrop, autoCropMargin, id, options);
  	if (options.fontWeight) {
      svg = svg.replace(/font-family=" Helvetica" /g, 'font-family=" Helvetica" font-weight="' + options.fontWeight + '" ');
    }
    if (options.strokeWidth) {
     svg = svg.replace(/stroke-width="[^"]+"/g, 'stroke-width="' + options.strokeWidth + '"');
    }
    return svg;
  }-*/;

  private String getSVG(int width, int height, float factorTextSize, boolean autoCrop, int autoCropMargin, String id, JavaScriptObject options) {
    int mode = Util.getDisplayMode(options);
    SVGDepictor d = new SVGDepictor(oclMolecule, mode, id);
    d.setFactorTextSize(factorTextSize);
    d.validateView(null, new Rectangle2D.Double(0, 0, width, height), AbstractDepictor.cModeInflateToMaxAVBL);
    Rectangle2D.Double b = d.getBoundingRect();
    d.paint(null);
    String result = d.toString();
    if (!autoCrop) {
      return result;
    } else {
      //return result.replaceAll("viewBox", "data-test=\"" + b.x + " " + b.y + " " + b.width + " " + b.height + "\" viewBox");
      int newWidth = (int) Math.round(b.width + autoCropMargin * 2);
      int newHeight = (int) Math.round(b.height + autoCropMargin * 2);
      int newX = (int) Math.round(b.x - autoCropMargin);
      int newY = (int) Math.round(b.y - autoCropMargin);
      return result.replaceAll(
        "width=\"\\d+px\" height=\"\\d+px\" viewBox=\"0 0 \\d+ \\d+\"",
        "width=\"" + newWidth + "px\" height=\"" + newHeight + "px\" viewBox=\"" + newX + " " + newY + " " + newWidth + " " + newHeight + "\""
      );
    }
  }

  public String getCanonizedIDCode(int flag) {
    Canonizer canonizer = new Canonizer(oclMolecule, flag);
    return canonizer.getIDCode();
  }

  public native JavaScriptObject getIDCodeAndCoordinates()
  /*-{
  	return {
  		idCode: this.@com.actelion.research.gwt.minimal.JSMolecule::getIDCode()(),
  		coordinates: this.@com.actelion.research.gwt.minimal.JSMolecule::getIDCoordinates()()
  	};
  }-*/;

  public MolecularFormula getMolecularFormula() {
    return new MolecularFormula(oclMolecule);
  }

  public int[] getIndex() {
    return services.getSSSearcherWithIndex().createIndex(oclMolecule);
  }

  public void inventCoordinates() {
    CoordinateInventor inventor = new CoordinateInventor();
    inventor.setRandomSeed(0);
    inventor.invent(oclMolecule);
    oclMolecule.setStereoBondsFromParity();
  }

  public native void addImplicitHydrogens(JavaScriptObject atomNumber)
  /*-{
  	if (atomNumber === undefined) {
  		this.@com.actelion.research.gwt.minimal.JSMolecule::addImplicitHydrogens()();
  	} else {
  		this.@com.actelion.research.gwt.minimal.JSMolecule::addImplicitHydrogens(I)(atomNumber);
  	}
  }-*/;

  public int getNumberOfHydrogens() {
    return HydrogenHandler.getNumberOfHydrogens(oclMolecule);
  }

  public String[] getDiastereotopicAtomIDs() {
    return DiastereotopicAtomID.getAtomIds(oclMolecule);
  }

  public native void addMissingChirality(JavaScriptObject esrType)
  /*-{
    if (esrType === undefined) {
      this.@com.actelion.research.gwt.minimal.JSMolecule::addMissingChirality()();
    } else {
      this.@com.actelion.research.gwt.minimal.JSMolecule::addMissingChirality(I)(esrType);
    }
  }-*/;

  @JsIgnore
  public void addMissingChirality() {
    DiastereotopicAtomID.addMissingChirality(oclMolecule);
  }

  @JsIgnore
  public void addMissingChirality(int esrType) {
    DiastereotopicAtomID.addMissingChirality(oclMolecule, esrType);
  }

  public native String[][] getHoseCodes(JavaScriptObject options)
  /*-{
  	options = options || {};
  	var maxSphereSize = (typeof options.maxSphereSize === 'undefined' ? 5 : options.maxSphereSize) | 0;
  	var type = (typeof options.type === 'undefined' ? 0 : options.type) | 0;
  	return @com.actelion.research.chem.contrib.HoseCodeCreator::getHoseCodes(Lcom/actelion/research/chem/StereoMolecule;II)(this.@com.actelion.research.gwt.minimal.JSMolecule::oclMolecule, maxSphereSize, type);
  }-*/;

  /**
   * @return a RingCollection object, which contains a total set of small rings
   */
  public JSRingCollection getRingSet() {
    return new JSRingCollection(oclMolecule.getRingSet());
  }

  public JavaScriptObject getBounds() {
    Rectangle2D.Double r = oclMolecule.getBounds(rectangle);
    if (r == null)
      return null;
    return getBounds(r.x, r.y, r.width, r.height);
  }

  private native JavaScriptObject getBounds(double x, double y, double width, double height)
  /*-{
  	return { x: x, y: y, width: width, height: height };
  }-*/;

  private void addImplicitHydrogens() {
    HydrogenHandler.addImplicitHydrogens(oclMolecule);
  }

  private void addImplicitHydrogens(int atomNumber) {
    HydrogenHandler.addImplicitHydrogens(oclMolecule, atomNumber);
  }

  @JsIgnore
  public static JSMolecule fromSmiles(String smiles, boolean ensure2DCoordinates, boolean readStereoFeatures)
      throws Exception {
    JSMolecule mol = new JSMolecule();
    new SmilesParser().parse(mol.oclMolecule, smiles.getBytes(), false, readStereoFeatures);
    if (ensure2DCoordinates) {
      mol.inventCoordinates();
    }
    return mol;
  }

  @JsIgnore
  public static JSMolecule fromIDCode(String idcode, boolean ensure2DCoordinates) {
    return new JSMolecule(new IDCodeParser(ensure2DCoordinates).getCompactMolecule(idcode));
  }

  @JsIgnore
  public static JSMolecule fromIDCode(String idcode, String coordinates) {
    return new JSMolecule(new IDCodeParser(false).getCompactMolecule(idcode, coordinates));
  }

  @JsIgnore
  public StereoMolecule getStereoMolecule() {
    return oclMolecule;
  }

  public static final int CANONIZER_CREATE_SYMMETRY_RANK = 1;
  public static final int CANONIZER_CONSIDER_DIASTEREOTOPICITY = 2;
  public static final int CANONIZER_CONSIDER_ENANTIOTOPICITY = 4;
  public static final int CANONIZER_CONSIDER_STEREOHETEROTOPICITY = CANONIZER_CONSIDER_DIASTEREOTOPICITY
      | CANONIZER_CONSIDER_ENANTIOTOPICITY;
  public static final int CANONIZER_ENCODE_ATOM_CUSTOM_LABELS = 8;
  public static final int CANONIZER_ENCODE_ATOM_SELECTION = 16;
  public static final int CANONIZER_ASSIGN_PARITIES_TO_TETRAHEDRAL_N = 32;
  public static final int CANONIZER_COORDS_ARE_3D = 64;
  public static final int CANONIZER_CREATE_PSEUDO_STEREO_GROUPS = 128;
  public static final int CANONIZER_DISTINGUISH_RACEMIC_OR_GROUPS = 256;

  public static final int cMaxAtomicNo = 190;
  public static final int cAtomParityNone = 0x000000;
  public static final int cAtomParity1 = 0x000001;
  public static final int cAtomParity2 = 0x000002;
  public static final int cAtomParityUnknown = 0x000003;
  public static final int cAtomParityIsPseudo = 0x000004;
  public static final int cAtomRadicalState = 0x000030;
  public static final int cAtomRadicalStateShift = 4;
  public static final int cAtomRadicalStateNone = 0x000000;
  public static final int cAtomRadicalStateS = 0x000010;
  public static final int cAtomRadicalStateD = 0x000020;
  public static final int cAtomRadicalStateT = 0x000030;
  public static final int cAtomColorNone = 0x000000;
  public static final int cAtomColorBlue = 0x000040;
  public static final int cAtomColorRed = 0x000080;
  public static final int cAtomColorGreen = 0x0000C0;
  public static final int cAtomColorMagenta = 0x000100;
  public static final int cAtomColorOrange = 0x000140;
  public static final int cAtomColorDarkGreen = 0x000180;
  public static final int cAtomColorDarkRed = 0x0001C0;
  public static final int cAtomCIPParityNone = 0x000000;
  public static final int cAtomCIPParityRorM = 0x000001;
  public static final int cAtomCIPParitySorP = 0x000002;
  public static final int cAtomCIPParityProblem = 0x000003;
  public static final int cESRTypeAbs = 0;
  public static final int cESRTypeAnd = 1;
  public static final int cESRTypeOr = 2;
  public static final int cESRMaxGroups = 32;
  public static final int cESRGroupBits = 5;
  public static final int cAtomQFNoOfBits = 30;
  public static final int cAtomQFAromStateBits = 2;
  public static final int cAtomQFAromStateShift = 1;
  public static final int cAtomQFRingStateBits = 4;
  public static final int cAtomQFRingStateShift = 3;
  public static final int cAtomQFHydrogenBits = 4;
  public static final int cAtomQFHydrogenShift = 7;
  public static final int cAtomQFPiElectronBits = 3;
  public static final int cAtomQFPiElectronShift = 14;
  public static final int cAtomQFNeighbourBits = 5;
  public static final int cAtomQFNeighbourShift = 17;
  public static final int cAtomQFRingSizeBits = 3;
  public static final int cAtomQFRingSizeShift = 22;
  public static final int cAtomQFChargeBits = 3;
  public static final int cAtomQFChargeShift = 25;
  public static final int cAtomQFRxnParityBits	= 2;
	public static final int cAtomQFRxnParityShift	= 30;
  public static final int cAtomQFSimpleFeatures = 0x0E3FC7FE;
  public static final int cAtomQFNarrowing = 0x0E3FC7FE;
  public static final int cAtomQFAny = 0x00000001;
  public static final int cAtomQFAromState = 0x00000006;
  public static final int cAtomQFAromatic = 0x00000002;
  public static final int cAtomQFNotAromatic = 0x00000004;
  public static final int cAtomQFRingState = 0x00000078;
  public static final int cAtomQFNotChain = 0x00000008;
  public static final int cAtomQFNot2RingBonds = 0x00000010;
  public static final int cAtomQFNot3RingBonds = 0x00000020;
  public static final int cAtomQFNot4RingBonds = 0x00000040;
  public static final int cAtomQFHydrogen = 0x00000780;
  public static final int cAtomQFNot0Hydrogen = 0x00000080;
  public static final int cAtomQFNot1Hydrogen = 0x00000100;
  public static final int cAtomQFNot2Hydrogen = 0x00000200;
  public static final int cAtomQFNot3Hydrogen = 0x00000400;
  public static final int cAtomQFNoMoreNeighbours = 0x00000800;
  public static final int cAtomQFMoreNeighbours = 0x00001000;
  public static final int cAtomQFMatchStereo = 0x00002000;
  public static final int cAtomQFPiElectrons = 0x0001C000;
  public static final int cAtomQFNot0PiElectrons = 0x00004000;
  public static final int cAtomQFNot1PiElectron = 0x00008000;
  public static final int cAtomQFNot2PiElectrons = 0x00010000;
  public static final int cAtomQFNeighbours = 0x003E0000; // these QF refer to non-H neighbours
  public static final int cAtomQFNot0Neighbours = 0x00020000;
  public static final int cAtomQFNot1Neighbour = 0x00040000;
  public static final int cAtomQFNot2Neighbours = 0x00080000;
  public static final int cAtomQFNot3Neighbours = 0x00100000;
  public static final int cAtomQFNot4Neighbours = 0x00200000; // this is not 4 or more neighbours
  public static final int cAtomQFRingSize = 0x01C00000;
  public static final int cAtomQFCharge = 0x0E000000;
  public static final int cAtomQFNotChargeNeg = 0x02000000;
  public static final int cAtomQFNotCharge0 = 0x04000000;
  public static final int cAtomQFNotChargePos = 0x08000000;
  public static final int cAtomQFFlatNitrogen = 0x10000000; // currently only used in TorsionDetail
  public static final int cAtomQFExcludeGroup = 0x20000000; // these atoms must not exist in SS-matches
  public static final int cAtomQFRxnParityHint    = 0xC0000000;  // Retain,invert,racemise configuration in reaction
	public static final int cAtomQFRxnParityRetain  = 0x40000000;  // Retain,invert,racemise configuration in reaction
	public static final int cAtomQFRxnParityInvert  = 0x80000000;  // Retain,invert,racemise configuration in reaction
	public static final int cAtomQFRxnParityRacemize= 0xC0000000;  // Retain,invert,racemise configuration in reaction
  public static final int cBondTypeSingle = 0x00000001;
  public static final int cBondTypeDouble = 0x00000002;
  public static final int cBondTypeTriple = 0x00000004;
  public static final int cBondTypeDown = 0x00000009;
  public static final int cBondTypeUp = 0x00000011;
  public static final int cBondTypeCross = 0x0000001A;
  public static final int cBondTypeMetalLigand = 0x00000020;
  public static final int cBondTypeDelocalized = 0x00000040;
  public static final int cBondTypeDeleted = 0x00000080;
  public static final int cBondTypeIncreaseOrder = 0x0000007F;
  public static final int cBondParityNone = 0x00000000;
  public static final int cBondParityEor1 = 0x00000001;
  public static final int cBondParityZor2 = 0x00000002;
  public static final int cBondParityUnknown = 0x00000003;
  public static final int cBondCIPParityNone = 0x00000000;
  public static final int cBondCIPParityEorP = 0x00000001;
  public static final int cBondCIPParityZorM = 0x00000002;
  public static final int cBondCIPParityProblem = 0x00000003;
  public static final int cBondQFNoOfBits = 21;
  public static final int cBondQFBondTypesBits = 5;
  public static final int cBondQFBondTypesShift = 0;
  public static final int cBondQFRingStateBits = 2;
  public static final int cBondQFRingStateShift = 5;
  public static final int cBondQFBridgeBits = 8;
  public static final int cBondQFBridgeShift = 7;
  public static final int cBondQFBridgeMinBits = 4;
  public static final int cBondQFBridgeMinShift = 7;
  public static final int cBondQFBridgeSpanBits = 4;
  public static final int cBondQFBridgeSpanShift = 11;
  public static final int cBondQFRingSizeBits = 3;
  public static final int cBondQFRingSizeShift = 15;
  public static final int cBondQFAromStateBits = 2;
  public static final int cBondQFAromStateShift = 19;
  public static final int cBondQFAllFeatures = 0x001FFFFF;
  public static final int cBondQFSimpleFeatures = 0x0018007F;
  public static final int cBondQFNarrowing = 0x00180060;
  public static final int cBondQFBondTypes = 0x0000001F;
  public static final int cBondQFSingle = 0x00000001;
  public static final int cBondQFDouble = 0x00000002;
  public static final int cBondQFTriple = 0x00000004;
  public static final int cBondQFDelocalized = 0x00000008;
  public static final int cBondQFMetalLigand = 0x00000010;
  public static final int cBondQFRingState = 0x00000060;
  public static final int cBondQFNotRing = 0x00000020;
  public static final int cBondQFRing = 0x00000040;
  public static final int cBondQFBridge = 0x00007F80;
  public static final int cBondQFBridgeMin = 0x00000780;
  public static final int cBondQFBridgeSpan = 0x00007800;
  public static final int cBondQFRingSize = 0x00038000;
  public static final int cBondQFMatchStereo = 0x00040000;
  public static final int cBondQFAromState = 0x00180000;
  public static final int cBondQFAromatic = 0x00080000;
  public static final int cBondQFNotAromatic = 0x00100000;
  public static final int cHelperNone = 0x0000;
  public static final int cHelperBitNeighbours = 0x0001;
  public static final int cHelperBitRings = 0x0002;
  public static final int cHelperBitParities = 0x0004;
  public static final int cHelperBitCIP = 0x0008;
  public static final int cHelperBitSymmetrySimple = 0x0010;
  public static final int cHelperBitSymmetryDiastereotopic = 0x0020;
  public static final int cHelperBitSymmetryEnantiotopic = 0x0040;
  public static final int cHelperBitIncludeNitrogenParities = 0x0080;
  public static final int cHelperBitsStereo = 0x00FC;
  public static final int cHelperNeighbours = cHelperBitNeighbours;
  public static final int cHelperRings = cHelperNeighbours | cHelperBitRings;
  public static final int cHelperParities = cHelperRings | cHelperBitParities;
  public static final int cHelperCIP = cHelperParities | cHelperBitCIP;
  public static final int cHelperSymmetrySimple = cHelperCIP | cHelperBitSymmetrySimple;
  public static final int cHelperSymmetryDiastereotopic = cHelperCIP | cHelperBitSymmetryDiastereotopic;
  public static final int cHelperSymmetryEnantiotopic = cHelperCIP | cHelperBitSymmetryEnantiotopic;
  public static final int cChiralityIsomerCountMask = 0x00FFFF;
  public static final int cChiralityUnknown = 0x000000;
  public static final int cChiralityNotChiral = 0x010000;
  public static final int cChiralityMeso = 0x020000; // this has added the number of meso isomers
  public static final int cChiralityRacemic = 0x030000;
  public static final int cChiralityKnownEnantiomer = 0x040000;
  public static final int cChiralityUnknownEnantiomer = 0x050000;
  public static final int cChiralityEpimers = 0x060000;
  public static final int cChiralityDiastereomers = 0x070000; // this has added the number of diastereomers
  public static final int cMoleculeColorDefault = 0;
  public static final int cMoleculeColorNeutral = 1;
  public static final String cAtomLabel[] = { "?", "H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na", "Mg",
      "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga",
      "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn",
      "Sb", "Te", "I", "Xe", "Cs", "Ba", "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm",
      "Yb", "Lu", "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn", "Fr", "Ra",
      "Ac", "Th", "Pa", "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md", "No", "Lr", "??", "??", "??", "??",
      "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??",
      "??", "??", "R4", "R5", "R6", "R7", "R8", "R9", "R10", "R11", // R4 to R16 do not belong to the MDL set
      "R12", "R13", "R14", "R15", "R16", "R1", "R2", "R3", "A", "A1", "A2", "A3", "??", "??", "D", "T", "X", "R", "H2",
      "H+", "Nnn", "HYD", "Pol", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "??", "Ala", "Arg", "Asn",
      "Asp", "Cys", "Gln", "Glu", "Gly", "His", "Ile", "Leu", "Lys", "Met", "Phe", "Pro", "Ser", "Thr", "Trp", "Tyr",
      "Val" };
  public static final short cRoundedMass[] = { 0, 1, 4, 7, 9, 11, 12, // H ,He ,Li ,Be ,B ,C ,
      14, 16, 19, 20, 23, 24, // N , O ,F ,Ne ,Na ,Mg ,
      27, 28, 31, 32, 35, 40, // Al ,Si ,P ,S ,Cl ,Ar ,
      39, 40, 45, 48, 51, 52, // K ,Ca ,Sc ,Ti ,V ,Cr ,
      55, 56, 59, 58, 63, 64, // Mn ,Fe ,Co ,Ni ,Cu ,Zn ,
      69, 74, 75, 80, 79, 84, // Ga ,Ge ,As ,Se ,Br ,Kr ,
      85, 88, 89, 90, 93, 98, // Rb ,Sr ,Y ,Zr ,Nb ,Mo ,
      0, 102, 103, 106, 107, 114, // Tc ,Ru ,Rh ,Pd ,Ag ,Cd ,
      115, 120, 121, 130, 127, 132, // In ,Sn ,Sb ,Te ,I ,Xe ,
      133, 138, 139, 140, 141, 142, // Cs ,Ba ,La ,Ce ,Pr ,Nd ,
      0, 152, 153, 158, 159, 164, // Pm ,Sm ,Eu ,Gd ,Tb ,Dy ,
      165, 166, 169, 174, 175, 180, // Ho ,Er ,Tm ,Yb ,Lu ,Hf ,
      181, 184, 187, 192, 193, 195, // Ta ,W , Re ,Os ,Ir ,Pt ,
      197, 202, 205, 208, 209, 0, // Au ,Hg ,Tl ,Pb ,Bi ,Po ,
      0, 0, 0, 0, 0, 232, // At ,Rn ,Fr ,Ra ,Ac ,Th ,
      0, 238, 0, 0, 0, 0, // Pa ,U , Np ,Pu ,Am ,Cm ,
      0, 0, 0, 0, 0, 0, // Bk ,Cf ,Es ,Fm ,Md ,No ,
      0, 0, 0, 0, 0, 0, // Lr ,?? ,?? ,?? ,?? ,?? ,
      0, 0, 0, 0, 0, 0, // ?? ,?? ,?? ,?? ,?? ,?? ,
      0, 0, 0, 0, 0, 0, // ?? ,?? ,?? ,?? ,?? ,?? ,
      0, 0, 0, 0, 0, 0, // ?? ,?? ,?? ,?? ,?? ,?? ,
      0, 0, 0, 0, 0, 0, // ?? ,?? ,R4 ,R5 ,R6 ,R7 ,
      0, 0, 0, 0, 0, 0, // R8 ,R9 ,R10,R11,R12,R13,
      0, 0, 0, 0, 0, 0, // R14,R15,R16,R1 ,R2 ,R3 ,
      0, 0, 0, 0, 0, 0, // A ,A1 ,A2 ,A3 ,?? ,?? ,
      2, 3, 0, 0, 0, 0, // D ,T ,X ,R ,H2 ,H+
      0, 0, 0, 0, 0, 0, // Nnn,HYD,Pol,?? ,?? ,?? ,
      0, 0, 0, 0, 0, 0, // ?? ,?? ,?? ,?? ,?? ,?? ,
      0, 0, 71, 156, 114, 115, // ?? ,?? ,Ala,Arg,Asn,Asp,
      103, 128, 129, 57, 137, 113, // Cys,Gln,Glu,Gly,His,Ile,
      113, 128, 131, 147, 97, 87, // Leu,Lys,Met,Phe,Pro,Ser,
      101, 186, 163, 99 }; // Thr,Trp,Tyr,Val,
  public static final int cDefaultAtomValence = 6;
  public static final float FISCHER_PROJECTION_LIMIT = (float) Math.PI / 36;
  public static final float STEREO_ANGLE_LIMIT = (float) Math.PI / 36; // 5 degrees
  public static final int cMaxConnAtoms = 16; // ExtendedMolecule is not restricted anymore
  public static final String VALIDATION_ERROR_ESR_CENTER_UNKNOWN = "Members of ESR groups must only be stereo centers with known configuration.";
  public static final String VALIDATION_ERROR_OVER_UNDER_SPECIFIED = "Over- or under-specified stereo feature or more than one racemic type bond";
  public static final String VALIDATION_ERROR_AMBIGUOUS_CONFIGURATION = "Ambiguous configuration at stereo center because of 2 parallel bonds";
  public static final String[] VALIDATION_ERRORS_STEREO = { VALIDATION_ERROR_ESR_CENTER_UNKNOWN,
      VALIDATION_ERROR_OVER_UNDER_SPECIFIED, VALIDATION_ERROR_AMBIGUOUS_CONFIGURATION };

  public static int getAtomicNoFromLabel(String atomLabel) {
    return StereoMolecule.getAtomicNoFromLabel(atomLabel);
  }

  public static double getAngle(double x1, double y1, double x2, double y2) {
    return StereoMolecule.getAngle(x1, y1, x2, y2);
  }

  public static double getAngleDif(double angle1, double angle2) {
    return StereoMolecule.getAngleDif(angle1, angle2);
  }

  public int addAtom(int atomicNo) {
    return oclMolecule.addAtom(atomicNo);
  }

  public int suggestBondType(int atom1, int atom2) {
    return oclMolecule.suggestBondType(atom1, atom2);
  }

  public int addBond(int atom1, int atom2) {
    return oclMolecule.addBond(atom1, atom2);
  }

  public boolean addOrChangeAtom(double x, double y, int atomicNo, int mass, int abnormalValence, int radical,
      String customLabel) {
    return oclMolecule.addOrChangeAtom(x, y, atomicNo, mass, abnormalValence, radical, customLabel);
  }

  public int addOrChangeBond(int atm1, int atm2, int type) {
    return oclMolecule.addOrChangeBond(atm1, atm2, type);
  }

  public boolean addRing(double x, double y, int ringSize, boolean aromatic) {
    return oclMolecule.addRing(x, y, ringSize, aromatic);
  }

  public boolean addRingToAtom(int atom, int ringSize, boolean aromatic) {
    return oclMolecule.addRingToAtom(atom, ringSize, aromatic);
  }

  public boolean addRingToBond(int bond, int ringSize, boolean aromatic) {
    return oclMolecule.addRingToBond(bond, ringSize, aromatic);
  }

  public boolean changeAtom(int atom, int atomicNo, int mass, int abnormalValence, int radical) {
    return oclMolecule.changeAtom(atom, atomicNo, mass, abnormalValence, radical);
  }

  public boolean changeAtomCharge(int atom, boolean positive) {
    return oclMolecule.changeAtomCharge(atom, positive);
  }

  public boolean changeBond(int bnd, int type) {
    return oclMolecule.changeBond(bnd, type);
  }

  public int[] addMolecule(JSMolecule mol) {
    return oclMolecule.addMolecule(mol.getStereoMolecule());
  }

  public int[] addSubstituent(JSMolecule substituent, int connectionAtom) {
    return oclMolecule.addSubstituent(substituent.getStereoMolecule(), connectionAtom);
  }

  public void copyMolecule(JSMolecule destMol) {
    oclMolecule.copyMolecule(destMol.getStereoMolecule());
  }

  public int copyAtom(JSMolecule destMol, int sourceAtom, int esrGroupOffsetAND, int esrGroupOffsetOR) {
    return oclMolecule.copyAtom(destMol.getStereoMolecule(), sourceAtom, esrGroupOffsetAND, esrGroupOffsetOR);
  }

  public int copyBond(JSMolecule destMol, int sourceBond, int esrGroupOffsetAND, int esrGroupOffsetOR, int[] atomMap,
      boolean useBondTypeDelocalized) {
    return oclMolecule.copyBond(destMol.getStereoMolecule(), sourceBond, esrGroupOffsetAND, esrGroupOffsetOR, atomMap,
        useBondTypeDelocalized);
  }

  public void copyMoleculeProperties(JSMolecule destMol) {
    oclMolecule.copyMoleculeProperties(destMol.getStereoMolecule());
  }

  public void invalidateHelperArrays(int helperBits) {
    oclMolecule.invalidateHelperArrays(helperBits);
  }

  public int renumberESRGroups(int type) {
    return oclMolecule.renumberESRGroups(type);
  }

  public void swapAtoms(int atom1, int atom2) {
    oclMolecule.swapAtoms(atom1, atom2);
  }

  public void swapBonds(int bond1, int bond2) {
    oclMolecule.swapBonds(bond1, bond2);
  }

  public void deleteAtom(int atom) {
    oclMolecule.deleteAtom(atom);
  }

  public boolean deleteAtomOrBond(double x, double y) {
    return oclMolecule.deleteAtomOrBond(x, y);
  }

  public void deleteBond(int bond) {
    oclMolecule.deleteBond(bond);
  }

  public void deleteBondAndSurrounding(int bond) {
    oclMolecule.deleteBondAndSurrounding(bond);
  }

  public int[] deleteAtoms(int[] atomList) {
    return oclMolecule.deleteAtoms(atomList);
  }

  public boolean deleteSelectedAtoms() {
    return oclMolecule.deleteSelectedAtoms();
  }

  public void markAtomForDeletion(int atom) {
    oclMolecule.markAtomForDeletion(atom);
  }

  public void markBondForDeletion(int bond) {
    oclMolecule.markBondForDeletion(bond);
  }

  public boolean isAtomMarkedForDeletion(int atom) {
    return oclMolecule.isAtomMarkedForDeletion(atom);
  }

  public boolean isBondMarkedForDeletion(int bond) {
    return oclMolecule.isBondMarkedForDeletion(bond);
  }

  public int[] deleteMarkedAtomsAndBonds() {
    return oclMolecule.deleteMarkedAtomsAndBonds();
  }

  public void deleteMolecule() {
    oclMolecule.clear();
  }

  public void clear() {
    oclMolecule.clear();
  }

  public void removeAtomSelection() {
    oclMolecule.removeAtomSelection();
  }

  public void removeAtomColors() {
    oclMolecule.removeAtomColors();
  }

  public void removeAtomCustomLabels() {
    oclMolecule.removeAtomCustomLabels();
  }

  public void removeAtomMarkers() {
    oclMolecule.removeAtomMarkers();
  }

  public void removeBondHiliting() {
    oclMolecule.removeBondHiliting();
  }

  public int findAtom(double pickx, double picky) {
    return oclMolecule.findAtom(pickx, picky);
  }

  public int findBond(double pickx, double picky) {
    return oclMolecule.findBond(pickx, picky);
  }

  public int getAllAtoms() {
    return oclMolecule.getAllAtoms();
  }

  public int getAllBonds() {
    return oclMolecule.getAllBonds();
  }

  public int getAtomAbnormalValence(int atom) {
    return oclMolecule.getAtomAbnormalValence(atom);
  }

  public int getAtomCharge(int atom) {
    return oclMolecule.getAtomCharge(atom);
  }

  public int getAtomCIPParity(int atom) {
    return oclMolecule.getAtomCIPParity(atom);
  }

  public int getAtomColor(int atom) {
    return oclMolecule.getAtomColor(atom);
  }

  public int getAtomESRGroup(int atom) {
    return oclMolecule.getAtomESRGroup(atom);
  }

  public int getAtomESRType(int atom) {
    return oclMolecule.getAtomESRType(atom);
  }

  public int getAtomicNo(int atom) {
    return oclMolecule.getAtomicNo(atom);
  }

  public String getAtomCustomLabel(int atom) {
    return oclMolecule.getAtomCustomLabel(atom);
  }

  public String getAtomLabel(int atom) {
    return oclMolecule.getAtomLabel(atom);
  }

  public int[] getAtomList(int atom) {
    return oclMolecule.getAtomList(atom);
  }

  public String getAtomListString(int atom) {
    return oclMolecule.getAtomListString(atom);
  }

  public int getAtomMapNo(int atom) {
    return oclMolecule.getAtomMapNo(atom);
  }

  public int getAtomMass(int atom) {
    return oclMolecule.getAtomMass(atom);
  }

  public int getAtomParity(int atom) {
    return oclMolecule.getAtomParity(atom);
  }

  public int getAtomQueryFeatures(int atom) {
    return oclMolecule.getAtomQueryFeatures(atom);
  }

  public int getAtomRadical(int atom) {
    return oclMolecule.getAtomRadical(atom);
  }

  public double getAtomX(int atom) {
    return oclMolecule.getAtomX(atom);
  }

  public double getAtomY(int atom) {
    return oclMolecule.getAtomY(atom);
  }

  public double getAtomZ(int atom) {
    return oclMolecule.getAtomZ(atom);
  }

  public static double getDefaultAverageBondLength() {
    return StereoMolecule.getDefaultAverageBondLength();
  }

  public static void setDefaultAverageBondLength(double defaultAVBL) {
    StereoMolecule.setDefaultAverageBondLength(defaultAVBL);
  }

  public double getBondAngle(int atom1, int atom2) {
    return oclMolecule.getBondAngle(atom1, atom2);
  }

  public double calculateTorsion(int[] atom) {
    return oclMolecule.calculateTorsion(atom);
  }

  public int getBondAtom(int no, int bond) {
    return oclMolecule.getBondAtom(no, bond);
  }

  public int getBondCIPParity(int bond) {
    return oclMolecule.getBondCIPParity(bond);
  }

  public int getBondESRGroup(int bond) {
    return oclMolecule.getBondESRGroup(bond);
  }

  public int getBondESRType(int bond) {
    return oclMolecule.getBondESRType(bond);
  }

  public double getBondLength(int bond) {
    return oclMolecule.getBondLength(bond);
  }

  public int getBondOrder(int bond) {
    return oclMolecule.getBondOrder(bond);
  }

  public int getBondParity(int bnd) {
    return oclMolecule.getBondParity(bnd);
  }

  public int getBondQueryFeatures(int bnd) {
    return oclMolecule.getBondQueryFeatures(bnd);
  }

  public boolean isBondBridge(int bond) {
    return oclMolecule.isBondBridge(bond);
  }

  public int getBondBridgeMinSize(int bond) {
    return oclMolecule.getBondBridgeMinSize(bond);
  }

  public int getBondBridgeMaxSize(int bond) {
    return oclMolecule.getBondBridgeMaxSize(bond);
  }

  public int getBondType(int bond) {
    return oclMolecule.getBondType(bond);
  }

  public int getBondTypeSimple(int bond) {
    return oclMolecule.getBondTypeSimple(bond);
  }

  public int getChirality() {
    return oclMolecule.getChirality();
  }

  public int getMaxAtoms() {
    return oclMolecule.getMaxAtoms();
  }

  public void setMaxAtoms(int v) {
    oclMolecule.setMaxAtoms(v);
  }

  public int getMaxBonds() {
    return oclMolecule.getMaxBonds();
  }

  public void setMaxBonds(int v) {
    oclMolecule.setMaxBonds(v);
  }

  public int getMoleculeColor() {
    return oclMolecule.getMoleculeColor();
  }

  public void setMoleculeColor(int color) {
    oclMolecule.setMoleculeColor(color);
  }

  public String getName() {
    return oclMolecule.getName();
  }

  public boolean getStereoProblem(int atom) {
    return oclMolecule.getStereoProblem(atom);
  }

  public boolean isAtomConfigurationUnknown(int atom) {
    return oclMolecule.isAtomConfigurationUnknown(atom);
  }

  public boolean isAtomParityPseudo(int atom) {
    return oclMolecule.isAtomParityPseudo(atom);
  }

  public boolean isAtomStereoCenter(int atom) {
    return oclMolecule.isAtomStereoCenter(atom);
  }

  public boolean isBondParityPseudo(int bond) {
    return oclMolecule.isBondParityPseudo(bond);
  }

  public boolean isBondParityUnknownOrNone(int bond) {
    return oclMolecule.isBondParityUnknownOrNone(bond);
  }

  public boolean isFragment() {
    return oclMolecule.isFragment();
  }

  public boolean is3D() {
    return oclMolecule.is3D();
  }

  public boolean isNaturalAbundance(int atom) {
    return oclMolecule.isNaturalAbundance(atom);
  }

  public boolean isPurelyOrganic() {
    return oclMolecule.isPurelyOrganic();
  }

  public boolean isSelectedAtom(int atom) {
    return oclMolecule.isSelectedAtom(atom);
  }

  public boolean isMarkedAtom(int atom) {
    return oclMolecule.isMarkedAtom(atom);
  }

  public boolean isBondBackgroundHilited(int bond) {
    return oclMolecule.isBondBackgroundHilited(bond);
  }

  public boolean isBondForegroundHilited(int bond) {
    return oclMolecule.isBondForegroundHilited(bond);
  }

  public boolean isSelectedBond(int bond) {
    return oclMolecule.isSelectedBond(bond);
  }

  public boolean isAutoMappedAtom(int atom) {
    return oclMolecule.isAutoMappedAtom(atom);
  }

  public boolean isStereoBond(int bond) {
    return oclMolecule.isStereoBond(bond);
  }

  public void setAllAtoms(int no) {
    oclMolecule.setAllAtoms(no);
  }

  public void setAllBonds(int no) {
    oclMolecule.setAllBonds(no);
  }

  public void setAtomAbnormalValence(int atom, int valence) {
    oclMolecule.setAtomAbnormalValence(atom, valence);
  }

  public void setAtomCharge(int atom, int charge) {
    oclMolecule.setAtomCharge(atom, charge);
  }

  public void setAtomColor(int atom, int color) {
    oclMolecule.setAtomColor(atom, color);
  }

  public void setAtomConfigurationUnknown(int atom, boolean u) {
    oclMolecule.setAtomConfigurationUnknown(atom, u);
  }

  public void setAtomSelection(int atom, boolean s) {
    oclMolecule.setAtomSelection(atom, s);
  }

  public void setAtomMarker(int atom, boolean s) {
    oclMolecule.setAtomMarker(atom, s);
  }

  public void setAtomicNo(int atom, int no) {
    oclMolecule.setAtomicNo(atom, no);
  }

  public void setAtomList(int atom, int[] list, boolean isExcludeList) {
    oclMolecule.setAtomList(atom, list, isExcludeList);
  }

  public void setAtomMapNo(int atom, int mapNo, boolean autoMapped) {
    oclMolecule.setAtomMapNo(atom, mapNo, autoMapped);
  }

  public void setAtomMass(int atom, int mass) {
    oclMolecule.setAtomMass(atom, mass);
  }

  public void setAtomParity(int atom, int parity, boolean isPseudo) {
    oclMolecule.setAtomParity(atom, parity, isPseudo);
  }

  public void setAtomQueryFeature(int atom, int feature, boolean value) {
    oclMolecule.setAtomQueryFeature(atom, feature, value);
  }

  public void setAtomRadical(int atom, int radical) {
    oclMolecule.setAtomRadical(atom, radical);
  }

  public void setAtomCIPParity(int atom, int parity) {
    oclMolecule.setAtomCIPParity(atom, parity);
  }

  public void setAtomX(int atom, double x) {
    oclMolecule.setAtomX(atom, x);
  }

  public void setAtomY(int atom, double y) {
    oclMolecule.setAtomY(atom, y);
  }

  public void setAtomZ(int atom, double z) {
    oclMolecule.setAtomZ(atom, z);
  }

  public void setBondAtom(int no, int bond, int atom) {
    oclMolecule.setBondAtom(no, bond, atom);
  }

  public void setBondCIPParity(int bond, int parity) {
    oclMolecule.setBondCIPParity(bond, parity);
  }

  public void setBondBackgroundHiliting(int bond, boolean s) {
    oclMolecule.setBondBackgroundHiliting(bond, s);
  }

  public void setBondForegroundHiliting(int bond, boolean s) {
    oclMolecule.setBondForegroundHiliting(bond, s);
  }

  public void setBondParity(int bond, int parity, boolean isPseudo) {
    oclMolecule.setBondParity(bond, parity, isPseudo);
  }

  public void setBondParityUnknownOrNone(int bond) {
    oclMolecule.setBondParityUnknownOrNone(bond);
  }

  public void setBondQueryFeature(int bond, int feature, boolean value) {
    oclMolecule.setBondQueryFeature(bond, feature, value);
  }

  public void setBondOrder(int bond, int order) {
    oclMolecule.setBondOrder(bond, order);
  }

  public void setBondType(int bond, int type) {
    oclMolecule.setBondType(bond, type);
  }

  public void setChirality(int c) {
    oclMolecule.setChirality(c);
  }

  public void setHydrogenProtection(boolean protectHydrogen) {
    oclMolecule.setHydrogenProtection(protectHydrogen);
  }

  public void setHelperValidity(int helperValidity) {
    oclMolecule.setHelperValidity(helperValidity);
  }

  public void setToRacemate() {
    oclMolecule.setToRacemate();
  }

  public void setAtomCustomLabel(int atom, String label) {
    oclMolecule.setAtomCustomLabel(atom, label);
  }

  public void setAtomESR(int atom, int type, int group) {
    oclMolecule.setAtomESR(atom, type, group);
  }

  public void setBondESR(int bond, int type, int group) {
    oclMolecule.setBondESR(bond, type, group);
  }

  public void setFragment(boolean isFragment) {
    oclMolecule.setFragment(isFragment);
  }

  public void setName(String name) {
    oclMolecule.setName(name);
  }

  public boolean removeQueryFeatures() {
    return oclMolecule.removeQueryFeatures();
  }

  public boolean stripIsotopInfo() {
    return oclMolecule.stripIsotopInfo();
  }

  public void translateCoords(double dx, double dy) {
    oclMolecule.translateCoords(dx, dy);
  }

  public void scaleCoords(double f) {
    oclMolecule.scaleCoords(f);
  }

  public void zoomAndRotateInit(double x, double y) {
    oclMolecule.zoomAndRotateInit(x, y);
  }

  public void zoomAndRotate(double zoom, double angle, boolean selected) {
    oclMolecule.zoomAndRotate(zoom, angle, selected);
  }

  public int getMaxValenceUncharged(int atom) {
    return oclMolecule.getMaxValenceUncharged(atom);
  }

  public int getDefaultMaxValenceUncharged(int atom) {
    return oclMolecule.getDefaultMaxValenceUncharged(atom);
  }

  public int getMaxValence(int atom) {
    return oclMolecule.getMaxValence(atom);
  }

  public int getElectronValenceCorrection(int atom, int occupiedValence) {
    return oclMolecule.getElectronValenceCorrection(atom, occupiedValence);
  }

  public static boolean isAtomicNoElectronegative(int atomicNo) {
    return StereoMolecule.isAtomicNoElectronegative(atomicNo);
  }

  public boolean isElectronegative(int atom) {
    return oclMolecule.isElectronegative(atom);
  }

  public static boolean isAtomicNoElectropositive(int atomicNo) {
    return StereoMolecule.isAtomicNoElectropositive(atomicNo);
  }

  public boolean isElectropositive(int atom) {
    return oclMolecule.isElectropositive(atom);
  }

  public boolean isMetalAtom(int atom) {
    return oclMolecule.isMetalAtom(atom);
  }

  public boolean isOrganicAtom(int atom) {
    return oclMolecule.isOrganicAtom(atom);
  }

  public void copyMoleculeByAtoms(JSMolecule destMol, boolean[] includeAtom, boolean recognizeDelocalizedBonds,
      int[] atomMap) {
    oclMolecule.copyMoleculeByAtoms(destMol.getStereoMolecule(), includeAtom, recognizeDelocalizedBonds, atomMap);
  }

  public int[] copyMoleculeByBonds(JSMolecule destMol, boolean[] includeBond, boolean recognizeDelocalizedBonds,
      int[] atomMap) {
    return oclMolecule.copyMoleculeByBonds(destMol.getStereoMolecule(), includeBond, recognizeDelocalizedBonds,
        atomMap);
  }

  public int getAllConnAtoms(int atom) {
    return oclMolecule.getAllConnAtoms(atom);
  }

  public int getAllHydrogens(int atom) {
    return oclMolecule.getAllHydrogens(atom);
  }

  public int getAtoms() {
    return oclMolecule.getAtoms();
  }

  public int getMetalBondedConnAtoms(int atom) {
    return oclMolecule.getMetalBondedConnAtoms(atom);
  }

  public int getAtomPi(int atom) {
    return oclMolecule.getAtomPi(atom);
  }

  public int getAtomRingSize(int atom) {
    return oclMolecule.getAtomRingSize(atom);
  }

  public int getBondRingSize(int bond) {
    return oclMolecule.getBondRingSize(bond);
  }

  public int getBonds() {
    return oclMolecule.getBonds();
  }

  public int getBond(int atom1, int atom2) {
    return oclMolecule.getBond(atom1, atom2);
  }

  public JSMolecule getCompactCopy() {
    return new JSMolecule(oclMolecule.getCompactCopy());
  }

  public int getConnAtom(int atom, int i) {
    return oclMolecule.getConnAtom(atom, i);
  }

  public int getConnAtoms(int atom) {
    return oclMolecule.getConnAtoms(atom);
  }

  public int getAllConnAtomsPlusMetalBonds(int atom) {
    return oclMolecule.getAllConnAtomsPlusMetalBonds(atom);
  }

  public int getConnBond(int atom, int i) {
    return oclMolecule.getConnBond(atom, i);
  }

  public int getConnBondOrder(int atom, int i) {
    return oclMolecule.getConnBondOrder(atom, i);
  }

  public int getNonHydrogenNeighbourCount(int atom) {
    return oclMolecule.getNonHydrogenNeighbourCount(atom);
  }

  public int getExcludedNeighbourCount(int atom) {
    return oclMolecule.getExcludedNeighbourCount(atom);
  }

  public double getAverageBondLength(boolean nonHydrogenBondsOnly) {
    return oclMolecule.getAverageBondLength(nonHydrogenBondsOnly);
  }

  public int getOccupiedValence(int atom) {
    return oclMolecule.getOccupiedValence(atom);
  }

  public int getFreeValence(int atom) {
    return oclMolecule.getFreeValence(atom);
  }

  public int getLowestFreeValence(int atom) {
    return oclMolecule.getLowestFreeValence(atom);
  }

  public int getImplicitHigherValence(int atom, boolean neglectExplicitHydrogen) {
    return oclMolecule.getImplicitHigherValence(atom, neglectExplicitHydrogen);
  }

  public float[] getAverageTopologicalAtomDistance() {
    return oclMolecule.getAverageTopologicalAtomDistance();
  }

  public int getPathLength(int atom1, int atom2) {
    return oclMolecule.getPathLength(atom1, atom2);
  }

  public int getPath(int[] pathAtom, int atom1, int atom2, int maxLength, boolean[] neglectBond) {
    return oclMolecule.getPath(pathAtom, atom1, atom2, maxLength, neglectBond);
  }

  public void getPathBonds(int[] pathAtom, int[] pathBond, int pathLength) {
    oclMolecule.getPathBonds(pathAtom, pathBond, pathLength);
  }

  public boolean shareSameFragment(int atom1, int atom2) {
    return oclMolecule.shareSameFragment(atom1, atom2);
  }

  public void addFragment(JSMolecule sourceMol, int rootAtom, int[] atomMap) {
    oclMolecule.addFragment(sourceMol.getStereoMolecule(), rootAtom, atomMap);
  }

  public int[] getFragmentAtoms(int rootAtom, boolean considerMetalBonds) {
    return oclMolecule.getFragmentAtoms(rootAtom, considerMetalBonds);
  }

  public int getFragmentNumbers(int[] fragmentNo, boolean markedAtomsOnly, boolean considerMetalBonds) {
    return oclMolecule.getFragmentNumbers(fragmentNo, markedAtomsOnly, considerMetalBonds);
  }

  public int[] stripSmallFragments(boolean considerMetalBonds) {
    return oclMolecule.stripSmallFragments(considerMetalBonds);
  }

  public void findRingSystem(int startAtom, boolean aromaticOnly, boolean[] isMemberAtom, boolean[] isMemberBond) {
    oclMolecule.findRingSystem(startAtom, aromaticOnly, isMemberAtom, isMemberBond);
  }

  public int getSubstituent(int coreAtom, int firstAtom, boolean[] isMemberAtom, JSMolecule substituent,
      int[] atomMap) {
    return oclMolecule.getSubstituent(coreAtom, firstAtom, isMemberAtom, substituent.getStereoMolecule(), atomMap);
  }

  public int getSubstituentSize(int coreAtom, int firstAtom) {
    return oclMolecule.getSubstituentSize(coreAtom, firstAtom);
  }

  public boolean supportsImplicitHydrogen(int atom) {
    return oclMolecule.supportsImplicitHydrogen(atom);
  }

  public int getImplicitHydrogens(int atom) {
    return oclMolecule.getImplicitHydrogens(atom);
  }

  public int getExplicitHydrogens(int atom) {
    return oclMolecule.getExplicitHydrogens(atom);
  }

  public int getMolweight() {
    return oclMolecule.getMolweight();
  }

  public int getRotatableBondCount() {
    return oclMolecule.getRotatableBondCount();
  }

  public boolean isPseudoRotatableBond(int bond) {
    return oclMolecule.isPseudoRotatableBond(bond);
  }

  public int getAromaticRingCount() {
    return oclMolecule.getAromaticRingCount();
  }

  public int getAtomRingCount(int atom, int maxRingSize) {
    return oclMolecule.getAtomRingCount(atom, maxRingSize);
  }

  public int getAtomPreferredStereoBond(int atom) {
    return oclMolecule.getAtomPreferredStereoBond(atom);
  }

  public int getBondPreferredStereoBond(int bond) {
    return oclMolecule.getBondPreferredStereoBond(bond);
  }

  public boolean isAllylicAtom(int atom) {
    return oclMolecule.isAllylicAtom(atom);
  }

  public boolean isAromaticAtom(int atom) {
    return oclMolecule.isAromaticAtom(atom);
  }

  public boolean isAromaticBond(int bnd) {
    return oclMolecule.isAromaticBond(bnd);
  }

  public boolean isDelocalizedBond(int bond) {
    return oclMolecule.isDelocalizedBond(bond);
  }

  public boolean isRingAtom(int atom) {
    return oclMolecule.isRingAtom(atom);
  }

  public boolean isRingBond(int bnd) {
    return oclMolecule.isRingBond(bnd);
  }

  public boolean isSmallRingAtom(int atom) {
    return oclMolecule.isSmallRingAtom(atom);
  }

  public boolean isSmallRingBond(int bond) {
    return oclMolecule.isSmallRingBond(bond);
  }

  public boolean isStabilizedAtom(int atom) {
    return oclMolecule.isStabilizedAtom(atom);
  }

  public int getAtomRingBondCount(int atom) {
    return oclMolecule.getAtomRingBondCount(atom);
  }

  public String getChiralText() {
    return oclMolecule.getChiralText();
  }

  public int getStereoBond(int atom) {
    return oclMolecule.getStereoBond(atom);
  }

  public void setParitiesValid(int helperStereoBits) {
    oclMolecule.setParitiesValid(helperStereoBits);
  }

  public void setStereoBondsFromParity() {
    oclMolecule.setStereoBondsFromParity();
  }

  public void convertStereoBondsToSingleBonds(int atom) {
    oclMolecule.convertStereoBondsToSingleBonds(atom);
  }

  public void setStereoBondFromAtomParity(int atom) {
    oclMolecule.setStereoBondFromAtomParity(atom);
  }

  public int getFisherProjectionParity(int atom, int[] sortedConnMap, double[] angle, int[] direction) {
    return oclMolecule.getFisherProjectionParity(atom, sortedConnMap, angle, direction);
  }

  public void setStereoBondFromBondParity(int bond) {
    oclMolecule.setStereoBondFromBondParity(bond);
  }

  public int findAlleneCenterAtom(int atom) {
    return oclMolecule.findAlleneCenterAtom(atom);
  }

  public int findAlleneEndAtom(int atom1, int atom2) {
    return oclMolecule.findAlleneEndAtom(atom1, atom2);
  }

  public int findBINAPChiralityBond(int atom) {
    return oclMolecule.findBINAPChiralityBond(atom);
  }

  public boolean isAmideTypeBond(int bond) {
    return oclMolecule.isAmideTypeBond(bond);
  }

  public boolean isFlatNitrogen(int atom) {
    return oclMolecule.isFlatNitrogen(atom);
  }

  public boolean isBINAPChiralityBond(int bond) {
    return oclMolecule.isBINAPChiralityBond(bond);
  }

  public void validate() throws Exception {
    oclMolecule.validate();
  }

  public boolean normalizeAmbiguousBonds() {
    return oclMolecule.normalizeAmbiguousBonds();
  }

  public boolean isAlkaliMetal(int atom) {
    return oclMolecule.isAlkaliMetal(atom);
  }

  public boolean isEarthAlkaliMetal(int atom) {
    return oclMolecule.isEarthAlkaliMetal(atom);
  }

  public boolean isNitrogenFamily(int atom) {
    return oclMolecule.isNitrogenFamily(atom);
  }

  public boolean isChalcogene(int atom) {
    return oclMolecule.isChalcogene(atom);
  }

  public boolean isHalogene(int atom) {
    return oclMolecule.isHalogene(atom);
  }

  public int canonizeCharge(boolean allowUnbalancedCharge) throws Exception {
    return oclMolecule.canonizeCharge(allowUnbalancedCharge);
  }

  public int getZNeighbour(int connAtom, int bond) {
    return oclMolecule.getZNeighbour(connAtom, bond);
  }

  public int getHelperArrayStatus() {
    return oclMolecule.getHelperArrayStatus();
  }

  public void ensureHelperArrays(int required) {
    oclMolecule.ensureHelperArrays(required);
  }

  public int[] getHandleHydrogenMap() {
    return oclMolecule.getHandleHydrogenMap();
  }

  public boolean isSimpleHydrogen(int atom) {
    return oclMolecule.isSimpleHydrogen(atom);
  }

  public void removeExplicitHydrogens(boolean is3D) {
    oclMolecule.removeExplicitHydrogens(is3D);
  }

  public JSMolecule[] getFragments() {
    StereoMolecule[] fragments = oclMolecule.getFragments();
    JSMolecule[] newFragments = new JSMolecule[fragments.length];
    for (int i = 0; i < fragments.length; i++) {
      newFragments[i] = new JSMolecule(fragments[i]);
    }
    return newFragments;

  }

  public void stripStereoInformation() {
    oclMolecule.stripStereoInformation();
  }

  public int getAbsoluteAtomParity(int atom) {
    return oclMolecule.getAbsoluteAtomParity(atom);
  }

  public int getAbsoluteBondParity(int bond) {
    return oclMolecule.getAbsoluteBondParity(bond);
  }

  public int getSymmetryRank(int atom) {
    return oclMolecule.getSymmetryRank(atom);
  }

  public String getIDCode() {
    return oclMolecule.getIDCode();
  }

  public String getIDCoordinates() {
    return oclMolecule.getIDCoordinates();
  }

  public int getStereoCenterCount() {
    return oclMolecule.getStereoCenterCount();
  }

  public void setUnknownParitiesToExplicitlyUnknown() {
    oclMolecule.setUnknownParitiesToExplicitlyUnknown();
  }

  public void setAssignParitiesToNitrogen(boolean b) {
    oclMolecule.setAssignParitiesToNitrogen(b);
  }
}

package com.actelion.research.gwt.js.api;

import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

import com.actelion.research.chem.*;

@JsType(name = "SmilesParser")
public class JSSmilesParser {
  private SmilesParser oclParser;

  public JSSmilesParser(JavaScriptObject options) {
    init(options);
  }

  private native void init(JavaScriptObject options)
  /*-{
    options = options || {};

    var smartsMode = options.smartsMode || 'smiles';
    var skipCoordinateTemplates = options.skipCoordinateTemplates || false;
    var makeHydrogenExplicit = options.makeHydrogenExplicit || false;
    var noCactvs = options.noCactvs || false;
    var singleDotSeparator = options.singleDotSeparator || false;
    var createSmartsWarnings = options.createSmartsWarnings || false;
    this.@com.actelion.research.gwt.js.api.JSSmilesParser::init(Ljava/lang/String;ZZZZZ)(smartsMode, skipCoordinateTemplates, makeHydrogenExplicit, noCactvs, singleDotSeparator, createSmartsWarnings);
  }-*/;

  private void init(String smartsMode,
    boolean skipCoordinateTemplates, boolean makeHydrogenExplicit,
    boolean noCactvs, boolean singleDotSeparator, boolean createSmartsWarnings) {
    int mode = SmilesParser.SMARTS_MODE_IS_SMILES;
    switch (smartsMode) {
      case "smarts":
        mode = SmilesParser.SMARTS_MODE_IS_SMARTS;
        break;
      case "guess":
        mode = SmilesParser.SMARTS_MODE_GUESS;
        break;
    }
    if (makeHydrogenExplicit) {
      mode |= SmilesParser.MODE_MAKE_HYDROGEN_EXPLICIT;
    }
    if (skipCoordinateTemplates) {
      mode |= SmilesParser.MODE_SKIP_COORDINATE_TEMPLATES;
    }
    if (noCactvs) {
      mode |= SmilesParser.MODE_NO_CACTUS_SYNTAX;
    }
    if (singleDotSeparator) {
      mode |= SmilesParser.MODE_SINGLE_DOT_SEPARATOR;
    }
    if (createSmartsWarnings) {
      mode |= SmilesParser.MODE_CREATE_SMARTS_WARNING;
    }
    oclParser = new SmilesParser(mode);
  }

  public void setRandomSeed(int seed) {
    oclParser.setRandomSeed((long)seed);
  }

  public native JSMolecule parseMolecule(String smiles, JavaScriptObject options)
  /*-{
    options = options || {};

    var molecule = options.molecule || @com.actelion.research.gwt.js.api.JSMolecule::new()();
    var createCoordinates = !options.noCoordinates;
    var readStereoFeatures = !options.noStereo;
    return this.@com.actelion.research.gwt.js.api.JSSmilesParser::parseMolecule(Lcom/actelion/research/gwt/js/api/JSMolecule;Ljava/lang/String;ZZ)(molecule, smiles, createCoordinates, readStereoFeatures);
  }-*/;

  private JSMolecule parseMolecule(JSMolecule molecule, String smiles,
    boolean createCoordinates, boolean readStereoFeatures) throws Exception {
    oclParser.parse(molecule.getStereoMolecule(), smiles.getBytes(), createCoordinates, readStereoFeatures);
    return molecule;
  }

  public JSReaction parseReaction(String smiles) throws Exception {
    return new JSReaction(oclParser.parseReaction(smiles));
  }

  public String getSmartsWarning() {
    return oclParser.getSmartsWarning();
  }
}

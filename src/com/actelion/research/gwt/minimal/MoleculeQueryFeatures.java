package com.actelion.research.gwt.minimal;

import com.actelion.research.chem.StereoMolecule;
import com.google.gwt.core.client.JavaScriptObject;

class MoleculeQueryFeatures {
  public static JavaScriptObject getAtomQueryFeatures(StereoMolecule oclMolecule, int atom) {
    PlainJSObject moleculeQueryFeatures = PlainJSObject.create();

    moleculeQueryFeatures.setPropertyString("oclID", "ABC");
    moleculeQueryFeatures.setPropertyString("smiles", "asd");

    return moleculeQueryFeatures;
  }
}

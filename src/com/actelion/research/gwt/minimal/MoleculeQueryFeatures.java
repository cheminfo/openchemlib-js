package com.actelion.research.gwt.minimal;

import jsinterop.annotations.*;
import com.actelion.research.chem.StereoMolecule;

@JsType(isNative = true, namespace = JsPackage.GLOBAL, name = "Object")
class MoleculeQueryFeatures {
  String oclID;
  String smiles;

  @JsOverlay
  public static MoleculeQueryFeatures getAtomQueryFeatures(StereoMolecule oclMolecule, int atom) {
    MoleculeQueryFeatures moleculeQueryFeatures = new MoleculeQueryFeatures();
    moleculeQueryFeatures.oclID = "ABC";
    // moleculeQueryFeatures.oclID = Long.toString(oclMolecule.getAtomQueryFeatures(0));
    moleculeQueryFeatures.smiles = "asd";
    return moleculeQueryFeatures;
  }
}

package com.actelion.research.gwt.js.api;

import jsinterop.annotations.*;

import com.actelion.research.chem.reaction.Transformer;
import com.actelion.research.chem.SSSearcher;

@JsType(name = "Transformer")
public class JSTransformer {
  private Transformer transformer;

  public JSTransformer(JSMolecule reactant, JSMolecule product, String name) {
    transformer = new Transformer(reactant.getStereoMolecule(), product.getStereoMolecule(), name);
  }

  public int setMolecule(JSMolecule molecule, int countMode) {
    return transformer.setMolecule(molecule.getStereoMolecule(), SSSearcher.cCountModeOverlapping);
  }

  public void applyTransformation(JSMolecule molecule, int matchNo) {
    transformer.applyTransformation(molecule.getStereoMolecule(), matchNo);
  }

}

package com.actelion.research.gwt.js.api;

import jsinterop.annotations.*;

import com.actelion.research.chem.ExtendedMolecule;

@JsType
public class MolecularFormula extends com.actelion.research.chem.MolecularFormula {

  @JsIgnore
  public MolecularFormula(ExtendedMolecule mol) {
    super(mol);
  }

  @Override
  @JsProperty
  public double getAbsoluteWeight() {
    return super.getAbsoluteWeight();
  }

  @Override
  @JsProperty
  public double getRelativeWeight() {
    return super.getRelativeWeight();
  }

  @Override
  @JsProperty
  public String getFormula() {
    return super.getFormula();
  }

}

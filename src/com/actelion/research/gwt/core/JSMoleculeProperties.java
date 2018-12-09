package com.actelion.research.gwt.core;

import com.actelion.research.chem.PropertyCalculator;
import com.actelion.research.gwt.minimal.JSMolecule;
import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

@JsType(name = "MoleculeProperties")
public class JSMoleculeProperties extends PropertyCalculator {

  public JSMoleculeProperties(JSMolecule mol) {
    super(mol.getStereoMolecule());
  }

  @Override
  @JsProperty
  public int getAcceptorCount() {
    return super.getAcceptorCount();
  }

  @Override
  @JsProperty
  public int getDonorCount() {
    return super.getDonorCount();
  }

  @Override
  @JsProperty
  public double getLogP() {
    return super.getLogP();
  }

  @JsProperty
  public JavaScriptObject getLogPString() {
    return Util.convertParameterizedStringList(super.getLogPDetail());
  }

  @Override
  @JsProperty
  public double getLogS() {
    return super.getLogS();
  }

  @JsProperty
  public JavaScriptObject getLogSString() {
    return Util.convertParameterizedStringList(super.getLogSDetail());
  }

  @Override
  @JsProperty
  public double getPolarSurfaceArea() {
    return super.getPolarSurfaceArea();
  }

  @JsProperty
  public JavaScriptObject getPolarSurfaceAreaString() {
    return Util.convertParameterizedStringList(super.getPolarSurfaceAreaDetail());
  }

  @Override
  @JsProperty
  public int getRotatableBondCount() {
    return super.getRotatableBondCount();
  }

  @Override
  @JsProperty
  public int getStereoCenterCount() {
    return super.getStereoCenterCount();
  }

}

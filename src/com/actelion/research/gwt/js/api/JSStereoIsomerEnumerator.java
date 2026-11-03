package com.actelion.research.gwt.js.api;

import com.actelion.research.chem.StereoIsomerEnumerator;
import com.actelion.research.chem.StereoMolecule;
import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

@JsType(name = "StereoIsomerEnumerator")
public class JSStereoIsomerEnumerator {

  private StereoIsomerEnumerator enumerator;

  public JSStereoIsomerEnumerator(JSMolecule mol, JavaScriptObject options) {
    // The enumerator keeps the molecule it is given and changes its helper
    // arrays, so it works on a copy.
    enumerator = new StereoIsomerEnumerator(
      new StereoMolecule(mol.getStereoMolecule()),
      skipsEnantiomers(options)
    );
  }

  private static native boolean skipsEnantiomers(
    JavaScriptObject options
  ) /*-{
    return !!(options && options.skipEnantiomers);
  }-*/;

  public boolean isSkippingEnantiomers() {
    return enumerator.isSkippingEnantiomers();
  }

  public int getStereoIsomerCount() {
    return enumerator.getStereoIsomerCount();
  }

  public JSMolecule getStereoIsomer(int index) {
    return new JSMolecule(enumerator.getStereoIsomer(index));
  }

  public boolean isCorrectStereoIsomer(JSMolecule mol, int index) {
    return enumerator.isCorrectStereoIsomer(mol.getStereoMolecule(), index);
  }
}

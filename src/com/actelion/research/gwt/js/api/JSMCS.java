package com.actelion.research.gwt.js.api;

import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.mcs.MCS;
import com.google.gwt.core.client.JavaScriptObject;
import java.util.LinkedList;
import jsinterop.annotations.*;

@JsType(name = "MCS")
public class JSMCS {

  private MCS mcs;

  public JSMCS(JavaScriptObject options) {
    int ringStatus = getRingStatus(options);
    mcs = new MCS(ringStatus);
  }

  private native int getRingStatus(
    JavaScriptObject options
  ) /*-{
    options = options || {};
    var ringMatchMode = options.ringMatchMode || 'cleaveRings';
    switch (ringMatchMode) {
      case 'cleaveRings': return @com.actelion.research.chem.mcs.MCS::PAR_CLEAVE_RINGS;
      case 'keepRings': return @com.actelion.research.chem.mcs.MCS::PAR_KEEP_RINGS;
      case 'keepAromaticRings': return @com.actelion.research.chem.mcs.MCS::PAR_KEEP_AROMATIC_RINGS;
      default: throw new Error('invalid ring match mode: ' + ringMatchMode);
    }
  }-*/;

  public void set(JSMolecule molecule, JSMolecule fragment) {
    this.mcs.set(molecule.getStereoMolecule(), fragment.getStereoMolecule());
  }

  public JSMolecule getMCS() {
    StereoMolecule result = this.mcs.getMCS();
    if (result == null) {
      return null;
    }
    return new JSMolecule(result);
  }

  public double getScore() {
    return this.mcs.getScore();
  }

  public JSMolecule[] getAllCommonSubstructures() {
    LinkedList<StereoMolecule> list = this.mcs.getAllCommonSubstructures();
    if (list == null) {
      return null;
    }
    JSMolecule[] result = new JSMolecule[list.size()];
    int index = 0;
    for (StereoMolecule substructure : list) {
      result[index++] = new JSMolecule(substructure);
    }
    return result;
  }
}

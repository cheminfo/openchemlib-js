package com.actelion.research.gwt.core;

import com.actelion.research.gwt.minimal.JSMolecule;
import com.actelion.research.chem.*;
import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;
import org.openmolecules.chem.conf.gen.ConformerGenerator;

@JsType(name = "ConformerGenerator")
public class JSConformerGenerator {
  private ConformerGenerator oclGenerator;

  public static final int STRATEGY_LIKELY_SYSTEMATIC = 1;
	public static final int STRATEGY_PURE_RANDOM = 2;
	public static final int STRATEGY_LIKELY_RANDOM = 3;
	public static final int STRATEGY_ADAPTIVE_RANDOM = 4;

  public JSConformerGenerator(int seed) {
    oclGenerator = new ConformerGenerator(seed);
  }

  public JSMolecule getOneConformerAsMolecule(JSMolecule mol) {
    StereoMolecule result = oclGenerator.getOneConformerAsMolecule(mol.getStereoMolecule());
    return result == null ? null : mol;
  }

  public native boolean initializeConformers(JSMolecule mol, JavaScriptObject options)
  /*-{
    if (options === undefined) options = {};
    var strategy = options.strategy;
    if (strategy === undefined) strategy = 3;
    var maxTorsionSets = options.maxTorsionSets;
    if (maxTorsionSets === undefined) maxTorsionSets = 100000;
    var use60degreeSteps = options.use60degreeSteps;
    if (use60degreeSteps === undefined) use60degreeSteps = false;
    return this.@com.actelion.research.gwt.core.JSConformerGenerator::initializeConformers(Lcom/actelion/research/gwt/minimal/JSMolecule;IIZ)(mol, strategy, maxTorsionSets, use60degreeSteps);
  }-*/;

  private boolean initializeConformers(JSMolecule mol, int strategy, int maxTorsionSets, boolean use60degreeSteps) {
    return oclGenerator.initializeConformers(mol.getStereoMolecule(), strategy, maxTorsionSets, use60degreeSteps);
  }

  public JSMolecule getNextConformerAsMolecule(JSMolecule mol) {
    StereoMolecule arg = mol == null ? null : mol.getStereoMolecule();
    StereoMolecule newMol = oclGenerator.getNextConformer(arg);
    if (newMol == null) return null;
    if (newMol == arg) return mol;
    return new JSMolecule(newMol);
  }

  public int getConformerCount() {
    return oclGenerator.getConformerCount();
  }

  public int getPotentialConformerCount() {
    return oclGenerator.getPotentialConformerCount();
  }

  public double getPreviousConformerContribution() {
    return oclGenerator.getPreviousConformerContribution();
  }
}

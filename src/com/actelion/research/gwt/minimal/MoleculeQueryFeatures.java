package com.actelion.research.gwt.minimal;

import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.Molecule;
import com.google.gwt.core.client.JavaScriptObject;

class MoleculeQueryFeatures {
  public static JavaScriptObject getAtomQueryFeatures(StereoMolecule oclMolecule, int atom) {
    PlainJSObject toReturn = PlainJSObject.create();
    long atomQueryFeatures = oclMolecule.getAtomQueryFeatures(atom);

    toReturn.setPropertyBoolean("aromatic", (atomQueryFeatures & Molecule.cAtomQFAromatic) > 0);
    toReturn.setPropertyBoolean("notAromatic",
        (atomQueryFeatures & Molecule.cAtomQFNotAromatic) > 0);

    toReturn.setPropertyBoolean("notChain", (atomQueryFeatures & Molecule.cAtomQFNotChain) > 0);
    toReturn.setPropertyBoolean("not2RingBonds",
        (atomQueryFeatures & Molecule.cAtomQFNot2RingBonds) > 0);
    toReturn.setPropertyBoolean("not3RingBonds",
        (atomQueryFeatures & Molecule.cAtomQFNot3RingBonds) > 0);
    toReturn.setPropertyBoolean("not4RingBonds",
        (atomQueryFeatures & Molecule.cAtomQFNot4RingBonds) > 0);

    toReturn.setPropertyBoolean("noMoreNeighbours",
        (atomQueryFeatures & Molecule.cAtomQFNoMoreNeighbours) > 0);
    toReturn.setPropertyBoolean("moreNeighbours",
        (atomQueryFeatures & Molecule.cAtomQFMoreNeighbours) > 0);
    toReturn.setPropertyBoolean("matchStereo",
        (atomQueryFeatures & Molecule.cAtomQFMatchStereo) > 0);

    toReturn.setPropertyBoolean("not0PiElectrons",
        (atomQueryFeatures & Molecule.cAtomQFNot0PiElectrons) > 0);
    toReturn.setPropertyBoolean("not1PiElectrons",
        (atomQueryFeatures & Molecule.cAtomQFNot1PiElectron) > 0);
    toReturn.setPropertyBoolean("not2PiElectrons",
        (atomQueryFeatures & Molecule.cAtomQFNot2PiElectrons) > 0);

    toReturn.setPropertyBoolean("not0Hydrogen",
        (atomQueryFeatures & Molecule.cAtomQFNot0Hydrogen) > 0);
    toReturn.setPropertyBoolean("not1Hydrogen",
        (atomQueryFeatures & Molecule.cAtomQFNot1Hydrogen) > 0);
    toReturn.setPropertyBoolean("not2Hydrogen",
        (atomQueryFeatures & Molecule.cAtomQFNot2Hydrogen) > 0);
    toReturn.setPropertyBoolean("not3Hydrogen",
        (atomQueryFeatures & Molecule.cAtomQFNot3Hydrogen) > 0);

    toReturn.setPropertyBoolean("not0Neighbours",
        (atomQueryFeatures & Molecule.cAtomQFNot0Neighbours) > 0);
    toReturn.setPropertyBoolean("not1Neighbours",
        (atomQueryFeatures & Molecule.cAtomQFNot0Neighbours) > 0);
    toReturn.setPropertyBoolean("not2Neighbours",
        (atomQueryFeatures & Molecule.cAtomQFNot2Neighbours) > 0);
    toReturn.setPropertyBoolean("not3Neighbours",
        (atomQueryFeatures & Molecule.cAtomQFNot3Neighbours) > 0);
    toReturn.setPropertyBoolean("not4Neighbours",
        (atomQueryFeatures & Molecule.cAtomQFNot4Neighbours) > 0);

    toReturn.setPropertyBoolean("notChargeNeg",
        (atomQueryFeatures & Molecule.cAtomQFNotChargeNeg) > 0);
    toReturn.setPropertyBoolean("notCharge0", (atomQueryFeatures & Molecule.cAtomQFNotCharge0) > 0);
    toReturn.setPropertyBoolean("noChargePos",
        (atomQueryFeatures & Molecule.cAtomQFNotChargePos) > 0);

    toReturn.setPropertyBoolean("ringSize0", (atomQueryFeatures & Molecule.cAtomQFRingSize0) > 0);
    toReturn.setPropertyBoolean("ringSize3", (atomQueryFeatures & Molecule.cAtomQFRingSize3) > 0);
    toReturn.setPropertyBoolean("ringSize4", (atomQueryFeatures & Molecule.cAtomQFRingSize4) > 0);
    toReturn.setPropertyBoolean("ringSize5", (atomQueryFeatures & Molecule.cAtomQFRingSize5) > 0);
    toReturn.setPropertyBoolean("ringSize6", (atomQueryFeatures & Molecule.cAtomQFRingSize6) > 0);
    toReturn.setPropertyBoolean("ringSize7", (atomQueryFeatures & Molecule.cAtomQFRingSize7) > 0);
    toReturn.setPropertyBoolean("ringSizeLarge",
        (atomQueryFeatures & Molecule.cAtomQFRingSizeLarge) > 0);

    return toReturn;
  }

  public static JavaScriptObject getBondQueryFeatures(StereoMolecule oclMolecule, int bond) {
    PlainJSObject toReturn = PlainJSObject.create();
    long bondQueryFeatures = oclMolecule.getBondQueryFeatures(bond);

    toReturn.setPropertyBoolean("single", (bondQueryFeatures & Molecule.cBondQFSingle) > 0);
    toReturn.setPropertyBoolean("double", (bondQueryFeatures & Molecule.cBondQFDouble) > 0);
    toReturn.setPropertyBoolean("triple", (bondQueryFeatures & Molecule.cBondQFTriple) > 0);
    toReturn.setPropertyBoolean("delocalized",
        (bondQueryFeatures & Molecule.cBondQFDelocalized) > 0);
    toReturn.setPropertyBoolean("metalLigand",
        (bondQueryFeatures & Molecule.cBondQFMetalLigand) > 0);
    toReturn.setPropertyBoolean("quadruple", (bondQueryFeatures & Molecule.cBondQFQuadruple) > 0);
    toReturn.setPropertyBoolean("quintuple", (bondQueryFeatures & Molecule.cBondQFQuintuple) > 0);

    toReturn.setPropertyBoolean("notRing", (bondQueryFeatures & Molecule.cBondQFNotRing) > 0);
    toReturn.setPropertyBoolean("ring", (bondQueryFeatures & Molecule.cBondQFRing) > 0);

    toReturn.setPropertyBoolean("aromatic", (bondQueryFeatures & Molecule.cBondQFAromatic) > 0);
    toReturn.setPropertyBoolean("nonAromatic",
        (bondQueryFeatures & Molecule.cBondQFNotAromatic) > 0);

    toReturn.setPropertyInt("ringSize",
        (int) (bondQueryFeatures & Molecule.cBondQFRingSize) >> Molecule.cBondQFRingSizeShift);

    toReturn.setPropertyInt("brigdeMin",
        (int) (bondQueryFeatures & Molecule.cBondQFBridgeMin) >> Molecule.cBondQFBridgeMinShift);
    toReturn.setPropertyInt("brigdeSpan",
        (int) (bondQueryFeatures & Molecule.cBondQFBridgeSpan) >> Molecule.cBondQFBridgeSpanShift);

    return toReturn;
  }
}

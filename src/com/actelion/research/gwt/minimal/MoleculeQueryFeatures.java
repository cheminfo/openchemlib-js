package com.actelion.research.gwt.minimal;

import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.Molecule;
import com.google.gwt.core.client.JavaScriptObject;

class MoleculeQueryFeatures {
  public static JavaScriptObject getAtomQueryFeatures(StereoMolecule oclMolecule, int atom) {
    PlainJSObject moleculeQueryFeatures = PlainJSObject.create();
    long atomQueryFeatures = oclMolecule.getAtomQueryFeatures(atom);

    moleculeQueryFeatures.setPropertyBoolean("aromatic",
        (atomQueryFeatures & Molecule.cAtomQFAromatic) > 0);
    moleculeQueryFeatures.setPropertyBoolean("notAromatic",
        (atomQueryFeatures & Molecule.cAtomQFNotAromatic) > 0);

    moleculeQueryFeatures.setPropertyBoolean("notChain",
        (atomQueryFeatures & Molecule.cAtomQFNotChain) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not2RingBonds",
        (atomQueryFeatures & Molecule.cAtomQFNot2RingBonds) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not3RingBonds",
        (atomQueryFeatures & Molecule.cAtomQFNot3RingBonds) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not4RingBonds",
        (atomQueryFeatures & Molecule.cAtomQFNot4RingBonds) > 0);

    moleculeQueryFeatures.setPropertyBoolean("noMoreNeighbours",
        (atomQueryFeatures & Molecule.cAtomQFNoMoreNeighbours) > 0);
    moleculeQueryFeatures.setPropertyBoolean("moreNeighbours",
        (atomQueryFeatures & Molecule.cAtomQFMoreNeighbours) > 0);
    moleculeQueryFeatures.setPropertyBoolean("matchStereo",
        (atomQueryFeatures & Molecule.cAtomQFMatchStereo) > 0);

    moleculeQueryFeatures.setPropertyBoolean("not0PiElectrons",
        (atomQueryFeatures & Molecule.cAtomQFNot0PiElectrons) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not1PiElectrons",
        (atomQueryFeatures & Molecule.cAtomQFNot1PiElectron) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not2PiElectrons",
        (atomQueryFeatures & Molecule.cAtomQFNot2PiElectrons) > 0);

    moleculeQueryFeatures.setPropertyBoolean("not0Hydrogen",
        (atomQueryFeatures & Molecule.cAtomQFNot0Hydrogen) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not1Hydrogen",
        (atomQueryFeatures & Molecule.cAtomQFNot1Hydrogen) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not2Hydrogen",
        (atomQueryFeatures & Molecule.cAtomQFNot2Hydrogen) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not3Hydrogen",
        (atomQueryFeatures & Molecule.cAtomQFNot3Hydrogen) > 0);

    moleculeQueryFeatures.setPropertyBoolean("not0Neighbours",
        (atomQueryFeatures & Molecule.cAtomQFNot0Neighbours) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not1Neighbours",
        (atomQueryFeatures & Molecule.cAtomQFNot0Neighbours) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not2Neighbours",
        (atomQueryFeatures & Molecule.cAtomQFNot2Neighbours) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not3Neighbours",
        (atomQueryFeatures & Molecule.cAtomQFNot3Neighbours) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not4Neighbours",
        (atomQueryFeatures & Molecule.cAtomQFNot4Neighbours) > 0);

    moleculeQueryFeatures.setPropertyBoolean("notChargeNeg",
        (atomQueryFeatures & Molecule.cAtomQFNotChargeNeg) > 0);
    moleculeQueryFeatures.setPropertyBoolean("notCharge0",
        (atomQueryFeatures & Molecule.cAtomQFNotCharge0) > 0);
    moleculeQueryFeatures.setPropertyBoolean("noChargePos",
        (atomQueryFeatures & Molecule.cAtomQFNotChargePos) > 0);

    moleculeQueryFeatures.setPropertyBoolean("ringSize0",
        (atomQueryFeatures & Molecule.cAtomQFRingSize0) > 0);
    moleculeQueryFeatures.setPropertyBoolean("ringSize3",
        (atomQueryFeatures & Molecule.cAtomQFRingSize3) > 0);
    moleculeQueryFeatures.setPropertyBoolean("ringSize4",
        (atomQueryFeatures & Molecule.cAtomQFRingSize4) > 0);
    moleculeQueryFeatures.setPropertyBoolean("ringSize5",
        (atomQueryFeatures & Molecule.cAtomQFRingSize5) > 0);
    moleculeQueryFeatures.setPropertyBoolean("ringSize6",
        (atomQueryFeatures & Molecule.cAtomQFRingSize6) > 0);
    moleculeQueryFeatures.setPropertyBoolean("ringSize7",
        (atomQueryFeatures & Molecule.cAtomQFRingSize7) > 0);
    moleculeQueryFeatures.setPropertyBoolean("ringSizeLarge",
        (atomQueryFeatures & Molecule.cAtomQFRingSizeLarge) > 0);

    return moleculeQueryFeatures;
  }
}

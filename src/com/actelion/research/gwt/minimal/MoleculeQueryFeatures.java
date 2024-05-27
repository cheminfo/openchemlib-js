package com.actelion.research.gwt.minimal;

import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.Molecule;
import com.google.gwt.core.client.JavaScriptObject;

class MoleculeQueryFeatures {
  public static JavaScriptObject getAtomQueryFeatures(StereoMolecule oclMolecule, int atom) {
    PlainJSObject moleculeQueryFeatures = PlainJSObject.create();

    moleculeQueryFeatures.setPropertyBoolean("aromatic",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFAromatic) > 0);
    moleculeQueryFeatures.setPropertyBoolean("notAromatic",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNotAromatic) > 0);

    moleculeQueryFeatures.setPropertyBoolean("notChain",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNotChain) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not2RingBonds",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot2RingBonds) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not3RingBonds",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot3RingBonds) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not4RingBonds",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot4RingBonds) > 0);

    moleculeQueryFeatures.setPropertyBoolean("noMoreNeighbours",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNoMoreNeighbours) > 0);
    moleculeQueryFeatures.setPropertyBoolean("moreNeighbours",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFMoreNeighbours) > 0);
    moleculeQueryFeatures.setPropertyBoolean("matchStereo",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFMatchStereo) > 0);

    moleculeQueryFeatures.setPropertyBoolean("not0PiElectrons",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot0PiElectrons) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not1PiElectrons",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot1PiElectron) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not2PiElectrons",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot2PiElectrons) > 0);

    moleculeQueryFeatures.setPropertyBoolean("not0Hydrogen",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot0Hydrogen) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not1Hydrogen",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot1Hydrogen) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not2Hydrogen",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot2Hydrogen) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not3Hydrogen",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot3Hydrogen) > 0);

    moleculeQueryFeatures.setPropertyBoolean("not0Neighbours",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot0Neighbours) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not1Neighbours",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot0Neighbours) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not2Neighbours",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot2Neighbours) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not3Neighbours",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot3Neighbours) > 0);
    moleculeQueryFeatures.setPropertyBoolean("not4Neighbours",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNot4Neighbours) > 0);

    moleculeQueryFeatures.setPropertyBoolean("notChargeNeg",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNotChargeNeg) > 0);
    moleculeQueryFeatures.setPropertyBoolean("notCharge0",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNotCharge0) > 0);
    moleculeQueryFeatures.setPropertyBoolean("noChargePos",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFNotChargePos) > 0);

    moleculeQueryFeatures.setPropertyBoolean("ringSize0",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFRingSize0) > 0);
    moleculeQueryFeatures.setPropertyBoolean("ringSize3",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFRingSize3) > 0);
    moleculeQueryFeatures.setPropertyBoolean("ringSize4",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFRingSize4) > 0);
    moleculeQueryFeatures.setPropertyBoolean("ringSize5",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFRingSize5) > 0);
    moleculeQueryFeatures.setPropertyBoolean("ringSize6",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFRingSize6) > 0);
    moleculeQueryFeatures.setPropertyBoolean("ringSize7",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFRingSize7) > 0);
    moleculeQueryFeatures.setPropertyBoolean("ringSizeLarge",
        (oclMolecule.getAtomQueryFeatures(atom) & Molecule.cAtomQFRingSizeLarge) > 0);

    return moleculeQueryFeatures;
  }
}

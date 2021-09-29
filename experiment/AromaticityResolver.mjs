import Molecule from './Molecule.mjs';
import RingCollection from './RingCollection.mjs';

export default class AromaticityResolver {
  mMol = null;
  mAllHydrogensAreExplicit = false;
  mIsDelocalizedAtom = null;
  mIsDelocalizedBond = null;
  mAromaticAtoms = 0;
  mAromaticBonds = 0;
  mPiElectronsAdded = 0;

  constructor(mol) {
    this.mMol = mol;
  }

  locateDelocalizedDoubleBonds(
    isAromaticBond,
    mayChangeAtomCharges = false,
    allHydrogensAreExplicit = false,
  ) {
    this.mMol.ensureHelperArrays(Molecule.cHelperNeighbours);

    if (isAromaticBond != null) {
      this.mIsDelocalizedBond = isAromaticBond;
    } else {
      this.mIsDelocalizedBond = new Array(this.mMol.getBonds()).fill(false);
      for (let bond = 0; bond < this.mMol.getBonds(); bond++) {
        if (this.mMol.getBondType(bond) == Molecule.cBondTypeDelocalized) {
          this.mIsDelocalizedBond[bond] = true;
          this.mMol.setBondType(bond, Molecule.cBondTypeSingle);
        }
      }
    }

    this.mPiElectronsAdded = 0;

    this.mIsDelocalizedAtom = new Array(this.mMol.getAtoms()).fill(false);
    for (let bond = 0; bond < this.mMol.getBonds(); bond++) {
      if (this.mIsDelocalizedBond[bond]) {
        this.mAromaticBonds++;
        for (let i = 0; i < 2; i++) {
          if (!this.mIsDelocalizedAtom[this.mMol.getBondAtom(i, bond)]) {
            this.mIsDelocalizedAtom[this.mMol.getBondAtom(i, bond)] = true;
            this.mAromaticAtoms++;
          }
        }
      }
    }

    if (this.mAromaticBonds == 0) return true;

    this.mAllHydrogensAreExplicit = allHydrogensAreExplicit;

    this.protectFullValenceAtoms(mayChangeAtomCharges);

    if (this.mMol.isFragment()) this.promoteDelocalizedChains();

    // create small-ring set without aromaticity information
    const ringSet = new RingCollection(
      this.mMol,
      RingCollection.MODE_SMALL_RINGS_ONLY,
    );

    if (mayChangeAtomCharges) this.addObviousAtomCharges(ringSet);

    // find mandatory conjugation breaking atoms in 3-, 5-, and 7-membered rings,
    // i.e. atoms whose neighbour bonds must be single bonds
    this.protectObviousDelocalizationLeaks(ringSet);

    this.protectAmideBonds(ringSet); // using rules for detecting aromatic (thio-)amide bonds
    this.protectDoubleBondAtoms();

    this.promoteObviousBonds();

    while (this.promoteOuterShellDelocalizedRingSystems(ringSet))
      this.promoteObviousBonds();

    while (this.mAromaticBonds != 0) {
      let bondsPromoted = false;

      if (!bondsPromoted) {
        // try to find and promote one entire aromatic 6-ring
        for (let ring = 0; ring < ringSet.getSize(); ring++) {
          if (ringSet.getRingSize(ring) == 6) {
            let isAromaticRing = true;
            let ringBond = ringSet.getRingBonds(ring);
            for (let i = 0; i < 6; i++) {
              if (!this.mIsDelocalizedBond[ringBond[i]]) {
                isAromaticRing = false;
                break;
              }
            }

            if (isAromaticRing) {
              for (let i = 0; i < 6; i += 2) this.promoteBond(ringBond[i]);
              bondsPromoted = true;
              break;
            }
          }
        }
      }

      if (!bondsPromoted) {
        // find and promote one aromatic bond
        // (should never happen, but to prevent an endless loop nonetheless)
        for (let bond = 0; bond < this.mMol.getBonds(); bond++) {
          if (this.mIsDelocalizedBond[bond]) {
            this.promoteBond(bond);
            this.promoteObviousBonds();
            bondsPromoted = true;
            break;
          }
        }
      }
    }

    return this.mAromaticAtoms == this.mPiElectronsAdded;
  }

  protectFullValenceAtoms(mayChangeAtomCharges) {
    for (let atom = 0; atom < this.mMol.getAtoms(); atom++)
      if (
        this.mIsDelocalizedAtom[atom] &&
        this.mMol.getLowestFreeValence(atom) == 0 &&
        (!mayChangeAtomCharges ||
          !this.mMol.isElectronegative(atom) ||
          this.mMol.getAtomCharge(atom) > 0)
      )
        this.protectAtom(atom);
  }
}

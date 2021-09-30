import Molecule from './Molecule.js';
import RingCollection from './RingCollection.js';

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

  protectObviousDelocalizationLeaks(ringSet) {
    for (let r = 0; r < ringSet.getSize(); r++) {
      let ringSize = ringSet.getRingSize(r);
      if (ringSize == 3 || ringSize == 5 || ringSize == 7) {
        let ringAtom = ringSet.getRingAtoms(r);
        for (let i = 0; i < ringSize; i++) {
          let atom = ringAtom[i];
          if (this.isAromaticAtom(atom)) {
            if (ringSize == 5) {
              // C(-),N,O,S in cyclopentadienyl, furan, pyrrol, etc.
              if (
                (this.mMol.getAtomicNo(atom) == 6 &&
                  this.mMol.getAtomCharge(atom) == -1 &&
                  this.mMol.getAllConnAtoms(atom) == 3) ||
                (this.mMol.getAtomicNo(atom) == 7 &&
                  this.mMol.getAtomCharge(atom) == 0 &&
                  this.mMol.getAllConnAtoms(atom) == 3) ||
                (this.mMol.getAtomicNo(atom) == 8 &&
                  this.mMol.getAtomCharge(atom) == 0 &&
                  this.mMol.getConnAtoms(atom) == 2) ||
                (this.mMol.getAtomicNo(atom) == 16 &&
                  this.mMol.getAtomCharge(atom) == 0 &&
                  this.mMol.getConnAtoms(atom) == 2) ||
                (this.mMol.getAtomicNo(atom) == 34 &&
                  this.mMol.getAtomCharge(atom) == 0 &&
                  this.mMol.getConnAtoms(atom) == 2)
              )
                this.protectAtom(atom);
            } else {
              // B,C+ in tropylium
              if (
                (this.mMol.getAtomicNo(atom) == 5 &&
                  this.mMol.getAtomCharge(atom) == 0 &&
                  this.mMol.getAllConnAtoms(atom) == 3) ||
                (this.mMol.getAtomicNo(atom) == 6 &&
                  this.mMol.getAtomCharge(atom) == 1)
              )
                this.protectAtom(atom);
            }
          }
        }
      }
    }

    // in 5-membered rings with 5 delocalized bonds and more than one negative carbon, we choose the most obvious as leak
    for (let r = 0; r < ringSet.getSize(); r++) {
      if (ringSet.getRingSize(r) == 5) {
        let ringBond = ringSet.getRingBonds(r);
        let isDelocalized = true;
        for (let i = 0; i < ringBond.length; i++) {
          if (!this.mIsDelocalizedBond[ringBond[i]]) {
            isDelocalized = false;
            break;
          }
        }
        if (isDelocalized) {
          let ringAtom = ringSet.getRingAtoms(r);
          let negativeCarbonPriority = 0;
          let negativeCarbon = -1;
          for (let i = 0; i < ringBond.length; i++) {
            if (
              this.mMol.getAtomCharge(ringAtom[i]) == -1 &&
              this.mMol.getAtomicNo(ringAtom[i]) == 6
            ) {
              let priority =
                this.mMol.getAllConnAtoms(ringAtom[i]) == 3
                  ? 3
                  : this.mMol.getAllConnAtomsPlusMetalBonds(ringAtom[i]) == 3
                  ? 2
                  : 1;
              if (negativeCarbonPriority < priority) {
                negativeCarbonPriority = priority;
                negativeCarbon = ringAtom[i];
              }
            }
          }
          if (negativeCarbon != -1) this.protectAtom(negativeCarbon);
        }
      }
    }
  }

  protectAmideBonds(ringSet) {
    for (let bond = 0; bond < this.mMol.getBonds(); bond++) {
      if (
        this.mIsDelocalizedBond[bond] &&
        ringSet.qualifiesAsAmideTypeBond(bond)
      ) {
        this.protectAtom(this.mMol.getBondAtom(0, bond));
        this.protectAtom(this.mMol.getBondAtom(1, bond));
      }
    }
  }

  protectDoubleBondAtoms() {
    for (let bond = 0; bond < this.mMol.getBonds(); bond++) {
      if (this.mMol.getBondOrder(bond) == 2) {
        for (let i = 0; i < 2; i++) {
          let atom = this.mMol.getBondAtom(i, bond);
          if (this.mMol.getAtomicNo(atom) <= 8) {
            for (let j = 0; j < this.mMol.getConnAtoms(atom); j++) {
              let connBond = this.mMol.getConnBond(atom, j);
              if (this.mIsDelocalizedBond[connBond]) {
                this.protectAtom(atom);
                break;
              }
            }
          }
        }
      }
    }
  }

  promoteObviousBonds() {
    // handle bond orders of aromatic bonds along the chains attached to 5- or 7-membered ring
    let terminalAromaticBondFound;
    do {
      terminalAromaticBondFound = false;
      for (let bond = 0; bond < this.mMol.getBonds(); bond++) {
        if (this.mIsDelocalizedBond[bond]) {
          let isTerminalAromaticBond = false;
          for (let i = 0; i < 2; i++) {
            let bondAtom = this.mMol.getBondAtom(i, bond);
            let aromaticNeighbourFound = false;
            for (let j = 0; j < this.mMol.getConnAtoms(bondAtom); j++) {
              if (
                bond != this.mMol.getConnBond(bondAtom, j) &&
                this.mIsDelocalizedBond[this.mMol.getConnBond(bondAtom, j)]
              ) {
                aromaticNeighbourFound = true;
                break;
              }
            }
            if (!aromaticNeighbourFound) {
              isTerminalAromaticBond = true;
              break;
            }
          }

          if (isTerminalAromaticBond) {
            terminalAromaticBondFound = true;
            this.promoteBond(bond);
          }
        }
      }
    } while (terminalAromaticBondFound);
  }

  promoteOuterShellDelocalizedRingSystems(ringSet) {
    let sharedDelocalizedRingCount = new Int32Array(this.mMol.getBonds());
    for (let r = 0; r < ringSet.getSize(); r++) {
      let ringBond = ringSet.getRingBonds(r);
      let isDelocalized = true;
      for (let i = 0; i < ringBond.length; i++) {
        if (!this.mIsDelocalizedBond[ringBond[i]]) {
          isDelocalized = false;
          break;
        }
      }
      if (isDelocalized)
        for (let i = 0; i < ringBond.length; i++)
          sharedDelocalizedRingCount[ringBond[i]]++;
    }

    let delocalizedBonds = this.mAromaticBonds;

    for (let bond = 0; bond < this.mMol.getBonds(); bond++) {
      if (sharedDelocalizedRingCount[bond] == 1) {
        for (let i = 0; i < 2 && this.mIsDelocalizedBond[bond]; i++) {
          let atom1 = this.mMol.getBondAtom(i, bond);
          let atom2 = this.mMol.getBondAtom(1 - i, bond);
          if (
            this.hasSharedDelocalizedBond(atom1, sharedDelocalizedRingCount) &&
            !this.hasSharedDelocalizedBond(atom2, sharedDelocalizedRingCount)
          ) {
            let connIndex;
            while (
              -1 !=
              (connIndex = this.getNextOuterDelocalizedConnIndex(
                atom2,
                atom1,
                sharedDelocalizedRingCount,
              ))
            ) {
              let atom3 = this.mMol.getConnAtom(atom2, connIndex);
              let bond2to3 = this.mMol.getConnBond(atom2, connIndex);
              if (!this.mIsDelocalizedBond[bond2to3]) break;

              this.promoteBond(bond2to3);
              connIndex = this.getNextOuterDelocalizedConnIndex(
                atom3,
                atom2,
                sharedDelocalizedRingCount,
              );
              if (connIndex == -1) break;

              atom1 = atom3;
              atom2 = this.mMol.getConnAtom(atom3, connIndex);
            }
          }
        }
      }
    }

    return delocalizedBonds != this.mAromaticBonds;
  }

  promoteBond(bond) {
    if (this.mMol.getBondType(bond) == Molecule.cBondTypeSingle) {
      this.mMol.setBondType(bond, Molecule.cBondTypeDouble);
      this.mPiElectronsAdded += 2;
    }

    for (let i = 0; i < 2; i++) {
      let bondAtom = this.mMol.getBondAtom(i, bond);
      for (let j = 0; j < this.mMol.getConnAtoms(bondAtom); j++) {
        let connBond = this.mMol.getConnBond(bondAtom, j);
        if (this.mIsDelocalizedBond[connBond]) {
          this.mIsDelocalizedBond[connBond] = false;
          this.mAromaticBonds--;
        }
      }
    }
  }

  isAromaticAtom(atom) {
    for (let i = 0; i < this.mMol.getConnAtoms(atom); i++)
      if (this.mIsDelocalizedBond[this.mMol.getConnBond(atom, i)]) return true;
    return false;
  }

  protectAtom(atom) {
    if (this.mIsDelocalizedAtom[atom]) {
      this.mIsDelocalizedAtom[atom] = false;
      this.mAromaticAtoms--;
    }
    for (let i = 0; i < this.mMol.getConnAtoms(atom); i++) {
      let connBond = this.mMol.getConnBond(atom, i);
      if (this.mIsDelocalizedBond[connBond]) {
        this.mIsDelocalizedBond[connBond] = false;
        this.mAromaticBonds--;
      }
    }
  }
}

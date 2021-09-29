import Molecule from './Molecule.mjs';
import RingCollection from './RingCollection.mjs';

export default class ExtendedMolecule extends Molecule {
  static FISCHER_PROJECTION_LIMIT = Math.PI / 36;
  static STEREO_ANGLE_LIMIT = Math.PI / 36; // 5 degrees

  static cMaxConnAtoms = 16; // ExtendedMolecule is not restricted anymore
  // However, this is a suggestion for editors and other classes
  mAtoms = 0;
  mBonds = 0;
  mRingSet = null;

  mPi = null;
  mConnAtoms = null; // non-H neighbour counts
  mAllConnAtoms = null; // neighbour counts including explicit hydrogen
  mConnAtom = null;
  mConnBond = null;
  mConnBondOrder = null;

  getAtoms() {
    return this.mAtoms;
  }

  getBonds() {
    return this.mBonds;
  }

  setParitiesValid(helperStereoBits) {
    this.mValidHelperArrays |=
      Molecule.cHelperBitsStereo &
      (Molecule.cHelperBitParities | helperStereoBits);
  }

  ensureHelperArrays(required) {
    // cHelperNeighbours: mConnAtoms,mConnBonds,mPi for all atoms
    // cHelperRings: rings,aromaticity/allylic/stabilized for non-H-atoms only
    // cHelperParities: stereo parities for non-H-atoms/bonds only
    // cHelperCIP: mCanonizer, stereo parities for non-H-atoms/bonds only

    if ((required & ~this.mValidHelperArrays) == 0) return;

    if ((this.mValidHelperArrays & Molecule.cHelperBitNeighbours) == 0) {
      this.handleHydrogens();
      this.calculateNeighbours();

      this.mValidHelperArrays |= Molecule.cHelperBitNeighbours;

      if (this.validateQueryFeatures()) {
        this.handleHydrogens();
        this.calculateNeighbours();
      }
    }

    if ((required & ~this.mValidHelperArrays) == 0) return;

    if (
      (this.mValidHelperArrays &
        ~(Molecule.cHelperBitRingsSimple | Molecule.cHelperBitRings)) !=
      0
    ) {
      for (let atom = 0; atom < this.mAtoms; atom++)
        this.mAtomFlags[atom] &= ~Molecule.cAtomFlagsHelper2;
      for (let bond = 0; bond < this.mBonds; bond++)
        this.mBondFlags[bond] &= ~Molecule.cBondFlagsHelper2;

      // if we are asked to only detect small rings and skip aromaticity, allylic and stabilized detection
      if ((required & Molecule.cHelperBitRings) == 0) {
        this.findRings(RingCollection.MODE_SMALL_RINGS_ONLY);
        this.mValidHelperArrays |= Molecule.cHelperBitRingsSimple;
        return;
      }

      this.findRings(RingCollection.MODE_SMALL_AND_LARGE_RINGS_AND_AROMATICITY);

      // set aromaticity flags of explicitly defined delocalized bonds
      for (let bond = 0; bond < this.mBonds; bond++) {
        if (this.mBondType[bond] == Molecule.cBondTypeDelocalized) {
          this.mAtomFlags[this.mBondAtom[0][bond]] |=
            Molecule.cAtomFlagAromatic;
          this.mAtomFlags[this.mBondAtom[1][bond]] |=
            Molecule.cAtomFlagAromatic;
          this.mBondFlags[bond] |= Molecule.cBondFlagAromatic;
          this.mBondFlags[bond] |= Molecule.cBondFlagDelocalized;
        }
      }

      for (let atom = 0; atom < this.mAtoms; atom++) {
        // allylic & stabilized flags
        for (let i = 0; i < this.mConnAtoms[atom]; i++) {
          let connBond = this.mConnBond[atom][i];
          if (this.isAromaticBond(connBond)) continue;

          let connAtom = this.mConnAtom[atom][i];
          for (let j = 0; j < this.mConnAtoms[connAtom]; j++) {
            if (this.mConnBond[connAtom][j] == connBond) continue;

            if (this.mConnBondOrder[connAtom][j] > 1) {
              if (this.mAtomicNo[this.mConnAtom[connAtom][j]] == 6)
                this.mAtomFlags[atom] |= Molecule.cAtomFlagAllylic;
              else {
                if (
                  !this.isAromaticBond(this.mConnBond[connAtom][j]) &&
                  this.isElectronegative(this.mConnAtom[connAtom][j])
                )
                  this.mAtomFlags[atom] |= Molecule.cAtomFlagStabilized;
              }
            }
          }
        }
      }

      // propagate stabilized flags to vinylic positions
      while (true) {
        let found = false;
        for (let atom = 0; atom < this.mAtoms; atom++) {
          // for non-aromatic stabilized atoms with pi-electrons
          if (
            this.mPi[atom] > 0 &&
            ((Molecule.cAtomFlagStabilized | Molecule.cAtomFlagAromatic) &
              this.mAtomFlags[atom]) ==
              Molecule.cAtomFlagStabilized
          ) {
            for (let i = 0; i < this.mConnAtoms[atom]; i++) {
              if (this.mConnBondOrder[atom][i] > 1) {
                let connAtom = this.mConnAtom[atom][i];
                let connBond = this.mConnBond[atom][i];
                for (let j = 0; j < this.mConnAtoms[connAtom]; j++) {
                  if (this.mConnBond[connAtom][j] != connBond) {
                    let candidate = this.mConnAtom[connAtom][j];
                    if (
                      (this.mAtomFlags[candidate] &
                        Molecule.cAtomFlagStabilized) ==
                      0
                    ) {
                      this.mAtomFlags[candidate] |=
                        Molecule.cAtomFlagStabilized;
                      found = true;
                    }
                  }
                }
              }
            }
          }
        }
        if (!found) break;
      }
    }

    this.mValidHelperArrays |=
      Molecule.cHelperBitRingsSimple | Molecule.cHelperBitRings;
  }

  handleHydrogens() {
    // find all hydrogens that are connected to a non-H atom and therefore can be implicit
    let isHydrogen = this.findSimpleHydrogens();

    // move all hydrogen atoms to end of atom table
    let lastNonHAtom = this.mAllAtoms;
    do lastNonHAtom--;
    while (lastNonHAtom >= 0 && isHydrogen[lastNonHAtom]);

    for (let atom = 0; atom < lastNonHAtom; atom++) {
      if (isHydrogen[atom]) {
        this.swapAtoms(atom, lastNonHAtom);

        // swap simple H flags also
        let temp = isHydrogen[atom];
        isHydrogen[atom] = isHydrogen[lastNonHAtom];
        isHydrogen[lastNonHAtom] = temp;

        do lastNonHAtom--;
        while (isHydrogen[lastNonHAtom]);
      }
    }
    this.mAtoms = lastNonHAtom + 1;

    // move all bonds to hydrogen to end of bond table
    if (this.mAllAtoms == this.mAtoms) {
      this.mBonds = this.mAllBonds;
      return;
    }

    let isHydrogenBond = new Array(mAllBonds).fill(false);
    for (let bond = 0; bond < thsi.mAllBonds; bond++) {
      // mark all bonds to hydrogen
      let atom1 = this.mBondAtom[0][bond];
      let atom2 = this.mBondAtom[1][bond];
      if (isHydrogen[atom1] || isHydrogen[atom2]) isHydrogenBond[bond] = true;
    }

    let lastNonHBond = this.mAllBonds;
    do lastNonHBond--;
    while (lastNonHBond >= 0 && isHydrogenBond[lastNonHBond]);

    for (let bond = 0; bond < lastNonHBond; bond++) {
      if (isHydrogenBond[bond]) {
        let tempInt = this.mBondAtom[0][bond];
        this.mBondAtom[0][bond] = this.mBondAtom[0][lastNonHBond];
        this.mBondAtom[0][lastNonHBond] = tempInt;
        tempInt = this.mBondAtom[1][bond];
        this.mBondAtom[1][bond] = this.mBondAtom[1][lastNonHBond];
        this.mBondAtom[1][lastNonHBond] = tempInt;
        tempInt = this.mBondType[bond];
        this.mBondType[bond] = this.mBondType[lastNonHBond];
        this.mBondType[lastNonHBond] = tempInt;
        isHydrogenBond[bond] = false;
        do lastNonHBond--;
        while (isHydrogenBond[lastNonHBond]);
      }
    }
    this.mBonds = lastNonHBond + 1;
  }

  findSimpleHydrogens() {
    let isSimpleHydrogen = new Array(this.mAllAtoms).fill(false);
    for (let atom = 0; atom < this.mAllAtoms; atom++)
      isSimpleHydrogen[atom] = this.isSimpleHydrogen(atom);

    // unflag simple hydrogens that have a bond with order != 1
    // or that have more than one bond or that are connected to metal atoms
    let oneBondFound = new Array(this.mAllAtoms).fill(false);
    for (let bond = 0; bond < this.mAllBonds; bond++) {
      let atom1 = this.mBondAtom[0][bond];
      let atom2 = this.mBondAtom[1][bond];

      if (this.getBondOrder(bond) != 1) {
        isSimpleHydrogen[atom1] = false;
        isSimpleHydrogen[atom2] = false;
        continue;
      }

      if (oneBondFound[atom1]) isSimpleHydrogen[atom1] = false;
      if (oneBondFound[atom2]) isSimpleHydrogen[atom2] = false;

      if (isSimpleHydrogen[atom1] && this.isMetalAtom(atom2))
        isSimpleHydrogen[atom1] = false;
      if (isSimpleHydrogen[atom2] && this.isMetalAtom(atom1))
        isSimpleHydrogen[atom2] = false;

      oneBondFound[atom1] = true;
      oneBondFound[atom2] = true;
    }

    // unflag simple hydrogens within an H2 molecule
    for (let bond = 0; bond < this.mAllBonds; bond++) {
      if (
        isSimpleHydrogen[this.mBondAtom[0][bond]] &&
        isSimpleHydrogen[this.mBondAtom[1][bond]]
      ) {
        isSimpleHydrogen[this.mBondAtom[0][bond]] = false;
        isSimpleHydrogen[this.mBondAtom[1][bond]] = false;
      }
    }

    // unflag simple hydrogens that have no connection to another atom
    for (let atom = 0; atom < this.mAllAtoms; atom++)
      if (!oneBondFound[atom]) isSimpleHydrogen[atom] = false;

    return isSimpleHydrogen;
  }

  isSimpleHydrogen(atom) {
    return (
      this.mAtomicNo[atom] == 1 &&
      this.mAtomMass[atom] == 0 &&
      this.mAtomCharge[atom] == 0 && // && mAtomMapNo[atom] == 0
      (this.mAtomCustomLabel == null || this.mAtomCustomLabel[atom] == null)
    );
    // Since a mapNo is not part of an idcode, a mapped but otherwise simple H must be considered simple; TLS 29Aug2020
  }

  calculateNeighbours() {
    this.mConnAtoms = new Int32Array(this.mAllAtoms);
    this.mAllConnAtoms = new Int32Array(this.mAllAtoms);
    this.mConnAtom = new Array(this.mAllAtoms);
    this.mConnBond = new Array(this.mAllAtoms);
    this.mConnBondOrder = new Array(this.mAllAtoms);
    this.mPi = new Int32Array(this.mAtoms);

    let connCount = new Int32Array(this.mAllAtoms);
    for (let bnd = 0; bnd < this.mAllBonds; bnd++) {
      connCount[this.mBondAtom[0][bnd]]++;
      connCount[this.mBondAtom[1][bnd]]++;
    }

    for (let atom = 0; atom < this.mAllAtoms; atom++) {
      this.mConnAtom[atom] = new Int32Array(connCount[atom]);
      this.mConnBond[atom] = new Int32Array(connCount[atom]);
      this.mConnBondOrder[atom] = new Int32Array(connCount[atom]);
    }

    let metalBondFound = false;
    for (let bnd = 0; bnd < this.mBonds; bnd++) {
      let order = this.getBondOrder(bnd);
      if (order == 0) {
        metalBondFound = true;
        continue;
      }

      for (let i = 0; i < 2; i++) {
        let atom = this.mBondAtom[i][bnd];
        let allConnAtoms = this.mAllConnAtoms[atom];
        this.mConnBondOrder[atom][allConnAtoms] = order;
        this.mConnAtom[atom][allConnAtoms] = this.mBondAtom[1 - i][bnd];
        this.mConnBond[atom][allConnAtoms] = bnd;
        this.mAllConnAtoms[atom]++; // all non metal-bonded neighbours (non-H, H)
        this.mConnAtoms[atom]++; // non-H and non-metal-bonded neighbours
        if (atom < this.mAtoms) {
          if (order > 1) this.mPi[atom] += order - 1;
          else if (this.mBondType[bnd] == Molecule.cBondTypeDelocalized)
            this.mPi[atom] = 1;
        }
      }
    }

    for (let bnd = this.mBonds; bnd < this.mAllBonds; bnd++) {
      let order = this.getBondOrder(bnd);
      if (order == 0) {
        metalBondFound = true;
        continue;
      }

      for (let i = 0; i < 2; i++) {
        let atom = this.mBondAtom[i][bnd];
        let allConnAtoms = this.mAllConnAtoms[atom];
        this.mConnBondOrder[atom][allConnAtoms] = order;
        this.mConnAtom[atom][allConnAtoms] = this.mBondAtom[1 - i][bnd];
        this.mConnBond[atom][allConnAtoms] = bnd;
        this.mAllConnAtoms[atom]++; // all non metal-bonded neighbours (non-H, H)
        if (this.mBondAtom[1 - i][bnd] < this.mAtoms) this.mConnAtoms[atom]++;
      }
    }

    if (metalBondFound) {
      let allConnAtoms = new Int32Array(mAllAtoms);
      for (let atom = 0; atom < this.mAllAtoms; atom++)
        allConnAtoms[atom] = this.mAllConnAtoms[atom];

      for (let bnd = 0; bnd < this.mAllBonds; bnd++) {
        let order = this.getBondOrder(bnd);
        if (order == 0) {
          for (let i = 0; i < 2; i++) {
            let atom = this.mBondAtom[i][bnd];
            this.mConnBondOrder[atom][allConnAtoms[atom]] = order;
            this.mConnAtom[atom][allConnAtoms[atom]] =
              this.mBondAtom[1 - i][bnd];
            this.mConnBond[atom][allConnAtoms[atom]] = bnd;
            allConnAtoms[atom]++; // all neighbours (non-H, H, metal-bonded)
          }
        }
      }
    }
  }

  validateQueryFeatures() {
    // returns true if hydrogens were deleted and, thus, mConnAtoms are invalid
    if (!this.mIsFragment) return false;

    // if an atom has no free valence then cAtomQFNoMoreNeighbours is not necessary
    // and cAtomQFMoreNeighbours is not possible
    // unless it is an uncharged N- or O-family atom that could be e.g. methylated
    for (let atom = 0; atom < this.mAllAtoms; atom++) {
      if (
        this.getFreeValence(atom) <= 0 &&
        !(
          this.mAtomCharge[atom] == 0 &&
          (this.mAtomicNo[atom] == 5 ||
            this.isNitrogenFamily(atom) ||
            this.isChalcogene(atom))
        )
      )
        this.mAtomQueryFeatures[atom] &= ~(
          Molecule.cAtomQFNoMoreNeighbours | Molecule.cAtomQFMoreNeighbours
        );
    }

    // approximate explicit hydrogens by query features
    // and remove explicit hydrogens except those with stereo bonds
    let deleteHydrogens = false;
    for (let atom = 0; atom < this.mAtoms; atom++) {
      let explicitHydrogens = this.getExplicitHydrogens(atom);
      if (!this.mProtectHydrogen && explicitHydrogens > 0) {
        if (
          (this.mAtomQueryFeatures[atom] & Molecule.cAtomQFNoMoreNeighbours) ==
          0
        ) {
          // add query feature hydrogen to explicit hydrogens
          let queryFeatureHydrogens =
            (this.mAtomQueryFeatures[atom] & Molecule.cAtomQFHydrogen) ==
            (Molecule.cAtomQFNot0Hydrogen |
              Molecule.cAtomQFNot1Hydrogen |
              Molecule.cAtomQFNot2Hydrogen)
              ? 3
              : (this.mAtomQueryFeatures[atom] & Molecule.cAtomQFHydrogen) ==
                (Molecule.cAtomQFNot0Hydrogen | Molecule.cAtomQFNot1Hydrogen)
              ? 2
              : (this.mAtomQueryFeatures[atom] &
                  Molecule.cAtomQFNot0Hydrogen) ==
                Molecule.cAtomQFNot0Hydrogen
              ? 1
              : 0;

          // For atoms with no specific charge definition a potential charge is not considered by getFreeValence().
          // Non-carbon atoms with a charge may have an increased valence. We need to consider that.
          let freeValence = this.getFreeValence(atom);
          if (
            this.mAtomCharge[atom] == 0 &&
            (this.mAtomQueryFeatures[atom] & Molecule.cAtomQFCharge) == 0 &&
            this.mAtomicNo[atom] != 6
          )
            freeValence++;

          let queryFeatureShift = explicitHydrogens;
          if (queryFeatureShift > 3 - queryFeatureHydrogens)
            queryFeatureShift = 3 - queryFeatureHydrogens;
          if (
            queryFeatureShift >
            freeValence + explicitHydrogens - queryFeatureHydrogens
          )
            queryFeatureShift =
              freeValence + explicitHydrogens - queryFeatureHydrogens;

          if (queryFeatureShift > 0) {
            let queryFeatures =
              queryFeatureHydrogens == 0 // purge 'less than' options
                ? 0
                : (this.mAtomQueryFeatures[atom] & Molecule.cAtomQFHydrogen) <<
                  queryFeatureShift;
            queryFeatures |=
              (queryFeatureShift == 3 ? 7 : explicitHydrogens == 2 ? 3 : 1) <<
              Molecule.cAtomQFHydrogenShift;

            this.mAtomQueryFeatures[atom] &= ~Molecule.cAtomQFHydrogen;
            this.mAtomQueryFeatures[atom] |=
              Molecule.cAtomQFHydrogen & queryFeatures;
          }
        }

        for (let i = this.mConnAtoms[atom]; i < this.mAllConnAtoms[atom]; i++) {
          let connBond = this.mConnBond[atom][i];
          if (this.mBondType[connBond] == Molecule.cBondTypeSingle) {
            // no stereo bond
            this.mAtomicNo[this.mConnAtom[atom][i]] = -1;
            this.mBondType[connBond] = Molecule.cBondTypeDeleted;
            deleteHydrogens = true;
          }
        }
      }

      if ((this.mAtomQueryFeatures[atom] & Molecule.cAtomQFAromatic) != 0)
        this.mAtomQueryFeatures[atom] &= ~Molecule.cAtomQFNotChain;

      if (this.mAtomCharge[atom] != 0)
        // explicit charge superceeds query features
        this.mAtomFlags[atom] &= ~Molecule.cAtomQFCharge;
    }
    if (deleteHydrogens) this.compressMolTable();

    return deleteHydrogens;
  }

  getBondOrder(bond) {
    switch (this.mBondType[bond] & Molecule.cBondTypeMaskSimple) {
      case Molecule.cBondTypeSingle:
      case Molecule.cBondTypeDelocalized:
        return 1;
      case Molecule.cBondTypeDouble:
        return 2;
      case Molecule.cBondTypeTriple:
        return 3;
      default:
        return 0; // dative bonds, ligand field bonds
    }
  }

  getLowestFreeValence(atom) {
    let occupiedValence = this.getOccupiedValence(atom);
    let correction = this.getElectronValenceCorrection(atom, occupiedValence);

    let valence = this.getAtomAbnormalValence(atom);
    if (valence == -1) {
      let valenceList =
        this.mAtomicNo[atom] < Molecule.cAtomValence.length
          ? Molecule.cAtomValence[this.mAtomicNo[atom]]
          : null;
      if (valenceList == null) {
        valence = Molecule.cDefaultAtomValence;
      } else {
        let i = 0;
        while (
          occupiedValence > valenceList[i] + correction &&
          i < valenceList.length - 1
        )
          i++;
        valence = valenceList[i];
      }
    }

    return valence + correction - occupiedValence;
  }

  getOccupiedValence(atom) {
    this.ensureHelperArrays(Molecule.cHelperNeighbours);

    let valence = 0;
    for (let i = 0; i < this.mAllConnAtoms[atom]; i++)
      if (
        !this.mIsFragment ||
        (this.mAtomQueryFeatures[this.mConnAtom[atom][i]] &
          Molecule.cAtomQFExcludeGroup) ==
          0
      )
        valence += this.mConnBondOrder[atom][i];

    return valence;
  }

  getElectronValenceCorrection(atom, occupiedValence) {
    if (this.mAtomicNo[atom] >= 171 && this.mAtomicNo[atom] <= 190) return 0;

    let correction = 0;

    if (
      (this.mAtomFlags[atom] & Molecule.cAtomRadicalState) ==
      Molecule.cAtomRadicalStateD
    )
      correction -= 1;
    if (
      (this.mAtomFlags[atom] & Molecule.cAtomRadicalState) ==
        Molecule.cAtomRadicalStateS ||
      (this.mAtomFlags[atom] & Molecule.cAtomRadicalState) ==
        Molecule.cAtomRadicalStateT
    )
      correction -= 2;

    let charge = this.mAtomCharge[atom];
    if (charge == 0 && this.mIsFragment) {
      if (
        (this.mAtomQueryFeatures[atom] & Molecule.cAtomQFCharge) ==
        Molecule.cAtomQFNotCharge0 + Molecule.cAtomQFNotChargePos
      )
        charge = -1;
      if (
        (this.mAtomQueryFeatures[atom] & Molecule.cAtomQFCharge) ==
        Molecule.cAtomQFNotCharge0 + Molecule.cAtomQFNotChargeNeg
      )
        charge = 1;
    }
    if (
      this.mAtomicNo[atom] == 7 || // N
      this.mAtomicNo[atom] == 8 || // O
      this.mAtomicNo[atom] == 9
    )
      // F
      correction += charge;
    else if (
      this.mAtomicNo[atom] == 6 || // C
      this.mAtomicNo[atom] == 14 || // Si
      this.mAtomicNo[atom] == 32
    )
      // Ge
      correction -= Math.abs(charge);
    else if (
      this.mAtomicNo[atom] == 15 || // P
      this.mAtomicNo[atom] == 33
    ) {
      // As
      if (occupiedValence - correction - charge <= 3) correction += charge;
      else correction -= charge;
    } else if (
      this.mAtomicNo[atom] == 16 || // S
      this.mAtomicNo[atom] == 34 || // Se
      this.mAtomicNo[atom] == 52
    ) {
      // Te
      if (occupiedValence - correction - charge <= 4) correction += charge;
      else correction -= Math.abs(charge);
    } else if (
      this.mAtomicNo[atom] == 17 || // Cl
      this.mAtomicNo[atom] == 35 || // Br
      this.mAtomicNo[atom] == 53
    ) {
      // I
      if (occupiedValence - correction - charge <= 5) correction += charge;
      else correction -= Math.abs(charge);
    } else {
      // B, Al, other metals
      correction -= charge;
    }

    return correction;
  }

  isFragment() {
    return this.mIsFragment;
  }

  findRings(mode) {
    this.mRingSet = new RingCollection(this, mode);

    let atomRingBondCount = new Int32Array(this.mAtoms);
    for (let bond = 0; bond < this.mBonds; bond++) {
      if (this.mRingSet.getBondRingSize(bond) != 0) {
        this.mBondFlags[bond] |= Molecule.cBondFlagRing;
        atomRingBondCount[this.mBondAtom[0][bond]]++;
        atomRingBondCount[this.mBondAtom[1][bond]]++;
      }
    }
    for (let atom = 0; atom < this.mAtoms; atom++) {
      if (atomRingBondCount[atom] == 2)
        this.mAtomFlags[atom] |= Molecule.cAtomFlags2RingBonds;
      else if (atomRingBondCount[atom] == 3)
        this.mAtomFlags[atom] |= Molecule.cAtomFlags3RingBonds;
      else if (atomRingBondCount[atom] > 3)
        this.mAtomFlags[atom] |= Molecule.cAtomFlags4RingBonds;
    }

    // the aromaticity flag is not public. Thus, generate it:
    let includeAromaticity =
      (mode &
        RingCollection.MODE_SMALL_RINGS_AND_AROMATICITY &
        ~RingCollection.MODE_SMALL_RINGS_ONLY) !=
      0;

    for (let ringNo = 0; ringNo < this.mRingSet.getSize(); ringNo++) {
      let ringAtom = this.mRingSet.getRingAtoms(ringNo);
      let ringBond = this.mRingSet.getRingBonds(ringNo);
      let ringAtoms = ringAtom.length;
      for (let i = 0; i < ringAtoms; i++) {
        this.mAtomFlags[ringAtom[i]] |= Molecule.cAtomFlagSmallRing;
        this.mBondFlags[ringBond[i]] |= Molecule.cBondFlagSmallRing;

        if (includeAromaticity) {
          if (this.mRingSet.isAromatic(ringNo)) {
            this.mAtomFlags[ringAtom[i]] |= Molecule.cAtomFlagAromatic;
            this.mBondFlags[ringBond[i]] |= Molecule.cBondFlagAromatic;
          }

          if (this.mRingSet.isDelocalized(ringNo))
            this.mBondFlags[ringBond[i]] |= Molecule.cBondFlagDelocalized;
        }

        if (this.mBondType[ringBond[i]] == Molecule.cBondTypeCross)
          this.mBondType[ringBond[i]] = Molecule.cBondTypeDouble;
      }
    }
  }
}

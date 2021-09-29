import Coordinates from './Coordinates.mjs';

export default class Molecule {
  static cMaxAtomicNo = 190;

  static cAtomFlagsParity = 0x000003;
  static cAtomParityNone = 0x000000;
  static cAtomParity1 = 0x000001;
  static cAtomParity2 = 0x000002;
  static cAtomParityUnknown = 0x000003;

  static cAtomParityIsPseudo = 0x000004;
  static cAtomFlagSmallRing = 0x000008;

  static cAtomRadicalState = 0x000030;
  static cAtomRadicalStateShift = 4;
  static cAtomRadicalStateNone = 0x000000;
  static cAtomRadicalStateS = 0x000010;
  static cAtomRadicalStateD = 0x000020;
  static cAtomRadicalStateT = 0x000030;

  static cBondTypeSingle = 0x00000001;
  static cBondTypeDouble = 0x00000002;
  static cBondTypeTriple = 0x00000004;
  static cBondTypeDown = 0x00000009;
  static cBondTypeUp = 0x00000011;
  static cBondTypeCross = 0x0000001a;
  static cBondTypeMetalLigand = 0x00000020;
  static cBondTypeDelocalized = 0x00000040;
  static cBondTypeDeleted = 0x00000080;
  static cBondTypeIncreaseOrder = 0x0000007f;
  static cESRTypeAbs = 0;
  static cESRTypeAnd = 1;
  static cESRTypeOr = 2;
  static cESRMaxGroups = 32;
  static cESRGroupBits = 5;

  static cBondFlagsParity = 0x00000003;
  static cBondParityNone = 0x00000000;
  static cBondParityEor1 = 0x00000001;
  static cBondParityZor2 = 0x00000002;
  static cBondParityUnknown = 0x00000003;
  static cBondParityIsPseudo = 0x00000004;

  static cBondFlagsCIPParity = 0x00000030;
  static cBondFlagsCIPParityShift = 4;
  static cBondCIPParityNone = 0x00000000;
  static cBondCIPParityEorP = 0x00000001;
  static cBondCIPParityZorM = 0x00000002;
  static cBondCIPParityProblem = 0x00000003;

  static cBondFlagRing = 0x00000040;
  static cBondFlagSmallRing = 0x00000080;
  static cBondFlagAromatic = 0x00000100;
  static cBondFlagDelocalized = 0x00000200;
  static cBondFlagsESR = 0x0001fc00;
  static cBondFlagsESRType = 0x00000c00;
  static cBondFlagsESRTypeShift = 10;
  static cBondFlagsESRGroup = 0x0001f000;
  static cBondFlagsESRGroupShift = 12;
  static cBondFlagBGHilited = 0x00020000;
  static cBondFlagFGHilited = 0x00040000;

  static cBondParityUnknownOrNone = 0x1000000;

  static cAtomFlagsValence = 0xf0000000;
  static cAtomFlagsValenceShift = 28;

  static cAtomQFNoOfBits = 30;
  static cAtomQFAromStateBits = 2;
  static cAtomQFAromStateShift = 1;
  static cAtomQFRingStateBits = 4;
  static cAtomQFRingStateShift = 3;
  static cAtomQFHydrogenBits = 4;
  static cAtomQFHydrogenShift = 7;
  static cAtomQFPiElectronBits = 3;
  static cAtomQFPiElectronShift = 14;
  static cAtomQFNeighbourBits = 5;
  static cAtomQFNeighbourShift = 17;
  static cAtomQFRingSizeBits = 3;
  static cAtomQFRingSizeShift = 22;
  static cAtomQFChargeBits = 3;
  static cAtomQFChargeShift = 25;
  static cAtomQFRxnParityBits = 2;
  static cAtomQFRxnParityShift = 30;
  static cAtomQFSimpleFeatures = 0x0e3fc7fe;
  static cAtomQFNarrowing = 0x0e3fc7fe;
  static cAtomQFAny = 0x00000001;
  static cAtomQFAromState = 0x00000006;
  static cAtomQFAromatic = 0x00000002;
  static cAtomQFNotAromatic = 0x00000004;
  static cAtomQFRingState = 0x00000078;
  static cAtomQFNotChain = 0x00000008;
  static cAtomQFNot2RingBonds = 0x00000010;
  static cAtomQFNot3RingBonds = 0x00000020;
  static cAtomQFNot4RingBonds = 0x00000040;
  static cAtomQFHydrogen = 0x00000780;
  static cAtomQFNot0Hydrogen = 0x00000080;
  static cAtomQFNot1Hydrogen = 0x00000100;
  static cAtomQFNot2Hydrogen = 0x00000200;
  static cAtomQFNot3Hydrogen = 0x00000400;
  static cAtomQFNoMoreNeighbours = 0x00000800;
  static cAtomQFMoreNeighbours = 0x00001000;
  static cAtomQFMatchStereo = 0x00002000;
  static cAtomQFPiElectrons = 0x0001c000;
  static cAtomQFNot0PiElectrons = 0x00004000;
  static cAtomQFNot1PiElectron = 0x00008000;
  static cAtomQFNot2PiElectrons = 0x00010000;
  static cAtomQFNeighbours = 0x003e0000; // these QF refer to non-H neighbours
  static cAtomQFNot0Neighbours = 0x00020000;
  static cAtomQFNot1Neighbour = 0x00040000;
  static cAtomQFNot2Neighbours = 0x00080000;
  static cAtomQFNot3Neighbours = 0x00100000;
  static cAtomQFNot4Neighbours = 0x00200000; // this is not 4 or more neighbours
  static cAtomQFRingSize = 0x01c00000;
  static cAtomQFCharge = 0x0e000000;
  static cAtomQFNotChargeNeg = 0x02000000;
  static cAtomQFNotCharge0 = 0x04000000;
  static cAtomQFNotChargePos = 0x08000000;
  static cAtomQFFlatNitrogen = 0x10000000; // currently only used in TorsionDetail
  static cAtomQFExcludeGroup = 0x20000000; // these atoms must not exist in SS-matches
  static cAtomQFRxnParityHint = 0xc0000000; // Retain,invert,racemise configuration in reaction
  static cAtomQFRxnParityRetain = 0x40000000; // Retain,invert,racemise configuration in reaction
  static cAtomQFRxnParityInvert = 0x80000000; // Retain,invert,racemise configuration in reaction
  static cAtomQFRxnParityRacemize = 0xc0000000; // Retain,invert,racemise configuration in reaction

  static cChiralityIsomerCountMask = 0x00ffff;
  static cChiralityUnknown = 0x000000;
  static cChiralityNotChiral = 0x010000;
  static cChiralityMeso = 0x020000; // this has added the number of meso isomers
  static cChiralityRacemic = 0x030000;
  static cChiralityKnownEnantiomer = 0x040000;
  static cChiralityUnknownEnantiomer = 0x050000;
  static cChiralityEpimers = 0x060000;
  static cChiralityDiastereomers = 0x070000; // this has added the number of diastereomers

  static cBondQFNoOfBits = 21;
  static cBondQFBondTypesBits = 5;
  static cBondQFBondTypesShift = 0;
  static cBondQFRingStateBits = 2;
  static cBondQFRingStateShift = 5;
  static cBondQFBridgeBits = 8;
  static cBondQFBridgeShift = 7;
  static cBondQFBridgeMinBits = 4;
  static cBondQFBridgeMinShift = 7;
  static cBondQFBridgeSpanBits = 4;
  static cBondQFBridgeSpanShift = 11;
  static cBondQFRingSizeBits = 3;
  static cBondQFRingSizeShift = 15;
  static cBondQFAromStateBits = 2;
  static cBondQFAromStateShift = 19;
  static cBondQFAllFeatures = 0x001fffff;
  static cBondQFSimpleFeatures = 0x0018007f;
  static cBondQFNarrowing = 0x00180060;
  static cBondQFBondTypes = 0x0000001f;
  static cBondQFSingle = 0x00000001;
  static cBondQFDouble = 0x00000002;
  static cBondQFTriple = 0x00000004;
  static cBondQFDelocalized = 0x00000008;
  static cBondQFMetalLigand = 0x00000010;
  static cBondQFRingState = 0x00000060;
  static cBondQFNotRing = 0x00000020;
  static cBondQFRing = 0x00000040;
  static cBondQFBridge = 0x00007f80;
  static cBondQFBridgeMin = 0x00000780;
  static cBondQFBridgeSpan = 0x00007800;
  static cBondQFRingSize = 0x00038000;
  static cBondQFMatchStereo = 0x00040000;
  static cBondQFAromState = 0x00180000;
  static cBondQFAromatic = 0x00080000;
  static cBondQFNotAromatic = 0x00100000;

  static cBondTypeSingle = 0x00000001;
  static cBondTypeDouble = 0x00000002;
  static cBondTypeTriple = 0x00000004;
  static cBondTypeDown = 0x00000009;
  static cBondTypeUp = 0x00000011;
  static cBondTypeCross = 0x0000001a;
  static cBondTypeMetalLigand = 0x00000020;
  static cBondTypeDelocalized = 0x00000040;
  static cBondTypeDeleted = 0x00000080;
  static cBondTypeIncreaseOrder = 0x0000007f;

  static cHelperNone = 0x0000;
  static cHelperBitNeighbours = 0x0001;
  static cHelperBitRingsSimple = 0x0002; // small rings only, no aromaticity, no allylic nor stabilized flags
  static cHelperBitRings = 0x0004;
  static cHelperBitParities = 0x0008;
  static cHelperBitCIP = 0x0010;

  static cHelperBitsStereo = 0x01f8;

  static cHelperNeighbours = this.cHelperBitNeighbours;
  static cHelperRingsSimple =
    this.cHelperNeighbours | this.cHelperBitRingsSimple;
  static cHelperRings = this.cHelperRingsSimple | this.cHelperBitRings;
  static cHelperParities = this.cHelperRings | this.cHelperBitParities;
  static cHelperCIP = this.cHelperParities | this.cHelperBitCIP;

  static getDefaultAverageBondLength() {
    return 24;
  }

  mMaxAtoms = 0;
  mMaxBonds = 0;

  mValidHelperArrays = 0;
  mAllAtoms = 0;
  mAllBonds = 0;
  mAtomicNo = null;
  mAtomCharge = null;
  mAtomMapNo = null;
  mAtomMass = null;
  mAtomFlags = null;
  mAtomQueryFeatures = null;
  mBondAtom = null;
  mBondType = null;
  mBondFlags = null;
  mBondQueryFeatures = null;
  mCoordinates = null;
  mIsFragment = false;
  mIsRacemate = false;
  mProtectHydrogen = false;
  mChirality = 0;
  mAtomList = null;
  mAtomCustomLabel = null;

  mMoleculeColor = 0;
  mZoomRotationX = 0;
  mZoomRotationY = 0;
  mOriginalAngle = null;
  mOriginalDistance = null;
  mName = null;
  mUserData = null;

  constructor(maxAtoms, maxBonds) {
    this.mMaxAtoms = Math.max(1, maxAtoms);
    this.mMaxBonds = Math.max(1, maxBonds);
    this.init();
  }

  init() {
    this.mValidHelperArrays = Molecule.cHelperNone;
    this.mAtomicNo = new Int32Array(this.mMaxAtoms);
    this.mAtomCharge = new Int32Array(this.mMaxAtoms);
    this.mAtomMapNo = new Int32Array(this.mMaxAtoms);
    this.mCoordinates = [];
    for (let i = 0; i < this.mMaxAtoms; i++) {
      this.mCoordinates.push(new Coordinates());
    }
    this.mAtomMass = new Int32Array(this.mMaxAtoms);
    this.mAtomFlags = new Int32Array(this.mMaxAtoms);
    this.mAtomQueryFeatures = new Int32Array(this.mMaxAtoms);
    this.mAtomList = null;
    this.mAtomCustomLabel = null;
    this.mBondAtom = [
      new Int32Array(this.mMaxBonds),
      new Int32Array(this.mMaxBonds),
    ];
    this.mBondType = new Int32Array(this.mMaxBonds);
    this.mBondFlags = new Int32Array(this.mMaxBonds);
    this.mBondQueryFeatures = new Int32Array(this.mMaxBonds);
  }

  clear() {
    this.mAllAtoms = 0;
    this.mAllBonds = 0;
    this.mIsFragment = false;
    this.mIsRacemate = false;
    this.mChirality = Molecule.cChiralityUnknown;
    this.mAtomList = null;
    this.mAtomCustomLabel = null;
    this.mName = null;
    this.mValidHelperArrays = Molecule.cHelperNone;
  }

  addAtom(atomicNo) {
    if (this.mAllAtoms >= this.mMaxAtoms) this.setMaxAtoms(mMaxAtoms * 2);

    this.mAtomicNo[this.mAllAtoms] = 0; // default
    this.setAtomicNo(this.mAllAtoms, atomicNo); // sets atomicNo and mass

    this.mAtomCharge[this.mAllAtoms] = 0;
    this.mAtomFlags[this.mAllAtoms] = 0;
    this.mAtomQueryFeatures[this.mAllAtoms] = 0;
    this.mAtomMapNo[this.mAllAtoms] = 0;
    this.mCoordinates[this.mAllAtoms].set(0, 0, 0);

    if (this.mAtomList != null) this.mAtomList[this.mAllAtoms] = null;
    if (this.mAtomCustomLabel != null)
      this.mAtomCustomLabel[this.mAllAtoms] = null;

    this.mValidHelperArrays = Molecule.cHelperNone;
    return this.mAllAtoms++;
  }

  setMaxAtoms(v) {
    this.mAtomicNo = copyOfInt(this.mAtomicNo, v);
    this.mAtomCharge = copyOfInt(this.mAtomCharge, v);
    this.mAtomMapNo = copyOfInt(this.mAtomMapNo, v);
    let orig = this.mCoordinates.length;
    this.mCoordinates = mCoordinates.slice(0, v);
    for (let i = orig; i < v; i++) {
      this.mCoordinates.push(new Coordinates());
    }
    this.mAtomMass = copyOfInt(this.mAtomMass, v);
    this.mAtomFlags = copyOfInt(this.mAtomFlags, v);
    this.mAtomQueryFeatures = copyOfInt(this.mAtomQueryFeatures, v);
    if (this.mAtomList != null)
      this.mAtomList = copyOfIntInt(this.mAtomList, v);
    if (this.mAtomCustomLabel != null)
      this.mAtomCustomLabel = copyOfByteByte(this.mAtomCustomLabel, v);
    this.mMaxAtoms = v;
  }

  setAtomicNo(atom, no) {
    if (no >= 0 && no <= Molecule.cMaxAtomicNo) {
      if (no == 151 || no == 152) {
        // 'D' or 'T'
        this.mAtomicNo[atom] = 1;
        this.mAtomMass[atom] = no - 149;
      } else {
        this.mAtomicNo[atom] = no;
        this.mAtomMass[atom] = 0;
      }
      this.mAtomFlags[atom] &= ~Molecule.cAtomFlagsValence;

      this.mValidHelperArrays = Molecule.cHelperNone;
    }
  }

  setAtomX(atom, x) {
    this.mCoordinates[atom].x = x;
    this.mValidHelperArrays &= Molecule.cHelperRings;
  }

  setAtomY(atom, y) {
    this.mCoordinates[atom].y = y;
    this.mValidHelperArrays &= Molecule.cHelperRings;
  }

  setAtomZ(atom, z) {
    this.mCoordinates[atom].z = z;
    this.mValidHelperArrays &= Molecule.cHelperRings;
  }

  addBond(atom1, atom2, type) {
    if (atom1 === atom2) {
      return -1;
    }

    for (let bnd = 0; bnd < this.mAllBonds; bnd++) {
      if (
        (this.mBondAtom[0][bnd] == atom1 && this.mBondAtom[1][bnd] == atom2) ||
        (this.mBondAtom[0][bnd] == atom2 && this.mBondAtom[1][bnd] == atom1)
      ) {
        if (this.mBondType[bnd] < type) this.mBondType[bnd] = type;
        return bnd;
      }
    }

    if (this.mAllBonds >= this.mMaxBonds) this.setMaxBonds(this.mMaxBonds * 2);

    this.mBondAtom[0][this.mAllBonds] = atom1;
    this.mBondAtom[1][this.mAllBonds] = atom2;
    this.mBondType[this.mAllBonds] = type;
    this.mBondFlags[this.mAllBonds] = 0;
    this.mBondQueryFeatures[this.mAllBonds] = 0;
    this.mValidHelperArrays = Molecule.cHelperNone;
    //		checkAtomParity(atm1);
    //		checkAtomParity(atm2);
    return this.mAllBonds++;
  }

  setMaxBonds() {
    this.mBondAtom[0] = copyOfInt(mBondAtom[0], v);
    this.mBondAtom[1] = copyOfInt(mBondAtom[1], v);
    this.mBondType = copyOfInt(mBondType, v);
    this.mBondFlags = copyOfInt(mBondFlags, v);
    this.mBondQueryFeatures = copyOfInt(mBondQueryFeatures, v);
    this.mMaxBonds = v;
  }

  setBondType(bond, type) {
    this.mBondType[bond] = type;
    this.mValidHelperArrays = Molecule.cHelperNone;
  }

  setFragment(isFragment) {
    if (this.mIsFragment !== isFragment) {
      this.mIsFragment = isFragment;
      if (!isFragment) {
        this.removeQueryFeatures();
      }
      this.mValidHelperArrays = Molecule.cHelperNone;
    }
  }

  setAtomParity(atom, parity, isPseudo) {
    this.mAtomFlags[atom] &= ~(
      Molecule.cAtomFlagsParity | Molecule.cAtomParityIsPseudo
    );
    this.mAtomFlags[atom] |= parity;
    if (isPseudo) this.mAtomFlags[atom] |= Molecule.cAtomParityIsPseudo;
  }

  setAtomCharge(atom, charge) {
    this.mAtomCharge[atom] = charge;
    this.mValidHelperArrays = Molecule.cHelperNone;
  }

  setAtomAbnormalValence(atom, valence) {
    if (valence >= -1 && valence <= 14) {
      this.mAtomFlags[atom] &= ~Molecule.cAtomFlagsValence;
      this.mAtomFlags[atom] |= (1 + valence) << Molecule.cAtomFlagsValenceShift;

      if (this.mAtomicNo[atom] == 6) {
        if (valence == -1 || valence == 0 || valence == 2 || valence == 4) {
          this.mAtomFlags[atom] &= ~Molecule.cAtomRadicalState;
          if (valence == 2)
            this.mAtomFlags[atom] |= Molecule.cAtomRadicalStateS;
        }
      }
    }
  }

  setAtomMass(atom, mass) {
    this.mAtomMass[atom] = mass;
    this.mValidHelperArrays &= Molecule.cHelperRings;
  }

  getBondType(bond) {
    return this.mBondType[bond];
  }

  setBondParity(bond, parity, isPseudo) {
    this.mBondFlags[bond] &= ~(
      Molecule.cBondFlagsParity |
      Molecule.cBondParityIsPseudo |
      Molecule.cBondParityUnknownOrNone
    );
    this.mBondFlags[bond] |= parity;
    if (isPseudo) this.mBondFlags[bond] |= Molecule.cBondParityIsPseudo;
  }

  setAtomQueryFeature(atom, feature, value) {
    if (value) this.mAtomQueryFeatures[atom] |= feature;
    else this.mAtomQueryFeatures[atom] &= ~feature;
    this.mValidHelperArrays = 0; // there is an influence on occipied valence, bond order, etc.
    this.mIsFragment = true;
  }

  setAtomRadical(atom, radical) {
    this.mAtomFlags[atom] &= ~Molecule.cAtomRadicalState;
    this.mAtomFlags[atom] |= radical;
    this.mValidHelperArrays &= Molecule.cHelperRings;
  }

  removeQueryFeatures() {
    throw new Error('missing');
  }
}

function copyOfInt(original, newLength) {
  if (newLength < original.length) {
    return original.slice(0, newLength);
  } else {
    const copy = new Int32Array(newLength);
    copy.set(original, 0);
    return copy;
  }
}

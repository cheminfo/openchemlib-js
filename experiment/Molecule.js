import Coordinates from './Coordinates.js';

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

  static cAtomFlagsColor = 0x0001c0;
  static cAtomColorNone = 0x000000;
  static cAtomColorBlue = 0x000040;
  static cAtomColorRed = 0x000080;
  static cAtomColorGreen = 0x0000c0;
  static cAtomColorMagenta = 0x000100;
  static cAtomColorOrange = 0x000140;
  static cAtomColorDarkGreen = 0x000180;
  static cAtomColorDarkRed = 0x0001c0;
  static cAtomFlagSelected = 0x000200;

  static cAtomFlagsHelper = 0x0003fc0f;
  static cAtomFlagsHelper2 = 0x00007c08;
  static cAtomFlagsHelper3 = 0x08038007;

  static cAtomFlagsRingBonds = 0x000c00;
  static cAtomFlags2RingBonds = 0x000400;
  static cAtomFlags3RingBonds = 0x000800;
  static cAtomFlags4RingBonds = 0x000c00;
  static cAtomFlagAromatic = 0x001000;
  static cAtomFlagAllylic = 0x002000;
  static cAtomFlagStabilized = 0x004000;

  static cAtomFlagsCIPParity = 0x018000;
  static cAtomFlagsCIPParityShift = 15;
  static cAtomCIPParityNone = 0x000000;
  static cAtomCIPParityRorM = 0x000001;
  static cAtomCIPParitySorP = 0x000002;
  static cAtomCIPParityProblem = 0x000003;

  static cAtomFlagStereoProblem = 0x020000;
  static cAtomFlagMarked = 0x040000;

  // MDL's enhanced stereochemical representation (ESR group and type may be assigned
  // to TH and allene stereo centers as well as to BINAP kind of stereo bonds)
  static cESRTypeAbs = 0;
  static cESRTypeAnd = 1;
  static cESRTypeOr = 2;
  static cESRMaxGroups = 32;
  static cESRGroupBits = 5;

  static cAtomFlagsESR = 0x03f80000;
  static cAtomFlagsESRType = 0x00180000;
  static cAtomFlagsESRTypeShift = 19;
  static cAtomFlagsESRGroup = 0x03e00000;
  static cAtomFlagsESRGroupShift = 21;

  static cAtomFlagConfigurationUnknown = 0x04000000;
  static cAtomFlagIsStereoCenter = 0x08000000;

  static cAtomFlagsValence = 0xf0000000;
  static cAtomFlagsValenceShift = 28;

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

  static cBondTypeMaskSimple = 0x00000067; // masks
  static cBondTypeMaskStereo = 0x00000018;

  static cBondFlagsHelper2 = 0x000003c0;
  static cBondFlagsHelper3 = 0x0000003f;

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

  static cDefaultAtomValence = 6;
  // prettier-ignore
  static  cAtomValence = [null,
			[1], [0], [1], [2], [3], [4], [3], [2], [1], [0],			// H to Ne
			[1], [2], [3], [4], [3, 5], [2, 4, 6], [1, 3, 5, 7], [0],	// Na to Ar
			[1], [2], null, null, null, null, null, null, null, null,	// K to Ni
			null, null, [2, 3], [2, 4], [3, 5], [2, 4, 6], [1, 3, 5, 7], [0, 2], // Cu to Kr
			[1], [2], null, null, null, null, null, null, null, null,	// Rb to Pd
			null, null, [1, 2, 3], [2, 4], [3, 5], [2, 4, 6], [1, 3, 5, 7], // Ag to I
			[0, 2, 4, 6], [1], [2],										// Xe to Ba
			null, null, null, null, null, null, null, null, null, null, // La to Dy
			null, null, null, null, null, null, null, null, null, null, // Ho to Os
			null, null, null, null, null, null, null, null, null, null, // Ir to Rn
			null, null, null, null, null, null, null, null, null, null, // Fr to Cm
			null, null, null, null, null, null, null, null, null, null, // Bk to Sg
			null, null, null, null, null, null, null, null, null, null, // Bh to Lv
			null, null, null, null, null, null, null, null, null, null, // Ts to 126
			null, null, null, null, null, null, null, null, null, null,	// 127 to R5
			null, null, null, null, null, null, null, null, null, null, // R6 to R15
			null, null, null, null, null, null, null, null, null, null,	// R16 to 156
			null, null, null, null, null, null, null, null, null, null, // D to 166
			null, null, null, null,										// 167 to 170
			[2], [2], [2], [2], [3], [2], [2], [2], [2], [2],			// Ala to Ile
			[2], [2], [2], [2], [2], [2], [2], [2], [2], [2],			// Leu to Val
	];

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
    if (this.mAllAtoms >= this.mMaxAtoms) this.setMaxAtoms(this.mMaxAtoms * 2);

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
    this.mCoordinates = this.mCoordinates.slice(0, v);
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

  getBondAtom(no, bond) {
    return this.mBondAtom[no][bond];
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

  setMaxBonds(v) {
    this.mBondAtom[0] = copyOfInt(this.mBondAtom[0], v);
    this.mBondAtom[1] = copyOfInt(this.mBondAtom[1], v);
    this.mBondType = copyOfInt(this.mBondType, v);
    this.mBondFlags = copyOfInt(this.mBondFlags, v);
    this.mBondQueryFeatures = copyOfInt(this.mBondQueryFeatures, v);
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

  getAtomAbnormalValence(atom) {
    return (
      ((this.mAtomFlags[atom] & Molecule.cAtomFlagsValence) >>>
        Molecule.cAtomFlagsValenceShift) -
      1
    );
  }

  getMaxAtoms() {
    return this.mMaxAtoms;
  }

  getAtomicNo(atom) {
    return this.mAtomicNo[atom];
  }

  getAtomCharge(atom) {
    return this.mAtomCharge[atom];
  }

  getMaxValence(atom) {
    let valence = this.getMaxValenceUncharged(atom);
    return valence + this.getElectronValenceCorrection(atom, valence);
  }

  getMaxValenceUncharged(atom) {
    let valence = this.getAtomAbnormalValence(atom);

    if (valence == -1) valence = this.getDefaultMaxValenceUncharged(atom);

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

  getDefaultMaxValenceUncharged(atom) {
    let valenceList =
      this.mAtomicNo[atom] < Molecule.cAtomValence.length
        ? Molecule.cAtomValence[this.mAtomicNo[atom]]
        : null;
    return valenceList == null
      ? Molecule.cDefaultAtomValence
      : valenceList[valenceList.length - 1];
  }

  isMetalAtom(atom) {
    let atomicNo = this.mAtomicNo[atom];
    return (
      (atomicNo >= 3 && atomicNo <= 4) ||
      (atomicNo >= 11 && atomicNo <= 13) ||
      (atomicNo >= 19 && atomicNo <= 31) ||
      (atomicNo >= 37 && atomicNo <= 51) ||
      (atomicNo >= 55 && atomicNo <= 84) ||
      (atomicNo >= 87 && atomicNo <= 103)
    );
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

export default class Molecule {
  static cAtomParityNone = 0x000000;
  static cAtomParity1 = 0x000001;
  static cAtomParity2 = 0x000002;
  static cAtomParityUnknown = 0x000003;

  static cAtomParityIsPseudo = 0x000004;
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
  static cBondParityNone = 0x00000000;
  static cBondParityEor1 = 0x00000001;
  static cBondParityZor2 = 0x00000002;
  static cBondParityUnknown = 0x00000003;
  static cBondParityIsPseudo = 0x00000004;

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

  static cAtomRadicalState = 0x000030;
  static cAtomRadicalStateShift = 4;
  static cAtomRadicalStateNone = 0x000000;
  static cAtomRadicalStateS = 0x000010;
  static cAtomRadicalStateD = 0x000020;
  static cAtomRadicalStateT = 0x000030;

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

  constructor(maxAtoms, maxBonds) {
    this.mMaxAtoms = Math.max(1, maxAtoms);
    this.mMaxBonds = Math.max(1, maxBonds);
    this.init();
  }
}

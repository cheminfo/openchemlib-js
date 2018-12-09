// Minimal API

export interface IMoleculeFromSmilesOptions {
  /**
   * Disable extra coordinate computation (default: false).
   */
  noCoordinates?: boolean;
  /**
   * Disable stereo features parsing (default: false).
   */
  noStereo?: boolean;
}

export interface IMoleculeToSVGOptions extends IDepictorOptions {
  /**
   * Default: 1
   */
  factorTextSize?: number;
  /**
   * font-weight attribute of atom labels.
   */
  fontWeight?: string;
  /**
   * stroke-width styling property of bonds.
   */
  strokeWidth?: string;
}

export interface IHoseCodesOptions {
  /**
   * Maximum number of atoms from the center (default: 5).
   */
  maxSphereSize: number;
  /**
   * 1: stop if Csp3-Csp3, 0: normal hose code (default: 0).
   */
  type: 0 | 1;
}

export interface Rectangle {
  x: number;
  y: number;
  width: number;
  height: number;
}

export declare class Molecule {
  constructor(maxAtoms: number, maxBonds: number);

  static CANONIZER_CREATE_SYMMETRY_RANK: number;
  static CANONIZER_CONSIDER_DIASTEREOTOPICITY: number;
  static CANONIZER_CONSIDER_ENANTIOTOPICITY: number;
  static CANONIZER_CONSIDER_STEREOHETEROTOPICITY: number;
  static CANONIZER_ENCODE_ATOM_CUSTOM_LABELS: number;
  static CANONIZER_ENCODE_ATOM_SELECTION: number;
  static CANONIZER_ASSIGN_PARITIES_TO_TETRAHEDRAL_N: number;
  static CANONIZER_COORDS_ARE_3D: number;
  static CANONIZER_CREATE_PSEUDO_STEREO_GROUPS: number;
  static CANONIZER_DISTINGUISH_RACEMIC_OR_GROUPS: number;
  static cMaxAtomicNo: number;
  static cAtomParityNone: number;
  static cAtomParity1: number;
  static cAtomParity2: number;
  static cAtomParityUnknown: number;
  static cAtomParityIsPseudo: number;
  static cAtomRadicalState: number;
  static cAtomRadicalStateShift: number;
  static cAtomRadicalStateNone: number;
  static cAtomRadicalStateS: number;
  static cAtomRadicalStateD: number;
  static cAtomRadicalStateT: number;
  static cAtomColorNone: number;
  static cAtomColorBlue: number;
  static cAtomColorRed: number;
  static cAtomColorGreen: number;
  static cAtomColorMagenta: number;
  static cAtomColorOrange: number;
  static cAtomColorDarkGreen: number;
  static cAtomColorDarkRed: number;
  static cAtomCIPParityNone: number;
  static cAtomCIPParityRorM: number;
  static cAtomCIPParitySorP: number;
  static cAtomCIPParityProblem: number;
  static cESRTypeAbs: number;
  static cESRTypeAnd: number;
  static cESRTypeOr: number;
  static cESRMaxGroups: number;
  static cESRGroupBits: number;
  static cAtomQFNoOfBits: number;
  static cAtomQFAromStateBits: number;
  static cAtomQFAromStateShift: number;
  static cAtomQFRingStateBits: number;
  static cAtomQFRingStateShift: number;
  static cAtomQFHydrogenBits: number;
  static cAtomQFHydrogenShift: number;
  static cAtomQFPiElectronBits: number;
  static cAtomQFPiElectronShift: number;
  static cAtomQFNeighbourBits: number;
  static cAtomQFNeighbourShift: number;
  static cAtomQFRingSizeBits: number;
  static cAtomQFRingSizeShift: number;
  static cAtomQFChargeBits: number;
  static cAtomQFChargeShift: number;
  static cAtomQFSimpleFeatures: number;
  static cAtomQFNarrowing: number;
  static cAtomQFAny: number;
  static cAtomQFAromState: number;
  static cAtomQFAromatic: number;
  static cAtomQFNotAromatic: number;
  static cAtomQFRingState: number;
  static cAtomQFNotChain: number;
  static cAtomQFNot2RingBonds: number;
  static cAtomQFNot3RingBonds: number;
  static cAtomQFNot4RingBonds: number;
  static cAtomQFHydrogen: number;
  static cAtomQFNot0Hydrogen: number;
  static cAtomQFNot1Hydrogen: number;
  static cAtomQFNot2Hydrogen: number;
  static cAtomQFNot3Hydrogen: number;
  static cAtomQFNoMoreNeighbours: number;
  static cAtomQFMoreNeighbours: number;
  static cAtomQFMatchStereo: number;
  static cAtomQFPiElectrons: number;
  static cAtomQFNot0PiElectrons: number;
  static cAtomQFNot1PiElectron: number;
  static cAtomQFNot2PiElectrons: number;
  static cAtomQFNeighbours: number;
  static cAtomQFNot0Neighbours: number;
  static cAtomQFNot1Neighbour: number;
  static cAtomQFNot2Neighbours: number;
  static cAtomQFNot3Neighbours: number;
  static cAtomQFNot4Neighbours: number;
  static cAtomQFRingSize: number;
  static cAtomQFCharge: number;
  static cAtomQFNotChargeNeg: number;
  static cAtomQFNotCharge0: number;
  static cAtomQFNotChargePos: number;
  static cAtomQFFlatNitrogen: number;
  static cAtomQFExcludeGroup: number;
  static cBondTypeSingle: number;
  static cBondTypeDouble: number;
  static cBondTypeTriple: number;
  static cBondTypeDown: number;
  static cBondTypeUp: number;
  static cBondTypeCross: number;
  static cBondTypeMetalLigand: number;
  static cBondTypeDelocalized: number;
  static cBondTypeDeleted: number;
  static cBondTypeIncreaseOrder: number;
  static cBondParityNone: number;
  static cBondParityEor1: number;
  static cBondParityZor2: number;
  static cBondParityUnknown: number;
  static cBondCIPParityNone: number;
  static cBondCIPParityEorP: number;
  static cBondCIPParityZorM: number;
  static cBondCIPParityProblem: number;
  static cBondQFNoOfBits: number;
  static cBondQFBondTypesBits: number;
  static cBondQFBondTypesShift: number;
  static cBondQFRingStateBits: number;
  static cBondQFRingStateShift: number;
  static cBondQFBridgeBits: number;
  static cBondQFBridgeShift: number;
  static cBondQFBridgeMinBits: number;
  static cBondQFBridgeMinShift: number;
  static cBondQFBridgeSpanBits: number;
  static cBondQFBridgeSpanShift: number;
  static cBondQFRingSizeBits: number;
  static cBondQFRingSizeShift: number;
  static cBondQFAromStateBits: number;
  static cBondQFAromStateShift: number;
  static cBondQFAllFeatures: number;
  static cBondQFSimpleFeatures: number;
  static cBondQFNarrowing: number;
  static cBondQFBondTypes: number;
  static cBondQFSingle: number;
  static cBondQFDouble: number;
  static cBondQFTriple: number;
  static cBondQFDelocalized: number;
  static cBondQFMetalLigand: number;
  static cBondQFRingState: number;
  static cBondQFNotRing: number;
  static cBondQFRing: number;
  static cBondQFBridge: number;
  static cBondQFBridgeMin: number;
  static cBondQFBridgeSpan: number;
  static cBondQFRingSize: number;
  static cBondQFMatchStereo: number;
  static cBondQFAromState: number;
  static cBondQFAromatic: number;
  static cBondQFNotAromatic: number;
  static cHelperNone: number;
  static cHelperBitNeighbours: number;
  static cHelperBitRings: number;
  static cHelperBitParities: number;
  static cHelperBitCIP: number;
  static cHelperBitSymmetrySimple: number;
  static cHelperBitSymmetryDiastereotopic: number;
  static cHelperBitSymmetryEnantiotopic: number;
  static cHelperBitIncludeNitrogenParities: number;
  static cHelperBitsStereo: number;
  static cHelperNeighbours: number;
  static cHelperRings: number;
  static cHelperParities: number;
  static cHelperCIP: number;
  static cHelperSymmetrySimple: number;
  static cHelperSymmetryDiastereotopic: number;
  static cHelperSymmetryEnantiotopic: number;
  static cChiralityIsomerCountMask: number;
  static cChiralityUnknown: number;
  static cChiralityNotChiral: number;
  static cChiralityMeso: number;
  static cChiralityRacemic: number;
  static cChiralityKnownEnantiomer: number;
  static cChiralityUnknownEnantiomer: number;
  static cChiralityEpimers: number;
  static cChiralityDiastereomers: number;
  static cMoleculeColorDefault: number;
  static cMoleculeColorNeutral: number;
  static cAtomLabel: string[];
  static cRoundedMass: number[];
  static cDefaultAtomValence: number;
  static FISCHER_PROJECTION_LIMIT: number;
  static STEREO_ANGLE_LIMIT: number;
  static cMaxConnAtoms: number;
  static VALIDATION_ERROR_ESR_CENTER_UNKNOWN: string;
  static VALIDATION_ERROR_OVER_UNDER_SPECIFIED: string;
  static VALIDATION_ERROR_AMBIGUOUS_CONFIGURATION: string;
  static VALIDATION_ERRORS_STEREO: string[];

  /**
   * Parse the provided `smiles` and return a `Molecule`.
   * By default, stereo features are parsed, which triggers itself a coordinate
   * computation and coordinates are computed again after parsing to guarantee that
   * they are always the same.
   * If you do not need stereo features and want the fastest parsing, use this method
   * with `{noCoordinates: true, noStereo: true}`.
   * @param smiles
   * @param options
   */
  static fromSmiles(
    smiles: string,
    options?: IMoleculeFromSmilesOptions
  ): Molecule;
  /**
   * Parse the provided `molfile` and return a `Molecule`.
   * @param molfile - MDL Molfile string in V2000 or V3000
   */
  static fromMolfile(molfile: string): Molecule;
  /**
   * Parse the provided `molfile` and return an object with `Molecule` and map.
   * @param molfile - MDL Molfile string in V2000 or V3000.
   */
  static fromMolfileWithAtomMap(
    molfile: string
  ): { molecule: Molecule; map: number[] };
  /**
   * Parse the provided `idcode` and return a `Molecule`.
   * @param idcode
   * @param coordinates
   */
  static fromIDCode(idcode: string, coordinates: string): Molecule;
  /**
   * Parse the provided `idcode` and return a `Molecule`.
   * @param idcode
   * @param ensure2DCoordinates - boolean indicating if the 2D coordinates
   * should be computed (default: `true`).
   */
  static fromIDCode(idcode: string, ensure2DCoordinates?: boolean): Molecule;

  static getAtomicNoFromLabel(atomLabel: string): number;
  static getAngle(x1: number, y1: number, x2: number, y2: number): number;
  static getAngleDif(angle1: number, angle2: number): number;
  static getDefaultAverageBondLength(): number;
  static setDefaultAverageBondLength(defaultAVBL: number): void;
  static isAtomicNoElectronegative(atomicNo: number): boolean;
  static isAtomicNoElectropositive(atomicNo: number): boolean;

  toSmiles(): string;
  toIsomericSmiles(): string;
  /**
   * Returns a MDL Molfile V2000 string.
   */
  toMolfile(): string;
  /**
   * Returns a MDL Molfile V3000 string.
   */
  toMolfileV3(): string;
  /**
   * Returns an SVG string representing the structure in two dimensions.
   * @param width
   * @param height
   * @param id - Id attribute of the resulting svg element. Defaults to "id"
   * followed by an automatically incremented number.
   * @param options
   */
  toSVG(
    width: number,
    height: number,
    id?: string,
    options?: IMoleculeToSVGOptions
  ): string;
  getCanonizedIDCode(flag: number): string;
  /**
   * Returns an object with both the ID code and coordinates of the molecule.
   */
  getIDCodeAndCoordinates(): { idCode: string; coordinates: string };
  getMolecularFormula(): MolecularFormula;
  /**
   * Returns the `int[]` index array that can be used for substructure search.
   */
  getIndex(): number[];
  /**
   * Compute and set atom coordinates for this molecule.
   */
  inventCoordinates(): void;
  /**
   * Expand and find a position for all the hydrogens of the 2D molecule. If
   * `atomNumber` is specified, the function only applies for the hydrogens of
   * the given atom.
   */
  addImplicitHydrogens(atomNumber?: number): void;
  /**
   * Returns the count of hydrogens in the molecule.
   */
  getNumberOfHydrogens(): number;
  /**
   * Returns the diastereotopic Ids of all the atoms in the molecule.
   */
  getDiastereotopicAtomIDs(): string[];
  addMissingChirality(esrType?: number): void;
  /**
   * This function returns an array of HOSE(Hierarchical Organisation of
   * Spherical Environments) codes represented as diastereotopic actelion IDs.
   * @param options
   */
  getHoseCodes(options?: IHoseCodesOptions): string[][];
  getRingSet(): RingCollection;

  /**
   * Returns the rectangle the bounds the molecule.
   */
  getBounds(): Rectangle;

  addAtom(atomicNo: number): number;
  suggestBondType(atom1: number, atom2: number): number;
  addBond(atom1: number, atom2: number): number;
  addOrChangeAtom(
    x: number,
    y: number,
    atomicNo: number,
    mass: number,
    abnormalValence: number,
    radical: number,
    customLabel: string
  ): boolean;
  addOrChangeBond(atm1: number, atm2: number, type: number): number;
  addRing(x: number, y: number, ringSize: number, aromatic: boolean): boolean;
  addRingToAtom(atom: number, ringSize: number, aromatic: boolean): boolean;
  addRingToBond(bond: number, ringSize: number, aromatic: boolean): boolean;
  changeAtom(
    atom: number,
    atomicNo: number,
    mass: number,
    abnormalValence: number,
    radical: number
  ): boolean;
  changeAtomCharge(atom: number, positive: boolean): boolean;
  changeBond(bnd: number, type: number): boolean;
  addMolecule(mol: Molecule): number[];
  addSubstituent(substituent: Molecule, connectionAtom: number): number[];
  copyMolecule(destMol: Molecule): void;
  copyAtom(
    destMol: Molecule,
    sourceAtom: number,
    esrGroupOffsetAND: number,
    esrGroupOffsetOR: number
  ): number;
  copyBond(
    destMol: Molecule,
    sourceBond: number,
    esrGroupOffsetAND: number,
    esrGroupOffsetOR: number
  ): number;
  copyMoleculeProperties(destMol: Molecule): void;
  invalidateHelperArrays(helperBits: number): void;
  renumberESRGroups(type: number): number;
  deleteAtom(atom: number): void;
  deleteAtomOrBond(x: number, y: number): boolean;
  deleteBond(bond: number): void;
  deleteBondAndSurrounding(bond: number): void;
  deleteAtoms(atomList: number[]): number[];
  deleteSelectedAtoms(): boolean;
  markAtomForDeletion(atom: number): void;
  markBondForDeletion(bond: number): void;
  isAtomMarkedForDeletion(atom: number): boolean;
  isBondMarkedForDeletion(bond: number): boolean;
  deleteMarkedAtomsAndBonds(): number[];
  deleteMolecule(): void;
  removeAtomSelection(): void;
  removeAtomColors(): void;
  removeAtomCustomLabels(): void;
  removeAtomMarkers(): void;
  removeBondHiliting(): void;
  findAtom(pickx: number, picky: number): number;
  findBond(pickx: number, picky: number): number;
  getAllAtoms(): number;
  getAllBonds(): number;
  getAtomAbnormalValence(atom: number): number;
  getAtomCharge(atom: number): number;
  getAtomCIPParity(atom: number): number;
  getAtomColor(atom: number): number;
  getAtomESRGroup(atom: number): number;
  getAtomESRType(atom: number): number;
  getAtomicNo(atom: number): number;
  getAtomCustomLabel(atom: number): string;
  getAtomLabel(atom: number): string;
  getAtomList(atom: number): number[];
  getAtomListString(atom: number): string;
  getAtomMapNo(atom: number): number;
  getAtomMass(atom: number): number;
  getAtomParity(atom: number): number;
  getAtomQueryFeatures(atom: number): number;
  getAtomRadical(atom: number): number;
  getAtomX(atom: number): number;
  getAtomY(atom: number): number;
  getAtomZ(atom: number): number;
  getBondAngle(atom1: number, atom2: number): number;
  calculateTorsion(atom: number[]): number;
  getBondAtom(no: number, bond: number): number;
  getBondCIPParity(bond: number): number;
  getBondESRGroup(bond: number): number;
  getBondESRType(bond: number): number;
  getBondLength(bond: number): number;
  getBondOrder(bond: number): number;
  getBondParity(bond: number): number;
  getBondQueryFeatures(bond: number): number;
  isBondBridge(bond: number): boolean;
  getBondBridgeMinSize(bond: number): number;
  getBondBridgeMaxSize(bond: number): number;
  getBondType(bond: number): number;
  getBondTypeSimple(bond: number): number;
  getChirality(): number;
  getMaxAtoms(): number;
  setMaxAtoms(v: number): void;
  getMaxBonds(): number;
  setMaxBonds(v: number): void;
  getMoleculeColor(): number;
  setMoleculeColor(color: number): void;
  getName(): string;
  getStereoProblem(atom: number): boolean;
  isAtomConfigurationUnknown(atom: number): boolean;
  isAtomParityPseudo(atom: number): boolean;
  isAtomStereoCenter(atom: number): boolean;
  isBondParityPseudo(bond: number): boolean;
  isBondParityUnknownOrNone(bond: number): boolean;
  isFragment(): boolean;
  isNaturalAbundanc(atom: number): boolean;
  isPurelyOrganic(): boolean;
  isSelectedAtom(atom: number): boolean;
  isMarkedAtom(atom: number): boolean;
  isBondBackgroundHilited(bond: number): boolean;
  isBondForegroundHilited(bond: number): boolean;
  isSelectedBond(bond: number): boolean;
  isAutoMappedAtom(atom: number): boolean;
  isStereoBond(bond: number): boolean;
  setAllAtoms(no: number): void;
  setAllBonds(no: number): void;
  setAtomAbnormalValence(atom: number, valence: number): void;
  setAtomCharge(atom: number, charge: number): void;
  setAtomColor(atom: number, color: number): void;
  setAtomConfigurationUnknown(atom: number, u: boolean): void;
  setAtomSelection(atom: number, s: boolean): void;
  setAtomMarker(atom: number, s: boolean): void;
  setAtomicNo(atom: number, no: number): void;
  setAtomList(atom: number, list: number[], isExcludeList: boolean): void;
  setAtomMapNo(atom: number, mapNo: number, autoMapped: boolean): void;
  setAtomMass(atom: number, mass: number): void;
  setAtomParity(atom: number, parity: number, isPseudo: boolean): void;
  setAtomQueryFeature(atom: number, feature: number, value: boolean): void;
  setAtomRadical(atom: number, radical: number): void;
  setAtomCIPParity(atom: number, parity: number): void;
  setAtomX(atom: number, x: number): void;
  setAtomY(atom: number, y: number): void;
  setAtomZ(atom: number, z: number): void;
  setBondAtom(no: number, bond: number, atom: number): void;
  setBondCIPParity(bond: number, parity: number): void;
  setBondBackgroundHiliting(bond: number, s: boolean): void;
  setBondForegroundHiliting(bond: number, s: boolean): void;
  setBondParity(bond: number, parity: number, isPseudo: boolean): void;
  setBondParityUnknownOrNone(bond: number): void;
  setBondQueryFeature(bond: number, feature: number, value: boolean): void;
  setBondOrder(bond: number, order: number): void;
  setBondType(bond: number, type: number): void;
  setChirality(c: number): void;
  setHydrogenProtection(protectHydrogen: boolean): void;
  setHelperValidity(helperValidity: number): void;
  setToRacemate(): void;
  setAtomCustomLabel(atom: number, label: string): void;
  setAtomESR(atom: number, type: number, group: number): void;
  setBondESR(bond: number, type: number, group: number): void;
  /**
   * Flags the molecule as a fragment or unflags it (useful for substructure search).
   * @param isFragment
   */
  setFragment(isFragment: boolean): void;
  setName(name: string): void;
  removeQueryFeatures(): boolean;
  stripIsotopInfo(): boolean;
  translateCoords(dx: number, dy: number): void;
  scaleCoords(f: number): void;
  zoomAndRotateInit(x: number, y: number): void;
  zoomAndRotate(zoom: number, angle: number, selected: boolean): void;
  getMaxValenceUncharged(atom: number);
  getDefaultMaxValenceUncharged(atom: number): number;
  getMaxValence(atom: number): number;
  getElectronValenceCorrection(atom: number, occupiedValence: number): number;
  isElectronegative(atom: number): boolean;
  isElectropositive(atom: number): boolean;
  isMetalAtom(atom: number): boolean;
  isOrganicAtom(atom: number): boolean;
  copyMoleculeByAtoms(
    destMol: Molecule,
    includeAtom: boolean[],
    recognizeDelocalizedBonds: boolean,
    atomMap: number[]
  ): void;
  copyMoleculeByBonds(
    destMol: Molecule,
    includeBond: boolean[],
    recognizeDelocalizedBonds: boolean,
    atomMap: number[]
  ): number[];
  getAllConnAtoms(atom: number): number;
  getAllHydrogens(atom: number): number;
  getAtoms(): number;
  getMetalBondedConnAtoms(atom: number): number;
  getAtomPi(atom: number): number;
  getAtomRingSize(atom: number): number;
  getBondRingSize(bond: number): number;
  getBonds(): number;
  getBond(atom1: number, atom2: number): number;
  getCompactCopy(): Molecule;
  getConnAtom(atom: number, i: number): number;
  getConnAtoms(atom: number): number;
  getAllConnAtomsPlusMetalBonds(atom: number): number;
  getConnBond(atom: number, i: number): number;
  getConnBondOrder(atom: number, i: number): number;
  getNonHydrogenNeighbourCount(atom: number): number;
  getAverageBondLength(nonHydrogenBondsOnly: boolean): number;
  getOccupiedValence(atom: number): number;
  getExcludeGroupValence(atom: number): number;
  getFreeValence(atom: number): number;
  getLowestFreeValence(atom: number): number;
  getImplicitHigherValence(
    atom: number,
    neglectExplicitHydrogen: boolean
  ): number;
  getAverageTopologicalAtomDistance(): number[];
  getPathLength(atom1: number, atom2: number): number;
  getPath(
    pathAtom: number[],
    atom1: number,
    atom2: number,
    maxLength: number,
    neglectBond: boolean[]
  ): number;
  getPathBonds(
    pathAtom: number[],
    pathBond: number[],
    pathLength: number
  ): void;
  shareSameFragment(atom1: number, atom2: number): boolean;
  addFragment(sourceMol: Molecule, rootAtom: number, atomMap: number[]): void;
  getFragmentAtoms(rootAtom: number, considerMetalBonds: boolean): number[];
  /**
   * Returns the number of fragments in the molecule.
   * @param fragmentNo
   * @param markedAtomsOnly
   * @param considerMetalBonds
   */
  getFragmentNumbers(
    fragmentNo: number[],
    markedAtomsOnly: boolean,
    considerMetalBonds: boolean
  ): number;
  stripSmallFragments(considerMetalBonds: boolean): number[];
  findRingSystem(
    startAtom: number,
    aromaticOnly: boolean,
    isMemberAtom: boolean[],
    isMemberBond: boolean[]
  ): void;
  getSubstituent(
    coreAtom: number,
    firstAtom: number,
    isMemberAtom: boolean[],
    substituent: Molecule,
    atomMap: number[]
  ): number;
  getSubstituentSize(coreAtom: number, firstAtom: number): number;
  supportsImplicitHydrogen(atom: number): boolean;
  getImplicitHydrogens(atom: number): number;
  getExplicitHydrogens(atom: number): number;
  getMolweight(): number;
  getRotatableBondCount(): number;
  isPseudoRotatableBond(bond: number): boolean;
  getAromaticRingCount(): number;
  getAtomRingCount(atom: number, maxRingSize: number): number;
  getAtomPreferredStereoBond(atom: number): number;
  getBondPreferredStereoBond(bond: number): number;
  isAllylicAtom(atom: number): boolean;
  isAromaticAtom(atom: number): boolean;
  isAromaticBond(bond: number): boolean;
  isDelocalizedBond(bond: number): boolean;
  isRingAtom(atom: number): boolean;
  isRingBond(bond: number): boolean;
  isSmallRingAtom(atom: number): boolean;
  isSmallRingBond(bond: number): boolean;
  isStabilizedAtom(atom: number): boolean;
  getAtomRingBondCount(atom: number): number;
  getChiralText(): string;
  getStereoBond(atom: number): number;
  setParitiesValid(helperStereoBits: number): void;
  setStereoBondsFromParity(): void;
  convertStereoBondsToSingleBonds(atom: number): void;
  setStereoBondFromAtomParity(atom: number): void;
  getFisherProjectionParity(
    atom: number,
    sortedConnMap: number[],
    angle: number[],
    direction: number[]
  ): number;
  setStereoBondFromBondParity(bond: number): void;
  findAlleneCenterAtom(atom: number): number;
  findBINAPChiralityBond(atom: number): number;
  isAmideTypeBond(bond: number): boolean;
  isFlatNitrogen(atom: number): boolean;
  isBINAPChiralityBond(bond: number): boolean;
  validate(): void;
  normalizeAmbiguousBonds(): boolean;
  isAlkaliMetal(atom: number): boolean;
  isEarthAlkaliMetal(atom: number): boolean;
  isNitrogenFamily(atom: number): boolean;
  isChalcogene(atom: number): boolean;
  isHalogene(atom: number): boolean;
  canonizeCharge(allowUnbalancedCharge: boolean): number;
  getZNeighbour(connAtom: number, bond: number): number;
  getHelperArrayStatus(): number;
  ensureHelperArrays(required: number): void;
  getHandleHydrogenMap(): number[];
  isSimpleHydrogen(atom: number): boolean;
  /**
   * Remove the explicit hydrogens in the molecule.
   */
  removeExplicitHydrogens(): void;
  /**
   * Returns an array of fragments from the molecule.
   */
  getFragments(): Molecule[];
  stripStereoInformation(): void;
  getAbsoluteAtomParity(atom: number): number;
  getAbsoluteBondParity(bond: number): number;
  getSymmetryRank(atom: number): number;
  /**
   * Returns the ID code of the molecule.
   */
  getIDCode(): string;
  /**
   * Returns a string representation of the coordinates of the atoms in the molecule.
   */
  getIDCoordinates(): string;
  getStereoCenterCount(): number;
  setUnknownParitiesToExplicitlyUnknown(): void;
  setAssignParitiesToNitrogen(b: boolean): void;
}

export interface MolecularFormula {
  absoluteWeight: number;
  relativeWeight: number;
  formula: string;
}

export declare class RingCollection {
  static MAX_SMALL_RING_SIZE: number;
  static MODE_SMALL_RINGS_ONLY: number;
  static MODE_SMALL_AND_LARGE_RINGS: number;
  static MODE_SMALL_RINGS_AND_AROMATICITY: number;
  static MODE_SMALL_AND_LARGE_RINGS_AND_AROMATICITY: number;

  private constructor();
  getAtomRingSize(atom: number): number;
  getBondRingSize(bond: number): number;
  getSize(): number;
  getRingAtoms(ringNo: number): number[];
  getRingBonds(ringNo: number): number[];
  getRingSize(ringNo: number): number;
  isAromatic(ringNo: number): boolean;
  isDelocalized(ringNo: number): boolean;
  getAtomIndex(ringNo: number, atom: number): number;
  getBondIndex(ringNo: number, bond: number): number;
  validateMemberIndex(ringNo: number, index: number): number;
  getHeteroPosition(ringNo: number): number;
  isAtomMember(ringNo: number, atom: number): boolean;
  isBondMember(ringNo: number, bond: number): boolean;
  getSharedRing(bond1: number, bond2: number): number;
  determineAromaticity(
    isAromatic: boolean[],
    isDelocalized: boolean[],
    heteroPosition: number[],
    includeTautomericBonds: boolean
  ): void;
  qualifiesAsAmideTypeBond(bond: number): boolean;
}

/**
 * All depictor options default to `false`.
 */
export interface IDepictorOptions {
  inflateToMaxAVBL: boolean;
  inflateToHighResAVBL: boolean;
  chiralTextBelowMolecule: boolean;
  chiralTextAboveMolecule: boolean;
  chiralTextOnFrameTop: boolean;
  chiralTextOnFrameBottom: boolean;
  noTabus: boolean;
  showAtomNumber: boolean;
  showBondNumber: boolean;
  highlightQueryFeatures: boolean;
  showMapping: boolean;
  suppressChiralText: boolean;
  suppressCIPParity: boolean;
  suppressESR: boolean;
  showSymmetrySimple: boolean;
  showSymmetryDiastereotopic: boolean;
  showSymmetryEnantiotopic: boolean;
  noImplicitAtomLabelColors: boolean;
  noStereoProblem: boolean;
}

export declare class Reaction {
  private constructor();

  /**
   * Returns a new empty `Reaction`.
   */
  static create(): Reaction;
  /**
   * Returns a new `Reaction` filled with the provided molecules.
   * @param molecules - Array of `Molecule` objects
   * @param reactantCount - Number of reactants in the `molecules` array.
   * The remaining objects will be treated as products.
   */
  static fromMolecules(molecules: Molecule[], reactantCount: number): Reaction;
  /**
   * Returns a new `Reaction` based on a reaction SMILES string. The `Reaction` will contain at most one `Molecule`
   * for each component.
   * @param smiles
   */
  static fromSmiles(smiles: string): Reaction;

  /**
   * Serialize the `Reaction` to a reaction SMILES string.
   */
  toSmiles(): string;
  /**
   * Returns a new copy of the `Reaction`.
   */
  clone(): Reaction;
  /**
   * Empty the `Reaction`.
   */
  clear(): void;
  /**
   * Remove all catalysts from the `Reaction`.
   */
  removeCatalysts(): void;
  /**
   * Returns whether the reaction is empty.
   */
  isEmpty(): boolean;
  /**
   * Mark the `Reaction` as `isFragment`.
   * @param isFragment
   */
  setFragment(isFragment: boolean): void;
  /**
   * Returns whether the `Reaction` is a fragment.
   */
  isFragment(): boolean;
  /**
   * Returns the reactant `Molecule` at `index`.
   * @param index
   */
  getReactant(index: number): Molecule;
  /**
   * Returns the number of reactants.
   */
  getReactants(): number;
  /**
   * Returns the product `Molecule` at `index`.
   * @param index
   */
  getProduct(index: number): Molecule;
  /**
   * Returns the number of products.
   */
  getProducts(): number;
  /**
   * Returns the catalyst `Molecule` at `index`.
   * @param index
   */
  getCatalyst(index: number): Molecule;
  /**
   * Returns the number of catalysts.
   */
  getCatalysts(): number;
  /**
   * Returns the total number of reactants and products.
   */
  getMolecules(): number;
  /**
   * Returns the reactant or product at `index` (starting with reactants).
   * @param index
   */
  getMolecule(index: number): Molecule;
  /**
   * Add a new `Molecule` in the reactants.
   * @param reactant
   */
  addReactant(reactant: Molecule): void;
  /**
   * Add a new `Molecule` in the reactants at `index`.
   * @param reactant
   * @param index
   */
  addReactantAt(reactant: Molecule, index: number): void;
  /**
   * Add a new `Molecule` in the products.
   * @param product
   */
  addProduct(product: Molecule): void;
  /**
   * Add a new `Molecule` in the products at `index`.
   * @param product
   * @param index
   */
  addProductAt(product: Molecule, index: number): void;
  /**
   * Add a new `Molecule` in the catalysts.
   * @param catalyst
   */
  addCatalyst(catalyst: Molecule): void;
  /**
   * Add a new `Molecule` in the catalysts at `index`.
   * @param catalyst
   * @param index
   */
  addCatalystAt(catalyst: Molecule, index: number): void;
  /**
   * Returns the name of the `Reaction`.
   */
  getName(): string;
  /**
   * Sets the name of the `Reaction`.
   * @param name
   */
  setName(name: string): void;
  /**
   * Returns the average bond length among reactants and products.
   */
  getAverageBondLength(): number;
  /**
   * Returns whether the molecules` atom coordinate bounds touch or overlap.
   */
  isReactionLayoutRequired(): boolean;
  /**
   * Returns whether all non-hydrogen atoms are mapped and whether every
   * reactant atom has exactly one assigned product atom.
   */
  isPerfectlyMapped(): boolean;
  getHighestMapNo(): number;
  /**
   * Removes mapping numbers that are only used on one side of the reaction. Throws an exception if duplicate mapping
   * numbers occur in reactants or products.
   */
  validateMapping(): void;
  /**
   * This method determines the largest mapping number in use (maxMapNo),
   * creates a boolean array[maxMapNo+1], and within this array flags every
   * mapping number that refers to atoms, which change bonds in the course of
   * the reaction. Mapped atoms that are connected to unpammed atoms are also
   * considered being part of the reaction center. If the reaction is unmapped
   * or has no reactants or products, then `null` is returned.
   */
  getReactionCenterMapNos(): boolean[];
  /**
   * Merges all reactants into one `Molecule` and all products into another and
   * creates a new `Reaction` object from those.
   */
  getMergedCopy(): Reaction;
}

export declare class SDFileParser {
  /**
   * Creates a new parser.
   * @param sdf - String with the SDF
   * @param fields - Array of field names to parse. If null, the SDF is scanned
   * to find all possible names (not efficient).
   */
  constructor(sdf: string, fields: string[]);
  /**
   * Move to the next Molfile. Returns `true` if there is one, `false` otherwise.
   * @example
   * ```js
   *
   * const sdf = fs.readFileSync('./mysdf.sdf', 'utf8');
   * const parser = new SDFileParser(sdf);
   * while (parser.next()) {
   *   const molecule = parser.getMolecule();
   *   // process molecule
   * }
   * ```
   */
  next(): boolean;
  /**
   * Returns the current `Molecule`.
   */
  getMolecule(): Molecule;
  /**
   * Returns the current Molfile string.
   */
  getNextMolFile(): string;
  getNextFieldData(): string;
  /**
   * Returns the list of field names for the entire SDF.
   * @param recordsToInspect - Number of records scanned to determine the list.
   */
  getFieldNames(recordsToInspect: number): string[];
  getFieldData(index: number): string;
  /**
   * Returns the content of the field `name` from the current record or `null`.
   * @param name
   */
  getField(name: string): string;
}

/**
 * Basic substructure searcher.
 */
export declare class SSSearcher {
  /**
   * Creates a new substructure searcher.
   */
  constructor();
  /**
   * Set the `fragment` to search.
   * @param fragment - `Molecule` instance to set as fragment. It has to be
   * flagged with `setFragment(true)` first.
   */
  setFragment(fragment: Molecule): void;
  /**
   * Set the target `molecule` in which the search will be done.
   * @param molecule - `Molecule` instance to set as target molecule.
   */
  setMolecule(molecule: Molecule): void;
  /**
   * Set the fragment and molecule in one call.
   * @param fragment
   * @param molecule
   */
  setMol(fragment: Molecule, molecule: Molecule): void;
  /**
   * Returns whether the current fragment is in the target molecule.
   */
  isFragmentInMolecule(): boolean;
}

/**
 * Fast substructure search with index filtering.
 */
export declare class SSSearcherWithIndex {
  /**
   * Create a new substructure searcher with index.
   */
  constructor();
  /**
   * Returns an array of the 512 idcodes that are used for computing indexes.
   */
  static getKeyIDCode(): string[];
  /**
   * Returns the Tanimoto similarity between the two indexes.
   * @param index1
   * @param index2
   */
  static getSimilarityTanimoto(index1: number[], index2: number[]): number;
  static getSimilarityAngleCosine(index1: number[], index2: number[]): number;
  static getIndexFromHexString(hex: string): number[];
  static getHexStringFromIndex(index: number[]): string;
  static bitCount(x: number): number;
  /**
   * Set the `fragment` to search.
   * @param fragment - `Molecule` instance to set as fragment. It has to be
   * flagged with `setFragment(true)` first.
   * @param index - If the index for this fragment was computed previously, it
   * can be provided here to save time.
   */
  setFragment(fragment: Molecule, index?: number[]): void;
  /**
   * Set the target `molecule` in which the search will be done.
   * @param molecule - `Molecule` instance to set as target molecule.
   * @param index - If the index for this fragment was computed previously, it
   * can be provided here to save time.
   */
  setMolecule(molecule: Molecule, index?: number[]): void;
  /**
   * Returns whether the current fragment is in the target molecule.
   */
  isFragmentInMolecule(): boolean;
  createIndex(molecule: Molecule): number[];
}

export declare namespace Util {
  /**
   * Returns the HOSE(Hierarchical Organisation of Spherical Environments) code
   * for the given diasterotopic ID.
   * @param diastereotopicID
   * @param options
   */
  export function getHoseCodesFromDiastereotopicID(
    diastereotopicID: string,
    options?: IHoseCodesOptions
  ): string[];
}

/**
 * Version number of the library.
 */
export declare const version: string;

// Core API

export interface IParameterizedString {
  type: number;
  value: string;
}

export declare class MoleculeProperties {
  constructor(molecule: Molecule);
  acceptorCount: number;
  donorCount: number;
  logP: number;
  logPString: IParameterizedString[];
  logS: number;
  logSString: IParameterizedString[];
  polarSurfaceArea: number;
  polarSurfaceAreaString: IParameterizedString[];
  rotatableBondCount: number;
  stereoCenterCount: number;
}

export declare class DruglikenessPredictor {
  constructor();

  static DRUGLIKENESS_UNKNOWN: number;

  /**
   * Returns the calculated drug likeness as a double.
   * @param molecule
   */
  assessDruglikeness(molecule: Molecule): number;
  getDruglikenessString(molecule: Molecule): string;
  /**
   * Returns detailed information about the previous drug likeness assessment.
   */
  getDetail(): IParameterizedString[];
}

export declare namespace DrugScoreCalculator {
  export function calculate(
    mCLogP: number,
    mSolubility: number,
    mMolweight: number,
    mDruglikeness: number,
    toxRisks: number[]
  ): number;
}

export declare class ToxicityPredictor {
  constructor();

  static RISK_UNKNOWN: number;
  static RISK_NO: number;
  static RISK_LOW: number;
  static RISK_HIGH: number;

  static TYPE_MUTAGENIC: number;
  static TYPE_TUMORIGENIC: number;
  static TYPE_IRRITANT: number;
  static TYPE_REPRODUCTIVE_EFFECTIVE: number;

  static RISK_NAMES: string[];

  /**
   * Returns the calculated risk as an integer.
   * @param molecule
   * @param riskType
   */
  assessRisk(molecule: Molecule, riskType: number): number;
  /**
   * Returns detailed information about the risk and the substructures that are
   * responsible for it.
   * @param molecule
   * @param riskType
   */
  getDetail(molecule: Molecule, riskType: number): IParameterizedString[];
}

// Full API

export declare namespace StructureView {
  export function showStructures(cssClass: string): void;
  export function renderStructure(id: string): void;
  export function getIDCode(id: string): string;
  export function drawStructure(
    id: string,
    idcode: string,
    coordinates: string,
    options?: IDepictorOptions
  ): void;
  export function drawMolecule(
    el: HTMLCanvasElement,
    mol: Molecule,
    options?: IDepictorOptions
  ): void;
  export function drawStructureWithText(
    id: string,
    idcode: string,
    coordinates: string,
    options?: IDepictorOptions,
    atomText?: string[]
  ): void;
}

export declare class StructureEditor {
  constructor(id: string, useSVG?: boolean, scale?: number);

  /**
   * Create a new structure editor.
   * @param id - Id of the DOM element
   */
  static createEditor(id: string): StructureEditor;
  /**
   * Create a new structure editor with an SVG toolbar.
   * @param id  - Id of the DOM element
   * @param scale
   */
  static createSVGEditor(id: string, scale: number);

  getMolecule(): Molecule;
  /**
   * Returns the current molecule as a string that contains its ID Code and
   * coordinates separated by a space.
   */
  getIDCode(): string;
  /**
   * Sets the current molecule to the provided ID code. The string can optionally
   * contain coordinates separated by a space.
   * @param idCode
   */
  setIDCode(idCode: string): void;
  /**
   * Switches the current molecule in the editor in fragment mode or not.
   * @param isFragment
   */
  setFragment(isFragment: boolean): void;
  /**
   * Returns whether the current molecule is a fragment.
   */
  isFragment(): boolean;
  /**
   * Returns the current molecule as a MDL Molfile V2000.
   */
  getMolFile(): string;
  /**
   * Sets the editor content to the molecule represented by this Molfile
   * (V2000 or V3000)
   * @param molfile
   */
  setMolFile(molfile: string): void;
  /**
   * Returns the current molecule as a MDL Molfile V3000.
   */
  getMolFileV3(): string;
  /**
   * Returns the current molecule as a SMILES string.
   */
  getSmiles(): string;
  /**
   * Sets the editor content to the molecule represented by this SMILES.
   * @param smiles
   */
  setSmiles(smiles: string): void;
  /**
   * Sets a callback function which is called whenever an atom is hovered/unhovered
   * by the mouse.
   * @param callback
   */
  setAtomHightlightCallback(callback: AtomHighlightCallback): void;
  /**
   * Sets a callback function which is called whenever a bond is hovered/unhovered
   * by the mouse.
   * @param callback
   */
  setBondHightlightCallback(callback: BondHighlightCallback): void;
  /**
   * Sets a callback function which is called whenever the structure in the editor
   * is changed.
   * @param callback
   */
  setChangeListenerCallback(callback: ChangeListenerCallback): void;
  hasFocus(): boolean;
}

export type AtomHighlightCallback = (atom: number, selected: boolean) => any;
export type BondHighlightCallback = (bond: number, selected: boolean) => any;
export type ChangeListenerCallback = (
  idcode: string,
  molecule: Molecule
) => any;

export declare namespace SVGRenderer {
  export function renderMolecule(
    idCode: string,
    width: number,
    height: number
  ): string;
}

// Minimal API

export interface IMoleculeFromSmilesOptions {
  /**
   * Disable extra coordinate computation. Default: false.
   */
  noCoordinates?: boolean;

  /**
   * Disable stereo features parsing. Default: false.
   */
  noStereo?: boolean;
}

export interface IMoleculeToSVGOptions extends IDepictorOptions {
  /**
   * Factor used to compute the size of text nodes.
   * Default: 1.
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

  /**
   * Automatically crop the SVG to fit around the molecule.
   * This changes the size of the SVG.
   * Default: false.
   */
  autoCrop?: boolean;

  /**
   * Margin (in px) to keep around the molecule when `autoCrop` is `true`.
   * Default: 5.
   */
  autoCropMargin?: number;
}

export interface IHoseCodesOptions {
  /**
   * Maximum number of atoms from the center.
   * Default: 5.
   */
  maxSphereSize: number;

  /**
   * Type of HOSE code.
   * 0: normal HOSE code.
   * 1: stop if Csp3-Csp3.
   * Default: 0.
   */
  type: 0 | 1;
}

export interface Rectangle {
  /**
   * X-coordinate of the top-left corner.
   */
  x: number;

  /**
   * Y-coordinate of the top-left corner.
   */
  y: number;

  /**
   * Width of the shape.
   */
  width: number;

  /**
   * Height of the shape.
   */
  height: number;
}

export declare class Molecule {
  /**
   * Construct a new molecule.
   * @param maxAtoms - Maximum number of initialized atoms. Default: 256.
   * @param maxBonds - Maximum number of initialized bonds. Default: 256.
   */
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
   * should be computed. Default: true.
   */
  static fromIDCode(idcode: string, ensure2DCoordinates?: boolean): Molecule;

  static getAtomicNoFromLabel(atomLabel: string): number;

  static getAngle(x1: number, y1: number, x2: number, y2: number): number;

  static getAngleDif(angle1: number, angle2: number): number;

  static getDefaultAverageBondLength(): number;

  /**
   * When the molecule adds a new bond to a new atom or a new ring, then atoms are
   * positioned such that the lengths of the new bonds are equal to the average
   * length of existing bonds. If there are no existing bonds, then this default
   * is used. If the default is not set by this function, then it is 24.
   * @param defaultAVBL
   */
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
   * Returns the rectangle that bounds the molecule.
   */
  getBounds(): Rectangle;

  addAtom(atomicNo: number): number;

  /**
   * Suggests either cBondTypeSingle or cBondTypeMetalLigand whatever seems more
   * appropriate for a new bond between the two atoms.
   * @param atom1
   * @param atom2
   */
  suggestBondType(atom1: number, atom2: number): number;

  /**
   * Adds a single or metal bond between the two atoms depending on whether one
   * of them is a metal atom.
   * @param atom1
   * @param atom2
   */
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

  /**
   * Copies all atoms and bonds of mol to the end of this Molecule's atom and bond
   * tables. If mol is a fragment then this Molecule's fragment flag is set to
   * true and all query features of mol are also copied.
   * @param mol
   * @returns Atom mapping from original mol to this molecule after incorporation of mol.
   */
  addMolecule(mol: Molecule): number[];

  /**
   *
   * @param substituent
   * @param connectionAtom
   * @returns Atom mapping from substituent to this molecule after addition of substituent.
   */
  addSubstituent(substituent: Molecule, connectionAtom: number): number[];

  /**
   * Copies this molecule including parity settings, if valid. The original
   * content of destMol is replaced. Helper arrays are not copied and need to be
   * recalculated if needed.
   * @param destMol
   */
  copyMolecule(destMol: Molecule): void;

  /**
   * Creates a new atom in destMol and copies all source atom properties including
   * atom list, custom label, flags, and mapNo to it.
   * @param destMol
   * @param sourceAtom
   * @param esrGroupOffsetAND - -1 to create new ESR group or destMol ESR group
   *                          count from esrGroupCountAND()
   * @param esrGroupOffsetOR - -1 to create new ESR group or destMol ESR group
   *                          count from esrGroupCountOR()
   * @returns Index of new atom in destMol.
   */
  copyAtom(
    destMol: Molecule,
    sourceAtom: number,
    esrGroupOffsetAND: number,
    esrGroupOffsetOR: number
  ): number;

  /**
   *
   * @param destMol
   * @param sourceBond
   * @param esrGroupOffsetAND - -1 to create new ESR group or destMol ESR group
   *                               count from esrGroupCountAND()
   * @param esrGroupOffsetOR - -1 to create new ESR group or destMol ESR group
   *                               count from esrGroupCountOR()
   */
  copyBond(
    destMol: Molecule,
    sourceBond: number,
    esrGroupOffsetAND: number,
    esrGroupOffsetOR: number
  ): number;

  /**
   * Copies name,isFragment,chirality and validity of parity & CIP flags. When
   * copying molecules parts only or when changing the atom order during copy,
   * then atom parities or CIP parities may not be valid anymore and
   * invalidateHelperArrays([affected bits]) should be called in these cases.
   * @param destMol
   */
  copyMoleculeProperties(destMol: Molecule): void;

  /**
   * Clears helperBits from mValidHelperArrays.
   * @param helperBits
   */
  invalidateHelperArrays(helperBits: number): void;

  /**
   * For the given ESR type (AND or OR) renumbers all group indexes starting from
   * 0. Use this, if stereo center deletion or other operations caused an
   * inconsisten ESR number state. Molecule and derived methods do this
   * automatically.
   * @param type - cESRTypeAnd or cESRTypeOr
   * @returns number of ESR groups
   */
  renumberESRGroups(type: number): number;

  /**
   * After the deletion the original order of atom and bond indexes is retained.
   * @param atom
   */
  deleteAtom(atom: number): void;

  deleteAtomOrBond(x: number, y: number): boolean;

  /**
   * After the deletion the original order of atom and bond indexes is retained.
   * @param bond
   */
  deleteBond(bond: number): void;

  /**
   * After the deletion the original order of atom and bond indexes is retained.
   * @param bond
   */
  deleteBondAndSurrounding(bond: number): void;

  /**
   * After the deletion the original order of atom and bond indexes is retained.
   * @param atomList
   */
  deleteAtoms(atomList: number[]): number[];

  /**
   * Delete all selected atoms
   * and all bonds attached to them. After the deletion the original order of atom
   * and bond indexes is retained.
   */
  deleteSelectedAtoms(): boolean;

  /**
   * Marks this atom to be deleted in a later call to deleteMarkedAtomsAndBonds().
   * @param atom
   */
  markAtomForDeletion(atom: number): void;

  /**
   * Marks this bond to be deleted in a later call to deleteMarkedAtomsAndBonds().
   * @param bond
   */
  markBondForDeletion(bond: number): void;

  /**
   * Checks whether this atom was marked to be deleted and not deleted yet.
   * @param atom
   */
  isAtomMarkedForDeletion(atom: number): boolean;

  /**
   * Checks whether this bond was marked to be deleted and not deleted yet.
   * @param bond
   */
  isBondMarkedForDeletion(bond: number): boolean;

  /**
   * Deletes all atoms and bonds
   * from the molecule, which were marked before for deletion by calling
   * markAtomForDeletion() or markBondForDeletion(). Bonds connecting atoms of
   * which at least one is marked for deletion, are deleted automatically and
   * don't require to be explicitly marked.<br>
   * When multiple atoms and/or bonds need to be deleted, marking them and calling
   * this method is more efficient than deleting them individually with
   * deleteAtom() and deleteBond(). Bonds, whose atoms carry opposite charges are
   * treated in the following manner: If only one of the two bond atoms is kept,
   * then its absolute charge will be reduced by 1. After the deletion the
   * original order of atom and bond indexes is retained.
   * @returns mapping from old to new atom indices; null if no atoms nor bonds were deleted.
   */
  deleteMarkedAtomsAndBonds(): number[];

  /**
   * Empties the molecule to serve as container for constructing a new molecule,
   * e.g. by multiply calling addAtom(...), addBond(...) and other high level
   * methods.
   */
  deleteMolecule(): void;

  removeAtomSelection(): void;

  removeAtomColors(): void;

  removeAtomCustomLabels(): void;

  removeAtomMarkers(): void;

  removeBondHiliting(): void;

  /**
   *
   * @param pickx
   * @param picky
   * @returns index of closest of nearby atoms or -1, if no atom is close.
   */
  findAtom(pickx: number, picky: number): number;

  /**
   *
   * @param pickx
   * @param picky
   * @returns index of closest of nearby bonds or -1, if no bond is close.
   */
  findBond(pickx: number, picky: number): number;

  /**
   * @returns the number of all atoms, which includes hydrogen atoms.
   */
  getAllAtoms(): number;

  /**
   * @returns the number of all bonds, which includes those connecting hydrogen atoms.
   */
  getAllBonds(): number;

  /**
   * Get an atom's defined maximum valance if different from the default one.
   * @param atom
   * @returns valence 0-14: new maximum valence; -1: use default
   */
  getAtomAbnormalValence(atom: number): number;

  /**
   *
   * @param atom
   * @returns The formal atom charge.
   */
  getAtomCharge(atom: number): number;

  /**
   * The atom Cahn-Ingold-Prelog parity is a calculated property available
   * above/equal helper level cHelperCIP. It encodes the stereo configuration of
   * an atom with its neighbors using up/down-bonds or 3D-atom-coordinates,
   * whatever is available. It depends on the atom indices of the neighbor atoms
   * and their orientation is space. This method is called by the Canonizer and
   * usually should not be called otherwise.
   * @param atom
   * @returns one of
   *         cAtomCIPParityNone,cAtomCIPParityRorM,cAtomCIPParitySorP,cAtomCIPParityProblem
   */
  getAtomCIPParity(atom: number): number;

  getAtomColor(atom: number): number;

  /**
   * This is MDL's enhanced stereo representation (ESR). Stereo atoms and bonds
   * with the same ESR type (AND or OR) and the same ESR group number are in the
   * same group, i.e. within this group they have the defined (relative) stereo
   * configuration.
   * @param atom
   * @returns group index starting with 0
   */
  getAtomESRGroup(atom: number): number;

  /**
   * This is MDL's enhanced stereo representation (ESR). Stereo atoms and bonds
   * with the same ESR type (AND or OR) and the same ESR group number are in the
   * same group, i.e. within this group they have the defined (relative) stereo
   * configuration.
   * @param atom
   * @returns one of cESRTypeAbs,cESRTypeAnd,cESRTypeOr
   */
  getAtomESRType(atom: number): number;

  /**
   * In addition to the natural atomic numbers, we support additional pseudo
   * atomic numbers. Most of these are for compatibility with the MDL atom table,
   * e.g. for amino acids and R-groups. D and T are accepted for setting, but are
   * on-the-fly translated to H with the proper atom mass.
   * @param atom
   */
  getAtomicNo(atom: number): number;

  /**
   * If a custom atom label is set, a molecule depiction displays the custom label
   * instead of the original one.
   * @param atom
   * @returns null or previously defined atom custom label
   */
  getAtomCustomLabel(atom: number): string;

  /**
   *
   * @param atom
   * @returns standard atom label of the atom: C,Li,Sc,...
   */
  getAtomLabel(atom: number): string;

  /**
   * The list of atoms that are allowed at this position during sub-structure
   * search. (or refused atoms, if atom query feature cAtomQFAny is set).
   * @param atom
   * @returns null or sorted list of unique atomic numbers, if defined.
   */
  getAtomList(atom: number): number[];

  getAtomListString(atom: number): string;

  /**
   * Returns an atom mapping number within the context of a reaction. Atoms that
   * that share the same mapping number on the reactant and product side are
   * considered to be the same atom.
   * @param atom
   */
  getAtomMapNo(atom: number): number;

  /**
   *
   * @param atom
   * @returns atom mass, if is specific isotop, otherwise 0 for natural abundance
   */
  getAtomMass(atom: number): number;

  /**
   * The atom parity is a calculated property available above/equal helper level
   * cHelperParities. It describes the stereo configuration of a chiral atom and
   * is calculated either from 2D-atom-coordinates and up/down-bonds or from
   * 3D-atom-coordinates, whatever is available. It depends on the atom indexes of
   * the neighbor atoms and their orientation in space.<br>
   * The parity is defined as follows: Look at the chiral atom such that its
   * neighbor atom with the highest atom index (or the hydrogen atom if it is
   * implicit) is oriented to the back. If the remaining three neighbors are in
   * clockwise order (considering ascending atom indexes) than the parity is 1. If
   * they are in anti-clockwise order, then the parity is 2.<br>
   * For linear chirality (allenes): Look along the straight line of double bonds
   * such that the rear neighbor with the lower atom index points to the top. If
   * the front neighbor with the lower atom index points to the right than the
   * parity is 1.<br>
   * @param atom
   * @returns one of cAtomParity1,cAtomParity2,cAtomParityNone,cAtomParityUnknown
   */
  getAtomParity(atom: number): number;

  /**
   * Returns all set query features for this atom. In order to get all features
   * related to a certain subject use something like this:
   * <i>getAtomQueryFeatures() & cAtomQFHydrogen</i>
   * @param atom
   */
  getAtomQueryFeatures(atom: number): number;

  /**
   * Gets an atom's radical state as singulet,dublet,triplet or none
   * @param atom
   * @returns one of
   *         cAtomRadicalStateNone,cAtomRadicalStateS,cAtomRadicalStateD,cAtomRadicalStateT
   */
  getAtomRadical(atom: number): number;

  getAtomX(atom: number): number;

  getAtomY(atom: number): number;

  getAtomZ(atom: number): number;

  getBondAngle(atom1: number, atom2: number): number;

  /**
   * Calculates a signed torsion as an exterior spherical angle from a valid
   * 4-atom strand. Looking along the central bond, the torsion angle is 0.0, if
   * the projection of front and rear bonds point in the same direction. If the
   * front bond is rotated in the clockwise direction, the angle increases, i.e.
   * has a positive value. http://en.wikipedia.org/wiki/Dihedral_angle
   * @param atom 4 valid atom indices defining a connected atom sequence
   * @returns torsion in the range: -pi <= torsion <= pi
   */
  calculateTorsion(atom: number[]): number;

  /**
   *
   * @param no - 0 or 1
   * @param bond
   * @returns atom index
   */
  getBondAtom(no: 0 | 1, bond: number): number;

  /**
   * The bond Cahn-Ingold-Prelog parity is a calculated property available
   * above/equal helper level cHelperCIP. It encodes the stereo configuration of a
   * bond with its neighbors using 2D-coordinates and up/down-bonds or
   * 3D-atom-coordinates, whatever is available. It depends on the atom indices of
   * the neighbor atoms and their orientation is space. This method is called by
   * the Canonizer and usually should not be called otherwise. Considered are
   * E/Z-double bonds and M/P-BINAP type single bonds.
   * @param bond
   * @returns one of
   *         cBondCIPParityNone,cBondCIPParityEorP,cBondCIPParityZorM,cBondCIPParityProblem
   */
  getBondCIPParity(bond: number): number;

  /**
   * This is MDL's enhanced stereo representation (ESR). Stereo atoms and bonds
   * with the same ESR type (AND or OR) and the same ESR group number are in the
   * same group, i.e. within this group they have the defined (relative) stereo
   * configuration.
   * @param bond
   * @returns group index starting with 0
   */
  getBondESRGroup(bond: number): number;

  /**
   * This is MDL's enhanced stereo representation (ESR). Stereo atoms and bonds
   * with the same ESR type (AND or OR) and the same ESR group number are in the
   * same group, i.e. within this group they have the defined (relative) stereo
   * configuration.
   * @param bond
   * @returns one of cESRTypeAbs,cESRTypeAnd,cESRTypeOr
   */
  getBondESRType(bond: number): number;

  /**
   *
   * @param bond
   * @returns bond length calculated from atom 2D-coordinates.
   */
  getBondLength(bond: number): number;

  /**
   * Delocalized bonds, i.e. bonds in an aromatic 6-membered ring, are returned as
   * 1. Ligand field bonds are returned as 0.
   * @param bond
   * @returns for organic molecules 1, 2 or 3.
   */
  getBondOrder(bond: number): number;

  /**
   * Returns the pre-calculated bond parity, e.g. cBondParityEor1. To distinguish
   * double bond parities (E/Z) from parities of axial chirality, e.g. BINAP type
   * (1/2) simply check with getBondOrder(bond): If the order is 2, then the
   * parity describes E/Z, otherwise an axial parity.
   * @param bond
   * @return one of cBondParity???
   */
  getBondParity(bond: number): number;

  getBondQueryFeatures(bond: number): number;

  isBondBridge(bond: number): boolean;

  getBondBridgeMinSize(bond: number): number;

  getBondBridgeMaxSize(bond: number): number;

  /**
   * Returns bond type combining bond order and stereo orientation.
   * @param bond
   * @returns one of cBondTypeSingle,cBondTypeDouble,cBondTypeUp,cBondTypeCross,...
   */
  getBondType(bond: number): number;

  /**
   * This is the bond type without stereo information.
   * @param bond
   * @returns cBondTypeSingle,cBondTypeDouble,cBondTypeTriple,cBondTypeDelocalized
   */
  getBondTypeSimple(bond: number): number;

  /**
   * Gets the overall chirality of the molecule, which is a calculated information
   * considering: Recognition of stereo centers and stereo bonds, defined ESR
   * features, meso detection. The chirality combines the knowledge about how many
   * stereo isomers are represented, whether all of these are meso, whether we
   * have one defined stereo isomer, a mixture of racemates, epimers, or other
   * diastereomers. The information is used during depiction.
   */
  getChirality(): number;

  /**
   * The currently defined maximum of atoms, which increases automatically when
   * using high level construction methods and new atoms exceed the current
   * maximum.
   */
  getMaxAtoms(): number;

  /**
   * Usually called automatically and hardly needed to be called.
   * @param v
   */
  setMaxAtoms(v: number): void;

  /**
   * The currently defined maximum of bonds, which increases automatically when
   * using high level construction methods and new bonds exceed the current
   * maximum.
   */
  getMaxBonds(): number;

  /**
   * Usually called automatically and hardly needed to be called.
   * @param v
   */
  setMaxBonds(v: number): void;

  /**
   * cMoleculeColorDefault: atom coloring depends on atomic number. Carbon and
   * hydrogen are drawn in neutral color<br>
   * cMoleculeColorNeutral: all atoms and bonds and CIP letters are drawn in
   * neutral color<br>
   * @returns cMoleculeColorNeutral or cMoleculeColorDefault. In future may also
   *         return ARGB values.
   */
  getMoleculeColor(): number;

  /**
   * Currently, this method only allows to switch the default atomic number
   * dependent atom coloring off by passing cMoleculeColorNeutral. In future
   * updates it may also accept ARGB values.
   * @param color currently supported values: cMoleculeColorDefault,
   *              cMoleculeColorNeutral
   */
  setMoleculeColor(color: number): void;

  /**
   * Allows to set a molecule name or identifier, that is, for instance, written
   * to or read from molfiles.
   */
  getName(): string;

  /**
   * The stereo problem flag is set by the stereo recognition (available
   * equal/above helper level cHelperParities) if an atom has over- or
   * under-specified stereo bonds attached, i.e. a stereo center with less or more
   * than one up/down-bond, an non-stereo-center atom carrying (a) stereo bond(s),
   * or a stereo center with neighbors coordinates such that the stereo
   * configuration cannot be deduced. This flag is used by the depiction and
   * causes affected atoms to be drawn in margenta.
   * @param atom
   */
  getStereoProblem(atom: number): boolean;

  /**
   *
   * @param atom
   * @returns whether the atom's stereo configuration was explicitly declared
   *         unknown
   */
  isAtomConfigurationUnknown(atom: number): boolean;

  /**
   * Pseudo paries are parities that indicate a relative configuration. It always
   * needs at least 2 pseudo parities (atom or bond) within a part of a molecule
   * to be meaningful. This information is calculated by
   * ensureHelperArrays(Molecule.cHelperCIP). Molecules extracted from IDCode
   * don't know about pseudo parities.
   * @param atom
   * @returns whether this atom's parity is a relative configuration
   */
  isAtomParityPseudo(atom: number): boolean;

  /**
   * Atoms with pseudo parities are not considered stereo centers. While parities
   * are canonized and always refer to the full set of molecules (in case ESR
   * groups are defined), this method returns true if this atom is a stereo center
   * in any(!) of the individual molecules described by the ESR settings.
   * @param atom
   * @returns true if atom is stereo center in at least one molecule after ESR
   *         resolution
   */
  isAtomStereoCenter(atom: number): boolean;

  isBondParityPseudo(bond: number): boolean;

  /**
   * This hint/flag is set by CoordinateInventor for double bonds without given
   * EZ-parity, because the new coordinates may imply a not intended EZ-parity. If
   * parities are calculated later by the Canonizer is can correctly assign
   * cBondParityUnknown if the bond is a stereo bond. The setBondParity() method
   * clears this flag. This method usually should not be called for other
   * purposes.
   * @param bond
   * @returns whether the bond parity was unknown when 2D- atom coordinates were
   *         created
   */
  isBondParityUnknownOrNone(bond: number): boolean;

  /**
   * Molecule objects may represent complete molecules or sub-structure fragments,
   * depending on, whether they are flagges as being a fragment or not. Both
   * representations have much in common, but in certain aspects behave
   * differently. Thus, complete molecules are considered to carry implicit
   * hydrogens to fill unoccupied atom valences. Sub-structure fragments on the
   * other hand may carry atom or bond query features. Depiction, sub-structure
   * search, and other algorithms treat fragments and complete molecules
   * differerently.
   */
  isFragment(): boolean;

  /**
   *
   * @param atom
   * @returns whether the atom has the natural isotop distribution
   */
  isNaturalAbundanc(atom: number): boolean;

  /**
   * @returns true if atom is one of H,B,C,N,O,F,Si,P,S,Cl,As,Se,Br,Te,I
   */
  isPurelyOrganic(): boolean;

  isSelectedAtom(atom: number): boolean;

  /**
   * Atom marking may be used for any external purpose
   * @param atom
   */
  isMarkedAtom(atom: number): boolean;

  /**
   * Used for depiction only.
   * @param bond
   */
  isBondBackgroundHilited(bond: number): boolean;

  /**
   * Used for depiction only.
   * @param bond
   */
  isBondForegroundHilited(bond: number): boolean;

  isSelectedBond(bond: number): boolean;

  isAutoMappedAtom(atom: number): boolean;

  /**
   * Checks whether bond is drawn as up/down single bond
   * @param bond
   * @returns true if bond is a stereo bond
   */
  isStereoBond(bond: number): boolean;

  /**
   * Low level method for constructing/modifying a molecule from scratch. Use
   * setAtomicNo(), possibly setAtomX(), setAtomY() and other setAtomXXX() methods
   * for new atoms.
   * @param no
   */
  setAllAtoms(no: number): void;

  /**
   * Low level method for constructing/modifying a molecule from scratch. Use
   * setBondType() and setBondAtom() if you increase the number of bonds with this
   * method.
   * @param no
   */
  setAllBonds(no: number): void;

  /**
   * Set an atom's maximum valance to be different from the default one. If a
   * carbon atom's valence is set to -1,0 or 4 its radical state is removed. If a
   * carbon atom's valence is set to 2, a singulet carbene state is assumed.
   * @param atom
   * @param valence 0-14: new maximum valence; -1: use default
   */
  setAtomAbnormalValence(atom: number, valence: number): void;

  setAtomCharge(atom: number, charge: number): void;

  setAtomColor(atom: number, color: number): void;

  /**
   * This is a user applied information, rather than a calculated value. The
   * stereo center configuration is declared to be unknown. If the atom is
   * recognized a stereo center, then its parity will be cAtomParityUnknown.
   * @param atom
   * @param u
   */
  setAtomConfigurationUnknown(atom: number, u: boolean): void;

  setAtomSelection(atom: number, s: boolean): void;

  /**
   * Atom marking may be used for any external purpose.
   * @param atom
   * @param s
   */
  setAtomMarker(atom: number, s: boolean): void;

  /**
   * Set an atom's atomic number and defines the isotop to be natural abundance.
   * @param atom
   * @param no
   */
  setAtomicNo(atom: number, no: number): void;

  /**
   * Defines an atom list as query feature for substructure search
   * @param atom
   * @param list - is null or a sorted int[] of valid atomic numbers
   * @param isExcludeList - true if atom is a wild card and list contains atoms to
   *                      be excluded
   */
  setAtomList(atom: number, list: number[], isExcludeList: boolean): void;

  /**
   * Defines an atom mapping number within the context of a reaction. Atoms that
   * that share the same mapping number on the reactant and product side are
   * considered to be the same atom.
   * @param atom
   * @param mapNo
   * @param autoMapped
   */
  setAtomMapNo(atom: number, mapNo: number, autoMapped: boolean): void;

  /**
   * Set atom to specific isotop or to have a natural isotop distribution
   * @param atom
   * @param mass - rounded atom mass or 0 (default) for natural abundance
   */
  setAtomMass(atom: number, mass: number): void;

  /**
   * The atom parity is a calculated property available above/equal helper level
   * cHelperParities. It describes the stereo configuration of a chiral atom and
   * is calculated either from 2D-atom-coordinates and up/down-bonds or from
   * 3D-atom-coordinates, whatever is available. It depends on the atom indices of
   * the neighbor atoms and their orientation in space.<br>
   * The parity is defined as follows: Look at the chiral atom such that its
   * neighbor atom with the highest atom index (or the hydrogen atom if it is
   * implicit) is oriented to the back. If the remaining three neighbors are in
   * clockwise order (considering ascending atom indexes) than the parity is 1. If
   * they are in anti-clockwise order, then the parity is 2.<br>
   * For linear chirality (allenes): Look along the straight line of double bonds
   * such that the rear neighbor with the lower atom index points to the top. If
   * the front neighbor with the lower atom index points to the right than the
   * parity is 1.<br>
   * This method is called by the Canonizer and usually should not be called
   * otherwise.
   * @param atom
   * @param parity - one of
   *                 cAtomParity1,cAtomParity2,cAtomParityNone,cAtomParityUnknown
   * @param isPseudo - true if the configuration is only meaningful relative to
   *                 another one
   */
  setAtomParity(atom: number, parity: number, isPseudo: boolean): void;

  /**
   * Introduce or remove an atom query feature and make sure, the molecule is
   * flagged to be a sub-structure fragment (see setFragment()). A query feature
   * is usually a flag, which if set, poses an additional atom/bond matching
   * constraint for the sub-structure search and, thus, reduces the number of
   * matching atoms and therefore also the number of molecules found. Often
   * multiple query feature flags are related and grouped, e.g. to define the
   * number of hydrogens atoms. These are the flags related to hydrogen
   * neighbors:<br>
   * <br>
   * public static final int cAtomQFHydrogen = 0x00000780;<br>
   * public static final int cAtomQFNot0Hydrogen = 0x00000080;<br>
   * public static final int cAtomQFNot1Hydrogen = 0x00000100;<br>
   * public static final int cAtomQFNot2Hydrogen = 0x00000200;<br>
   * public static final int cAtomQFNot3Hydrogen = 0x00000400;<br>
   * <p>
   * An inverse logic needs to be applied to translate a user request to the bits
   * needed. For example, to only accept atoms that have 1 or 2 hydrogen
   * neighbors, we need to filter out all others. Thus, we would call<br>
   * setAtomQueryFeature(atom, cAtomQFNot0Hydrogen | cAtomQFNot3Hydrogen, true);
   * </p>
   * <p>
   * To match only atoms without hydrogen neighbors, call<br>
   * setAtomQueryFeature(atom, cAtomQFHydrogen & ~cAtomQFNot3Hydrogen, true);<br>
   * This mechanism allows a very efficient atom matching and therefore very fast
   * sub-structure search.
   * </p>
   * @param atom
   * @param feature - one of cAtomQF...
   * @param value - if true, the feature is set, otherwise it is removed
   */
  setAtomQueryFeature(atom: number, feature: number, value: boolean): void;

  /**
   * Sets an atom's radical state as singulet,dublet,triplet or none
   * @param atom
   * @param radical - one of
   *                cAtomRadicalStateNone,cAtomRadicalStateS,cAtomRadicalStateD,cAtomRadicalStateT
   */
  setAtomRadical(atom: number, radical: number): void;

  /**
   * The atom Cahn-Ingold-Prelog parity is a calculated property available
   * above/equal helper level cHelperCIP. It encodes the stereo configuration of
   * an atom with its neighbors using up/down-bonds or 3D-atom-coordinates,
   * whatever is available. It depends on the atom indices of the neighbor atoms
   * and their orientation is space. This method is called by the Canonizer and
   * usually should not be called otherwise.
   * @param atom
   * @param parity - one of
   *               cAtomCIPParityRorM,cAtomCIPParitySorP,cAtomCIPParityProblem
   */
  setAtomCIPParity(atom: number, parity: number): void;

  setAtomX(atom: number, x: number): void;

  setAtomY(atom: number, y: number): void;

  setAtomZ(atom: number, z: number): void;

  setBondAtom(no: number, bond: number, atom: number): void;

  /**
   * The bond Cahn-Ingold-Prelog parity is a calculated property available
   * above/equal helper level cHelperCIP. It encodes the stereo configuration of a
   * bond with its neighbors using 2D-coordinates and up/down-bonds or
   * 3D-atom-coordinates, whatever is available. It depends on the atom indices of
   * the neighbor atoms and their orientation is space. This method is called by
   * the Canonizer and usually should not be called otherwise. Considered are
   * E/Z-double bonds and M/P-BINAP type single bonds.
   * @param bond
   * @param parity - one of
   *               cBondCIPParityEorP,cBondCIPParityZorM,cBondCIPParityProblem
   */
  setBondCIPParity(bond: number, parity: number): void;

  /**
   * Used for depiction only.
   * @param bond
   * @param s
   */
  setBondBackgroundHiliting(bond: number, s: boolean): void;

  /**
   * Used for depiction only.
   * @param bond
   * @param s
   */
  setBondForegroundHiliting(bond: number, s: boolean): void;

  /**
   * The bond parity is a calculated property available above/equal helper level
   * cHelperParities. It encodes the stereo configuration of a double bond or
   * BINAP type single bond from up/down-bonds and 2D-coordinates or
   * 3D-atom-coordinates, whatever is available. It depends on the atom indices of
   * the neighbor atoms and their orientation is space. This method is called by
   * the Canonizer and usually should not be called otherwise.
   * @param bond
   * @param parity - one of
   *                 cBondParityEor1,cBondParityZor2,cBondParityNone,cBondParityUnknown
   * @param isPseudo - true if the configuration is only meaningful relative to
   *                 another one
   */
  setBondParity(bond: number, parity: number, isPseudo: boolean): void;

  /**
   * This hint/flag is set by CoordinateInventor for double bonds without given
   * EZ-parity, because the new coordinates may imply a not intended EZ-parity. If
   * parities are calculated later by the Canonizer is can correctly assign
   * cBondParityUnknown if the bond is a stereo bond. The setBondParity() method
   * clears this flag. This method usually should not be called for other
   * purposes.
   * @param bond
   */
  setBondParityUnknownOrNone(bond: number): void;

  setBondQueryFeature(bond: number, feature: number, value: boolean): void;

  /**
   * Sets the bond type based on bond order without stereo orientation.
   * @param bond
   * @param order - 1,2, or 3
   */
  setBondOrder(bond: number, order: number): void;

  /**
   * Defines a bond type combining bod order and stereo orientation.
   * @param bond
   * @param type - one of
   *             cBondTypeSingle,cBondTypeDouble,cBondTypeUp,cBondTypeCross,...
   */
  setBondType(bond: number, type: number): void;

  /**
   * Sets the overall chirality of the molecule taking into account: Recognition
   * of stereo centers and stereo bonds, defined ESR features, meso detection. The
   * chirality combines the knowledge about how many stereo isomers are
   * represented, whether all of these are meso, whether we have one defined
   * stereo isomer, a mixture of racemates, epimers, or other diastereomers. The
   * information is used during depiction. This method is called by the Canonizer
   * and usually should not be called otherwise.
   * @param c
   */
  setChirality(c: number): void;

  /**
   * Fragment's query features are checked for consistency and normalized during
   * helper array creation. As part of this, simple hydrogen atoms are converted
   * into hydrogen-count query features. If hydrogen protection is enabled,
   * explicit hydrogens are not touched.
   * @param protectHydrogen
   */
  setHydrogenProtection(protectHydrogen: boolean): void;

  /**
   * Use this method with extreme care. If you make a change to the molecule, the
   * validity of the helper arrays is typically set to cHelperNone. If you make a
   * small change to a molecule that doesn't change its topology, you may override
   * the automatic automatically cleared helper validity with this method and
   * avoid a new calculation of the neighbour arrays and ring detection.
   * @param helperValidity - cHelperNeighbours or cHelperRings
   */
  setHelperValidity(helperValidity: number): void;

  /**
   * This is for compatibility with old MDL stereo representation that contained a
   * 'chiral' flag to indicate that the molecule is not a racemate. If a molecule
   * is constructed from a source format (e.g. a molfile version 2) that contains
   * a 'chiral' flag then setToRacemate() needs to be called if the chiral flag is
   * not(!) set. This causes after stereo center recognition to turn all absolute
   * stereo centers into racemic ones.
   */
  setToRacemate(): void;

  /**
   * If a custom atom label is set, a molecule depiction displays the custom label
   * instead of the original one. Custom labels are not interpreted otherwise.
   * However, they may optionally be encoded into idcodes; see
   * Canonizer.encodeAtomCustomLabels(). If a custom label start with ']' then the
   * label without the ']' symbol is shown at the top left of the original atom
   * label rather than replacing the original atom label. If label is null or
   * equals the normal atom label, then the custom label is removed. This method
   * is less efficient than the byte[] version: setAtomCustomLabel(int, byte[])
   * @param atom
   * @param label - null to remove custom label
   */
  setAtomCustomLabel(atom: number, label: string): void;

  /**
   * This is MDL's enhanced stereo representation (ESR). Stereo atoms and bonds
   * with the same ESR type (AND or OR) and the same ESR group number are in the
   * same group, i.e. within this group they have the defined (relative) stereo
   * configuration.
   * @param atom
   * @param type - one of cESRTypeAbs,cESRTypeAnd,cESRTypeOr
   * @param group - index starting with 0 (not considered if type is cESRTypeAbs)
   */
  setAtomESR(atom: number, type: number, group: number): void;

  /**
   * MDL's enhanced stereo representation for BINAP type of stereo bonds. Stereo
   * atoms and bonds with the same ESR type (AND or OR) and the same ESR group
   * number are in the same group, i.e. within this group they have the defined
   * (relative) stereo configuration.
   * @param bond
   * @param type - one of cESRTypeAbs,cESRTypeAnd,cESRTypeOr
   * @param group - index starting with 0
   */
  setBondESR(bond: number, type: number, group: number): void;

  /**
   * Molecule objects may represent complete molecules or sub-structure fragments,
   * depending on, whether they are flagges as being a fragment or not. Both
   * representations have much in common, but in certain aspects behave
   * differently. Thus, complete molecules are considered to carry implicit
   * hydrogens to fill unoccupied atom valences. Sub-structure fragments on the
   * other hand may carry atom or bond query features. Depiction, sub-structure
   * search, and other algorithms treat fragments and complete molecules
   * differently.
   * @param isFragment - if false, then all query features are removed
   */
  setFragment(isFragment: boolean): void;

  setName(name: string): void;

  /**
   * Removes any query features from the molecule
   * @returns whether any query features were removed
   */
  removeQueryFeatures(): boolean;

  /**
   * Removes all isotop information, i.e. sets all atoms to the natural isotop
   * abundance.
   * @returns true if something was changed
   */
  stripIsotopInfo(): boolean;

  translateCoords(dx: number, dy: number): void;

  scaleCoords(f: number): void;

  zoomAndRotateInit(x: number, y: number): void;

  zoomAndRotate(zoom: number, angle: number, selected: boolean): void;

  /**
   * This is the defined maximum valence (or set abnormal valence) neglecting atom
   * charge or radical influences, e.g. N or N(+) -> 3.
   * @param atom
   */
  getMaxValenceUncharged(atom: number): number;

  /**
   * This is the default maximum valence of the atom neglecting atom charge or
   * radical influences, e.g. N or N(+) -> 3. If the atomic no has multiple valid
   * max valences, it is the highest one.
   * @param atom
   */
  getDefaultMaxValenceUncharged(atom: number): number;

  /**
   * This is the defined maximum valence (or set abnormal valence) corrected by
   * atom charge or radical influences, e.g. N(+) -> 4.
   * @param atom
   */
  getMaxValence(atom: number): number;

  /**
   * This is the maximum valence correction caused by atom charge or radical
   * status, e.g. N+ -> 1; N- -> -1; Al+ -> -1; C+,C- -> -1. In some cases, where
   * the atomicNo can have multiple valences, the influence of a charge depends on
   * the atom's actual valence, e.g. valence corrections for R3P(+) and R5P(+) are
   * 1 and -1, respectively. Criteria are:<br>
   * -in the given valence state is there a lone pair that can be protonated<br>
   * -can we introduce a negative substituent as in BH3 or PF5 vs. SF6<br>
   * @param atom
   * @param occupiedValence
   */
  getElectronValenceCorrection(atom: number, occupiedValence: number): number;

  /**
   *
   * @param atom
   * @returns whether atom is an electronegative one
   */
  isElectronegative(atom: number): boolean;

  /**
   *
   * @param atom
   * @returns whether atom is an electropositive one
   */
  isElectropositive(atom: number): boolean;

  /**
   *
   * @param atom
   * @returns whether atom is any metal atom
   */
  isMetalAtom(atom: number): boolean;

  /**
   *
   * @param atom
   * @returns true if this atom is not a metal and not a nobel gas
   */
  isOrganicAtom(atom: number): boolean;

  /**
   * Clears destmol and then copies a part of this Molecule into destMol, being
   * defined by a mask of atoms to be included. If not all atoms are copied, then
   * destMol is set to be a substructure fragment.
   * @param destMol - receives the part of this Molecule
   * @param includeAtom - defines atoms to be copied; its size may be
   *                                  this.getAtoms() or this.getAllAtoms()
   * @param recognizeDelocalizedBonds - defines whether disconnected delocalized
   *                                  bonds will keep their single/double bond
   *                                  status or whether the query feature
   *                                  'delocalized bond' will be set
   * @param atomMap - null or int[] not smaller than
   *                                  includeAtom.length; receives atom indices of
   *                                  dest molecule
   */
  copyMoleculeByAtoms(
    destMol: Molecule,
    includeAtom: boolean[],
    recognizeDelocalizedBonds: boolean,
    atomMap: number[]
  ): void;

  /**
   * Clears destmol and then copies a part of this Molecule into destMol, being
   * defined by a mask of bonds to be included. Bonds, whose atoms carry opposite
   * charges are treated in the following manner: If only one of the two bond
   * atoms is kept, then its absolute charge will be reduced by 1.
   * @param destMol - receives the part of this Molecule
   * @param includeBond - defines bonds to be copied
   * @param recognizeDelocalizedBonds - defines whether disconnected delocalized
   *                                  bonds will keep their single/double bond
   *                                  status or whether the query feature
   *                                  'delocalized bond' will be set
   * @param atomMap - null or int[] not smaller than
   *                                  this.getAllAtoms()
   * @returns atom map from this to destMol with not copied atom's index being -1
   */
  copyMoleculeByBonds(
    destMol: Molecule,
    includeBond: boolean[],
    recognizeDelocalizedBonds: boolean,
    atomMap: number[]
  ): number[];

  /**
   * The neighbours (connected atoms) of any atom are sorted by their
   * relevance:<br>
   * 1. non-plain-hydrogen atoms (bond order 1 and above)<br>
   * 2. plain-hydrogen atoms (natural abundance, bond order 1)<br>
   * 3. non-plain-hydrogen atoms (bond order 0, i.e. metall ligand bond)<br>
   * Only valid after calling ensureHelperArrays(cHelperNeighbours or higher);
   * @param atom
   * @returns count of category 1 & 2 neighbour atoms (excludes neighbours
   *         connected with zero bond order)
   */
  getAllConnAtoms(atom: number): number;

  /**
   *
   * @param atom
   * @returns the number of connected plain explicit and implicit hydrogen atoms
   */
  getAllHydrogens(atom: number): number;

  /**
   * A validated molecule (after helper array creation) contains a sorted list of
   * all atoms with the plain (neglegible) hydrogen atoms at the end of the list.
   * Neglegible hydrogen atoms a those that can be considered implicit, because
   * they have no attached relevant information. Hydrogen atoms that cannot be
   * neglected are special isotops (mass != 0), if they carry a custom label, if
   * they are connected to another atom with bond order different from 1, or if
   * they are connected to another neglegible hydrogen.<br>
   * Only valid after calling ensureHelperArrays(cHelperNeighbours or higher);
   * @returns the number relevant atoms not including neglegible hydrogen atoms
   */
  getAtoms(): number;

  /**
   *
   * @param atom
   * @returns count of neighbour atoms connected by a 0-order metal ligand bond
   */
  getMetalBondedConnAtoms(atom: number): number;

  /**
   * This is different from the Hendrickson pi-value, which considers pi-bonds to
   * carbons only.
   * @param atom
   * @returns the number pi electrons at atom (the central atom of acetone would
   *         have 1)
   */
  getAtomPi(atom: number): number;

  /**
   *
   * @param atom
   * @returns 0 or the size of the smallest ring that atom is a member of
   */
  getAtomRingSize(atom: number): number;

  /**
   *
   * @param bond
   * @returns 0 or the size of the smallest ring that bond is a member of
   */
  getBondRingSize(bond: number): number;

  /**
   * The bond list is preprocessed such that all bonds leading to a plain hydrogen
   * atom (natural abundance, no custom labels) are at the end of the list. Only
   * valid after calling ensureHelperArrays(cHelperNeighbours or higher);
   * @returns count of bonds not including those connecting plain-H atoms
   */
  getBonds(): number;

  /**
   * @returns -1 or the bond that connects atom1 with atom2
   */
  getBond(atom1: number, atom2: number): number;

  /**
   * @returns a copy of this with all arrays sized to just cover all existing atoms
   *         and bonds
   */
  getCompactCopy(): Molecule;

  /**
   * The neighbours (connected atoms) of any atom are sorted by their
   * relevance:<br>
   * 1. non-plain-hydrogen atoms (bond order 1 and above)<br>
   * 2. plain-hydrogen atoms (natural abundance, bond order 1)<br>
   * 3. non-plain-hydrogen atoms (bond order 0, i.e. metall ligand bond)<br>
   * Only valid after calling ensureHelperArrays(cHelperNeighbours or higher);
   * @param atom
   * @param i - index into sorted neighbour list
   * @returns the i-th neighbor atom of atom
   */
  getConnAtom(atom: number, i: number): number;

  /**
   * The neighbours (connected atoms) of any atom are sorted by their
   * relevance:<br>
   * 1. non-plain-hydrogen atoms (bond order 1 and above)<br>
   * 2. plain-hydrogen atoms (natural abundance, bond order 1)<br>
   * 3. non-plain-hydrogen atoms (bond order 0, i.e. metall ligand bond)<br>
   * Only valid after calling ensureHelperArrays(cHelperNeighbours or higher);
   * @param atom
   * @returns count of category 1 neighbour atoms (excludes plain H and bond zero
   *         orders)
   */
  getConnAtoms(atom: number): number;

  /**
   * The neighbours (connected atoms) of any atom are sorted by their
   * relevance:<br>
   * 1. non-plain-hydrogen atoms (bond order 1 and above)<br>
   * 2. plain-hydrogen atoms (natural abundance, bond order 1)<br>
   * 3. non-plain-hydrogen atoms (bond order 0, i.e. metall ligand bond)<br>
   * Only valid after calling ensureHelperArrays(cHelperNeighbours or higher);
   * @param atom
   * @returns count of category 1 & 2 & 3 neighbour atoms (excludes neighbours
   *         connected with zero bond order)
   */
  getAllConnAtomsPlusMetalBonds(atom: number): number;

  /**
   * The neighbours (connected atoms) of any atom are sorted by their
   * relevance:<br>
   * 1. non-plain-hydrogen atoms (bond order 1 and above)<br>
   * 2. plain-hydrogen atoms (natural abundance, bond order 1)<br>
   * 3. non-plain-hydrogen atoms (bond order 0, i.e. metall ligand bond)<br>
   * Only valid after calling ensureHelperArrays(cHelperNeighbours or higher);
   * @param atom
   * @param i - index into sorted neighbour list
   * @returns index of bond connecting atom with its i-th neighbor
   */
  getConnBond(atom: number, i: number): number;

  /**
   * The neighbours (connected atoms) of any atom are sorted by their
   * relevance:<br>
   * 1. non-plain-hydrogen atoms (bond order 1 and above)<br>
   * 2. plain-hydrogen atoms (natural abundance, bond order 1)<br>
   * 3. non-plain-hydrogen atoms (bond order 0, i.e. metall ligand bond)<br>
   * Only valid after calling ensureHelperArrays(cHelperNeighbours or higher);
   * @param atom
   * @param i - index into sorted neighbour list
   * @returns order of bond connecting atom with its i-th neighbor
   */
  getConnBondOrder(atom: number, i: number): number;

  /**
   * This method returns the non-hydrogen neighbour count of atom. It excludes any
   * hydrogen atoms in contrast to getConnAtoms(), which only excludes plain
   * hydrogen (not deuterium, tritium, custom labelled hydrogen, etc.). Don't use
   * this method's return value for loops with getConnAtom(), getConnBond(), or
   * getConnBondOrder().
   * @param atom
   * @returns the number of non-hydrogen neighbor atoms
   */
  getNonHydrogenNeighbourCount(atom: number): number;

  /**
   * Calculates and returns the mean bond length of all bonds including or not
   * including hydrogen bonds. If there are no bonds, then the average distance
   * between unconnected atoms is returned. If we have less than 2 atoms,
   * cDefaultAverageBondLength is returned.
   * @param nonHydrogenBondsOnly
   */
  getAverageBondLength(nonHydrogenBondsOnly: boolean): number;

  /**
   * The sum of bond orders of explicitly connected neighbour atoms including
   * explicit hydrogen. The occupied valence includes bonds to atoms with set
   * cAtomQFExcludeGroup flags.
   * @param atom
   * @returns explicitly used valence
   */
  getOccupiedValence(atom: number): number;

  /**
   * The free valence is the number of potential additional single bonded
   * neighbours to reach the atom's maximum valence. Atomic numbers that have
   * multiple possible valences, the highest value is taken. Atom charges are
   * considered. Implicit hydrogens are not considered. Thus, the oxygen in a
   * R-O(-) has a free valence of 0, the nitrogen in R3N(+) has a free valence of
   * 1. Chlorine in Cl(-) has a free valence of 6. If you need the free valence
   * taking the lowest possible valence into account, use getLowestFreeValence(),
   * which would return 0 for Cl(-).
   * @param atom
   */
  getFreeValence(atom: number): number;

  /**
   * The free valence is the number of potential additional single bonded
   * neighbours to reach the atom's lowest valence above or equal its current
   * occupied valence. Atom charges are considered. Implicit hydrogens are not
   * considered. Thus, the phosphor atoms in PF2 and PF4 both have a lowest free
   * valence of 1. The oxygen in R-O(-) has a lowest free valence of 0, the
   * nitrogen in R3N(+) has a free valence of 1. If you need the maximum possible
   * free valence, use getFreeValence(), which would give 6 for Cl(-) and HCl.
   * @param atom
   */
  getLowestFreeValence(atom: number): number;

  /**
   * If the explicitly attached neighbors cause an atom valence to exceed the
   * lowest allowed valence for this atomic no, then this method returns the next
   * higher allowed valence, e.g. O=P(-H)-OMe :<br>
   * standard P valence is 3, used valence is 4, implicit abnormal valence is 5.
   * The molecule is interpreted as O=PH2-OMe. Requires cHelperNeighbours!
   * @param atom
   * @param neglectExplicitHydrogen
   * @returns abnormal valence or -1 if valence doesn't exceed standard valence
   */
  getImplicitHigherValence(
    atom: number,
    neglectExplicitHydrogen: boolean
  ): number;

  /**
   * Calculates for every non-H atom the mean value of all shortest routes (bonds
   * in between) to any other atom of the same fragment.
   */
  getAverageTopologicalAtomDistance(): number[];

  /**
   * Calculates the length of the shortest path between atoms atom1 and atom2
   * @param atom1
   * @param atom2
   * @returns path length (no of bonds); -1 if there is no path
   */
  getPathLength(atom1: number, atom2: number): number;

  /**
   * Locates and returns the shortest path between atoms atom1 and atom2
   * @param pathAtom array large enough to hold all path atoms, i.e.
   *                    maxLength+1
   * @param atom1 first atom of path; ends up in pathAtom[0]
   * @param atom2 last atom of path; ends up in pathAtom[pathLength]
   * @param maxLength paths larger than maxLength won't be detected
   * @param neglectBond null or bitmask of forbidden bonds
   * @returns number of bonds of path; -1 if there is no path
   */
  getPath(
    pathAtom: number[],
    atom1: number,
    atom2: number,
    maxLength: number,
    neglectBond: boolean[]
  ): number;

  /**
   * Finds bonds of a path that is defined by an atom sequence.
   * @param pathAtom - pathAtom[0]...[pathLength] -> list of atoms on path
   * @param pathBond - int array not smaller than pathLength
   * @param pathLength - no of path bonds == no of path atoms - 1
   */
  getPathBonds(
    pathAtom: number[],
    pathBond: number[],
    pathLength: number
  ): void;

  /**
   *
   * @param atom1
   * @param atom2
   * @returns whether there is a path of bonds leading from atom1 to atom2
   */
  shareSameFragment(atom1: number, atom2: number): boolean;

  /**
   * This adds a fragment from sourceMol to this molecule by first copying
   * rootAtom and then all connected atoms and bonds by traversing the graph
   * breadth first.
   * @param sourceMol - molecule from which the fragment is copied to this
   * @param rootAtom
   * @param atomMap - null or int[] not smaller than sourceMol.mAllAtoms; receives
   *                  atom indices of this molecule
   */
  addFragment(sourceMol: Molecule, rootAtom: number, atomMap: number[]): void;

  /**
   * Returns an array of all atoms for which a path of bonds leads to rootAtom.
   * Metal ligand bonds may or may not be considered a connection.
   * @param rootAtom
   * @param considerMetalBonds
   * @returns atoms being in the same fragment as rootAtom
   */
  getFragmentAtoms(rootAtom: number, considerMetalBonds: boolean): number[];

  /**
   * Locates all unconnected fragments in the Molecule and assigns fragment
   * indexes for every atom starting with 0. Optionally the fragment detection may
   * be restricted to those atoms that have been previously marked with
   * setAtomMarker(). In that case non-marked atoms receive the fragment number -1
   * and are not considered a connection between marked atoms potentially causing
   * two marked atoms to end up in different fragments, despite sharing the same
   * fragment. Metal ligand bonds may or may not be considered a connection.
   * @param fragmentNo - array at least mAllAtoms big to receive atom
   *                           fragment indexes
   * @param markedAtomsOnly - if true, then only atoms marked with
   *                           setAtomMarker() are considered
   * @param considerMetalBonds
   * @returns number of disconnected fragments
   */
  getFragmentNumbers(
    fragmentNo: number[],
    markedAtomsOnly: boolean,
    considerMetalBonds: boolean
  ): number;

  /**
   * Removes all unconnected fragments except for the largest one. If small
   * fragments were removed, then canonizeCharge() is called to neutralize charges
   * after potential removal of counter ions. Metal ligand bonds may or may not be
   * considered a connection.
   * @param considerMetalBonds
   * @returns atom mapping from old to new index; null if no fragments were removed
   */
  stripSmallFragments(considerMetalBonds: boolean): number[];

  /**
   * Starting from startAtom this method locates a system of annelated or bridged
   * ring systems with all members bonds being a ring bond. Detected member atoms
   * and bonds are flagged accordingly.
   * @param startAtom
   * @param aromaticOnly - if set then only aromatic atoms and bonds are considered
   * @param isMemberAtom
   * @param isMemberBond
   */
  findRingSystem(
    startAtom: number,
    aromaticOnly: boolean,
    isMemberAtom: boolean[],
    isMemberBond: boolean[]
  ): void;

  /**
   * Determines all atoms of the substituent attached to coreAtom and starting
   * with firstAtom. If isMemberAtom!=null, then all substituent member atoms will
   * have the the respective index being flagged upon return. This includes
   * firstAtom and excludes coreAtom. If substituent!=null, then it will contain
   * the substituent as Molecule. At the position of the coreAtom substituent will
   * contain a wildcard atom. If substituent!=null and atomMap!=null then atomMap
   * receives atom index mapping from this to substituent with non-member atoms
   * being -1. Returns -1 and an empty substituent if coreAtom and firstAtom share
   * a ring
   * @param coreAtom - the atom to which the substituent is connected
   * @param firstAtom - the substituent's atom that is connected to coreAtom
   * @param isMemberAtom - may be null, otherwise set to contain atom membership
   *                     mask
   * @param substituent - may be null, otherwise set to contain the substituent
   * @param atomMap - null or int[] not smaller than this.getAllAtoms()
   * @returns substituent atom count not counting coreAtom; -1 if coreAtom and
   *         firstAtom share a ring
   */
  getSubstituent(
    coreAtom: number,
    firstAtom: number,
    isMemberAtom: boolean[],
    substituent: Molecule,
    atomMap: number[]
  ): number;

  /**
   * Counts the number of atoms of the substituent connected to coreAtom defined
   * by firstAtom and not including the coreAtom.
   * @param coreAtom
   * @param firstAtom
   * @returns atom count of substituent or -1 if coreAtom and firstAtom are in the
   *         same ring
   */
  getSubstituentSize(coreAtom: number, firstAtom: number): number;

  /**
   * Whether an atom may be considered to carry implicit hydrogen atoms depends on
   * the atomicNo of that atom. Aluminum and all non/metal atoms except the nobel
   * gases and except hydrogen itself are considered to carry implicit hydrogens
   * to fill up their unoccupied valences. Atoms with an assigned unusual valence
   * always support implicit hydrogens independent of their atomicNo.
   * @param atom
   * @returns true if this atom's unoccupied valences are considered to be implicit
   *         hydrogens
   */
  supportsImplicitHydrogen(atom: number): boolean;

  /**
   * Calculates and return the number of implicit hydrogens at atom. If atom is
   * itself a hydrogen atom, a metal except Al, or a noble gase, then 0 is
   * returned. For all other atom kinds the number of implicit hydrogens is
   * basically the lowest typical valence that is compatible with the occupied
   * valence, minus the occupied valence corrected by atom charge and radical
   * state.
   * @param atom
   */
  getImplicitHydrogens(atom: number): number;

  /**
   *
   * @param atom
   * @returns number of explicit plain hydrogen atoms (does not include D,T,custom
   *         labelled H, etc)
   */
  getExplicitHydrogens(atom: number): number;

  /**
   * Calculates a rounded mass of the molecule
   */
  getMolweight(): number;

  /**
   * Simple method to calculate rotatable bonds. This method counts all single
   * bonds provided that they<br>
   * - are not a terminal bond<br>
   * - are not part of a ring<br>
   * - are not an amide bond<br>
   * - are not the second of two equivalent bonds next to the same triple bond<br>
   */
  getRotatableBondCount(): number;

  /**
   * In a consecutive sequence of sp-hybridized atoms multiple single bonds cause
   * redundant torsions. Only that single bond with the smallest bond index is
   * considered really rotatable; all other single bonds are pseudo rotatable. If
   * one/both end(s) of the sp-atom sequence doesn't carry atoms outside of the
   * straight line then no bond is considered rotatable. A simple terminal single
   * bond
   * @param bond
   * @returns true, if this bond is not considered rotatable because of a
   *         redundancy
   */
  isPseudoRotatableBond(bond: number): boolean;

  getAromaticRingCount(): number;

  /**
   * Calculates the number of independent rings of which 'atom' is a member. Any
   * combination of two connected atoms to 'atom' is used for: - finding the
   * shortest path connecting these two neighbors avoiding 'atom' - if such a path
   * exists and at least one bonds of that path is not a member of a path found
   * earlier then count this path as an independent ring closure.
   * @param atom
   * @param maxRingSize
   * @returns number of independent rings
   */
  getAtomRingCount(atom: number, maxRingSize: number): number;

  /**
   * Locates that single bond which is the preferred one to be converted into
   * up/down bond in order to define the atom chirality.
   * @param atom - parity carrying atom, i.e. a tetrahedral stereocenter or central
   *             allene atom
   * @returns preferred bond or -1, if no single bond existing
   */
  getAtomPreferredStereoBond(atom: number): number;

  /**
   * Locates that single bond which is the preferred one to be converted into
   * up/down bond in order to define the bond chirality.
   * @param bond - BINAP type of chirality bond
   * @returns preferred bond or -1, if no single bond existing
   */
  getBondPreferredStereoBond(bond: number): number;

  /**
   *
   * @param atom
   * @returns whether the atom is in an allylic/benzylic position
   */
  isAllylicAtom(atom: number): boolean;

  isAromaticAtom(atom: number): boolean;

  isAromaticBond(bond: number): boolean;

  /**
   * A bond is considered delocalized, if it has different bond orders in
   * different, but energetically equivalent mesomeric structures. Bonds in
   * aromatic 6-membered rings typically are delocalized, while those in uncharged
   * 5-membered aromatic rings are not. Indole has 6 delocalized bonds.
   * @param bond
   */
  isDelocalizedBond(bond: number): boolean;

  isRingAtom(atom: number): boolean;

  isRingBond(bond: number): boolean;

  /**
   *
   * @param atom
   * @returns whether atom is a member of a ring not larger than 7 atoms
   */
  isSmallRingAtom(atom: number): boolean;

  /**
   *
   * @param bond
   * @returns whether bond is a member of a ring not larger than 7 atoms
   */
  isSmallRingBond(bond: number): boolean;

  /**
   *
   * @param atom
   * @returns whether atom has a neighbor that is connected through a double/triple
   *         bond to a hetero atom
   */
  isStabilizedAtom(atom: number): boolean;

  getAtomRingBondCount(atom: number): number;

  getChiralText(): string;

  /**
   * Checks whether at least one of the connected bonds is a stereo bond. If atom
   * is the central atom of an allene, then its direct neighbours are checked,
   * whether one of them has a stereo bond.
   * @param atom
   * @returns the stereo bond or -1 if not found
   */
  getStereoBond(atom: number): number;

  /**
   * Atom stereo parities and bond E/Z-parities are properties that are usually
   * perceived from up/down-bonds and atom coordinates, respectively. This is done
   * during the helper array calculation triggered by
   * ensureHelperArrays(cHelperParities).<br>
   * This method tells the molecule that current atom/bond parities are valid,
   * even if the stereo perception not has been performed. In addition to the
   * stereo parities one may declare CIP parities and/or symmetry ranks also to be
   * valid (helperStereoBits != 0). setParitiesValid(0) should be called if no
   * coordinates are available but the parities are valid nevertheless, e.g. after
   * the IDCodeParser has parsed an idcode without coordinates. (Note: After
   * idcode parsing unknown stereo centers have parities cAtomParityNone instead
   * of cAtomParityUnknown. Thus, calling isStereoCenter(atom) returns false!!!)
   * Declaring parities valid prevents the Canonizer to run the stereo recognition
   * again when ensureHelperArrays(cHelperParities or higher) is called.<br>
   * May also be called after filling valences with explicit hydrogen atoms, which
   * have no coordinates, to tell the molecule that the earlier created stereo
   * flags are still valid.
   * @param helperStereoBits - 0 or combinations of
   *                         cHelperBitCIP,cHelperBitSymmetry...,cHelperBitIncludeNitrogenParities
   */
  setParitiesValid(helperStereoBits: number): void;

  /**
   * This converts one single bond per parity into a stereo up/down bond to
   * correctly reflect the given parity. This works for tetrahedral and allene
   * atom parities as well as for BINAP type of bond parities. Should only be
   * called with valid TH and EZ parities and valid coordinates, e.g. after idcode
   * parsing with coordinates or after coordinate generation.
   */
  setStereoBondsFromParity(): void;

  /**
   * Converts any stereo bond attached with its pointed tip to this atom into a
   * single bond.
   * @param atom
   */
  convertStereoBondsToSingleBonds(atom: number): void;

  setStereoBondFromAtomParity(atom: number): void;

  /**
   * If the atom is a stereo center in fisher projection, then its tetrahedral
   * parity is returned. If the horizontal bonds are plain single bonds, then they
   * are interpreted as up-bonds.
   * @param atom - the stereo center
   * @param sortedConnMap - map of neighbours sorted by atom index
   * @param angle - bond angles sorted by neighbour atom index
   * @param direction - null or int[] large enough to receive bond directions
   * @returns cAtomParity1,cAtomParity2 or cAtomParityUnknown
   */
  getFisherProjectionParity(
    atom: number,
    sortedConnMap: number[],
    angle: number[],
    direction: number[]
  ): number;

  /**
   * In case bond is a BINAP kind of chiral bond with defined parity, then the
   * preferred neighbour single bond is converted into a stereo bond to correctly
   * reflect its defined parity.
   * @param bond
   */
  setStereoBondFromBondParity(bond: number): void;

  /**
   * Checks whether atom is one of the two end of an allene.
   * @param atom
   * @returns allene center or -1
   */
  findAlleneCenterAtom(atom: number): number;

  /**
   * Checks whether atom is one of the two atoms of an axial chirality bond of
   * BINAP type. Condition: non-aromatic single bond connecting two aromatic rings
   * with 6 or more members that together bear at least three ortho substituents.
   * A stereo bond indicating the chirality is not(!!!) a condition.
   * @param atom - to check, whether it is part of a bond, which has BINAP type of
   *             axial chirality
   * @returns axial chirality bond or -1 if axial chirality conditions are not met
   */
  findBINAPChiralityBond(atom: number): number;

  /**
   * Evaluates, whether bond is an amide bond, thio-amide, or amidine bond.
   * @param bond
   */
  isAmideTypeBond(bond: number): boolean;

  /**
   * Checks whether this nitrogen atom is flat, because it has a double bond, is
   * member of an aromatic ring or is part of amide, an enamine or in resonance
   * with an aromatic ring. It is also checked that ortho substituents don't force
   * the amine into a non-resonance torsion. State of helper arrays must be at
   * least cHelperRings.
   * @param atom
   */
  isFlatNitrogen(atom: number): boolean;

  /**
   * Checks whether bond is an axial chirality bond of the BINAP type. Condition:
   * non-aromatic, non-small-ring (<= 7 members) single bond connecting two
   * aromatic rings with 6 or more members each that together bear at least three
   * ortho substituents. A stereo bond indicating the chirality is not(!!!) a
   * condition.
   * @param bond
   * @returns true if axial chirality conditions are met
   */
  isBINAPChiralityBond(bond: number): boolean;

  validate(): void;

  /**
   * Normalizes different forms of functional groups (e.g. nitro) to a preferred
   * one. This step should precede any canonicalization.
   * @returns true if the molecule was changed
   */
  normalizeAmbiguousBonds(): boolean;

  /**
   *
   * @param atom
   * @returns whether atom is one of Li,Na,K,Rb,Cs
   */
  isAlkaliMetal(atom: number): boolean;

  /**
   *
   * @param atom
   * @returns whether atom is one of Mg,Ca,Sr,Ba
   */
  isEarthAlkaliMetal(atom: number): boolean;

  /**
   *
   * @param atom
   * @returns whether atom is one of N,P,As
   */
  isNitrogenFamily(atom: number): boolean;

  /**
   *
   * @param atom
   * @returns whether atom is one of O,S,Se,Te
   */
  isChalcogene(atom: number): boolean;

  /**
   *
   * @param atom
   * @returns whether atom is one of F,Cl,Br,I
   */
  isHalogene(atom: number): boolean;

  /**
   * Canonizes charge distribution in single- and multifragment molecules.
   * Neutralizes positive and an equal amount of negative charges on
   * electronegative atoms, provided these are not on 1,2-dipolar structures, in
   * order to ideally achieve a neutral molecule. This method does not change the
   * overall charge of the molecule. It does not change the number of explicit
   * atoms or bonds or their connectivity except bond orders.
   * @param allowUnbalancedCharge
   * @returns remaining overall molecule charge
   */
  canonizeCharge(allowUnbalancedCharge: boolean): number;

  /**
   * Provided that the bond parity of a double bond is available, this method
   * determines, whether connAtom has a counterpart with Z- (cis) configuration at
   * the other end of the double bond. If there is no Z-counterpart, then -1 is
   * returned. Requires cHelperParities.
   * @param connAtom - directly connected to one of the double bond atoms
   * @param bond - double bond with available bond parity
   * @returns -1 or counterpart to connAtom in Z-configuration
   */
  getZNeighbour(connAtom: number, bond: number): number;

  getHelperArrayStatus(): number;

  /**
   * While the Molecule class covers all primary molecule information, its derived
   * class ExtendedMolecule handles secondary, i.e. calculated molecule
   * information, which is cached in helper arrays and stays valid as long as the
   * molecule's primary information is not changed. Most methods of
   * ExtendedMolecule require some of the helper array's information. High level
   * methods, e.g. getPath(), take care of updating an outdated cache themselves.
   * Low level methods, e.g. isAromaticAtom(), which typically are called very
   * often, do not check for validity nor update the helper arrays themselves. If
   * you use low level methods, then you need to make sure that the needed helper
   * array information is valid by this method.<br>
   * For performance reasons there are <b>distinct levels of helper
   * information</b>. (A higher level always includes all properties of the
   * previous level):<br>
   * <i>cHelperNeighbours:</i> explicit hydrogen atoms are moved to the end of the
   * atom table and bonds leading to them are moved to the end of the bond table.
   * This way algorithms can skip hydrogen atoms easily. For every atom directly
   * connected atoms and bonds (with and without hydrogens) are determined. The
   * number of pi electrons is counted.<br>
   * <i>cHelperRings</i>: Aromatic and non-aromatic rings are detected. Atom and
   * bond ring properties are set and a ring collection provides a total set of
   * small rings (7 or less atoms). Atoms being in allylic/benzylic or stabilized
   * (neighbor of a carbonyl or similar group) position are flagged as such.<br>
   * <i>cHelperParities</i>: Atom (tetrahedral or axial) and bond (E/Z or atrop)
   * parities are calculated from the stereo configurations.<br>
   * <i>cHelperCIP</i>: Cahn-Ingold-Prelog stereo information for atoms and
   * bonds.<br>
   * <br>
   * cHelperParities and cHelperCIP require a StereoMolecule!!!<br>
   * @param required - one of
   *                 cHelperNeighbours,cHelperRings,cHelperParities,cHelperCIP
   * @returns true if the molecule was changed
   */
  ensureHelperArrays(required: number): void;

  /**
   * If ensureHelperArrays() (and with it handleHydrogens()) was not called yet on
   * a fresh molecule and if the molecule contains simple hydrogen atoms within
   * non-hydrogens atoms, then this function returns a map from current atom
   * indexes to those new atom indexes that would result from a call to
   * handleHydrogens.
   */
  getHandleHydrogenMap(): number[];

  /**
   * Uncharged hydrogen atoms with no isotop information nor with an attached
   * custom label are considered simple and can usually be suppressed, effectively
   * converting them from an explicit to an implicit hydrogen atom.<br>
   * <b>Note:</b> This method returns true for uncharged, natural abundance
   * hydrogens without custom labels even if they have a non-standard bonding
   * situation (everything being different from having one single bonded
   * non-simple-hydrogen neighbour, e.g. unbonded hydrogen, H2, a metal ligand
   * bond to another atom, two single bonds, etc.) If unusual bonding needs to be
   * considered, check for that independently from this method.
   * @param atom
   */
  isSimpleHydrogen(atom: number): boolean;

  /**
   * Removes all plain explicit hydrogens atoms from the molecule, converting them
   * effectively to implicit ones. If the molecules has 2D-coordinates (is3D==false),
   * then this method perceives stereo configurations from up/down-bonds
	 * to explicit hydrogens before deleting them and turns another bond into a
   * stereo bond to indicate the proper configuration.
	 * If the removal of a hydrogen atom would change an atom's implicit valence,
   * the atom's abnormal valence is set accordingly.
	 * @param is3D pass true, if atom coordinates are three dimensional
   */
  removeExplicitHydrogens(is3D: boolean): void;

  /**
   * Separates all disconnected fragments of this Molecule into individual
   * Molecule objects. If fragment separation is only needed, if there are
   * multiple fragments, it may be more efficient to run this functionality in two
   * steps, e.g.:<br>
   * int[] fragmentNo = new int[mol.getAllAtoms()];<br>
   * int fragmentCount = getFragmentNumbers(fragmentNo, boolean, boolean);<br>
   * if (fragmentCount > 1) {<br>
   * StereoMolecule[] fragment = getUniqueFragmentsEstimated(int[] fragmentNo,
   * fragmentCount);<br>
   * ...<br>
   * }<br>
   */
  getFragments(): Molecule[];

  /**
   * Removes defined and implicit stereo information from the molecule.<br>
   * - up/down-bonds are converted to double bonds<br>
   * - stereo centers are flagged to be unknown<br>
   * - double bonds with implicit stereo configurations are converted into cross
   * bonds<br>
   * - all atom and bond ESR assignments are removed<br>
   * - parity and CIP helper state is set to invalid, such that stereo calculation
   * is redone, if needed.
   */
  stripStereoInformation(): void;

  /**
   * This returns the absolute(!) atom parity from the canonization procedure.
   * While the molecule's (relative) atom parity returned by getAtomParity() is
   * based on atom indices and therefore depends on the order of atoms, the
   * absolute atom parity is based on atom ranks and therefore independent of the
   * molecule's atom order. Usually relative parities are used, because the atom's
   * stereo situation can be interpreted without the need for atom rank
   * calculation. This requires valid helper arrays level cHelperParities or
   * higher.
   * @param atom
   * @returns one of the Molecule.cAtomParityXXX constants
   */
  getAbsoluteAtomParity(atom: number): number;

  /**
   * This returns the absolute(!) bond parity from the canonization procedure.
   * While the molecule's (relative) bond parity returned by getBondParity() is
   * based on atom indices and therefore depends on the order of atoms, the
   * absolute bond parity is based on atom ranks and therefore independent of the
   * molecule's atom order. Usually relative parities are used, because the bond's
   * stereo situation can be interpreted without the need for atom rank
   * calculation. This requires valid helper arrays level cHelperParities or
   * higher.
   * @param bond
   * @returns one of the Molecule.cBondParityXXX constants
   */
  getAbsoluteBondParity(bond: number): number;

  /**
   * This returns atom symmetry numbers from within the molecule canonicalization
   * procedure. Atoms with same symmetry numbers can be considered topologically
   * equivalent. Symmetry ranks are only available after calling
   * ensureHelperArrays(cHelperSymmetry...). In mode cHelperSymmetrySimple
   * stereoheterotopic atoms are considered equivalent. In mode
   * cHelperSymmetryDiastereotopic only diastereotopic atoms are distinguished. In
   * mode cHelperSymmetryEnantiotopic all stereoheterotopic atoms, i.e.
   * enantiotopic and diastereotopic atoms, are distinguished.
   * @param atom
   */
  getSymmetryRank(atom: number): number;

  /**
   * This is a convenience method that creates the molecule's idcode without
   * explicitly creating a Canonizer object for this purpose. The idcode is a
   * compact String that uniquely encodes the molecule with all stereo and query
   * features. <br>
   * WARNING: If the molecule has no atom coordinates but valid parities, e.g.
   * after new IDCodeParser(false).parse(idcode, null), this method returns null;
   */
  getIDCode(): string;

  /**
   * This is a convenience method that creates the molecule's id-coordinates
   * matching the idcode available with getIDCode(). It does not explicitly create
   * a Canonizer object for this purpose. <br>
   * WARNING: If the molecule has no atom coordinates but valid parities, e.g.
   * after new IDCodeParser(false).parse(idcode, null), this method returns null;
   */
  getIDCoordinates(): string;

  getStereoCenterCount(): number;

  /**
   * Sets all atoms with TH-parity 'unknown' to explicitly defined 'unknown'. Sets
   * all double bonds with EZ-parity 'unknown' to cross bonds.
   */
  setUnknownParitiesToExplicitlyUnknown(): void;

  /**
   * This is a policy setting for this StereoMolecule as molecule container. If
   * set to true then this StereoMolecule will treat tetrahedral nitrogen atoms
   * with three or four distinguishable substituents as stereo centers and will
   * assign parities. deleteMolecule() does not change this behavior.
   * @param b
   */
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
  inflateToMaxAVBL?: boolean;

  inflateToHighResAVBL?: boolean;

  chiralTextBelowMolecule?: boolean;

  chiralTextAboveMolecule?: boolean;

  chiralTextOnFrameTop?: boolean;

  chiralTextOnFrameBottom?: boolean;

  noTabus?: boolean;

  showAtomNumber?: boolean;

  showBondNumber?: boolean;

  highlightQueryFeatures?: boolean;

  showMapping?: boolean;

  suppressChiralText?: boolean;

  suppressCIPParity?: boolean;

  suppressESR?: boolean;

  showSymmetrySimple?: boolean;

  showSymmetryDiastereotopic?: boolean;

  showSymmetryEnantiotopic?: boolean;

  noImplicitAtomLabelColors?: boolean;

  noStereoProblem?: boolean;
}

export declare class Reaction {
  private constructor();

  /**
   * Create a new empty `Reaction`.
   */
  static create(): Reaction;

  /**
   * Create a new `Reaction` filled with the provided molecules.
   * @param molecules - Array of `Molecule` objects
   * @param reactantCount - Number of reactants in the `molecules` array.
   * The remaining objects will be treated as products.
   */
  static fromMolecules(molecules: Molecule[], reactantCount: number): Reaction;

  /**
   * Create a new `Reaction` based on a reaction SMILES string. The `Reaction` will contain at most one `Molecule`
   * for each component.
   * @param smiles
   */
  static fromSmiles(smiles: string): Reaction;

  /**
   * Create a new `Reaction` based on a MDL Reaction file (V2000 or V3000).
   * @param rxn - The RXN file's contents
   */
  static fromRxn(rxn: string): Reaction;

  /**
   * Serialize the `Reaction` to a reaction SMILES string.
   */
  toSmiles(): string;

  /**
   * Serialize the `Reaction` to a MDL V2000 Reaction file.
   */
  toRxn(programName?: string): string;

  /**
   * Serialize the `Reaction` to a MDL V3000 Reaction file.
   */
  toRxnV3(programName?: string): string;

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

export interface IInitializeConformersOptions {
  /**
   * One of the ConformerGenerator.STRATEGY_ constants.
   * Default: `ConformerGenerator.STRATEGY_LIKELY_RANDOM`.
   */
  strategy?: number;
  /**
   * Maximum number of distinct torsion sets the strategy will try.
   * Default: `100000`.
   */
  maxTorsionSets?: number;
  /**
   * Use 60 degree steps for every rotatable bond instead of torsion DB.
   * Default: `false`.
   */
  use60degreeSteps?: boolean;
}

export declare class ConformerGenerator {
  static STRATEGY_LIKELY_SYSTEMATIC: number;
  static STRATEGY_PURE_RANDOM: number;
  static STRATEGY_LIKELY_RANDOM: number;
  static STRATEGY_ADAPTIVE_RANDOM: number;

  constructor(seed: number);

  /**
   * Fills all free valences of mol with explicit hydrogens and tries to
	 * create a reasonable conformer by starting with the most likely torsion set.
	 * If there are collisions, then less likely torsions are tried to find
	 * a collision free conformer. If it succeeds, mol receives the modified
	 * atom coordinates and mol is returned. If the conformer generation fails,
	 * then null is returned. The torsion strategy used is STRATEGY_ADAPTIVE_RANDOM.
	 * New 3D-coordinates correctly reflect E/Z and R/S bond/atom parities.
	 * This is a convenience method that does not require any initialization.
   * @param mol The molecule that will receive new 3D coordinates in place.
   * @returns - Original molecule with new 3D-coordinates or null.
   */
  getOneConformerAsMolecule(mol: Molecule): Molecule | null;

  /**
   * The `initializeConformers()` method needs to be called before getting individual
	 * conformers of the same molecule by `getNextConformerAsMolecule()`.
	 * Open valences of the passed molecule are filled with hydrogen atoms.
	 * The passed molecule may repeatedly be used as container for a new conformer's atom
	 * coordinates, if it is passed to getNextConformerAsMolecule().
   * @param mol - Will be saturated with hydrogen atoms.
   * @param options
   * @returns - `false` if there is a structure problem.
   */
  initializeConformers(mol: Molecule, options?: IInitializeConformersOptions): boolean;

  /**
   * Creates the next random, likely or systematic new(!) conformer of the molecule
	 * that was passed when calling `initializeConformers()`. A new conformer is one,
	 * whose combination of torsion angles was not used in a previous conformer
	 * created by this function since the last call of `initializeConformers()`.
	 * Parameter mol may be null or recycle the original molecule to receive new 3D coordinates.
	 * If it is null, then a fresh copy of the original molecule with new atom coordinates is returned.
	 * Every call of this method creates a new collision-free conformer until the employed torsion set
	 * strategy decides that it cannot generate any more suitable torsion sets.
   * @param mol
   */
  getNextConformerAsMolecule(mol?: Molecule): Molecule | null;

  /**
   * @returns - Count of valid delivered conformers.
   */
  getConformerCount(): number;

  /**
   * Calculates the potential count of conformers by multiplying degrees of freedom
	 * (torsions per rotatable bond & rigid fragment multiplicities).
	 * Cannot be called before calling `initializeConformers()`.
   */
  getPotentialConformerCount(): number;

  /**
   * With best current knowledge about colliding torsion combinations
	 * and based on the individual frequencies of currently active torsions
	 * this method returns the conformers's overall contribution to the
	 * total set of non colliding conformers.
   * @returns - This conformer's contribution to all conformers.
   */
  getPreviousConformerContribution(): number;

  /** 
   * Returns an iterator of molecule conformers.
   */
  molecules(): IterableIterator<Molecule>;
}

export interface IForceFieldMMFF94Options {
  // TODO
}

export interface IForceFieldMinimiseOptions {
  /**
   * The maximum number of iterations to run for.
   * Default: 4000.
   */
  maxIts?: number;

  /**
   * The gradient tolerance.
   * Default: 1e-4.
   */
  gradTol?: number;

  /**
   * The energy tolerance.
   * Default: 1e-6.
   */
  funcTol?: number;
}

export declare class ForceFieldMMFF94 {
  static MMFF94: 'MMFF94';
  static MMFF94S: 'MMFF94s';
  static MMFF94SPLUS: 'MMFF94s+';

  /**
   * 
   * @param molecule - The molecule to construct the forcefield on.
   * @param tablename - The string name for the Tables to be used. Can be `'MMFF94'`, `'MMFF94s'` or `'MMFF94s+'`.
   * @param options
   */
  constructor(molecule: Molecule, tablename: 'MMFF94' | 'MMFF94s' | 'MMFF94s+', options?: IForceFieldMMFF94Options);

  /**
   * Returns the total number of atoms in this force field.
   */
  size(): number;

  /**
   * Gets the total energy of the molecule as the sum of the energy
   * terms.
   */
  getTotalEnergy(): number;

  /**
   * Minimise the current molecule.
   * @param options
   * @returns - Return code, 0 on success.
   */
  minimise(options?: IForceFieldMinimiseOptions): number;
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
  constructor(element: HTMLElement, useSVG?: boolean, scale?: number);

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
  static createSVGEditor(id: string, scale: number): StructureEditor;

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

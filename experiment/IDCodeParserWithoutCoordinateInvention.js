const encoder = new TextEncoder();
const decoder = new TextDecoder();

import Molecule from './Molecule.js';
import StereoMolecule from './StereoMolecule.js';
import Canonizer from './Canonizer.js';
import AromaticityResolver from './AromaticityResolver.js';

export default class IDCodeParserWithoutCoordinateInvention {
  mMol = null;
  mDecodingBytes = null;
  mIDCodeBitsAvail = 0;
  mIDCodeTempData = 0;
  mIDCodeBufferIndex = 0;
  mNeglectSpaceDelimitedCoordinates = false;

  ensure2DCoordinates() {
    return false;
  }

  neglectSpaceDelimitedCoordinates() {
    this.mNeglectSpaceDelimitedCoordinates = true;
  }

  getCompactMolecule(idcode = '') {
    if (idcode.length === 0) {
      return null;
    }
    const bytes = encoder.encode(idcode);
    this.decodeBitsStart(bytes, 0);
    let abits = this.decodeBits(4);
    let bbits = this.decodeBits(4);

    if (abits > 8) {
      // abits is the version number
      abits = bbits;
    }

    const allAtoms = this.decodeBits(abits);
    const allBonds = this.decodeBits(bbits);

    const mol = new StereoMolecule(allAtoms, allBonds);
    this.parse(mol, bytes, null, 0, 0);
    return mol;
  }

  parse(mol, idcode, coordinates, idcodeStart, coordsStart) {
    mol.clear();

    if (idcode == null || idcodeStart >= idcode.length) {
      return;
    }

    this.mMol = mol;
    let version = Canonizer.cIDCodeVersion2;

    if (coordinates != null && coordsStart >= coordinates.length)
      coordinates = null;

    this.decodeBitsStart(idcode, idcodeStart);
    let abits = this.decodeBits(4);
    let bbits = this.decodeBits(4);

    if (abits > 8) {
      // abits is the version number
      version = abits;
      abits = bbits;
    }

    if (abits == 0) {
      this.mMol.setFragment(this.decodeBits(1) == 1);
      return;
    }

    let allAtoms = this.decodeBits(abits);
    let allBonds = this.decodeBits(bbits);
    let nitrogens = this.decodeBits(abits);
    let oxygens = this.decodeBits(abits);
    let otherAtoms = this.decodeBits(abits);
    let chargedAtoms = this.decodeBits(abits);
    for (let atom = 0; atom < allAtoms; atom++) this.mMol.addAtom(6);
    for (let i = 0; i < nitrogens; i++)
      this.mMol.setAtomicNo(this.decodeBits(abits), 7);
    for (let i = 0; i < oxygens; i++)
      this.mMol.setAtomicNo(this.decodeBits(abits), 8);
    for (let i = 0; i < otherAtoms; i++)
      this.mMol.setAtomicNo(this.decodeBits(abits), this.decodeBits(8));
    for (let i = 0; i < chargedAtoms; i++)
      this.mMol.setAtomCharge(this.decodeBits(abits), this.decodeBits(4) - 8);

    let closureBonds = 1 + allBonds - allAtoms;
    let dbits = this.decodeBits(4);
    let base = 0;

    this.mMol.setAtomX(0, 0.0);
    this.mMol.setAtomY(0, 0.0);
    this.mMol.setAtomZ(0, 0.0);

    let decodeOldCoordinates = coordinates !== null && coordinates[0] >= "'";
    let targetAVBL = 0.0;
    let xOffset = 0.0;
    let yOffset = 0.0;
    let zOffset = 0.0;
    let coordsAre3D = false;
    let coordsAreAbsolute = false;

    if (decodeOldCoordinates) {
      // old coordinate encoding
      if (
        (coordinates.length > 2 * allAtoms - 2 &&
          coordinates[2 * allAtoms - 2] == "'") ||
        (coordinates.length > 3 * allAtoms - 3 &&
          coordinates[3 * allAtoms - 3] == "'")
      ) {
        // old faulty encoding
        coordsAreAbsolute = true;
        coordsAre3D = coordinates.length == 3 * allAtoms - 3 + 9;
        let index = coordsAre3D ? 3 * allAtoms - 3 : 2 * allAtoms - 2;
        let avblInt =
          86 * (coordinates[index + 1] - 40) + coordinates[index + 2] - 40;
        targetAVBL = Math.pow(10.0, avblInt / 2000.0 - 1.0);
        index += 2;
        let xInt =
          86 * (coordinates[index + 1] - 40) + coordinates[index + 2] - 40;
        xOffset = Math.pow(10.0, xInt / 1500.0 - 1.0);
        index += 2;
        let yInt =
          86 * (coordinates[index + 1] - 40) + coordinates[index + 2] - 40;
        yOffset = Math.pow(10.0, yInt / 1500.0 - 1.0);
        if (coordsAre3D) {
          index += 2;
          let zInt =
            86 * (coordinates[index + 1] - 40) + coordinates[index + 2] - 40;
          zOffset = Math.pow(10.0, zInt / 1500.0 - 1.0);
        }
      } else {
        coordsAre3D = coordinates.length == 3 * allAtoms - 3;
      }
    }

    // don't use 3D coordinates, if we need 2D
    if (this.ensure2DCoordinates() && coordsAre3D) {
      coordinates = null;
      decodeOldCoordinates = false;
    }

    for (let i = 1; i < allAtoms; i++) {
      let dif = this.decodeBits(dbits);
      if (dif == 0) {
        if (decodeOldCoordinates) {
          this.mMol.setAtomX(
            i,
            this.mMol.getAtomX(0) + 8 * (coordinates[i * 2 - 2] - 83),
          );
          this.mMol.setAtomY(
            i,
            this.mMol.getAtomY(0) + 8 * (coordinates[i * 2 - 1] - 83),
          );
          if (coordsAre3D)
            this.mMol.setAtomZ(
              i,
              this.mMol.getAtomZ(0) +
                8 * (coordinates[2 * allAtoms - 3 + i] - 83),
            );
        }

        closureBonds++;
        continue;
      }

      base += dif - 1;

      if (decodeOldCoordinates) {
        this.mMol.setAtomX(
          i,
          this.mMol.getAtomX(base) + coordinates[i * 2 - 2] - 83,
        );
        this.mMol.setAtomY(
          i,
          this.mMol.getAtomY(base) + coordinates[i * 2 - 1] - 83,
        );
        if (coordsAre3D)
          this.mMol.setAtomZ(
            i,
            this.mMol.getAtomZ(base) + (coordinates[2 * allAtoms - 3 + i] - 83),
          );
      }
      this.mMol.addBond(base, i, Molecule.cBondTypeSingle);
    }

    for (let i = 0; i < closureBonds; i++)
      this.mMol.addBond(
        this.decodeBits(abits),
        this.decodeBits(abits),
        Molecule.cBondTypeSingle,
      );

    let isDelocalizedBond = new Array(allBonds).fill(false);

    for (let bond = 0; bond < allBonds; bond++) {
      let bondOrder = this.decodeBits(2);
      switch (bondOrder) {
        case 0:
          isDelocalizedBond[bond] = true;
          break;
        case 2:
          this.mMol.setBondType(bond, Molecule.cBondTypeDouble);
          break;
        case 3:
          this.mMol.setBondType(bond, Molecule.cBondTypeTriple);
          break;
      }
    }

    let THCount = this.decodeBits(abits);
    for (let i = 0; i < THCount; i++) {
      let atom = this.decodeBits(abits);
      if (version == Canonizer.cIDCodeVersion2) {
        let parity = this.decodeBits(2);
        if (parity == 3) {
          // this was the old discontinued Molecule.cAtomParityMix
          // version2 idcodes had never more than one center with parityMix
          this.mMol.setAtomESR(atom, Molecule.cESRTypeAnd, 0);
          this.mMol.setAtomParity(atom, Molecule.cAtomParity1, false);
        } else {
          this.mMol.setAtomParity(atom, parity, false);
        }
      } else {
        let parity = this.decodeBits(3);
        switch (parity) {
          case Canonizer.cParity1And:
            this.mMol.setAtomParity(atom, Molecule.cAtomParity1, false);
            this.mMol.setAtomESR(
              atom,
              Molecule.cESRTypeAnd,
              this.decodeBits(3),
            );
            break;
          case Canonizer.cParity2And:
            this.mMol.setAtomParity(atom, Molecule.cAtomParity2, false);
            this.mMol.setAtomESR(
              atom,
              Molecule.cESRTypeAnd,
              this.decodeBits(3),
            );
            break;
          case Canonizer.cParity1Or:
            this.mMol.setAtomParity(atom, Molecule.cAtomParity1, false);
            this.mMol.setAtomESR(atom, Molecule.cESRTypeOr, this.decodeBits(3));
            break;
          case Canonizer.cParity2Or:
            this.mMol.setAtomParity(atom, Molecule.cAtomParity2, false);
            this.mMol.setAtomESR(atom, Molecule.cESRTypeOr, this.decodeBits(3));
            break;
          default:
            this.mMol.setAtomParity(atom, parity, false);
        }
      }
    }

    if (version == Canonizer.cIDCodeVersion2)
      if (this.decodeBits(1) == 0)
        // translate chiral flag
        this.mMol.setToRacemate();

    let EZCount = this.decodeBits(bbits);
    for (let i = 0; i < EZCount; i++) {
      let bond = this.decodeBits(bbits);
      if (this.mMol.getBondType(bond) == Molecule.cBondTypeSingle) {
        // BINAP type of axial chirality
        let parity = this.decodeBits(3);
        switch (parity) {
          case Canonizer.cParity1And:
            this.mMol.setBondParity(bond, Molecule.cBondParityEor1, false);
            this.mMol.setBondESR(
              bond,
              Molecule.cESRTypeAnd,
              this.decodeBits(3),
            );
            break;
          case Canonizer.cParity2And:
            this.mMol.setBondParity(bond, Molecule.cBondParityZor2, false);
            this.mMol.setBondESR(
              bond,
              Molecule.cESRTypeAnd,
              this.decodeBits(3),
            );
            break;
          case Canonizer.cParity1Or:
            this.mMol.setBondParity(bond, Molecule.cBondParityEor1, false);
            this.mMol.setBondESR(bond, Molecule.cESRTypeOr, this.decodeBits(3));
            break;
          case Canonizer.cParity2Or:
            this.mMol.setBondParity(bond, Molecule.cBondParityZor2, false);
            this.mMol.setBondESR(bond, Molecule.cESRTypeOr, this.decodeBits(3));
            break;
          default:
            this.mMol.setBondParity(bond, parity, false);
        }
      } else {
        this.mMol.setBondParity(bond, this.decodeBits(2), false); // double bond
      }
    }

    this.mMol.setFragment(this.decodeBits(1) == 1);

    let aromaticSPBond = null;

    let offset = 0;
    while (this.decodeBits(1) == 1) {
      let dataType = offset + this.decodeBits(4);
      let no;
      switch (dataType) {
        case 0: //	datatype 'AtomQFNoMoreNeighbours'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            this.mMol.setAtomQueryFeature(
              atom,
              Molecule.cAtomQFNoMoreNeighbours,
              true,
            );
          }
          break;
        case 1: //	datatype 'isotop'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            let mass = this.decodeBits(8);
            this.mMol.setAtomMass(atom, mass);
          }
          break;
        case 2: //	datatype 'bond defined to be delocalized'
          no = this.decodeBits(bbits);
          for (let i = 0; i < no; i++) {
            let bond = this.decodeBits(bbits);
            // This used to be Molecule.cBondTypeDelocalized, which is redundant to the bond order encoding
            // Then it was wrongly fixed to cBondQFDelocalized, which is part of cBondQFBondTypes and encoded as type 10
            // We can take it out entirely without sacrifycing idcode compatibility
            //					this.mMol.setBondQueryFeature(bond, Molecule.cBondQFDelocalized, true);
            //System.out.println("wrong outdated 'delocalized bond'; idcode:"+new String(mDecodingBytes));
          }
          break;
        case 3: //	datatype 'AtomQFMoreNeighbours'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            this.mMol.setAtomQueryFeature(
              atom,
              Molecule.cAtomQFMoreNeighbours,
              true,
            );
          }
          break;
        case 4: //	datatype 'AtomQFRingState'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            let ringState =
              this.decodeBits(Molecule.cAtomQFRingStateBits) <<
              Molecule.cAtomQFRingStateShift;
            this.mMol.setAtomQueryFeature(atom, ringState, true);
          }
          break;
        case 5: //	datatype 'AtomQFAromState'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            let aromState =
              this.decodeBits(Molecule.cAtomQFAromStateBits) <<
              Molecule.cAtomQFAromStateShift;
            this.mMol.setAtomQueryFeature(atom, aromState, true);
          }
          break;
        case 6: //	datatype 'AtomQFAny'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            this.mMol.setAtomQueryFeature(atom, Molecule.cAtomQFAny, true);
          }
          break;
        case 7: //	datatype 'AtomQFHydrogen'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            let hydrogen =
              this.decodeBits(Molecule.cAtomQFHydrogenBits) <<
              Molecule.cAtomQFHydrogenShift;
            this.mMol.setAtomQueryFeature(atom, hydrogen, true);
          }
          break;
        case 8: //	datatype 'AtomList'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            let atoms = this.decodeBits(4);
            let atomList = new Array(atoms);
            for (let j = 0; j < atoms; j++) {
              let atomicNo = this.decodeBits(8);
              atomList[j] = atomicNo;
            }
            this.mMol.setAtomList(atom, atomList);
          }
          break;
        case 9: //	datatype 'BondQFRingState'
          no = this.decodeBits(bbits);
          for (let i = 0; i < no; i++) {
            let bond = this.decodeBits(bbits);
            let ringState =
              this.decodeBits(Molecule.cBondQFRingStateBits) <<
              Molecule.cBondQFRingStateShift;
            this.mMol.setBondQueryFeature(bond, ringState, true);
          }
          break;
        case 10: //	datatype 'BondQFBondTypes'
          no = this.decodeBits(bbits);
          for (let i = 0; i < no; i++) {
            let bond = this.decodeBits(bbits);
            let bondTypes =
              this.decodeBits(Molecule.cBondQFBondTypesBits) <<
              Molecule.cBondQFBondTypesShift;
            this.mMol.setBondQueryFeature(bond, bondTypes, true);
          }
          break;
        case 11: //	datatype 'AtomQFMatchStereo'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            this.mMol.setAtomQueryFeature(
              atom,
              Molecule.cAtomQFMatchStereo,
              true,
            );
          }
          break;
        case 12: //  datatype 'bond defined to be a bridge from n1 to n2 atoms'
          no = this.decodeBits(bbits);
          for (let i = 0; i < no; i++) {
            let bond = this.decodeBits(bbits);
            let bridgeData =
              this.decodeBits(Molecule.cBondQFBridgeBits) <<
              Molecule.cBondQFBridgeShift;
            this.mMol.setBondQueryFeature(bond, bridgeData, true);
          }
          break;
        case 13: //  datatype 'AtomQFPiElectrons'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            let piElectrons =
              this.decodeBits(Molecule.cAtomQFPiElectronBits) <<
              Molecule.cAtomQFPiElectronShift;
            this.mMol.setAtomQueryFeature(atom, piElectrons, true);
          }
          break;
        case 14: //  datatype 'AtomQFNeighbours'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            let neighbours =
              this.decodeBits(Molecule.cAtomQFNeighbourBits) <<
              Molecule.cAtomQFNeighbourShift;
            this.mMol.setAtomQueryFeature(atom, neighbours, true);
          }
          break;
        case 15: //  datatype 'start second feature set'
          offset = 16;
          break;
        case 16: //  datatype 'AtomQFRingSize'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            let ringSize =
              this.decodeBits(Molecule.cAtomQFRingSizeBits) <<
              Molecule.cAtomQFRingSizeShift;
            this.mMol.setAtomQueryFeature(atom, ringSize, true);
          }
          break;
        case 17: //  datatype 'AtomAbnormalValence'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            this.mMol.setAtomAbnormalValence(atom, this.decodeBits(4));
          }
          break;
        case 18: //  datatype 'AtomCustomLabel'
          no = this.decodeBits(abits);
          let lbits = this.decodeBits(4);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            let count = this.decodeBits(lbits);
            let label = new Uint8Array(count);
            for (let j = 0; j < count; j++) label[j] = this.decodeBits(7);
            this.mMol.setAtomCustomLabel(atom, decoder.decode(label));
          }
          break;
        case 19: //  datatype 'AtomQFCharge'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            let charge =
              this.decodeBits(Molecule.cAtomQFChargeBits) <<
              Molecule.cAtomQFChargeShift;
            this.mMol.setAtomQueryFeature(atom, charge, true);
          }
          break;
        case 20: //  datatype 'BondQFRingSize'
          no = this.decodeBits(bbits);
          for (let i = 0; i < no; i++) {
            let bond = this.decodeBits(bbits);
            let ringSize =
              this.decodeBits(Molecule.cBondQFRingSizeBits) <<
              Molecule.cBondQFRingSizeShift;
            this.mMol.setBondQueryFeature(bond, ringSize, true);
          }
          break;
        case 21: //  datatype 'AtomRadicalState'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            this.mMol.setAtomRadical(
              atom,
              this.decodeBits(2) << Molecule.cAtomRadicalStateShift,
            );
          }
          break;
        case 22: //	datatype 'flat nitrogen'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            this.mMol.setAtomQueryFeature(
              atom,
              Molecule.cAtomQFFlatNitrogen,
              true,
            );
          }
          break;
        case 23: //	datatype 'BondQFMatchStereo'
          no = this.decodeBits(bbits);
          for (let i = 0; i < no; i++) {
            let bond = this.decodeBits(bbits);
            this.mMol.setBondQueryFeature(
              bond,
              Molecule.cBondQFMatchStereo,
              true,
            );
          }
          break;
        case 24: //	datatype 'cBondQFAromState'
          no = this.decodeBits(bbits);
          for (let i = 0; i < no; i++) {
            let bond = this.decodeBits(bbits);
            let aromState =
              this.decodeBits(Molecule.cBondQFAromStateBits) <<
              Molecule.cBondQFAromStateShift;
            this.mMol.setBondQueryFeature(bond, aromState, true);
          }
          break;
        case 25: //	datatype 'atom selection'
          for (let i = 0; i < allAtoms; i++)
            if (this.decodeBits(1) == 1) this.mMol.setAtomSelection(i, true);
          break;
        case 26: //	datatype 'delocalized high order bond'
          no = this.decodeBits(bbits);
          aromaticSPBond = new Int32Array(no);
          for (let i = 0; i < no; i++)
            aromaticSPBond[i] = this.decodeBits(bbits);
          break;
        case 27: //	datatype 'part of an exclude group'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            this.mMol.setAtomQueryFeature(
              atom,
              Molecule.cAtomQFExcludeGroup,
              true,
            );
          }
          break;
        case 28: //  datatype 'coordinate bond'
          no = this.decodeBits(bbits);
          for (let i = 0; i < no; i++)
            this.mMol.setBondType(
              this.decodeBits(bbits),
              Molecule.cBondTypeMetalLigand,
            );
          break;
        case 29: //	datatype 'reaction parity hint'
          no = this.decodeBits(abits);
          for (let i = 0; i < no; i++) {
            let atom = this.decodeBits(abits);
            let hint =
              this.decodeBits(Molecule.cAtomQFRxnParityBits) <<
              Molecule.cAtomQFRxnParityShift;
            this.mMol.setAtomQueryFeature(atom, hint, true);
          }
          break;
      }
    }

    new AromaticityResolver(this.mMol).locateDelocalizedDoubleBonds(
      isDelocalizedBond,
    );

    if (aromaticSPBond !== null)
      for (const bond of aromaticSPBond) {
        this.mMol.setBondType(
          bond,
          this.mMol.getBondType(bond) == Molecule.cBondTypeDouble
            ? Molecule.cBondTypeTriple
            : Molecule.cBondTypeDouble,
        );
      }

    if (
      coordinates == null &&
      !this.mNeglectSpaceDelimitedCoordinates &&
      idcode.length > this.mIDCodeBufferIndex + 1 &&
      (idcode[this.mIDCodeBufferIndex + 1] == ' ' ||
        idcode[this.mIDCodeBufferIndex + 1] == '\t')
    ) {
      coordinates = idcode;
      coordsStart = this.mIDCodeBufferIndex + 2;
    }

    if (coordinates != null) {
      try {
        if (
          coordinates[coordsStart] == '!' ||
          coordinates[coordsStart] == '#'
        ) {
          // new coordinate format
          this.decodeBitsStart(coordinates, coordsStart + 1);
          coordsAre3D = this.decodeBits(1) == 1;
          coordsAreAbsolute = this.decodeBits(1) == 1;
          let resolutionBits = 2 * this.decodeBits(4);
          let binCount = 1 << resolutionBits;

          let factor;
          let from = 0;
          let bond = 0;
          for (let atom = 1; atom < allAtoms; atom++) {
            if (bond < allBonds && this.mMol.getBondAtom(1, bond) == atom) {
              from = this.mMol.getBondAtom(0, bond++);
              factor = 1.0;
            } else {
              from = 0;
              factor = 8.0;
            }
            this.mMol.setAtomX(
              atom,
              this.mMol.getAtomX(from) +
                factor * (this.decodeBits(resolutionBits) - binCount / 2),
            );
            this.mMol.setAtomY(
              atom,
              this.mMol.getAtomY(from) +
                factor * (this.decodeBits(resolutionBits) - binCount / 2),
            );
            if (coordsAre3D)
              this.mMol.setAtomZ(
                atom,
                this.mMol.getAtomZ(from) +
                  factor * (this.decodeBits(resolutionBits) - binCount / 2),
              );
          }

          if (coordinates[coordsStart] == '#') {
            // we have 3D-coordinates that include implicit hydrogen coordinates
            let hydrogenCount = 0;

            // we need to cache hCount, because otherwise getImplicitHydrogens() would create helper arrays with every call
            let hCount = new Int32Array(allAtoms);
            for (let atom = 0; atom < allAtoms; atom++)
              hydrogenCount += hCount[atom] =
                this.mMol.getImplicitHydrogens(atom);

            for (let atom = 0; atom < allAtoms; atom++) {
              for (let i = 0; i < hCount[atom]; i++) {
                let hydrogen = this.mMol.addAtom(1);
                this.mMol.addBond(atom, hydrogen, Molecule.cBondTypeSingle);

                this.mMol.setAtomX(
                  hydrogen,
                  this.mMol.getAtomX(atom) +
                    (this.decodeBits(resolutionBits) - binCount / 2),
                );
                this.mMol.setAtomY(
                  hydrogen,
                  this.mMol.getAtomY(atom) +
                    (this.decodeBits(resolutionBits) - binCount / 2),
                );
                if (coordsAre3D)
                  this.mMol.setAtomZ(
                    hydrogen,
                    this.mMol.getAtomZ(atom) +
                      (this.decodeBits(resolutionBits) - binCount / 2),
                  );
              }
            }

            allAtoms += hydrogenCount;
            allBonds += hydrogenCount;
          }

          let avblDefault = coordsAre3D
            ? 1.5
            : Molecule.getDefaultAverageBondLength();
          let avbl = this.mMol.getAverageBondLength(
            allAtoms,
            allBonds,
            avblDefault,
          );

          if (coordsAreAbsolute) {
            targetAVBL = this.decodeAVBL(
              this.decodeBits(resolutionBits),
              binCount,
            );
            xOffset =
              targetAVBL *
              this.decodeShift(this.decodeBits(resolutionBits), binCount);
            yOffset =
              targetAVBL *
              this.decodeShift(this.decodeBits(resolutionBits), binCount);
            if (coordsAre3D)
              zOffset =
                targetAVBL *
                this.decodeShift(this.decodeBits(resolutionBits), binCount);

            factor = targetAVBL / avbl;
            for (let atom = 0; atom < allAtoms; atom++) {
              this.mMol.setAtomX(
                atom,
                xOffset + factor * this.mMol.getAtomX(atom),
              );
              this.mMol.setAtomY(
                atom,
                yOffset + factor * this.mMol.getAtomY(atom),
              );
              if (coordsAre3D)
                this.mMol.setAtomZ(
                  atom,
                  zOffset + factor * this.mMol.getAtomZ(atom),
                );
            }
          } else {
            // with new format 2D and 3D coordinates are scaled to average bond lengths of 1.5 Angstrom
            targetAVBL = 1.5;
            factor = targetAVBL / avbl;
            for (let atom = 0; atom < allAtoms; atom++) {
              this.mMol.setAtomX(atom, factor * this.mMol.getAtomX(atom));
              this.mMol.setAtomY(atom, factor * this.mMol.getAtomY(atom));
              if (coordsAre3D)
                this.mMol.setAtomZ(atom, factor * this.mMol.getAtomZ(atom));
            }
          }
        } else {
          // old coordinate format
          if (coordsAre3D && !coordsAreAbsolute && targetAVBL == 0.0)
            // if no scaling factor is given, then scale to mean bond length = 1.5
            targetAVBL = 1.5;

          if (targetAVBL != 0.0 && this.mMol.getAllBonds() != 0) {
            let avbl = 0.0;
            for (let bond = 0; bond < this.mMol.getAllBonds(); bond++) {
              let dx =
                this.mMol.getAtomX(this.mMol.getBondAtom(0, bond)) -
                this.mMol.getAtomX(this.mMol.getBondAtom(1, bond));
              let dy =
                this.mMol.getAtomY(this.mMol.getBondAtom(0, bond)) -
                this.mMol.getAtomY(this.mMol.getBondAtom(1, bond));
              let dz = coordsAre3D
                ? this.mMol.getAtomZ(this.mMol.getBondAtom(0, bond)) -
                  this.mMol.getAtomZ(this.mMol.getBondAtom(1, bond))
                : 0.0;
              avbl += Math.sqrt(dx * dx + dy * dy + dz * dz);
            }
            avbl /= this.mMol.getAllBonds();
            let f = targetAVBL / avbl;
            for (let atom = 0; atom < this.mMol.getAllAtoms(); atom++) {
              this.mMol.setAtomX(atom, this.mMol.getAtomX(atom) * f + xOffset);
              this.mMol.setAtomY(atom, this.mMol.getAtomY(atom) * f + yOffset);
              if (coordsAre3D)
                this.mMol.setAtomZ(
                  atom,
                  this.mMol.getAtomZ(atom) * f + zOffset,
                );
            }
          }
        }
      } catch (e) {
        console.error(e);
        console.error(
          'Faulty id-coordinates:' +
            e.toString() +
            ' ' +
            decoder.decode(idcode) +
            ' ' +
            decoder.decode(coordinates),
        );
        coordinates = null;
        coordsAre3D = false;
      }
    }

    let coords2DAvailable = coordinates != null && !coordsAre3D;

    // If we have or create 2D-coordinates, then we need to set all double bonds to a cross bond, which
    // - have distinguishable substituents on both ends, i.e. is a stereo double bond
    // - are not in a small ring
    // Here we don't know, whether a double bond without E/Z parity is a stereo bond with unknown
    // configuration or not a stereo bond. Therefore we need to set a flag, that causes the Canonizer
    // during the next stereo recognition with atom coordinates to assign an unknown configuration rather
    // than E or Z based on created or given coordinates.
    // In a next step these double bonds are converted into cross bonds by
    if (coords2DAvailable || this.ensure2DCoordinates()) {
      this.mMol.ensureHelperArrays(Molecule.cHelperRings);
      for (let bond = 0; bond < this.mMol.getBonds(); bond++)
        if (
          this.mMol.getBondOrder(bond) == 2 &&
          !this.mMol.isSmallRingBond(bond) &&
          this.mMol.getBondParity(bond) == Molecule.cBondParityNone
        )
          this.mMol.setBondParityUnknownOrNone(bond);
    }

    if (!coords2DAvailable && this.ensure2DCoordinates()) {
      this.mMol.setParitiesValid(0);
      try {
        this.inventCoordinates(this.mMol);
        coords2DAvailable = true;
      } catch (e) {
        console.error(e);
        console.error(
          '2D-coordinate creation failed:' +
            e.toString() +
            ' ' +
            decoder.decode(idcode),
        );
      }
    }

    if (coords2DAvailable) {
      this.mMol.setStereoBondsFromParity();
      this.mMol.setUnknownParitiesToExplicitlyUnknown();
    } else if (!coordsAre3D) {
      this.mMol.setParitiesValid(0);
    }
  }

  decodeBitsStart(bytes, offset) {
    this.mIDCodeBitsAvail = 6;
    this.mIDCodeBufferIndex = offset;
    this.mDecodingBytes = bytes;
    this.mIDCodeTempData = (bytes[this.mIDCodeBufferIndex] & 0x3f) << 11;
  }

  decodeBits(bits) {
    const allBits = bits;
    let data = 0;
    while (bits !== 0) {
      if (this.mIDCodeBitsAvail === 0) {
        this.mIDCodeTempData =
          (this.mDecodingBytes[++this.mIDCodeBufferIndex] & 0x3f) << 11;
        this.mIDCodeBitsAvail = 6;
      }
      data |= (0x00010000 & this.mIDCodeTempData) >> (16 - allBits + bits);

      this.mIDCodeTempData <<= 1;
      bits--;
      this.mIDCodeBitsAvail--;
    }
    return data;
  }
}

import Molecule from './Molecule.mjs';

export default class ExtendedMolecule extends Molecule {
  setParitiesValid() {
    this.mValidHelperArrays |=
      Molecule.cHelperBitsStereo &
      (Molecule.cHelperBitParities | helperStereoBits);
  }
}

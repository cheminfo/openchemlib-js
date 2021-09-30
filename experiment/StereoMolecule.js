import ExtendedMolecule from './ExtendedMolecule.js';

export default class StereoMolecule extends ExtendedMolecule {
  setParitiesValid(helperStereoBits) {
    this.mValidHelperArrays |=
      ExtendedMolecule.cHelperBitsStereo &
      (ExtendedMolecule.cHelperBitParities | helperStereoBits);
  }
}

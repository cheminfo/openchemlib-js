import OCL from '../lib/index.js';

let molecule = OCL.Molecule.fromSmiles('C[C@H](Cl)CC');
molecule = OCL.Molecule.fromSmiles('CC=C(O)CC');

console.log(molecule.getIDCode());
console.log(OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.NORMAL));
console.log(OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.NOSTEREO));
console.log(OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.BACKBONE));
console.log(OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.TAUTOMER));
console.log(
  OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.NOSTEREO_TAUTOMER),
);

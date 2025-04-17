import { CanonizerUtil, Molecule } from '../lib/index.js';

let molecule = Molecule.fromSmiles('C[C@H](Cl)CC');
molecule = Molecule.fromSmiles('CC=C(O)CC');

console.log(molecule.getIDCode());
console.log(CanonizerUtil.getIDCode(molecule, CanonizerUtil.NORMAL));
console.log(CanonizerUtil.getIDCode(molecule, CanonizerUtil.NOSTEREO));
console.log(CanonizerUtil.getIDCode(molecule, CanonizerUtil.BACKBONE));
console.log(CanonizerUtil.getIDCode(molecule, CanonizerUtil.TAUTOMER));
console.log(CanonizerUtil.getIDCode(molecule, CanonizerUtil.NOSTEREO_TAUTOMER));

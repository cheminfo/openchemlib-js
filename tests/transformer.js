import { Molecule, Transformer } from '../lib/index.js';

const reagent = Molecule.fromSmiles('CCO');

reagent.setFragment(true);
for (let i = 0; i < 3; i++) {
  reagent.setAtomMapNo(i, i);
}
const product = Molecule.fromSmiles('C=C.O');
product.setFragment(true);
for (let i = 0; i < 3; i++) {
  product.setAtomMapNo(i, i);
}

const transformer = new Transformer(reagent, product, 'elimination');

let molecule = Molecule.fromSmiles('OCCCCC');
molecule.setFragment(true);
const nbFound = transformer.setMolecule(molecule);
for (let i = 0; i < nbFound; i++) {
  const copy = molecule.getCompactCopy();

  transformer.applyTransformation(copy, i);
  console.log(copy.getMolecularFormula().formula);
  console.log(copy.toIsomericSmiles());
}

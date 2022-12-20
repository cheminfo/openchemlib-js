'use strict';

const OCL = require('../core');

const Molecule = OCL.Molecule;
const Transformer = OCL.Transformer;

test('Transformer', () => {
  let reagent = Molecule.fromSmiles('[C]');
  let product = Molecule.fromSmiles('[O]');
  const transformer = new Transformer(reagent, product, 'ether');

  let molecule = Molecule.fromSmiles('CCCC');
  const result = transformer.setMolecule(molecule);
  console.log(result);
});

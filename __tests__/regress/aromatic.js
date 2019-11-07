'use strict';

const OCL = require('../..');

test('parse wrong SMILES then correct one should throw only once', () => {
  OCL.Molecule.fromSmiles('c1ccccc1');
  expect(function() {
    OCL.Molecule.fromSmiles('c1ccccc');
  }).toThrow(/SmilesParser: dangling ring closure/);
  OCL.Molecule.fromSmiles('c1ccccc1');
});

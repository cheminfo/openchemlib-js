import { expect, test } from 'vitest';

import { Molecule } from '../../lib/index.js';

test('parse wrong SMILES then correct one should throw only once', () => {
  Molecule.fromSmiles('c1ccccc1');
  expect(() => {
    Molecule.fromSmiles('c1ccccc');
  }).toThrow(/SmilesParser: dangling ring closure/);
  Molecule.fromSmiles('c1ccccc1');
});

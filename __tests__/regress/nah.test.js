import { expect, test } from 'vitest';

import { Molecule } from '../..';

const molfile = `
  -ISIS-  09030813062D

  2  1  0  0  0  0  0  0  0  0999 V2000
    4.8042   -2.8500    0.0000 Na  0  0  0  0  0  0  0  0  0  0  0  0
    5.6292   -2.8500    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0
  2  1  1  0  0  0  0
M  END
`;

test('parsing molfile for NaH should keep the hydrogen', () => {
  const mol = Molecule.fromMolfile(molfile);
  expect(mol.getIDCode()).toBe('eFACPhBL@');
});

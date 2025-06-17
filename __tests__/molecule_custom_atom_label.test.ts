import { expect, test } from 'vitest';

import { Molecule } from '../lib/index.js';

test('Custom atom labels should be supported in molfiles', () => {
  const mol = Molecule.fromSmiles('COCCON');
  mol.setAtomCustomLabel(0, 'R3');
  mol.setAtomCustomLabel(1, "]1'");
  const molfile = mol.toMolfileV3();
  expect(molfile).toMatchInlineSnapshot(`
    "
    Actelion Java MolfileCreator 2.0

      0  0  0  0  0  0              0 V3000
    M  V30 BEGIN CTAB
    M  V30 COUNTS 6 5 0 0 0
    M  V30 BEGIN ATOM
    M  V30 1 C 4.3301 0 0 0
    M  V30 2 O 3.4641 -0.5 0 0
    M  V30 3 C 2.598 0 0 0
    M  V30 4 C 1.732 -0.5 0 0
    M  V30 5 O 0.866 0 0 0
    M  V30 6 N 0 -0.5 0 0
    M  V30 END ATOM
    M  V30 BEGIN BOND
    M  V30 1 1 1 2
    M  V30 2 1 2 3
    M  V30 3 1 3 4
    M  V30 4 1 4 5
    M  V30 5 1 5 6
    M  V30 END BOND
    M  V30 BEGIN SGROUP
    M  V30 1 DAT 1 ATOMS=(1 1) FIELDNAME="NOSEARCH_OCL_CUSTOM_LABEL" -
    M  V30 FIELDDISP="    4.3301    0.0000    DA    ALL  1       5" FIELDDATA="R3"
    M  V30 2 DAT 2 ATOMS=(1 2) FIELDNAME="NOSEARCH_OCL_CUSTOM_LABEL" -
    M  V30 FIELDDISP="    3.4641    0.5000    DA    ALL  1       5" FIELDDATA="]1'"
    M  V30 END SGROUP
    M  V30 END CTAB
    M  END
    "
  `);
  const mol2 = Molecule.fromMolfile(molfile);
  expect(mol2.getAtomCustomLabel(0)).toBe('R3');
  expect(mol2.getAtomCustomLabel(1)).toBe("]1'");
  expect(mol2.getAtomCustomLabel(2)).toBe(null);
});

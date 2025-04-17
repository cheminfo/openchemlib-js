import { beforeAll, describe, expect, it } from 'vitest';

import {
  ConformerGenerator,
  ForceFieldMMFF94,
  Molecule,
  Resources,
} from '../lib/index.js';

beforeAll(() => {
  Resources.registerFromNodejs();
});

describe('ForceFieldMMFF94', () => {
  it.each(['MMFF94', 'MMFF94s', 'MMFF94s+'] as const)(
    'should generate force field (%s)',
    (tablename) => {
      const mol = Molecule.fromSmiles('COCCON');
      const gen = new ConformerGenerator(1);
      gen.getOneConformerAsMolecule(mol);
      const molfileBefore = mol.toMolfile();

      const ff = new ForceFieldMMFF94(mol, tablename);
      ff.minimise();
      const molfileAfter = mol.toMolfile();
      expect(molfileAfter).toMatchSnapshot();
      expect(molfileAfter).not.toBe(molfileBefore);
    },
  );
});

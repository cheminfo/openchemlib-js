import { describe, expect, it } from 'vitest';

import { ConformerGenerator, ForceFieldMMFF94, Molecule } from '../core';

describe('ForceFieldMMFF94', () => {
  it('should generate force field', () => {
    const mol = Molecule.fromSmiles('COCCON');
    const gen = new ConformerGenerator(1);
    gen.getOneConformerAsMolecule(mol);
    const molfileBefore = mol.toMolfile();

    const ff = new ForceFieldMMFF94(mol, 'MMFF94');
    ff.minimise();
    const molfileAfter = mol.toMolfile();
    expect(molfileAfter).toMatchSnapshot();
    expect(molfileAfter).not.toBe(molfileBefore);
  });
});

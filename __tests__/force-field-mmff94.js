'use strict';

const OCL = require('../core');

const { Molecule, ForceFieldMMFF94, ConformerGenerator } = OCL;

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

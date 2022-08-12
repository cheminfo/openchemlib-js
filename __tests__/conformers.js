'use strict';

const OCL = require('../core');

const { Molecule, ConformerGenerator } = OCL;

describe('ConformerGenerator', () => {
  it('should return one conformer', () => {
    const mol = Molecule.fromSmiles('COCCON');

    const gen = new ConformerGenerator(1);
    expect(gen.getConformerCount()).toBe(0);

    expect(mol.getAtomZ(0)).toBe(0);
    const result = gen.getOneConformerAsMolecule(mol);
    expect(gen.getConformerCount()).toBe(1);
    expect(result).toBe(mol);
    expect(result.getAtomZ(0)).not.toBe(0);
  });

  it('should generate conformers', () => {
    const mol = Molecule.fromSmiles('COCCON');

    const gen = new ConformerGenerator(1);
    expect(gen.getPotentialConformerCount()).toBe(1);
    expect(gen.getConformerCount()).toBe(0);
    expect(gen.getPreviousConformerContribution()).toBe(1);
    gen.initializeConformers(mol);
    expect(gen.getPotentialConformerCount()).toBeGreaterThan(1);

    const conf1 = gen.getNextConformerAsMolecule();
    expect(gen.getConformerCount()).toBe(1);
    expect(gen.getPreviousConformerContribution()).toBeCloseTo(0.17, 2);
    const conf1Molfile = conf1.toMolfile();
    expect(conf1Molfile).toMatchSnapshot();

    const conf2 = gen.getNextConformerAsMolecule();
    expect(gen.getConformerCount()).toBe(2);
    const conf2Molfile = conf2.toMolfile();
    expect(conf2Molfile).toMatchSnapshot();

    expect(conf2).not.toBe(conf1);
    expect(conf2Molfile).not.toBe(conf1Molfile);
  });

  it('should use and return the passed molecule', () => {
    const mol = Molecule.fromSmiles('COCCON');

    const gen = new ConformerGenerator(1);
    gen.initializeConformers(mol);

    expect(mol.getAtomZ(0)).toBe(0);

    const conf = gen.getNextConformerAsMolecule(mol);
    expect(conf).toBe(mol);
    expect(mol.getAtomZ(0)).not.toBe(0);
  });

  it('should molecules() return an iterator', () => {
    const mol = Molecule.fromSmiles('COCCON');

    const gen = new ConformerGenerator(1);
    gen.initializeConformers(mol);

    const allConformers = [...gen.molecules()];
    expect(allConformers).toHaveLength(26);

    // All molecules should be different
    expect(new Set(allConformers).size).toBe(26);

    expect(allConformers[0].getAtomZ(0)).not.toBe(allConformers[1].getAtomZ(1));

    expect(allConformers.every((conf) => conf instanceof Molecule)).toBe(true);
  });
});

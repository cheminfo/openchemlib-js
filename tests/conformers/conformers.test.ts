import { assert, beforeAll, describe, expect, it } from 'vitest';

import { ConformerGenerator, Molecule, Resources } from '#lib';

beforeAll(() => {
  Resources.registerFromNodejs();
});

describe('ConformerGenerator', () => {
  it('should return one conformer', () => {
    const mol = Molecule.fromSmiles('COCCON');

    const gen = new ConformerGenerator(1);

    expect(gen.getConformerCount()).toBe(0);

    expect(mol.getAtomZ(0)).toBe(0);

    const result = gen.getOneConformerAsMolecule(mol);

    expect(gen.getConformerCount()).toBe(1);
    expect(result).toBe(mol);
    expect(mol.getAtomZ(0)).not.toBe(0);
  });

  it('should generate conformers', () => {
    const mol = Molecule.fromSmiles('COCCON');

    const gen = new ConformerGenerator(1);

    expect(gen.getPotentialConformerCount()).toBe(1);
    expect(gen.getConformerCount()).toBe(0);

    gen.initializeConformers(mol);

    expect(gen.getPotentialConformerCount()).toBeGreaterThan(1);

    const conformer1 = gen.getNextConformerAsMolecule();

    expect(gen.getConformerCount()).toBe(1);

    assert(conformer1);
    const config1Molfile = conformer1.toMolfile();

    expect(config1Molfile).toMatchSnapshot();

    const conformer2 = gen.getNextConformerAsMolecule();

    expect(gen.getConformerCount()).toBe(2);

    assert(conformer2);
    const conformer2Molfile = conformer2.toMolfile();

    expect(conformer2Molfile).toMatchSnapshot();

    expect(conformer2).not.toBe(conformer1);
    expect(conformer2Molfile).not.toBe(config1Molfile);
  });

  it('should use and return the passed molecule', () => {
    const mol = Molecule.fromSmiles('COCCON');

    const gen = new ConformerGenerator(1);
    gen.initializeConformers(mol);

    expect(mol.getAtomZ(0)).toBe(0);

    const config = gen.getNextConformerAsMolecule(mol);

    expect(config).toBe(mol);
    expect(mol.getAtomZ(0)).not.toBe(0);
  });

  it('should molecules() return an iterator', () => {
    const mol = Molecule.fromSmiles('COCCON');

    const gen = new ConformerGenerator(1);
    gen.initializeConformers(mol);

    const allConformers = [...gen.molecules()];

    expect(allConformers).toHaveLength(24);

    // All molecules should be different
    expect(new Set(allConformers).size).toBe(24);

    expect(allConformers[0].getAtomZ(0)).not.toBe(allConformers[1].getAtomZ(1));

    expect(allConformers.every((config) => config instanceof Molecule)).toBe(
      true,
    );
  });
});

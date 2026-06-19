import { describe, expect, it } from 'vitest';

import { MCS, Molecule } from '#lib';

describe('MCS', () => {
  it('finds the common substructure of two molecules', () => {
    const mcs = new MCS();
    mcs.set(
      Molecule.fromSmiles('c1ccccc1CCO'),
      Molecule.fromSmiles('c1ccccc1CCN'),
    );

    const common = mcs.getMCS();

    expect(common).not.toBeNull();
    // The shared scaffold is the ethylbenzene part (8 atoms, no oxygen/nitrogen).
    expect(common?.getAllAtoms()).toBe(8);

    const score = mcs.getScore();

    expect(score).toBeGreaterThan(0);
    expect(score).toBeLessThanOrEqual(1);
  });

  it('returns the full molecule when one is a substructure of the other', () => {
    const mcs = new MCS();
    mcs.set(
      Molecule.fromSmiles('c1ccccc1CCO'),
      Molecule.fromSmiles('c1ccccc1'),
    );

    const common = mcs.getMCS();

    expect(common?.getAllAtoms()).toBe(6);
    // Score is the MCS bond count (benzene: 6) divided by the larger molecule's
    // bond count (phenethyl alcohol: 9).
    expect(mcs.getScore()).toBeCloseTo(6 / 9);
  });

  it('lists all common substructures', () => {
    // The two molecules differ only at the linker atom (O vs N). On each side of
    // that atom sits a distinct ring system, so two separate common substructures
    // are found: the benzene side and the pyridine side.
    const mcs = new MCS();
    mcs.set(
      Molecule.fromSmiles('c1ccccc1CCOCc1ccncc1'),
      Molecule.fromSmiles('c1ccccc1CCNCc1ccncc1'),
    );

    const all = mcs.getAllCommonSubstructures();

    expect(all).not.toBeNull();

    const idCodes = all?.map((molecule) => molecule.getIDCode()).toSorted();

    expect(idCodes).toStrictEqual(
      [
        'daD@@DjUZxHH@B', // ethylbenzene fragment (8 atoms)
        'gOx@@eJyh@PA@', // methylpyridine fragment (7 atoms)
      ].toSorted(),
    );
  });
});

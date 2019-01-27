'use strict';

const Molecule = require('../minimal').Molecule;

test('no ring', () => {
  const mol = Molecule.fromSmiles('CCOCC');
  const coll = mol.getRingSet();
  expect(coll.getSize()).toBe(0);
});

test('simple ring', () => {
  const mol = Molecule.fromSmiles('COC1CCCCC1OC');
  const coll = mol.getRingSet();
  expect(coll.getSize()).toBe(1);
  expect(coll.getRingAtoms(0).slice()).toStrictEqual([2, 3, 4, 5, 6, 7]);
  expect(coll.getRingBonds(0).slice()).toStrictEqual([2, 3, 4, 5, 6, 7]);
  expect(coll.getRingSize(0)).toBe(6);
  expect(coll.isAromatic(0)).toBe(false);
  expect(coll.isDelocalized(0)).toBe(false);
  expect(coll.isAtomMember(0, 0)).toBe(false);
  expect(coll.isAtomMember(0, 4)).toBe(true);
  expect(coll.getSharedRing(0, 1)).toBe(-1);
  expect(coll.getSharedRing(3, 4)).toBe(0);
});

test('aromatic ring', () => {
  const mol = Molecule.fromSmiles('COc1ccccc1OC');
  const coll = mol.getRingSet();
  expect(coll.getSize()).toBe(1);
  expect(coll.getRingAtoms(0).slice()).toStrictEqual([2, 3, 4, 5, 6, 7]);
  expect(coll.getRingBonds(0).slice()).toStrictEqual([2, 3, 4, 5, 6, 7]);
  expect(coll.getRingSize(0)).toBe(6);
  expect(coll.isAromatic(0)).toBe(true);
  expect(coll.isDelocalized(0)).toBe(true);
  expect(coll.isAtomMember(0, 0)).toBe(false);
  expect(coll.isAtomMember(0, 4)).toBe(true);
  expect(coll.getSharedRing(0, 1)).toBe(-1);
  expect(coll.getSharedRing(3, 4)).toBe(0);
});

test('multiple rings', () => {
  // Source: https://en.wikipedia.org/wiki/Heroin
  const mol = Molecule.fromSmiles(
    'CC(OC1=C(O[C@@H]2[C@]34CCN(C)[C@@H]([C@@H]4C=C[C@@H]2OC(C)=O)C5)C3=C5C=C1)=O'
  );
  const coll = mol.getRingSet();
  expect(coll.getSize()).toBe(5);
  const sizes = [6, 5, 6, 6, 6];
  const aromatic = [true, false, false, false, false];
  const delocalized = [true, false, false, false, false];
  for (let i = 0; i < 5; i++) {
    expect(coll.getRingSize(i)).toBe(sizes[i]);
    expect(coll.isAromatic(i)).toBe(aromatic[i]);
    expect(coll.isDelocalized(i)).toBe(delocalized[i]);
  }
  expect(coll.isAtomMember(0, 4)).toBe(true);
  expect(coll.isAtomMember(1, 4)).toBe(true);
  expect(coll.isAtomMember(0, 3)).toBe(true);
  expect(coll.isAtomMember(1, 3)).toBe(false);
  expect(coll.getSharedRing(23, 25)).toBe(0);
  expect(coll.getSharedRing(23, 24)).toBe(1);
});

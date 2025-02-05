import { expect, test } from 'vitest';

import { Molecule } from '../full.pretty.js';

// idCode debug view: https://www.cheminfo.org/?viewURL=https%3A%2F%2Fcouch.cheminfo.org%2Fcheminfo-public%2F1cc9e892242664b1d5a37312bda159ef%2Fview.json&loadversion=true&fillsearch=Display+OCLcode+oclID
test('copyMoleculeByAtoms and keep aromaticity', () => {
  const molecule = Molecule.fromSmiles('c1ccccc1');
  const fragment = new Molecule(0, 0);

  expect(molecule.getBondOrder(0)).toBe(2);
  expect(molecule.isDelocalizedBond(0)).toBe(true);
  expect(molecule.getBondOrder(1)).toBe(1);
  expect(molecule.isDelocalizedBond(1)).toBe(true);

  const atomMask = [true, true, true, false, false, false];
  molecule.copyMoleculeByAtoms(fragment, atomMask, true, null);
  fragment.ensureHelperArrays(Molecule.cHelperBitRings); // would crash without Helper

  expect(fragment.getAllAtoms()).toBe(3);
  expect(fragment.getAllBonds()).toBe(2);

  expect(fragment.getBondOrder(0)).toBe(2);
  expect(fragment.isDelocalizedBond(0)).toBe(true);
  expect(fragment.getBondOrder(1)).toBe(1);
  expect(fragment.isDelocalizedBond(1)).toBe(true);
});

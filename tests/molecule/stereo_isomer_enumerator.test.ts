import { expect, test } from 'vitest';

import { Molecule, StereoIsomerEnumerator } from '#lib';

test('every parity permutation of two undefined stereo centres', () => {
  const diol = Molecule.fromSmiles('CC(O)C(O)C');
  const enumerator = new StereoIsomerEnumerator(diol);
  const isomers = isomersOf(enumerator);

  expect(enumerator.isSkippingEnantiomers()).toBe(false);
  expect(enumerator.getStereoIsomerCount()).toBe(4);
  // The two permutations of the meso form are one compound.
  expect(new Set(isomers.map((isomer) => isomer.getIDCode())).size).toBe(3);
  expect(diol.toIsomericSmiles()).toBe('CC(C(C)O)O');
});

test('one enantiomer of each pair', () => {
  const enumerator = new StereoIsomerEnumerator(
    Molecule.fromSmiles('CC(O)C(O)C'),
    { skipEnantiomers: true },
  );

  expect(enumerator.isSkippingEnantiomers()).toBe(true);
  expect(
    isomersOf(enumerator).map((isomer) => isomer.toIsomericSmiles()),
  ).toStrictEqual(['C[C@H]([C@H](C)O)O', 'C[C@H]([C@@H](C)O)O']);
});

test('both configurations of an undefined double bond', () => {
  const enumerator = new StereoIsomerEnumerator(Molecule.fromSmiles('CC=CC'));

  expect(
    isomersOf(enumerator).map((isomer) => isomer.toIsomericSmiles()),
  ).toStrictEqual(['C/C=C/C', String.raw`C/C=C\C`]);
});

test('a molecule is checked against the stereoisomer it should be', () => {
  const enumerator = new StereoIsomerEnumerator(Molecule.fromSmiles('CC=CC'));
  const first = enumerator.getStereoIsomer(0);

  expect(enumerator.isCorrectStereoIsomer(first, 0)).toBe(true);
  expect(enumerator.isCorrectStereoIsomer(first, 1)).toBe(false);
});

function isomersOf(enumerator: StereoIsomerEnumerator): Molecule[] {
  const isomers: Molecule[] = [];
  for (let index = 0; index < enumerator.getStereoIsomerCount(); index++) {
    isomers.push(enumerator.getStereoIsomer(index));
  }
  return isomers;
}

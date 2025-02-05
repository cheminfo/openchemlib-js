import { expect, test } from 'vitest';

import { Molecule } from '../minimal';

test('atom query features more than 2 neighbours', () => {
  const molecule = Molecule.fromIDCode('eF@Hp[qp');
  const firstQueryFeatures = molecule.getAtomQueryFeaturesObject(0);
  expect(firstQueryFeatures.not0Neighbours).toBe(true);
  expect(firstQueryFeatures.not1Neighbour).toBe(true);
  expect(firstQueryFeatures.not2Neighbours).toBe(true);
  expect(firstQueryFeatures.not3Neighbours).toBe(false);
  let secondQueryFeatures = molecule.getAtomQueryFeaturesObject(1);
  expect(secondQueryFeatures.not0Neighbours).toBe(false);
  expect(secondQueryFeatures.not1Neighbour).toBe(false);
  expect(secondQueryFeatures.not2Neighbours).toBe(false);
  expect(secondQueryFeatures.not3Neighbours).toBe(false);
  molecule.setAtomQueryFeature(1, Molecule.cAtomQFNot0Neighbours, true);
  molecule.setAtomQueryFeature(1, Molecule.cAtomQFNot2Neighbours, true);
  secondQueryFeatures = molecule.getAtomQueryFeaturesObject(1);
  expect(secondQueryFeatures.not0Neighbours).toBe(true);
  expect(secondQueryFeatures.not1Neighbour).toBe(false);
  expect(secondQueryFeatures.not2Neighbours).toBe(true);
  expect(secondQueryFeatures.not3Neighbours).toBe(false);
  molecule.setAtomQueryFeature(1, Molecule.cAtomQFNot0Neighbours, false);
  molecule.setAtomQueryFeature(1, Molecule.cAtomQFNot2Neighbours, false);
  molecule.setAtomQueryFeature(1, Molecule.cAtomQFNot1Neighbour, true);
  molecule.setAtomQueryFeature(1, Molecule.cAtomQFNot3Neighbours, true);
  secondQueryFeatures = molecule.getAtomQueryFeaturesObject(1);
  expect(secondQueryFeatures.not0Neighbours).toBe(false);
  expect(secondQueryFeatures.not1Neighbour).toBe(true);
  expect(secondQueryFeatures.not2Neighbours).toBe(false);
  expect(secondQueryFeatures.not3Neighbours).toBe(true);
});

test('atom query features exactly 2 hydrogens', () => {
  const molecule = Molecule.fromIDCode('eF@Hp_Qh');
  const firstAtomQueryFeatures = molecule.getAtomQueryFeaturesObject(0);
  expect(firstAtomQueryFeatures.not0Hydrogen).toBe(true);
  expect(firstAtomQueryFeatures.not1Hydrogen).toBe(true);
  expect(firstAtomQueryFeatures.not2Hydrogen).toBe(false);
  expect(firstAtomQueryFeatures.not3Hydrogen).toBe(true);
});

test('bond query features cycle of 4 atoms with aromatic bond', () => {
  const molecule = Molecule.fromIDCode('eM@HzC~TFLP');
  const firstBondQueryFeatures = molecule.getBondQueryFeaturesObject(0);
  expect(firstBondQueryFeatures.ringSize).toBe(4);
  expect(firstBondQueryFeatures.aromatic).toBe(true);
  expect(firstBondQueryFeatures.nonAromatic).toBe(false);
});

test('bond query features atom bridge 2 to 7', () => {
  const molecule = Molecule.fromIDCode('eM@HzCNDh');
  const firstBondQueryFeatures = molecule.getBondQueryFeaturesObject(0);
  expect(firstBondQueryFeatures.brigdeMin).toBe(2);
  expect(firstBondQueryFeatures.brigdeSpan).toBe(5);
  expect(firstBondQueryFeatures.nonAromatic).toBe(false);
});

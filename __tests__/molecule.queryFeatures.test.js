'use strict';

const { Molecule } = require('../minimal');

test('atom query features more than 2 neighbours', () => {
  const molecule = Molecule.fromIDCode('eF@Hp[qp');
  const firstAtomQueryFeatures = molecule.getAtomQueryFeaturesObject(0);
  expect(firstAtomQueryFeatures.not0Neighbours).toBe(true);
  expect(firstAtomQueryFeatures.not1Neighbours).toBe(true);
  expect(firstAtomQueryFeatures.not2Neighbours).toBe(true);
  expect(firstAtomQueryFeatures.not3Neighbours).toBe(false);

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
})

test('bond query features atom bridge 2 to 7', () => {
  const molecule = Molecule.fromIDCode('eM@HzCNDh');
  const firstBondQueryFeatures = molecule.getBondQueryFeaturesObject(0);
  expect(firstBondQueryFeatures.brigdeMin).toBe(2);
  expect(firstBondQueryFeatures.brigdeSpan).toBe(5);
  expect(firstBondQueryFeatures.nonAromatic).toBe(false);
})
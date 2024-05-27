'use strict';

const { Molecule } = require('../minimal');

test('queryFeatures more than 2 neighbours', () => {
  const molecule = Molecule.fromIDCode('eF@Hp[qp');

  const firstQueryFeatures = molecule.getAtomQueryFeaturesObject(0);
  expect(firstQueryFeatures.not0Neighbours).toBe(true);
  expect(firstQueryFeatures.not1Neighbours).toBe(true);
  expect(firstQueryFeatures.not2Neighbours).toBe(true);
  expect(firstQueryFeatures.not3Neighbours).toBe(false);

});

test('queryFeatures exactly 2 hydrogens', () => {
  const molecule = Molecule.fromIDCode('eF@Hp_Qh');
  const firstQueryFeatures = molecule.getAtomQueryFeaturesObject(0);
  expect(firstQueryFeatures.not0Hydrogen).toBe(true);
  expect(firstQueryFeatures.not1Hydrogen).toBe(true);
  expect(firstQueryFeatures.not2Hydrogen).toBe(false);
  expect(firstQueryFeatures.not3Hydrogen).toBe(true);
});
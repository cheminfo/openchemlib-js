'use strict';


const { Molecule } = require('../minimal')


test('queryFeatures', () => {
  const molecule = Molecule.fromSmiles('CCO');
  const features = molecule.getAtomQueryFeaturesObject(0);
  console.log(features)

})

'use strict';

const core = require('..');

const minimal = require('../minimal');
const full = require('../full');
const pretty = require('../dist/openchemlib-full.pretty');

describe('Checking for the presence of main APIs', function () {
  const minimalAPI = [
    'Molecule',
    'SDFileParser',
    'SSSearcher',
    'SSSearcherWithIndex',
    'Reaction',
    'Util',
    'version'
  ];

  const coreAPI = [
    'MoleculeProperties',
    'DruglikenessPredictor',
    'DrugScoreCalculator',
    'ToxicityPredictor'
  ];

  const fullAPI = ['StructureView', 'StructureEditor'];

  it('minimal', function () {
    checkHas(minimal, minimalAPI);
    checkHasNot(minimal, coreAPI);
    checkHasNot(minimal, fullAPI);
  });

  it('core', function () {
    expect(core).toBe(require('..'));
    checkHas(core, minimalAPI);
    checkHas(core, coreAPI);
    checkHasNot(core, fullAPI);
  });

  it('full', function () {
    [full, pretty].forEach((lib) => {
      checkHas(lib, minimalAPI);
      checkHas(lib, coreAPI);
      checkHas(lib, fullAPI);
    });
  });
});

function checkHas(obj, properties) {
  properties.forEach((prop) => {
    expect(obj).toHaveProperty(prop);
  });
}

function checkHasNot(obj, properties) {
  properties.forEach((prop) => {
    expect(obj).not.toHaveProperty(prop);
  });
}

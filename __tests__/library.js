'use strict';

const core = require('../core');
const minimal = require('../minimal');
const full = require('../full');
const pretty = require('../dist/openchemlib-full.pretty');

describe('Checking for the presence of main APIs', function () {
  const minimalAPI = [
    'Molecule',
    'RingCollection',
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

  const fullAPI = ['StructureView', 'StructureEditor', 'SVGRenderer'];

  it('minimal', function () {
    checkHas(minimal, minimalAPI);
    checkHasNot(minimal, [...coreAPI, ...fullAPI]);
  });

  it('core', function () {
    expect(core).toBe(require('..'));
    checkHas(core, [...minimalAPI, ...coreAPI]);
    checkHasNot(core, fullAPI);
  });

  it('full', function () {
    [full, pretty].forEach((lib) => {
      checkHas(lib, [...minimalAPI, ...coreAPI, ...fullAPI]);
    });
  });
});

function checkHas(obj, properties) {
  expect(Object.keys(obj).sort()).toEqual(properties.sort());
}

function checkHasNot(obj, properties) {
  properties.forEach((prop) => {
    expect(obj).not.toHaveProperty(prop);
  });
}

'use strict';

const core = require('../core');
const pretty = require('../dist/openchemlib-full.pretty');
const full = require('../full');
const minimal = require('../minimal');

describe('Checking for the presence of main APIs', () => {
  const minimalAPI = [
    'Molecule',
    'RingCollection',
    'SDFileParser',
    'SSSearcher',
    'SSSearcherWithIndex',
    'Reaction',
    'Util',
    'version',
  ];

  const coreAPI = [
    'MoleculeProperties',
    'DruglikenessPredictor',
    'DrugScoreCalculator',
    'ToxicityPredictor',
    'ConformerGenerator',
    'ForceFieldMMFF94',
  ];

  const fullAPI = ['StructureView', 'StructureEditor', 'SVGRenderer'];

  it('minimal', () => {
    checkHas(minimal, minimalAPI);
    checkHasNot(minimal, [...coreAPI, ...fullAPI]);
  });

  it('core', () => {
    expect(core).toBe(require('..'));
    checkHas(core, [...minimalAPI, ...coreAPI]);
    checkHasNot(core, fullAPI);
  });

  it('full', () => {
    [full, pretty].forEach((lib) => {
      checkHas(lib, [...minimalAPI, ...coreAPI, ...fullAPI]);
    });
  });
});

function checkHas(obj, properties) {
  expect(Object.keys(obj).sort()).toStrictEqual(properties.sort());
}

function checkHasNot(obj, properties) {
  properties.forEach((prop) => {
    expect(obj).not.toHaveProperty(prop);
  });
}

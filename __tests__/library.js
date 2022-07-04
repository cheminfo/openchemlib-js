'use strict';

const core = require('../core');
const pretty = require('../dist/openchemlib-full.pretty');
const full = require('../full');
const minimal = require('../minimal');

describe('Checking for the presence of main APIs', () => {
  const minimalAPI = [
    'Molecule',
    'Reaction',
    'RingCollection',
    'SDFileParser',
    'SSSearcher',
    'SSSearcherWithIndex',
    'Util',
    'version',
  ];

  const coreAPI = [
    'CanonizerUtil',
    'ConformerGenerator',
    'DruglikenessPredictor',
    'DrugScoreCalculator',
    'ForceFieldMMFF94',
    'MoleculeProperties',
    'ToxicityPredictor',
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

'use strict';

const core = require('../core');
const pretty = require('../dist/openchemlib-full.pretty');
const full = require('../full');
const minimal = require('../minimal');

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
  'ReactionEncoder',
  'Reactor',
  'ToxicityPredictor',
  'Transformer',
];

const fullAPI = ['StructureView', 'StructureEditor', 'SVGRenderer'];

const allAPI = [...minimalAPI, ...coreAPI, ...fullAPI];

test('minimal', () => {
  checkHas(minimal, minimalAPI);
  checkHasNot(minimal, [...coreAPI, ...fullAPI]);
});

test('core', () => {
  expect(core).toBe(require('..'));
  checkHas(core, [...minimalAPI, ...coreAPI]);
  checkHasNot(core, fullAPI);
});

test('full', () => {
  [full, pretty].forEach((lib) => {
    checkHas(lib, allAPI);
  });
});

allAPI.forEach((key) => {
  const api = full[key];
  if (typeof api === 'function') {
    test(`static properties of ${key}`, () => {
      expect(getFilteredKeys(api)).toMatchSnapshot();
    });
    if (typeof api.prototype === 'object') {
      test(`prototype properties of ${key}`, () => {
        expect(getFilteredKeys(api.prototype)).toMatchSnapshot();
      });
    }
  }
});

function checkHas(obj, properties) {
  expect(Object.keys(obj).sort()).toStrictEqual(properties.sort());
}

function checkHasNot(obj, properties) {
  properties.forEach((prop) => {
    expect(obj).not.toHaveProperty(prop);
  });
}

function getFilteredKeys(obj) {
  return Object.keys(obj)
    .filter((key) => {
      // Filter out GWT-specific properties.
      return key.length > 2;
    })
    .sort();
}

import { expect, test } from 'vitest';

import core from '../core';
import full from '../full';
import pretty from '../full.pretty';
import minimal from '../minimal';

const minimalAPI = [
  'Molecule',
  'Reaction',
  'RingCollection',
  'SDFileParser',
  'SmilesParser',
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

const fullAPI = [
  'CanvasEditor',
  'registerCustomElement',
  'StructureView',
  'StructureEditor',
  'SVGRenderer',
];

const allAPI = [...minimalAPI, ...coreAPI, ...fullAPI];

test('minimal', () => {
  checkHas(minimal, minimalAPI);
  checkHasNot(minimal, [...coreAPI, ...fullAPI]);
});

test('core', () => {
  expect(core).toBe(require('..').default);
  checkHas(core, [...minimalAPI, ...coreAPI]);
  checkHasNot(core, fullAPI);
});

test('full', () => {
  for (const lib of [full, pretty]) {
    checkHas(lib, allAPI);
  }
});

for (const key of allAPI) {
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
}

function checkHas(obj, properties) {
  expect(Object.keys(obj).sort()).toStrictEqual(properties.sort());
}

function checkHasNot(obj, properties) {
  for (const prop of properties) {
    expect(obj).not.toHaveProperty(prop);
  }
}

function getFilteredKeys(obj) {
  return Object.keys(obj)
    .filter((key) => {
      // Filter out GWT-specific properties.
      return key.length > 2;
    })
    .sort();
}

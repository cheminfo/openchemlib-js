import assert from 'node:assert';

import { expect, test } from 'vitest';

import OCL from '../lib/index';
import debugOCL from '../lib/index.debug';

const allAPI = Object.keys(OCL).sort();

test('debug build should have the same exports', () => {
  expect(allAPI).toStrictEqual(Object.keys(debugOCL).sort());
});

test('top-level API', () => {
  expect(allAPI).toMatchSnapshot();
  for (const api of allAPI) {
    assert.ok(OCL[api], `Missing top-level API: ${api}`);
  }
});

for (const key of allAPI) {
  const api = OCL[key];
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

function getFilteredKeys(obj) {
  return Object.keys(obj)
    .filter((key) => {
      // Filter out GWT-specific properties.
      return key.length > 2;
    })
    .sort();
}

import { assert, describe, expect, test } from 'vitest';

import debugOCL from '../lib/index.debug.js';
import OCL from '../lib/index.js';

const allAPI = Object.keys(OCL).sort();

test('debug build should have the same exports', () => {
  expect(allAPI).toStrictEqual(Object.keys(debugOCL).sort());
});

test('top-level API', () => {
  expect(allAPI).toMatchSnapshot();
  for (const api of allAPI) {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    assert((OCL as any)[api], `Missing top-level API: ${api}`);
  }
});

describe('class prototypes', () => {
  for (const key of allAPI) {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const api = (OCL as any)[key];
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
});

function getFilteredKeys(obj: Record<string, unknown>) {
  return Object.keys(obj)
    .filter((key) => {
      // Filter out GWT-specific properties.
      return key.length > 2;
    })
    .sort();
}

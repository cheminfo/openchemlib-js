import { readFile } from 'node:fs/promises';
import path from 'node:path';

import { afterAll, beforeAll, describe, expect, it } from 'vitest';

import { Molecule, Resources, ToxicityPredictor } from '../lib/index.js';

let fetchBefore: typeof globalThis.fetch;
beforeAll(() => {
  fetchBefore = globalThis.fetch;
  // @ts-expect-error Simplified version for the test.
  globalThis.fetch = (url: string) => {
    return {
      async json() {
        const data = await readFile(url, 'utf8');
        return JSON.parse(data);
      },
    };
  };
});
afterAll(() => {
  globalThis.fetch = fetchBefore;
});

describe('Resources registered from URL', () => {
  it('should be registered using fetch and predict toxicity', async () => {
    await Resources.registerFromUrl(
      path.join(import.meta.dirname, '../dist/resources.json'),
    );

    const pred = new ToxicityPredictor();

    expect(
      pred.assessRisk(
        Molecule.fromSmiles('COCCON'),
        ToxicityPredictor.TYPE_MUTAGENIC,
      ),
    ).toBe(ToxicityPredictor.RISK_HIGH);
  });
});

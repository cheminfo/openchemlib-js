import { readFile } from 'node:fs/promises';
import path from 'node:path';

import { afterAll, beforeAll, describe, expect, it, vi } from 'vitest';

import { Molecule, Resources, ToxicityPredictor } from '#lib';

beforeAll(() => {
  vi.stubGlobal('fetch', (url: string) => {
    return {
      async json() {
        const data = await readFile(url, 'utf8');
        return JSON.parse(data);
      },
    };
  });
});

afterAll(() => {
  vi.unstubAllGlobals();
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

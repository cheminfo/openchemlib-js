import { beforeAll, describe, expect, it } from 'vitest';

import { Molecule, Resources, ToxicityPredictor } from '../lib/index.js';

beforeAll(() => {
  Resources.registerResourcesNodejs();
});

describe('ToxicityPredictor', () => {
  it.each([
    [
      'mutagenic',
      ToxicityPredictor.TYPE_MUTAGENIC,
      ToxicityPredictor.RISK_HIGH,
      ToxicityPredictor.RISK_NO,
    ],
    [
      'tumorigenic',
      ToxicityPredictor.TYPE_TUMORIGENIC,
      ToxicityPredictor.RISK_NO,
      ToxicityPredictor.RISK_NO,
    ],
    [
      'irritant',
      ToxicityPredictor.TYPE_IRRITANT,
      ToxicityPredictor.RISK_NO,
      ToxicityPredictor.RISK_NO,
    ],
    [
      'reproductive effective',
      ToxicityPredictor.TYPE_REPRODUCTIVE_EFFECTIVE,
      ToxicityPredictor.RISK_NO,
      ToxicityPredictor.RISK_NO,
    ],
  ])('should assess risk (%s)', (name, riskType, expected1, expected2) => {
    const pred = new ToxicityPredictor();

    expect(pred.assessRisk(Molecule.fromSmiles('COCCON'), riskType)).toBe(
      expected1,
    );

    // Morphine
    expect(
      pred.assessRisk(
        Molecule.fromSmiles(
          '[H][C@]12C=C[C@H](O)[C@@H]3Oc4c(O)ccc5C[C@H]1N(C)CC[C@@]23c45',
        ),
        riskType,
      ),
    ).toBe(expected2);
  });

  it('should return details', () => {
    const pred = new ToxicityPredictor();

    expect(
      pred.getDetail(
        Molecule.fromSmiles('COCCON'),
        ToxicityPredictor.TYPE_MUTAGENIC,
      ),
    ).toMatchInlineSnapshot(`
      [
        {
          "type": 2,
          "value": "High-risk fragments indicating Mutagenicity:",
        },
        {
          "type": 1,
          "value": "eMhHchLJyH",
        },
        {
          "type": 2,
          "value": "Medium-risk fragments indicating Mutagenicity:",
        },
        {
          "type": 1,
          "value": "eFhHcAaWH@",
        },
      ]
    `);
  });
});

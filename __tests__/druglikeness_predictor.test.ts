import { beforeAll, describe, expect, it } from 'vitest';

import { DruglikenessPredictor, Molecule, Resources } from '../lib/index.js';

beforeAll(() => {
  Resources.registerFromNodejs();
});

describe('DruglikenessPredictor', () => {
  it('should assess druglikeness', () => {
    const pred = new DruglikenessPredictor();

    expect(pred.assessDruglikeness(Molecule.fromSmiles('COCCON'))).toBe(
      -4.564473319220205,
    );

    // Morphine
    expect(
      pred.assessDruglikeness(
        Molecule.fromSmiles(
          '[H][C@]12C=C[C@H](O)[C@@H]3Oc4c(O)ccc5C[C@H]1N(C)CC[C@@]23c45',
        ),
      ),
    ).toBe(5.143266280203382);
  });

  it('should get druglikeness string', () => {
    const pred = new DruglikenessPredictor();

    expect(pred.getDruglikenessString(Molecule.fromSmiles('COCCON'))).toBe(
      '-1.0583048194679503\t11\t6',
    );

    // Morphine
    expect(
      pred.getDruglikenessString(
        Molecule.fromSmiles(
          '[H][C@]12C=C[C@H](O)[C@@H]3Oc4c(O)ccc5C[C@H]1N(C)CC[C@@]23c45',
        ),
      ),
    ).toBe('5.205766280203382\t39\t21');
  });

  it('should return details', () => {
    const pred = new DruglikenessPredictor();

    expect(() => pred.getDetail()).toThrow(/assessed first/);

    pred.assessDruglikeness(Molecule.fromSmiles('COC'));

    expect(pred.getDetail()).toMatchInlineSnapshot(`
      [
        {
          "type": 2,
          "value": "Found sub-structure fragments and their contributions:",
        },
        {
          "type": 2,
          "value": "(yellow atoms carry at least one more substituent)",
        },
        {
          "type": 1,
          "value": "RFPDXLJy@",
        },
        {
          "type": 3,
          "value": "0.11",
        },
        {
          "type": 1,
          "value": "\`H@^P",
        },
        {
          "type": 3,
          "value": "0.08",
        },
        {
          "type": 1,
          "value": "\`J@OH",
        },
        {
          "type": 3,
          "value": "-0.1",
        },
      ]
    `);
  });
});

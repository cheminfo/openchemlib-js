import { describe, expect, it } from 'vitest';

import { DrugScoreCalculator, ToxicityPredictor } from '../lib/index.js';

describe('DrugScoreCalculator', () => {
  it('should calculate drug score', () => {
    expect(DrugScoreCalculator.calculate(1, 1, 50, 1, null)).toBe(
      0.8547591078618019,
    );
    expect(
      DrugScoreCalculator.calculate(2, 2, 60, 2, [ToxicityPredictor.RISK_LOW]),
    ).toBe(0.732284749199936);
  });
});

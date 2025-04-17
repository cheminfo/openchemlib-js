import { describe, expect, it } from 'vitest';

import { Molecule } from '../lib/index.js';

describe('MoleculeFormula', () => {
  it('should compute formula', () => {
    const mol = Molecule.fromSmiles('COCCON');
    const formula = mol.getMolecularFormula();
    expect(formula.absoluteWeight).toBeCloseTo(91.063);
    expect(formula.relativeWeight).toBeCloseTo(91.109);
    expect(formula.formula).toBe('C3H9NO2');
  });
});

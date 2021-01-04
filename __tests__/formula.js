'use strict';

const OCL = require('../minimal');

const Molecule = OCL.Molecule;

describe('MoleculeFormula', function () {
  it('should compute formula', function () {
    const mol = Molecule.fromSmiles('COCCON');
    const formula = mol.getMolecularFormula();
    expect(formula.absoluteWeight).toBeCloseTo(91.063);
    expect(formula.relativeWeight).toBeCloseTo(91.109);
    expect(formula.formula).toBe('C3H9NO2');
  });
});

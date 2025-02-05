import { describe, expect, it } from 'vitest';

import { Molecule, MoleculeProperties } from '../core';

describe('MoleculeProperties', () => {
  it('should compute properties', () => {
    const mol = Molecule.fromSmiles('COCCON');
    const properties = new MoleculeProperties(mol);
    expect(properties.acceptorCount).toBe(3);
    expect(properties.donorCount).toBe(1);
    expect(properties.logP).toBeCloseTo(-0.8937);
    expect(properties.logS).toBeCloseTo(-0.5);
    expect(properties.polarSurfaceArea).toBeCloseTo(44.48);
    expect(properties.rotatableBondCount).toBe(3);
    expect(properties.stereoCenterCount).toBe(0);
  });
});

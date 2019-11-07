'use strict';

const OCL = require('../core');

const Molecule = OCL.Molecule;
const MoleculeProperties = OCL.MoleculeProperties;

describe('MoleculeProperties', function() {
  it('should compute properties', function() {
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

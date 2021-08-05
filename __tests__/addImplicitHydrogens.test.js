'use strict';

const Molecule = require('../minimal').Molecule;

describe('addImplicitHydroens', () => {
  it('C', () => {
    const molecule = Molecule.fromSmiles('CC');
    expect(molecule.getAllAtoms()).toBe(2);
    molecule.addImplicitHydrogens();
    expect(molecule.getAllAtoms()).toBe(8);
  });
  it('N', () => {
    const molecule = Molecule.fromSmiles('N');
    expect(molecule.getAllAtoms()).toBe(1);
    molecule.addImplicitHydrogens();
    expect(molecule.getAllAtoms()).toBe(4);
  });
  it('Cl', () => {
    const molecule = Molecule.fromSmiles('Cl');
    expect(molecule.getAllAtoms()).toBe(1);
    molecule.addImplicitHydrogens();
    expect(molecule.getAllAtoms()).toBe(2);
  });
});

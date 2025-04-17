import { describe, expect, it } from 'vitest';

import { Molecule } from '../lib/index.js';

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
  it('O', () => {
    const molecule = Molecule.fromSmiles('O');
    expect(molecule.getAllAtoms()).toBe(1);
    molecule.addImplicitHydrogens();
    expect(molecule.getAllAtoms()).toBe(3);
  });
});

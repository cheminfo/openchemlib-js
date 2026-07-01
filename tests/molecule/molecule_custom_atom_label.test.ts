import { expect, test } from 'vitest';

import { Canonizer, Molecule } from '#lib';

test('Custom atom labels should be supported in molfiles', () => {
  const mol = Molecule.fromSmiles('COCCON');
  mol.setAtomCustomLabel(0, 'R3');
  mol.setAtomCustomLabel(1, "]1'");
  const molfile = mol.toMolfileV3();

  expect(molfile).toMatchSnapshot();

  const mol2 = Molecule.fromMolfile(molfile);

  expect(mol2.getAtomCustomLabel(0)).toBe('R3');
  expect(mol2.getAtomCustomLabel(1)).toBe("]1'");
  expect(mol2.getAtomCustomLabel(2)).toBeNull();
});

test('Custom atom labels on explicit hydrogens survive the canonized idcode', () => {
  const molecule = Molecule.fromSmiles('C=O');
  molecule.addImplicitHydrogens();

  // formaldehyde: C(0), O(1) and the two explicit hydrogens on the carbon
  molecule.setAtomCustomLabel(2, 'r');
  molecule.setAtomCustomLabel(3, 's');

  const idCode = molecule.getCanonizedIDCode(
    Molecule.CANONIZER_ENCODE_ATOM_CUSTOM_LABELS,
  );
  const molecule2 = Molecule.fromIDCode(idCode, false);

  const labels = [];
  for (let i = 0; i < molecule2.getAllAtoms(); i++) {
    const label = molecule2.getAtomCustomLabel(i);
    if (label !== null) labels.push(label);
  }

  expect(molecule2.getAllAtoms()).toBe(4);
  expect(labels.toSorted()).toStrictEqual(['r', 's']);
});

test('Custom atom labels should not break molfile syntax', () => {
  const molecule = Molecule.fromSmiles('C');
  const specialText = 'special: "\'-_/chars';
  molecule.setAtomCustomLabel(0, specialText);

  const molfileV2 = molecule.toMolfile();

  expect(molfileV2).toMatchSnapshot();

  const mol2 = Molecule.fromMolfile(molfileV2);

  expect(mol2.getAtomCustomLabel(0)).toBe(specialText);

  const molfileV3 = molecule.toMolfileV3();

  expect(molfileV3).toMatchSnapshot();

  const mol3 = Molecule.fromMolfile(molfileV3);

  expect(mol3.getAtomCustomLabel(0)).toBe(specialText);
});

test('Custom atom labels should support unicode', () => {
  const molecule = Molecule.fromSmiles('OCN');

  molecule.setAtomCustomLabel(0, 'α');

  expect(molecule.getAtomCustomLabel(0)).toBe('α');

  molecule.setAtomCustomLabel(1, '裞');

  expect(molecule.getAtomCustomLabel(1)).toBe('裞');

  molecule.setAtomCustomLabel(2, '🪿🥰🦹🏽');

  expect(molecule.getAtomCustomLabel(2)).toBe('🪿🥰🦹🏽');

  const idCode = new Canonizer(molecule, {
    encodeAtomCustomLabels: true,
  }).getIDCode();
  const molecule2 = Molecule.fromIDCode(idCode);

  expect(molecule2.getAtomCustomLabel(0)).toBe('α');
  expect(molecule2.getAtomCustomLabel(1)).toBe('裞');
  expect(molecule2.getAtomCustomLabel(2)).toBe('🪿🥰🦹🏽');

  const molfile = molecule.toMolfileV3();
  const molecule3 = Molecule.fromMolfile(molfile);

  expect(molecule3.getAtomCustomLabel(0)).toBe('α');
  expect(molecule3.getAtomCustomLabel(1)).toBe('裞');
  expect(molecule3.getAtomCustomLabel(2)).toBe('🪿🥰🦹🏽');

  expect(molfile).toMatchSnapshot();
});

import { expect, test } from 'vitest';

import { Canonizer, CanonizerUtil, Molecule } from '../lib/index.js';

test('canonizer chiral', () => {
  const molecule = Molecule.fromSmiles('C[C@H](Cl)CC');
  const idCode = molecule.getIDCode();

  expect(idCode).toBe('gJPHADILuTb@');
  expect(CanonizerUtil.getIDCode(molecule, CanonizerUtil.NORMAL)).toBe(idCode);

  expect(CanonizerUtil.getIDCode(molecule, CanonizerUtil.NOSTEREO)).toBe(
    'gJPHADILuP@',
  );

  expect(CanonizerUtil.getIDCode(molecule, CanonizerUtil.BACKBONE)).toBe(
    'gJPHADILuP@',
  );

  expect(CanonizerUtil.getIDCode(molecule, CanonizerUtil.TAUTOMER)).toBe(
    'gJPHADILuTb@',
  );

  expect(
    CanonizerUtil.getIDCode(molecule, CanonizerUtil.NOSTEREO_TAUTOMER),
  ).toBe('gJPHADILuP@');

  molecule.stripStereoInformation();
  expect(molecule.getIDCode()).toBe('gJPHADILuP@');
});

test('canonizer tautomer', () => {
  const molecule = Molecule.fromSmiles('CC=C(O)CC');

  const idCode = molecule.getIDCode();

  expect(idCode).toBe('gGQ@@drsT@@');
  expect(CanonizerUtil.getIDCode(molecule, CanonizerUtil.NORMAL)).toBe(idCode);

  expect(CanonizerUtil.getIDCode(molecule, CanonizerUtil.NOSTEREO)).toBe(
    'gGQ@@drsT@@',
  );

  expect(CanonizerUtil.getIDCode(molecule, CanonizerUtil.BACKBONE)).toBe(
    'gGQ@@druT@@',
  );

  expect(CanonizerUtil.getIDCode(molecule, CanonizerUtil.TAUTOMER)).toBe(
    'gGQ@@druTAkabXVOtfBicxX~F@',
  );

  expect(
    CanonizerUtil.getIDCode(molecule, CanonizerUtil.NOSTEREO_TAUTOMER),
  ).toBe('gGQ@@druTAkabXVOtfBicxX~F@');

  molecule.stripStereoInformation();
  expect(molecule.getIDCode()).toBe('gGQ@@drsT@@');
});

test('getIDCode returns expected value', () => {
  const moleculeChiral = Molecule.fromSmiles('C[C@H](Cl)CC');
  const canonizer = new Canonizer(moleculeChiral, 0);
  // The default canonicalization should yield the same ID code as the molecule's method.
  expect(canonizer.getIDCode()).toBe(moleculeChiral.getIDCode());
});

test('hasCIPParityDistinctionProblem returns a boolean', () => {
  const moleculeChiral = Molecule.fromSmiles('C[C@H](Cl)CC');
  const canonizer = new Canonizer(moleculeChiral, 0);
  const result = canonizer.hasCIPParityDistinctionProblem();
  expect(typeof result).toBe('boolean');
  expect(result).toBe(false);
});

test('getCanMolecule returns a canonical molecule', () => {
  const moleculeChiral = Molecule.fromSmiles('C[C@H](Cl)CC');
  moleculeChiral.addImplicitHydrogens();
  const canonizer = new Canonizer(moleculeChiral, 0);

  const canMolDefault = canonizer.getCanMolecule(false);
  const canMolWithH = canonizer.getCanMolecule(true);

  expect(canMolDefault.getIDCode()).toBe(canMolWithH.getIDCode());
  expect(canMolDefault.getAllAtoms()).toBe(5);
  expect(canMolWithH.getAllAtoms()).toBe(14);
});

test('getFinalRank returns an array with expected length', () => {
  const moleculeChiral = Molecule.fromSmiles('C[C@H](Cl)CC');
  const canonizer = new Canonizer(moleculeChiral, 0);
  const finalRank = canonizer.getFinalRank();
  expect(Array.isArray(finalRank)).toBe(true);
  expect(finalRank.length).toBeGreaterThan(0);
  expect(finalRank.toString()).toBe('2,4,5,3,1');
});

test('getSymmetryRank and getSymmetryRanks return consistent results', () => {
  const moleculeSymmetric = Molecule.fromSmiles('CC(C)C');
  const canonizer = new Canonizer(
    moleculeSymmetric,
    Canonizer.CREATE_SYMMETRY_RANK,
  );
  const symmetryRanks = canonizer.getSymmetryRanks();
  expect(Array.isArray(symmetryRanks)).toBe(true);
  expect(symmetryRanks.toString()).toBe('1,2,1,1');
  // For each atom, the singular symmetry rank should match the corresponding element.
  for (const [index, rank] of symmetryRanks.entries()) {
    expect(canonizer.getSymmetryRank(index)).toBe(rank);
  }
});

test('invalidateCoordinates and getEncodedCoordinates work as expected', () => {
  const moleculeChiral = Molecule.fromSmiles('C[C@H](Cl)CC');
  moleculeChiral.inventCoordinates();
  const canonizer = new Canonizer(moleculeChiral, 0);
  // Get encoded coordinates before invalidation.
  const encodedBefore = canonizer.getEncodedCoordinates(false);
  expect(typeof encodedBefore).toBe('string');
  expect(encodedBefore.length).toBeGreaterThan(0);
  expect(encodedBefore).toBe('!B?`BH@k\\BbOt');

  // Invalidate coordinates.
  canonizer.invalidateCoordinates();
  // Get encoded coordinates after invalidation.
  const encodedAfter = canonizer.getEncodedCoordinates(false);
  expect(typeof encodedAfter).toBe('string');
  // We simply check that a string is returned; the value should not differ.
  expect(encodedAfter.length).toBeGreaterThan(0);
  expect(encodedAfter).toBe('!B?`BH@k\\BbOt');
});

test('getEncodedMapping returns a non-empty string', () => {
  const moleculeMapped = Molecule.fromSmiles('C[C@H](Cl)CC');
  moleculeMapped.setAtomMapNo(0, 1, false);
  moleculeMapped.setAtomMapNo(1, 1, false);
  let canonizer = new Canonizer(moleculeMapped, 0);
  let mapping = canonizer.getEncodedMapping();
  expect(typeof mapping).toBe('string');
  expect(mapping.length).toBeGreaterThan(0);
  expect(mapping).toBe('aT');

  const molecule = Molecule.fromSmiles('C[C@H](Cl)CC');
  canonizer = new Canonizer(molecule, 0);
  mapping = canonizer.getEncodedMapping();
  expect(typeof mapping).toBe('string');
  expect(mapping.length).toBe(0);
});

test('normalizeEnantiomer returns a boolean value', () => {
  const moleculeChiral = Molecule.fromSmiles('C[C@H](Cl)CC');
  const canonizer = new Canonizer(moleculeChiral, 0);
  const normalized = canonizer.normalizeEnantiomer();
  expect(typeof normalized).toBe('boolean');
  expect(normalized).toBe(true);
});

test('setParities, getGraphAtoms, and getGraphIndexes produce consistent results', () => {
  const moleculeChiral = Molecule.fromSmiles('C[C@H](Cl)CC');
  const canonizer = new Canonizer(moleculeChiral, 0);
  // Obtain initial graph arrays.
  const graphAtomsBefore = canonizer.getGraphAtoms();
  const graphIndexesBefore = canonizer.getGraphIndexes();

  // Call setParities to update internal state.
  canonizer.setParities();

  const graphAtomsAfter = canonizer.getGraphAtoms();
  const graphIndexesAfter = canonizer.getGraphIndexes();

  // Check that the returned arrays are indeed arrays and of consistent length.
  // The values should not have changed after calling setParities.
  expect(Array.isArray(graphAtomsBefore)).toBe(true);
  expect(Array.isArray(graphIndexesBefore)).toBe(true);
  expect(Array.isArray(graphAtomsAfter)).toBe(true);
  expect(Array.isArray(graphIndexesAfter)).toBe(true);
  expect(graphAtomsAfter.length).toBe(graphAtomsBefore.length);
  expect(graphIndexesAfter.length).toBe(graphIndexesBefore.length);
  expect(graphAtomsBefore.toString()).toBe('2,1,3,0,4');
  expect(graphIndexesBefore.toString()).toBe('3,1,0,2,4');
  expect(graphAtomsAfter.toString()).toBe('2,1,3,0,4');
  expect(graphIndexesAfter.toString()).toBe('3,1,0,2,4');
});

test('Constructor with mode parameter works', () => {
  const moleculeTautomer = Molecule.fromSmiles('CC=C(O)CC');
  let canonizer = new Canonizer(moleculeTautomer, 0);
  let idCode = canonizer.getIDCode();
  expect(typeof idCode).toBe('string');
  expect(idCode.length).toBeGreaterThan(0);
  expect(idCode).toBe('gGQ@@drsT@@');

  // Use custom label to test mode
  const mode = Canonizer.ENCODE_ATOM_CUSTOM_LABELS;
  moleculeTautomer.setAtomCustomLabel(0, 'label');
  canonizer = new Canonizer(moleculeTautomer, mode);
  idCode = canonizer.getIDCode();
  expect(typeof idCode).toBe('string');
  expect(idCode.length).toBeGreaterThan(0);
  expect(idCode).toBe('gGQ@@drsT@RXZ[ahzYl');
});

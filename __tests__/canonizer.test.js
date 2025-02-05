import { expect, test } from 'vitest';

import { CanonizerUtil, Molecule } from '../core';

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

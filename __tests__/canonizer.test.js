'use strict';

const OCL = require('../core');

test('canonizer chiral', () => {
  const molecule = OCL.Molecule.fromSmiles('C[C@H](Cl)CC');
  const idCode = molecule.getIDCode();

  expect(idCode).toBe('gJPHADILuTb@');
  expect(OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.NORMAL)).toBe(
    idCode,
  );

  expect(
    OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.NOSTEREO),
  ).toBe('gJPHADILuP@');

  expect(
    OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.BACKBONE),
  ).toBe('gJPHADILuP@');

  expect(
    OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.TAUTOMER),
  ).toBe('gJPHADILuTb@');

  expect(
    OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.NOSTEREO_TAUTOMER),
  ).toBe('gJPHADILuP@');

  molecule.stripStereoInformation();
  expect(molecule.getIDCode()).toBe('gJPHADILuP@');
});

test('canonizer tautomer', () => {
  const molecule = OCL.Molecule.fromSmiles('CC=C(O)CC');

  const idCode = molecule.getIDCode();

  expect(idCode).toBe('gGQ@@drsT@@');
  expect(OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.NORMAL)).toBe(
    idCode,
  );

  expect(
    OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.NOSTEREO),
  ).toBe('gGQ@@drsT@@');

  expect(
    OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.BACKBONE),
  ).toBe('gGQ@@druT@@');

  expect(
    OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.TAUTOMER),
  ).toBe('gGQ@@druTAkabXVOtfBicxX~F@');

  expect(
    OCL.CanonizerUtil.getIDCode(molecule, OCL.CanonizerUtil.NOSTEREO_TAUTOMER),
  ).toBe('gGQ@@druTAkabXVOtfBicxX~F@');

  molecule.stripStereoInformation();
  expect(molecule.getIDCode()).toBe('gGQ@@drsT@@');
});

'use strict';

const OCL = require('../core');

describe('CanonizerUtil', () => {
  it('check id codes', () => {
    const molecule1 = OCL.Molecule.fromSmiles('CCC(=O)CC');
    const molecule2 = OCL.Molecule.fromSmiles('CC=C(O)CC');

    const id11 = molecule1.getIDCode();
    const id21 = molecule2.getIDCode();

    const id12 = OCL.CanonizerUtil.getIDCode(
      molecule1,
      OCL.CanonizerUtil.NOSTEREO_TAUTOMER,
    );
    const id22 = OCL.CanonizerUtil.getIDCode(
      molecule2,
      OCL.CanonizerUtil.NOSTEREO_TAUTOMER,
    );

    expect(id11).not.toBe(id21);
    expect(id12).toBe(id22);
  });
});

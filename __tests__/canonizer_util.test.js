import { describe, expect, it } from 'vitest';

import { CanonizerUtil, Molecule } from '../core';

describe('CanonizerUtil', () => {
  it('check id codes', () => {
    const molecule1 = Molecule.fromSmiles('CCC(=O)CC');
    const molecule2 = Molecule.fromSmiles('CC=C(O)CC');

    const id11 = molecule1.getIDCode();
    const id21 = molecule2.getIDCode();

    const id12 = CanonizerUtil.getIDCode(
      molecule1,
      CanonizerUtil.NOSTEREO_TAUTOMER,
    );
    const id22 = CanonizerUtil.getIDCode(
      molecule2,
      CanonizerUtil.NOSTEREO_TAUTOMER,
    );

    expect(id11).not.toBe(id21);
    expect(id12).toBe(id22);
  });
});

import { describe, expect, it } from 'vitest';

import { Molecule } from '../lib/index.js';

describe('diastereotopicIDs', () => {
  it('methylcyclohexane', () => {
    const molecule = Molecule.fromSmiles('C1CCCCC1C');
    const diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toStrictEqual([
      'gOp@DiekjjiJ@qAP_iDCaU@',
      'gOp@DiVMjjij@qAP_iDCaU@',
      'gOp@DiWMjj`FHJC}H`\\Jh',
      'gOp@DjWkjj`FHJC}H`\\Jh',
      'gOp@DfUkjj`FHJC}H`\\Jh',
    ]);

    molecule.addImplicitHydrogens();
    const diaIDsH = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));

    for (const diaID of diaIDs) {
      expect(diaIDsH.indexOf(diaID)).toBeGreaterThan(-1);
    }

    expect(diaIDsH).toStrictEqual([
      'gOp@DiekjjiJ@qAP_iDCaU@',
      'gOp@DiVMjjij@qAP_iDCaU@',
      'gOp@DiWMjj`FHJC}H`\\Jh',
      'gOp@DjWkjj`FHJC}H`\\Jh',
      'gOp@DfUkjj`FHJC}H`\\Jh',
      'daD@`@fTfYVzjjdbaB@_iB@bUP',
      'daD@`@fTfYVzjjdbaJ@_iB@bUP',
      'daD@`@fTfUjZjjdb`b@_iB@bUP',
      'daD@`@fTfUjZjjdb`j@_iB@bUP',
      'daD@`@fTfUzZjjdbL`_iB@bUP',
      'daD@`@fTfUzZjjdbM@_iB@bUP',
      'daD@`@fTfevzjj`A~dHBIU@',
      'daD@`@fTeeVzjj`A~dHBIU@',
    ]);
  });

  it('methane', () => {
    const molecule = Molecule.fromSmiles('C');
    const diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(1);
    molecule.addImplicitHydrogens();
    const diaIDsH = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));

    expect(diaIDsH).toHaveLength(2);
  });

  it('hexane', () => {
    const molecule = Molecule.fromSmiles('CCCCCC');
    const diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(3);
  });

  it('butanol meso', () => {
    const molecule = Molecule.fromSmiles('C[C@@H](O)[C@@H](O)C');
    molecule.addImplicitHydrogens();
    const diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(6);
  });

  it('butanol C2', () => {
    const molecule = Molecule.fromSmiles('C[C@@H](O)[C@H](O)C');
    molecule.addImplicitHydrogens();
    const diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(6);
  });

  it('trans pyrolidine', () => {
    const molecule = Molecule.fromSmiles('CCN1[C@@H](C)CC[C@@H]1C');
    molecule.addImplicitHydrogens();
    const diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(13);
  });

  it('cis pyrolidine', () => {
    const molecule = Molecule.fromSmiles('CCN1[C@@H](C)CC[C@H]1C');
    molecule.addImplicitHydrogens();
    const diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(12);
  });

  it('benzene', () => {
    const molecule = Molecule.fromSmiles('c1ccccc1');
    molecule.addImplicitHydrogens();
    const diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(2);
  });

  it('ethylbenzene', () => {
    const molecule = Molecule.fromSmiles('c1ccccc1CC');
    molecule.addImplicitHydrogens();
    const diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(11);
  });

  it('ethylbenzene 2', () => {
    const molecule = Molecule.fromSmiles('CC(O)C(C)C');
    const diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(6);
  });

  it('CC(Cl)CC', () => {
    const molecule = Molecule.fromSmiles('CC(Cl)CC');
    const diaIDs = Array.from(molecule.getDiastereotopicAtomIDs());
    expect(diaIDs).toStrictEqual([
      'gJPHADIMuTe@XbhOtbIpj`',
      'gJPHADILuTe@XdhOtbQpj`',
      'gJPHADILuTe@X`hOtbCpfuP',
      'gJPHADILuTe@XbhOtbIpj`',
      'gJPHADILuTe@XahOtbEpj`',
    ]);
  });
});

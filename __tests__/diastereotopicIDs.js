'use strict';

const Molecule = require('../minimal').Molecule;

describe('diastereotopicIDs', function() {
  it('methylcyclohexane', function() {
    let molecule = Molecule.fromSmiles('C1CCCCC1C');
    let diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toStrictEqual([
      'gOp@DiekjjiJ@qAP_iDCaU@',
      'gOp@DiVMjjij@qAP_iDCaU@',
      'gOp@DiWMjj`FHJC}H`\\Jh',
      'gOp@DjWkjj`FHJC}H`\\Jh',
      'gOp@DfUkjj`FHJC}H`\\Jh',
    ]);

    molecule.addImplicitHydrogens();
    let diaIDsH = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));

    for (let diaID of diaIDs) {
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

  it('methane', function() {
    let molecule = Molecule.fromSmiles('C');
    let diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(1);
    molecule.addImplicitHydrogens();
    let diaIDsH = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));

    expect(diaIDsH).toHaveLength(2);
  });

  it('hexane', function() {
    let molecule = Molecule.fromSmiles('CCCCCC');
    let diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(3);
  });

  it('butanol meso', function() {
    let molecule = Molecule.fromSmiles('C[C@@H](O)[C@@H](O)C');
    molecule.addImplicitHydrogens();
    let diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(6);
  });

  it('butanol C2', function() {
    let molecule = Molecule.fromSmiles('C[C@@H](O)[C@H](O)C');
    molecule.addImplicitHydrogens();
    let diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(6);
  });

  it('trans pyrolidine', function() {
    let molecule = Molecule.fromSmiles('CCN1[C@@H](C)CC[C@@H]1C');
    molecule.addImplicitHydrogens();
    let diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(13);
  });

  it('cis pyrolidine', function() {
    let molecule = Molecule.fromSmiles('CCN1[C@@H](C)CC[C@H]1C');
    molecule.addImplicitHydrogens();
    let diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(12);
  });

  it('benzene', function() {
    let molecule = Molecule.fromSmiles('c1ccccc1');
    molecule.addImplicitHydrogens();
    let diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(2);
  });

  it('ethylbenzene', function() {
    let molecule = Molecule.fromSmiles('c1ccccc1CC');
    molecule.addImplicitHydrogens();
    let diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(11);
  });

  it('ethylbenzene 2', function() {
    let molecule = Molecule.fromSmiles('CC(O)C(C)C');
    let diaIDs = Array.from(new Set(molecule.getDiastereotopicAtomIDs()));
    expect(diaIDs).toHaveLength(6);
  });
});

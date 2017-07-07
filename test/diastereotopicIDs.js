'use strict';

const Molecule = require('../minimal').Molecule;

describe('diastereotopicIDs', function () {
    
    it('methylcyclohexane', function () {
        let molecule = Molecule.fromSmiles('C1CCCCC1C');
        var diaIDs = [... new Set(molecule.getDiastereotopicAtomIDs())];
        diaIDs.length.should.equal(5);
        diaIDs.should.eql([
            'gOp@DiekjjiJ@qAP_iDCaU@',
            'gOp@DiVMjjij@qAP_iDCaU@',
            'gOp@DiWMjj`FHJC}H`\\Jh',
            'gOp@DjWkjj`FHJC}H`\\Jh',
            'gOp@DfUkjj`FHJC}H`\\Jh'
        ])


        molecule.addImplicitHydrogens();
        var diaIDsH = [... new Set(molecule.getDiastereotopicAtomIDs())];

        for (var diaID of diaIDsH) {
            diaIDs.includes(diaID).should.be.greaterThan(-1);
        }

        diaIDsH.should.eql([
            'gOp@DiekjjiJ@qAP_iDCaU@',
            'gOp@DiVMjjij@qAP_iDCaU@',
            'gOp@DiWMjj`FHJC}H`\\Jh',
            'gOp@DjWkjj`FHJC}H`\\Jh',
            'gOp@DfUkjj`FHJC}H`\\Jh',
            'daD@`J`DjYvzjjdBbB@XbjC}HRdRj@',
            'daD@`J`DjYvzjjdBbJ@XbjC}HRdRj@',
            'daD@`N`DjWjZjjdB`b@XcjC}HSdRj@',
            'daD@`N`DjWjZjjdB`j@XcjC}HSdRj@',
            'daD@`N`DjWzZjjdBL`XcjC}HSdRj@',
            'daD@`N`DjWzZjjdBM@XcjC}HSdRj@',
            'daD@`B`LddRK]UUP@qATGzPaHeT',
            'daD@`F`DjUZzjj`AbFhOtaFQJh'
        ]);
    });

    it('methane', function () {
        let molecule = Molecule.fromSmiles('C');
        let diaIDs = [... new Set(molecule.getDiastereotopicAtomIDs())];
        diaIDs.length.should.equal(1);
        molecule.addImplicitHydrogens();
        let diaIDsH = [... new Set(molecule.getDiastereotopicAtomIDs())];

        diaIDsH.length.should.equal(2);
    });

    it('hexane', function () {
        let molecule = Molecule.fromSmiles('CCCCCC');
        let diaIDs = [... new Set(molecule.getDiastereotopicAtomIDs())];
        diaIDs.length.should.equal(3);
    });

    it('hexane', function () {
        let molecule = Molecule.fromSmiles('CCCCCC');
        let diaIDs = [... new Set(molecule.getDiastereotopicAtomIDs())];
        diaIDs.length.should.equal(3);
    });

    it('butanol meso', function () {
        let molecule = Molecule.fromSmiles('C[C@@H](O)[C@@H](O)C');
        molecule.addImplicitHydrogens();
        let diaIDs = [... new Set(molecule.getDiastereotopicAtomIDs())];
        diaIDs.length.should.equal(6);
    });


    it('butanol C2', function () {
        let molecule = Molecule.fromSmiles('C[C@@H](O)[C@H](O)C');
        molecule.addImplicitHydrogens();
        let diaIDs = [... new Set(molecule.getDiastereotopicAtomIDs())];
        diaIDs.length.should.equal(6);
    });


    it('trans pyrolidine', function () {
        let molecule = Molecule.fromSmiles('CCN1[C@@H](C)CC[C@@H]1C');
        molecule.addImplicitHydrogens();
        let diaIDs = [... new Set(molecule.getDiastereotopicAtomIDs())];
        diaIDs.length.should.equal(13);
    });

    it('cis pyrolidine', function () {
        let molecule = Molecule.fromSmiles('CCN1[C@@H](C)CC[C@H]1C');
        molecule.addImplicitHydrogens();
        let diaIDs = [... new Set(molecule.getDiastereotopicAtomIDs())];
        diaIDs.length.should.equal(12);
    });

    it('benzene', function () {
        let molecule = Molecule.fromSmiles('c1ccccc1');
        molecule.addImplicitHydrogens();
        let diaIDs = [... new Set(molecule.getDiastereotopicAtomIDs())];
        diaIDs.length.should.equal(2);
    });

    it('ethylbenzene', function () {
        let molecule = Molecule.fromSmiles('c1ccccc1CC');
        molecule.addImplicitHydrogens();
        let diaIDs = [... new Set(molecule.getDiastereotopicAtomIDs())];
        diaIDs.length.should.equal(11);
    });
});

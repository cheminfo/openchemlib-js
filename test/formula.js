'use strict';

const OCL = require('../minimal');
const Molecule = OCL.Molecule;

describe('MoleculeFormula', function () {
    
    it('should compute formula', function () {
        const mol = Molecule.fromSmiles('COCCON');
        const formula = mol.getMolecularFormula();
        formula.absoluteWeight.should.be.a.Number();
        formula.relativeWeight.should.be.a.Number();
        formula.formula.should.be.a.String();
    });

});

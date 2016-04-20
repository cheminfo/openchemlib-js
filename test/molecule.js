'use strict';

const Molecule = require('..').Molecule;

describe('Molecule', function () {
    
    it('fromSmiles', function () {
        testFromSmiles('C');
        testFromSmiles('COCOC');
        
        (function () {
            Molecule.fromSmiles('ABC');
        }).should.throw();
        
        function testFromSmiles(smiles) {
            var mol = Molecule.fromSmiles(smiles);
            mol.toSmiles().should.equal(smiles);
        }
    });
    
});
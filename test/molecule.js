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

    it('medley', function () {
        var idcode = 'enYXNH@MHDAELem`OCIILdhhdiheCDlieKDdefndZRVVjjfjjfjihJBbb@@@';
        var mol = Molecule.fromIDCode(idcode);
        
        var molfile = mol.toMolfile();
        mol = Molecule.fromMolfile(molfile);
        mol.getIDCode().should.equal(idcode);
        
        var smiles = mol.toSmiles();
        mol = Molecule.fromSmiles(smiles);
        mol.getIDCode().should.equal(idcode);
    });
    
});

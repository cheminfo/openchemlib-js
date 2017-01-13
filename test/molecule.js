'use strict';

const Molecule = require('../minimal').Molecule;

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

    it('toSVG', function () {
        var mol = Molecule.fromSmiles('CCOCCO');
        var svg = mol.toSVG(300, 150, 'myId');
        svg.should.containEql('width="300px" height="150px"');
        svg.should.containEql('myId:Bond:1-0');
        svg.should.containEql('myId:Atom:0');
    });
    
});

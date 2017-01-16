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
            const mol = Molecule.fromSmiles(smiles);
            mol.toSmiles().should.equal(smiles);
        }
    });

    it('medley', function () {
        const idcode = 'enYXNH@MHDAELem`OCIILdhhdiheCDlieKDdefndZRVVjjfjjfjihJBbb@@@';
        let mol = Molecule.fromIDCode(idcode);
        
        const molfile = mol.toMolfile();
        mol = Molecule.fromMolfile(molfile);
        mol.getIDCode().should.equal(idcode);
        
        const smiles = mol.toSmiles();
        mol = Molecule.fromSmiles(smiles);
        mol.getIDCode().should.equal(idcode);
    });

    it('toSVG', function () {
        const mol = Molecule.fromSmiles('CCOCCO');
        const svg = mol.toSVG(300, 150, 'myId');
        svg.should.containEql('width="300px" height="150px"');
        svg.should.containEql('myId:Bond:1-0');
        svg.should.containEql('myId:Atom:0');
    });

    it('molfile V3', function () {
        const idcode = 'enYXNH@MHDAELem`OCIILdhhdiheCDlieKDdefndZRVVjjfjjfjihJBbb@@@';
        let mol = Molecule.fromIDCode(idcode);

        let molfileV3 = mol.toMolfileV3();
        mol = Molecule.fromMolfile(molfileV3);
        mol.getIDCode().should.equal(idcode);

        molfileV3 = mol.toMolfileV3();
        mol = Molecule.fromMolfile(molfileV3);
        mol.getIDCode().should.equal(idcode);
    });
    
});

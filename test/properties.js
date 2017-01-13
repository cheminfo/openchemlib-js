'use strict';

const OCL = require('..');
const Molecule = OCL.Molecule;
const MoleculeProperties = OCL.MoleculeProperties;

describe('MoleculeProperties', function () {
    
    it('should compute properties', function () {
        const mol = Molecule.fromSmiles('COCCON');
        const properties = new MoleculeProperties(mol);
        properties.acceptorCount.should.be.a.Number();
        properties.donorCount.should.be.a.Number();
        properties.logP.should.be.a.Number();
        properties.logS.should.be.a.Number();
        properties.polarSurfaceArea.should.be.a.Number();
        properties.rotatableBondCount.should.be.a.Number();
        properties.stereoCenterCount.should.be.a.Number();
    });

});

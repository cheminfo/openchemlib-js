'use strict';

const OCL = require('../..');

describe('parse wrong SMILES then correct one', function () {
    it('should throw only once', function () {
        OCL.Molecule.fromSmiles('c1ccccc1');
        (function () {
            OCL.Molecule.fromSmiles('c1ccccc');
        }).should.throw(/SmilesParser: dangling ring closure/);
        OCL.Molecule.fromSmiles('c1ccccc1');
    });
});

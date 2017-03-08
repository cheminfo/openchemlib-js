'use strict';

const OCL = require('../..');

const molfile =
`
  -ISIS-  09030813062D

  2  1  0  0  0  0  0  0  0  0999 V2000
    4.8042   -2.8500    0.0000 Na  0  0  0  0  0  0  0  0  0  0  0  0
    5.6292   -2.8500    0.0000 H   0  0  0  0  0  0  0  0  0  0  0  0
  2  1  1  0  0  0  0
M  END
`;

describe('parsing molfile for NaH', function () {
    it('should keep the hydrogen', function () {
        const mol = OCL.Molecule.fromMolfile(molfile);
        mol.getIDCode().should.equal('eFACPhBL@');
    });
});

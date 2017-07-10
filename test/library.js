'use strict';

const core = require('..');
const minimal = require('../minimal');
const full = require('../full');
const pretty = require('../dist/openchemlib-full.pretty');

describe('Checking for the presence of main APIs', function () {

    const minimalAPI = [
        'Molecule',
        'SDFileParser',
        'SSSearcher',
        'SSSearcherWithIndex',
        'Util',
        'version'
    ];

    const coreAPI = [
        'MoleculeProperties',
        'DruglikenessPredictor',
        'DrugScoreCalculator',
        'ToxicityPredictor'
    ];

    const fullAPI = [
        'StructureView',
        'StructureEditor'
    ];

    it('minimal', function () {
        minimal.should.have.properties(minimalAPI);
        minimal.should.not.have.properties(coreAPI);
        minimal.should.not.have.properties(fullAPI);
    });

    it('core', function () {
        core.should.equal(require('..'));
        core.should.have.properties(minimalAPI);
        core.should.have.properties(coreAPI);
        core.should.not.have.properties(fullAPI)
    });

    it('full', function () {
        [full, pretty].forEach((lib) => {
            lib.should.have.properties(minimalAPI);
            lib.should.have.properties(coreAPI);
            lib.should.have.properties(fullAPI)
        });
    })

});

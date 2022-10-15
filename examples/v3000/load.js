'use strict';

const { readFileSync } = require('fs');
const { join } = require('path');

const { Molecule } = require('../../dist/openchemlib-full.pretty');

const molfile = readFileSync(join(__dirname, 'molfile.txt'), 'utf8');
const molecule = Molecule.fromMolfile(molfile);
console.log('molecule', molecule.getAllAtoms());

const molfileNoSGroup = readFileSync(
  join(__dirname, 'molfileNoSGroup.txt'),
  'utf8',
);
const moleculeNoSGroup = Molecule.fromMolfile(molfileNoSGroup);
console.log('moleculeNoSGroup', moleculeNoSGroup.getAllAtoms());

const molfileV2000 = readFileSync(join(__dirname, 'molfileV2000.txt'), 'utf8');
const moleculeV2000 = Molecule.fromMolfile(molfileV2000);
console.log('moleculeV2000', moleculeV2000.getAllAtoms());

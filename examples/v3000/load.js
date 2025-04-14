'use strict';

const { readFileSync } = require('fs');
const { join } = require('path');

const { Molecule } = require('../../lib/index');

const molfile = readFileSync(join(__dirname, 'molfile.txt'), 'utf8');
const molecule = Molecule.fromMolfile(molfile);
console.log('molecule', molecule.getAllAtoms());

const molfileHacked = readFileSync(
  join(__dirname, 'molfile.txt'),
  'utf8',
).replace(
  /M {2}V30 BEGIN SGROUP[\r\n]+(.*[\r\n]+)*M {2}V30 END SGROUP[\r\n]+/,
  '',
);
const moleculeHacked = Molecule.fromMolfile(molfileHacked);
console.log('moleculeHacked', moleculeHacked.getAllAtoms());

const molfileNoSGroup = readFileSync(
  join(__dirname, 'molfileNoSGroup.txt'),
  'utf8',
);
const moleculeNoSGroup = Molecule.fromMolfile(molfileNoSGroup);
console.log('moleculeNoSGroup', moleculeNoSGroup.getAllAtoms());

const molfileV2000 = readFileSync(join(__dirname, 'molfileV2000.txt'), 'utf8');
const moleculeV2000 = Molecule.fromMolfile(molfileV2000);
console.log('moleculeV2000', moleculeV2000.getAllAtoms());

'use strict';

const Benchmark = require('benchmark');

const OCLNew = require('../dist/openchemlib-core');
const OCLOld = require('../distold/openchemlib-core');

const idcode = 'enYXNH@MHDAELem`OCIILdhhdiheCDlieKDdefndZRVVjjfjjfjihJBbb@@@';
const mol = OCLNew.Molecule.fromIDCode(idcode);
const molfile = mol.toMolfile();

const suite = new Benchmark.Suite();

suite
  .add('old', function () {
    OCLNew.Molecule.fromMolfile(molfile);
  })
  .add('new', function () {
    OCLOld.Molecule.fromMolfile(molfile);
  })
  .on('cycle', function (event) {
    console.log(String(event.target));
  })
  .on('complete', function () {
    console.log(`Fastest is ${this.filter('fastest').map('name')}`);
  })
  .run();

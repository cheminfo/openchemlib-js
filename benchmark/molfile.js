/* eslint-disable import/no-named-as-default-member */

import Benchmark from 'benchmark';

import OCLNew from '../dist/openchemlib.js';
import OCLOld from '../distold/openchemlib.js';

const idcode = 'enYXNH@MHDAELem`OCIILdhhdiheCDlieKDdefndZRVVjjfjjfjihJBbb@@@';
const mol = OCLNew.Molecule.fromIDCode(idcode);
const molfile = mol.toMolfile();

const suite = new Benchmark.Suite();

suite
  .add('old', () => {
    OCLNew.Molecule.fromMolfile(molfile);
  })
  .add('new', () => {
    OCLOld.Molecule.fromMolfile(molfile);
  })
  .on('cycle', (event) => {
    console.log(String(event.target));
  })
  .on('complete', function onComplete() {
    console.log(`Fastest is ${suite.filter('fastest').map('name')}`);
  })
  .run();

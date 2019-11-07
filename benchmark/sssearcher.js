'use strict';

const Benchmark = require('benchmark');

const OCLNew = require('../dist/openchemlib-core');
const OCLOld = require('../distold/openchemlib-core');

let benzeneFragmentNew = OCLNew.Molecule.fromSmiles('c1ccccc1');
benzeneFragmentNew.setFragment(true);
let ethylBenzeneNew = OCLNew.Molecule.fromSmiles('CCc1ccccc1');
let searcherNew = new OCLNew.SSSearcher();
searcherNew.setMolecule(ethylBenzeneNew);
searcherNew.setFragment(benzeneFragmentNew);

let benzeneFragmentOld = OCLOld.Molecule.fromSmiles('c1ccccc1');
benzeneFragmentOld.setFragment(true);
let ethylBenzeneOld = OCLOld.Molecule.fromSmiles('CCc1ccccc1');
let searcherOld = new OCLOld.SSSearcher();
searcherOld.setMolecule(ethylBenzeneOld);
searcherOld.setFragment(benzeneFragmentOld);

const suite = new Benchmark.Suite();

suite
  .add('old', function() {
    searcherOld.isFragmentInMolecule();
  })
  .add('new', function() {
    searcherNew.isFragmentInMolecule();
  })
  .on('cycle', function(event) {
    console.log(String(event.target));
  })
  .on('complete', function() {
    console.log(`Fastest is ${this.filter('fastest').map('name')}`);
  })
  .run();

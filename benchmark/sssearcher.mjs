import Benchmark from 'benchmark';

import OCLNew from '../dist/openchemlib.js';
import OCLOld from '../distold/openchemlib.js';

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
  .add('old', () => {
    searcherOld.isFragmentInMolecule();
  })
  .add('new', () => {
    searcherNew.isFragmentInMolecule();
  })
  .on('cycle', (event) => {
    console.log(String(event.target));
  })
  .on('complete', function onComplete() {
    console.log(`Fastest is ${suite.filter('fastest').map('name')}`);
  })
  .run();

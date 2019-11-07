'use strict';

const OCL = require('../minimal');

const Molecule = OCL.Molecule;
const SSSearcher = OCL.SSSearcher;

describe('SSSearcher', function() {
  let benzene = Molecule.fromSmiles('c1ccccc1');
  let benzeneFragment = Molecule.fromSmiles('c1ccccc1');
  benzeneFragment.setFragment(true);
  let ethylBenzene = Molecule.fromSmiles('CCc1ccccc1');
  let allicin = Molecule.fromSmiles('O=S(SC\\C=C)C\\C=C');

  it('should find in itself', function() {
    let searcher = new SSSearcher();
    searcher.setMolecule(benzene);
    searcher.setFragment(benzeneFragment);
    expect(searcher.isFragmentInMolecule()).toBe(true);
  });

  it('should find in larger structure', function() {
    let searcher = new SSSearcher();
    searcher.setMolecule(ethylBenzene);
    searcher.setFragment(benzeneFragment);
    expect(searcher.isFragmentInMolecule()).toBe(true);
  });

  it('should not find in different structure', function() {
    let searcher = new SSSearcher();
    searcher.setMolecule(allicin);
    searcher.setFragment(benzeneFragment);
    expect(searcher.isFragmentInMolecule()).toBe(false);
  });

  it('should work with setMol', function() {
    let searcher = new SSSearcher();
    searcher.setMol(benzeneFragment, benzene);
    expect(searcher.isFragmentInMolecule()).toBe(true);
    searcher.setMol(allicin, benzene);
    expect(searcher.isFragmentInMolecule()).toBe(false);
  });
});

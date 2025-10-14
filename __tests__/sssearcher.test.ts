import { describe, expect, it } from 'vitest';

import { Molecule, SSSearcher } from '../lib/index.js';

describe('SSSearcher', () => {
  const benzene = Molecule.fromSmiles('c1ccccc1');
  const benzeneFragment = Molecule.fromSmiles('c1ccccc1');
  benzeneFragment.setFragment(true);
  const ethylBenzene = Molecule.fromSmiles('CCc1ccccc1');
  const allicin = Molecule.fromSmiles(String.raw`O=S(SC\C=C)C\C=C`);
  it('should find in itself', () => {
    const searcher = new SSSearcher();
    searcher.setMolecule(benzene);
    searcher.setFragment(benzeneFragment);
    expect(searcher.isFragmentInMolecule()).toBe(true);
  });

  it('should find in larger structure', () => {
    const searcher = new SSSearcher();
    searcher.setMolecule(ethylBenzene);
    searcher.setFragment(benzeneFragment);
    expect(searcher.isFragmentInMolecule()).toBe(true);
  });

  it('should not find in different structure', () => {
    const searcher = new SSSearcher();
    searcher.setMolecule(allicin);
    searcher.setFragment(benzeneFragment);
    expect(searcher.isFragmentInMolecule()).toBe(false);
  });

  it('should work with setMol', () => {
    const searcher = new SSSearcher();
    searcher.setMol(benzeneFragment, benzene);
    expect(searcher.isFragmentInMolecule()).toBe(true);
    searcher.setMol(allicin, benzene);
    expect(searcher.isFragmentInMolecule()).toBe(false);
  });

  it.only('find all matches', () => {
    const searcher = new SSSearcher();
    const diketone = Molecule.fromSmiles('CCC(=O)CCC(=O)CC');
    const ketoneFragment = Molecule.fromSmiles('C(=O)');
    ketoneFragment.setFragment(true);
    searcher.setMolecule(diketone);
    searcher.setFragment(ketoneFragment);
    searcher.findFragmentInMolecule();
    const matchList = searcher.getMatchList();
    console.log(matchList);
    expect(matchList.length).toBe(2);
  });
});

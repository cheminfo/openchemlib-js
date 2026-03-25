import { describe, expect, it } from 'vitest';

import { Molecule, SSSearcher } from '#lib';

describe('SSSearcher', () => {
  it('should find in itself', () => {
    const searcher = new SSSearcher();
    searcher.setMolecule(getBenzene());
    searcher.setFragment(getBenzeneFragment());

    expect(searcher.isFragmentInMolecule()).toBe(true);
  });

  it('should find in larger structure', () => {
    const searcher = new SSSearcher();
    searcher.setMolecule(getEthylBenzene());
    searcher.setFragment(getBenzeneFragment());

    expect(searcher.isFragmentInMolecule()).toBe(true);
  });

  it('should not find in different structure', () => {
    const searcher = new SSSearcher();
    searcher.setMolecule(getAllicin());
    searcher.setFragment(getBenzeneFragment());

    expect(searcher.isFragmentInMolecule()).toBe(false);
  });

  it('should work with setMol', () => {
    const searcher = new SSSearcher();
    searcher.setMol(getBenzeneFragment(), getBenzene());

    expect(searcher.isFragmentInMolecule()).toBe(true);

    searcher.setMol(getAllicin(), getBenzene());

    expect(searcher.isFragmentInMolecule()).toBe(false);
  });

  it('find all matches', () => {
    const scn = Molecule.fromSmiles('[15N]#C=[S-]');
    const nitrogen = Molecule.fromSmiles('N');
    nitrogen.setFragment(true);
    const sulfur = Molecule.fromSmiles('S');
    sulfur.setFragment(true);

    let searcher = new SSSearcher({});
    searcher.setMolecule(scn);
    searcher.setFragment(nitrogen);

    expect(searcher.findFragmentInMolecule()).toBe(1);

    // need to match charge
    searcher = new SSSearcher({ matchAtomCharge: true });
    searcher.setMolecule(scn);
    searcher.setFragment(nitrogen);

    expect(searcher.findFragmentInMolecule()).toBe(1);

    searcher.setFragment(sulfur);

    expect(searcher.findFragmentInMolecule()).toBe(0);

    // need to match mass
    searcher = new SSSearcher({ matchAtomMass: true });
    searcher.setMolecule(scn);
    searcher.setFragment(nitrogen);

    expect(searcher.findFragmentInMolecule()).toBe(0);

    searcher.setFragment(sulfur);

    expect(searcher.findFragmentInMolecule()).toBe(1);

    // need to match charge and mass, check if combination works correctly
    searcher = new SSSearcher({ matchAtomMass: true, matchAtomCharge: true });
    searcher.setMolecule(scn);
    searcher.setFragment(nitrogen);

    expect(searcher.findFragmentInMolecule()).toBe(0);

    searcher.setFragment(sulfur);

    expect(searcher.findFragmentInMolecule()).toBe(0);
  });

  it('check mode', () => {
    const searcher = new SSSearcher();
    const diketone = Molecule.fromSmiles('CCC(=O)C(=O)CC');
    const carbonyFragment = Molecule.fromSmiles('C(=O)');
    carbonyFragment.setFragment(true);
    searcher.setMolecule(diketone);
    searcher.setFragment(carbonyFragment);
    const found = searcher.findFragmentInMolecule();

    expect(found).toBe(2);

    const matchList = searcher.getMatchList();

    expect(matchList).toStrictEqual([
      [2, 3],
      [4, 5],
    ]);

    const foundExistence = searcher.findFragmentInMolecule({
      countMode: 'existence',
    });

    expect(foundExistence).toBe(1);

    const foundOverlapping = searcher.findFragmentInMolecule({
      countMode: 'overlapping',
    });

    expect(foundOverlapping).toBe(2);

    const foundSeparated = searcher.findFragmentInMolecule({
      countMode: 'separated',
    });

    expect(foundSeparated).toBe(2);

    const ketoneFragment = Molecule.fromSmiles('CC(=O)C');
    ketoneFragment.setFragment(true);
    searcher.setFragment(ketoneFragment);
    const foundKetoneOverlapping = searcher.findFragmentInMolecule({
      countMode: 'overlapping',
    });

    expect(foundKetoneOverlapping).toBe(2);

    const foundKetoneSeparated = searcher.findFragmentInMolecule({
      countMode: 'separated',
    });

    expect(foundKetoneSeparated).toBe(1);
  });
});

function getBenzene() {
  return Molecule.fromSmiles('c1ccccc1');
}

function getBenzeneFragment() {
  const benzeneFragment = Molecule.fromSmiles('c1ccccc1');
  benzeneFragment.setFragment(true);
  return benzeneFragment;
}

function getEthylBenzene() {
  return Molecule.fromSmiles('CCc1ccccc1');
}

function getAllicin() {
  return Molecule.fromSmiles(String.raw`O=S(SC\C=C)C\C=C`);
}

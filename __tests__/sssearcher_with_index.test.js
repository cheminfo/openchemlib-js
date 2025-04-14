import { expect, test } from 'vitest';

import { Molecule, SSSearcherWithIndex } from '../lib/index.js';

test('getKeyIDCode', () => {
  expect(SSSearcherWithIndex.getKeyIDCode()).toMatchSnapshot();
});

test('should compute tanimoto similarity', () => {
  const {
    benzeneIndex,
    benzeneFragmentIndex,
    ethylBenzeneIndex,
    allicinIndex,
  } = getTestData();
  expect(
    SSSearcherWithIndex.getSimilarityTanimoto(benzeneIndex, benzeneIndex),
  ).toBe(1);
  expect(
    SSSearcherWithIndex.getSimilarityTanimoto(
      benzeneIndex,
      benzeneFragmentIndex,
    ),
  ).toBe(1);
  expect(
    SSSearcherWithIndex.getSimilarityTanimoto(benzeneIndex, ethylBenzeneIndex),
  ).toBe(1 / 3);
  expect(
    SSSearcherWithIndex.getSimilarityTanimoto(benzeneIndex, allicinIndex),
  ).toBeCloseTo(0.0476);
});

test('should compute angle cosine similarity', () => {
  const {
    benzeneIndex,
    benzeneFragmentIndex,
    ethylBenzeneIndex,
    allicinIndex,
  } = getTestData();
  expect(
    SSSearcherWithIndex.getSimilarityAngleCosine(benzeneIndex, benzeneIndex),
  ).toBe(1);
  expect(
    SSSearcherWithIndex.getSimilarityAngleCosine(
      benzeneIndex,
      benzeneFragmentIndex,
    ),
  ).toBe(1);
  expect(
    SSSearcherWithIndex.getSimilarityAngleCosine(
      benzeneIndex,
      ethylBenzeneIndex,
    ),
  ).toBeCloseTo(0.57735);
  expect(
    SSSearcherWithIndex.getSimilarityAngleCosine(benzeneIndex, allicinIndex),
  ).toBeCloseTo(0.18033);
});

test('hex string', () => {
  const { benzeneIndex } = getTestData();
  const hexString = SSSearcherWithIndex.getHexStringFromIndex(benzeneIndex);
  expect(hexString).toHaveLength(128);
  expect(SSSearcherWithIndex.getIndexFromHexString(hexString)).toStrictEqual(
    benzeneIndex,
  );
});

test('bit count', () => {
  expect(SSSearcherWithIndex.bitCount(0)).toBe(0);
  expect(SSSearcherWithIndex.bitCount(0b110011)).toBe(4);
});

test('should find in itself', () => {
  const { benzene, benzeneIndex, benzeneFragment, benzeneFragmentIndex } =
    getTestData();
  const searcher = new SSSearcherWithIndex();
  searcher.setMolecule(benzene, benzeneIndex);
  searcher.setFragment(benzeneFragment, benzeneFragmentIndex);
  expect(searcher.isFragmentInMolecule()).toBe(true);
});

test('should find in larger structure', () => {
  const {
    ethylBenzene,
    ethylBenzeneIndex,
    benzeneFragment,
    benzeneFragmentIndex,
  } = getTestData();
  const searcher = new SSSearcherWithIndex();
  searcher.setMolecule(ethylBenzene, ethylBenzeneIndex);
  searcher.setFragment(benzeneFragment, benzeneFragmentIndex);
  expect(searcher.isFragmentInMolecule()).toBe(true);
});

test('should not find in different structure', () => {
  const { allicin, allicinIndex, benzeneFragment, benzeneFragmentIndex } =
    getTestData();
  const searcher = new SSSearcherWithIndex();
  searcher.setMolecule(allicin, allicinIndex);
  searcher.setFragment(benzeneFragment, benzeneFragmentIndex);
  expect(searcher.isFragmentInMolecule()).toBe(false);
});

function getTestData() {
  const searcher = new SSSearcherWithIndex();

  const benzene = Molecule.fromSmiles('c1ccccc1');
  const benzeneIndex = searcher.createIndex(benzene);

  const benzeneFragment = Molecule.fromSmiles('c1ccccc1');
  benzeneFragment.setFragment(true);
  const benzeneFragmentIndex = searcher.createIndex(benzeneFragment);

  const ethylBenzene = Molecule.fromSmiles('CCc1ccccc1');
  const ethylBenzeneIndex = searcher.createIndex(ethylBenzene);

  const allicin = Molecule.fromSmiles(String.raw`O=S(SC\C=C)C\C=C`);
  const allicinIndex = searcher.createIndex(allicin);
  return {
    benzene,
    benzeneIndex,
    benzeneFragment,
    benzeneFragmentIndex,
    ethylBenzene,
    ethylBenzeneIndex,
    allicin,
    allicinIndex,
  };
}

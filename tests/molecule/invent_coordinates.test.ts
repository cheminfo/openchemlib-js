import { readFileSync } from 'node:fs';
import { join } from 'node:path';
import { isDeepStrictEqual } from 'node:util';

import { assert, expect, test } from 'vitest';

import { Molecule, SmilesParser } from '#lib';

function getMolecule() {
  return Molecule.fromSmiles('CCCC1(CC)C(C)(C)CC(C)CCC1C', {
    noCoordinates: true,
  });
}

function getMolAndInventCoordinates(seed?: number) {
  const molecule = getMolecule();
  molecule.inventCoordinates({ seed });
  return getAllCoordinates(molecule);
}

function readMolfile() {
  return Molecule.fromMolfile(
    readFileSync(
      join(import.meta.dirname, 'data/benzene_with_h_coordinates.mol'),
      'utf8',
    ),
  );
}

function getCoordinates(mol: Molecule, atom: number) {
  return {
    x: mol.getAtomX(atom),
    y: mol.getAtomY(atom),
    z: mol.getAtomZ(atom),
  };
}

function getAllCoordinates(mol: Molecule) {
  const coordinates = [];
  for (let i = 0; i < mol.getAllAtoms(); i++) {
    coordinates.push(getCoordinates(mol, i));
  }
  return coordinates;
}

test('generates new coordinates', () => {
  const mol = getMolecule();
  const beforeCoordinates = getAllCoordinates(mol);

  expect(beforeCoordinates[0].x).toBe(0);
  expect(beforeCoordinates[1].y).toBe(0);

  mol.inventCoordinates();

  const afterCoordinates = getAllCoordinates(mol);

  expect(afterCoordinates[0].x).not.toBe(0);
  expect(afterCoordinates[1].y).not.toBe(0);
});

test('uses seeded generator by default', () => {
  const beforeCoordinates = getMolAndInventCoordinates();
  const afterCoordinates = getMolAndInventCoordinates();

  expect(beforeCoordinates).toStrictEqual(afterCoordinates);
});

test('removes implicit hydrogens by default', () => {
  const mol = readMolfile();

  expect(mol.getAllAtoms()).toBe(12);

  mol.inventCoordinates();

  expect(mol.getAllAtoms()).toBe(6);
  expect(getAllCoordinates(mol)).toStrictEqual(
    getAllCoordinates(Molecule.fromSmiles('c1ccccc1')),
  );
});

test('keeps hydrogens if asked for it', () => {
  const mol = readMolfile();

  expect(mol.getAllAtoms()).toBe(12);

  mol.inventCoordinates({ keepHydrogens: true });

  expect(mol.getAllAtoms()).toBe(12);
});

test('using smiles add addImplicitHydrogens', () => {
  const mol1 = Molecule.fromSmiles('CCO');

  expect(mol1.getAllAtoms()).toBe(3);

  mol1.inventCoordinates();

  expect(mol1.getAllAtoms()).toBe(3);

  mol1.addImplicitHydrogens();

  expect(mol1.getAllAtoms()).toBe(9);

  mol1.inventCoordinates();

  expect(mol1.getAllAtoms()).toBe(3);

  mol1.addImplicitHydrogens();
  mol1.inventCoordinates({ keepHydrogens: true });

  expect(mol1.getAllAtoms()).toBe(9);
});

test('using smiles with explicit hydrogens and invent coordinates with keepHydrogens', () => {
  const smilesParser = new SmilesParser({ makeHydrogenExplicit: true });
  const mol1 = smilesParser.parseMolecule('[CH3]CO');

  expect(mol1.getAllAtoms()).toBe(6);

  mol1.inventCoordinates({ keepHydrogens: true });

  expect(mol1.getAllAtoms()).toBe(6);
});

test('custom seeds', () => {
  const defaultCoordinates = getMolAndInventCoordinates();
  const zeroCoordinates = getMolAndInventCoordinates(0);
  const oneCoordinates1 = getMolAndInventCoordinates(1);
  const oneCoordinates2 = getMolAndInventCoordinates(1);

  // Default seed is zero.
  expect(zeroCoordinates).toStrictEqual(defaultCoordinates);
  expect(oneCoordinates1).not.toStrictEqual(defaultCoordinates);
  // The same seed should generate the same coordinates.
  expect(oneCoordinates2).toStrictEqual(oneCoordinates1);

  // Random seed should eventually generate different coordinates.
  const randomCoordinates1 = getMolAndInventCoordinates(-1);
  let found = false;
  for (let i = 0; i < 100; i++) {
    const randomCoordinates2 = getMolAndInventCoordinates(-1);
    if (!isDeepStrictEqual(randomCoordinates1, randomCoordinates2)) {
      console.log(i);
      found = true;
      break;
    }
  }
  assert(
    found,
    'did not find differing coordinates after trying 100 random seeds',
  );
});

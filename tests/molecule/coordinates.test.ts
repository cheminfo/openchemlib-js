import { expect, test } from 'vitest';

import { Molecule } from '#lib';

function buildMolecule() {
  // fromSmiles assigns a 2D layout, so set every coordinate explicitly.
  const molecule = Molecule.fromSmiles('CCO');
  const positions: Array<[number, number, number]> = [
    [1, 0, 0],
    [0, 0, 0],
    [-1, 0, 0],
  ];
  for (let atom = 0; atom < positions.length; atom++) {
    const [x, y, z] = positions[atom];
    molecule.setAtomX(atom, x);
    molecule.setAtomY(atom, y);
    molecule.setAtomZ(atom, z);
  }
  return molecule;
}

test('getCoordinates returns a plain { x, y, z } copy', () => {
  const molecule = buildMolecule();

  const coordinates = molecule.getCoordinates(0);

  expect(coordinates).toStrictEqual({ x: 1, y: 0, z: 0 });

  // Mutating the returned object must NOT change the molecule.
  coordinates.x = 99;

  expect(molecule.getAtomX(0)).toBe(1);
});

test('setCoordinates writes a { x, y, z } object back to the atom', () => {
  const molecule = buildMolecule();

  molecule.setCoordinates(0, { x: 6, y: 0, z: 5 });

  expect(molecule.getAtomX(0)).toBe(6);
  expect(molecule.getAtomY(0)).toBe(0);
  expect(molecule.getAtomZ(0)).toBe(5);
});

test('translate moves every atom in 3D', () => {
  const molecule = buildMolecule();
  molecule.translate({ x: 1, y: 2, z: 3 });

  expect(molecule.getAtomX(0)).toBeCloseTo(2);
  expect(molecule.getAtomZ(0)).toBeCloseTo(3);
  expect(molecule.getAtomZ(2)).toBeCloseTo(3);
});

test('rotate rotates every atom in 3D', () => {
  const molecule = buildMolecule();
  // 90° about Z in OpenChemLib's row-vector convention:
  // (1, 0, 0) -> (0, 1, 0) and (-1, 0, 0) -> (0, -1, 0).
  molecule.rotate([
    [0, 1, 0],
    [-1, 0, 0],
    [0, 0, 1],
  ]);

  expect(molecule.getAtomX(0)).toBeCloseTo(0);
  expect(molecule.getAtomY(0)).toBeCloseTo(1);
  expect(molecule.getAtomX(2)).toBeCloseTo(0);
  expect(molecule.getAtomY(2)).toBeCloseTo(-1);
});

test('center and getCenterOfGravity', () => {
  const molecule = buildMolecule();

  // (1 + 0 + -1) / 3 = 0
  expect(molecule.getCenterOfGravity().x).toBeCloseTo(0);

  molecule.center();
  const centered = molecule.getCenterOfGravity();

  expect(centered.x).toBeCloseTo(0);
  expect(centered.y).toBeCloseTo(0);
  expect(centered.z).toBeCloseTo(0);
});

test('getCenterOfGravity returns the origin for an empty molecule', () => {
  const molecule = new Molecule(0, 0);

  expect(molecule.getCenterOfGravity()).toStrictEqual({ x: 0, y: 0, z: 0 });
});

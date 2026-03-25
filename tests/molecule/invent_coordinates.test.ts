import { expect, test } from 'vitest';

import { Molecule } from '#lib';

function getMolecule() {
  return Molecule.fromSmiles('COCCON', { noCoordinates: true });
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

test('inventCoordinates default behavior', () => {
  const mol = getMolecule();
  const beforeCoordinates = getAllCoordinates(mol);

  expect(beforeCoordinates[0].x).toBe(0);
  expect(beforeCoordinates[1].y).toBe(0);

  mol.inventCoordinates();

  const afterCoordinates = getAllCoordinates(mol);

  expect(afterCoordinates[0].x).not.toBe(0);
  expect(afterCoordinates[1].y).not.toBe(0);
});

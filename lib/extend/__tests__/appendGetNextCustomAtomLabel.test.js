import { expect, test } from 'vitest';

import { Molecule } from '../../index.js';

test('just numbers', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(0, '1');
  molecule.setAtomCustomLabel(1, '2');
  molecule.setAtomCustomLabel(2, '3');
  expect(molecule.getNextCustomAtomLabel('')).toBe('4');
  expect(molecule.getNextCustomAtomLabel('4')).toBe('4');
  expect(molecule.getNextCustomAtomLabel('a')).toBe('a');
  expect(molecule.getNextCustomAtomLabel('z')).toBe('z');
});

test('just lowercase', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(0, 'a');
  molecule.setAtomCustomLabel(1, 'b');
  molecule.setAtomCustomLabel(2, 'd');
  expect(molecule.getNextCustomAtomLabel('')).toBe('1');
  expect(molecule.getNextCustomAtomLabel('a')).toBe('c');
  expect(molecule.getNextCustomAtomLabel('d')).toBe('e');
  expect(molecule.getNextCustomAtomLabel('1')).toBe('1');
  expect(molecule.getNextCustomAtomLabel('2')).toBe('2');
});

test('lower / uppercase', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(0, 'a');
  molecule.setAtomCustomLabel(1, 'A');
  molecule.setAtomCustomLabel(2, 'd');
  expect(molecule.getNextCustomAtomLabel('')).toBe('1');
  expect(molecule.getNextCustomAtomLabel('a')).toBe('b');
  expect(molecule.getNextCustomAtomLabel('A')).toBe('B');
  expect(molecule.getNextCustomAtomLabel('1')).toBe('1');
  expect(molecule.getNextCustomAtomLabel('2')).toBe('2');
});

test('mixed', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(0, '1a');
  molecule.setAtomCustomLabel(1, '2b');
  molecule.setAtomCustomLabel(2, '3d');
  expect(molecule.getNextCustomAtomLabel('')).toBe('1');
  expect(molecule.getNextCustomAtomLabel('1a')).toBe('2a');
  expect(molecule.getNextCustomAtomLabel('3a')).toBe('3a');
});

test('mixed with letter before', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(0, 'a1');
  molecule.setAtomCustomLabel(1, 'a2');
  molecule.setAtomCustomLabel(2, '3d');
  expect(molecule.getNextCustomAtomLabel('')).toBe('1');
  expect(molecule.getNextCustomAtomLabel('a1')).toBe('a3');
  expect(molecule.getNextCustomAtomLabel('3a')).toBe('3a');
  expect(molecule.getNextCustomAtomLabel('3d')).toBe('4d');
});

test('with exotic characters', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(0, `a'`);
  molecule.setAtomCustomLabel(2, `b'`);
  expect(molecule.getNextCustomAtomLabel(`a'`)).toBe(`c'`);
  expect(molecule.getNextCustomAtomLabel(`c'`)).toBe(`c'`);
});

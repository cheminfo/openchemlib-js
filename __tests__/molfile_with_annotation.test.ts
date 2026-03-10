import { readFileSync } from 'node:fs';
import { join } from 'node:path';

import { expect, test } from 'vitest';

import { Canonizer, Molecule } from '../lib';

test('Molecule with annotation', () => {
  const filename = join(import.meta.dirname, 'data', 'molfile_annotated.mol');
  const molfile = readFileSync(filename, 'utf8');
  const molecule = Molecule.fromMolfile(molfile);
  expect(molecule.getAllAtoms()).toBe(16);
  const customLabels = [];
  for (let i = 0; i < 16; i++) {
    customLabels.push(molecule.getAtomCustomLabel(i));
  }
  const expected = [
    ']8α',
    ']10α',
    ']10',
    ']4α',
    ']9α',
    ']9',
    ']8',
    ']7',
    ']6',
    ']5',
    null,
    null,
    ']4',
    ']3',
    ']2',
    ']1',
  ];
  expect(customLabels).toStrictEqual(expected);

  const canonizer = new Canonizer(molecule, {
    encodeAtomCustomLabels: true,
  });
  const idCode = canonizer.getIDCode();

  const molecule2 = Molecule.fromIDCode(idCode);
  const customLabels2 = [];
  for (let i = 0; i < 16; i++) {
    customLabels2.push(molecule2.getAtomCustomLabel(i));
  }

  const expected2 = [
    null,
    ']9',
    ']9α',
    ']8α',
    ']4α',
    ']1',
    ']10α',
    ']8',
    ']10',
    ']4',
    ']2',
    ']5',
    ']7',
    null,
    ']3',
    ']6',
  ];
  expect(customLabels2).toStrictEqual(expected2);
});

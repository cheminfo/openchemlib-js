import { readFileSync } from 'node:fs';
import { join } from 'node:path';

import { expect, test } from 'vitest';

import { Molecule } from '../../index.js';

test('with V', () => {
  const molfile = `
ChemDraw09292517232D

  7  7  0  0  0  0  0  0  0  0999 V2000
   -1.0717    0.4125    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
   -1.0717   -0.4125    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
   -0.3572   -0.8250    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    0.3572   -0.4125    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    0.3572    0.4125    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
   -0.3572    0.8250    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    1.0717    0.8250    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0
  1  2  1  0        0
  2  3  1  0        0
  3  4  1  0        0
  4  5  1  0        0
  5  6  1  0        0
  6  1  1  0        0
  5  7  1  0        0
V    1 5'
V    2 6'
V    5 3
V    6 4'
V    7 2
M  END`;
  const molecule = Molecule.fromMolfile(molfile, {
    customLabelPosition: 'superscript',
  });
  const customAtomLabels = [];
  for (let i = 0; i < molecule.getAllAtoms(); i++) {
    customAtomLabels.push(molecule.getAtomCustomLabel(i));
  }

  expect(customAtomLabels).toHaveLength(7);
  expect(customAtomLabels).toStrictEqual([
    "]5'",
    "]6'",
    null,
    null,
    ']3',
    "]4'",
    ']2',
  ]);

  const molecule2 = Molecule.fromMolfile(molfile, {
    customLabelPosition: 'auto',
  });

  const customAtomLabels2 = [];
  for (let i = 0; i < molecule2.getAllAtoms(); i++) {
    customAtomLabels2.push(molecule2.getAtomCustomLabel(i));
  }

  expect(customAtomLabels2).toHaveLength(7);
  expect(customAtomLabels2).toStrictEqual([
    "5'",
    "6'",
    null,
    null,
    '3',
    "4'",
    ']2',
  ]);
});

test('with A', () => {
  const molfile = `structure.mol
CTNMRSCT0105132519343D 1       1.0         0.0     0

 13 12  0  0  0  0  0  0  0  0999 V2000
    0.7095   -0.5612    0.5739 C   0  0  0  0  0  0  0  0  0  0  0  0
    2.1383   -0.0770    0.4147 C   0  0  0  0  0  0  0  0  0  0  0  0
   -1.4706    0.0126   -0.0034 C   0  0  0  0  0  0  0  0  0  0  0  0
   -2.4345    0.8462   -0.3955 C   0  0  0  0  0  0  0  0  0  0  0  0
   -0.1497    0.3428   -0.1239 O   0  0  0  0  0  0  0  0  0  0  0  0
    0.6141   -1.5691    0.1525 H   0  0  0  0  0  0  0  0  0  0  0  0
    0.4458   -0.5850    1.6381 H   0  0  0  0  0  0  0  0  0  0  0  0
    2.8353   -0.7408    0.9338 H   0  0  0  0  0  0  0  0  0  0  0  0
    2.2491    0.9358    0.8161 H   0  0  0  0  0  0  0  0  0  0  0  0
    2.4146   -0.0323   -0.6440 H   0  0  0  0  0  0  0  0  0  0  0  0
   -1.6811   -0.9633    0.4137 H   0  0  0  0  0  0  0  0  0  0  0  0
   -2.1909    1.8158   -0.8170 H   0  0  0  0  0  0  0  0  0  0  0  0
   -3.4799    0.5753   -0.3038 H   0  0  0  0  0  0  0  0  0  0  0  0
  2  8  1  0  0  0  0
  2  9  1  0  0  0  0
  2 10  1  0  0  0  0
  1  6  1  0  0  0  0
  1  7  1  0  0  0  0
  4 12  1  0  0  0  0
  4 13  1  0  0  0  0
  3 11  1  0  0  0  0
  2  1  1  0  0  0  0
  1  5  1  0  0  0  0
  5  3  1  0  0  0  0
  4  3  2  0  0  0  0
A    1
C1
A    2
C2
A    3
C3
A    4
C4
A    5
O5
A    6
H1A
A    7
H1B
A    8
H2A
A    9
H2B
A   10
H2C
A   11
H3
A   12
H4A
A   13
H4B
M  END
$$$$
`;
  const molecule = Molecule.fromMolfile(molfile);
  const customAtomLabels = [];
  for (let i = 0; i < molecule.getAllAtoms(); i++) {
    customAtomLabels.push(molecule.getAtomCustomLabel(i));
  }

  expect(customAtomLabels).toHaveLength(13);
  expect(customAtomLabels).toStrictEqual([
    'C1',
    'C2',
    'C3',
    'C4',
    'O5',
    'H1A',
    'H1B',
    'H2A',
    'H2B',
    'H2C',
    'H3',
    'H4A',
    'H4B',
  ]);
});

test('Mestrec format from windows', () => {
  let molfile = readFileSync(
    join(import.meta.dirname, 'data/mestrec.mol'),
    'utf8',
  );
  // molfile of mestrec is unconventional and put the custom labels in the field 'M  ZZC'
  molfile = molfile.replaceAll('M  ZZC ', 'V  ');
  const molecule = Molecule.fromMolfile(molfile);
  const customAtomLabels = [];
  for (let i = 0; i < molecule.getAllAtoms(); i++) {
    customAtomLabels.push(molecule.getAtomCustomLabel(i));
  }
  expect(customAtomLabels).toStrictEqual([
    '15',
    '10',
    '9',
    '8',
    '7',
    '1',
    '6',
    '5',
    '4',
    '3',
    '2',
    null,
    '11',
    '12',
    '13',
    '13OH',
    '14',
    '16',
    null,
    '2OCH3',
  ]);
});

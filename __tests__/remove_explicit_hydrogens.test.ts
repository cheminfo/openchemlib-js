import { expect, test } from 'vitest';

import { Molecule } from '../lib/index.js';

test('removeExplicitHydrogens', () => {
  const molfile = `cytisine
Actelion Java MolfileCreator 1.0

 14 16  0  0  1  0  0  0  0  0999 V2000
   -0.6573   -0.3901    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0
   -0.6573    1.1073    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
   -1.9721   -1.1285    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    0.6504    1.8526    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    0.6433   -1.1285    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
   -1.9721    1.8526    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    3.2587    1.8526    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0
    1.9370    1.0652    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    1.9370   -0.3691    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
   -3.2587    1.0723    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
   -3.2587   -0.3901    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
   -1.9721   -2.6470    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0
    1.9932    2.6470    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    3.2587    0.3339    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
  2  1  1  0  0  0  0
  3  1  1  0  0  0  0
  4  2  1  0  0  0  0
  5  1  1  0  0  0  0
  6  2  2  0  0  0  0
  7 14  1  0  0  0  0
  9  8  1  6  0  0  0
  9  5  1  0  0  0  0
 10 11  2  0  0  0  0
 11  3  1  0  0  0  0
 12  3  2  0  0  0  0
 13  4  1  0  0  0  0
 14  9  1  0  0  0  0
  4  8  1  6  0  0  0
  6 10  1  0  0  0  0
 13  7  1  0  0  0  0
M  END
`;

  const molecule = Molecule.fromMolfile(molfile);

  const molfile1 = molecule.toMolfile();
  expect(molfile1).toMatchSnapshot();

  molecule.removeExplicitHydrogens(false);
  const molfile2 = molecule.toMolfile();
  expect(molfile2).toMatchSnapshot();

  expect(molfile1).toStrictEqual(molfile2);
});

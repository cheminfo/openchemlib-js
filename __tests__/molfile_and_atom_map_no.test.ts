import { readFileSync } from 'node:fs';

import { expect, test } from 'vitest';

import { Molecule } from '../lib/index.js';

test('molfile with atomMapNo', () => {
  const molfile = readFileSync(
    `${import.meta.dirname}/data/atomMapNo.mol`,
    'utf8',
  );
  const molecule = Molecule.fromMolfile(molfile);

  const newMolfile = molecule.toMolfile();
  const atomMapNo = newMolfile
    .split(/\r?\n/)
    .filter((line) => line.match(/ [CHO] /))
    .map((line) =>
      line.replace(/.* (?<och>[CHO]) .*(?<n>.) {2}0 {2}0$/, '$<och> $<n>'),
    );

  expect(atomMapNo).toStrictEqual(['O 5', 'C 1', 'C 3', 'C 4', 'H 2']);

  const svg = molecule.toSVG(300, 200);
  const mapNos = svg
    .split(/\r?\n/)
    .filter((line) => line.includes('data-atom-map'))
    .map((line) =>
      line.replace(/.*atom-map-no="(?<atom_map_no>.).*/, '$<atom_map_no>'),
    );
  expect(mapNos).toStrictEqual(['5', '1', '3', '4', '2']);
});

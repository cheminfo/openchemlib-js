'use strict';

const { readFileSync } = require('fs');

const { Molecule } = require('../minimal');



test('molfile with atomMapNo', () => {
  const molfile = readFileSync(
    `${__dirname}/data/atomMapNo.mol`,
    'utf8',
  );
  const molecule = Molecule.fromMolfile(molfile);

  const newMolfile = molecule.toMolfile();
  const atomMapNo = newMolfile.split(/\r?\n/).filter((line) => line.match(/ [OCH] /))
    .map(line => line.replace(/.* ([OCH]) .*(.) {2}0 {2}0$/, '$1 $2'));
  expect(atomMapNo).toStrictEqual(['O 5', 'C 1', 'C 3', 'C 4', 'H 2'])

  const svg = molecule.toSVG(300, 200);
  const mapNos = svg.split(/\r?\n/).filter((line) => line.includes('data-atom-map')).map(line => line.replace(/.*atom-map-no="(.).*/, '$1'));
  expect(mapNos).toStrictEqual(["5", "1", "3", "4", "2"])



})
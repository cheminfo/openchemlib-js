'use strict';

const { getDiastereotopicAtomIDs } = require('openchemlib-utils');

const { Molecule, version } = require('../full');

async function doAll() {
  console.log('OCL version', version);
  console.time('diastereoID');
  const response = await fetch(
    'https://wikipedia.cheminfo.org/src/json/data.json',
  );
  const entries = (await response.json()).data.molecules;
  let totalLength = 0;
  for (const entry of entries.slice(0, 700)) {
    const molecule = Molecule.fromIDCode(entry.actID.value);
    const ids = getDiastereotopicAtomIDs(molecule);
    totalLength += ids.length;
  }
  console.timeEnd('diastereoID');
  console.log(totalLength);
}

doAll();

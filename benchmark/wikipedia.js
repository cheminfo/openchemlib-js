'use strict';

const { Molecule } = require('../dist/openchemlib-full.pretty');

async function doAll() {
  console.time('wikipedia');
  const response = await fetch(
    'https://wikipedia.cheminfo.org/src/json/data.json',
  );
  const entries = (await response.json()).data.molecules;
  const idCodes = [];
  for (const entry of entries.slice(0, 2000)) {
    const molecule = Molecule.fromIDCode(entry.actID.value);
    const idCode = molecule.getIDCode();
    idCodes.push(idCode);
  }
  console.timeEnd('wikipedia');
  console.log(idCodes.join(',').length);
}

doAll();

import { getDiastereotopicAtomIDs } from 'openchemlib-utils';

import { Molecule, version } from '../lib/index.js';

console.log('OCL version', version);
console.time('diastereoID');
const response = await fetch('https://wikipedia.cheminfo.org/data.json');
const { data } = await response.json();
let totalLength = 0;
for (const entry of data.molecules.slice(0, 700)) {
  const molecule = Molecule.fromIDCode(entry.idCode);
  const ids = getDiastereotopicAtomIDs(molecule);
  totalLength += ids.length;
}
console.timeEnd('diastereoID');
console.log(totalLength);

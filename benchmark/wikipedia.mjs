import { Molecule, version } from '../lib/index.js';

console.log('OCL version', version);
console.time('wikipedia');
const response = await fetch('https://wikipedia.cheminfo.org/data.json');
const { data } = await response.json();
const idCodes = [];
for (const entry of data.molecules.slice(0, 10000)) {
  const molecule = Molecule.fromIDCode(entry.idCode);
  const idCode = molecule.getIDCode();
  idCodes.push(idCode);
}
console.timeEnd('wikipedia');
console.log(idCodes.join(',').length);

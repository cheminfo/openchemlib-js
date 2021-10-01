import { readFile } from 'fs/promises';
import IDCodeParser from './IDCodeParser.js';
import OCL from '../dist/openchemlib-full.js';

const parser = new IDCodeParser(false);
function parseNew(idcode) {
  return parser.getCompactMolecule(idcode);
}

function parseOld(idcode) {
  return OCL.Molecule.fromIDCode(idcode, false);
}

const what = process.argv[2];
console.log(what);

let func;
if (what === 'new') {
  func = parseNew;
} else if (what === 'old') {
  func = parseOld;
} else {
  throw new Error('unknown mode');
}

const file = await readFile('experiment/idcode.txt', 'utf-8');
const lines = file.split('\n');
const idcodes = lines.map((line) => line.split('\t')[0]);

console.log(`time to parse ${idcodes.length} idcodes`);

let total = 0;
console.time('time');
for (const idcode of idcodes) {
  if (idcode.length === 0) continue;
  const result = func(idcode);
  total += result.getAllAtoms();
}
console.timeEnd('time');
console.log(total + ' atoms');

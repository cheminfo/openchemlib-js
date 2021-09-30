import { readFile } from 'fs/promises';
import IDCodeParser from './IDCodeParser.js';
import OCL from '../dist/openchemlib-full.js';

function parseNew(idcode) {
  return new IDCodeParser(false).getCompactMolecule(idcode);
}

function parseOld(idcode) {
  OCL.Molecule.fromIDCode(idcode, false);
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

console.time('time');
for (const idcode of idcodes) {
  func(idcode);
}
console.timeEnd('time');

import { cpSync, readFileSync, rmSync } from 'node:fs';
import path from 'node:path';

const toCopy = [
  'cod/torsionID.txt',
  'cod/torsionAngle.txt',
  'cod/torsionRange.txt',
  'cod/torsionFrequency.txt',
  'cod/bondLengthData.txt',
  'forcefield/mmff94/angle.csv',
  'forcefield/mmff94/pbci.csv',
  'forcefield/mmff94/bci.csv',
  'forcefield/mmff94/atom.csv',
  'forcefield/mmff94/bndk.csv',
  'forcefield/mmff94/bond.csv',
  'forcefield/mmff94/covrad.csv',
  'forcefield/mmff94/dfsb.csv',
  'forcefield/mmff94/def.csv',
  'forcefield/mmff94/herschbachlaurie.csv',
  'forcefield/mmff94/outofplane.csv',
  'forcefield/mmff94/stbn.csv',
  'forcefield/mmff94/torsion.csv',
  'mmff94/vanderwaals.csv',
  'druglikenessNoIndex.txt',
  'toxpredictor/m1.txt',
  'toxpredictor/t1.txt',
  'toxpredictor/i1.txt',
  'toxpredictor/r1.txt',
  'toxpredictor/m2.txt',
  'toxpredictor/t2.txt',
  'toxpredictor/i2.txt',
  'toxpredictor/r2.txt',
  'toxpredictor/m3.txt',
  'toxpredictor/t3.txt',
  'toxpredictor/i3.txt',
  'toxpredictor/r3.txt',
].map((p) => p.replaceAll('/', path.sep));

const source = path.join(import.meta.dirname, '../src/resources/resources');
const destination = path.join(import.meta.dirname, '../dist/resources');

rmSync(destination, { recursive: true, force: true });
cpSync(source, destination, {
  recursive: true,
  filter(source) {
    if (path.extname(source) === '') return true;
    if (toCopy.some((resource) => source.endsWith(resource))) {
      verifyAscii(source);
      return true;
    }
  },
});

function verifyAscii(source) {
  const contents = readFileSync(source);
  for (const byte of contents) {
    if (byte > 127) {
      throw new Error(`Source is not ASCII: ${source}`);
    }
  }
}

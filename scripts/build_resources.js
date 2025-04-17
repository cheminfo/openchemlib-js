import fs from 'node:fs';
import path from 'node:path';

const resourcesToBundle = [
  'cod/bondLengthData.txt',
  'cod/torsionAngle.txt',
  'cod/torsionFrequency.txt',
  'cod/torsionID.txt',
  'cod/torsionRange.txt',
  'forcefield/mmff94/94s/outofplane.csv',
  'forcefield/mmff94/94s/torsionPlus.csv',
  'forcefield/mmff94/94s/torsion.csv',
  'forcefield/mmff94/angle.csv',
  'forcefield/mmff94/atom.csv',
  'forcefield/mmff94/bci.csv',
  'forcefield/mmff94/bndk.csv',
  'forcefield/mmff94/bond.csv',
  'forcefield/mmff94/covrad.csv',
  'forcefield/mmff94/def.csv',
  'forcefield/mmff94/dfsb.csv',
  'forcefield/mmff94/herschbachlaurie.csv',
  'forcefield/mmff94/outofplane.csv',
  'forcefield/mmff94/pbci.csv',
  'forcefield/mmff94/stbn.csv',
  'forcefield/mmff94/torsion.csv',
  'forcefield/mmff94/vanderwaals.csv',
  'druglikenessNoIndex.txt',
  'toxpredictor/i1.txt',
  'toxpredictor/i2.txt',
  'toxpredictor/i3.txt',
  'toxpredictor/m1.txt',
  'toxpredictor/m2.txt',
  'toxpredictor/m3.txt',
  'toxpredictor/r1.txt',
  'toxpredictor/r2.txt',
  'toxpredictor/r3.txt',
  'toxpredictor/t1.txt',
  'toxpredictor/t2.txt',
  'toxpredictor/t3.txt',
];

const source = path.join(import.meta.dirname, '../src/resources/resources');
const destination = path.join(import.meta.dirname, '../dist/resources.json');

const resourcesJson = ['{'];
for (const resourceName of resourcesToBundle) {
  const contents = fs.readFileSync(path.join(source, resourceName), 'utf8');
  verifyAscii(resourceName, contents);
  resourcesJson.push(
    `  "/resources/${resourceName}": ${JSON.stringify(contents)},`,
  );
}

// Remove the last `,`.
resourcesJson[resourcesJson.length - 1] = resourcesJson.at(-1).slice(0, -1);
resourcesJson.push('}\n');

fs.mkdirSync(path.dirname(destination), { recursive: true });
fs.writeFileSync(destination, resourcesJson.join('\n'));

function verifyAscii(resourceName, contents) {
  for (const byte of contents) {
    if (byte > 127) {
      throw new Error(`Resource is not ASCII: ${resourceName}`);
    }
  }
}

import fs from 'node:fs';
import path from 'node:path';

const folder = 'main/resources/resources/forcefield/mmff94';
const files = [
  'angle',
  'atom',
  'bci',
  'bndk',
  'bond',
  'covrad',
  'def',
  'dfsb',
  'herschbachlaurie',
  'outofplane',
  'pbci',
  'stbn',
  'torsion',
  'vanderwaals',
  '94s/outofplane',
  '94s/torsion',
  '94s/torsionPlus',
];

const start = `package com.actelion.research.chem.forcefield.mmff;

public class CsvData {

`;

const end = `
}
`;

export function generateCsvData() {
  const csvData = [start];
  for (const file of files) {
    const contents = fs.readFileSync(
      path.join(
        import.meta.dirname,
        '../../openchemlib/src',
        folder,
        `${file}.csv`,
      ),
      'utf8',
    );
    csvData.push(
      `\npublic static final String ${file.replace(
        '94s/',
        'n94s_',
      )}Data = ${JSON.stringify(contents)};\n`,
    );
  }
  csvData.push(end);
  return csvData.join('');
}

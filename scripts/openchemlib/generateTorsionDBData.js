'use strict';

const fs = require('node:fs');
const path = require('node:path');

const folder = 'main/resources/resources/cod';
const files = [
  'torsionID',
  'torsionAngle',
  'torsionRange',
  'torsionFrequency',
  'torsionBins',
  'bondLengthData',
];

const start = `package com.actelion.research.chem.conf;

public class TorsionDBData {

`;

const end = `
}
`;

function generateTorsionDBData() {
  const torsionDBData = [start];
  for (const file of files) {
    const contents = fs.readFileSync(
      path.join(__dirname, '../../openchemlib/src', folder, `${file}.txt`),
      'utf8',
    );
    const lines = contents
      .split('\n')
      .filter((l) => l.length > 0)
      .join('\n');
    torsionDBData.push(
      `
public static final String[] get${file}Data() {
  String data = ${JSON.stringify(lines)};
  String[] result = data.split("\\n");
  return result;
}
`,
    );
  }
  torsionDBData.push(end);
  return torsionDBData.join('');
}

module.exports = generateTorsionDBData;

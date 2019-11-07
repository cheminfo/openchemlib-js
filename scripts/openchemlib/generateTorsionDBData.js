'use strict';

const fs = require('fs');
const path = require('path');

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

function generateTorsionDBData({ config }) {
  const torsionDBData = [start];
  for (const file of files) {
    const contents = fs.readFileSync(
      path.join(config.openchemlib, folder, `${file}.txt`),
      'utf8',
    );
    const lines = contents.split('\n').filter((l) => l.length > 0);
    const linesArray = lines.map((line) => JSON.stringify(line)).join(', ');
    torsionDBData.push(
      `\npublic static final String[] get${file}Data() { String[] result = { ${linesArray} }; return result; }\n`,
    );
  }
  torsionDBData.push(end);
  return torsionDBData.join('');
}

module.exports = generateTorsionDBData;

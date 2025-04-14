import fs from 'node:fs';
import path from 'node:path';

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

export function generateTorsionDBData() {
  const torsionDBData = [start];
  for (const file of files) {
    const contents = fs.readFileSync(
      path.join(
        import.meta.dirname,
        '../../openchemlib/src',
        folder,
        `${file}.txt`,
      ),
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

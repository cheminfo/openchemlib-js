import { cpSync, rmSync } from 'node:fs';
import path from 'node:path';

const toCopy = [
  'cod/torsionID.txt',
  'cod/torsionAngle.txt',
  'cod/torsionRange.txt',
  'cod/torsionFrequency.txt',
];

const source = path.join(
  import.meta.dirname,
  '../openchemlib/src/main/resources/resources',
);
const destination = path.join(import.meta.dirname, '../dist/resources');

rmSync(destination, { recursive: true, force: true });
cpSync(source, destination, {
  recursive: true,
  filter(source) {
    return (
      path.extname(source) === '' ||
      toCopy.some((resource) => source.endsWith(resource))
    );
  },
});

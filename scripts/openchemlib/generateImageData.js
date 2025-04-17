import fs from 'node:fs';
import path from 'node:path';

import { decode } from 'fast-png';

const resourcesDir = path.join(
  import.meta.dirname,
  '../../src/resources/images',
);

const images = ['editorButtons.png', 'esrButtons.png'];

const start = `package com.actelion.research.gwt.js.api.generic.internal;

public class ImageData {
`;

const end = `
}
`;

export function generateImageData() {
  const imageData = [start];
  for (const image of images) {
    const contents = fs.readFileSync(path.join(resourcesDir, image));
    const png = decode(contents);
    const name = image.replace('.png', '');
    const base64 = Buffer.from(png.data).toString('base64');
    const compressed = base64.replaceAll(/A+/g, (match) => {
      const n = match.length;
      return n < 4 ? match : `%${n}%`;
    });
    const compressedSplit = [];
    for (let i = 0; i < compressed.length; i += 50_000) {
      compressedSplit.push(compressed.slice(i, i + 50_000));
    }
    imageData.push(
      `
public static final int ${name}Width = ${png.width};
public static final int ${name}Height = ${png.height};
${compressedSplit
  .map((s, i) => `public static final String ${name}Data${i} = "${s}";`)
  .join('\n')}
`,
    );
  }
  imageData.push(end);
  return imageData.join('\n');
}

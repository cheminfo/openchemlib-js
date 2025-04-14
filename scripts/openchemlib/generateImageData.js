'use strict';

const fs = require('node:fs');
const path = require('node:path');

const { decode } = require('fast-png');

const resourcesDir = path.join(
  __dirname,
  '../../openchemlib/src/main/resources/images',
);

const images = ['editorButtons.png', 'esrButtons.png'];

const start = `package com.actelion.research.gwt.js.api.generic.internal;

public class ImageData {
`;

const end = `
}
`;

function generateImageData() {
  const imageData = [start];
  for (const image of images) {
    const contents = fs.readFileSync(path.join(resourcesDir, image));
    const png = decode(contents);
    const name = image.replace('.png', '');
    const base64 = Buffer.from(png.data).toString('base64');
    const compressed = base64.replaceAll('A'.repeat(20), '%');
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

module.exports = generateImageData;

import { readdir, readFile, writeFile } from 'fs/promises'

const homedir = new URL('../openchemlib/src/main/resources/html/', import.meta.url)

// loading the images
const pngNames = await readdir(new URL('editor', homedir)).then(files => files.filter(file => file.endsWith('.png')))
const images = {}
for (const pngName of pngNames) {
  const blob = await readFile(new URL(`editor/${pngName}`, homedir))
  const encoded = toBase64URL(blob, 'image/png')
  images[pngName] = encoded
}

// loading the html file
let html = await readFile(new URL('editor/editor.html', homedir), 'utf8')
for (const [pngName, encoded] of Object.entries(images)) {
  html = html.replaceAll(`src="${pngName}"`, `src="${encoded}"`)
}

// loading css
let css = await readFile(new URL('styles.css', homedir), 'utf8')
html = html.replace('<link type="text/css" href="../styles.css" rel="stylesheet">', `<style>${css}</style>`)

await writeFile(new URL('../help.html', import.meta.url), html)

/*
 * base64-arraybuffer
 * https://github.com/niklasvh/base64-arraybuffer
 *
 * Copyright (c) 2012 Niklas von Hertzen
 * Licensed under the MIT license.
 */

function encode(bytes) {

  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/';

  // Use a lookup table to find the index.
  const lookup = new Uint8Array(256);
  for (let i = 0; i < chars.length; i++) {
    lookup[chars.charCodeAt(i)] = i;
  }

  let i;
  let len = bytes.length;
  let base64 = '';

  for (i = 0; i < len; i += 3) {
    base64 += chars[bytes[i] >> 2];
    base64 += chars[((bytes[i] & 3) << 4) | (bytes[i + 1] >> 4)];
    base64 += chars[((bytes[i + 1] & 15) << 2) | (bytes[i + 2] >> 6)];
    base64 += chars[bytes[i + 2] & 63];
  }

  if (len % 3 === 2) {
    base64 = `${base64.substring(0, base64.length - 1)}=`;
  } else if (len % 3 === 1) {
    base64 = `${base64.substring(0, base64.length - 2)}==`;
  }

  return base64;
}

function toBase64URL(bytes, type) {
  const base64 = encode(bytes);
  return `data:${type};base64,${base64}`;
}

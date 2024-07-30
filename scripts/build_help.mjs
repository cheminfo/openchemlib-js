import { readdir, readFile, writeFile } from 'fs/promises'

const homedir = new URL('../openchemlib/src/main/resources/html/', import.meta.url)

// loading the images
const pngNames = await readdir(new URL('editor', homedir)).then(files => files.filter(file => file.endsWith('.png')))
const images = {}
for (const pngName of pngNames) {
  const blob = await readFile(new URL(`editor/${pngName}`, homedir), 'base64')
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

function toBase64URL(base64, type) {
  return `data:${type};base64,${base64}`;
}

<h3 align="center">
  <a href="https://www.zakodium.com">
    <img src="https://www.zakodium.com/brand/zakodium-logo-white.svg" width="50" alt="Zakodium logo" />
  </a>
  <p>
    Maintained by <a href="https://www.zakodium.com">Zakodium</a>
  </p>
</h3>

# OpenChemLib JS

[![NPM version][npm-image]][npm-url]
[![npm download][download-image]][download-url]
[![license][license-image]][license-url]
[![DOI](https://www.zenodo.org/badge/23346814.svg)](https://www.zenodo.org/badge/latestdoi/23346814)

JavaScript port of the [OpenChemLib](https://github.com/actelion/openchemlib) Java library.

## Installation

```console
npm install openchemlib
```

## Documentation

- [Generated API documentation](https://cheminfo.github.io/openchemlib-js/)
- [Web examples](https://openchemlib-js.pages.dev)

## Development

To build this project, you need:

- Java JDK (Tested with version 21)
- GWT (Tested with version 2.13.0, [Download build here](http://www.gwtproject.org/download.html))
- Node.js (Tested with version 24, [Download here](https://nodejs.org/en/download/))

### Install dependencies

```console
npm ci
```

### Configure directories

Copy `config.default.json` to `config.json` and put the path to the GWT classes on your computer.  
If you want to use a custom JDK installation, set the "jdk" key to be the path to your JDK directory.

### npm scripts

To run one of the scripts, use `npm run <scriptName>`.  
To pass additional options, use `npm run <scriptName> -- --option`.

#### build-java

Compile and export the Java API.

#### copy-openchemlib

Copy the required java files from the openchemlib project.

## License

[BSD-3-Clause](./LICENSE)

[npm-image]: https://img.shields.io/npm/v/openchemlib.svg
[npm-url]: https://www.npmjs.com/package/openchemlib
[download-image]: https://img.shields.io/npm/dm/openchemlib.svg
[download-url]: https://www.npmjs.com/package/openchemlib
[license-image]: https://img.shields.io/npm/l/openchemlib.svg
[license-url]: https://github.com/cheminfo/openchemlib-js/blob/main/LICENSE

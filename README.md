# OpenChemLib JS

JavaScript port of the [OpenChemLib](https://github.com/actelion/openchemlib) Java library.

<h3 align="center">

  <a href="https://www.zakodium.com">
    <img src="https://www.zakodium.com/brand/zakodium-logo-white.svg" width="50" alt="Zakodium logo" />
  </a>

  <p>
    Maintained by <a href="https://www.zakodium.com">Zakodium</a>
  </p>
  
  [![NPM version][npm-image]][npm-url]
  [![build status][ci-image]][ci-url]
  [![npm download][download-image]][download-url]
  [![DOI](https://www.zenodo.org/badge/23346814.svg)](https://www.zenodo.org/badge/latestdoi/23346814)

</h3>

## Installation

```console
npm install openchemlib
```

## Documentation

- [TypeDoc home page](https://cheminfo.github.io/openchemlib-js/index.html)

## Development

To build this project, you need :

- Java JDK (Tested with version 21)
- GWT (Tested with version 2.12.2, [Download build here](http://www.gwtproject.org/download.html))
- Node.js (Tested with version 22, [Download here](https://nodejs.org/en/download/))

### Install dependencies

`npm ci`

### Configure directories

Copy `config.default.json` to `config.json` and put the path to the GWT classes on your computer.  
If you want to use a custom JDK installation, set the "jdk" key to be the path to your JDK directory.

### npm scripts

To run one of the scripts, use `npm run <scriptName>`.  
To pass additional options, use `npm run <scriptName> -- --option`.

**Options** :

- -m [name]: only process module "name"
- -v: verbose output

#### build-java

Compile and export the Java API.

#### copy-openchemlib

Copy the required java files from the openchemlib project.

## License

[BSD-3-Clause](./LICENSE)

[npm-image]: https://img.shields.io/npm/v/openchemlib.svg
[npm-url]: https://www.npmjs.com/package/openchemlib
[ci-image]: https://github.com/cheminfo/openchemlib-js/workflows/Node.js%20CI/badge.svg?branch=main
[ci-url]: https://github.com/cheminfo/openchemlib-js/actions?query=workflow%3A%22Node.js+CI%22
[download-image]: https://img.shields.io/npm/dm/openchemlib.svg
[download-url]: https://www.npmjs.com/package/openchemlib

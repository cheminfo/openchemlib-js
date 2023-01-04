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

This library is available in three different builds: `minimal`, `core` and `full`.
Each larger build contains all functionalities from the smaller ones.

The `core` build is the one that you get when you `require('openchemlib')`. It
contains all functionalities that can be used in Node.js.

The `minimal` (`require('openchemlib/minimal')`) build is a smaller one
(~ half the size of `core`) that is meant to be used in a browser application
if bundle size matters and prediction functionalities are not needed.

The `full` build (`require('openchemlib/full')`) build is the largest build.
It contains a structure viewer and a structure editor for browser applications.

- [TypeDoc home page](https://cheminfo.github.io/openchemlib-js/index.html)

### Modules present in minimal, core and full builds

- [Molecule](https://cheminfo.github.io/openchemlib-js/classes/Molecule.html)
- [Reaction](https://cheminfo.github.io/openchemlib-js/classes/Reaction.html)
- [RingCollection](https://cheminfo.github.io/openchemlib-js/classes/RingCollection.html)
- [SDFileParser](https://cheminfo.github.io/openchemlib-js/classes/SDFileParser.html)
- [SSSearcher](https://cheminfo.github.io/openchemlib-js/classes/SSSearcher.html)
- [SSSearcherWithIndex](https://cheminfo.github.io/openchemlib-js/classes/SSSearcherWithIndex.html)
- [Util](https://cheminfo.github.io/openchemlib-js/modules/Util.html)

### Modules present only in core and full builds

- [CanonizerUtil](https://cheminfo.github.io/openchemlib-js/classes/CanonizerUtil.html)
- [ConformerGenerator](https://cheminfo.github.io/openchemlib-js/classes/ConformerGenerator.html)
- [DruglikenessPredictor](https://cheminfo.github.io/openchemlib-js/classes/DruglikenessPredictor.html)
- [DrugScoreCalculator](https://cheminfo.github.io/openchemlib-js/modules/DrugScoreCalculator.html)
- [ForceFieldMMFF94](https://cheminfo.github.io/openchemlib-js/classes/ForceFieldMMFF94.html)
- [MoleculeProperties](https://cheminfo.github.io/openchemlib-js/classes/MoleculeProperties.html)
- [ReactionEncoder](https://cheminfo.github.io/openchemlib-js/classes/ReactionEncoder.html)
- [Reactor](https://cheminfo.github.io/openchemlib-js/classes/Reactor.html)
- [ToxicityPredictor](https://cheminfo.github.io/openchemlib-js/classes/ToxicityPredictor.html)
- [Transformer](https://cheminfo.github.io/openchemlib-js/classes/Transformer.html)

### Modules present only in full build

- [StructureEditor](https://cheminfo.github.io/openchemlib-js/classes/StructureEditor.html)
- [StructureView](https://cheminfo.github.io/openchemlib-js/modules/StructureView.html)
- [SVGRenderer](https://cheminfo.github.io/openchemlib-js/modules/SVGRenderer.html)

## Development

To build this project, you need :

- Java JDK (Tested with version 17)
- GWT (Tested with version 2.10, [Download build here](http://www.gwtproject.org/download.html))
- Node.js (Tested with version 18, [Dowload here](https://nodejs.org/en/download/))

### Install dependencies

`npm ci`

### Configure directories

Copy `config.default.json` to `config.json` and put the path to the GWT classes on your computer.  
If you want to use a custom JDK installation, set the "jdk" key to be the path to your JDK directory.

### npm scripts

To run one of the scripts, use `npm run <scriptName>`.  
To pass an options, use `npm run <scriptName> -- --option`.

**Options**:

- -m [name]: only process module "name"
- -v: verbose output

#### compile:min / compile:pretty

Execute the GWT compiler.

#### export

Transform the GWT compiled files to JavaScript modules.

#### build:min / build:pretty

Compile and export.

#### copy:openchemlib

Copy the required java files from the openchemlib project.

## License

[BSD-3-Clause](./LICENSE)

[npm-image]: https://img.shields.io/npm/v/openchemlib.svg
[npm-url]: https://www.npmjs.com/package/openchemlib
[ci-image]: https://github.com/cheminfo/openchemlib-js/workflows/Node.js%20CI/badge.svg?branch=main
[ci-url]: https://github.com/cheminfo/openchemlib-js/actions?query=workflow%3A%22Node.js+CI%22
[download-image]: https://img.shields.io/npm/dm/openchemlib.svg
[download-url]: https://www.npmjs.com/package/openchemlib

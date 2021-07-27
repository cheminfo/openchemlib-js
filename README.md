# OpenChemLib JS

[![NPM version][npm-image]][npm-url]
[![build status][ci-image]][ci-url]
[![npm download][download-image]][download-url]
[![DOI](https://www.zenodo.org/badge/23346814.svg)](https://www.zenodo.org/badge/latestdoi/23346814)


JavaScript port of the [OpenChemLib](https://github.com/actelion/openchemlib) Java library.

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

- [TypeDoc home page](https://cheminfo.github.io/openchemlib-js/globals.html)

### Modules present in minimal, core and full builds

- [Molecule](https://cheminfo.github.io/openchemlib-js/classes/molecule.html)
- [Reaction](https://cheminfo.github.io/openchemlib-js/classes/reaction.html)
- [RingCollection](https://cheminfo.github.io/openchemlib-js/classes/ringcollection.html)
- [SDFileParser](https://cheminfo.github.io/openchemlib-js/classes/sdfileparser.html)
- [SSSearcher](https://cheminfo.github.io/openchemlib-js/classes/sssearcher.html)
- [SSSearcherWithIndex](https://cheminfo.github.io/openchemlib-js/classes/sssearcherwithindex.html)
- [Util](https://cheminfo.github.io/openchemlib-js/modules/util.html)

### Modules present only in core and full builds

- [ConformerGenerator](https://cheminfo.github.io/openchemlib-js/classes/conformergenerator.html)
- [DruglikenessPredictor](https://cheminfo.github.io/openchemlib-js/classes/druglikenesspredictor.html)
- [DrugScoreCalculator](https://cheminfo.github.io/openchemlib-js/modules/drugscorecalculator.html)
- [ForceFieldMMFF94](https://cheminfo.github.io/openchemlib-js/classes/forcefieldmmff94.html)
- [MoleculeProperties](https://cheminfo.github.io/openchemlib-js/classes/moleculeproperties.html)
- [ToxicityPredictor](https://cheminfo.github.io/openchemlib-js/classes/toxicitypredictor.html)

### Modules present only in full build

- [StructureEditor](https://cheminfo.github.io/openchemlib-js/classes/structureeditor.html)
- [StructureView](https://cheminfo.github.io/openchemlib-js/modules/structureview.html)
- [SVGRenderer](https://cheminfo.github.io/openchemlib-js/modules/svgrenderer.html)

## Development

To build this project, you need :

- Java JDK >= 8
- GWT 2.9 ([Download build here](http://www.gwtproject.org/download.html))
- Node.js ([link](https://nodejs.org/en/download/))

### Install dependencies

`npm install`

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
[ci-image]: https://github.com/cheminfo/openchemlib-js/workflows/Node.js%20CI/badge.svg?branch=master
[ci-url]: https://github.com/cheminfo/openchemlib-js/actions?query=workflow%3A%22Node.js+CI%22
[download-image]: https://img.shields.io/npm/dm/openchemlib.svg
[download-url]: https://www.npmjs.com/package/openchemlib

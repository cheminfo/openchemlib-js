# OpenChemLib JS

[![NPM version][npm-image]][npm-url]
[![build status][travis-image]][travis-url]
[![npm download][download-image]][download-url]

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

- [TypeDoc home page](https://cheminfo.github.io/openchemlib-js/docs/globals.html)

### Modules present in minimal, core and full builds

- [Molecule](https://cheminfo.github.io/openchemlib-js/docs/classes/molecule.html)
- [Reaction](https://cheminfo.github.io/openchemlib-js/docs/classes/reaction.html)
- [RingCollection](https://cheminfo.github.io/openchemlib-js/docs/classes/ringcollection.html)
- [SDFileParser](https://cheminfo.github.io/openchemlib-js/docs/classes/sdfileparser.html)
- [SSSearcher](https://cheminfo.github.io/openchemlib-js/docs/classes/sssearcher.html)
- [SSSearcherWithIndex](https://cheminfo.github.io/openchemlib-js/docs/classes/sssearcherwithindex.html)
- [Util](https://cheminfo.github.io/openchemlib-js/docs/modules/util.html)

### Modules present only in core and full builds

- [ConformerGenerator](https://cheminfo.github.io/openchemlib-js/docs/classes/conformergenerator.html)
- [DruglikenessPredictor](https://cheminfo.github.io/openchemlib-js/docs/classes/druglikenesspredictor.html)
- [DrugScoreCalculator](https://cheminfo.github.io/openchemlib-js/docs/modules/drugscorecalculator.html)
- [ForceFieldMMFF94](https://cheminfo.github.io/openchemlib-js/docs/classes/forcefieldmmff94.html)
- [MoleculeProperties](https://cheminfo.github.io/openchemlib-js/docs/classes/moleculeproperties.html)
- [ToxicityPredictor](https://cheminfo.github.io/openchemlib-js/docs/classes/toxicitypredictor.html)

### Modules present only in full build

- [StructureEditor](https://cheminfo.github.io/openchemlib-js/docs/classes/structureeditor.html)
- [StructureView](https://cheminfo.github.io/openchemlib-js/docs/modules/structureview.html)
- [SVGRenderer](https://cheminfo.github.io/openchemlib-js/docs/modules/svgrenderer.html)

### Online demos

- [Editor](https://cheminfo.github.io/openchemlib-js/examples/Editor.html)
- [Show structures](https://cheminfo.github.io/openchemlib-js/examples/ShowStructures.html)
- [SVG](https://cheminfo.github.io/openchemlib-js/examples/SVG.html)

## Development

To build this project, you need :

- java >= 1.7
- GWT 2.8 ([Download build here](http://www.gwtproject.org/download.html))
- Node.js ([link](https://nodejs.org/en/download/current/))

### Install dependencies

`npm install`

### Configure directories

Copy `config.default.json` to `config.json` and put the path to the GWT classes on your computer.  
If you want to update the java code from openchemlib, provide the path to the openchemlib source.

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

[npm-image]: https://img.shields.io/npm/v/openchemlib.svg?style=flat-square
[npm-url]: https://www.npmjs.com/package/openchemlib
[travis-image]: https://img.shields.io/travis/cheminfo/openchemlib-js/master.svg?style=flat-square
[travis-url]: https://travis-ci.org/cheminfo/openchemlib-js
[download-image]: https://img.shields.io/npm/dm/openchemlib.svg?style=flat-square
[download-url]: https://www.npmjs.com/package/openchemlib

# openchemlib-js

[![NPM version][npm-image]][npm-url]
[![build status][travis-image]][travis-url]
[![npm download][download-image]][download-url]

JavaScript interface with the [openchemlib](https://github.com/actelion/openchemlib) java library

## Documentation

- [TypeDoc home page](https://cheminfo.github.io/openchemlib-js/docs/modules/_types_d_.html)

### Modules present in minimal, core and full builds

- [Molecule](https://cheminfo.github.io/openchemlib-js/docs/classes/_types_d_.molecule.html)
- [Reaction](https://cheminfo.github.io/openchemlib-js/docs/classes/_types_d_.reaction.html)
- [SDFileParser](https://cheminfo.github.io/openchemlib-js/docs/classes/_types_d_.sdfileparser.html)
- [SSSearcher](https://cheminfo.github.io/openchemlib-js/docs/classes/_types_d_.sssearcher.html)
- [SSSearcherWithIndex](https://cheminfo.github.io/openchemlib-js/docs/classes/_types_d_.sssearcherwithindex.html)
- [Util](https://cheminfo.github.io/openchemlib-js/docs/modules/_types_d_.util.html)

### Modules present only in core and full builds

- [DruglikenessPredictor](https://cheminfo.github.io/openchemlib-js/docs/classes/_types_d_.druglikenesspredictor.html)
- [DrugScoreCalculator](https://cheminfo.github.io/openchemlib-js/docs/modules/_types_d_.drugscorecalculator.html)
- [Molecule Properties](https://cheminfo.github.io/openchemlib-js/docs/classes/_types_d_.moleculeproperties.html)
- [ToxicityPredictor](https://cheminfo.github.io/openchemlib-js/docs/classes/_types_d_.toxicitypredictor.html)

### Modules present only in full build

- [StructureEditor](https://cheminfo.github.io/openchemlib-js/docs/classes/_types_d_.structureeditor.html)
- [StructureView](https://cheminfo.github.io/openchemlib-js/docs/modules/_types_d_.structureview.html)
- [SVGRenderer](https://cheminfo.github.io/openchemlib-js/docs/modules/_types_d_.svgrenderer.html)

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

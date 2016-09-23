4.4.1 / 2016-09-23
==================

* editor: fix structure change notification with keyboard
* editor: add drag and drop support
* editor: fix invasive paste issue

4.4.0 / 2016-09-01
==================

* fix highlighting issues
* add SVG editor version
* add compatibility with CouchDB views

4.3.2 / 2016-08-16
==================

* update openchemlib
* update GWT compiler

4.3.1 / 2016-07-19
==================

* update openchemlib

4.3.0 / 2016-05-24
==================

* update openchemlib
* add support for exclude group

4.2.2 / 2016-04-25
==================

* update openchemlib
* remove sourceURL from dist files

4.2.1 / 2016-04-20
==================

* toSVG now works correctly in core build
* warn that toSVG only works in a browser's window

4.2.0 / 2016-04-14
==================

* add DrugScoreCalculator

4.1.0 / 2016-03-17
==================

* add new contrib methods to generate HOSE codes and diastereotopic atom IDs
* do not use global window for `core` library (still needed for the other builds)
 * this allows to load any number of independant versions of `core` along with one version of `full` or `viewer`
 * `viewer` will probably be removed in the future, leaving only `core` and `full`

4.0.0 / 2016-02-16
==================

* Major: use property getters for MolecularFormula and MoleculeProperties
* update openchemlib

3.2.2 / 2015-12-15
==================

* fix namespace for editor and structure viewer

3.2.1 / 2015-12-15
==================

* refactor for and recompile with GWT 2.8.0-beta1

3.2.0 / 2015-11-24
==================

* add auto-generated methods to Molecule

3.1.1 / 2015-11-07
==================

* Editor: fixed AtomHightlight
* Editor: working query feature dialogs

3.1.0 / 2015-10-21
==================

* add ToxicityPredictor
* add DruglikenessPredictor

3.0.1 / 2015-10-20
==================

* update openchemlib
* use GWT 2.8 to fix bug in SSSearcher

3.0.0 / 2015-09-28
==================

* Make JS API in line with Java API
* Remove cheminfo-specific methods

3.0.0-beta6 / 2015-06-03
========================

* Changed prefix from actchem to OCL

3.0.0-beta5 / 2015-06-01
========================

* Fixed resizing issues in StructureViewer

3.0.0-beta4 / 2015-05-15
========================

* fix layout and missing images in editor

3.0.0-beta3 / 2015-05-12
========================

* fix resizing
* fix toolbar in case of multiple instances
* fix right click issues

3.0.0-beta2 / 2015-05-11
========================

* build without fake window

3.0.0-beta1 / 2015-05-11
========================

* update openchemlib
* build editor

3.0.0-alpha10 / 2015-04-08
==========================

* update openchemlib

3.0.0-alpha9 / 2015-03-20
=========================

* fix SVG depictor

3.0.0-alpha8 / 2015-03-20
=========================

* add bower.json

3.0.0-alpha7 / 2015-03-20
=========================

* update SVGDepictor
* expose molecule.getDiastereotopicAtomIDsArray

3.0.0-alpha6 / 2015-03-18
=========================

* fixed build errors for editor (missing class): this prevented exports
* add SVG output

3.0.0-alpha5 / 2015-02-27
=========================

* update chemlib
* update gwt exporter (no more globals)
* add options to Molecule.fromSmiles

3.0.0-alpha4 / 2015-02-06
=========================

* update chemlib
 * fix SMILES parser

3.0.0-alpha3 / 2015-01-26
=========================

* update chemlib
 * Depictor : new flag to allow suppression of stereo problems
 * Depictor : fix superscript locations
 * SMILES parser : @ indicators in smiles don't conflict anymore with implicit hydrogens as in [N@H]
 * SMILES parser : handling of '.' was improved
 * SMILES parser : detection of unusual valences was improved
* add noStereoProblem option to drawStructure

3.0.0-alpha2 / 2015-01-23
=========================

* add support for displayMode options in javascript

3.0.0-alpha1 / 2015-01-22
=========================

* add StructureViewer

2.0.7 / 2015-01-21
==================

* ensure invented coordinates are always the same

2.0.6 / 2015-01-19
==================

* update java library

2.0.5 / 2015-01-15
==================

* SMILES parser : custom coordinate inventor
* update java library

2.0.4 / 2015-01-12
==================

* Update java library

2.0.3 / 2015-01-09
==================

* Github webhook

2.0.2 / 2014-12-22
==================

* fix bugs in SMILES parser

2.0.1 / 2014-12-19
==================

* add molecule.ensureHelperArrays

2.0.0 / 2014-12-05
==================

* GWT 2.7
* no more javascript getters
* initialize molecule with 32 length arrays
* improve IDCode parser performance

1.2.1 / 2014-11-18
==================

* add molecule.getIDCodeAndCoordinates()
* fix bug with SSSearchWithIndex

1.2.0 / 2014-11-11
==================

* add dependency to actelion-ext project
* add molecule.getIDCoordinates()
* add Molecule.fromIDCode(idcode, coordinates)
* add Molecule.expandHydrogens()
* add Molecule.getDiastereotopicAtomIDs
* perf improvement : parsers and other utility classes are created only once and when needed 

1.1.0 / 2014-11-05
==================

* add molecule.getFragmentNumbers() and molecule.getFragments()

1.0.0 / 2014-09-10
==================

* first release

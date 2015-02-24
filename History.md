3.0.0-alpha5 / HEAD
===================

* update chemlib
* update gwt exporter (no more globals)

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
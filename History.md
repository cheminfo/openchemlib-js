# OpenChemLib JS Changelog

## 7.2.3 / 2020-01-10

- Update OCL to 2020.1.0
- Fix repaint issue in SVG Toolbar

## 7.2.2 / 2019-11-07

- Update OCL to 2019_11_05-15_48_20

## 7.2.1 / 2019-07-12

- Fix: work around a bug in Chromium with SVGDepictor when SVG is printed.

## 7.2.0 / 2019-07-08

- Add ConformerGenerator class.
- Add ForceFieldMMFF94 class.

## 7.1.1 / 2019-07-06

- Update OCL to 2019_07_04-04_00_27
- The SVG depictor (`Molecule#toSVG`) has been improved and its visual output is
  now more in line with the Java version.

## 7.1.0 / 2019-06-09

- Added two new options to the `toSVG` method:
  - `autoCrop`: if set to `true`, the SVG will be automatically crop to fit the bounds of the molecule.
    This will change the resulting dimensions of the SVG.
  - `autoCropMargin`: This is the margin (in px) kept around the molecule when `autoCrop` is `true`. Default: 5.
- Update OCL to 2019.6.0
- Added `is3D` option to the `removeExplicitHydrogens` method.

## 7.0.1 / 2019-05-05

- fix(types): make all depictor options effectively optional

## 7.0.0 / 2019-05-03

- Update OCL to 2019_05_03-16_18_03
- **BREAKING**: Node.js 6 is no longer supported
- **BREAKING**: The output of `Molecule.toSVG` has changed: bonds are now identified
  by the bond id instead of the ids of its two atoms. For example: `myId:Bond:0-1`
  becomes `myId:Bond:0`.

## 6.0.1 / 2019-02-08

- Update OCL to 2019_02_08-04_00_25
- Add missing return types in TypeScript declarations
- Support retina displays in StructureEditor

## 6.0.0 / 2019-01-27

- Update OCL to 2019_01_26-04_00_26
- **BREAKING**: Editor: the `StructureEditor` constructor now takes a DOM element instead of an id.

## 5.9.1 / 2018-12-21

- Update OCL to 2018_12_21-04_00_23
- Editor: fix bond query feature dialog

## 5.9.0 / 2018-12-17

- Update OCL to 2018_12_17-16_27_18
- Added RXN methods to Reaction class
- Added more options to bond query features in editor
- Added getBounds method to Molecule class

## 5.8.0 / 2018-12-09

- Update OCL to 2018_12_08-04_00_22
- Added Reaction class
- Added TypeScript declaration files
- Ported the documentation to TypeDoc

## 5.7.0 / 2018-12-02

- Update OCL to 2018.12.0
- Added support for copying and pasting molfiles
- Added molecule.toIsomericSmiles(includeAtomMapping)
- Fixed display of query features
- Fixed emitting change event for modifications coming from dialogs

## 5.6.1 / 2018-11-06

- Update OCL to 2018_11_03-04_00_20

## 5.6.0 / 2018-06-19

- Update OCL to 2018.6.0
- feat: expose RingCollection to JS

## 5.5.2 / 2018-04-27

- fix(editor): do not consider a mouse move for small movements

## 5.5.1 / 2018-04-19

- feat: add factorTextSize option to toSVG

## 5.5.0 / 2017-11-20

- Allow to retrieve a JSMolecule from editor

## 5.4.0 / 2017-11-16

- Update OCL to 2017_11_14-04_01_01

## 5.3.0 / 2017-09-22

- Update OCL to 2017_08_29-04_01_13
- Fix addMissingChirality

## 5.2.10 / 2017-07-13

- Fix H mapping issue

## 5.2.9 / 2017-07-07

- Use the X symbol for hydrogens in DiaID

## 5.2.8 / 2017-07-07

- Identical to 5.2.7

## 5.2.7 / 2017-07-07

- Use isotopes to mark atoms

## 5.2.6 / 2017-07-07

- Precalculate X atom number

## 5.2.5 / 2017-07-06

- Update to add missing stereochemistry based on parities helper

## 5.2.4 / 2017-07-06

- update diastereotopicAtomID not to rely on CIP

## 5.2.3 / 2017-07-04

- fix hoseCodeCreator

## 5.2.2 / 2017-07-03

- Update OCL to 2017_06_10-04_00_52
- Update diaID

## 5.2.1 / 2017-04-24

- Update OCL to 2017_04_22-04_01_31
- Fix a bug with the SMILES parser that could enter in a wrong state after parsing an invalid SMILES

## 5.2.0 / 2017-03-21

- feat: add fontWeight and strokeWidth options to toSVG

## 5.1.2 / 2017-03-08

- fix: Update OCL to 2017_03_03-04_00_41

## 5.1.1 / 2017-01-30

- fix: Update OCL to 2017_01_28-04_00_45

## 5.1.0 / 2017-01-27

- feat: add mol.toMolfileV3
- fix: Fixed SVGDepictor setLineWidth() regression.

## 5.0.0 / 2017-01-13

- remove viewer build
- introduce minimal build
- do not include MoleculeProperties in minimal build

BREAKING CHANGE:

`molecule.getProperties()` has been removed. `new MoleculeProperties(molecule)` must be used instead.

## 4.7.2 / 2017-01-10

- include full dist directory in npm and add require aliases

## 4.7.1 / 2017-01-10

- SVG depictor: compute string width without canvas for Helvetica. This allows the SVG depictor to be used on Node.js

## 4.7.0 / 2017-01-05

Update public methods of JSMolecule. This adds a few new methods:

- static double getDefaultAverageBondLength()
- static void setDefaultAverageBondLength(double defaultAVBL)
- int suggestBondType(int atom1, int atom2)
- double calculateTorsion(int[] atom)
- void setBondOrder(int bond,int order)
- int getMetalBondedConnAtoms(int atom)
- int getAllConnAtomsPlusMetalBonds(int atom)
- int getNonHydrogenNeighbourCount(int atom)
- int getExcludeGroupValence(int atom)
- int getLowestFreeValence(int atom)
- int getExplicitHydrogens(int atom)
- boolean isPseudoRotatableBond(int bond)
- boolean isAmideTypeBond(int bond)
- int getZNeighbour(int connAtom, int bond)
- int getHelperArrayStatus()

## 4.6.3 / 2017-01-04

- update OCL to 2017.1.0

## 4.6.2 / 2016-11-08

- AtomQueryFeaturesDialog: added more than 2 Hydrogens on query features
- Fix SVGDepictor: minimal bond width is now 1

## 4.6.1 / 2016-11-03

- fix license issue
- fix StructureEditor not present in last release

## 4.6.0 / 2016-10-25

- add more depictor options and support options in SVG depictor

## 4.5.1 / 2016-10-25

- update OCL to 2016_10_25-04_00_46

## 4.5.0 / 2016-10-19

- editor: fixed regression in chain placement
- update OCL to 2016_10_18-04_00_45

## 4.4.2 / 2016-09-23

- editor: fix initial placement of new bond/chain/ring
- editor: fix dialog positions

## 4.4.1 / 2016-09-23

- editor: fix structure change notification with keyboard
- editor: add drag and drop support
- editor: fix invasive paste issue

## 4.4.0 / 2016-09-01

- fix highlighting issues
- add SVG editor version
- add compatibility with CouchDB views

## 4.3.2 / 2016-08-16

- update openchemlib
- update GWT compiler

## 4.3.1 / 2016-07-19

- update openchemlib

## 4.3.0 / 2016-05-24

- update openchemlib
- add support for exclude group

## 4.2.2 / 2016-04-25

- update openchemlib
- remove sourceURL from dist files

## 4.2.1 / 2016-04-20

- toSVG now works correctly in core build
- warn that toSVG only works in a browser's window

## 4.2.0 / 2016-04-14

- add DrugScoreCalculator

## 4.1.0 / 2016-03-17

- add new contrib methods to generate HOSE codes and diastereotopic atom IDs
- do not use global window for `core` library (still needed for the other builds)
- this allows to load any number of independant versions of `core` along with one version of `full` or `viewer`
- `viewer` will probably be removed in the future, leaving only `core` and `full`

## 4.0.0 / 2016-02-16

- Major: use property getters for MolecularFormula and MoleculeProperties
- update openchemlib

## 3.2.2 / 2015-12-15

- fix namespace for editor and structure viewer

## 3.2.1 / 2015-12-15

- refactor for and recompile with GWT 2.8.0-beta1

## 3.2.0 / 2015-11-24

- add auto-generated methods to Molecule

## 3.1.1 / 2015-11-07

- Editor: fixed AtomHightlight
- Editor: working query feature dialogs

## 3.1.0 / 2015-10-21

- add ToxicityPredictor
- add DruglikenessPredictor

## 3.0.1 / 2015-10-20

- update openchemlib
- use GWT 2.8 to fix bug in SSSearcher

## 3.0.0 / 2015-09-28

- Make JS API in line with Java API
- Remove cheminfo-specific methods

## 3.0.0-beta6 / 2015-06-03

- Changed prefix from actchem to OCL

## 3.0.0-beta5 / 2015-06-01

- Fixed resizing issues in StructureViewer

## 3.0.0-beta4 / 2015-05-15

- fix layout and missing images in editor

## 3.0.0-beta3 / 2015-05-12

- fix resizing
- fix toolbar in case of multiple instances
- fix right click issues

## 3.0.0-beta2 / 2015-05-11

- build without fake window

## 3.0.0-beta1 / 2015-05-11

- update openchemlib
- build editor

## 3.0.0-alpha10 / 2015-04-08

- update openchemlib

## 3.0.0-alpha9 / 2015-03-20

- fix SVG depictor

## 3.0.0-alpha8 / 2015-03-20

- add bower.json

## 3.0.0-alpha7 / 2015-03-20

- update SVGDepictor
- expose molecule.getDiastereotopicAtomIDsArray

## 3.0.0-alpha6 / 2015-03-18

- fixed build errors for editor (missing class): this prevented exports
- add SVG output

## 3.0.0-alpha5 / 2015-02-27

- update chemlib
- update gwt exporter (no more globals)
- add options to Molecule.fromSmiles

## 3.0.0-alpha4 / 2015-02-06

- update chemlib
- fix SMILES parser

## 3.0.0-alpha3 / 2015-01-26

- update chemlib
- Depictor : new flag to allow suppression of stereo problems
- Depictor : fix superscript locations
- SMILES parser : @ indicators in smiles don't conflict anymore with implicit hydrogens as in [N@H]
- SMILES parser : handling of '.' was improved
- SMILES parser : detection of unusual valences was improved
- add noStereoProblem option to drawStructure

## 3.0.0-alpha2 / 2015-01-23

- add support for displayMode options in javascript

## 3.0.0-alpha1 / 2015-01-22

- add StructureViewer

## 2.0.7 / 2015-01-21

- ensure invented coordinates are always the same

## 2.0.6 / 2015-01-19

- update java library

## 2.0.5 / 2015-01-15

- SMILES parser : custom coordinate inventor
- update java library

## 2.0.4 / 2015-01-12

- Update java library

## 2.0.3 / 2015-01-09

- Github webhook

## 2.0.2 / 2014-12-22

- fix bugs in SMILES parser

## 2.0.1 / 2014-12-19

- add molecule.ensureHelperArrays

## 2.0.0 / 2014-12-05

- GWT 2.7
- no more javascript getters
- initialize molecule with 32 length arrays
- improve IDCode parser performance

## 1.2.1 / 2014-11-18

- add molecule.getIDCodeAndCoordinates()
- fix bug with SSSearchWithIndex

## 1.2.0 / 2014-11-11

- add dependency to actelion-ext project
- add molecule.getIDCoordinates()
- add Molecule.fromIDCode(idcode, coordinates)
- add Molecule.expandHydrogens()
- add Molecule.getDiastereotopicAtomIDs
- perf improvement : parsers and other utility classes are created only once and when needed

## 1.1.0 / 2014-11-05

- add molecule.getFragmentNumbers() and molecule.getFragments()

## 1.0.0 / 2014-09-10

- first release

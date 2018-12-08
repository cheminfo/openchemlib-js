# Documentation

## Table of contents

### Classes present in minimal, core and full builds

- [Molecule](#molecule)
- [MolecularFormula](#molecularformula)
- [Reaction](#reaction)
- [SDFileParser](#sdfileparser)
- [SSSearcher](#sssearcher)
- [SSSearcherWithIndex](#sssearcherwithindex)
- [Util](#util)

### Classes present in core and full builds

- [Molecule Properties](#moleculeproperties)
- [ToxicityPredictor](#toxicitypredictor)
- [DruglikenessPredictor](#druglikenesspredictor)
- [DrugScoreCalculator](#drugscorecalculator)

### Classes present in full build

- [StructureView](#structureview)
- [StructureEditor](#structureeditor)

---

## Molecule

### Molecule.fromSmiles(smiles, [options])

Parse the provided `smiles` and return a `Molecule`.  
By default, stereo features are parsed, which triggers itself a coordinate
computation and coordinates are computed again after parsing to guarantee that
they are always the same.  
If you do not need stereo features and want the fastest parsing, use this method
with `{noCoordinates: true, noStereo: true}`.

**Options**:

- `noCoordinates` - disable extra coordinate computation (default: false).
- `noStereo` - disable stereo features parsing (default: false).

### Molecule.fromMolfile(molfile)

Parse the provided `molfile` and return a `Molecule`.

### Molecule.fromMolfileWithAtomMap(molfile)

Parse the provided `molfile` and return an object with `Molecule` and map.

### Molecule.fromIDCode(idcode, [ensure2DCoordinates|coordinates])

Parse the provided `idcode` and return a `Molecule`.

**Parameters**:

- `idcode` - string with the idcode
- `ensure2DCoordinates` - boolean indicating if the 2D coordinates should be
  computed (default: `true`)
- `coordinates` - string with the idcoordinates to use

### molecule.toSmiles()

Returns a SMILES string.

### molecule.toIsomericSmiles(includeAtomMapping = false)

Returns an isomeric SMILES string.

### molecule.toMolfile()

Returns a molfile (V2000) string.

### molecule.toMolfileV3()

Returns a molfile (V3000) string.

### molecule.toSVG(width, height, id, options)

Returns an SVG string representing the structure in two dimensions.

**Options**:

- `factorTextSize` (default: 1)
- `fontWeight`: font-weight attribute of atom labels
- `strokeWidth`: stroke-width styling property of bonds

See [Depictor options](#depictor-options) for other options

### molecule.getIDCode()

Returns the ID Code if the molecule.

### molecule.getIDCoordinates()

Returns a string representation of the coordinates of the atoms in the molecule.

### molecule.getIDCodeAndCoordinates()

Returns an object with both the idcode and coordinates

### molecule.getMolecularFormula()

Returns a [MolecularFormula](#molecularformula) object

### molecule.getIndex()

Returns the int[] index array used for substructure search

### molecule.inventCoordinates()

Compute and set atom coordinates for this molecule

### molecule.addImplicitHydrogens([atomNumber])

Expand and find a position for all the hydrogens of the 2D molecule. If
`atomNumber` is specified, the function only applies for the hydrogens of the
given atom.

**Options**:

- `atomNumber`- The atom number according to the molfile (default: all the
  atoms)

### molecule.removeExplicitHydrogens()

Remove the explicit hydrogens in the molecule.

### molecule.getNumberOfHydrogens()

Returns the count of hydrogens in the molecule.

### molecule.getDiastereotopicAtomIDs()

Returns the diastereotopic IDs of all the atoms in the molecule

### molecule.addMissingChirality()

### molecule.getHoseCodes(options)

This function returns an array of HOSE(Hierarchical Organisation of Spherical
Environments) codes represented as diastereotopic actelion IDs.

**Options**:

- `maxSphereSize` - Maximum number of atoms from the center (default: 5).
- `type` - 1: stop if Csp3-Csp3, 0: normal hose code (default: 0).

### molecule.setFragment(isFragment)

Flags the molecule as a fragment or unflags it (useful for substructure search)

### molecule.getFragmentNumbers()

Returns the number of fragments in the molecule

### molecule.getFragments()

Returns an array of fragments from the molecule

## MolecularFormula

### mf.absoluteWeight

### mf.relativeWeight

### mf.formula

---

## Reaction

### Reaction.create()

Returns a new empty `Reaction`.

### Reaction.fromMolecules(molecules, reactantCount)

Returns a new `Reaction` filled with the provided molecules.

**Parameters**:

- `molecules` - Array of `Molecule` objects.
- `reactantCount` - Number of reactants in the `molecules` array. The remaining
  objects will be treated as products.

### Reaction.fromSmiles(smiles)

Returns a new `Reaction` based on a reaction SMILES string. The `Reaction` will
contain at most one `Molecule` for each component.

### reaction.toSmiles()

Serialize the `Reaction` to a reaction SMILES string.

### reaction.clone()

Returns a new copy of the `Reaction`.

### reaction.clear()

Empty the `Reaction`.

### reaction.removeCatalysts()

Remove all catalysts from the `Reaction`.

### reaction.isEmpty()

Returns whether the reaction is empty.

### reaction.setFragment(isFragment)

Mark the `Reaction` as `isFragment` (`true` or `false`).

### reaction.isFragment()

Returns whether the `Reaction` is a fragment.

### reaction.getReactant(index)

Returns the reactant `Molecule` at `index`.

### reaction.getReactants()

Returns the number of reactants.

### reaction.getProduct(index)

Returns the product `Molecule` at `index`.

### reaction.getProducts()

Returns the number of products.

### reaction.getCatalyst(index)

Returns the catalyst `Molecule` at `index`.

### reaction.getCatalysts()

Returns the number of catalysts.

### reaction.getMolecules()

Returns the total number of reactants and products.

### reaction.getMolecule(index)

Returns the reactant or product at `index` (starting with reactants).

### reaction.addReactant(reactant)

Add a new `Molecule` in the reactants.

### reaction.addReactantAt(reactant, index)

Add a new `Molecule` in the reactants at `index`.

### reaction.addProduct(product)

Add a new `Molecule` in the products.

### reaction.addProductAt(product, index)

Add a new `Molecule` in the products at `index`.

### reaction.addCatalyst(catalyst)

Add a new `Molecule` in the catalysts.

### reaction.addCatalystAt(catalyst, index)

Add a new `Molecule` in the catalysts at `index`.

### reaction.getName()

Returns the name of the `Reaction`.

### reaction.setName(name)

Sets the name of the `Reaction`.

### reaction.getAverageBondLength()

Returns the average bond length among reactants and products.

### reaction.isReactionLayoutRequired()

Returns whether the molecules` atom coordinate bounds touch or overlap.

### reaction.isPerfectlyMapped()

Returns whether all non-hydrogen atoms are mapped and whether every reactant
atom has exactly one assigned product atom.

### reaction.getHighestMapNo()

### reaction.validateMapping()

Removes mapping numbers that are only used on one side of the reaction. Throws
an exception if duplicate mapping numbers occur in reactants or products.

### reaction.getReactionCenterMapNos()

This method determines the largest mapping number in use (maxMapNo), creates a
boolean array[maxMapNo+1], and within this array flags every mapping number
that refers to atoms, which change bonds in the course of the reaction. Mapped
atoms that are connected to unpammed atoms are also considered being part of the
reaction center. If the reaction is unmapped or has no reactants or products,
then `null` is returned.

### reaction.getMergedCopy()

Merges all reactants into one `Molecule` and all products into another and
creates a new `Reaction` object from those.

---

## SDFileParser

### new SDFileParser(sdf, [fields])

Create a new parser

**Parameters**:

- `sdf` - string with the sdf
- `fields` - array of field names to parse. If null, the sdf is scanned to find
  all possible names (not efficient)

### parser.next()

Move to the next molfile. Returns `true` if there is one, `false` otherwise.

**Example**:

```js
var sdf = fs.readFileSync('./mysdf.sdf');
var parser = new actelion.SDFileParser(sdf);
while (parser.next()) {
  var molecule = parser.getMolecule();
  // process molecule
}
```

### parser.getMolecule()

Returns the current [`Molecule`](#molecule)

### parser.getNextMolFile()

Returns the current molfile string

### parser.getNextFieldData()

### parser.getField(name)

Returns the content of the field `name` from the current record or null

### parser.getFieldNames()

Returns the list of field names for the entire sdf

---

## SSSearcher

Basic substructure searcher

### new SSSearcher()

Create a new substructure searcher

### searcher.setFragment(fragment)

Set the `fragment` to search

**Parameters**:

- `fragment` - [Molecule](#molecule) instance to set as fragment. It has to be
  flagged with [`setFragment(true)`](#moleculesetfragmentisfragment) first.

### searcher.setMolecule(molecule)

Set the target `molecule` in which the search will be done

**Parameters**:

- `molecule` - [Molecule](#molecule) instance to set as target molecule

### searcher.setMol(fragment, molecule)

### searcher.isFragmentInMolecule()

Returns whether the current fragment is in the target molecule.

---

## SSSearcherWithIndex

Fast substructure search with index filtering

### SSSearcherWithIndex.getKeyIDCode()

Returns an array of the 512 idcodes that are used for the index

### SSSearcherWithIndex.getSimilarityTanimoto(index1, index2)

Returns the Tanimoto similarity between the two indexes

### SSSearcherWithIndex.getSimilarityAngleCosine(index1, index2)

### SSSearcherWithIndex.getIndexFromHexString(string)

### SSSearcherWithIndex.getHexStringFromIndex(index)

### SSSearcherWithIndex.bitCount(int)

### new SSSearcherWithIndex()

Create a new substructure searcher

### searcher.setFragment(fragment, [index])

Set the `fragment` to search

**Parameters**:

- `fragment` - [Molecule](#molecule) instance to set as fragment. It has to be
  flagged with [`setFragment(true)`](#moleculesetfragmentisfragment) first.
- `index` - If the index for this fragment was computed previously, it can be
  provided here to save time.

### searcher.setMolecule(molecule, [index])

Set the target `molecule` in which the search will be done

**Parameters**:

- `molecule` - [Molecule](#molecule) instance to set as target molecule
- `index` - If the index for this fragment was computed previously, it can be
  provided here to save time.

### searcher.isFragmentInMolecule()

Returns whether the current fragment is in the target molecule.

### searcher.createIndex()

---

## Util

### Util.getHoseCodesFromDiastereotopicID(id, options)

Returns the HOSE(Hierarchical Organisation of Spherical Environments) code for
the given diasterotopic ID.

**Options**:

- `maxSphereSize` - Maximum number of atoms from the center (default: 5).
- `type` - 1: stop if Csp3-Csp3, 0: normal hose code (default: 0).

---

## MoleculeProperties

### p.acceptorCount

### p.donorCount

### p.logP

### p.logS

### p.polarSurfaceArea

### p.rotatableBondCount

### p.stereoCenterCount

---

## ToxicityPredictor

### Risk constants

- ToxicityPredictor.RISK_UNKNOWN
- ToxicityPredictor.RISK_NO
- ToxicityPredictor.RISK_LOW
- ToxicityPredictor.RISK_HIGH

### Toxicity type constants

- ToxicityPredictor.TYPE_MUTAGENIC
- ToxicityPredictor.TYPE_TUMORIGENIC
- ToxicityPredictor.TYPE_IRRITANT
- ToxicityPredictor.TYPE_REPRODUCTIVE_EFFECTIVE

### predictor.assessRisk(molecule, type)

Returns the calculated risk as an integer.

### predictor.getDetail(molecule, type)

Returns detailed information about the risk and the substructures
that are responsible for it.

---

## DruglikenessPredictor

### Constants

- DruglikenessPredictor.DRUGLIKENESS_UNKNOWN

### predictor.assessDruglikeness(molecule)

Returns the calculated drug likeness as a double.

### predictor.getDruglikenessString(molecule)

### predictor.getDetail()

Returns detailed information about the previous drug likeness assessment.

---

## DrugScoreCalculator

### DrugScoreCalculator.calculate(cLogP, solubility, molWeight, drugLikeness, toxicityRisks)

---

## StructureView

### StructureView.drawStructure(id, idcode, coordinates, options)

**Options**

See [Depictor options](#depictor-options)

---

## StructureEditor

You can test it on line:

- <a href="https://cheminfo.github.io/openchemlib-js/examples/Editor.html" target="_blank">Editor</a>
- <a href="https://cheminfo.github.io/openchemlib-js/examples/ShowStructures.html" target="_blank">showStructures</a>
- <a href="https://cheminfo.github.io/openchemlib-js/examples/SVG.html" target="_blank">SVG</a>

### StructureEditor.createEditor(id)

Create a chemical structure editor control at the DIV with the id of id.
returns the editor control object

### editor.getIDCode()

Returns the current molecules IDCode

### editor.setIDCode(idcode)

Sets the editor content to this IDCode

### editor.setFragment(set)

Switches the current molecule in the editor in fragment mode

### editor.isFragment()

Returns whether or not the current molecule is a fragment

### editor.getMolFile()

Returns String consting of a MDL Molfile Version 2.0

### editor.getMolFileV3()

Returns String consting of a MDL Molfile Version 3.0

### editor.setMolFile(molfile)

Sets the editor content to the molecule represented by this Molfile (Version 2.0
or 3.0).

### editor.getSmiles()

Returns the Smiles string of the current molecule

### editor.setSmiles(smiles)

Sets the editor content to the molecule represented by the passed Smiles

### editor.setAtomHightlightCallback(onAtomHighlight)

Sets a callback function which is called whenever an atom is selected/unselected
during mouse hover. The callback signature is: callback(atom,selected)

### editor.setBondHightlightCallback(onBondHighlight)

Sets a callback function which is called whenever an bond is selected/unselected
during mouse hover. The callback signature is: callback(bond,selected)

### editor.setChangeListenerCallback(changeMolecule)

Sets a callback function which is called whenever the structure in the editor
has been changed. The callback signature is: callback(newIdCode)

### Usage in your page

load this script:

     src="openchemlib-full.js"

create a div with the following attributes:

     id="editor"
     style="width:100%;height:400px;border:solid;border-width:2px"
     view-only="false"
     show-idcode="true"
     data-idcode="fhvacFGXhDFICDPx@fCHW@@UDhdmdCVZ``J@@@ !BLsicOgDjrHKHwW{@rHJW`lbBrMu~pG{@rHI[E\}bup}"

execute the following JS:

        editorCtrl = window.OCL.StructureEditor.createEditor("editor");

### Attributes (defaults are in UPPERCASE):

     view-only="FALSE" | "true"     = Shows the toolbar

     show-idcode="FALSE" | "true"   = Shows the input control containing the current IDCode

     data-idcode=""                 = This allows you to specify the molecule IDCode in the editor

     is-fragment="FALSE" | "true"   = Sets the editor into fragment (query) mode.
                                      Please note that the data-icode attribute overrules this behavior:
                                      If specified, the mode depends on the passed molecule

     show-fragment-indicator= "FALSE" | "true"
                                    = Shows a "Q" on the right lower if the Molecule is in fragment (query) mode

     ignore-stereo-errors= "FALSE" | "true"
                                    = If true, then shows no magenta-colored atom/bonds on stereo errors

     no-stereo-text= "FALSE" | "true"
                                    = If true, does not show stereo information text

---

## Depictor options

The following options are all boolean and they default to `false`.

- `inflateToMaxAVBL`
- `inflateToHighResAVBL`
- `chiralTextBelowMolecule`
- `chiralTextAboveMolecule`
- `chiralTextOnFrameTop`
- `chiralTextOnFrameBottom`
- `noTabus`
- `showAtomNumber`
- `showBondNumber`
- `highlightQueryFeatures`
- `showMapping`
- `suppressChiralText`
- `suppressCIPParity`
- `suppressESR`
- `showSymmetrySimple`
- `showSymmetryDiastereotopic`
- `showSymmetryEnantiotopic`
- `noImplicitAtomLabelColors`
- `noStereoProblem`

# Documentation

[Molecule](#molecule)  
[SDF parser](#sdfileparser)  
[Substructure searcher](#sssearcher)  
[Substructure searcher with index](#sssearcherwithindex)  
[Toxicity predictor](#toxicitypredictor)  
[Drug likeness predictor](#druglikenesspredictor)

[StructureEditor](#structureeditor)


---------------------------------------

## Molecule

### Molecule.fromSmiles(smiles, [options])

Parse the provided `smiles` and return a `Molecule`.  
By default, stereo features are parsed, which triggers itself a coordinate computation and coordinates are computed again after parsing to guarantee that they are always the same.  
If you do not need stereo features and want the fastest parsing, use this method with `{noCoordinates: true, noStereo: true}`.

__Options__

* `noCoordinates` - disable extra coordinate computation (default: false).
* `noStereo` - disable stereo features parsing (default: false).

### Molecule.fromMolfile(molfile)

Parse the provided `molfile` and return a `Molecule`

### Molecule.fromIDCode(idcode, [ensure2DCoordinates|coordinates])

Parse the provided `idcode` and return a `Molecule`

__Arguments__

* `idcode` - string with the idcode
* `ensure2DCoordinates` - boolean indicating if the 2D coordinates should be computed (default: true)
* `coordinates` - string with the idcoordinates to use

### molecule.toSmiles()

Returns a smiles string

### molecule.toMolfile()

Returns a molfile (V2000) string

### molecule.toSVG(width, height, id)

Returns an SVG string

### molecule.getIDCode()

Returns the ID Code if the molecule

### molecule.getIDCoordinates()

Returns a string representation of the coordinates of the atoms in the molecule

### molecule.getIDCodeAndCoordinates()

Returns an object with both the idcode and coordinates

### molecule.getMolecularFormula()

Returns a [MolecularFormula](#molecularformula) object

### molecule.getProperties()

Returns a [MoleculeProperties](#moleculeproperties) object

### molecule.getIndex()

Returns the int[] index array used for substructure search

### molecule.inventCoordinates()

Compute and set atom coordinates for this molecule

### molecule.setFragment(isFragment)

Flags the molecule as a fragment or unflags it (useful for substructure search)

### molecule.getFragmentNumbers()

Returns the number of fragments in the molecule

### molecule.getFragments()

Returns an array of fragments from the molecule

## MolecularFormula

### mf.getAbsoluteWeight()
### mf.getRelativeWeight()
### mf.getFormula()

## MoleculeProperties

### p.getAcceptorCount()
### p.getDonorCount()
### p.getLogP()
### p.getLogS()
### p.getPolarSurfaceArea()
### p.getRotatableBondCount()
### p.getStereoCenterCount()

---------------------------------------

## SDFileParser

### new SDFileParser(sdf, [fields])

Create a new parser

__Arguments__

* `sdf` - string with the sdf
* `fields` - array of field names to parse. If null, the sdf is scanned to find all possible names (not efficient)

### parser.next()

Move to the next molfile. Returns true if there is one, false otherwise.

__Example__
```js
var sdf = fs.readFileSync('./mysdf.sdf');
var parser = new actelion.SDFileParser(sdf);
while(parser.next()) {
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

---------------------------------------

## SSSearcher

Basic substructure searcher

### new SSSearcher()

Create a new substructure searcher

### searcher.setFragment(fragment)

Set the `fragment` to search

__Arguments__

* `fragment` - [Molecule](#molecule) instance to set as fragment. It has to be flagged with [`setFragment(true)`](#moleculesetfragmentisfragment) first

### searcher.setMolecule(molecule)

Set the target `molecule` in which the search will be done

__Arguments__

* `molecule` - [Molecule](#molecule) instance to set as target molecule

### searcher.setMol(fragment, molecule)

### searcher.isFragmentInMolecule()

Returns true if the set fragment is in the target molecule, false otherwise

---------------------------------------

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

__Arguments__

* `fragment` - [Molecule](#molecule) instance to set as fragment. It has to be flagged with [`setFragment(true)`](#moleculesetfragmentisfragment) first
* `index` - If the index for this fragment was computed previously, it can be provided here to save time

### searcher.setMolecule(molecule, [index])

Set the target `molecule` in which the search will be done

__Arguments__

* `molecule` - [Molecule](#molecule) instance to set as target molecule
* `index` - If the index for this fragment was computed previously, it can be provided here to save time

### searcher.isFragmentInMolecule()

Returns true if the set fragment is in the target molecule, false otherwise

### searcher.createIndex()

---------------------------------------

## ToxicityPredictor

### Risk constants

* ToxicityPredictor.RISK_UNKNOWN
* ToxicityPredictor.RISK_NO
* ToxicityPredictor.RISK_LOW
* ToxicityPredictor.RISK_HIGH

### Toxicity type constants

* ToxicityPredictor.TYPE_MUTAGENIC
* ToxicityPredictor.TYPE_TUMORIGENIC
* ToxicityPredictor.TYPE_IRRITANT
* ToxicityPredictor.TYPE_REPRODUCTIVE_EFFECTIVE

### predictor.assessRisk(molecule, type)

Returns the calculated risk as an integer.

### predictor.getDetail(molecule, type)

Returns detailed information about the risk and the substructures
that are responsible for it.

---------------------------------------

## DruglikenessPredictor

### Constants

* DruglikenessPredictor.DRUGLIKENESS_UNKNOWN

### predictor.assessDruglikeness(molecule)

Returns the calculated drug likeness as a double.

### predictor.getDruglikenessString(molecule)

### predictor.getDetail()

Returns detailed information about the previous drug likeness assessment.

---------------------------------------

## StructureEditor

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

Sets the editor content to the molecule represented by this Molfile (Version 2.0 or 3.0)

### editor.getSmiles()

Returns the Smiles string of the current molecule

### editor.setSmiles(smiles)

Sets the editor content to the molecule represented by the passed Smiles

### editor.setAtomHightlightCallback(onAtomHighlight)

Sets a callback function which is called whenever an atom is selected/unselected during mouse hover. The callback signature is: callback(atom,selected)

### editor.setBondHightlightCallback(onBondHighlight)

Sets a callback function which is called whenever an bond is selected/unselected during mouse hover. The callback signature is: callback(bond,selected)

### editor.setChangeListenerCallback(changeMolecule)

Sets a callback function which is called whenever the structure in the editor has been changed. The callback signature is: callback(newIdCode)


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
                                     



Script: https://my.cheminfo.org/?viewURL=https%3A%2F%2Fmydb.cheminfo.org%2Fdb%2Fvisualizer%2Fentry%2F59656995247862943f92f86ad8bd3232%2Fview.json&login=false

## Manually removed

- public JSMolecule[] getFragments(int[] fragmentNo, int fragmentCount)
- public RingCollection getRingSet()
- public JSMolecule createMolecule(int atoms, int bonds)
- public Canonizer getCanonizer()
- public Coordinates getCoordinates(int atom)

## Duplicates

Duplicata must be removed.

- public void ensureHelperArrays(int required)
- public void validate() throws Exception
- public String getChiralText()
- public JSMolecule getCompactCopy()
- public void copyMoleculeProperties(JSMolecule destMol)
- public void stripStereoInformation()

## Problem with the name

Must correct the name in oclMolecule.$name() call.

- public void copyMoleculeByAtoms(JSMolecule destMol, boolean[] includeAtom, boolean recognizeDelocalizedBonds, int[] atomMap)
- public int[] copyMoleculeByBonds(JSMolecule destMol, boolean[] includeBond, boolean recognizeDelocalizedBonds, int[] atomMap)
- public void deleteMolecule()
- public void copyMolecule(JSMolecule destMol)
- public int[] addMolecule(JSMolecule mol)
- public void copyMoleculeProperties(JSMolecule destMol)
- public String getAtomListString(int atom)
- public JSMolecule createMolecule(int atoms, int bonds)
- public int getMoleculeColor()
- public void setMoleculeColor(int color)

## Multiline methods

Must be copied by hand from the source file.

- public int copyBond(JSMolecule destMol, int sourceBond, int esrGroupOffsetAND, int esrGroupOffsetOR, int[] atomMap, boolean useBondTypeDelocalized)

## Multiple signatures

Must be removed from JSMolecule.java.

- public int addAtom(double x, double y)
- public int addAtom(double x, double y, double z)
- public int addAtom(String atomLabel)
- public int addBond(int atom1, int atom2, int type)
- public boolean changeAtomCharge(double x, double y, boolean positive)
- public int[] deleteAtoms(boolean[] deleteAtom)
- public double getAverageBondLength()
- public double getAverageBondLength(int atoms, int bonds)
- public double getAverageBondLength(int atoms, int bonds, double defaultBondLength)
- public boolean isStereoBond(int bond, int atom)
- public void setAtomList(int atom, int[] list)
- public int getPathLength(int atom1, int atom2, int maxLength, boolean[] neglectAtom)
- public int getFragmentNumbers(int[] fragmentNo, boolean[] neglectBond, boolean considerMetalBonds)
- public int[] getFragmentAtoms(int rootAtom)
- public int[] stripSmallFragments()

## Unknown issue

- public int getAtomZValue(int atom) // commented out in OCL
- public int getAtomSigma(int atom) // commented out in OCL

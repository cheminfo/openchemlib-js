Script: http://visualizer.epfl.ch/tiny/X0dNMRc1NiGIUASYEAlk

## Manually removed

- public JSMolecule[] getFragments(int[] fragmentNo, int fragmentCount)
- public RingCollection getRingSet()
- public JSMolecule createMolecule(int atoms, int bonds)
- public Canonizer getCanonizer()

## Duplicates

- public void ensureHelperArrays(int required)
- public void validate() throws Exception
- public String getChiralText()
- public JSMolecule getCompactCopy()

## Problem with the name

- public void copyMoleculeByAtoms(JSMolecule destMol, boolean[] includeAtom, boolean recognizeDelocalizedBonds, int[] atomMap)
- public int[] copyMoleculeByBonds(JSMolecule destMol, boolean[] includeBond, boolean recognizeDelocalizedBonds, int[] atomMap)
- public void deleteMolecule()
- public void copyMolecule(JSMolecule destMol)
- public int[] addMolecule(JSMolecule mol)
- public void copyMoleculeProperties(JSMolecule destMol)
- public String getAtomListString(int atom)

## Multiline methods

- public int copyBond(JSMolecule destMol, int sourceBond, int esrGroupOffsetAND, int esrGroupOffsetOR, int[] atomMap, boolean useBondTypeDelocalized)

## Multiple signatures

- public int addAtom(float x, float y)
- public int addAtom(float x, float y, flat z)
- public int addAtom(String atomLabel)
- public boolean changeAtomCharge(float x, float y, boolean positive)
- public int[] deleteAtoms(boolean[] deleteAtom)
- public float getAverageBondLength()
- public float getAverageBondLength(int atoms, int bonds)
- public boolean isStereoBond(int bond, int atom)
- public void setAtomList(int atom, int[] list)
- public void setAtomX(int atom, float x)
- public void setAtomY(int atom, float y)
- public void setAtomZ(int atom, float z)
- public int getPathLength(int atom1, int atom2, int maxLength, boolean[] neglectAtom)
- public int getFragmentNumbers(int[] fragmentNo, boolean[] neglectBond)

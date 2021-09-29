import ExtendedMolecule from './ExtendedMolecule.mjs';

export default class RingCollection {
  static MAX_SMALL_RING_SIZE = 7;
  static MAX_SMALL_RING_COUNT = 1024; // to prevent explosions with highly connected metal grids, etc.

  static MODE_SMALL_RINGS = 1;
  static MODE_LARGE_RINGS = 2;
  static MODE_AROMATICITY = 4;
  static MODE_SMALL_RINGS_ONLY = this.MODE_SMALL_RINGS;
  static MODE_SMALL_AND_LARGE_RINGS =
    this.MODE_SMALL_RINGS | this.MODE_LARGE_RINGS;
  static MODE_SMALL_RINGS_AND_AROMATICITY =
    this.MODE_SMALL_RINGS | this.MODE_AROMATICITY;
  static MODE_SMALL_AND_LARGE_RINGS_AND_AROMATICITY =
    this.MODE_SMALL_RINGS | this.MODE_LARGE_RINGS | this.MODE_AROMATICITY;

  mMol = null;
  mRingAtomSet = null;
  mRingBondSet = null;
  mAtomRingSize = null;
  mBondRingSize = null;
  mHeteroPosition = null;
  mIsAromatic = null;
  mIsDelocalized = null;
  mMaxSmallRingSize = 0;

  constructor(
    mol,
    mode,
    maxSmallRingSize = RingCollection.MAX_SMALL_RING_SIZE,
  ) {
    this.mMol = mol;
    this.mMaxSmallRingSize = maxSmallRingSize;
    this.mRingAtomSet = [];
    this.mRingBondSet = [];
    this.mAtomRingSize = new Int32Array(this.mMol.getAtoms());
    this.mBondRingSize = new Int32Array(this.mMol.getBonds());
    this.mMol.ensureHelperArrays(ExtendedMolecule.cHelperRings);

    let isConfirmedChainAtom = new Array(this.mMol.getAtoms()).fill(false);
    let isConfirmedChainBond = new Array(this.mMol.getBonds()).fill(false);

    let found;
    do {
      // detect atoms of side chains as non-ring-atoms
      found = false;
      for (let atom = 0; atom < this.mMol.getAtoms(); atom++) {
        if (!isConfirmedChainAtom[atom]) {
          let potentialRingNeighbours = 0;
          for (let i = 0; i < this.mMol.getConnAtoms(atom); i++)
            if (!isConfirmedChainAtom[this.mMol.getConnAtom(atom, i)])
              potentialRingNeighbours++;

          if (potentialRingNeighbours < 2) {
            isConfirmedChainAtom[atom] = true;
            for (let i = 0; i < this.mMol.getConnAtoms(atom); i++)
              isConfirmedChainBond[this.mMol.getConnBond(atom, i)] = true;

            found = true;
          }
        }
      }
    } while (found);

    // generate graph of potential ring atoms to find ring closure bonds
    let startAtom = 0; // simply take the first potential ring atom as graph base
    while (startAtom < this.mMol.getAtoms() && isConfirmedChainAtom[startAtom])
      startAtom++;
    if (startAtom == this.mMol.getAtoms()) return; // no rings found

    // find all rings with less than 8 members of all closure bonds
    let graphAtom = new Int32Array(this.mMol.getAtoms());
    graphAtom[0] = startAtom;
    let parent = new Int32Array(this.mMol.getAtoms());
    parent[0] = -1;
    let fragmentNo = new Int32Array(this.mMol.getAtoms());
    fragmentNo[startAtom] = 1;
    let current = 0;
    let highest = 0;
    let noOfFragments = 1;
    while (current <= highest) {
      for (let i = 0; i < this.mMol.getConnAtoms(graphAtom[current]); i++) {
        let candidate = this.mMol.getConnAtom(graphAtom[current], i);
        if (candidate == parent[graphAtom[current]]) continue;

        if (fragmentNo[candidate] != 0) {
          // closure bond
          this.addSmallRingsToSet(
            this.mMol.getConnBond(graphAtom[current], i),
            isConfirmedChainAtom,
          );
          continue;
        }

        if (!isConfirmedChainAtom[candidate]) {
          fragmentNo[candidate] = noOfFragments;
          parent[candidate] = graphAtom[current];
          graphAtom[++highest] = candidate;
        }
      }
      current++;
      if (current > highest) {
        // if run out of atoms look for new base atom of other fragment
        for (let atom = 0; atom < this.mMol.getAtoms(); atom++) {
          if (fragmentNo[atom] == 0 && !isConfirmedChainAtom[atom]) {
            fragmentNo[atom] = ++noOfFragments;
            graphAtom[++highest] = atom;
            parent[atom] = -1;
            break;
          }
        }
      }
    }

    if ((mode & RingCollection.MODE_AROMATICITY) != 0) {
      this.mIsAromatic = new Array(this.mRingAtomSet.length).fill(false);
      this.mIsDelocalized = new Array[this.mRingAtomSet.length].fill(false);
      this.mHeteroPosition = new Int32Array(this.mRingAtomSet.length);
      this.determineAromaticity(
        this.mIsAromatic,
        this.mIsDelocalized,
        this.mHeteroPosition,
        false,
      );
    }

    // find large rings by examining every potential ring bond
    // which is not a member of a small ring
    if ((mode & RingCollection.MODE_LARGE_RINGS) != 0) {
      for (let bond = 0; bond < this.mMol.getBonds(); bond++) {
        if (!isConfirmedChainBond[bond] && this.mMol.getBondOrder(bond) != 0) {
          let ringAtom = this.findSmallestRing(bond, isConfirmedChainAtom);
          if (ringAtom != null)
            this.updateRingSizes(ringAtom, this.getRingBonds(ringAtom));
        }
      }
    }
  }
}

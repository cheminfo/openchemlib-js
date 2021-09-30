import ExtendedMolecule from './ExtendedMolecule.js';

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
    this.mMol.ensureHelperArrays(ExtendedMolecule.cHelperNeighbours);

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

  addSmallRingsToSet(closureBond, isConfirmedChainAtom) {
    let graphAtom = new Int32Array(this.mMaxSmallRingSize);
    let connIndex = new Int32Array(this.mMaxSmallRingSize);
    let isUsed = new Array(this.mMol.getAtoms()).fill(false);

    let atom1 = this.mMol.getBondAtom(0, closureBond);
    let atom2 = this.mMol.getBondAtom(1, closureBond);

    graphAtom[0] = atom1;
    graphAtom[1] = atom2;
    connIndex[1] = -1;
    isUsed[atom2] = true;
    let current = 1;

    while (current >= 1) {
      connIndex[current]++;
      if (connIndex[current] == this.mMol.getConnAtoms(graphAtom[current])) {
        isUsed[graphAtom[current]] = false;
        current--;
        continue;
      }

      let candidate = this.mMol.getConnAtom(
        graphAtom[current],
        connIndex[current],
      );
      if (isUsed[candidate] || isConfirmedChainAtom[candidate]) continue;

      if (candidate == atom1 && current > 1) {
        this.addRingIfNew(graphAtom, current + 1);

        // if we have already such many rings, we only collect the smallest ring to avoid a combinatorial explosion
        if (this.mRingAtomSet.length >= RingCollection.MAX_SMALL_RING_COUNT)
          return;

        continue;
      }

      if (current + 1 < this.mMaxSmallRingSize) {
        current++;
        graphAtom[current] = candidate;
        isUsed[candidate] = true;
        connIndex[current] = -1;
      }
    }
  }

  addRingIfNew(ringAtom, ringSize) {
    let lowAtom = this.mMol.getMaxAtoms();
    let lowIndex = 0;
    for (let i = 0; i < ringSize; i++) {
      if (lowAtom > ringAtom[i]) {
        lowAtom = ringAtom[i];
        lowIndex = i;
      }
    }

    let sortedRing = new Int32Array(ringSize);
    let leftIndex = lowIndex > 0 ? lowIndex - 1 : ringSize - 1;
    let rightIndex = lowIndex < ringSize - 1 ? lowIndex + 1 : 0;
    let inverse = ringAtom[leftIndex] < ringAtom[rightIndex];
    for (let i = 0; i < ringSize; i++) {
      sortedRing[i] = ringAtom[lowIndex];
      if (inverse) {
        if (--lowIndex < 0) lowIndex = ringSize - 1;
      } else {
        if (++lowIndex == ringSize) lowIndex = 0;
      }
    }

    for (let i = 0; i < this.mRingAtomSet.length; i++) {
      let ringOfSet = this.mRingAtomSet[i];
      if (ringOfSet.length != ringSize) continue;
      let equal = true;
      for (let j = 0; j < ringSize; j++) {
        if (ringOfSet[j] != sortedRing[j]) {
          equal = false;
          break;
        }
      }
      if (equal) return;
    }

    this.mRingAtomSet.push(sortedRing);
    let ringBond = this.getRingBonds(sortedRing);
    this.mRingBondSet.push(ringBond);

    this.updateRingSizes(sortedRing, ringBond);
  }

  updateRingSizes(ringAtom, ringBond) {
    let ringSize = ringAtom.length;
    for (let i = 0; i < ringSize; i++)
      if (
        this.mAtomRingSize[ringAtom[i]] == 0 ||
        this.mAtomRingSize[ringAtom[i]] > ringSize
      )
        this.mAtomRingSize[ringAtom[i]] = ringSize;

    for (let i = 0; i < ringSize; i++)
      if (
        this.mBondRingSize[ringBond[i]] == 0 ||
        this.mBondRingSize[ringBond[i]] > ringSize
      )
        this.mBondRingSize[ringBond[i]] = ringSize;
  }

  getRingBonds(ringAtom) {
    let ringAtoms = ringAtom.length;
    let ringBond = new Int32Array(ringAtoms);
    for (let i = 0; i < ringAtoms; i++) {
      let atom = i == ringAtoms - 1 ? ringAtom[0] : ringAtom[i + 1];
      for (let j = 0; j < this.mMol.getConnAtoms(ringAtom[i]); j++) {
        if (this.mMol.getConnAtom(ringAtom[i], j) == atom) {
          ringBond[i] = this.mMol.getConnBond(ringAtom[i], j);
          break;
        }
      }
    }
    return ringBond;
  }

  getSize() {
    return this.mRingAtomSet.length;
  }

  getRingSize(ringNo) {
    return this.mRingBondSet[ringNo].length;
  }

  getRingAtoms(ringNo) {
    return this.mRingAtomSet[ringNo];
  }

  qualifiesAsAmideTypeBond(bond) {
    for (let i = 0; i < 2; i++) {
      let atom1 = this.mMol.getBondAtom(i, bond);
      if (
        this.mMol.getAtomicNo(atom1) == 7 &&
        this.mMol.getConnAtoms(atom1) == 2
      ) {
        let atom2 = this.mMol.getBondAtom(1 - i, bond);
        if (this.mMol.getAtomicNo(atom2) == 6) {
          for (let j = 0; j < this.mMol.getConnAtoms(atom2); j++) {
            let connAtom = this.mMol.getConnAtom(atom2, j);
            let connBond = this.mMol.getConnBond(atom2, j);
            if (
              (this.mMol.getAtomicNo(connAtom) == 8 ||
                this.mMol.getAtomicNo(connAtom) == 16) &&
              this.mMol.getBondOrder(connBond) == 2 &&
              this.mMol.getConnAtoms(connAtom) == 1
            )
              return true;
          }
        }
      }
    }

    return false;
  }
}

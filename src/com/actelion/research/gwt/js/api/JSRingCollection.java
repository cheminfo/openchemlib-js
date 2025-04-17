package com.actelion.research.gwt.js.api;

import jsinterop.annotations.*;

import com.actelion.research.chem.RingCollection;
import com.actelion.research.chem.StereoMolecule;

@JsType(name = "RingCollection")
public class JSRingCollection {
  public static final int MAX_SMALL_RING_SIZE = 7;

  private static final int MODE_SMALL_RINGS = 1;
  private static final int MODE_LARGE_RINGS = 2;
  private static final int MODE_AROMATICITY = 4;
  public static final int MODE_SMALL_RINGS_ONLY = MODE_SMALL_RINGS;
  public static final int MODE_SMALL_AND_LARGE_RINGS = MODE_SMALL_RINGS | MODE_LARGE_RINGS;
  public static final int MODE_SMALL_RINGS_AND_AROMATICITY = MODE_SMALL_RINGS | MODE_AROMATICITY;
  public static final int MODE_SMALL_AND_LARGE_RINGS_AND_AROMATICITY = MODE_SMALL_RINGS | MODE_LARGE_RINGS
      | MODE_AROMATICITY;

  private RingCollection coll;

  @JsIgnore
  public JSRingCollection(RingCollection input) {
    coll = input;
  }

  public int getAtomRingSize(int atom) {
    return coll.getAtomRingSize(atom);
  }

  public int getBondRingSize(int bond) {
    return coll.getBondRingSize(bond);
  }

  public int getSize() {
    return coll.getSize();
  }

  public int[] getRingAtoms(int ringNo) {
    return coll.getRingAtoms(ringNo);
  }

  public int[] getRingBonds(int ringNo) {
    return coll.getRingBonds(ringNo);
  }

  public int getRingSize(int ringNo) {
    return coll.getRingSize(ringNo);
  }

  public boolean isAromatic(int ringNo) {
    return coll.isAromatic(ringNo);
  }

  public boolean isDelocalized(int ringNo) {
    return coll.isDelocalized(ringNo);
  }

  public int getAtomIndex(int ringNo, int atom) {
    return coll.getAtomIndex(ringNo, atom);
  }

  public int getBondIndex(int ringNo, int bond) {
    return coll.getBondIndex(ringNo, bond);
  }

  public int validateMemberIndex(int ringNo, int index) {
    return coll.validateMemberIndex(ringNo, index);
  }

  public int getHeteroPosition(int ringNo) {
    return coll.getHeteroPosition(ringNo);
  }

  public boolean isAtomMember(int ringNo, int atom) {
    return coll.isAtomMember(ringNo, atom);
  }

  public boolean isBondMember(int ringNo, int bond) {
    return coll.isBondMember(ringNo, bond);
  }

  public int getSharedRing(int bond1, int bond2) {
    return coll.getSharedRing(bond1, bond2);
  }

  public void determineAromaticity(boolean[] isAromatic, boolean[] isDelocalized, int[] heteroPosition,
      boolean includeTautomericBonds) {
    coll.determineAromaticity(isAromatic, isDelocalized, heteroPosition, includeTautomericBonds);
  }

  public boolean qualifiesAsAmideTypeBond(int bond) {
    return coll.qualifiesAsAmideTypeBond(bond);
  }
}

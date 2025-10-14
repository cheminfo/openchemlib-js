package com.actelion.research.gwt.js.api;

import jsinterop.annotations.*;
import java.util.ArrayList;
import com.actelion.research.chem.SSSearcher;

@JsType(name = "SSSearcher")
public class JSSSSearcher {
  private SSSearcher searcher;

  // CONSTANTS TO DEFINE KIND OF SIMILARITY BETWEEN ATOMS AND BONDS
  public final static int cMatchAtomCharge = 1;
  public final static int cMatchAtomMass = 2;
  public final static int cMatchDBondToDelocalized = 4;
  public final static int cMatchAromDBondToDelocalized = 8;

  /*
   * For index match modes we need to consider the following: - If fragment C is SS of fragment B
   * and B is SS of molecule A then C must be SS of A. This implies that if X is SS of Y then X is
   * in all respects equal or less exactly defined as Y. Consequently, if the SS operator matches
   * any double bonds to aromatic double bonds and matches aromatic double bonds to delocaliced
   * bonds, it must match any double bond to delocalized bonds (cMatchAromDBondToDelocalized cannot
   * be used for index creation). Example: key C=C-N-C=C, query pyrol, molecule indole, key would
   * match pyrol but not indol!!! - match modes used for the actual atom by atom check must be more
   * or equally restrictive than the match mode used for index creation. Otherwise, index keys may
   * filter out molecules which would be considered a match with the less strict matching conditions
   * of the atom by atom check.
   */

  // match mode to be used for index creation
  public static final int cIndexMatchMode = cMatchDBondToDelocalized;
  public static final int cDefaultMatchMode = cMatchAromDBondToDelocalized;

  public final static int cCountModeExistence = 1; // check only, don't create matchList
  public final static int cCountModeFirstMatch = 2; // create matchList with just one match
  public final static int cCountModeSeparated = 3; // create list of all non-overlapping matches /
                                                   // not optimized for maximum match count
  public final static int cCountModeOverlapping = 4; // create list not containing multiple matches
                                                     // sharing exactly the same atoms
  public final static int cCountModeRigorous = 5; // create list of all possible matches neglecting
                                                  // any symmetries
  public final static int cCountModeUnique = 6; // create list of all distinguishable matches
                                                // considering symmetries

  public JSSSSearcher(int matchMode) {
    // todo: how to deal with default matchMode: cDefaultMatchMode
    this.searcher = new SSSearcher(matchMode);
  }

  public void setMol(JSMolecule fragment, JSMolecule molecule) {
    setMolecule(molecule);
    setFragment(fragment);
  }

  public int findFragmentInMolecule(int countMode, int matchMode) {
    // todo: how to deal with default values
    // countMode = cCountModeOverlapping;
    // matchMode = cDefaultMatchMode;
    return this.searcher.findFragmentInMolecule(countMode, matchMode);
  }

  public ArrayList<int[]> getMatchList() {
    return this.searcher.getMatchList();
  }

  public void setFragment(JSMolecule fragment) {
    this.searcher.setFragment(fragment.getStereoMolecule());
  }

  public void setMolecule(JSMolecule molecule) {
    this.searcher.setMolecule(molecule.getStereoMolecule());
  }

  public boolean isFragmentInMolecule() {
    return this.searcher.isFragmentInMolecule();
  }
}

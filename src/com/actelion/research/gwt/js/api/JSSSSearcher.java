package com.actelion.research.gwt.js.api;

import com.actelion.research.chem.SSSearcher;
import com.actelion.research.gwt.js.utils.*;
import com.google.gwt.core.client.JavaScriptObject;
import java.util.ArrayList;
import jsinterop.annotations.*;

@JsType(name = "SSSearcher")
public class JSSSSearcher {
  private SSSearcher searcher;
  private int mMatchMode;

  public JSSSSearcher(JavaScriptObject options) {
    int matchMode = getMatchMode(options);
    mMatchMode = matchMode;
    searcher = new SSSearcher(matchMode);
  }

  private native int getMatchMode(JavaScriptObject options)
  /*-{
    options = options || {};
    var matchMode = 0;
    if (options.matchAtomCharge === true) matchMode |= @com.actelion.research.chem.SSSearcher::cMatchAtomCharge;
    if (options.matchAtomMass === true) matchMode |= @com.actelion.research.chem.SSSearcher::cMatchAtomMass;
    if (options.matchDBondToDelocalized === true) matchMode |= @com.actelion.research.chem.SSSearcher::cMatchDBondToDelocalized;
    if (options.matchAromDBondToDelocalized === true ||
        // Default is true to match default behavior of the Java SSSearcher.
        options.matchAromDBondToDelocalized === undefined) matchMode |= @com.actelion.research.chem.SSSearcher::cMatchAromDBondToDelocalized;
    return matchMode;
  }-*/;

  public void setMol(JSMolecule fragment, JSMolecule molecule) {
    setMolecule(molecule);
    setFragment(fragment);
  }

  public int findFragmentInMolecule(JavaScriptObject options) {
    int countMode = getCountMode(options);
    return this.searcher.findFragmentInMolecule(countMode, mMatchMode);
  }

  private native int getCountMode(JavaScriptObject options)
  /*-{
    options = options || {};
    var countMode = options.countMode || 'overlapping';
    switch (countMode) {
      case 'overlapping': return @com.actelion.research.chem.SSSearcher::cCountModeOverlapping;
      case 'existence':  return @com.actelion.research.chem.SSSearcher::cCountModeExistence;
      case 'firstMatch':  return @com.actelion.research.chem.SSSearcher::cCountModeFirstMatch;
      case 'separated':  return @com.actelion.research.chem.SSSearcher::cCountModeSeparated;
      case 'rigorous':  return @com.actelion.research.chem.SSSearcher::cCountModeRigorous;
      case 'unique':  return @com.actelion.research.chem.SSSearcher::cCountModeUnique;
      default: throw new Error('invalid count mode: ' + countMode);
    }
  }-*/;

  public JavaScriptObject getMatchList() {
    return Util.convertIntArrayArrayList(this.searcher.getMatchList());
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

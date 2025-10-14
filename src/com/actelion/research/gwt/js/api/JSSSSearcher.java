package com.actelion.research.gwt.js.api;

import jsinterop.annotations.*;
import java.util.ArrayList;
import com.actelion.research.chem.SSSearcher;

@JsType(name = "SSSearcher")
public class JSSSSearcher {
  private SSSearcher searcher = new SSSearcher();

  public JSSSSearcher() {}

  public void setMol(JSMolecule fragment, JSMolecule molecule) {
    setMolecule(molecule);
    setFragment(fragment);
  }

  public void findFragmentInMolecule() {
    this.searcher.findFragmentInMolecule();
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

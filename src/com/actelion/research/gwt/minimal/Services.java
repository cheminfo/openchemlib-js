package com.actelion.research.gwt.minimal;

import com.actelion.research.chem.SSSearcherWithIndex;

public class Services {

  private static Services instance = null;

  private SSSearcherWithIndex sSSearcherWithIndex = null;

  private Services() {
  }

  public static Services getInstance() {
    if (instance == null) {
      instance = new Services();
    }
    return instance;
  }

  public SSSearcherWithIndex getSSSearcherWithIndex() {
    if (sSSearcherWithIndex == null) {
      sSSearcherWithIndex = new SSSearcherWithIndex();
    }
    return sSSearcherWithIndex;
  }
}

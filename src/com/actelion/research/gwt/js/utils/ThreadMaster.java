package com.actelion.research.gwt.js.utils;

public class ThreadMaster implements com.actelion.research.calc.ThreadMaster {
  private static ThreadMaster instance = null;

  public static ThreadMaster getInstance() {
    if (instance == null) {
      instance = new ThreadMaster();
    }
    return instance;
  }

  public boolean threadMustDie() {
    return false;
  }
}

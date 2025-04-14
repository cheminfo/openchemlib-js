package com.actelion.research.gwt.js.utils;

import com.actelion.research.chem.prediction.ToxicityPredictor;
import com.actelion.research.chem.prediction.DruglikenessPredictor;
import com.actelion.research.calc.ThreadMaster;

public class Services2 {

  private static Services2 instance = null;

  private DruglikenessPredictor druglikenessPredictor = null;
  private ToxicityPredictor toxicityPredictor = null;
  private ThreadMaster threadMaster = null;

  private Services2() {
  }

  public static Services2 getInstance() {
    if (instance == null) {
      instance = new Services2();
    }
    return instance;
  }

  public ToxicityPredictor getToxicityPredictor() {
    if (toxicityPredictor == null) {
      toxicityPredictor = new ToxicityPredictor();
    }
    return toxicityPredictor;
  }

  public DruglikenessPredictor getDruglikenessPredictor() {
    if (druglikenessPredictor == null) {
      druglikenessPredictor = new DruglikenessPredictor();
    }
    return druglikenessPredictor;
  }

  public ThreadMaster getThreadMaster() {
    if (threadMaster == null) {
      threadMaster = new CustomThreadMaster();
    }
    return threadMaster;
  }

  private class CustomThreadMaster implements ThreadMaster {
    public boolean threadMustDie() {
      return false;
    }
  }

}

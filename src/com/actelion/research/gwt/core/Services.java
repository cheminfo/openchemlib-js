package com.actelion.research.gwt.core;

import com.actelion.research.chem.prediction.ToxicityPredictor;
import com.actelion.research.chem.prediction.DruglikenessPredictor;
import com.actelion.research.calc.ThreadMaster;

public class Services {

  private static Services instance = null;

  private DruglikenessPredictor druglikenessPredictor = null;
  private ToxicityPredictor toxicityPredictor = null;
  private ThreadMaster threadMaster = null;

  private Services() {
  }

  public static Services getInstance() {
    if (instance == null) {
      instance = new Services();
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

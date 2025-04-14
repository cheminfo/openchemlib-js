package com.actelion.research.gwt.js.api;

import jsinterop.annotations.*;

import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.reaction.Reactor;

@JsType(name = "Reactor")
public class JSReactor {
  private Reactor reactor;

  public JSReactor(JSReaction reaction) {
    reactor = new Reactor(reaction.getReaction(), true);
  }

  public boolean setReactant(int no, JSMolecule reactant) {
    return reactor.setReactant(no, reactant.getStereoMolecule());
  }

  public JSMolecule[][] getProducts() {
    StereoMolecule[][] products = reactor.getProducts();

    if (products == null || products.length == 0) {
      return new JSMolecule[0][0];
    }

    JSMolecule[][] jsProducts = new JSMolecule[products.length][products[0].length];

    for (int i = 0; i < products.length; i++) {
      for (int j = 0; j < products[0].length; j++) {
        jsProducts[i][j] = new JSMolecule(products[i][j]);
      }
    }
    return jsProducts;
  }
}

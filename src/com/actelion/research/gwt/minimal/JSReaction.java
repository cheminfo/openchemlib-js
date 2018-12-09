package com.actelion.research.gwt.minimal;

import com.actelion.research.chem.*;
import com.actelion.research.chem.reaction.*;
import jsinterop.annotations.*;

@JsType(name = "Reaction")
public class JSReaction {
  private Reaction oclReaction;

  public static JSReaction create() {
    return new JSReaction(new Reaction());
  }

  public static JSReaction fromMolecules(JSMolecule[] mol, int reactantCount) {
    StereoMolecule[] oclMol = new StereoMolecule[mol.length];
    for (int i = 0; i < mol.length; i++) {
      oclMol[i] = mol[i].getStereoMolecule();
    }
    return new JSReaction(new Reaction(oclMol, reactantCount));
  }

  public static JSReaction fromSmiles(String smiles) throws Exception {
    Reaction reaction = new SmilesParser().parseReaction(smiles.getBytes());
    return new JSReaction(reaction);
  }

  @JsIgnore
  public JSReaction(Reaction reaction) {
    oclReaction = reaction;
  }

  public String toSmiles() {
    return IsomericSmilesCreator.createReactionSmiles(oclReaction);
  }

  public JSReaction clone() {
    return new JSReaction(new Reaction(oclReaction));
  }

  public void clear() {
    oclReaction.clear();
  }

  public void removeCatalysts() {
    oclReaction.removeCatalysts();
  }

  public boolean isEmpty() {
    return oclReaction.isEmpty();
  }

  public void setFragment(boolean f) {
    oclReaction.setFragment(f);
  }

  public boolean isFragment() {
    return oclReaction.isFragment();
  }

  public JSMolecule getReactant(int no) {
    return new JSMolecule(oclReaction.getReactant(no));
  }

  public int getReactants() {
    return oclReaction.getReactants();
  }

  public JSMolecule getProduct(int no) {
    return new JSMolecule(oclReaction.getProduct(no));
  }

  public int getProducts() {
    return oclReaction.getProducts();
  }

  public JSMolecule getCatalyst(int no) {
    return new JSMolecule(oclReaction.getCatalyst(no));
  }

  public int getCatalysts() {
    return oclReaction.getCatalysts();
  }

  public int getMolecules() {
    return oclReaction.getMolecules();
  }

  public JSMolecule getMolecule(int no) {
    return new JSMolecule(oclReaction.getMolecule(no));
  }

  public void addReactant(JSMolecule reactant) {
    oclReaction.addReactant(reactant.getStereoMolecule());
  }

  public void addReactantAt(JSMolecule reactant, int position) {
    oclReaction.addReactant(reactant.getStereoMolecule(), position);
  }

  public void addProduct(JSMolecule product) {
    oclReaction.addProduct(product.getStereoMolecule());
  }

  public void addProductAt(JSMolecule product, int position) {
    oclReaction.addProduct(product.getStereoMolecule(), position);
  }

  public void addCatalyst(JSMolecule catalyst) {
    oclReaction.addCatalyst(catalyst.getStereoMolecule());
  }

  public void addCatalystAt(JSMolecule catalyst, int position) {
    oclReaction.addCatalyst(catalyst.getStereoMolecule(), position);
  }

  public String getName() {
    return oclReaction.getName();
  }

  public void setName(String name) {
    oclReaction.setName(name);
  }

  public double getAverageBondLength() {
    return oclReaction.getAverageBondLength();
  }

  public boolean isReactionLayoutRequired() {
    return oclReaction.isReactionLayoutRequired();
  }

  public boolean isPerfectlyMapped() {
    return oclReaction.isPerfectlyMapped();
  }

  public int getHighestMapNo() {
    return oclReaction.getHighestMapNo();
  }

  public void validateMapping() throws Exception {
    oclReaction.validateMapping();
  }

  public boolean[] getReactionCenterMapNos() {
    return oclReaction.getReactionCenterMapNos();
  }

  public JSReaction getMergedCopy() {
    return new JSReaction(oclReaction.getMergedCopy());
  }
}

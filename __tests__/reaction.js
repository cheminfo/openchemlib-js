'use strict';

const { Molecule, Reaction } = require('../minimal');

describe('Reaction class', () => {
  it('should create an empty Reaction', () => {
    const reaction = Reaction.create();
    expect(reaction.getReactants()).toBe(0);
    expect(reaction.getProducts()).toBe(0);
    expect(reaction.getCatalysts()).toBe(0);
  });

  it('should be able to add molecules', () => {
    const reaction = Reaction.create();
    const reactant1 = Molecule.fromSmiles('C');
    const reactant2 = Molecule.fromSmiles('O');
    const product = Molecule.fromSmiles('CO');
    const catalyst = Molecule.fromSmiles('[Fe]');
    reaction.addReactant(reactant1);
    reaction.addReactant(reactant2);
    reaction.addProduct(product);
    reaction.addCatalyst(catalyst);
    expect(reaction.getReactants()).toBe(2);
    expect(reaction.getProducts()).toBe(1);
    expect(reaction.getCatalysts()).toBe(1);
  });

  it('should be able to create from array', () => {
    const reactant1 = Molecule.fromSmiles('C');
    const reactant2 = Molecule.fromSmiles('O');
    const product = Molecule.fromSmiles('CO');
    const reaction = Reaction.fromMolecules([reactant1, reactant2, product], 2);
    expect(reaction.getReactants()).toBe(2);
    expect(reaction.getProducts()).toBe(1);
  });

  it('should work with SMILES', () => {
    const reactant1 = Molecule.fromSmiles('C');
    const reactant2 = Molecule.fromSmiles('O');
    const product = Molecule.fromSmiles('CO');
    const reaction = Reaction.fromMolecules([reactant1, reactant2, product], 2);

    const smiles = reaction.toSmiles();
    expect(smiles).toBe('C.O>>CO');

    const newReaction = Reaction.fromSmiles(smiles);
    expect(newReaction.getReactants()).toBe(1);
    expect(newReaction.getProducts()).toBe(1);

    expect(newReaction.getReactant(0).toSmiles()).toBe('C.O');
    expect(newReaction.getProduct(0).toSmiles()).toBe('CO');
  });
});

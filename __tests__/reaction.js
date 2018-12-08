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
});

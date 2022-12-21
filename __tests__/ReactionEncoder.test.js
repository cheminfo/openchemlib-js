'use strict';

const { ReactionEncoder, Molecule, Reaction } = require('../core');

describe('ReactionEncoder class', () => {
  it('should be able to encode and decode using string', () => {
    const reactant1 = Molecule.fromSmiles('C');
    const reactant2 = Molecule.fromSmiles('O');
    const product = Molecule.fromSmiles('CO');
    const reaction = Reaction.fromMolecules([reactant1, reactant2, product], 2);
    const string = ReactionEncoder.encode(reaction);
    const newReaction = ReactionEncoder.decode(string);
    expect(newReaction.getReactants()).toBe(2);
    expect(newReaction.getProducts()).toBe(1);
  });
});

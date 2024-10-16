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

  it('should be able to decode using string from idcodeexplorer', () => {
    const idcode =
      'gOp@DjWkB@@@ gOp@DjWkB@@@ gOp@DjWkB@@@!fhy@@@LdbbbTQQRYhTg^@@``@`@@@##!RbGwW_Wx@_c}~O}]}v`@@ !Rb@KW@gx?_`A~@M\\Bv`@@ !R|Gq~_{]||Owp?Wy?v`@@ !R_c~H?M_|uwvH_Xa}_`CW_]_|_c~H?M_|uwwW_]_|u?sZ@@@##';
    const reaction = ReactionEncoder.decode(idcode);
    expect(reaction.getReactants()).toBe(3);
    expect(reaction.getProducts()).toBe(1);
  });
});

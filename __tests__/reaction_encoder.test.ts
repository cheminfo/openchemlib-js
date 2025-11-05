import { assert, expect, test } from 'vitest';

import { Molecule, Reaction, ReactionEncoder } from '../lib/index.js';

test('should be able to encode and decode using string', () => {
  const reactant1 = Molecule.fromSmiles('C');
  const reactant2 = Molecule.fromSmiles('O');
  const product = Molecule.fromSmiles('CO');
  const reaction = Reaction.fromMolecules([reactant1, reactant2, product], 2);
  const string = ReactionEncoder.encode(reaction);
  assert(string);
  const newReaction = ReactionEncoder.decode(string);
  assert(newReaction);
  expect(newReaction.getReactants()).toBe(2);
  expect(newReaction.getProducts()).toBe(1);
});

test('should be able to decode using string from idcodeexplorer', () => {
  const idcode =
    'gOp@DjWkB@@@ gOp@DjWkB@@@ gOp@DjWkB@@@!fhy@@@LdbbbTQQRYhTg^@@``@`@@@##!RbGwW_Wx@_c}~O}]}v`@@ !Rb@KW@gx?_`A~@M\\Bv`@@ !R|Gq~_{]||Owp?Wy?v`@@ !R_c~H?M_|uwvH_Xa}_`CW_]_|_c~H?M_|uwwW_]_|u?sZ@@@##';
  const reaction = ReactionEncoder.decode(idcode);
  assert(reaction);
  expect(reaction.getReactants()).toBe(3);
  expect(reaction.getProducts()).toBe(1);
});

test('should support reaction with only reactants', () => {
  const reaction = Reaction.fromMolecules(
    [Molecule.fromSmiles('C'), Molecule.fromSmiles('CC')],
    2,
  );
  const encoded = ReactionEncoder.encode(reaction);
  assert(encoded);
  const reaction2 = ReactionEncoder.decode(encoded);
  assert(reaction2);
  expect(reaction2.getReactants()).toBe(2);
  expect(reaction2.getProducts()).toBe(0);
});

test('should support reaction with only products', () => {
  const reaction = Reaction.fromMolecules(
    [Molecule.fromSmiles('C'), Molecule.fromSmiles('CC')],
    0,
  );
  const encoded = ReactionEncoder.encode(reaction);
  assert(encoded);
  const reaction2 = ReactionEncoder.decode(encoded);
  assert(reaction2);
  expect(reaction2.getReactants()).toBe(0);
  expect(reaction2.getProducts()).toBe(2);
});

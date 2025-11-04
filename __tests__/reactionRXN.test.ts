import { expect, test } from 'vitest';

import { Molecule, Reaction } from '../lib/index.js';

test('RXN from / to', () => {
  const reaction = Reaction.create();
  const methane = Molecule.fromSmiles('C');
  const ethane = Molecule.fromSmiles('CC');
  reaction.addReactant(methane);
  reaction.addReactant(ethane);

  const rxn = reaction.toRxn();
  console.log(rxn);

  const reaction2 = Reaction.fromRxn(rxn);
  expect(reaction2.getReactants()).toBe(2);
  console.log(reaction2.toRxn());
});

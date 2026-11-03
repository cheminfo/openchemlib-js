import { expect, test } from 'vitest';

import { Reaction } from '#lib';

test('an esterification is mapped, the acid losing its hydroxy oxygen', () => {
  const reaction = Reaction.fromSmiles('CC(=O)O.OCC>>CC(=O)OCC.O');
  const { score, rule } = reaction.autoMap();

  expect(score).toBeCloseTo(-3.6, 5);
  expect(rule).toBeNull();
  expect(reaction.toSmiles()).toBe(
    '[CH3:1][CH2:2][OH:3].[CH3:4][C:5]([OH:7])=[O:6]>>[CH3:1][CH2:2][O:3][C:5]([CH3:4])=[O:6].[OH2:7]',
  );
});

test('a Diels-Alder reaction is mapped by its chemical rule', () => {
  const reaction = Reaction.fromSmiles('C=CC=C.C=C>>C1=CCCCC1');
  const { rule } = reaction.autoMap();

  expect(rule).toBe('Diels-Alder_S');
  // The SMILES parser keeps the diene and the dienophile as one reactant.
  expect(mapNumbers(reaction)).toStrictEqual([
    [4, 6, 5, 3, 1, 2],
    [6, 5, 3, 1, 2, 4],
  ]);
});

test('atoms paired by hand stay paired', () => {
  // The hydroxy oxygen of the acid, atom 3 of the one reactant, is paired by
  // hand with the ester oxygen, atom 3 of the one product, against the mapping
  // the mapper would choose.
  const reaction = Reaction.fromSmiles('CC(=O)[OH:9].OCC>>CC(=O)[O:9]CC.O');
  reaction.autoMap();
  const [reactant, product] = mapNumbers(reaction);

  expect(reactant?.[3]).toBe(1);
  expect(product?.[3]).toBe(1);
});

function mapNumbers(reaction: Reaction): number[][] {
  const numbers: number[][] = [];
  for (let index = 0; index < reaction.getMolecules(); index++) {
    const molecule = reaction.getMolecule(index);
    const molecules: number[] = [];
    for (let atom = 0; atom < molecule.getAllAtoms(); atom++) {
      molecules.push(molecule.getAtomMapNo(atom));
    }
    numbers.push(molecules);
  }
  return numbers;
}

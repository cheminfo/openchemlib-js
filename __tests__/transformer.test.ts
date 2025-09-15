import { describe, expect, it } from 'vitest';

import { Molecule, Transformer } from '../lib/index.js';

describe('Transformer', () => {
  it('transform single in double bond', () => {
    const reagent = Molecule.fromSmiles('CC');
    reagent.setFragment(true);
    reagent.setAtomMapNo(0, 0);
    reagent.setAtomMapNo(1, 1);
    const product = Molecule.fromSmiles('C=C');
    product.setFragment(true);
    product.setAtomMapNo(0, 0);
    product.setAtomMapNo(1, 1);

    const transformer = new Transformer(reagent, product, 'ether');

    const molecule = Molecule.fromSmiles('CCCC');
    const nbTransforms = transformer.setMolecule(molecule, 0);

    expect(nbTransforms).toBe(3);
    const smiles = [];
    for (let i = 0; i < nbTransforms; i++) {
      const copy = molecule.getCompactCopy();
      transformer.applyTransformation(copy, i);
      smiles.push(copy.toIsomericSmiles());
    }

    expect(smiles).toStrictEqual(['CCC=C', 'CC=CC', 'CCC=C']);
  });
});

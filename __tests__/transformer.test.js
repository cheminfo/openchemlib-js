import { describe, expect, it } from 'vitest';

import { Molecule, Transformer } from '../lib/index.js';

describe('Transformer', () => {
  it('transform single in double bond', () => {
    let reagent = Molecule.fromSmiles('CC');
    reagent.setFragment(true);
    reagent.setAtomMapNo(0, 0);
    reagent.setAtomMapNo(1, 1);
    let product = Molecule.fromSmiles('C=C');
    product.setFragment(true);
    product.setAtomMapNo(0, 0);
    product.setAtomMapNo(1, 1);

    const transformer = new Transformer(reagent, product, 'ether');

    let molecule = Molecule.fromSmiles('CCCC');
    const nbTransforms = transformer.setMolecule(molecule);

    expect(nbTransforms).toBe(3);
    const smiles = [];
    for (let i = 0; i < nbTransforms; i++) {
      const copy = molecule.getCompactCopy();
      transformer.applyTransformation(copy, i);
      smiles.push(copy.toSmiles());
    }

    expect(smiles).toStrictEqual(['C=CCC', 'CC=CC', 'CCC=C']);
  });
});

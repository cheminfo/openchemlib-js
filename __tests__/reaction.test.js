import { describe, expect, it } from 'vitest';

import { Molecule, Reaction } from '../minimal';

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
    const catalyst = Molecule.fromSmiles('[Fe]');
    const reaction = Reaction.fromMolecules([reactant1, reactant2, product], 2);
    reaction.addCatalyst(catalyst);

    const smiles = reaction.toSmiles();
    expect(smiles).toBe('C.O>[Fe]>CO');

    const newReaction = Reaction.fromSmiles(smiles);
    expect(newReaction.getReactants()).toBe(1);
    expect(newReaction.getProducts()).toBe(1);
    expect(newReaction.getCatalysts()).toBe(1);

    expect(newReaction.getReactant(0).toSmiles()).toBe('C.O');
    expect(newReaction.getProduct(0).toSmiles()).toBe('CO');
    expect(newReaction.getCatalyst(0).toSmiles()).toBe('[Fe]');
  });

  it('should load and create RXN files', () => {
    const rxn = `$RXN


JME Molecular Editor
  2  2
$MOL
CC(C)C(=O)Cl.CCNCC>>CCNCC.Cl
JME 2017-11-16 Fri Nov 09 13:48:29 GMT+100 2018

  6  5  0  0  0  0  0  0  0  0999 V2000
    2.4460    0.0000    0.0000 Cl  0  0  0  0  0  0  0  0  0  0  0  0
    2.4350    1.4040    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    3.6307    2.1060    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0
    1.2175    2.1060    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    1.2066    3.5100    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    0.0000    1.4150    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
  1  2  1  0  0  0  0
  2  3  2  0  0  0  0
  2  4  1  0  0  0  0
  4  5  1  0  0  0  0
  4  6  1  0  0  0  0
M  END
$MOL
CC(C)C(=O)Cl.CCNCC>>CCNCC.Cl
JME 2017-11-16 Fri Nov 09 13:48:29 GMT+100 2018

  5  4  0  0  0  0  0  0  0  0999 V2000
    2.4467    0.0000    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0
    3.6508    0.7072    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    1.2233    0.7072    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    4.8550    0.0191    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    0.0000    0.0191    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
  1  2  1  0  0  0  0
  1  3  1  0  0  0  0
  2  4  1  0  0  0  0
  3  5  1  0  0  0  0
M  END
$MOL
CC(C)C(=O)Cl.CCNCC>>CCNCC.Cl
JME 2017-11-16 Fri Nov 09 13:48:29 GMT+100 2018

  5  4  0  0  0  0  0  0  0  0999 V2000
    2.4467    0.0000    0.0000 N   0  0  0  0  0  0  0  0  0  0  0  0
    3.6508    0.7072    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    1.2233    0.7072    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    4.8550    0.0191    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
    0.0000    0.0191    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
  1  2  1  0  0  0  0
  1  3  1  0  0  0  0
  2  4  1  0  0  0  0
  3  5  1  0  0  0  0
M  END
$MOL
CC(C)C(=O)Cl.CCNCC>>CCNCC.Cl
JME 2017-11-16 Fri Nov 09 13:48:29 GMT+100 2018

  1  0  0  0  0  0  0  0  0  0999 V2000
    0.0000    0.0000    0.0000 Cl  0  0  0  0  0  0  0  0  0  0  0  0
M  END
`;

    const reaction = Reaction.fromRxn(rxn);
    expect(reaction.getReactants()).toBe(2);
    expect(reaction.getProducts()).toBe(2);
    expect(reaction.getCatalysts()).toBe(0);

    expect(reaction.toRxn()).toMatchSnapshot();

    expect(reaction.toRxnV3()).toMatchSnapshot();
  });
});

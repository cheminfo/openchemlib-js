'use strict';

const { getMF } = require('openchemlib-utils');

const { Reactor, Molecule, Reaction, ReactionEncoder } = require('../core');

// the actelion reaction ID is encoded in the RXN file !!!
// this code has priority so if it is there the rest of the RXN file is ignored
// Currently we generate RXN files using ChemDraw

describe('Reactor class', () => {
  it('deshydratation', () => {
    const reaction = ReactionEncoder.decode(
      'eMHAIhH!eF@HhP#QF Qd#!R_vq?DqtJ_@ !R@Fp]Agp',
    );

    const reactor = new Reactor(reaction);

    const match = reactor.setReactant(0, Molecule.fromSmiles('OC(C)CCCO'));
    if (!match) return;

    const products = reactor.getProducts();

    const smiles = [];
    for (let i = 0; i < products.length; i++) {
      for (let j = 0; j < products[i].length; j++) {
        smiles.push(products[i][j].toSmiles());
      }
    }

    expect(smiles).toStrictEqual(['C(CCCO)=C', 'C(C)=CCCO', 'OC(C)CC=C']);
  });

  it('protonation, we add a H+ on the O', () => {
    const rxn = `$RXN
    
    

  1  1
$MOL

Actelion Java MolfileCreator 1.0

  3  2  0  0  0  0  0  0  0  0999 V2000
    1.7321   -0.5000   -0.0000 O   0  0  0  0  0  0  0  0  0  1  0  0
    0.8660   -0.0000   -0.0000 C   0  0  0  0  0  0  0  0  0  2  0  0
    0.0000   -0.5000   -0.0000 C   0  0  0  0  0  0  0  0  0  3  0  0
  1  2  1  0  0  0  0
  2  3  1  0  0  0  0
M  END
$MOL

Actelion Java MolfileCreator 1.0

  3  2  0  0  0  0  0  0  0  0999 V2000
    1.7321   -0.5000   -0.0000 O   0  3  0  0  0  0  0  0  0  1  0  0
    0.8660   -0.0000   -0.0000 C   0  0  0  0  0  0  0  0  0  2  0  0
    0.0000   -0.5000   -0.0000 C   0  0  0  0  0  0  0  0  0  3  0  0
  1  2  1  0  0  0  0
  2  3  1  0  0  0  0
M  END`;

    const reaction = Reaction.fromRxn(rxn);

    const reactor = new Reactor(reaction);

    const match = reactor.setReactant(0, Molecule.fromSmiles('OC(C)CCCO'));
    if (!match) return;

    const products = reactor.getProducts();

    const smiles = [];
    for (let i = 0; i < products.length; i++) {
      for (let j = 0; j < products[i].length; j++) {
        smiles.push(products[i][j].toSmiles());
      }
    }
    expect(smiles).toStrictEqual(['[OH2+]C(CCCO)C', 'OC(C)CCC[OH2+]']);
  });

  it('charge O and break bond', () => {
    const rxn = `$RXN
test.rxn
  ChemDraw01042314002D

  1  2
$MOL



  3  2  0  0  0  0  0  0  0  0999 V2000
   -0.7145   -0.2062    0.0000 C   0  0  0  0  0  0  0  0  0  2  0  0
   -0.0000    0.2062    0.0000 C   0  0  0  0  0  0  0  0  0  1  0  0
    0.7145   -0.2062    0.0000 O   0  0  0  0  0  0  0  0  0  3  0  0
  1  2  1  0        4
  2  3  1  0        0
M  END
$MOL



  1  0  0  0  0  0  0  0  0  0999 V2000
    0.0000    0.0000    0.0000 C   0  4  0  0  0  0  0  0  0  2  0  0
M  RAD  1   1   2
M  END
$MOL



  2  1  0  0  0  0  0  0  0  0999 V2000
   -0.3268    0.2643    0.0000 C   0  0  0  0  0  0  0  0  0  1  0  0
    0.3268   -0.2643    0.0000 O   0  3  0  0  0  0  0  0  0  3  0  0
  1  2  1  0        0
M  CHG  1   2   1
M  END
`;

    const reaction = Reaction.fromRxn(rxn);

    const reactor = new Reactor(reaction);

    const match = reactor.setReactant(0, Molecule.fromSmiles('OC(C)CCCO'));
    if (!match) return;

    const products = reactor.getProducts();

    const smiles = [];
    const mfs = [];
    for (let i = 0; i < products.length; i++) {
      for (let j = 0; j < products[i].length; j++) {
        smiles.push(products[i][j].toSmiles());
        mfs.push(getMF(products[i][j]).mf);
      }
    }
    expect(mfs).toStrictEqual([
      'CH3',
      'C4H11O2(+)',
      'C3H7O',
      'C2H7O(+)',
      'C4H9O',
      'CH5O(+)',
    ]);
  });
});

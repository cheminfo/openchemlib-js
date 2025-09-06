import fs from 'node:fs';

import { describe, expect, it } from 'vitest';

import OCL, { Molecule } from '../lib/index.js';

describe('from and to SMILES', () => {
  it.each(['C', 'COCOC', 'c1cc2cccc3c4cccc5cccc(c(c1)c23)c54'])(
    'fromSmiles: %s',
    (smiles) => {
      const mol = Molecule.fromSmiles(smiles);
      expect(Molecule.fromSmiles(mol.toSmiles()).getIDCode()).toBe(
        mol.getIDCode(),
      );
    },
  );

  it('should throw on syntax error', () => {
    expect(() => {
      Molecule.fromSmiles('ABC');
    }).toThrow(/SmilesParser: unknown element label found/);
  });

  it.each([
    ['C1CC1', 'C1CC1'],
    ['OCC', 'CCO'],
    ['[CH3][CH2][OH]', 'CCO'],
    ['C-C-O', 'CCO'],
    ['C(O)C', 'CCO'],
    ['OC(=O)C(Br)(Cl)N', 'NC(C(O)=O)(Cl)Br'],
    ['ClC(Br)(N)C(=O)O', 'NC(C(O)=O)(Cl)Br'],
    ['O=C(O)C(N)(Br)Cl', 'NC(C(O)=O)(Cl)Br'],
  ])('toIsomericSmiles: %s', (input, output) => {
    const mol = Molecule.fromSmiles(input);
    expect(mol.toIsomericSmiles()).toBe(output);
  });

  it('smiles options', () => {
    const mol = Molecule.fromSmiles('C1=CC=CC=C1');
    expect(mol.toIsomericSmiles()).toBe('c1ccccc1');
    expect(mol.toIsomericSmiles({ kekulizedOutput: true })).toBe('C1=CC=CC=C1');
    expect(mol.toIsomericSmiles({ createSmarts: true })).toBe('c1ccccc1');
    mol.setAtomMapNo(0, 1);
    expect(mol.toIsomericSmiles({ includeMapping: true })).toBe(
      'c1cc[cH:1]cc1',
    );

    const fragment = Molecule.fromSmiles('C1=CC=CC=C1C');
    fragment.setFragment(true);
    fragment.setAtomicNo(6, 1);
    expect(fragment.toIsomericSmiles({ createSmarts: true })).toBe(
      'c1cc[c;!H0]cc1',
    );
  });
});

it('smarts options', () => {
  const fragment = Molecule.fromSmiles('C1=CC=CC=C1C');
  fragment.setFragment(true);
  fragment.setAtomicNo(6, 1);
  expect(fragment.toSmarts()).toBe('c1cc[c;!H0]cc1');
});

describe('toSVG', () => {
  it('should create a valid SVG with requested size and id', () => {
    const mol = Molecule.fromSmiles('CCOCCO');
    const svg = mol.toSVG(300, 150, 'myId');

    expect(svg).toContain('width="300px" height="150px"');
    expect(svg).toContain('"myId:Bond:0"');
    expect(svg).toContain('"myId:Atom:0"');
  });

  it('should support custom style', () => {
    const mol = Molecule.fromSmiles('CCOCCO');
    const svg = mol.toSVG(300, 300, 'myId', {
      strokeWidth: 2,
      fontWeight: 'bold',
    });

    expect(svg).toContain('font-weight="bold"');
    expect(svg).toContain('stroke-width="2"');
  });

  it('should work with wrong coordinates (all 0)', () => {
    const mol = Molecule.fromSmiles('CCOCCO');
    for (let i = 0; i < mol.getAllAtoms(); i++) {
      mol.setAtomX(i, 0);
      mol.setAtomY(i, 0);
      mol.setAtomZ(i, 0);
    }
    const svg = mol.toSVG(300, 150, 'myId');

    for (let i = 0; i < mol.getAllAtoms(); i++) {
      expect(mol.getAtomX(i)).toBe(0);
      expect(mol.getAtomY(i)).toBe(0);
      expect(mol.getAtomZ(i)).toBe(0);
    }
    expect(svg).toContain('width="300px" height="150px"');
  });

  it('should autoCrop', () => {
    const mol = Molecule.fromSmiles('CCOCCO');

    expect(mol.toSVG(300, 150, 'myId', { autoCrop: true })).toMatchSnapshot();
    expect(
      mol.toSVG(300, 150, 'myId', {
        autoCrop: true,
        autoCropMargin: 25,
      }),
    ).toMatchSnapshot();
  });

  it('should throw with no size', () => {
    const mol = Molecule.fromSmiles('CCOCCO');

    // @ts-expect-error testing error
    expect(() => mol.toSVG()).toThrow(
      'Molecule#toSVG requires width and height to be specified',
    );
  });

  it('should not throw with zero size', () => {
    const mol = Molecule.fromSmiles('CCOCCO');

    expect(mol.toSVG(0, 0)).toMatchSnapshot();
  });
});

describe('Molecule', () => {
  it('medley', () => {
    const idcode =
      'enYXNH@MHDAELem`OCIILdhhdiheCDlieKDdefndZRVVjjfjjfjihJBbb@@@';
    let mol = Molecule.fromIDCode(idcode);

    const molfile = mol.toMolfile();
    mol = Molecule.fromMolfile(molfile);
    expect(mol.getIDCode()).toBe(idcode);

    const smiles = mol.toIsomericSmiles();
    mol = Molecule.fromSmiles(smiles);
    expect(mol.getIDCode()).toBe(idcode);
  });

  it('molfile V3', () => {
    const idcode =
      'enYXNH@MHDAELem`OCIILdhhdiheCDlieKDdefndZRVVjjfjjfjihJBbb@@@';
    let mol = Molecule.fromIDCode(idcode);

    let molfileV3 = mol.toMolfileV3();
    mol = Molecule.fromMolfile(molfileV3);
    expect(mol.getIDCode()).toBe(idcode);

    molfileV3 = mol.toMolfileV3();
    mol = Molecule.fromMolfile(molfileV3);
    expect(mol.getIDCode()).toBe(idcode);
  });

  it('fromMolfileWithAtomMap', () => {
    const molfile = fs.readFileSync(
      `${import.meta.dirname}/data/molfileWithHMap.mol`,
      'utf8',
    );
    const result = Molecule.fromMolfileWithAtomMap(molfile);
    expect(result.molecule).toBeInstanceOf(Molecule);
    expect(result.molecule.toIsomericSmiles()).toBe('CCCCC');
    expect(result.map).toHaveLength(result.molecule.getAllAtoms());
    expect(new Set(result.map).size).toBe(result.molecule.getAllAtoms());
  });

  it('addMissingChirality', () => {
    let mol = Molecule.fromSmiles('CC(Cl)CC');
    expect(mol.getChiralText()).toBe('unknown chirality');
    mol.addMissingChirality();
    expect(mol.getChiralText()).toBe('both enantiomers');

    mol = Molecule.fromSmiles('CC(Cl)CC');
    mol.addMissingChirality(Molecule.cESRTypeOr);
    expect(mol.getChiralText()).toBe('this or other enantiomer');

    mol = Molecule.fromSmiles('CC(Cl)CC');
    mol.addMissingChirality(Molecule.cESRTypeAbs);
    expect(mol.getChiralText()).toBe('this enantiomer');
  });

  it('getCanonizedIDCode', () => {
    let idcode = 'didH@@RYm^Fh@BHx@';
    let mol = Molecule.fromIDCode(idcode);
    expect(
      mol.getCanonizedIDCode(Molecule.CANONIZER_DISTINGUISH_RACEMIC_OR_GROUPS),
    ).toBe(idcode);
    mol.inventCoordinates();
    expect(
      mol.getCanonizedIDCode(Molecule.CANONIZER_DISTINGUISH_RACEMIC_OR_GROUPS),
    ).toBe(idcode);

    idcode = 'didH@@RYm^Fh@BHX@';
    mol = Molecule.fromIDCode(idcode);
    expect(
      mol.getCanonizedIDCode(Molecule.CANONIZER_DISTINGUISH_RACEMIC_OR_GROUPS),
    ).toBe(idcode);
    mol.inventCoordinates();
    expect(
      mol.getCanonizedIDCode(Molecule.CANONIZER_DISTINGUISH_RACEMIC_OR_GROUPS),
    ).toBe(idcode);
  });

  it('getFinalRanks', () => {
    const molecule = Molecule.fromSmiles('CCC');
    molecule.setAtomicNo(0, 8);
    const atoms = [];
    const ranks = [...molecule.getFinalRanks(0)];
    for (let i = 0; i < molecule.getAllAtoms(); i++) {
      atoms.push(molecule.getAtomLabel(i), ranks[i]);
    }
    expect(atoms).toStrictEqual(['O', 3, 'C', 2, 'C', 1]);
    const molecule2 = Molecule.fromSmiles('CCC');
    molecule2.setAtomicNo(2, 8);
    const atoms2 = [];
    const ranks2 = [...molecule2.getFinalRanks(0)];
    for (let i = 0; i < molecule2.getAllAtoms(); i++) {
      atoms2.push(molecule2.getAtomLabel(i), ranks2[i]);
    }
    expect(atoms2).toStrictEqual(['C', 1, 'C', 2, 'O', 3]);
  });

  it('getFinalRanks of xMolecule', () => {
    const molecule = Molecule.fromSmiles('CCCO');
    const xAtomicNumber = Molecule.getAtomicNoFromLabel(
      'X',
      Molecule.cPseudoAtomX,
    );
    molecule.addImplicitHydrogens();
    for (let i = 0; i < molecule.getAllAtoms(); i++) {
      // hydrogens are not taken into account during canonization, we need to change them with an atom with a valence of 1
      if (molecule.getAtomicNo(i) === 1) {
        molecule.setAtomicNo(i, xAtomicNumber);
      }
    }

    const ranks = [...molecule.getFinalRanks(0)];
    expect(ranks).toStrictEqual([3, 1, 2, 4, 11, 10, 9, 5, 6, 7, 8, 12]);
  });

  it('getOCL', () => {
    const molecule = new Molecule(0, 0);
    expect(molecule.getOCL()).toBe(OCL);
  });
});

describe('fromText', () => {
  it('should parse molfile V2000', () => {
    const molfile = fs.readFileSync(
      `${import.meta.dirname}/data/molfile_v2000.mol`,
      'utf8',
    );
    expect(Molecule.fromText(molfile)?.toIsomericSmiles()).toBe('COCO');
  });

  it('should parse molfile V3000', () => {
    const molfile = fs.readFileSync(
      `${import.meta.dirname}/data/molfile_v3000.mol`,
      'utf8',
    );
    expect(Molecule.fromText(molfile)?.toIsomericSmiles()).toBe('COCO');
  });

  it('should parse SMILES', () => {
    expect(Molecule.fromText('COCO')?.toIsomericSmiles()).toBe('COCO');
  });

  it('should parse ID code and coordinates', () => {
    const idCode = 'gC``Adij@@';
    const coordinates = '!B@Fq?[@@S';
    const molecule = Molecule.fromText(`${idCode} ${coordinates}`);
    expect(molecule?.getIDCodeAndCoordinates()).toStrictEqual({
      idCode,
      coordinates,
    });
  });

  it('should return null on invalid text', () => {
    expect(Molecule.fromText('')).toBeNull();
    expect(Molecule.fromText('BAD')).toBeNull();
  });
});

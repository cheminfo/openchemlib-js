'use strict';

const fs = require('fs');

const Molecule = require('../minimal').Molecule;

describe('from and to SMILES', () => {
  it.each(['C', 'COCOC', 'c1cc2cccc3c4cccc5cccc(c(c1)c23)c54'])(
    'fromSmiles: %s',
    (smiles) => {
      const mol = Molecule.fromSmiles(smiles);
      expect(Molecule.fromSmiles(mol.toSmiles()).getIDCode()).toBe(
        mol.getIDCode()
      );
    }
  );

  it('should throw on syntax error', () => {
    expect(function () {
      Molecule.fromSmiles('ABC');
    }).toThrow(/SmilesParser: unknown element label found/);
  });

  it.each([
    ['C1CC1', 'C1CC1'],
    ['OCC', 'CCO'],
    ['[CH3][CH2][OH]', 'CCO'],
    ['C-C-O', 'CCO'],
    ['C(O)C', 'CCO']
    // Next examples don't work rigth now. See https://github.com/Actelion/openchemlib/issues/35
    // ['OC(=O)C(Br)(Cl)N', 'NC(Cl)(Br)C(=O)O'],
    // ['ClC(Br)(N)C(=O)O', 'NC(Cl)(Br)C(=O)O'],
    // ['O=C(O)C(N)(Br)Cl', 'NC(Cl)(Br)C(=O)O']
  ])('toIsomericSmiles: %s', (input, output) => {
    const mol = Molecule.fromSmiles(input);
    expect(mol.toIsomericSmiles()).toBe(output);
  });
});

describe('Molecule', function () {
  it('medley', function () {
    const idcode =
      'enYXNH@MHDAELem`OCIILdhhdiheCDlieKDdefndZRVVjjfjjfjihJBbb@@@';
    let mol = Molecule.fromIDCode(idcode);

    const molfile = mol.toMolfile();
    mol = Molecule.fromMolfile(molfile);
    expect(mol.getIDCode()).toBe(idcode);

    const smiles = mol.toSmiles();
    mol = Molecule.fromSmiles(smiles);
    expect(mol.getIDCode()).toBe(idcode);
  });

  it('toSVG', function () {
    const mol = Molecule.fromSmiles('CCOCCO');
    let svg = mol.toSVG(300, 150, 'myId');
    expect(svg).toContain('width="300px" height="150px"');
    expect(svg).toContain('"myId:Bond:0"');
    expect(svg).toContain('"myId:Atom:0"');

    svg = mol.toSVG(300, 300, 'myId', {
      strokeWidth: 2,
      fontWeight: 'bold'
    });
    expect(svg).toContain('font-weight="bold"');
    expect(svg).toContain('stroke-width:2');
  });

  it('toSVG with autoCrop', () => {
    const mol = Molecule.fromSmiles('CCOCCO');
    let svg = mol.toSVG(300, 150, 'myId', { autoCrop: true });
    expect(svg).toMatchSnapshot();

    svg = mol.toSVG(300, 150, 'myId', { autoCrop: true, autoCropMargin: 50 });
    expect(svg).toMatchSnapshot();
  });

  it('molfile V3', function () {
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
      `${__dirname}/data/molfileWithHMap.mol`,
      'utf8'
    );
    const result = Molecule.fromMolfileWithAtomMap(molfile);
    expect(result.molecule).toBeInstanceOf(Molecule);
    expect(result.molecule.toSmiles()).toBe('CCCCC');
    expect(result.map).toHaveLength(result.molecule.getAllAtoms());
    expect(new Set(result.map).size).toBe(result.molecule.getAllAtoms());
  });

  it('addMissingChirality', () => {
    let mol = Molecule.fromSmiles('CC(Cl)CC');
    expect(mol.getChiralText()).toBe('unknown chirality');
    mol.addMissingChirality();
    expect(mol.getChiralText()).toBe('racemate');

    mol = Molecule.fromSmiles('CC(Cl)CC');
    mol.addMissingChirality(Molecule.cESRTypeOr);
    expect(mol.getChiralText()).toBe('this or other enantiomer');

    mol = Molecule.fromSmiles('CC(Cl)CC');
    mol.addMissingChirality(Molecule.cESRTypeAbs);
    expect(mol.getChiralText()).toBe('this enantiomer');
  });

  it.skip('getCanonizedIDCode', () => {
    let idcode = 'didH@@RYm^Fh@BHx@';
    let mol = Molecule.fromIDCode(idcode);
    expect(
      mol.getCanonizedIDCode(Molecule.CANONIZER_DISTINGUISH_RACEMIC_OR_GROUPS)
    ).toBe(idcode);
    mol.inventCoordinates();
    expect(
      mol.getCanonizedIDCode(Molecule.CANONIZER_DISTINGUISH_RACEMIC_OR_GROUPS)
    ).toBe(idcode);

    idcode = 'didH@@RYm^Fh@BHX@';
    mol = Molecule.fromIDCode(idcode);
    expect(
      mol.getCanonizedIDCode(Molecule.CANONIZER_DISTINGUISH_RACEMIC_OR_GROUPS)
    ).toBe(idcode);
    mol.inventCoordinates();
    expect(
      mol.getCanonizedIDCode(Molecule.CANONIZER_DISTINGUISH_RACEMIC_OR_GROUPS)
    ).toBe(idcode);
  });
});

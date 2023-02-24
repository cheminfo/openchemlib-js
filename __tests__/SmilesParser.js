'use strict';

const { SmilesParser, Molecule } = require('../minimal');

it.each([
  ['COCO', { atoms: 4 }],
  ['CC(=O)O', { atoms: 4 }],
])('should parse normal SMILES %s', (smiles, { atoms }) => {
  const parser = new SmilesParser();
  const mol = parser.parseMolecule(smiles);
  expect(mol.isFragment()).toBe(false);
  expect(mol.getAllAtoms()).toBe(atoms);
});

it.each([
  ['COCO', { atoms: 4 }],
  ['[C,c]', { atoms: 1 }],
  ['[R0]', { atoms: 1 }],
])('should parse SMARTS %s', (smarts, { atoms }) => {
  const parser = new SmilesParser({ smartsMode: 'smarts' });
  const mol = parser.parseMolecule(smarts);
  expect(mol.isFragment()).toBe(true);
  expect(mol.getAllAtoms()).toBe(atoms);
});

it('should guess SMARTS', () => {
  const parser = new SmilesParser({ smartsMode: 'guess' });
  const molNormal = parser.parseMolecule('COCO');
  expect(molNormal.isFragment()).toBe(false);
  const molSmarts = parser.parseMolecule('[C,c]');
  expect(molSmarts.isFragment()).toBe(true);
});

it('should optionally not parse CACTVS', () => {
  const cactvs = '[C;z3]';
  const parserWithCactvs = new SmilesParser({
    smartsMode: 'smarts',
  });
  const molecule = parserWithCactvs.parseMolecule(cactvs);
  expect(molecule.getAllAtoms()).toBe(1);
  const parserWithoutCactvs = new SmilesParser({
    smartsMode: 'smarts',
    noCactvs: true,
  });
  expect(() => {
    parserWithoutCactvs.parseMolecule(cactvs);
  }).toThrow(/'z'/);
});

it('should optionally skip coordinate templates', () => {
  const cubane = 'C12C3C4C1C5C2C3C45';
  const molecule = new Molecule(0, 0);
  const parserWithTemplates = new SmilesParser();
  parserWithTemplates.setRandomSeed(1);
  parserWithTemplates.parseMolecule(cubane, { molecule });
  const coords1 = molecule.getIDCoordinates();
  const parserWithoutTemplates = new SmilesParser({
    skipCoordinateTemplates: true,
  });
  parserWithoutTemplates.setRandomSeed(1);
  parserWithoutTemplates.parseMolecule(cubane, { molecule });
  expect(molecule.getIDCoordinates()).not.toBe(coords1);
});

it('should optionally make hydrogens explicit', () => {
  const smiles = '[CH4]';
  const molecule = new Molecule(0, 0);
  const parserWithoutExplicitH = new SmilesParser({ smartsMode: 'smarts' });
  parserWithoutExplicitH.parseMolecule(smiles, { molecule });
  expect(molecule.getAllAtoms()).toBe(1);
  const parserWithExplicitH = new SmilesParser({
    smartsMode: 'smarts',
    makeHydrogenExplicit: true,
  });
  parserWithExplicitH.parseMolecule(smiles, { molecule });
  expect(molecule.getAllAtoms()).toBe(5);
});

it('should allow to set random seed', () => {
  const smiles = 'C1CN2CCN1CC2';
  const parser = new SmilesParser();
  const coords1 = parser.parseMolecule(smiles).getIDCoordinates();
  const coords2 = parser.parseMolecule(smiles).getIDCoordinates();
  // TODO: Find a SMILES that goes through the random branch of coordinate invention.
  // expect(coords1).not.toBe(coords2);
  expect(coords1).toBe(coords2);
  parser.setRandomSeed(1);
  const coords3 = parser.parseMolecule(smiles).getIDCoordinates();
  const coords4 = parser.parseMolecule(smiles).getIDCoordinates();
  expect(coords3).toBe(coords4);
});

it('should create smarts warnings', () => {
  const parserWithoutWarnings = new SmilesParser({
    smartsMode: 'smarts',
  });
  parserWithoutWarnings.parseMolecule('[R9]');
  expect(parserWithoutWarnings.getSmartsWarning()).toBe('');
  const parserWithWarnings = new SmilesParser({
    smartsMode: 'smarts',
    createSmartsWarnings: true,
  });
  parserWithWarnings.parseMolecule('[R9]');
  expect(parserWithWarnings.getSmartsWarning()).toBe(
    'Unresolved SMARTS features: R9',
  );
});

it('should parse into the passed molecule', () => {
  const parser = new SmilesParser();
  const molecule = new Molecule(0, 0);
  const mol = parser.parseMolecule('COCO', { molecule });
  expect(mol.toSmiles()).toBe('COCO');
  expect(mol).toBe(molecule);
});

it('should should optionally not invent coordinates', () => {
  const parser = new SmilesParser();
  const molecule = new Molecule(0, 0);
  parser.parseMolecule('COCO', { molecule, noCoordinates: false });
  expect(molecule.getAtomX(0)).not.toBe(0);
  parser.parseMolecule('COCO', { molecule, noCoordinates: true });
  expect(molecule.getAtomX(0)).toBe(0);
});

it('should should optionally not parse stereo features', () => {
  const parser = new SmilesParser();
  const vitaminA = 'C/C(=C\\CO)/C=C/C=C(/C)\\C=C\\C1=C(C)CCCC1(C)C';
  const molecule = new Molecule(0, 0);
  parser.parseMolecule(vitaminA, { molecule, noStereo: false });
  const idCodeWithStereo = molecule.getIDCode();
  parser.parseMolecule(vitaminA, { molecule, noStereo: true });
  const idCodeWithoutStereo = molecule.getIDCode();
  expect(idCodeWithStereo).not.toBe(idCodeWithoutStereo);
});

it('should parse reactions', () => {
  const parser = new SmilesParser();
  const reaction = parser.parseReaction('COCO>>COC.O');
  expect(reaction.getProducts()).toBe(1);
  expect(reaction.getReactants()).toBe(1);
  expect(reaction.getCatalysts()).toBe(0);
  expect(reaction.toSmiles()).toBe('COCO>>COC.O');
});

import { expect, test } from 'vitest';

import { Molecule } from '#lib';

test.each([
  ['COCO', { atoms: 4 }],
  ['CC(=O)O', { atoms: 4 }],
])('should parse normal SMILES %s', (smiles, { atoms }) => {
  const mol = Molecule.fromSmiles(smiles);

  expect(mol.isFragment()).toBe(false);
  expect(mol.getAllAtoms()).toBe(atoms);
});

test('should optionally not invent coordinates', () => {
  const mol = Molecule.fromSmiles('COCO', { noCoordinates: false });

  expect(mol.getAtomX(0)).not.toBe(0);

  const molNoCoords = Molecule.fromSmiles('COCO', { noCoordinates: true });

  expect(molNoCoords.getAtomX(0)).toBe(0);
});

test('should optionally not parse stereo features', () => {
  const vitaminA = String.raw`C/C(=C\CO)/C=C/C=C(/C)\C=C\C1=C(C)CCCC1(C)C`;
  const molWithStereo = Molecule.fromSmiles(vitaminA);
  const molWithoutStereo = Molecule.fromSmiles(vitaminA, { noStereo: true });

  expect(molWithStereo.getIDCode()).not.toBe(molWithoutStereo.getIDCode());
});

test('should parse SMARTS with smartsMode', () => {
  const mol = Molecule.fromSmiles('[C,c]', {
    smartsMode: 'smarts',
    noCoordinates: true,
  });

  expect(mol.isFragment()).toBe(true);
  expect(mol.getAllAtoms()).toBe(1);
});

test('should guess SMARTS mode', () => {
  const molNormal = Molecule.fromSmiles('COCO', {
    smartsMode: 'guess',
    noCoordinates: true,
  });

  expect(molNormal.isFragment()).toBe(false);

  const molSmarts = Molecule.fromSmiles('[C,c]', {
    smartsMode: 'guess',
    noCoordinates: true,
  });

  expect(molSmarts.isFragment()).toBe(true);
});

test('should optionally not parse CACTVS', () => {
  const cactvs = '[C;z3]';
  const mol = Molecule.fromSmiles(cactvs, {
    smartsMode: 'smarts',
    noCoordinates: true,
  });

  expect(mol.getAllAtoms()).toBe(1);
  expect(() => {
    Molecule.fromSmiles(cactvs, {
      smartsMode: 'smarts',
      noCactvs: true,
      noCoordinates: true,
    });
  }).toThrow(/'z'/);
});

test('should optionally make hydrogens explicit', () => {
  const mol = Molecule.fromSmiles('[CH4]', { noCoordinates: true });

  expect(mol.getAllAtoms()).toBe(1);

  const molExplicitH = Molecule.fromSmiles('[CH4]', {
    makeHydrogenExplicit: true,
    noCoordinates: true,
  });

  expect(molExplicitH.getAllAtoms()).toBe(5);
});

test('should optionally skip coordinate templates', () => {
  const cubane = 'C12C3C4C1C5C2C3C45';
  const mol = Molecule.fromSmiles(cubane);
  const coords1 = mol.getIDCoordinates();
  const molNoTemplates = Molecule.fromSmiles(cubane, {
    skipCoordinateTemplates: true,
  });

  expect(molNoTemplates.getIDCoordinates()).not.toBe(coords1);
});

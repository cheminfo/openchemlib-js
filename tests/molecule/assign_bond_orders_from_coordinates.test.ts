import { readFileSync } from 'node:fs';
import { join } from 'node:path';

import { beforeAll, expect, test } from 'vitest';

import { Molecule, Resources } from '#lib';

beforeAll(() => {
  Resources.registerFromNodejs();
});

const NO_STEREO = Molecule.CANONIZER_NEGLECT_ANY_STEREO_INFORMATION;
const METAL_LIGAND = Molecule.cBondTypeMetalLigand;
const ASPIRIN = 'CC(=O)Oc1ccccc1C(=O)O';

function readMolecule(name: string, hydrogens = true) {
  const molecule = Molecule.fromMolfile(
    readFileSync(join(import.meta.dirname, 'data', `${name}.mol`), 'utf8'),
  );
  if (!hydrogens) molecule.removeExplicitHydrogens();
  return molecule;
}

function stripOrdersAndCharges(molecule: Molecule) {
  for (let bond = 0; bond < molecule.getAllBonds(); bond++) {
    molecule.setBondType(bond, Molecule.cBondTypeSingle);
  }
  for (let atom = 0; atom < molecule.getAllAtoms(); atom++) {
    molecule.setAtomCharge(atom, 0);
  }
}

function idCode(smiles: string) {
  return Molecule.fromSmiles(smiles).getCanonizedIDCode(NO_STEREO);
}

function fragmentIDCodes(molecule: Molecule) {
  return molecule
    .getFragments()
    .map((fragment) => fragment.getCanonizedIDCode(NO_STEREO))
    .toSorted();
}

function expectedFragments(smiles: string) {
  return smiles.split('.').map(idCode).toSorted();
}

function countBondsOfType(molecule: Molecule, bondType: number) {
  let count = 0;
  for (let bond = 0; bond < molecule.getAllBonds(); bond++) {
    if (molecule.getBondType(bond) === bondType) count++;
  }
  return count;
}

function bondPairs(molecule: Molecule) {
  const pairs: string[] = [];
  for (let bond = 0; bond < molecule.getAllBonds(); bond++) {
    const atom1 = molecule.getBondAtom(0, bond);
    const atom2 = molecule.getBondAtom(1, bond);
    pairs.push(`${Math.min(atom1, atom2)}-${Math.max(atom1, atom2)}`);
  }
  return pairs.toSorted();
}

test.each([
  { hydrogens: true, bonds: true },
  { hydrogens: false, bonds: true },
  { hydrogens: true, bonds: false },
  { hydrogens: false, bonds: false },
])('aspirin (hydrogens: $hydrogens, bonds: $bonds)', ({ hydrogens, bonds }) => {
  const molecule = readMolecule('aspirin', hydrogens);
  stripOrdersAndCharges(molecule);
  molecule.setAllBonds(bonds ? molecule.getAllBonds() : 0);

  molecule.assignBondOrdersFromCoordinates();

  expect(molecule.getCanonizedIDCode(NO_STEREO)).toBe(idCode(ASPIRIN));
});

test.each([true, false])(
  'metal-ligand bonds charge the chlorides and the zinc (hydrogens: %s)',
  (hydrogens) => {
    const molecule = readMolecule('zinc_dichloro_dipyridine', hydrogens);

    molecule.assignBondOrdersFromCoordinates();

    expect(fragmentIDCodes(molecule)).toStrictEqual(
      expectedFragments('c1ccncc1.c1ccncc1.[Cl-].[Cl-].[Zn+2]'),
    );
    expect(countBondsOfType(molecule, METAL_LIGAND)).toBe(4);
  },
);

test.each([true, false])(
  'a metal without bonds is left alone (hydrogens: %s)',
  (hydrogens) => {
    const molecule = readMolecule('zinc_dichloro_dipyridine', hydrogens);
    molecule.setAllBonds(0);

    molecule.assignBondOrdersFromCoordinates();

    expect(fragmentIDCodes(molecule)).toStrictEqual(
      expectedFragments('c1ccncc1.c1ccncc1.Cl.Cl.[Zn]'),
    );
  },
);

test.each([true, false])(
  'ammonia ligands of copper stay neutral (hydrogens: %s)',
  (hydrogens) => {
    const molecule = readMolecule('tetraammine_copper', hydrogens);

    molecule.assignBondOrdersFromCoordinates();

    expect(fragmentIDCodes(molecule)).toStrictEqual(
      expectedFragments('N.N.N.N.[Cu]'),
    );
  },
);

test.each([true, false])(
  'ferrocene gives two cyclopentadienides (hydrogens: %s)',
  (hydrogens) => {
    const molecule = readMolecule('ferrocene', hydrogens);

    molecule.assignBondOrdersFromCoordinates();

    expect(fragmentIDCodes(molecule)).toStrictEqual(
      expectedFragments('[cH-]1cccc1.[cH-]1cccc1.[Fe]'),
    );
    expect(countBondsOfType(molecule, METAL_LIGAND)).toBe(10);
  },
);

test('keeps the bonds it is given and creates none', () => {
  const molecule = readMolecule('aspirin');
  molecule.ensureHelperArrays(Molecule.cHelperNeighbours);
  // A missing C–H bond, as for an atom across a symmetry operation, stays out.
  molecule.deleteBond(molecule.getAllBonds() - 1);
  molecule.ensureHelperArrays(Molecule.cHelperNeighbours);
  stripOrdersAndCharges(molecule);
  const pairs = bondPairs(molecule);

  molecule.assignBondOrdersFromCoordinates();

  expect(bondPairs(molecule)).toStrictEqual(pairs);
});

test('gives the same molecule when called twice', () => {
  const molecule = readMolecule('aspirin');
  stripOrdersAndCharges(molecule);
  molecule.assignBondOrdersFromCoordinates();
  molecule.assignBondOrdersFromCoordinates();

  expect(molecule.getCanonizedIDCode(NO_STEREO)).toBe(idCode(ASPIRIN));
});

test('createBondsFromCoordinates finds every bond', () => {
  const molecule = readMolecule('aspirin');
  const pairs = bondPairs(molecule);
  molecule.setAllBonds(0);

  molecule.createBondsFromCoordinates();

  expect(bondPairs(molecule)).toStrictEqual(pairs);
  expect(countBondsOfType(molecule, Molecule.cBondTypeSingle)).toBe(
    pairs.length,
  );
});

test('createBondsFromCoordinates keeps existing bonds and duplicates none', () => {
  const molecule = readMolecule('aspirin');
  const pairs = bondPairs(molecule);
  molecule.setAllBonds(5);
  const kept = molecule.getBondType(0);

  molecule.createBondsFromCoordinates();

  expect(bondPairs(molecule)).toStrictEqual(pairs);
  expect(molecule.getBondType(0)).toBe(kept);
});

import { expect, test } from 'vitest';

import { Molecule } from '#lib';

test('aromatic bonds of a molfile become single and double bonds', () => {
  const benzene = Molecule.fromMolfile(
    aromaticRing(['C', 'C', 'C', 'C', 'C', 'C']),
  );
  const pyrrole = Molecule.fromMolfile(aromaticRing(['N', 'C', 'C', 'C', 'C']));

  expect(benzene.getMolecularFormula().formula).toBe('C6H12');
  expect(benzene.locateDelocalizedDoubleBonds()).toBe(true);
  expect(benzene.getMolecularFormula().formula).toBe('C6H6');
  expect(benzene.toIsomericSmiles({ kekulizedOutput: true })).toBe(
    'C1=CC=CC=C1',
  );

  expect(pyrrole.locateDelocalizedDoubleBonds()).toBe(true);
  expect(pyrrole.getMolecularFormula().formula).toBe('C4H5N');
  expect(pyrrole.toIsomericSmiles({ kekulizedOutput: true })).toBe('C1=CNC=C1');
});

test('a charge left out of the file may be added', () => {
  const pyridinium = Molecule.fromMolfile(
    aromaticRing(['N', 'C', 'C', 'C', 'C', 'C'], 'C'),
  );

  expect(
    pyridinium.locateDelocalizedDoubleBonds({ mayChangeAtomCharges: true }),
  ).toBe(true);
  expect(pyridinium.getAtomCharge(0)).toBe(1);
  expect(pyridinium.toIsomericSmiles({ kekulizedOutput: true })).toBe(
    'C[N+]1=CC=CC=C1',
  );
});

test('the bonds to resolve may be given', () => {
  const molecule = Molecule.fromSmiles('C1CCCCC1');
  const bonds = Array.from({ length: molecule.getAllBonds() }, () => true);

  expect(molecule.locateDelocalizedDoubleBonds({ bonds })).toBe(true);
  expect(molecule.getMolecularFormula().formula).toBe('C6H6');
});

// A ring whose bonds are all aromatic (type 4), with a substituent on its
// first atom when one is given.
function aromaticRing(symbols: string[], substituent?: string) {
  const count = symbols.length;
  const atoms = symbols.map((symbol, index) => {
    const angle = (2 * Math.PI * index) / count;
    return atomLine(symbol, Math.cos(angle), Math.sin(angle));
  });
  const bonds = symbols.map((_, index) =>
    bondLine(index + 1, ((index + 1) % count) + 1, 4),
  );
  if (substituent) {
    atoms.push(atomLine(substituent, 2, 0));
    bonds.push(bondLine(1, count + 1, 1));
  }
  const counts = `${String(atoms.length).padStart(3)}${String(bonds.length).padStart(3)}  0  0  0  0  0  0  0  0999 V2000`;
  return ['', '  test', '', counts, ...atoms, ...bonds, 'M  END'].join('\n');
}

function atomLine(symbol: string, x: number, y: number) {
  return `${x.toFixed(4).padStart(10)}${y.toFixed(4).padStart(10)}    0.0000 ${symbol.padEnd(3)} 0  0`;
}

function bondLine(from: number, to: number, type: number) {
  return `${String(from).padStart(3)}${String(to).padStart(3)}${String(type).padStart(3)}  0`;
}

import { expect, test } from 'vitest';

import { Molecule } from '../../index.js';

test('default encode atom labels', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(0, 'C1');
  molecule.setAtomCustomLabel(1, 'C2');
  molecule.setAtomCustomLabel(2, 'O3');
  const molfile = molecule.toMolfile();
  expect(molfile).toMatchInlineSnapshot(`
    "
    Actelion Java MolfileCreator 1.0

      3  2  0  0  0  0  0  0  0  0999 V2000
        1.7321   -0.5000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
        0.8660    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
        0.0000   -0.5000    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0
      1  2  1  0  0  0  0
      2  3  1  0  0  0  0
    M  STY  1   1 DAT
    M  SLB  1   1   1
    M  SAL   1  1   1
    M  SDT   1 NOSEARCH_OCL_CUSTOM_LABEL
    M  SDD   1     1.7321    0.5000    DA    ALL  1       5
    M  SED   1 C1
    M  STY  1   2 DAT
    M  SLB  1   2   2
    M  SAL   2  1   2
    M  SDT   2 NOSEARCH_OCL_CUSTOM_LABEL
    M  SDD   2     0.8660    0.0000    DA    ALL  1       5
    M  SED   2 C2
    M  STY  1   3 DAT
    M  SLB  1   3   3
    M  SAL   3  1   3
    M  SDT   3 NOSEARCH_OCL_CUSTOM_LABEL
    M  SDD   3     0.0000    0.5000    DA    ALL  1       5
    M  SED   3 O3
    M  END
    "
  `);
});

test('encode in V', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(0, 'C1');
  molecule.setAtomCustomLabel(1, 'C2');
  molecule.setAtomCustomLabel(2, 'O3');
  const molfile = molecule.toMolfile({
    includeCustomAtomLabelsAsVLines: true,
    customLabelPosition: 'superscript',
  });

  expect(molfile).toMatchInlineSnapshot(`
    "
    Actelion Java MolfileCreator 1.0

      3  2  0  0  0  0  0  0  0  0999 V2000
        1.7321   -0.5000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
        0.8660    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
        0.0000   -0.5000    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0
      1  2  1  0  0  0  0
      2  3  1  0  0  0  0
    M  STY  1   1 DAT
    M  SLB  1   1   1
    M  SAL   1  1   1
    M  SDT   1 NOSEARCH_OCL_CUSTOM_LABEL
    M  SDD   1     1.7321    0.5000    DA    ALL  1       5
    M  SED   1 ]C1
    M  STY  1   2 DAT
    M  SLB  1   2   2
    M  SAL   2  1   2
    M  SDT   2 NOSEARCH_OCL_CUSTOM_LABEL
    M  SDD   2     0.8660    0.0000    DA    ALL  1       5
    M  SED   2 ]C2
    M  STY  1   3 DAT
    M  SLB  1   3   3
    M  SAL   3  1   3
    M  SDT   3 NOSEARCH_OCL_CUSTOM_LABEL
    M  SDD   3     0.0000    0.5000    DA    ALL  1       5
    M  SED   3 ]O3
    V    1 ]C1
    V    2 ]C2
    V    3 ]O3
    M  END
    "
  `);
});

test('encode in A', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(0, 'C1');
  molecule.setAtomCustomLabel(1, 'C2');
  molecule.setAtomCustomLabel(2, 'O3');
  const molfile = molecule.toMolfile({
    includeCustomAtomLabelsAsALines: true,
  });

  expect(molfile).toMatchInlineSnapshot(`
    "
    Actelion Java MolfileCreator 1.0

      3  2  0  0  0  0  0  0  0  0999 V2000
        1.7321   -0.5000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
        0.8660    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
        0.0000   -0.5000    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0
      1  2  1  0  0  0  0
      2  3  1  0  0  0  0
    M  STY  1   1 DAT
    M  SLB  1   1   1
    M  SAL   1  1   1
    M  SDT   1 NOSEARCH_OCL_CUSTOM_LABEL
    M  SDD   1     1.7321    0.5000    DA    ALL  1       5
    M  SED   1 C1
    M  STY  1   2 DAT
    M  SLB  1   2   2
    M  SAL   2  1   2
    M  SDT   2 NOSEARCH_OCL_CUSTOM_LABEL
    M  SDD   2     0.8660    0.0000    DA    ALL  1       5
    M  SED   2 C2
    M  STY  1   3 DAT
    M  SLB  1   3   3
    M  SAL   3  1   3
    M  SDT   3 NOSEARCH_OCL_CUSTOM_LABEL
    M  SDD   3     0.0000    0.5000    DA    ALL  1       5
    M  SED   3 O3
    A    1
    C1
    A    2
    C2
    A    3
    O3
    M  END
    "
  `);
});

test('remove custom atom labels', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(0, 'C1');
  molecule.setAtomCustomLabel(1, 'C2');
  molecule.setAtomCustomLabel(2, 'O3');
  const molfile = molecule.toMolfile({
    removeCustomAtomLabels: true,
  });
  expect(molfile).toMatchInlineSnapshot(`
    "
    Actelion Java MolfileCreator 1.0

      3  2  0  0  0  0  0  0  0  0999 V2000
        1.7321   -0.5000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
        0.8660    0.0000    0.0000 C   0  0  0  0  0  0  0  0  0  0  0  0
        0.0000   -0.5000    0.0000 O   0  0  0  0  0  0  0  0  0  0  0  0
      1  2  1  0  0  0  0
      2  3  1  0  0  0  0
    M  END
    "
  `);
});

test('force superscript', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(1, 'C2');
  const molfile = molecule.toMolfile({
    customLabelPosition: 'superscript',
  });
  const molecule2 = Molecule.fromMolfile(molfile);
  expect(molecule2.getAtomCustomLabel(1)).toBe(']C2');
});

test('force normal', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(1, ']C2');
  const molfile = molecule.toMolfile({
    customLabelPosition: 'normal',
  });
  const molecule2 = Molecule.fromMolfile(molfile);
  expect(molecule2.getAtomCustomLabel(1)).toBe('C2');
});

test('force auto', () => {
  const molecule = Molecule.fromSmiles('CCO');
  molecule.setAtomCustomLabel(0, ']C1');
  molecule.setAtomCustomLabel(1, ']C2');
  molecule.setAtomCustomLabel(2, ']O3');
  const molfile = molecule.toMolfile({
    customLabelPosition: 'auto',
  });
  const molecule2 = Molecule.fromMolfile(molfile);
  expect(molecule2.getAtomCustomLabel(0)).toBe('C1');
  expect(molecule2.getAtomCustomLabel(1)).toBe('C2');
  expect(molecule2.getAtomCustomLabel(2)).toBe(']O3');
});

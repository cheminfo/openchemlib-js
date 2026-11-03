export function extendFromMolfile(Molecule) {
  const _fromMolfileWithAtomMap = Molecule.fromMolfileWithAtomMap;
  Molecule.fromMolfile = function fromMolfile(molfile, options = {}) {
    const { customLabelPosition } = options;
    // The parser moves explicit hydrogens after the other atoms, so the atom
    // numbers of the molfile go through the map to reach the molecule's.
    const { molecule, map } = _fromMolfileWithAtomMap.call(this, molfile);
    // what is eol ? \r\n or \n ?
    const eol = molfile.includes('\r\n') ? '\r\n' : '\n';
    const lines = molfile.split(eol);
    if (lines.length < 4 || !lines[3].includes('V2000')) {
      return molecule; // nothing to do
    }
    const atomOf = (molfileAtom) => map[molfileAtom - 1];
    const possibleLines = lines.slice(
      4 + molecule.getAllAtoms() + molecule.getAllBonds(),
    );
    // we should not forget that for A lines the value is on the next line
    for (let i = 0; i < possibleLines.length; i++) {
      const line = possibleLines[i];
      if (line.startsWith('A  ')) {
        const atom = atomOf(Number(line.slice(3)));
        const valueLine = possibleLines[i + 1]?.trim();
        i++;
        if (
          atom !== undefined &&
          valueLine &&
          !molecule.getAtomCustomLabel(atom)
        ) {
          molecule.setAtomCustomLabel(atom, valueLine);
        }
      }
      if (line.startsWith('V  ')) {
        const parts = line.split(' ').filter(Boolean);
        if (parts.length >= 3) {
          const atom = atomOf(Number(parts[1]));
          const label = parts.slice(2).join(' ');
          if (atom !== undefined && !molecule.getAtomCustomLabel(atom)) {
            molecule.setAtomCustomLabel(atom, label);
          }
        }
      }
      if (line.startsWith('M  ZZC')) {
        const atom = atomOf(Number(line.slice(7, 10).trim()));
        const label = line.slice(10).trim();
        if (atom !== undefined && label) {
          molecule.setAtomCustomLabel(atom, label);
        }
      }
    }
    molecule.changeCustomLabelPosition(customLabelPosition);
    return molecule;
  };
}

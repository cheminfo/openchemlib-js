import { changeMolfileCustomLabelPosition } from './utils/changeMolfileCustomLabelPosition.js';

export function extendFromMolfile(Molecule) {
  const _fromMolfile = Molecule.fromMolfile;
  Molecule.fromMolfile = function fromMolfile(molfile, options = {}) {
    const { customLabelPosition } = options;
    const molecule = _fromMolfile.call(this, molfile);
    // what is eol ? \r\n or \n ?
    const eol = molfile.includes('\r\n') ? '\r\n' : '\n';
    const lines = molfile.split(eol);
    if (lines.length < 4 || !lines[3].includes('V2000')) {
      return molecule; // nothing to do
    }
    const possibleLines = lines.slice(
      4 + molecule.getAllAtoms() + molecule.getAllBonds(),
    );
    // we should not forget that for A lines the value is on the next line
    for (let i = 0; i < possibleLines.length; i++) {
      const line = possibleLines[i];
      if (line.startsWith('A  ')) {
        const atom = Number(line.slice(3));
        const valueLine = possibleLines[i + 1]?.trim();
        i++;
        if (
          !Number.isNaN(atom) &&
          atom <= molecule.getAllAtoms() &&
          valueLine &&
          !molecule.getAtomCustomLabel(atom - 1)
        ) {
          molecule.setAtomCustomLabel(atom - 1, valueLine);
        }
      }
      if (line.startsWith('V  ')) {
        const parts = line.split(' ').filter(Boolean);
        if (parts.length >= 3) {
          const atom = Number(parts[1]);
          const label = parts.slice(2).join(' ');
          if (
            !Number.isNaN(atom) &&
            atom <= molecule.getAllAtoms() &&
            !molecule.getAtomCustomLabel(atom - 1)
          ) {
            molecule.setAtomCustomLabel(atom - 1, label);
          }
        }
      }
    }
    changeMolfileCustomLabelPosition(molecule, customLabelPosition);
    return molecule;
  };
}

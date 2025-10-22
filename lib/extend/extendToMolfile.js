import { changeMolfileCustomLabelPosition } from './utils/changeMolfileCustomLabelPosition.js';

const CUSTOM_ATOMS_LABELS_TAGS = [
  'M  STY',
  'M  SLB',
  'M  SAL',
  'M  SDT',
  'M  SDD',
  'M  SED',
];
/**
 * Extends the instance method toMolfile to add the possibility to include A and V lines for custom atom labels
 * @param {*} Molecule
 */
export function extendToMolfile(Molecule) {
  const _toMolfile = Molecule.prototype.toMolfile;
  Molecule.prototype.toMolfile = function toMolfile(options = {}) {
    let molecule = this.getCompactCopy();

    const {
      includeCustomAtomLabelsAsALines = false,
      includeCustomAtomLabelsAsVLines = false,
      customLabelPosition,
      removeCustomAtomLabels = false,
    } = options;
    changeMolfileCustomLabelPosition(molecule, customLabelPosition);

    const molfile = _toMolfile.call(molecule);

    if (
      !includeCustomAtomLabelsAsALines &&
      !includeCustomAtomLabelsAsVLines &&
      !removeCustomAtomLabels
    ) {
      return molfile; // nothing to do
    }

    const eol = molfile.includes('\r\n') ? '\r\n' : '\n';
    // need to add A or V lines just before M END
    let lines = molfile.split(eol);
    if (removeCustomAtomLabels) {
      lines = lines.filter(
        (line) => !CUSTOM_ATOMS_LABELS_TAGS.some((tag) => line.startsWith(tag)),
      );
    }
    if (lines.length < 4 || !lines[3].includes('V2000')) {
      return molfile; // nothing to do
    }
    const newLines = [];
    for (let i = 0; i < molecule.getAllAtoms(); i++) {
      const label = molecule.getAtomCustomLabel(i);
      if (label) {
        // need the atom number prepend with spaces to be 3 characters long
        const paddedAtomNumber = String(i + 1).padStart(3, ' ');
        if (includeCustomAtomLabelsAsALines) {
          newLines.push(`A  ${paddedAtomNumber}`, label);
        }
        if (includeCustomAtomLabelsAsVLines) {
          newLines.push(`V  ${paddedAtomNumber} ${label}`);
        }
      }
    }
    // insert newLines just before M  END
    const mEndIndex = lines.findIndex((line) => line.startsWith('M  END'));
    if (mEndIndex === -1) {
      return molfile; // nothing to do
    }
    lines.splice(mEndIndex, 0, ...newLines);
    return lines.join(eol);
  };
}

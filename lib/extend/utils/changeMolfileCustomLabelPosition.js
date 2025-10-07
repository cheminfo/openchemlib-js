export function changeMolfileCustomLabelPosition(
  molecule,
  customLabelPosition,
) {
  switch (customLabelPosition) {
    case 'superscript':
      for (let i = 0; i < molecule.getAllAtoms(); i++) {
        const customLabel = molecule.getAtomCustomLabel(i);
        if (customLabel && !customLabel.startsWith(']')) {
          molecule.setAtomCustomLabel(i, `]${customLabel}`);
        }
      }
      break;
    case 'normal':
      for (let i = 0; i < molecule.getAllAtoms(); i++) {
        const customLabel = molecule.getAtomCustomLabel(i);
        if (customLabel?.startsWith(']')) {
          molecule.setAtomCustomLabel(i, customLabel.slice(1));
        }
      }
      break;
    case 'auto':
      for (let i = 0; i < molecule.getAllAtoms(); i++) {
        const customLabel = molecule.getAtomCustomLabel(i);
        if (customLabel) {
          const atomLabel = molecule.getAtomLabel(i);
          if (atomLabel === 'C') {
            // normal
            if (customLabel.startsWith(']')) {
              molecule.setAtomCustomLabel(i, customLabel.slice(1));
            }
          } else if (!customLabel.startsWith(']')) {
            molecule.setAtomCustomLabel(i, `]${customLabel}`);
          }
        }
      }
      break;
    case undefined:
      // nothing to do
      break;
    default:
      break;
  }
}

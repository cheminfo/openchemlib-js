/**
 * Append the method changeCustomLabelPosition to the Molecule class in order
 * to change the position of custom labels according to the provided option
 * - 'superscript' will add a ] at the beginning of the custom label
 * - 'normal' will remove a leading ] if present
 * - 'auto' will set the label as superscript for non-carbon atoms and normal for carbon atoms
 * @param {*} Molecule
 */
export function appendChangeCustomLabelPosition(Molecule) {
  Molecule.prototype.changeCustomLabelPosition =
    function changeCustomLabelPosition(customLabelPosition) {
      switch (customLabelPosition) {
        case 'superscript':
          for (let i = 0; i < this.getAllAtoms(); i++) {
            const customLabel = this.getAtomCustomLabel(i);
            if (customLabel && !customLabel.startsWith(']')) {
              this.setAtomCustomLabel(i, `]${customLabel}`);
            }
          }
          break;
        case 'normal':
          for (let i = 0; i < this.getAllAtoms(); i++) {
            const customLabel = this.getAtomCustomLabel(i);
            if (customLabel?.startsWith(']')) {
              this.setAtomCustomLabel(i, customLabel.slice(1));
            }
          }
          break;
        case 'auto':
          for (let i = 0; i < this.getAllAtoms(); i++) {
            const customLabel = this.getAtomCustomLabel(i);
            if (customLabel) {
              const atomLabel = this.getAtomLabel(i);
              if (atomLabel === 'C') {
                // normal
                if (customLabel.startsWith(']')) {
                  this.setAtomCustomLabel(i, customLabel.slice(1));
                }
              } else if (!customLabel.startsWith(']')) {
                this.setAtomCustomLabel(i, `]${customLabel}`);
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
    };
}

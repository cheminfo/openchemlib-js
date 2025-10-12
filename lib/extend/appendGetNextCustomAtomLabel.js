/**
 * Will add the method getNextCustomAtomLabel to Molecule prototype
 * This method will return the next custom label that can be used for an atom
 * If the provided label is already used, it will try to increment it
 * If no label is provided, it will start from '1'
 * The incrementing works for numbers and letters (lower and uppercase)
 * If the label contains both letters and numbers, only the number part is incremented
 * If the label ends with Z or z, it will return '1' as next label
 * @param {*} Molecule
 */
export function appendGetNextCustomAtomLabel(Molecule) {
  Molecule.prototype.getNextCustomAtomLabel = function getNextCustomAtomLabel(
    label,
  ) {
    let nextLabel = label || '1';
    const existingLabels = new Set();
    for (let i = 0; i < this.getAllAtoms(); i++) {
      const existingLabel = this.getAtomCustomLabel(i);
      if (existingLabel) {
        existingLabels.add(existingLabel);
      }
    }
    let counter = 0; // just to avoid infinite loop, you never know
    while (existingLabels.has(nextLabel) && counter++ < 100) {
      nextLabel = getNextLabel(nextLabel);
    }
    return nextLabel;
  };
}

function getNextLabel(label) {
  // are there some numbers ?
  const match = label.match(/(\d+)/);
  if (match) {
    const number = Number.parseInt(match[1], 10);
    return label.replace(/(\d+)/, (number + 1).toString());
  }
  // is there a letter ? We take the last one and increase ascii code if it does not match Z or z
  // letter can be followed by ', " or other symbols
  // eg C', C", C` etc
  const match2 = label.match(/([a-yA-Y])([^a-zA-Z]*)$/);
  if (match2) {
    const char = match2[1];
    const nextChar = String.fromCharCode(char.charCodeAt(0) + 1);
    if (nextChar === 'Z' || nextChar === 'z') return '1';
    return label.replace(/([a-yA-Y])([^a-zA-Z]*)$/, `${nextChar}$2`);
  }
  return '1';
}

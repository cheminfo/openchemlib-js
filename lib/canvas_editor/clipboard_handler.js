'use strict';

class ClipboardHandler {
  copyMolecule(molecule) {
    const data = molecule.getIDCodeAndCoordinates();
    navigator.clipboard.writeText(`${data.idCode} ${data.coordinates}`);
  }

  pasteMolecule() {
    // TODO: find a way to implement this in a synchronous way.
    return null;
  }
}

module.exports = ClipboardHandler;

import { appendChangeCustomLabelPosition } from './appendChangeCustomLabelPosition.js';
import { appendGetNextCustomAtomLabel } from './appendGetNextCustomAtomLabel.js';
import { extendFromMolfile } from './extendFromMolfile.js';
import { extendToMolfile } from './extendToMolfile.js';
import { extendToRxn } from './extendToRxn.js';
import { extendToSVG } from './extendToSVG.js';

export function extendOCL(OCL) {
  const { ConformerGenerator, ForceFieldMMFF94, Molecule, Reaction } = OCL;

  ConformerGenerator.prototype.molecules = function* molecules() {
    let nextConformer;
    while ((nextConformer = this.getNextConformerAsMolecule()) !== null) {
      yield nextConformer;
    }
  };

  const defaultMinimiseOptions = {
    maxIts: 4000,
    gradTol: 1e-4,
    funcTol: 1e-6,
  };
  const _minimise = ForceFieldMMFF94.prototype._minimise;
  delete ForceFieldMMFF94.prototype._minimise;
  ForceFieldMMFF94.prototype.minimise = function minimise(options) {
    options = { ...defaultMinimiseOptions, ...options };
    return _minimise.call(
      this,
      options.maxIts,
      options.gradTol,
      options.funcTol,
    );
  };
  appendChangeCustomLabelPosition(Molecule);
  appendGetNextCustomAtomLabel(Molecule);
  extendFromMolfile(Molecule);
  extendToMolfile(Molecule);
  extendToRxn(Reaction, Molecule);
  extendToSVG(Molecule);

  function parseMoleculeFromText(text) {
    // Empty input
    if (!text) {
      return null;
    }

    // Molfile
    if (text.includes('V2000') || text.includes('V3000')) {
      return Molecule.fromMolfile(text);
    }

    // SMILES
    try {
      return Molecule.fromSmiles(text);
    } catch {
      // Ignore error.
    }

    // ID code
    try {
      return Molecule.fromIDCode(text);
    } catch {
      // Ignore error.
    }

    return null;
  }

  Molecule.fromText = function fromText(text) {
    const molecule = parseMoleculeFromText(text);
    if (molecule && molecule.getAllAtoms() > 0) {
      return molecule;
    }
    return null;
  };

  // For npellet/visualizer, that needs to handle multiple instances of OCL
  // with openchemlib-utils.
  Molecule.prototype.getOCL = function getOCL() {
    return OCL;
  };
}

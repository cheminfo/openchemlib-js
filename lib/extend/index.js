'use strict';

function extendOCL(OCL) {
  OCL.ConformerGenerator.prototype.molecules = function* molecules() {
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
  const _minimise = OCL.ForceFieldMMFF94.prototype._minimise;
  delete OCL.ForceFieldMMFF94.prototype._minimise;
  OCL.ForceFieldMMFF94.prototype.minimise = function minimise(options) {
    options = { ...defaultMinimiseOptions, ...options };
    return _minimise.call(
      this,
      options.maxIts,
      options.gradTol,
      options.funcTol,
    );
  };
}

module.exports = extendOCL;

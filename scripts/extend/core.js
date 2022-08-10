module.exports = function extendCore(exports) {
  'use strict';

  let ConformerGenerator = exports.ConformerGenerator;
  ConformerGenerator.prototype.molecules = function* molecules() {
    let nextConformer;
    while ((nextConformer = this.getNextConformerAsMolecule()) !== null) {
      yield nextConformer;
    }
  };

  let ForceFieldMMFF94 = exports.ForceFieldMMFF94;
  let defaultMinimiseOptions = {
    maxIts: 4000,
    gradTol: 1e-4,
    funcTol: 1e-6,
  };
  ForceFieldMMFF94.prototype.minimise = function minimise(options) {
    options = { ...defaultMinimiseOptions, ...options};
    return this._minimise(options.maxIts, options.gradTol, options.funcTol);
  };
};

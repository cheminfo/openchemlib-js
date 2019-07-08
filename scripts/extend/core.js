module.exports = function extendCore(exports) {
  'use strict';

  var ConformerGenerator = exports.ConformerGenerator;
  ConformerGenerator.prototype.molecules = function* molecules() {
    var nextConformer;
    while ((nextConformer = this.getNextConformerAsMolecule()) !== null) {
      yield nextConformer;
    }
  };

  var ForceFieldMMFF94 = exports.ForceFieldMMFF94;
  var defaultMinimiseOptions = {
    maxIts: 4000,
    gradTol: 1e-4,
    funcTol: 1e-6
  };
  ForceFieldMMFF94.prototype.minimise = function minimise(options) {
    options = Object.assign({}, defaultMinimiseOptions, options);
    return this._minimise(options.maxIts, options.gradTol, options.funcTol);
  };
};

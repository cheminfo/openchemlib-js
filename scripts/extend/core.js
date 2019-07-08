module.exports = function extendCore(exports) {
  'use strict';

  const ConformerGenerator = exports.ConformerGenerator;

  ConformerGenerator.prototype.molecules = function* molecules() {
    let nextConformer;
    while ((nextConformer = this.getNextConformerAsMolecule()) !== null) {
      yield nextConformer;
    }
  };
};

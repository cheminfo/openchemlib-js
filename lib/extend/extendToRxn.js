/**
 * Extends the instance methods toRxn and toRxnV3 to ...
 * @param {*} Reaction
 */
export function extendToRxn(Reaction) {
  const _toRxn = Reaction.prototype.toRxn;
  const _toRxnV3 = Reaction.prototype.toRxnV3;
  Reaction.prototype.toRxn = function toRxn(options = {}) {
    return internalToRxn(this, _toRxn, options);
  };
  Reaction.prototype.toRxnV3 = function toRxnV3(options = {}) {
    return internalToRxn(this, _toRxnV3, options);
  };
}

function internalToRxn(reaction, rxnFct, options) {
  const { programName = '', keepIdCode = false } = options;
  const rxn = rxnFct.call(reaction, programName);
  if (keepIdCode) {
    return rxn;
  } else {
    return rxn.replace(/^OCL_RXN_V1.0:.*$/m, '');
  }
}

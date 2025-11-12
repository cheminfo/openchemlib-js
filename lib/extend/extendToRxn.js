/**
 * Extends the instance method toMolfile to add the possibility to include A and V lines for custom atom labels
 * @param {*} Reaction
 */
export function extendToRxn(Reaction) {
  //console.log(Molecule.prototype);
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
  }
  const eol = rxn.includes('\r\n') ? '\r\n' : '\n';
  let lines = rxn.split(eol);
  lines[3] = '';

  return lines.join(eol);
}

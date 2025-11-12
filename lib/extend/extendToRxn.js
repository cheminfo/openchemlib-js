/**
 * Extends the instance method toMolfile to add the possibility to include A and V lines for custom atom labels
 * @param {*} Reaction
 */
export function extendToRxn(Reaction, Molecule) {
  //console.log(Molecule.prototype);
  console.log(Reaction.prototype);
  const _toRxn = Reaction.prototype.toRxn;
  Reaction.prototype.toRxn = function toRxn(options = {}) {
    const reaction = this;
    const rxn = _toRxn.call(reaction);

    const eol = rxn.includes('\r\n') ? '\r\n' : '\n';
    // need to add A or V lines just before M END
    let lines = molfile.split(eol);
    return lines.join(eol);
  };
}

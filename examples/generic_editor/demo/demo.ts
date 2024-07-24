import OCL from '../../../distesm/full.pretty';
import { getEditor, resetEditor } from './editor.ts';
import rxn from './seeds/rxn.ts';
import molfile from './seeds/molfile.ts';

const { Molecule, Reaction } = OCL;

resetEditor();

const resetButton = document.getElementById('resetButton') as HTMLButtonElement;
resetButton.onclick = () => {
  resetEditor();
};

const clearButton = document.getElementById('clearButton') as HTMLButtonElement;
clearButton.onclick = () => {
  getEditor().clearAll();
};

const loadMolecule = document.getElementById(
  'loadMolecule',
) as HTMLButtonElement;
loadMolecule.onclick = () => {
  const molecule = Molecule.fromMolfile(molfile);
  getEditor().setMolecule(molecule);
};

const loadFragment = document.getElementById(
  'loadFragment',
) as HTMLButtonElement;
loadFragment.onclick = () => {
  const molecule = Molecule.fromMolfile(molfile);
  molecule.setFragment(true);
  getEditor().setMolecule(molecule);
};

const loadReaction = document.getElementById(
  'loadReaction',
) as HTMLButtonElement;
loadReaction.onclick = () => {
  const reaction = Reaction.fromRxn(rxn);
  getEditor().setReaction(reaction);
};

const loadReactionFragment = document.getElementById(
  'loadReactionFragment',
) as HTMLButtonElement;
loadReactionFragment.onclick = () => {
  const reaction = Reaction.fromRxn(rxn);
  reaction.setFragment(true);
  getEditor().setReaction(reaction);
};

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
  // const molecule = Molecule.fromMolfile(molfile);
  const molecule = Molecule.fromSmiles('c1ccccc1CO');
  getEditor().setMolecule(molecule);
};

const loadFragment = document.getElementById(
  'loadFragment',
) as HTMLButtonElement;
loadFragment.onclick = () => {
  // const molecule = Molecule.fromMolfile(molfile);
  const molecule = Molecule.fromSmiles('CCC');
  molecule.setFragment(true);
  getEditor().setMolecule(molecule);
};

const loadReaction = document.getElementById(
  'loadReaction',
) as HTMLButtonElement;
loadReaction.onclick = () => {
  const reaction = Reaction.fromSmiles('c1ccccc1..CC>CO>c1ccccc1..CCC..CC');
  // const reaction = Reaction.fromRxn(rxn);
  // reaction.addCatalyst(Molecule.fromSmiles('CO'));
  console.log(reaction.toSmiles());
  getEditor().setReaction(reaction);
};

import { Molecule, Reaction, ReactionEncoder } from '../../../lib/index.js';
import { getEditor, resetEditor } from './editor.ts';
import rxn from './seeds/rxn.ts';
import molfile from './seeds/molfile.ts';

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

const idcodeForm = document.getElementById('idcodeForm') as HTMLFormElement;
idcodeForm.onsubmit = (event) => {
  event.preventDefault();
  event.stopPropagation();

  const formData = new FormData(idcodeForm);
  const idcode = formData.get('idcode') as string;
  const type = formData.get('type') as string;
  const ensureCoordinates = formData.has('ensureCoordinates');

  if (type === 'molecule') {
    const molecule = Molecule.fromIDCode(idcode, ensureCoordinates);
    getEditor().setMolecule(molecule);
    return;
  }

  if (type === 'reaction') {
    const useMode = formData.has('options-type');

    function getMode() {
      const mapping = formData.has('INCLUDE_MAPPING')
        ? ReactionEncoder.INCLUDE_MAPPING
        : 0;
      const coords = formData.has('INCLUDE_COORDS')
        ? ReactionEncoder.INCLUDE_COORDS
        : 0;
      const draw = formData.has('INCLUDE_DRAWING_OBJECTS')
        ? ReactionEncoder.INCLUDE_DRAWING_OBJECTS
        : 0;
      const catalysts = formData.has('INCLUDE_CATALYSTS')
        ? ReactionEncoder.INCLUDE_CATALYSTS
        : 0;

      return mapping | coords | draw | catalysts;
    }

    const reaction = useMode
      ? ReactionEncoder.decode(idcode, {
          mode: getMode(),
        })
      : ReactionEncoder.decode(idcode, {
          ensureCoordinates: ensureCoordinates,
        });

    if (!reaction) {
      window.alert('Invalid reaction');
      return;
    }

    getEditor().setReaction(reaction);
  }
};

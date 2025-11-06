import {
  CanvasEditor,
  CanvasEditorMode,
  Molecule,
  ReactionEncoder,
} from '../../ocl.ts';
import {
  incrementChangeCount,
  resetChangeCount,
  updateIDCode,
  updateMolfileOrRxn,
  updateMolfileOrRxnV3,
  updateSmiles,
} from './result.ts';

let editor: CanvasEditor | undefined;

export function resetEditor() {
  if (editor) {
    editor.destroy();
  }
  const modeSelect = document.getElementById('modeSelect') as HTMLSelectElement;
  const readOnlyCheckbox = document.getElementById(
    'readOnlyCheckbox',
  ) as HTMLInputElement;
  const fragmentCheckbox = document.getElementById(
    'fragmentCheckbox',
  ) as HTMLInputElement;
  const newEditor = new CanvasEditor(
    document.getElementById('editor') as HTMLElement,
    {
      readOnly: readOnlyCheckbox.checked,
      initialMode: modeSelect.value as CanvasEditorMode,
      initialFragment: fragmentCheckbox.checked,
    },
  );

  editor = newEditor;

  resetChangeCount();
  updateIDCode('');
  updateSmiles('');
  updateMolfileOrRxn('');
  updateMolfileOrRxnV3('');

  editor.setOnChangeListener(({ type, isUserEvent }) => {
    if (type === 'molecule') {
      if (isUserEvent) {
        incrementChangeCount();
      }
      const mode = newEditor.getMode();
      if (mode === 'molecule') {
        const molecule = newEditor.getMolecule();
        const idCode = molecule.getCanonizedIDCode(
          Molecule.CANONIZER_ENCODE_ATOM_CUSTOM_LABELS,
        );
        const coordinates = molecule.getIDCoordinates();
        updateIDCode(`${idCode} ${coordinates}`);
        updateSmiles(molecule.toIsomericSmiles());
        updateMolfileOrRxn(molecule.toMolfile());
        updateMolfileOrRxnV3(molecule.toMolfileV3());
      } else {
        const reaction = newEditor.getReaction();
        const idCode = ReactionEncoder.encode(reaction, {
          keepAbsoluteCoordinates: true,
          mode:
            ReactionEncoder.INCLUDE_MAPPING |
            ReactionEncoder.INCLUDE_COORDS |
            ReactionEncoder.INCLUDE_CATALYSTS |
            ReactionEncoder.RETAIN_REACTANT_AND_PRODUCT_ORDER,
        });
        updateIDCode(idCode ?? '');
        updateSmiles(reaction.toSmiles());
        updateMolfileOrRxn(reaction.toRxn());
        updateMolfileOrRxnV3(reaction.toRxnV3());
      }
    }
  });
}

export function getEditor(): CanvasEditor {
  if (!editor) {
    throw new Error('Editor not initialized');
  }
  return editor;
}

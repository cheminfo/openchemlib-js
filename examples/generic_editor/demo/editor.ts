import OCL from '../../../distesm/full.pretty';
import {
  incrementChangeCount,
  resetChangeCount,
  updateIDCode,
  updateMolfileOrRxn,
  updateMolfileOrRxnV3,
} from './result.ts';

let editor: OCL.CanvasEditor | undefined;

export function resetEditor() {
  if (editor) {
    editor.destroy();
  }
  const modeSelect = document.getElementById('modeSelect') as HTMLSelectElement;
  const readOnlyCheckbox = document.getElementById(
    'readOnlyCheckbox',
  ) as HTMLInputElement;
  const newEditor = new OCL.CanvasEditor(
    document.getElementById('editor') as HTMLElement,
    {
      readOnly: readOnlyCheckbox.checked,
      initialMode: modeSelect.value as OCL.CanvasEditorMode,
    },
  );

  editor = newEditor;

  resetChangeCount();
  updateIDCode('');
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
        updateIDCode(molecule.getIDCode());
        updateMolfileOrRxn(molecule.toMolfile());
        updateMolfileOrRxnV3(molecule.toMolfileV3());
      } else {
        const reaction = newEditor.getReaction();
        updateIDCode(OCL.ReactionEncoder.encode(reaction));
        updateMolfileOrRxn(reaction.toRxn());
        updateMolfileOrRxnV3(reaction.toRxnV3());
      }
    }
  });
}

export function getEditor(): OCL.CanvasEditor {
  if (!editor) {
    throw new Error('Editor not initialized');
  }
  return editor;
}

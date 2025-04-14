import {
  CanvasEditor,
  CanvasEditorMode,
  ReactionEncoder,
} from '../../../lib/index.js';
import {
  incrementChangeCount,
  resetChangeCount,
  updateIDCode,
  updateMolfileOrRxn,
  updateMolfileOrRxnV3,
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
        const idCode = ReactionEncoder.encode(reaction);
        updateIDCode(idCode ?? '');
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

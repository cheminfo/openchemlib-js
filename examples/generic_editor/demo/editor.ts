import OCL from '../../../distesm/full.pretty';
import {
  incrementChangeCount,
  resetChangeCount,
  updateIDCode,
  updateMolfile,
} from './result.ts';

let editor: OCL.CanvasEditor | undefined;

export function resetEditor() {
  if (editor) {
    editor.destroy();
  }
  const modeSelect = document.getElementById('modeSelect') as HTMLSelectElement;
  const newEditor = new OCL.CanvasEditor(
    document.getElementById('editor') as HTMLElement,
    {
      initialMode: modeSelect.value as OCL.CanvasEditorMode,
    },
  );

  editor = newEditor;

  resetChangeCount();

  editor.setOnChangeListener(({ type, isUserEvent }) => {
    if (type === 'molecule') {
      if (isUserEvent) {
        incrementChangeCount();
      }
      updateIDCode(newEditor.getMolecule());
      updateMolfile(newEditor.getMolecule());
    }
  });
}

export function getEditor(): OCL.CanvasEditor {
  if (!editor) {
    throw new Error('Editor not initialized');
  }
  return editor;
}

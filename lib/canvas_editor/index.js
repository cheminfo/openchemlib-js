'use strict';

function createCanvasEditor(EditorArea, EditorToolbar) {
  return class CanvasEditor {
    setMolecule(molecule) {
      console.log('WITH WATCH');
      this.molecule = molecule;
    }
  };
}

module.exports = createCanvasEditor;

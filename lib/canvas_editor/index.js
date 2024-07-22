'use strict';

const createEditor = require('./create_editor');

function createCanvasEditor(JavaEditorArea, JavaEditorToolbar) {
  return class CanvasEditor {
    #editorArea;
    // Can be useful for debugging.
    // eslint-disable-next-line no-unused-private-class-members
    #toolbar;
    #onChange;
    #changeEventMap;

    constructor(rootElement) {
      const { editorArea, toolbar } = createEditor(
        rootElement,
        (event) => this.#handleChange(event),
        JavaEditorArea,
        JavaEditorToolbar,
      );
      this.#editorArea = editorArea;
      this.#toolbar = toolbar;
      this.#onChange = null;
      this.#changeEventMap = {
        [JavaEditorArea.EDITOR_EVENT_MOLECULE_CHANGED]: 'molecule',
        [JavaEditorArea.EDITOR_EVENT_SELECTION_CHANGED]: 'selection',
        [JavaEditorArea.EDITOR_EVENT_HIGHLIGHT_ATOM_CHANGED]: 'highlight-atom',
        [JavaEditorArea.EDITOR_EVENT_HIGHLIGHT_BOND_CHANGED]: 'highlight-bond',
      };
    }

    setMolecule(molecule) {
      this.#editorArea.setMolecule(molecule);
    }

    getMolecule() {
      return this.#editorArea.getMolecule();
    }

    setReaction(reaction) {
      this.#editorArea.setReaction(reaction);
    }

    getReaction() {
      this.#editorArea.getReaction();
    }

    setOnChangeListener(onChange) {
      this.#onChange = onChange;
    }

    removeOnChangeListner() {
      this.#onChange = null;
    }

    /**
     * @param {{ what: number; isUserEvent: boolean; }} event
     */
    #handleChange(event) {
      if (!this.#onChange) {
        return;
      }
      const { what, isUserEvent } = event;
      this.#onChange({
        type: this.#changeEventMap[what],
        isUserEvent,
      });
    }
  };
}

module.exports = createCanvasEditor;

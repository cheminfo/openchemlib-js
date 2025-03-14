'use strict';

const createEditor = require('../create_editor');

function initCanvasEditor(
  JavaEditorArea,
  JavaEditorToolbar,
  JavaUIHelper,
  Molecule,
  Reaction,
) {
  class CanvasEditor {
    #editorArea;
    // Can be useful for debugging.
    /* eslint-disable no-unused-private-class-members */
    #toolbar;
    #uiHelper;
    /* eslint-enable no-unused-private-class-members */
    #onChange;
    #changeEventMap;
    #destroy;

    constructor(parentElement, options = {}) {
      const { editorArea, toolbar, uiHelper, destroy } = createEditor(
        parentElement,
        options,
        (event) => this.#handleChange(event),
        JavaEditorArea,
        JavaEditorToolbar,
        JavaUIHelper,
        Molecule,
        Reaction,
      );
      this.#editorArea = editorArea;
      this.#toolbar = toolbar;
      this.#uiHelper = uiHelper;
      this.#onChange = null;
      this.#changeEventMap = {
        [JavaEditorArea.EDITOR_EVENT_MOLECULE_CHANGED]: 'molecule',
        [JavaEditorArea.EDITOR_EVENT_SELECTION_CHANGED]: 'selection',
        [JavaEditorArea.EDITOR_EVENT_HIGHLIGHT_ATOM_CHANGED]: 'highlight-atom',
        [JavaEditorArea.EDITOR_EVENT_HIGHLIGHT_BOND_CHANGED]: 'highlight-bond',
      };
      this.#destroy = destroy;
    }

    getMode() {
      this.#checkNotDestroyed();

      const mode = this.#editorArea.getMode();
      const isReaction = mode & (JavaEditorArea.MODE_REACTION !== 0);
      return isReaction ? 'reaction' : 'molecule';
    }

    setMolecule(molecule) {
      this.#checkNotDestroyed();

      this.#editorArea.setMolecule(molecule);
    }

    getMolecule() {
      this.#checkNotDestroyed();

      return this.#editorArea.getMolecule();
    }

    setReaction(reaction) {
      this.#checkNotDestroyed();

      this.#editorArea.setReaction(reaction);
    }

    getReaction() {
      this.#checkNotDestroyed();

      return this.#editorArea.getReaction();
    }

    setOnChangeListener(onChange) {
      this.#checkNotDestroyed();

      this.#onChange = onChange;
    }

    removeOnChangeListner() {
      this.#checkNotDestroyed();

      this.#onChange = null;
    }

    clearAll() {
      this.#checkNotDestroyed();

      this.#editorArea.clearAll();
    }

    destroy() {
      this.#checkNotDestroyed();

      this.#destroy();
      this.#editorArea = null;
      this.#toolbar = null;
      this.#uiHelper = null;
      this.#onChange = null;
      this.#destroy = null;
    }

    moleculeChanged() {
      this.#checkNotDestroyed();

      this.#editorArea.getGenericEditorArea().moleculeChanged();
    }

    #checkNotDestroyed() {
      if (!this.#editorArea) {
        throw new Error('CanvasEditor has been destroyed');
      }
    }

    /**
     * @param {{ what: number; isUserEvent: boolean; }} event
     */
    #handleChange(event) {
      if (!this.#onChange) return;

      const { what, isUserEvent } = event;
      this.#onChange({
        type: this.#changeEventMap[what],
        isUserEvent,
      });
    }
  }

  return CanvasEditor;
}

module.exports = initCanvasEditor;

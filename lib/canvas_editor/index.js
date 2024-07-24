'use strict';

const createEditor = require('./create_editor');

function createCanvasEditor(JavaEditorArea, JavaEditorToolbar, JavaUIHelper) {
  class CanvasEditor {
    #isReadOnly;
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
      const { readOnly = false } = options;
      const { editorArea, toolbar, uiHelper, destroy } = createEditor(
        parentElement,
        options,
        (event) => this.#handleChange(event),
        JavaEditorArea,
        JavaEditorToolbar,
        JavaUIHelper,
      );
      this.#isReadOnly = readOnly;
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

      this.#editorArea.getReaction();
    }

    setOnChangeListener(onChange) {
      this.#checkNotDestroyed();

      if (this.#isReadOnly) return;

      this.#onChange = onChange;
    }

    removeOnChangeListner() {
      this.#checkNotDestroyed();

      this.#onChange = null;
    }

    clearAll() {
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

    #checkNotDestroyed() {
      if (!this.#editorArea) {
        throw new Error('CanvasEditor has been destroyed');
      }
    }

    /**
     * @param {{ what: number; isUserEvent: boolean; }} event
     */
    #handleChange(event) {
      if (this.#isReadOnly) return;
      if (!this.#onChange) return;

      const { what, isUserEvent } = event;
      this.#onChange({
        type: this.#changeEventMap[what],
        isUserEvent,
      });
    }
  }

  class CanvasEditorElement extends HTMLElement {
    /** @type {{MOLECULE: 'molecule', REACTION: 'reaction'}} */
    static MODE = Object.freeze(
      Object.create({
        MOLECULE: 'molecule',
        REACTION: 'reaction',
      }),
    );
    static observedAttributes = Object.freeze([
      'idcode',
      'fragment',
      'mode',
      'readonly',
    ]);

    /* --- custom element attributes --- */
    /** @type {string} */ idcode = '';
    /** @type {boolean} */ fragment = false;
    /** @type {'molecule' | 'reaction'} */ mode =
      CanvasEditorElement.MODE.MOLECULE;
    /** @type {boolean} */ readonly = false;

    /** @type {CanvasEditor} */ #editor;

    /**
     * Custom element added to page.
     */
    connectedCallback() {
      const root = this.attachShadow({ mode: 'open' });
      root.adoptedStyleSheets = [new CSSStyleSheet()];

      this.#editor = new CanvasEditor(root, {
        readOnly: this.readonly,
        initialMode: this.mode,
      });

      switch (this.mode) {
        case this.constructor.MODE.MOLECULE: {
          const molecule = OCL.Molecule.fromIDCode(this.idcode || '');
          molecule.setFragment(this.fragment);

          return this.#editor.setMolecule(molecule);
        }
        case this.constructor.MODE.REACTION: {
          throw new Error(
            'Unsupported because `Reaction.fromIDCode` do not exists',
          );
        }
        default:
          throw new Error(`Mode ${this.mode} is not supported`);
      }
    }

    /**
     * Custom element removed from page.
     */
    // eslint-disable-next-line no-empty-function
    disconnectedCallback() {}

    /**
     * Custom element moved to new page.
     */
    // eslint-disable-next-line no-empty-function
    adoptedCallback() {}

    /**
     * Attribute ${name} has changed from ${oldValue} to ${newValue}
     */
    // eslint-disable-next-line no-unused-vars,no-empty-function
    attributeChangedCallback(name, oldValue, newValue) {}
  }

  customElements.define('openchemlib-editor', CanvasEditorElement);

  return CanvasEditor;
}

module.exports = createCanvasEditor;

'use strict';

function initCanvasEditorElement(CanvasEditor, Molecule, ReactionEncoder) {
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

    /* --- custom element api --- */
    /**
     * @param {Molecule} molecule
     */
    setMolecule(molecule) {
      this.fragment = molecule.isFragment();
      this.idcode = molecule.getIDCode();

      this.#editor.setMolecule(molecule);
    }

    /**
     * @return {Molecule}
     */
    getMolecule() {
      return this.#editor.getMolecule();
    }

    /**
     * @param {Reaction} reaction
     */
    setReaction(reaction) {
      this.fragment = reaction.isFragment();
      this.idcode = ReactionEncoder.encode(reaction);

      this.#editor.setReaction(reaction);
    }

    /**
     * @return {Reaction}
     */
    getReaction() {
      return this.#editor.getReaction();
    }

    clearAll() {
      this.#editor.clearAll();
      this.idcode = '';
    }

    /* --- internals --- */
    /** @type {CanvasEditor} */ #editor;

    #initEditor() {
      if (this.#editor) return;

      this.#editor = new CanvasEditor(this, {
        readOnly: this.readonly,
        initialMode: this.mode,
      });
      this.#editor.setOnChangeListener(this.#handleChange);

      requestIdleCallback(() => this.#initIdCode());
    }

    #initIdCode() {
      switch (this.mode) {
        case this.constructor.MODE.MOLECULE: {
          return this.#initMolecule();
        }
        case this.constructor.MODE.REACTION: {
          return this.#initReaction();
        }
        default:
          throw new Error(`Mode ${this.mode} is not supported`);
      }
    }

    #initMolecule() {
      const molecule = Molecule.fromIDCode(this.idcode);
      molecule.setFragment(this.fragment);

      this.#editor.setMolecule(molecule);
    }

    #initReaction() {
      const reaction = ReactionEncoder.decode(this.idcode);
      reaction.setFragment(this.fragment);

      this.#editor.setReaction(reaction);
    }

    #handleChange = (editorEventOnChange) => {
      const domEvent = new CustomEvent('change', {
        detail: editorEventOnChange,
        composed: true,
      });
      this.dispatchEvent(domEvent);
    };

    #destroyEditor() {
      if (!this.#editor) return;

      this.#editor.destroy();
      this.#editor = undefined;
    }

    #resetEditor() {
      this.#destroyEditor();
      this.#initEditor();
    }

    /* --- lifecycle hooks --- */
    /**
     * Custom element added to page.
     */
    connectedCallback() {
      this.#initEditor();
    }

    /**
     * Custom element removed from page.
     */
    disconnectedCallback() {
      this.#destroyEditor();
    }

    /**
     * Custom element moved to new page.
     */
    // eslint-disable-next-line no-empty-function
    adoptedCallback() {}

    /**
     * Attribute ${name} has changed from ${oldValue} to ${newValue}
     */
    attributeChangedCallback(name, oldValue, newValue) {
      const mutatorHandler = (() => {
        switch (name) {
          case 'idcode': {
            this.idcode = String(newValue);
            return () => this.#initIdCode();
          }
          case 'fragment': {
            this.fragment = newValue !== null;
            return () => this.#initIdCode();
          }
          case 'mode': {
            this.mode = String(newValue);
            return () => this.#resetEditor();
          }
          case 'readonly': {
            this.readonly = newValue !== null;
            return () => this.#resetEditor();
          }
          default:
            throw new Error('unsupported attribute change');
        }
      })();

      mutatorHandler();
    }
  }

  return CanvasEditorElement;
}

module.exports = initCanvasEditorElement;

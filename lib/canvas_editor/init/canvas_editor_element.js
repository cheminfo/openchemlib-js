'use strict';

function initCanvasEditorElement(CanvasEditor, Molecule) {
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
          const molecule = Molecule.fromIDCode(this.idcode || '');
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

  return CanvasEditorElement;
}

module.exports = initCanvasEditorElement;

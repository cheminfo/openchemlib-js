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

    /**
     * @type {{mode: 'molecule' | 'reaction', fragment: boolean, idcode: string, readonly: boolean}}
     */
    #state;

    get idcode() {
      return this.#state.idcode;
    }

    set idcode(value) {
      this.#state.idcode = String(value);
      this.setAttribute('idcode', this.#state.idcode);
    }

    get fragment() {
      return this.#state.fragment;
    }

    set fragment(value) {
      this.#state.fragment = Boolean(value);
      if (this.#state.fragment) {
        this.setAttribute('fragment', '');
      } else {
        this.removeAttribute('fragment');
      }
    }

    get mode() {
      return this.#state.mode;
    }

    set mode(value) {
      this.#state.mode = String(value);
      this.setAttribute('mode', this.#state.mode);
    }

    get readonly() {
      return this.#state.readonly;
    }

    set readonly(value) {
      this.#state.readonly = Boolean(value);
      if (this.#state.readonly) {
        this.setAttribute('readonly', '');
      } else {
        this.removeAttribute('readonly');
      }
    }

    /* --- custom element api --- */
    /**
     * @param {Molecule} molecule
     * @this {CanvasEditorElement}
     */
    setMolecule(molecule) {
      this.fragment = molecule.isFragment();
      this.idcode = `${molecule.getIDCode()} ${molecule.getIDCoordinates()}`;

      this.#editor.setMolecule(molecule);
    }

    /**
     * @return {Molecule}
     * @this {CanvasEditorElement}
     */
    getMolecule() {
      return this.#editor.getMolecule();
    }

    /**
     * @param {Reaction} reaction
     * @this {CanvasEditorElement}
     */
    setReaction(reaction) {
      this.fragment = reaction.isFragment();
      this.idcode = ReactionEncoder.encode(reaction, {
        keepAbsoluteCoordinates: true,
        mode:
          ReactionEncoder.INCLUDE_MAPPING |
          ReactionEncoder.INCLUDE_COORDS |
          ReactionEncoder.RETAIN_REACTANT_AND_PRODUCT_ORDER,
      });

      this.#editor.setReaction(reaction);
    }

    /**
     * @return {Reaction}
     * @this {CanvasEditorElement}
     */
    getReaction() {
      return this.#editor.getReaction();
    }

    /**
     * @this {CanvasEditorElement}
     */
    clearAll() {
      this.#editor.clearAll();
      this.idcode = '';
    }

    /**
     * @this {CanvasEditorElement}
     */
    moleculeChanged() {
      this.#editor.moleculeChanged();
    }

    /* --- internals --- */
    /** @type {CanvasEditor} */ #editor;

    /**
     * @this {CanvasEditorElement}
     */
    #initEditor() {
      if (this.#editor) return;

      this.#editor = new CanvasEditor(this, {
        readOnly: this.readonly,
        initialMode: this.mode,
      });
      this.#editor.setOnChangeListener(this.#handleChange);

      requestIdleCallback(() => this.#initIdCode());
    }

    /**
     * @this {CanvasEditorElement}
     */
    #initIdCode() {
      switch (this.mode) {
        case CanvasEditorElement.MODE.MOLECULE: {
          return this.#initMolecule();
        }
        case CanvasEditorElement.MODE.REACTION: {
          return this.#initReaction();
        }
        default:
          throw new Error(`Mode ${this.mode} is not supported`);
      }
    }

    /**
     * @param {string} idcodeAndCoordinates
     */
    #moleculeFromIdCode(idcodeAndCoordinates) {
      const index = idcodeAndCoordinates.indexOf(' ');

      if (index === -1) {
        return Molecule.fromIDCode(idcodeAndCoordinates);
      }

      const idcode = idcodeAndCoordinates.slice(0, index);
      const coordinates = idcodeAndCoordinates.slice(index + 1);

      return Molecule.fromIDCode(idcode, coordinates);
    }

    /**
     * @this {CanvasEditorElement}
     */
    #initMolecule() {
      const molecule = this.#moleculeFromIdCode(this.idcode);
      molecule.setFragment(this.fragment);

      this.#editor.setMolecule(molecule);
    }

    /**
     * @this {CanvasEditorElement}
     */
    #initReaction() {
      const reaction = ReactionEncoder.decode(this.idcode, {
        ensureCoordinates: true,
      });
      reaction.setFragment(this.fragment);

      this.#editor.setReaction(reaction);
    }

    #ignoreAttributeChange = false;
    /**
     * @param {() => void} fn
     * @this {CanvasEditorElement}
     */
    #wrapIgnoreAttributeChange(fn) {
      this.#ignoreAttributeChange = true;

      try {
        fn();
      } finally {
        this.#ignoreAttributeChange = false;
      }
    }

    /**
     * @param {{
     *  type: 'molecule' | 'selection' | 'highlight-atom' | 'highlight-bond';
     *  isUserEvent: boolean;
     * }} editorEventOnChange
     */
    #handleChange = (editorEventOnChange) => {
      const idcode = this.idcode;
      const fragment = this.fragment;

      // update internal state from editor change
      this.#wrapIgnoreAttributeChange(() => {
        if (editorEventOnChange.type !== 'molecule') return;

        switch (this.mode) {
          case CanvasEditorElement.MODE.MOLECULE: {
            const molecule = this.getMolecule();
            this.idcode = `${molecule.getIDCode()} ${molecule.getIDCoordinates()}`;
            this.fragment = molecule.isFragment();
            break;
          }
          case CanvasEditorElement.MODE.REACTION: {
            const reaction = this.getReaction();
            this.idcode = ReactionEncoder.encode(reaction, {
              keepAbsoluteCoordinates: true,
              mode:
                ReactionEncoder.INCLUDE_MAPPING |
                ReactionEncoder.INCLUDE_COORDS |
                ReactionEncoder.RETAIN_REACTANT_AND_PRODUCT_ORDER,
            });
            this.fragment = reaction.isFragment();
            break;
          }
          default:
            throw new Error(`Unsupported mode ${this.mode}`);
        }
      });

      // propagate editor changes to parent
      const domEvent = new CustomEvent('change', {
        detail: editorEventOnChange,
        bubbles: true,
      });
      this.dispatchEvent(domEvent);

      if (editorEventOnChange.mode !== 'molecule') return;

      // propagate vaadin events
      if (this.idcode !== idcode) {
        const idcodeChangeEvent = new CustomEvent('idcode-changed', {
          detail: this.idcode,
          bubbles: true,
        });
        this.dispatchEvent(idcodeChangeEvent);
      }
      if (this.fragment !== fragment) {
        const fragmentChangeEvent = new CustomEvent('fragment-changed', {
          detail: this.fragment,
          bubbles: true,
        });
        this.dispatchEvent(fragmentChangeEvent);
      }
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
      this.#state = {
        idcode: this.getAttribute('idcode') || '',
        fragment: this.hasAttribute('fragment'),
        mode: this.getAttribute('mode') || CanvasEditorElement.MODE.MOLECULE,
        readonly: this.hasAttribute('readonly'),
      };

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
    adoptedCallback() {
      this.connectedCallback();
    }

    /**
     * Attribute ${name} has changed from ${oldValue} to ${newValue}
     *
     * Sync attribute changes to internal state.
     * propagate changes to editor.
     */
    attributeChangedCallback(name, oldValue, newValue) {
      if (!this.#editor) return;
      if (this.#ignoreAttributeChange) return;

      const mutatorHandler = (() => {
        switch (name) {
          case 'idcode': {
            this.#state.idcode = String(newValue);
            return () => this.#initIdCode();
          }
          case 'fragment': {
            this.#state.fragment = newValue !== null;
            return () => this.#initIdCode();
          }
          case 'mode': {
            this.#state.mode = String(newValue);
            return () => this.#resetEditor();
          }
          case 'readonly': {
            this.#state.readonly = newValue !== null;
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

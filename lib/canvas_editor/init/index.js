'use strict';

const initCanvasEditor = require('./canvas_editor');
const initCanvasEditorElement = require('./canvas_editor_element');

function init(OCL) {
  const {
    GenericEditorArea: JavaEditorArea,
    GenericEditorToolbar: JavaEditorToolbar,
    GenericUIHelper: JavaUIHelper,
    Molecule,
    Reaction,
    ReactionEncoder,
  } = OCL;

  const CanvasEditor = initCanvasEditor(
    JavaEditorArea,
    JavaEditorToolbar,
    JavaUIHelper,
    Molecule,
    Reaction,
  );

  function registerCustomElement() {
    const CanvasEditorElement = initCanvasEditorElement(
      CanvasEditor,
      Molecule,
      ReactionEncoder,
    );

    const constructor = customElements.get('openchemlib-editor');
    if (constructor) return constructor;

    customElements.define('openchemlib-editor', CanvasEditorElement);

    const css = new CSSStyleSheet();
    css.replaceSync(`
    openchemlib-editor:defined {
      display: block;
      height: 400px;
    }
    `);
    document.adoptedStyleSheets.push(css);

    return CanvasEditorElement;
  }

  OCL.CanvasEditor = CanvasEditor;
  OCL.registerCustomElement = registerCustomElement;

  // Do not expose internal classes to end users.
  delete OCL.GenericEditorArea;
  delete OCL.GenericEditorToolbar;
  delete OCL.GenericUIHelper;
}

module.exports = init;

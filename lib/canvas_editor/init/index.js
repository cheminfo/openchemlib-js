'use strict';

const initCanvasEditor = require('./canvas_editor');
const initCanvasEditorElement = require('./canvas_editor_element');

function init(OCL) {
  const {
    GenericEditorArea: JavaEditorArea,
    GenericEditorToolbar: JavaEditorToolbar,
    GenericUIHelper: JavaUIHelper,
    Molecule,
  } = OCL;

  const CanvasEditor = initCanvasEditor(
    JavaEditorArea,
    JavaEditorToolbar,
    JavaUIHelper,
  );

  function registerCustomElement() {
    const CanvasEditorElement = initCanvasEditorElement(CanvasEditor, Molecule);
    customElements.define('openchemlib-editor', CanvasEditorElement);
  }

  OCL.CanvasEditor = CanvasEditor;
  OCL.registerCustomElement = registerCustomElement;

  // Do not expose internal classes to end users.
  delete OCL.GenericEditorArea;
  delete OCL.GenericEditorToolbar;
  delete OCL.GenericUIHelper;
}

module.exports = init;

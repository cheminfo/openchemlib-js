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
    const constructor = customElements.get('openchemlib-editor');
    if (constructor) return constructor;

    const CanvasEditorElement = initCanvasEditorElement(
      CanvasEditor,
      Molecule,
      ReactionEncoder,
    );

    customElements.define('openchemlib-editor', CanvasEditorElement);

    // mutate the first stylesheet available or construct a new one, added to adoptedStyleSheets
    // It's default styling for openchemlib-editor element,
    // should be considered as a user-agent stylesheet (low-priority)
    const css =
      document.styleSheets[0] ??
      (() => {
        const css = new CSSStyleSheet();
        document.adoptedStyleSheets.unshift(css);
        return css;
      })();
    css.insertRule(
      `
      /* dynamicaly added from openchemlib registerCustomElement with low priority */
      openchemlib-editor:defined {
        display: block;
        height: 400px;
        width: 600px;
      }
      `,
      0,
    );

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

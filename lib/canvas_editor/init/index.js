import { initCanvasEditor } from './canvas_editor.js';
import { initCanvasEditorElement } from './canvas_editor_element.js';

export default function init(OCL) {
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

    // Add new stylesheet (prepend to the head)
    // It's the default styling for openchemlib-editor element,
    // should be considered as a user-agent stylesheet (low-priority)
    const style = document.createElement('style');
    style.id = 'openchemlib-editor-default-style';
    style.innerHTML = `
    /* dynamicaly added from openchemlib registerCustomElement with low priority */
    openchemlib-editor:defined {
      display: block;
      height: 400px;
      width: 600px;
    }
    `;
    document.head.prepend(style);

    return CanvasEditorElement;
  }

  OCL.CanvasEditor = CanvasEditor;
  OCL.registerCustomElement = registerCustomElement;

  // Do not expose internal classes to end users.
  delete OCL.GenericEditorArea;
  delete OCL.GenericEditorToolbar;
  delete OCL.GenericUIHelper;
}

'use strict';

const EditorArea = require('./editor_area');
const { addMouseListeners, addKeyboardListeners } = require('./events');
const Toolbar = require('./toolbar');
const UIHelper = require('./ui_helper');

function createEditor(
  rootElement,
  options,
  onChange,
  JavaEditorArea,
  JavaEditorToolbar,
  JavaUIHelper,
) {
  const { readOnly = false } = options;
  const childElement = document.createElement('div');
  childElement.setAttribute(
    'style',
    'width: 100%; height: 100%; display: flex; flex-direction: row; align-items: start; background-color: white;',
  );
  rootElement.append(childElement);

  const toolbarCanvas = document.createElement('canvas');
  childElement.append(toolbarCanvas);

  const editorContainer = document.createElement('div');
  editorContainer.setAttribute('style', 'width: 100%; height: 100%;');
  childElement.append(editorContainer);

  const editorCanvas = document.createElement('canvas');
  editorCanvas.tabIndex = '0';
  editorCanvas.style = 'outline: none';
  editorContainer.append(editorCanvas);

  const containerSize = editorContainer.getBoundingClientRect();
  editorCanvas.width = containerSize.width;
  editorCanvas.height = containerSize.height;

  const uiHelper = new JavaUIHelper(new UIHelper(editorCanvas, JavaEditorArea));

  const editorArea = new JavaEditorArea(
    new EditorArea(editorCanvas, onChange),
    uiHelper,
  );

  uiHelper.setEditorArea(editorArea);

  editorArea.draw();

  const toolbar = readOnly
    ? null
    : new Toolbar(toolbarCanvas, editorArea, JavaEditorArea, JavaEditorToolbar);

  const resizeObserver = new ResizeObserver(([entry]) => {
    editorCanvas.width = entry.contentRect.width;
    editorCanvas.height = entry.contentRect.height;
    editorArea.repaint();
  });
  resizeObserver.observe(editorContainer);

  let removeMouseListeners = null;
  let removeKeyboardListeners = null;

  if (readOnly) {
    toolbarCanvas.remove();
  } else {
    removeMouseListeners = addMouseListeners(
      editorCanvas,
      editorArea,
      JavaEditorArea,
    );
    removeKeyboardListeners = addKeyboardListeners(
      editorCanvas,
      editorArea,
      JavaEditorArea,
    );
  }

  function destroy() {
    childElement.remove();
    resizeObserver.disconnect();
    removeMouseListeners?.();
    removeKeyboardListeners?.();
  }

  return { editorArea, toolbar, uiHelper, destroy };
}

module.exports = createEditor;

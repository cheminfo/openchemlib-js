'use strict';

const EditorArea = require('./editor_area');
const { addMouseListeners, addKeyboardListeners } = require('./events');
const Toolbar = require('./toolbar');

function createEditor(
  rootElement,
  onChange,
  JavaEditorArea,
  JavaEditorToolbar,
) {
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

  const editorArea = new JavaEditorArea(
    new EditorArea(editorCanvas, onChange, JavaEditorArea),
  );

  editorArea.draw();

  const toolbar = new Toolbar(
    toolbarCanvas,
    editorArea,
    JavaEditorArea,
    JavaEditorToolbar,
  );

  const resizeObserver = new ResizeObserver(([entry]) => {
    editorCanvas.width = entry.contentRect.width;
    editorCanvas.height = entry.contentRect.height;
    editorArea.repaint();
  });
  resizeObserver.observe(editorContainer);

  addMouseListeners(editorCanvas, editorArea, JavaEditorArea);
  addKeyboardListeners(editorCanvas, editorArea, JavaEditorArea);

  return { editorArea, toolbar };
}

module.exports = createEditor;
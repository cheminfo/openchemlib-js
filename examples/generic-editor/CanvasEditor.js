import { CanvasEditorArea } from './CanvasEditorArea.js';
import { CanvasToolbar } from './CanvasToolbar.js';
import { addMouseListeners, addKeyboardListeners } from './events.js';

const { EditorArea } = OCL;

export class CanvasEditor {
  /**
   *
   * @param {HTMLElement} rootElement
   */
  constructor(rootElement, options = {}) {
    const { onChange } = options;

    const childElement = document.createElement('div');
    childElement.setAttribute(
      'style',
      'width: 100%; height: 100%; display: flex; flex-direction: row; align-items: start; background-color: white;',
    );
    rootElement.appendChild(childElement);

    const toolbarCanvas = document.createElement('canvas');
    childElement.appendChild(toolbarCanvas);

    const editorContainer = document.createElement('div');
    editorContainer.setAttribute('style', 'width: 100%; height: 100%;');
    childElement.appendChild(editorContainer);

    const editorCanvas = document.createElement('canvas');
    editorCanvas.tabIndex = '0';
    editorCanvas.style = 'outline: none';
    editorContainer.appendChild(editorCanvas);

    const containerSize = editorContainer.getBoundingClientRect();
    editorCanvas.width = containerSize.width;
    editorCanvas.height = containerSize.height;

    const editorArea = new EditorArea(
      new CanvasEditorArea(editorCanvas, onChange),
    );
    this.editorArea = editorArea;

    editorArea.draw();

    this.toolbar = new CanvasToolbar(toolbarCanvas, editorArea);

    const resizeObserver = new ResizeObserver(([entry]) => {
      editorCanvas.width = entry.contentRect.width;
      editorCanvas.height = entry.contentRect.height;
      editorArea.repaint();
    });
    resizeObserver.observe(editorContainer);

    addMouseListeners(editorCanvas, editorArea);
    addKeyboardListeners(editorCanvas, editorArea);
  }

  getMolecule() {
    return this.editorArea.getMolecule();
  }

  setMolecule(molecule) {
    this.editorArea.setMolecule(molecule);
  }

  getReaction() {
    return this.editorArea.getReaction();
  }

  setReaction(reaction) {
    this.editorArea.setReaction(reaction);
  }
}

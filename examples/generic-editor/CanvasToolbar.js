import { CanvasDrawContext } from './CanvasDrawContext.js';
import { addMouseListeners } from './events.js';

const { EditorToolbar } = OCL;

export class CanvasToolbar {
  constructor(canvasElement, editorArea) {
    this.toolbar = new EditorToolbar(editorArea, {
      setDimensions(width, height) {
        canvasElement.width = width;
        canvasElement.height = height;
      },
      getDrawContext() {
        return new CanvasDrawContext(canvasElement.getContext('2d'));
      },
      getBackgroundRGB() {
        return 0xffffff;
      },

      getForegroundRGB() {
        return 0x000000;
      },
    });

    addMouseListeners(canvasElement, this.toolbar);
  }
}

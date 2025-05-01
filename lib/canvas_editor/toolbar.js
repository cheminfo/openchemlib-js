import { DrawContext } from './draw_context.js';
import { hidpiScaleFactor } from './utils.js';

export class Toolbar {
  constructor(canvasElement) {
    this.canvasElement = canvasElement;
    this.drawContext = new DrawContext(this.canvasElement.getContext('2d'));
  }

  setDimensions(width, height) {
    this.canvasElement.width = width;
    this.canvasElement.style.width = `${width / hidpiScaleFactor}px`;
    this.canvasElement.height = height;
    this.canvasElement.style.height = `${height / hidpiScaleFactor}px`;
  }

  getDrawContext() {
    return this.drawContext;
  }

  getBackgroundRGB() {
    return 0xffffff;
  }

  getForegroundRGB() {
    return 0x000000;
  }
}

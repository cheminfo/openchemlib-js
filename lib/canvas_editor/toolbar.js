import { DrawContext } from './draw_context.js';

export class Toolbar {
  constructor(canvasElement) {
    this.canvasElement = canvasElement;
    this.drawContext = new DrawContext(this.canvasElement.getContext('2d'));
  }

  setDimensions(width, height) {
    this.canvasElement.width = width;
    this.canvasElement.height = height;
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

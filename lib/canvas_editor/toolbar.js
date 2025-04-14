import { DrawContext } from './draw_context.js';

export class Toolbar {
  constructor(canvasElement) {
    this.canvasElement = canvasElement;
  }

  setDimensions(width, height) {
    this.canvasElement.width = width;
    this.canvasElement.height = height;
  }

  getDrawContext() {
    return new DrawContext(this.canvasElement.getContext('2d'));
  }

  getBackgroundRGB() {
    return 0xffffff;
  }

  getForegroundRGB() {
    return 0x000000;
  }
}

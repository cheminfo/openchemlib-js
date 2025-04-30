import { ClipboardHandler } from './clipboard_handler.js';
import { DrawContext } from './draw_context.js';

export class EditorArea {
  constructor(canvasElement, onChange) {
    this.canvasElement = canvasElement;
    this.changeListener = onChange;
    this.drawContext = new DrawContext(this.canvasElement.getContext('2d'));
  }
  getBackgroundRGB() {
    return 0xffffff;
  }
  getCanvasWidth() {
    return this.canvasElement.width;
  }
  getCanvasHeight() {
    return this.canvasElement.height;
  }
  getDrawContext() {
    return this.drawContext;
  }
  onChange(what, isUserEvent) {
    this.changeListener?.({ what, isUserEvent });
  }
  getClipboardHandler() {
    return new ClipboardHandler();
  }
}

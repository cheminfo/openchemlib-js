import { ClipboardHandler } from './clipboard_handler.js';
import { DrawContext } from './draw_context.js';

export class EditorArea {
  constructor(canvasElement, onChange) {
    this.canvasElement = canvasElement;
    this.changeListener = onChange;
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
    return new DrawContext(this.canvasElement.getContext('2d'));
  }
  onChange(what, isUserEvent) {
    this.changeListener?.({ what, isUserEvent });
  }
  getClipboardHandler() {
    return new ClipboardHandler();
  }
}

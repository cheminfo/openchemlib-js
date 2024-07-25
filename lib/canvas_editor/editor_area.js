'use strict';

const ClipboardHandler = require('./clipboard_handler');
const DrawContext = require('./draw_context');

class EditorArea {
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

module.exports = EditorArea;

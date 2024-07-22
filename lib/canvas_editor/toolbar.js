'use strict';

const DrawContext = require('./draw_context');
const { addMouseListeners } = require('./events');

class Toolbar {
  constructor(canvasElement, editorArea, JavaEditorArea, JavaEditorToolbar) {
    this.toolbar = new JavaEditorToolbar(editorArea, {
      setDimensions(width, height) {
        canvasElement.width = width;
        canvasElement.height = height;
      },
      getDrawContext() {
        return new DrawContext(canvasElement.getContext('2d'));
      },
      getBackgroundRGB() {
        return 0xffffff;
      },

      getForegroundRGB() {
        return 0x000000;
      },
    });

    addMouseListeners(canvasElement, this.toolbar, JavaEditorArea);
  }
}

module.exports = Toolbar;

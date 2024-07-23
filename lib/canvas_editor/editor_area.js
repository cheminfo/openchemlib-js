'use strict';

const ClipboardHandler = require('./clipboard_handler');
const CursorManager = require('./cursor_manager');
const DrawContext = require('./draw_context');
const EditorDialog = require('./editor_dialog');
const EditorImage = require('./editor_image');
const { decodeBase64 } = require('./utils');

class EditorArea {
  constructor(canvasElement, onChange, JavaEditorArea) {
    this.canvasElement = canvasElement;
    this.changeListener = onChange;
    this.cursorManager = new CursorManager(JavaEditorArea);
  }
  // JSEditorArea methods
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
  // JSUIHelper methods
  grabFocus() {
    this.canvasElement.focus({preventScroll: true});
  }
  setCursor(cursor) {
    this.canvasElement.style.cursor = this.cursorManager.getCursor(cursor);
  }
  showHelpDialog(/* url, title */) {
    // TODO: implement help dialog?
    // console.log({ url, title });
  }
  createImageFromBase64(width, height, base64) {
    base64 = base64.replaceAll('%', 'A'.repeat(20));
    const decoded = decodeBase64(base64);
    const typedArray = new Uint8ClampedArray(decoded);
    const imageData = new ImageData(typedArray, width, height);
    return new EditorImage(imageData);
  }
  createDialog(title) {
    return new EditorDialog(title);
  }
}

module.exports = EditorArea;

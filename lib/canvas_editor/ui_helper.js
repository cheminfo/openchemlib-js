'use strict';

const CursorManager = require('./cursor_manager_24');
const EditorDialog = require('./editor_dialog');
const EditorImage = require('./editor_image');
const { decodeBase64 } = require('./utils');

class UIHelper {
  /**
   *
   * @param {HTMLCanvasElement} canvasElement
   * @param {HTMLElement} dialogRoot
   * @param JavaEditorArea
   */
  constructor(canvasElement, dialogRoot, JavaEditorArea) {
    this.canvasElement = canvasElement;
    this.dialogRoot = dialogRoot;
    this.JavaEditorArea = JavaEditorArea;
  }

  register(javaUiHelper) {
    this.javaUiHelper = javaUiHelper;
    this.cursorManager = new CursorManager(this.JavaEditorArea, javaUiHelper);
  }

  grabFocus() {
    this.canvasElement.focus({ preventScroll: true });
  }

  setCursor(cursor) {
    this.canvasElement.style.cursor = this.cursorManager.getCursor(cursor);
  }

  showHelpDialog(/* url, title */) {
    // TODO: implement help dialog?
    // console.log({ url, title });
  }

  createImage(width, height) {
    const imageData = new ImageData(width, height);
    return new EditorImage(imageData);
  }

  createImageFromBase64(width, height, base64) {
    base64 = base64.replaceAll('%', 'A'.repeat(20));
    const decoded = decodeBase64(base64);
    const typedArray = new Uint8ClampedArray(decoded);
    const imageData = new ImageData(typedArray, width, height);
    return new EditorImage(imageData);
  }

  createDialog(title) {
    return new EditorDialog(title, this.dialogRoot);
  }
}

module.exports = UIHelper;

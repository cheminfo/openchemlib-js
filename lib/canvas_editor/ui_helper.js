import { CursorManager } from './cursor_manager_24.js';
import { EditorDialog } from './editor_dialog.js';
import { EditorImage } from './editor_image.js';
import { decodeBase64 } from './utils.js';

export class UIHelper {
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
    base64 = base64.replaceAll(/%\d+%/g, (match) =>
      'A'.repeat(Number(match.slice(1, -1))),
    );
    const decoded = decodeBase64(base64);
    const typedArray = new Uint8ClampedArray(decoded);
    const imageData = new ImageData(typedArray, width, height);
    return new EditorImage(imageData);
  }

  createDialog(title) {
    return new EditorDialog(title, this.dialogRoot);
  }

  runLater(fn) {
    if (typeof requestAnimationFrame === 'function') {
      requestAnimationFrame(fn);
    } else if (typeof setImmediate === 'function') {
      // eslint-disable-next-line no-undef
      setImmediate(fn);
    } else {
      setTimeout(fn, 0);
    }
  }
}

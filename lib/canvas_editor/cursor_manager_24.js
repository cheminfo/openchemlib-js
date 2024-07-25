'use strict';

const cursors = require('./cursors_24px');

const computedCursors = Object.create(null);

const scaleFactor = 3 / 4;

class CursorManager {
  constructor(JavaEditorArea) {
    this.HOTSPOT_32 = JavaEditorArea.HOTSPOT_32;
    this.IMAGE_NAME_32 = JavaEditorArea.IMAGE_NAME_32;
    this.cPointerCursor = JavaEditorArea.cPointerCursor;
    this.cTextCursor = JavaEditorArea.cTextCursor;
  }

  getCursor(cursor) {
    if (computedCursors[cursor]) {
      return computedCursors[cursor];
    }
    if (this.IMAGE_NAME_32[cursor]) {
      return this.buildCursor(cursor);
    }
    switch (cursor) {
      case this.cPointerCursor:
        return 'default';
      case this.cTextCursor:
        return 'text';
      default:
        throw new Error(`Unknown cursor: ${cursor}`);
    }
  }

  buildCursor(cursor) {
    const cursorName = this.IMAGE_NAME_32[cursor];
    const cursorUrl = cursors[cursorName];
    const builtCursor = `${cursorUrl} ${this.HOTSPOT_32[cursor * 2] * scaleFactor} ${this.HOTSPOT_32[cursor * 2 + 1] * scaleFactor}, default`;
    computedCursors[cursor] = builtCursor;
    return builtCursor;
  }
}

module.exports = CursorManager;

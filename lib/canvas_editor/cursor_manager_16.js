'use strict';

// This code is unused for now and is kept for reference in case we want to
// support low definition cursors in the future.

const computedCursors = Object.create(null);

class CursorManager {
  constructor(JavaEditorArea, javaUiHelper) {
    this.IMAGE_DATA_16 = JavaEditorArea.IMAGE_DATA_16;
    this.HOTSPOT_16 = JavaEditorArea.HOTSPOT_16;
    this.cPointerCursor = JavaEditorArea.cPointerCursor;
    this.cTextCursor = JavaEditorArea.cTextCursor;
    this.javaUiHelper = javaUiHelper;
  }

  getCursor(cursor) {
    if (computedCursors[cursor]) {
      return computedCursors[cursor];
    }
    if (this.IMAGE_DATA_16[cursor]) {
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
    const cursorImage = this.javaUiHelper.build16x16CursorImage(cursor);
    const dataURL = cursorImage.toDataURL();
    return `url(${dataURL}) ${this.HOTSPOT_16[cursor * 2]} ${this.HOTSPOT_16[cursor * 2 + 1]}, default`;
  }
}

module.exports = CursorManager;

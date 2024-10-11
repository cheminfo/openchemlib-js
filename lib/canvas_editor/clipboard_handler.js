'use strict';

class ClipboardHandler {
  putString(data) {
    void navigator.clipboard.writeText(data);
    return true;
  }
}

module.exports = ClipboardHandler;

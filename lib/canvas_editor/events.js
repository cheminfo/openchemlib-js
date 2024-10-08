'use strict';

/**
 *
 * @param {HTMLCanvasElement} canvasElement
 * @param drawArea
 * @param JavaEditorArea
 */
function addPointerListeners(canvasElement, drawArea, JavaEditorArea) {
  let pointerDownId = -1;

  function fireMouseEvent(what, ev, clickCount = 0) {
    if (ev.button > 0) {
      // TODO: remove this to implement popup menu.
      return;
    }
    drawArea.fireMouseEvent(
      what,
      ev.button + 1,
      clickCount,
      Math.round(ev.offsetX),
      Math.round(ev.offsetY),
      ev.shiftKey,
      ev.ctrlKey,
      ev.altKey,
      ev.button === 2,
    );
  }

  canvasElement.addEventListener('pointerdown', (ev) => {
    if (pointerDownId === -1) {
      pointerDownId = ev.pointerId;
      fireMouseEvent(JavaEditorArea.MOUSE_EVENT_PRESSED, ev, ev.detail);
    }
  });

  function handlePointerUp(ev) {
    if (pointerDownId === ev.pointerId) {
      pointerDownId = -1;
      fireMouseEvent(JavaEditorArea.MOUSE_EVENT_RELEASED, ev, ev.detail);
    }
  }
  // Listen on document to capture mouse release outside the canvas.
  document.addEventListener('pointerup', handlePointerUp);

  canvasElement.addEventListener('click', (ev) => {
    fireMouseEvent(JavaEditorArea.MOUSE_EVENT_CLICKED, ev, ev.detail);
  });
  canvasElement.addEventListener('pointerenter', (ev) => {
    fireMouseEvent(JavaEditorArea.MOUSE_EVENT_ENTERED, ev);
  });
  canvasElement.addEventListener('pointerleave', (ev) => {
    fireMouseEvent(JavaEditorArea.MOUSE_EVENT_EXITED, ev);
  });
  canvasElement.addEventListener('pointermove', (ev) => {
    if (pointerDownId !== -1) {
      if (pointerDownId === ev.pointerId) {
        fireMouseEvent(JavaEditorArea.MOUSE_EVENT_DRAGGED, ev);
      }
    } else {
      fireMouseEvent(JavaEditorArea.MOUSE_EVENT_MOVED, ev);
    }
  });

  return () => {
    document.removeEventListener('pointerup', handlePointerUp);
  };
}

function addKeyboardListeners(canvasElement, editorArea, JavaEditorArea) {
  const isMac =
    typeof navigator !== 'undefined' && navigator.platform === 'MacIntel';

  function fireKeyEvent(what, ev) {
    const key = getKeyFromEvent(ev, JavaEditorArea);
    if (key === null) return;
    // ev.stopPropagation();
    // ev.preventDefault();
    editorArea.fireKeyEvent(
      what,
      key,
      ev.altKey,
      ev.ctrlKey,
      ev.shiftKey,
      (isMac && ev.metaKey) || (!isMac && ev.ctrlKey),
    );
  }

  canvasElement.addEventListener('keydown', (ev) => {
    fireKeyEvent(JavaEditorArea.KEY_EVENT_PRESSED, ev);
  });
  canvasElement.addEventListener('keyup', (ev) => {
    fireKeyEvent(JavaEditorArea.KEY_EVENT_RELEASED, ev);
  });

  return () => {
    // No cleanup needed.
  };
}

/**
 *
 * @param {KeyboardEvent} ev
 * @param JavaEditorArea
 */
function getKeyFromEvent(ev, JavaEditorArea) {
  switch (ev.key) {
    case 'Control':
      return JavaEditorArea.KEY_CTRL;
    case 'Alt':
      return JavaEditorArea.KEY_ALT;
    case 'Shift':
      return JavaEditorArea.KEY_SHIFT;
    case 'Delete':
    case 'Backspace':
      return JavaEditorArea.KEY_DELETE;
    // Backspace is currently unused by the Java code, so we remap it.
    // return JavaEditorArea.KEY_BACKSPACE;
    case 'F1':
      return JavaEditorArea.KEY_HELP;
    case 'Escape':
      return JavaEditorArea.KEY_ESCAPE;
    default:
      if (ev.key.length === 1) {
        return ev.key.codePointAt(0);
      } else {
        return null;
      }
  }
}

module.exports = {
  addPointerListeners,
  addKeyboardListeners,
};

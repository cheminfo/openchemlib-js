const { EditorArea } = OCL;

export function addMouseListeners(canvasElement, drawArea) {
  let isMouseDown = false;

  function fireMouseEvent(what, ev, clickCount = 0) {
    if (ev.button !== 0) {
      // TODO: remove this to implement popup menu.
      return;
    }
    drawArea.fireMouseEvent(
      what,
      ev.button + 1,
      clickCount,
      ev.offsetX,
      ev.offsetY,
      ev.shiftKey,
      ev.ctrlKey,
      ev.altKey,
      ev.button === 2,
    );
  }

  canvasElement.addEventListener('mousedown', (ev) => {
    isMouseDown = true;
    fireMouseEvent(EditorArea.MOUSE_EVENT_PRESSED, ev);
  });
  canvasElement.addEventListener('mouseup', (ev) => {
    isMouseDown = false;
    fireMouseEvent(EditorArea.MOUSE_EVENT_RELEASED, ev);
  });
  canvasElement.addEventListener('click', (ev) => {
    fireMouseEvent(EditorArea.MOUSE_EVENT_CLICKED, ev, ev.detail);
  });
  canvasElement.addEventListener('mouseenter', (ev) => {
    fireMouseEvent(EditorArea.MOUSE_EVENT_ENTERED, ev);
  });
  canvasElement.addEventListener('mouseleave', (ev) => {
    fireMouseEvent(EditorArea.MOUSE_EVENT_EXITED, ev);
  });
  canvasElement.addEventListener('mousemove', (ev) => {
    if (isMouseDown) {
      fireMouseEvent(EditorArea.MOUSE_EVENT_DRAGGED, ev);
    } else {
      fireMouseEvent(EditorArea.MOUSE_EVENT_MOVED, ev);
    }
  });
}

export function addKeyboardListeners(canvasElement, editorArea) {
  const isMac =
    typeof navigator !== 'undefined' && navigator.platform === 'MacIntel';

  function fireKeyEvent(what, ev) {
    const key = getKeyFromEvent(ev);
    if (key === null) return;
    ev.stopPropagation();
    ev.preventDefault();
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
    fireKeyEvent(EditorArea.KEY_EVENT_PRESSED, ev);
  });
  canvasElement.addEventListener('keyup', (ev) => {
    fireKeyEvent(EditorArea.KEY_EVENT_RELEASED, ev);
  });
}

/**
 *
 * @param {KeyboardEvent} ev
 */
function getKeyFromEvent(ev) {
  switch (ev.key) {
    case 'Control':
      return EditorArea.KEY_CTRL;
    case 'Alt':
      return EditorArea.KEY_ALT;
    case 'Shift':
      return EditorArea.KEY_SHIFT;
    case 'Delete':
      return EditorArea.KEY_DELETE;
    case 'Backspace':
      return EditorArea.KEY_BACKSPACE;
    case 'F1':
      return EditorArea.KEY_HELP;
    case 'Escape':
      return EditorArea.KEY_ESCAPE;
    default:
      if (ev.key.length === 1) {
        return ev.key.charCodeAt(0);
      } else {
        return null;
      }
  }
}

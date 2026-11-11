import { hidpiScaleFactor } from './utils.js';

/**
 *
 * @param {HTMLCanvasElement} canvasElement
 * @param drawArea
 * @param JavaEditorArea
 */
export function addPointerListeners(canvasElement, drawArea, JavaEditorArea) {
  let pointerDownId = -1;

  function fireMouseEvent(what, event, clickCount = 0) {
    if (event.button > 0) {
      // TODO: remove this to implement popup menu.
      return;
    }
    drawArea.fireMouseEvent(
      what,
      event.button + 1,
      clickCount,
      Math.round(event.offsetX * hidpiScaleFactor),
      Math.round(event.offsetY * hidpiScaleFactor),
      event.shiftKey,
      event.ctrlKey,
      event.altKey,
      event.button === 2,
    );
  }

  canvasElement.addEventListener('pointerdown', (event) => {
    if (pointerDownId !== -1) {
      return;
    }

    pointerDownId = event.pointerId;
    fireMouseEvent(JavaEditorArea.MOUSE_EVENT_PRESSED, event);
  });

  function handlePointerUp(event) {
    if (pointerDownId !== event.pointerId) {
      return;
    }

    pointerDownId = -1;
    fireMouseEvent(JavaEditorArea.MOUSE_EVENT_RELEASED, event);
  }
  // Listen on document to capture mouse release outside the canvas.
  document.addEventListener('pointerup', handlePointerUp);

  canvasElement.addEventListener('click', (event) => {
    fireMouseEvent(JavaEditorArea.MOUSE_EVENT_CLICKED, event, event.detail);
  });
  canvasElement.addEventListener('pointerenter', (event) => {
    // event.detail on pointerenter doesn't include the click count
    fireMouseEvent(JavaEditorArea.MOUSE_EVENT_ENTERED, event);
  });
  canvasElement.addEventListener('pointerleave', (event) => {
    // event.detail on pointerenter doesn't include the click count
    fireMouseEvent(JavaEditorArea.MOUSE_EVENT_EXITED, event);
  });
  canvasElement.addEventListener('pointermove', (event) => {
    if (pointerDownId !== -1) {
      if (pointerDownId === event.pointerId) {
        fireMouseEvent(JavaEditorArea.MOUSE_EVENT_DRAGGED, event);
      }
    } else {
      fireMouseEvent(JavaEditorArea.MOUSE_EVENT_MOVED, event);
    }
  });

  return () => {
    document.removeEventListener('pointerup', handlePointerUp);
  };
}

export function addKeyboardListeners(
  parentElement,
  canvasElement,
  editorArea,
  JavaEditorArea,
  Molecule,
) {
  const isMac =
    typeof navigator !== 'undefined' && navigator.platform === 'MacIntel';
  const isMenuKey = (event) =>
    (isMac && event.metaKey) || (!isMac && event.ctrlKey);

  function fireKeyEvent(what, event) {
    const key = getKeyFromEvent(event, JavaEditorArea);
    if (key === null) return;

    editorArea.fireKeyEvent(
      what,
      key,
      event.altKey,
      event.ctrlKey,
      event.shiftKey,
      isMenuKey(event),
    );
  }

  canvasElement.addEventListener('keydown', (event) => {
    // copy-paste is handled by the clipboard event
    // java ctrl-c / ctrl-v events can't work and could throw error
    if (isMenuKey(event) && event.key === 'c') return;
    if (isMenuKey(event) && event.key === 'v') return;

    fireKeyEvent(JavaEditorArea.KEY_EVENT_PRESSED, event);
  });
  canvasElement.addEventListener('keyup', (event) => {
    fireKeyEvent(JavaEditorArea.KEY_EVENT_RELEASED, event);
  });

  // Firefox doesn't emit a paste event on canvas elements, so we listen on the parent.
  parentElement.addEventListener('paste', (event) => {
    const textData = event.clipboardData.getData('text');
    const molecule = Molecule.fromText(textData);
    if (molecule && molecule.getAllAtoms() > 0) {
      // TODO: send the cursor position to paste where it is located.
      editorArea.addPastedOrDropped(molecule);
    }
  });
}

/**
 *
 * @param {KeyboardEvent} event
 * @param JavaEditorArea
 */
function getKeyFromEvent(event, JavaEditorArea) {
  switch (event.key) {
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
    case 'Enter':
      return JavaEditorArea.KEY_ENTER;
    default:
      if (event.key.length === 1) {
        return event.key.codePointAt(0);
      }
      return null;
  }
}

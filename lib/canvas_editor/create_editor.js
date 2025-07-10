import { EditorArea } from './editor_area.js';
import { getEditorStylesheet } from './editor_stylesheet.js';
import { addKeyboardListeners, addPointerListeners } from './events.js';
import { Toolbar } from './toolbar.js';
import { UIHelper } from './ui_helper.js';
import { hidpiScaleFactor } from './utils.js';

export function createEditor(
  parentElement,
  options,
  onChange,
  JavaEditorArea,
  JavaEditorToolbar,
  JavaUIHelper,
  Molecule,
  Reaction,
) {
  const {
    readOnly = false,
    initialMode = 'molecule',
    initialFragment = false,
  } = options;

  const rootElement = document.createElement('div');
  Object.assign(rootElement.style, {
    width: '100%',
    height: '100%',
    display: 'flex',
    flexDirection: 'row',
    alignItems: 'start',
    backgroundColor: 'white',
    // Prevent side effects of pointer events, like scrolling the page or text
    // selection.
    touchAction: 'none',
    userSelect: 'none',
    webkitUserSelect: 'none',
  });

  const shadowRoot = rootElement.attachShadow({ mode: 'open' });

  shadowRoot.adoptedStyleSheets = [getEditorStylesheet()];

  let toolbarCanvas = null;
  if (!readOnly) {
    toolbarCanvas = document.createElement('canvas');
    shadowRoot.append(toolbarCanvas);
  }

  const editorContainer = document.createElement('div');
  Object.assign(editorContainer.style, {
    width: '100%',
    height: '100%',
  });
  shadowRoot.append(editorContainer);

  const editorCanvas = document.createElement('canvas');
  editorCanvas.tabIndex = 0;
  Object.assign(editorCanvas.style, {
    outline: 'none',
  });
  editorContainer.append(editorCanvas);

  parentElement.append(rootElement);

  const uiHelper = new JavaUIHelper(
    new UIHelper(editorCanvas, editorContainer, JavaEditorArea),
  );

  const editorArea = new JavaEditorArea(
    computeMode(initialMode, JavaEditorArea),
    new EditorArea(editorCanvas, onChange),
    uiHelper,
  );
  if (initialFragment) {
    if (initialMode === 'molecule') {
      const fragmentMolecule = new Molecule(0, 0);
      fragmentMolecule.setFragment(true);
      editorArea.setMolecule(fragmentMolecule);
    } else {
      const fragmentReaction = Reaction.create();
      fragmentReaction.setFragment(true);
      editorArea.setReaction(fragmentReaction);
    }
  }

  uiHelper.setEditorArea(editorArea);

  const toolbar = readOnly
    ? null
    : new JavaEditorToolbar(editorArea, new Toolbar(toolbarCanvas), uiHelper);

  function updateCanvasDimensions(containerSize) {
    editorCanvas.style.width = `${containerSize.width}px`;
    editorCanvas.width = Math.floor(containerSize.width * hidpiScaleFactor);
    editorCanvas.style.height = `${containerSize.height}px`;
    editorCanvas.height = Math.floor(containerSize.height * hidpiScaleFactor);

    // Do not repaint when the size is zero (there would be nothing to see anyway).
    // This can happen when the editor is hidden with `display: none` and breaks
    // the rendering on Chrome-based browsers.
    if (containerSize.width > 0 && containerSize.height > 0) {
      editorArea.repaint();
    }
  }

  const containerSize = editorContainer.getBoundingClientRect();
  updateCanvasDimensions(containerSize);

  const resizeObserver = new ResizeObserver(([entry]) => {
    updateCanvasDimensions(entry.contentRect);
  });
  resizeObserver.observe(editorContainer);

  let removePointerListeners = null;
  let removeKeyboardListeners = null;
  let removeToolbarPointerListeners = null;

  if (!readOnly) {
    removePointerListeners = addPointerListeners(
      editorCanvas,
      editorArea,
      JavaEditorArea,
    );
    removeKeyboardListeners = addKeyboardListeners(
      editorCanvas,
      editorArea,
      JavaEditorArea,
    );
    removeToolbarPointerListeners = addPointerListeners(
      toolbarCanvas,
      toolbar,
      JavaEditorArea,
    );
  }

  function destroy() {
    rootElement.remove();
    resizeObserver.disconnect();
    removePointerListeners?.();
    removeKeyboardListeners?.();
    removeToolbarPointerListeners?.();
  }

  return { editorArea, toolbar, uiHelper, destroy };
}

function computeMode(initialMode, JavaEditorArea) {
  switch (initialMode) {
    case 'molecule':
      return 0;
    case 'reaction':
      return (
        JavaEditorArea.MODE_REACTION | JavaEditorArea.MODE_MULTIPLE_FRAGMENTS
      );
    default:
      throw new Error(`Invalid initial mode: ${initialMode}`);
  }
}

'use strict';

class EditorDialog {
  /**
   *
   * @param {string} title
   * @param {HTMLElement} rootElement
   */
  constructor(title, rootElement) {
    this.title = title;
    this.rootElement = rootElement;
    this.elements = [];
    this.dialogElement = null;
  }

  setLayout(hLayout, vLayout) {
    this.hLayout = generateLayout(hLayout);
    this.vLayout = generateLayout(vLayout);
  }

  add(component, x, y, x2, y2) {
    this.elements.push({ component: component.getJsComponent(), x, y, x2, y2 });
  }

  createTextField(width, height) {
    return new TextField(width, height);
  }

  createLabel(text) {
    return new Label(text);
  }

  createComboBox() {
    return new ComboBox();
  }

  createCheckBox(text) {
    return new CheckBox(text);
  }

  setEventConsumer(consumer) {
    this.consumer = consumer;
  }

  showMessage(message) {
    // eslint-disable-next-line no-alert
    window.alert(message);
  }

  showDialog() {
    const dialog = document.createElement('dialog');
    const rootElementBounds = this.rootElement.getBoundingClientRect();
    // Center the dialog horizontally relative to the editor's canvas.
    Object.assign(dialog.style, {
      position: 'absolute',
      marginBlock: 0,
      left: `${rootElementBounds.left}px`,
      right: `${document.body.parentElement.clientWidth - rootElementBounds.right}px`,
      top: `${this.rootElement.offsetTop + 30}px`,
    });
    this.dialogElement = dialog;
    this.rootElement.getRootNode().append(dialog);
    const grid = document.createElement('div');
    grid.style.display = 'grid';
    grid.style.gridTemplateColumns = this.hLayout;
    grid.style.gridTemplateRows = this.vLayout;
    dialog.append(grid);
    for (const { component, x, y, x2, y2 } of this.elements) {
      const div = document.createElement('div');
      if (x2 === undefined) {
        div.style.gridColumn = `${x + 1} / ${x + 2}`;
        div.style.gridRow = `${y + 1} / ${y + 2}`;
      } else {
        div.style.gridColumn = `${x + 1} / ${x2 + 2}`;
        div.style.gridRow = `${y + 1} / ${y2 + 2}`;
      }
      div.append(component.getElement());
      grid.append(div);
    }
    const buttons = document.createElement('div');
    buttons.style.display = 'flex';
    buttons.style.flexDirection = 'row-reverse';
    buttons.style.gap = '15px';
    const okButton = document.createElement('button');
    okButton.textContent = 'OK';
    okButton.addEventListener('click', () => {
      this.consumer.fireOk();
    });
    buttons.append(okButton);
    const cancelButton = document.createElement('button');
    cancelButton.textContent = 'Cancel';
    cancelButton.addEventListener('click', () => {
      this.consumer.fireCancel();
    });
    buttons.append(cancelButton);
    dialog.append(buttons);
    dialog.showModal();
    dialog.addEventListener('cancel', () => {
      this.consumer.fireCancel();
    });
  }

  disposeDialog() {
    if (this.dialogElement !== null) {
      this.dialogElement.remove();
      this.dialogElement = null;
    }
  }
}

class Component {
  setEventHandler(eventHandler) {
    this.eventHandler = eventHandler;
  }

  fireEvent(what, value) {
    this.eventHandler(what, value);
  }
}

class Label extends Component {
  constructor(text) {
    super();
    this.element = document.createElement('label');
    this.setText(text);
  }

  setText(text) {
    this.element.textContent = text;
  }

  getElement() {
    return this.element;
  }
}

class TextField extends Component {
  constructor() {
    super();
    this.element = document.createElement('input');
    this.element.type = 'text';
  }

  setText(text) {
    this.element.value = text;
  }

  getText() {
    return this.element.value;
  }

  getElement() {
    return this.element;
  }
}

class ComboBox extends Component {
  constructor() {
    super();
    this.element = document.createElement('select');
    this.element.addEventListener('change', () => {
      this.fireEvent(2, this.element.selectedIndex);
    });
  }

  setEnabled(enabled) {
    this.element.disabled = !enabled;
  }

  addItem(item) {
    const option = document.createElement('option');
    option.textContent = item;
    this.element.append(option);
  }

  getSelectedIndex() {
    return this.element.selectedIndex;
  }

  setSelectedIndex(index) {
    this.element.selectedIndex = index;
  }

  setSelectedItem(item) {
    const options = this.element.options;
    for (let i = 0; i < options.length; i++) {
      if (options[i].textContent === item) {
        this.element.selectedIndex = i;
      }
    }
  }

  getSelectedItem() {
    return this.element.options[this.element.selectedIndex].textContent;
  }

  removeAllItems() {
    this.element.innerHTML = '';
  }

  getElement() {
    return this.element;
  }
}

class CheckBox extends Component {
  constructor(text) {
    super();
    const label = document.createElement('label');
    const input = document.createElement('input');
    input.type = 'checkbox';
    input.addEventListener('change', () => {
      this.fireEvent(3, input.checked ? 1 : 0);
    });
    label.append(input);
    label.append(text);
    this.element = label;
    this.checkBox = input;
  }

  setEnabled(enabled) {
    this.checkBox.disabled = !enabled;
  }

  isSelected() {
    return this.checkBox.checked;
  }

  setSelected(selected) {
    this.checkBox.checked = selected;
  }

  getElement() {
    return this.element;
  }
}

function generateLayout(layout) {
  return layout
    .map((dim) => {
      if (dim > 0) {
        return `${dim}px`;
      } else {
        return 'auto';
      }
    })
    .join(' ');
}

module.exports = EditorDialog;

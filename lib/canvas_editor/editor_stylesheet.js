'use strict';

const styles = `
/* We can customize editor styles here. */
`;

let stylesheet;

function getEditorStylesheet() {
  if (stylesheet) {
    return stylesheet;
  }

  const sheet = new CSSStyleSheet();
  sheet.replaceSync(styles);

  stylesheet = sheet;
  return sheet;
}

module.exports = getEditorStylesheet;

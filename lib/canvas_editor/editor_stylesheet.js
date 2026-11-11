const styles = `
/* We can customize editor styles here. */
`;

let stylesheet;

export function getEditorStylesheet() {
  if (stylesheet) {
    return stylesheet;
  }

  const sheet = new CSSStyleSheet();
  sheet.replaceSync(styles);

  // eslint-disable-next-line unicorn/no-top-level-assignment-in-function
  stylesheet = sheet;
  return sheet;
}

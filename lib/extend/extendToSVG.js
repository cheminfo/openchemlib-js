/**
 * Extends the instance method toSVG to handle options and sanitise the generated SVG.
 * @param {*} Molecule
 */
export function extendToSVG(Molecule) {
  const _toSVG = Molecule.prototype.toSVG;
  Molecule.prototype.toSVG = function toSVG(width, height, id, options) {
    if (typeof width !== 'number' || typeof height !== 'number') {
      throw new Error(
        'Molecule#toSVG requires width and height to be specified',
      );
    }
    options = options || {};
    const factorTextSize = options.factorTextSize || 1;
    const autoCrop = options.autoCrop === true;
    const autoCropMargin =
      options.autoCropMargin === undefined ? 5 : options.autoCropMargin;

    let svg = _toSVG.call(
      this,
      width,
      height,
      factorTextSize,
      autoCrop,
      autoCropMargin,
      id,
      options,
    );

    svg = svg.replace('<style>', '<style> text {font-family: sans-serif;}');
    if (options.fontWeight) {
      svg = svg.replaceAll(
        'font-size=',
        `font-weight="${options.fontWeight}" font-size=`,
      );
    }
    if (options.strokeWidth) {
      svg = svg.replaceAll(
        /stroke-width="[^"]+"/g,
        `stroke-width="${options.strokeWidth}"`,
      );
    }
    return svg;
  };
}

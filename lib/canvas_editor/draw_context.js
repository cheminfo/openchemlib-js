'use strict';

const { toHex } = require('./utils');

class DrawContext {
  /**
   *
   * @param {CanvasRenderingContext2D} ctx
   */
  constructor(ctx) {
    this.ctx = ctx;

    this.ctx.textAlign = 'left';
    this.ctx.textBaseline = 'top';

    this.currentFontSize = 12;
    this.currentFont = '12px sans-serif';
    this.ctx.font = this.currentFont;

    this.currentColor = '#000000';
    this.currentLineWidth = 1;
  }

  clearRect(x, y, w, h) {
    this.ctx.clearRect(x, y, w, h);
  }

  getBackgroundRGB() {
    return 0xffffff;
  }

  getForegroundRGB() {
    return 0x000000;
  }

  getSelectionBackgroundRGB() {
    // return 0xeb8282; // arbitrary color
    // return 0x000080; // color from java default AquaLookAndFeel "TextArea.selectionBackground"
    return 0xbbd6fc; // color from default macos highlight-accent color (blue)
  }

  getLineWidth() {
    return this.currentLineWidth;
  }

  setRGB(rgb) {
    const r = (rgb >>> 16) & 0xff;
    const g = (rgb >>> 8) & 0xff;
    const b = (rgb >>> 0) & 0xff;
    this.currentColor = `#${toHex(r)}${toHex(g)}${toHex(b)}`;
    this.ctx.fillStyle = this.currentColor;
    this.ctx.strokeStyle = this.currentColor;
  }

  setFont(size, isBold, isItalic) {
    this.currentFontSize = size;
    this.currentFont = `${isBold ? 'bold' : ''} ${
      isItalic ? 'italic' : ''
    } ${size}px sans-serif`;
    this.ctx.font = this.currentFont;
  }

  getFontSize() {
    return this.currentFontSize;
  }

  getBounds(s, rect) {
    const metrics = this.ctx.measureText(s);
    rect.set(
      metrics.actualBoundingBoxLeft,
      metrics.actualBoundingBoxAscent,
      metrics.actualBoundingBoxRight,
      metrics.actualBoundingBoxAscent,
    );
  }

  drawString(x, y, s) {
    this.ctx.fillText(s, x, y);
  }

  drawCenteredString(x, y, s) {
    this.ctx.textAlign = 'center';
    this.ctx.textBaseline = 'middle';
    this.ctx.fillText(s, x, y);
    this.ctx.textAlign = 'left';
    this.ctx.textBaseline = 'top';
  }

  setLineWidth(lineWidth) {
    this.currentLineWidth = lineWidth;
    this.ctx.lineWidth = lineWidth;
  }

  fillRectangle(x, y, w, h) {
    this.ctx.fillRect(x, y, w, h);
  }

  fillCircle(x, y, d) {
    const r = d / 2;
    this.ctx.beginPath();
    this.ctx.arc(x + r, y + r, r, 0, 2 * Math.PI);
    this.ctx.fill();
  }

  drawLine(x1, y1, x2, y2) {
    this.ctx.beginPath();
    this.ctx.moveTo(x1, y1);
    this.ctx.lineTo(x2, y2);
    this.ctx.stroke();
  }

  drawPolygon(p) {
    this.ctx.beginPath();
    this.ctx.moveTo(p.getX(0), p.getY(0));
    for (let i = 1; i < p.getSize(); i++) {
      this.ctx.lineTo(p.getX(i), p.getY(i));
    }
    this.ctx.stroke();
  }

  drawRectangle(x, y, w, h) {
    this.ctx.strokeRect(x, y, w, h);
  }

  fillPolygon(p) {
    this.ctx.beginPath();
    this.ctx.moveTo(p.getX(0), p.getY(0));
    for (let i = 1; i < p.getSize(); i++) {
      this.ctx.lineTo(p.getX(i), p.getY(i));
    }
    this.ctx.fill();
  }

  drawImage(image, sx, sy, sw, sh, dx, dy, dw, dh) {
    if (arguments.length !== 9) {
      throw new Error(
        `drawImage call with ${arguments.length} arguments unimplemented`,
      );
    }
    const fullScaleCanvas = document.createElement('canvas');
    /** @type {ImageData} */
    const imageData = image.imageData;
    fullScaleCanvas.width = imageData.width;
    fullScaleCanvas.height = imageData.height;
    const fullScaleContext = fullScaleCanvas.getContext('2d');
    fullScaleContext.globalAlpha = 0;
    fullScaleContext.putImageData(imageData, 0, 0);
    this.ctx.drawImage(fullScaleCanvas, sx, sy, sw, sh, dx, dy, dw, dh);
  }

  isDarkBackground() {
    return false;
  }
}

module.exports = DrawContext;

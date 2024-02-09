export class CanvasEditorImage {
  /**
   *
   * @param {ImageData} imageData
   */
  constructor(imageData) {
    this.imageData = imageData;
    this.dataView = new DataView(imageData.data.buffer);
  }

  getWidth() {
    return this.imageData.width;
  }

  getHeight() {
    return this.imageData.height;
  }

  getRGB(x, y) {
    const color = this.dataView.getInt32(
      (y * this.imageData.width + x) * 4,
      false,
    );
    const alpha = color & 0xff;
    return (alpha << 24) | (color >>> 8);
  }

  setRGB(x, y, argb) {
    const alpha = (argb >>> 24) & 0xff;
    const rgb = (argb << 8) | alpha;
    this.dataView.setInt32((y * this.imageData.width + x) * 4, rgb, false);
  }
}
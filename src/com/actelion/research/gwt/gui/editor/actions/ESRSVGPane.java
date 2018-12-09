package com.actelion.research.gwt.gui.editor.actions;

import com.actelion.research.gwt.gui.editor.ImageHolder;
import com.actelion.research.gwt.gui.editor.ToolBar;
import com.actelion.research.gwt.gui.viewer.GraphicsContext;
import com.actelion.research.share.gui.editor.Model;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Project: User: rufenec Date: 4/2/13 Time: 2:08 PM
 */

public class ESRSVGPane extends AbstractESRPane {
  private boolean pressed = false;
  private boolean loaded = false;
  private int scale = 1;
  private static final Image ESR_BUTTON_UP = new Image(ImageHolder.ESRBUTTONS);

  public ESRSVGPane(Model m, int scale) {
    super(m, ESR_BUTTON_UP.getWidth() * scale, ESR_BUTTON_UP.getHeight() * scale);
    this.scale = scale;

    ESR_BUTTON_UP.addLoadHandler(new LoadHandler() {
      @Override
      public void onLoad(LoadEvent event) {
        if (loaded)
          requestLayout();
        loaded = true;
      }
    });
  }

  @Override
  public void onMouseButtonReleased() {
    if (pressed) {
      hide();
      requestLayout();
    }
  }

  @Override
  public void onMouseButtonPressed() {
    pressed = true;
  }

  @Override
  public void drawButtons(GraphicsContext ctx) {
    drawAllButtons(ctx);
    drawSelectedButton(ctx);
  }

  private void drawSelectedButton(GraphicsContext ctx) {
    int selectedRow = model.rowFromESRType(model.getESRType());
    double dx = ESR_BUTTON_UP.getWidth() * scale;
    double dy = ESR_BUTTON_UP.getHeight() * scale / ToolBar.ESR_IMAGE_ROWS;
    int y = (int) (ESR_BUTTON_UP.getHeight() * scale / ToolBar.ESR_IMAGE_ROWS * selectedRow);
    int x = 0;
    ctx.setFill(model.getGeomFactory().getDrawConfig().createColor(.5, .5, .5, .5));
    ctx.fillRect(x, y, dx, dy);
  }

  private void drawAllButtons(GraphicsContext ctx) {
    ctx.clearRect(0, 0, ESR_BUTTON_UP.getWidth() * scale, ESR_BUTTON_UP.getHeight() * scale);
    ctx.save();
    ctx.setFill(model.getGeomFactory().getDrawConfig().createColor(1, 1, 1, 1));
    ctx.fillRect(0, 0, ESR_BUTTON_UP.getWidth() * scale, ESR_BUTTON_UP.getHeight() * scale);
    ctx.setStroke(model.getGeomFactory().getDrawConfig().createColor(.5, .5, .5, 1));
    ctx.drawRect(0, 0, ESR_BUTTON_UP.getWidth() * scale, ESR_BUTTON_UP.getHeight() * scale);
    ctx.restore();
    ctx.drawImage(ESR_BUTTON_UP, 0, 0, ESR_BUTTON_UP.getWidth(), ESR_BUTTON_UP.getHeight(), 0, 0,
        ESR_BUTTON_UP.getWidth() * scale, ESR_BUTTON_UP.getHeight() * scale);
  }

}

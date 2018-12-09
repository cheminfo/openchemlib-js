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

public class ESRPane extends AbstractESRPane {

  private boolean loaded = false;

  private static final Image ESR_BUTTON_UP = new Image(ImageHolder.ESRBUTTONUP64);
  private static final Image ESR_BUTTON_DOWN = new Image(ImageHolder.ESRBUTTONDOWN64);

  public ESRPane(Model m) {
    super(m, ESR_BUTTON_DOWN.getWidth(), ESR_BUTTON_UP.getHeight());
    this.model = m;

    ESR_BUTTON_UP.addLoadHandler(new LoadHandler() {
      @Override
      public void onLoad(LoadEvent event) {
        if (loaded)
          requestLayout();
        loaded = true;
      }
    });

    ESR_BUTTON_DOWN.addLoadHandler(new LoadHandler() {
      @Override
      public void onLoad(LoadEvent event) {
        if (loaded)
          requestLayout();
        loaded = true;
      }
    });

    // This class allows us to modify the pane.
    // This might be necessary for instance with z-index
    // if the editor is placed in an app which modifies the z-index e.g.
    // angular/bootstrap etc.
    getElement().setClassName("ESRPane");
  }

  @Override
  public void onMouseButtonReleased() {

  }

  @Override
  public void onMouseButtonPressed() {

  }

  @Override
  public void drawButtons(GraphicsContext ctx) {
    ctx.drawImage(ESR_BUTTON_UP, 0, 0);
    int selectedRow = model.rowFromESRType(model.getESRType());
    double dx = ToolBar.ESR_IMAGE_WIDTH;
    double dy = ToolBar.ESR_IMAGE_HEIGHT / ToolBar.ESR_IMAGE_ROWS;
    int y = (int) (ToolBar.ESR_IMAGE_HEIGHT / ToolBar.ESR_IMAGE_ROWS * selectedRow);
    int x = 0;
    ctx.drawImage(ESR_BUTTON_DOWN, x, y, dx, dy, x, y, dx, dy);
  }

}

/*

Copyright (c) 2015-2017, cheminfo

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.
    * Neither the name of {{ project }} nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package com.actelion.research.gwt.gui.editor;

import com.actelion.research.gwt.gui.editor.actions.ESRSVGTypeAction;
import com.actelion.research.gwt.gui.viewer.GraphicsContext;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.actions.*;
import com.actelion.research.share.gui.editor.listeners.IChangeListener;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Project: User: rufenec Date: 7/2/2014 Time: 10:53 AM
 */
class SVGToolBarImpl implements ToolBar<Element>, IChangeListener {
  static int instanceCount = 0;

  private final Image BUTTON_SVG_IMAGE = new Image(ImageHolder.SVGDRAWBUTTON);
  private final Image BUTTON_ESRAND = new Image(ImageHolder.SVGESRAND);
  private final Image BUTTON_ESROR = new Image(ImageHolder.SVGESROR);

  private Action[][] ACTIONS = null;
  private Model model = null;
  private Canvas canvas = null;
  private int selectedRow;
  private int selectetCol;
  private Action currentAction = null;
  private Action lastAction = null;
  private Action hoverAction;

  private int scale = 1;
  private boolean focus;

  SVGToolBarImpl(Model model, int scale) {
    this.model = model;
    this.scale = scale;
    instanceCount++;
  }

  /**
   * Creates the HTML Element containing the toolbar
   * 
   * @param parent Parent HTML element
   * @param width  Element with (specified via inline style)
   * @param height Element height (specified via inline style)
   * @return Element containing/representing the toolbar
   */
  public Element createElement(Element parent, int width, int height) {
    String toolBarId = "toolbar" + instanceCount;

    DivElement toolbarHolder = Document.get().createDivElement();
    toolbarHolder.setId(toolBarId);
    toolbarHolder.setAttribute("style",
        "position:absolute;width:" + width * scale + "px;height:" + height * scale + "px;");
    parent.appendChild(toolbarHolder);
    canvas = Util.createScaledCanvas(width * scale, height * scale);
    canvas.addFocusHandler(new FocusHandler() {
      @Override
      public void onFocus(FocusEvent event) {
        focus = true;
      }
    });

    canvas.addBlurHandler(new BlurHandler() {
      @Override
      public void onBlur(BlurEvent event) {
        focus = false;
      }
    });
    // Add the canvas to the toolbar container
    RootPanel.get(toolBarId).add(canvas);

    setupActions();
    setupMouseHandlers();
    model.addChangeListener(this);

    return toolbarHolder;
  }

  /**
   * Setup the button actions
   */
  private void setupActions() {
    ACTIONS = new Action[][] { { new ClearAction(model), new UndoAction(model) },
        { new CleanAction(model), new ZoomRotateAction(model) },
        { new SelectionAction(model), model.isReaction() ? new AtomMapAction(model) : null, },
        { new UnknownParityAction(model), new ESRSVGTypeAction(model, scale) }, { new DeleteAction(model), null },
        { new NewBondAction(model), new NewChainAction(model) },
        { new UpBondAction(model), new DownBondAction(model), },
        { new AddRingAction(model, 3, false), new AddRingAction(model, 4, false), },
        { new AddRingAction(model, 5, false), new AddRingAction(model, 6, false), },
        { new AddRingAction(model, 7, false), new AddRingAction(model, 6, true), },
        { new ChangeChargeAction(model, true), new ChangeChargeAction(model, false), },
        { new ChangeAtomAction(model, 6), new ChangeAtomAction(model, 14), },
        { new ChangeAtomAction(model, 7), new ChangeAtomAction(model, 15), },
        { new ChangeAtomAction(model, 8), new ChangeAtomAction(model, 16), },
        { new ChangeAtomAction(model, 9), new ChangeAtomAction(model, 17), },
        { new ChangeAtomAction(model, 35), new ChangeAtomAction(model, 53), },
        { new ChangeAtomAction(model, 1), new ChangeAtomPropertiesAction(model) }, };
    lastAction = setAction(2, 0);// currentAction = ACTIONS[selectedRow][selectetCol];
  }

  /**
   * Drawing function
   * 
   * @param canvas
   */
  private void draw(Canvas canvas) {
    Context2d context2d = canvas.getContext2d();
    GraphicsContext ctx = new GraphicsContext(context2d);
    drawButtons(ctx);
    drawESRButtons(ctx);
    drawHover(ctx);
  }

  /**
   * Returns the row/col of an action within the button image
   * 
   * @param a Action to be found
   * @return Point x,y representing column and row of the action of null if action
   *         is not found within the image
   */
  public Point getActionPosition(Action a) {
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLS; col++) {
        if (ACTIONS[row][col] == a) {
          return new Point(col, row);
        }
      }
    }
    return null;
  }

  /**
   * @param ctx
   */
  private void drawButtons(GraphicsContext ctx) {
    ctx.clearRect(0, 0, IMAGE_WIDTH * scale, IMAGE_HEIGHT * scale);
    ctx.drawImage(BUTTON_SVG_IMAGE, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, 0, 0, IMAGE_WIDTH * scale, IMAGE_HEIGHT * scale);

    if (selectedRow != -1 && selectetCol != -1) {
      double dx = IMAGE_WIDTH / COLS * scale;
      double dy = IMAGE_HEIGHT / ROWS * scale;
      int y = (int) (IMAGE_HEIGHT / ROWS * selectedRow * scale);
      int x = (int) (IMAGE_WIDTH / COLS * selectetCol * scale);
      Action action = getCurrentAction();
      Point pt = getActionPosition(action);
      ctx.setFill(model.getGeomFactory().getDrawConfig().createColor(.5, .5, .5, .5));
      ctx.fillRect(x, y, dx, dy);
    }

    double dy = IMAGE_HEIGHT * scale / ROWS;
    double dx = IMAGE_WIDTH * scale / COLS;
    for (int row = 0; row < ROWS + 1; row++) {
      ctx.setStroke(model.getGeomFactory().getDrawConfig().createColor(.8, .8, .8, .5));
      ctx.drawLine(0, dy * row, IMAGE_WIDTH * scale, dy * row);
    }
    for (int col = 0; col <= COLS; col++) {
      ctx.drawLine(col * dx, 0, col * dx, IMAGE_HEIGHT * scale);
    }

  }

  /**
   * Draw the button when mouse is hovering it
   * 
   * @param ctx
   */
  private void drawHover(GraphicsContext ctx) {
    if (hoverAction != null && hoverAction != getCurrentAction()) {
      double dx = IMAGE_WIDTH / COLS * scale;
      double dy = IMAGE_HEIGHT / ROWS * scale;
      Point pt = getActionPosition(hoverAction);
      int y = (int) (IMAGE_HEIGHT * scale / ROWS * pt.y);
      int x = (int) (IMAGE_WIDTH * scale / COLS * pt.x);
      ctx.setFill(model.getGeomFactory().getDrawConfig().createColor(.3, .3, .3, .5));
      ctx.fillRect(x, y, dx, dy);
    }
  }

  /**
   * Draw the current ESR image
   * 
   * @param ctx
   */
  private void drawESRButtons(GraphicsContext ctx) {
    int row = Model.rowFromESRType(model.getESRType());
    int y = (int) (IMAGE_HEIGHT * scale / ROWS * 3);
    int x = (int) (IMAGE_WIDTH * scale / COLS * 1);
    double dx = IMAGE_WIDTH * scale / COLS;
    double dy = IMAGE_HEIGHT * scale / ROWS;

    Image node = null;
    switch (row) {
    case 0:
      break;
    case 1:
      node = BUTTON_ESROR;
      break;
    case 2:
      node = BUTTON_ESRAND;
      break;
    }
    if (node != null) {
      ctx.clearRect(x, y, node.getWidth() * scale, node.getHeight() * scale);
      ctx.drawImage(node, 0, 0, node.getWidth(), node.getHeight(), x, y, node.getWidth() * scale,
          node.getHeight() * scale);
    }

  }

  private void setupMouseHandlers() {
    canvas.addMouseDownHandler(new MouseDownHandler() {
      @Override
      public void onMouseDown(MouseDownEvent event) {
        onMousePressed(event);

      }
    });

    canvas.addMouseUpHandler(new MouseUpHandler() {
      @Override
      public void onMouseUp(MouseUpEvent event) {
        onMouseReleased(event);
      }
    });
    canvas.addMouseMoveHandler(new MouseMoveHandler() {
      @Override
      public void onMouseMove(MouseMoveEvent event) {
        onMouseMoved(event);
      }
    });
    canvas.addMouseOutHandler(new MouseOutHandler() {
      @Override
      public void onMouseOut(MouseOutEvent event) {
        onMouseLeft(event);
      }
    });

  }

  private void onMouseLeft(MouseOutEvent event) {
    if (hoverAction != null) {
      hoverAction = null;
      repaint();
    }
  }

  private void onMouseMoved(MouseMoveEvent evt) {
    double x = evt.getX();
    double y = evt.getY();
    if (x >= 0 && x <= IMAGE_WIDTH * scale && y >= 0 && y < IMAGE_HEIGHT * scale) {
      double dy = IMAGE_HEIGHT / ROWS * scale;
      double dx = IMAGE_WIDTH / COLS * scale;
      int col = (int) (x / dx);
      int row = (int) (y / dy);
      Action a = getAction(row, col);
      if (a != hoverAction) {
        hoverAction = a;
        repaint();
      }
    } else {
      if (hoverAction != null) {
        hoverAction = null;
        repaint();
      }
    }
  }

  /**
   * Find the action at row, col within the button image
   * 
   * @param row y position
   * @param col x position
   * @return Action found or null with outside of the buttons range
   */
  public Action getAction(int row, int col) {
    if (row >= 0 && row < ROWS && col >= 0 && col < COLS)
      return ACTIONS[row][col];
    return null;
  }

  Action setAction(Action a) {
    selectedRow = -1;
    selectetCol = -1;
    currentAction = a;
    for (int r = 0; r < ACTIONS.length; r++) {
      for (int c = 0; c < 2; c++) {
        if (ACTIONS[r][c] == a) {
          selectedRow = r;
          selectetCol = c;
          currentAction = a;
        }
      }
    }
    return currentAction;
  }

  Action setAction(int row, int col) {
    if (ACTIONS[row][col] != null) {
      selectedRow = row;
      selectetCol = col;
      Action last = currentAction;
      currentAction = ACTIONS[selectedRow][selectetCol];
      if (last != null && last != currentAction) {
        last.onActionLeave();
      }
      currentAction.onActionEnter();
      lastAction = last;
    } else {
      System.err.println("Error setting null action:");
    }
    return currentAction;

  }

  private void onMousePressed(MouseEvent evt) {
    double x = evt.getX();
    double y = evt.getY();
    if (x >= 0 && x <= IMAGE_WIDTH * scale && y >= 0 && y < IMAGE_HEIGHT * scale) {
      double dy = IMAGE_HEIGHT / ROWS * scale;
      double dx = IMAGE_WIDTH / COLS * scale;
      int col = (int) (x / dx);
      int row = (int) (y / dy);
      Action action = setAction(row, col);
      if (action instanceof ButtonPressListener) {
        ButtonPressListener bpl = (ButtonPressListener) action;
        bpl.onButtonPressed(new Window(canvas), new Point2D.Double(x, y));
      }
      repaint();
    }
  }

  private void repaint() {
    draw(canvas);
  }

  private void onMouseReleased(MouseEvent evt) {
    boolean repaint = false;
    if (currentAction != null) {
      if (currentAction instanceof ButtonPressListener) {
        ButtonPressListener bpl = (ButtonPressListener) currentAction;
        bpl.onButtonReleased(/* this.getScene().getWindow() */null, new Point2D.Double(evt.getX(), evt.getY()));
        repaint = true;
      }
      if (currentAction.isCommand()) {
        currentAction.onMouseUp(new ACTMouseEvent(evt));
        setAction(lastAction);
        repaint = true;
      }
    }
    if (repaint) {
      repaint();
    }
  }

  @Override
  public Action getCurrentAction() {
    return currentAction;
  }

  @Override
  public boolean hasFocus() {
    return focus;
  }

  @Override
  public void onChange() {
    repaint();
  }

  public void doAction(Action a) {
    if (a.isCommand()) {
      a.onCommand();
    } else {
      setAction(a);
    }
    repaint();
  }

}

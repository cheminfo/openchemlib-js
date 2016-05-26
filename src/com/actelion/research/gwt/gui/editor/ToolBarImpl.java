/*

Copyright (c) 2015-2016, cheminfo

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

import com.actelion.research.gwt.gui.editor.actions.ESRTypeAction;
import com.actelion.research.gwt.gui.viewer.GraphicsContext;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.actions.Action;
import com.actelion.research.share.gui.editor.actions.AddRingAction;
import com.actelion.research.share.gui.editor.actions.AtomMapAction;
import com.actelion.research.share.gui.editor.actions.ChangeAtomAction;
import com.actelion.research.share.gui.editor.actions.ChangeAtomPropertiesAction;
import com.actelion.research.share.gui.editor.actions.ChangeChargeAction;
import com.actelion.research.share.gui.editor.actions.CleanAction;
import com.actelion.research.share.gui.editor.actions.ClearAction;
import com.actelion.research.share.gui.editor.actions.DeleteAction;
import com.actelion.research.share.gui.editor.actions.DownBondAction;
import com.actelion.research.share.gui.editor.actions.NewBondAction;
import com.actelion.research.share.gui.editor.actions.NewChainAction;
import com.actelion.research.share.gui.editor.actions.SelectionAction;
import com.actelion.research.share.gui.editor.actions.UndoAction;
import com.actelion.research.share.gui.editor.actions.UnknownParityAction;
import com.actelion.research.share.gui.editor.actions.UpBondAction;
import com.actelion.research.share.gui.editor.actions.ZoomRotateAction;
import com.actelion.research.share.gui.editor.listeners.IChangeListener;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import java.awt.geom.Point2D;

/**
 * Project:
 * User: rufenec
 * Date: 7/2/2014
 * Time: 10:53 AM
 */
class ToolBarImpl implements ToolBar<Element>, IChangeListener
{
    static int instanceCount = 0;

    private final Image BUTTON_UP = new Image(ImageHolder.DRAWBUTTONUP64);
    private final Image BUTTON_DOWN = new Image(ImageHolder.DRAWBUTTONDOWN64);
    private final Image ESR_BUTTON_UP = new Image(ImageHolder.ESRBUTTONUP64);
    private final Image ESR_BUTTON_DOWN = new Image(ImageHolder.ESRBUTTONDOWN64);

    public static final int ESR_BUTTON_ROW = 3;
    public static final int ESR_BORDER = 3;


    private Action[][] ACTIONS = null;
    private Model model = null;
    Canvas canvas = null;
    private int selectedRow;
    private int selectetCol;
    private Action currentAction = null;
    private Action lastAction = null;
    private boolean loaded = false;



    ToolBarImpl(Model model)
    {
        this.model = model;
        instanceCount++;
    }

    public Element createElement(Element parent, int width, int height)
    {
        String toolBarId = "toolbar" + instanceCount;

        BUTTON_UP.addLoadHandler(new LoadHandler()
        {
            @Override
            public void onLoad(LoadEvent event)
            {
                if (loaded)
                    requestLayout();
                loaded = true;
            }
        });

        BUTTON_DOWN.addLoadHandler(new LoadHandler()
        {
            @Override
            public void onLoad(LoadEvent event)
            {
                if (loaded)
                    requestLayout();
                loaded = true;
            }
        });

        DivElement toolbarHolder = Document.get().createDivElement();
        toolbarHolder.setId(toolBarId);
        toolbarHolder.setAttribute(
            "style", "position:absolute;width:" + width + "px;height:" + height + "px;");
        parent.appendChild(toolbarHolder);
        canvas = Canvas.createIfSupported();
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
        canvas.setWidth(width + "px");
        canvas.setHeight(height + "px");
        RootPanel.get(toolBarId).add(canvas);

        //  This is to force addLoadHandler
        BUTTON_UP.setVisible(false);
        RootPanel.get(toolBarId).add(BUTTON_UP);
        BUTTON_DOWN.setVisible(false);
        RootPanel.get(toolBarId).add(BUTTON_DOWN);

        setupHandlers();
        setupMouseHandlers();
        model.addChangeListener(this);

        return toolbarHolder;
    }

    private void requestLayout()
    {
        draw(canvas);
    }



    private void draw(Canvas toolBar)
    {
        Context2d context2d = toolBar.getContext2d();
//        context2d.drawImage(ImageElement.as(ESR_BUTTON_UP.getElement()), 0, 0);
        GraphicsContext ctx = new GraphicsContext(context2d);
        drawButtons(ctx);
        drawESRButtons(ctx);
    }



    private void drawButtons(GraphicsContext ctx)
    {
        ctx.drawImage(BUTTON_UP, 0, 0);

        if (selectedRow != -1 && selectetCol != -1) {
            double dx = IMAGE_WIDTH / COLS;
            double dy = IMAGE_HEIGHT / ROWS;
            int y = (int) (IMAGE_HEIGHT / ROWS * selectedRow);
            int x = (int) (IMAGE_WIDTH / COLS * selectetCol);
            ctx.drawImage(BUTTON_DOWN, x, y, dx, dy, x, y, dx, dy);
        }

        // Draw  inactive buttons
        for (int row =0; row < ACTIONS.length; row++)
        {
            double dx = IMAGE_WIDTH / COLS;
            double dy = IMAGE_HEIGHT / ROWS;
            for (int col = 0; col < ACTIONS[row].length; col++) {
                if (ACTIONS[row][col] == null) {
                    int y = (int) (IMAGE_HEIGHT / ROWS * row);
                    int x = (int) (IMAGE_WIDTH / COLS * col);
                    ctx.save();
                    ctx.setFill(0xFFFFFFAA);
                    ctx.fillRect(x+2,y+2,dx-4,dy-2);
                    ctx.restore();
                }
            }
        }
    }

    private void drawESRButtons(GraphicsContext ctx)
    {

        double ESRdx = ESR_IMAGE_WIDTH - 2 * ESR_BORDER;
        double ESRdy = (ESR_IMAGE_HEIGHT - 2 * ESR_BORDER) / ESR_IMAGE_ROWS;
        int row = Model.rowFromESRType(model.getESRType());
        int ESRy = (int) ((ESR_IMAGE_HEIGHT - 2 * ESR_BORDER) / ESR_IMAGE_ROWS * row + ESR_BORDER);
        int ESRx = ESR_BORDER;

        if (currentAction instanceof ESRTypeAction) {
            ctx.drawImage(ESR_BUTTON_DOWN, ESRx, ESRy, ESRdx, ESRdy, ESRdx, ESRdy * ESR_BUTTON_ROW, ESRdx, ESRdy);
        } else {
            ctx.drawImage(ESR_BUTTON_UP, ESRx, ESRy, ESRdx, ESRdy, ESRdx, ESRdy * ESR_BUTTON_ROW, ESRdx, ESRdy);
        }
    }

    private void setupMouseHandlers()
    {
//        canvas.addMouseMoveHandler(new MouseMoveHandler()
//        {
//            @Override
//            public void onMouseMove(MouseMoveEvent event)
//            {
//                onMouseMove();
//            }
//        });
        canvas.addMouseDownHandler(new MouseDownHandler()
        {
            @Override
            public void onMouseDown(MouseDownEvent event)
            {
//                System.out.println("Mouse Down in Toolbar " + event);
                onMousePressed(event);

            }
        });

        canvas.addMouseUpHandler(new MouseUpHandler()
        {
            @Override
            public void onMouseUp(MouseUpEvent event)
            {
                onMouseReleased(event);
            }
        });

    }

    private void setupHandlers()
    {
        ACTIONS = new Action[][]
            {
                {
                    new ClearAction(model),
                    new UndoAction(model)
                },
                {
                    new CleanAction(model),
                    new ZoomRotateAction(model)
                },
                {
                    new SelectionAction(model),
                    model.isReaction() ? new AtomMapAction(model) : null,
                },
                {
                    new UnknownParityAction(model),
                    new ESRTypeAction(model)
                },
                {
                    new DeleteAction(model),
                    null
                },
                {
                    new NewBondAction(model),
                    new NewChainAction(model)
                },
                {
                    new UpBondAction(model),
                    new DownBondAction(model),
                },
                {
                    new AddRingAction(model, 3, false),
                    new AddRingAction(model, 4, false),
                },
                {
                    new AddRingAction(model, 5, false),
                    new AddRingAction(model, 6, false),
                },
                {
                    new AddRingAction(model, 7, false),
                    new AddRingAction(model, 6, true),
                },
                {
                    new ChangeChargeAction(model, true),
                    new ChangeChargeAction(model, false),
                },
                {
                    new ChangeAtomAction(model, 6),
                    new ChangeAtomAction(model, 14),
                },
                {
                    new ChangeAtomAction(model, 7),
                    new ChangeAtomAction(model, 15),
                },
                {
                    new ChangeAtomAction(model, 8),
                    new ChangeAtomAction(model, 16),
                },
                {
                    new ChangeAtomAction(model, 9),
                    new ChangeAtomAction(model, 17),
                },
                {
                    new ChangeAtomAction(model, 35),
                    new ChangeAtomAction(model, 53),
                },
                {
                    new ChangeAtomAction(model, 1),
                    new ChangeAtomPropertiesAction(model)
                },
            };
        lastAction = setAction(2, 0);// currentAction = ACTIONS[selectedRow][selectetCol];
    }

    Action setAction(Action a)
    {
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

    Action setAction(int row, int col)
    {
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

    private void onMousePressed(MouseEvent evt)
    {
        double x = evt.getX();
        double y = evt.getY();
        if (x >= 0 && x <= IMAGE_WIDTH && y >= 0 && y < IMAGE_HEIGHT) {
            double dy = IMAGE_HEIGHT / ROWS;
            double dx = IMAGE_WIDTH / COLS;
            int col = (int) (x / dx);
            int row = (int) (y / dy);
            Action action = setAction(row, col);
            if (action instanceof ButtonPressListener) {
                ButtonPressListener bpl = (ButtonPressListener) action;
                bpl.onButtonPressed(new Window(canvas), new Point2D.Double(x, y));
            }
            //System.out.printf("Mouse pressed on r/c %d/%d\n", row, col);
            repaint();
        }
    }

    private void repaint()
    {
        draw(canvas);
    }

    private void onMouseReleased(MouseEvent evt)
    {
        boolean repaint = false;
        if (currentAction != null) {
            if (currentAction instanceof ButtonPressListener) {
                ButtonPressListener bpl = (ButtonPressListener) currentAction;
                bpl.onButtonReleased(/*this.getScene().getWindow()*/null, new Point2D.Double(evt.getX(), evt.getY()));
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
    public Action getCurrentAction()
    {
        return currentAction;
    }

    @Override
     public void onChange()
     {
         repaint();
     }

     public void doAction(Action a)
     {
         if (a.isCommand()) {
             a.onCommand();
         } else {
             setAction(a);
         }
         repaint();
     }
}

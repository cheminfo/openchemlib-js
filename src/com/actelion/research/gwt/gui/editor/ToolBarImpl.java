/*
* Copyright (c) 1997 - 2015
* Actelion Pharmaceuticals Ltd.
* Gewerbestrasse 16
* CH-4123 Allschwil, Switzerland
*
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice, this
*    list of conditions and the following disclaimer.
* 2. Redistributions in binary form must reproduce the above copyright notice,
*    this list of conditions and the following disclaimer in the documentation
*    and/or other materials provided with the distribution.
* 3. Neither the name of the the copyright holder nor the
*    names of its contributors may be used to endorse or promote products
*    derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*/





package com.actelion.research.gwt.gui.editor;

import com.actelion.research.gwt.gui.editor.actions.ESRTypeAction;
import com.actelion.research.gwt.gui.viewer.GraphicsContext;
import com.actelion.research.gwt.gui.viewer.Log;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.actions.*;
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
import com.google.gwt.core.client.GWT;
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
    private static final String PREFIX = "data:image/gif;base64,";

    private static final String drawButtonUp64 =
           PREFIX +
            "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";

    private static final String drawButtonDown64 =
            PREFIX +
                    "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";

    private static final String esrButtonUp64 =
            PREFIX +
                    "R0lGODlhHABGANUAAISChPLx8ubl5uDf4NLR0s/OzwAA//Hx8tHR0svLzMXFxuvs7OXm5t/g4MvMzAD/APf49+vs6+Xm5dfY18vMy/j49/Ly8enp6ODg38bGxdbTzv8AAPj39+zr68bFxcC/v/////39/fr6+vf39/T09PHx8e7u7uzs7Obm5uPj4+Dg4N3d3dra2tfX19TU1NHR0czMzMvLy8nJycbGxsPDw8DAwL29vbq6uqqqqiEhIQAAAAAAAAAAAAAAAAAAAAAAACwAAAAAHABGAAAG/0CQcEgsGo0AkGbJbDqfzqQGQK1ar1iqUgrIeb/gsFi33eZAp7R6zV6Tp+bTzfahzWSUAsLVYq02KQIXEThvXDknNjU0GTIxBS98fhuUFwsmhWVTiIsKjpCSf5SUJhaZcJsnNJ4OeqEqo6Mkp4cnHp+RE36wsaO0cTIwrn0rDb2jIr+pCQUEoQPHlCIhyl0nw34DKccc0yDViLl+GNu9I97fhnGv5bHdIUPgJ8Qq5To6GyTn8PHqqbspJFC6pw9dP03Wit27t2GhDngO0yFEVI9BQx0H7omIeBCVtRQoHJK4F+IeiIVC5Am4Z+IeBJMmhZiUd8Ghjo0oOco7YeIACf8IBo9I9IgIh9GjSJMi9ddFjFOnTM+0mdomqhw6dvBgW2Eg0CB5ihjhCmWgrCVMVjuNJVa2bSl5q2S02kOvbdtZVm89ErdChV278oJtbfC3bTKrzJwRG1C47DR5W7UVfje0Fl9yhfeltMpuskF59FL8Lchv88QTACXYJV1EXrFAq1GenGm1YlufJGGSqRwH5IWyAfTpnm2a6AlBHUyU+ImTeEyaPJdX8Cb7uVWePoGWFipPqXelUZ+K/xIVDdXzJ6zOqXMnD10/D7wSshq20d5QD/Kflaf2PrH8AL5lVVxzvQIggHidphcouvR14IGBCfNeMQ8CeNhpiT1TYX6PWRVvWQoVUibPZSA+qBlvqbAT4mdWhfbgSLKhaE1qB8JY0m4yIvKaADUCFWOOJ1QEoE/n/Aikb/kFVyQZNgGJnHLM3SiTdaddEB0J0/FjE22nYRelUB3V8t2YRjG15Zlo+gPmmkWgAsWbcMKRxZx0VhEEADs=";
    private static final String esrButtonDown64 =
            PREFIX +
                    "R0lGODlhHABGANUAAISChH59fnt6e3h3eHRzdMTDxLi3uJiXmAAAw3d3eLe3uKamp5eXmI+PkIaGh4ODhH1+fsPExLe4uJGSkgDDAM/Qz8DBwHh4d76+vZ6enZiYl4qKidbTzsMAANDPz8fGxqSjo5KRkYqJiYSDg////9LS0svLy76+vru7u7i4uLW1tbKysq+vr6ysrKmpqaOjo6GhoZubm5iYmJWVlZKSko+Pj4yMjImJiYSEhIODg4CAgHh4eG9vb19fXyEhIQAAACwAAAAAHABGAAAG/0CScEgsGo0AEmfJbDqfziQHQK1ar1iqUgrweb/gsPi33YrPaF956qXx3vC4PL7u+t4EQkIQ0AEcNzY1NB0HMRkwPXVePHk7fDojgIKEHR2IL4pTZncEAwIQOjkOIpSWpyALmlyMF5A4k4Mzp6cuq5w8kA+xNLO0p7dsd6GjgbK/pyzBdjyRvL7ILCvLjLvGEzMayC3SKtR3DhuCITMy290p3zzGvQznKyoKKOqU2ZY/Px0u3PAGKCfqGvSSEePej33d/J2woK4XPnwdHv6AJ7GAunIF8S3Ax0IiBgsFPqjTEEOiC3wr8KF4+MGEupI/XuBrgU8FvoUR8LlcdCeDxP8fHR9ikGjCgzoYLxYghCcBxceQJkxUKKGuh9WrWLNi5Zmmq5pNwtzMGSuHJx49kP6wQ2AIkTpHkCStRYAA09tHxEhRoss3lTpXfWCxm8GXry2zup4VLqwu7+DFfJWZdfYYMgJp6qwJwqYBMj9vZsONK+cZnbrBDErDk0dPVufCS1UoDDgwBuzPChmarQ37oYoUDy2axchXKUqV+EKOPERXKc0fNn+cwNfyJSbnQQ3gs6BzJ1hmiFLFfghSp1GzSLEzdQqypVSqZrXK18rVKxqeYsnqN9sIbR+1lFDQViJmwdWHXAFSQIFdBX6SVymDKCihX2YBpoNg9Ugo4WHfMZKO2GAaatiYKM+EKKFkHd5BWYYmUoCZWZrVwJmJn6kjWg3kyECjaWahtuNq85hVjwYhnvTQbGYJhJGGRqb0wz+6pchDbUxy8xB7wkm5pILYXfmUSGaRlAGXCOEDHFHWpbeUStN1p0546v32Ez7nSanmZ019GdVUy/xwx3yAWrXGEYQWChYUiCa6SRaMNlpFEAA7";

    static String TOOLBARID = "toolbar";
//    static final Image BUTTON_UP = new Image("images/drawButtonsUp.gif");
//    static final Image BUTTON_DOWN = new Image("images/drawButtonsDown.gif");
//    static final Image ESR_BUTTON_UP = new Image("images/ESRButtonsUp.gif");
//    static final Image ESR_BUTTON_DOWN = new Image("images/ESRButtonsDown.gif");
    static final Image BUTTON_UP = new Image(drawButtonUp64);
    static final Image BUTTON_DOWN = new Image(drawButtonDown64);
    static final Image ESR_BUTTON_UP = new Image(esrButtonUp64);
    static final Image ESR_BUTTON_DOWN = new Image(esrButtonDown64);


    public static final int ESR_BUTTON_ROW = 3;
    public static final int ESR_BORDER = 3;


    private Action[][] ACTIONS = null;
    private Model model = null;
    Canvas canvas = null;
    private int selectedRow;
    private int selectetCol;
    private Action currentAction = null;
    private Action lastAction = null;



    ToolBarImpl(Model model)
    {
        this.model = model;
    }
  private boolean loaded = false;
    public Element create(Element parent, int width, int height)
    {

        Log.console("BASE URL => '" + GWT.getModuleBaseURL());
        BUTTON_UP.addLoadHandler(new LoadHandler()
        {
            @Override
            public void onLoad(LoadEvent event)
            {
                Log.console("Loaded UP");
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
                Log.console("Loaded down");
                if (loaded)
                    requestLayout();
                loaded = true;
            }
        });

        DivElement toolbarHolder = Document.get().createDivElement();
        toolbarHolder.setId(TOOLBARID);
        toolbarHolder.setAttribute(
            "style", "position:relative;float:left;width:" + width + "px;height:" + height + "px;");
//        img.setUrl(GWT.getModuleBaseURL() + "/images/drawButtonsUp.gif");
        parent.appendChild(toolbarHolder);
        canvas = Canvas.createIfSupported();
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
        canvas.setWidth(width + "px");
        canvas.setHeight(height + "px");
        RootPanel.get(TOOLBARID).add(canvas);

        //  This is to force addLoadHandler
        BUTTON_UP.setVisible(false);
        RootPanel.get(TOOLBARID).add(BUTTON_UP);
        BUTTON_DOWN.setVisible(false);
        RootPanel.get(TOOLBARID).add(BUTTON_DOWN);

        setupHandlers();
        setupMouseHandlers();
        model.addChangeListener(this);

        return toolbarHolder;
    }

    private void requestLayout()
    {
        draw(canvas);
    }

//    public Element create(Element parent, int width, int height)
//    {
//        DivElement toolbarHolder = Document.get().createDivElement();
//        toolbarHolder.setId(TOOLBARID);
//        toolbarHolder.setAttribute(
//            "style", "position:relative;float:left;width:" + width + "px;height:" + height + "px;");
////        img.setUrl(GWT.getModuleBaseURL() + "/images/drawButtonsUp.gif");
//        parent.appendChild(toolbarHolder);
//        canvas = Canvas.createIfSupported();
//        canvas.setCoordinateSpaceWidth(width);
//        canvas.setCoordinateSpaceHeight(height);
//        canvas.setWidth(width + "px");
//        canvas.setHeight(height + "px");
//        RootPanel.get(TOOLBARID).add(canvas);
//        setupHandlers();
//        setupMouseHandlers();
//        draw(canvas);
//        return toolbarHolder;
//    }

    private void draw(Canvas toolBar)
    {
        Context2d context2d = toolBar.getContext2d();
//        context2d.drawImage(ImageElement.as(BUTTON_UP.getElement()), 0, 0);
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
//        Log.println("drawESRButtons " + ESR_BUTTON_UP);

        double ESRdx = ESR_IMAGE_WIDTH - 2 * ESR_BORDER;
        double ESRdy = (ESR_IMAGE_HEIGHT - 2 * ESR_BORDER) / ESR_IMAGE_ROWS;
        int row = Model.rowFromESRType(model.getESRType());
        int ESRy = (int) ((ESR_IMAGE_HEIGHT - 2 * ESR_BORDER) / ESR_IMAGE_ROWS * row + ESR_BORDER);
        int ESRx = ESR_BORDER;

        if (currentAction instanceof ESRTypeAction) {
//            Log.println("currentAction is ESType Action");
            ctx.drawImage(ESR_BUTTON_DOWN, ESRx, ESRy, ESRdx, ESRdy, ESRdx, ESRdy * ESR_BUTTON_ROW, ESRdx, ESRdy);
        } else {
//            Log.println("currentAction is NOT ESType Action");
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

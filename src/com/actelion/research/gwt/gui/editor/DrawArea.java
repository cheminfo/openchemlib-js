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

import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.gwt.gui.viewer.GWTDepictor;
import com.actelion.research.gwt.gui.viewer.GraphicsContext;
import com.actelion.research.gwt.gui.viewer.Log;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.listeners.IChangeListener;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

class DrawArea implements IChangeListener
{
    public static final CssColor WHITE = CssColor.make("WHITE");
    static String DRAWAREAID = "drawarea";

    private com.actelion.research.share.gui.editor.actions.Action action;
    private Model model;
    //    CanvasElement canvasElement = null;
    Canvas canvas;
    FocusPanel panel;

    DrawArea(Model m)
    {
        model = m;
        model.addChangeListener(this);
    }

    public Element create(Element parent, int width, int height)
    {
        DivElement toolbarHolder = Document.get().createDivElement();
        toolbarHolder.setId(DRAWAREAID);
        toolbarHolder.setAttribute("style",
            "position:relative;float:right;width:" + width + "px;height:" + height + "px;");


//        canvasElement = Document.get().createCanvasElement();
//        canvasElement.setAttribute("style",
//            "position:relative;float:right;width:" + width + "px;height:" + height + "px;");
        parent.appendChild(toolbarHolder);
        canvas = Canvas.createIfSupported();
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
        canvas.setWidth(width + "px");
        canvas.setHeight(height + "px");
        model.setDisplaySize(new Dimension(width, height));

        System.out.println("Added focus panel");
        panel = new FocusPanel();
        panel.add(canvas);
        RootPanel.get(DRAWAREAID).add(panel);
//        setupHandlers();
//        setupMouseHandlers();
//        draw(canvas);

        return toolbarHolder;
    }

    public void draw(com.actelion.research.share.gui.editor.actions.Action a)
    {
        action = a;
        draw(canvas);
//        a.paint(new GraphicsContext(canvas.getContext2d()));
    }

    public void draw()
    {
        draw(canvas);
    }

    private void draw(Canvas canvas)
    {
//        System.out.println("Drawing canvas");
        Context2d context2d = canvas.getContext2d();
        AbstractDepictor depictor;
        int w = (int) model.getDisplaySize().getWidth();
        int h = (int) model.getDisplaySize().getHeight();
        //if (w > 0 && h > 0)
        {
            //System.out.printf("Drawing MolArea %f %f\n", w, h);
            int displayMode = 0;
//            if (isReaction()) {
//                depictor = new JFXExtendedDepictor(model.getReaction(), model.getDrawinObjects(), displayMode);
//                depictor.setDisplayMode(model.getDisplayMode());
//            } else
            {
                depictor = new GWTDepictor(context2d, model.getMolecule());
                depictor.setDisplayMode(model.getDisplayMode());
            }
            //depictor.setFragmentNoColor(isMultiFragment() ? Color.RED : null);

            if (model.needsLayout()) {
                System.out.println("Need layout " + w + " " + h);
                depictor.updateCoords(null,
                    new java.awt.geom.Rectangle2D.Float(0, 0, (float) w, (float) h), AbstractDepictor.cModeInflateToMaxAVBL);
            }
            model.needsLayout(false);
            context2d.clearRect(0, 0, w, h);
            context2d.setFillStyle(WHITE);
            context2d.fillRect(0, 0, w, h);
            long start = new Date().getTime();
            depictor.paint(context2d);
            long end = new Date().getTime();
//            Log.println("MD used " + (end - start) + " msec");

            // This is needed due
            if (action != null) {
                //   Log.println("Drawing action " + action);
                action.paint(new GraphicsContext(context2d));
            }
        }
    }

    @Override
    public void onChange()
    {
//        System.out.println("DrawArea on change...");
        draw(canvas);
    }

    public void requestLayout()
    {
        draw(canvas);
    }


    public void setOnMouseClicked(ClickHandler handler, DoubleClickHandler dbl)
    {
        canvas.addClickHandler(handler);
        canvas.addDoubleClickHandler(dbl);
        canvas.addDomHandler(new ContextHandler(), ContextMenuEvent.getType());
    }

    public void setOnMouseDragged(MouseMoveHandler handler)
    {
        canvas.addMouseMoveHandler(handler);
    }

    public void setOnMouseMoved(MouseMoveHandler handler)
    {
        canvas.addMouseMoveHandler(handler);
    }

    public void setOnMouseOut(MouseOutHandler h)
    {
        canvas.addMouseOutHandler(h);
    }

    public void setOnMousePressed(MouseDownHandler h)
    {
        canvas.addMouseDownHandler(h);
    }

    public void setOnMouseReleased(MouseUpHandler handler)
    {
        canvas.addMouseUpHandler(handler);
    }

    private boolean down = false;
    private boolean pressed = false;
    private int code = 0;

    public void setOnKeyPressed(final ACTKeyEventHandler handler)
    {
        canvas.addKeyDownHandler(new KeyDownHandler()
        {
            @Override
            public void onKeyDown(KeyDownEvent event)
            {
                down = true;
                code = event.getNativeKeyCode();
                Log.console("Key Down " + code);
            }
        });

        canvas.addKeyUpHandler(new KeyUpHandler()
        {
            @Override
            public void onKeyUp(KeyUpEvent event)
            {
//                System.out.println("Key UP " + code);
                code = event.getNativeKeyCode();
                Log.console("Key Up " + code);
                if ((code >= KeyCodes.KEY_A && code <= KeyCodes.KEY_Z)
                    || (code >= KeyCodes.KEY_ZERO && code <= KeyCodes.KEY_NINE)
                    || (code == KeyCodes.KEY_DELETE)
                    ) {
                    handler.onKey(new ACTKeyEvent(
                        code,
                        event.isShiftKeyDown(),
                        pressed ? (ACTKeyEvent.LETTER | ACTKeyEvent.DIGIT) : 0));
                }
                down = pressed = false;
            }
        });

        canvas.addKeyPressHandler(new KeyPressHandler()
        {
            @Override
            public void onKeyPress(KeyPressEvent event)
            {
                pressed = true;
                code = event.getCharCode();
                Log.console("Key Press " + code);
            }
        });
    }


    public void requestFocus()
    {
        canvas.setFocus(true);
    }

    public GraphicsContext getDrawContext()
    {
        return new GraphicsContext(canvas.getContext2d());
    }

    public void setCursor(Style.Cursor c)
    {
        canvas.getCanvasElement().getStyle().setCursor(c);
    }
}


class ContextHandler implements ContextMenuHandler
{
    class MenuItem
    {
        String name;

        MenuItem(String n)
        {
            name = n;
        }

        @Override
        public String toString()
        {
            return name;
        }
    }

    class MenuCell extends AbstractCell<MenuItem>
    {

        @Override
        public void render(com.google.gwt.cell.client.Cell.Context context,
                           MenuItem value, SafeHtmlBuilder sb)
        {
            SafeHtml html = SafeHtmlUtils
                .fromSafeConstant("<div class='cell'> " + value
                    + "</div>");
            sb.append(html);
        }

    }

    private SingleSelectionModel<MenuItem> selectionModel = new SingleSelectionModel<MenuItem>();
    private MenuCell contextMenuItem = new MenuCell();
    private CellList<MenuItem> contextMenuItems = new CellList<MenuItem>(contextMenuItem);
    private MenuItem copy = new MenuItem("Copy");
    private MenuItem paste = new MenuItem("Paste");
    final PopupPanel contextMenu = new PopupPanel(true);


    ContextHandler()
    {
        ArrayList<MenuItem> items = new ArrayList<MenuItem>();
        items.add(copy);
        items.add(paste);

        contextMenuItems.setSelectionModel(selectionModel);
        contextMenuItems.setRowCount(items.size(), true);
        contextMenuItems.setRowData(0, items);
        VerticalPanel p = new VerticalPanel();
        p.add(contextMenuItems);

        contextMenu.setStyleName("contextMenu");
        contextMenu.add(p);

    }

    @Override
    public void onContextMenu(ContextMenuEvent event)
    {
//        contextMenu.hide();
        event.preventDefault();
        event.stopPropagation();
/*
        contextMenu.setPopupPosition(event.getNativeEvent().getClientX(), event.getNativeEvent().getClientY());
        contextMenu.show();

        selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler()
        {

            @Override
            public void onSelectionChange(SelectionChangeEvent event)
            {
                if (selectionModel.getSelectedObject() == copy) {
                    copy("test copy");
                }
                // changed the context menu selection
                Log.println("Selected item is" + selectionModel.getSelectedObject());
                contextMenu.hide();

            }
        });
*/
    }

//    public static native void copy(String text)
//    /*-{
//        console.log(text);
//        var copyEvent = new ClipboardEvent('copy', { dataType: 'text/plain', data: 'Data to be copied' } );
//
//        $doc.dispatchEvent(copyEvent);
//    }-*/;
}
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

import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.chem.IDCodeParser;
import com.actelion.research.chem.MolfileV3Creator;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.reaction.Reaction;
import com.actelion.research.gwt.gui.viewer.Console;
import com.actelion.research.gwt.gui.viewer.GraphicsContext;
import com.actelion.research.gwt.gui.viewer.Log;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.chem.AbstractExtendedDepictor;
import com.actelion.research.share.gui.editor.geom.GeomFactory;
import com.actelion.research.share.gui.editor.listeners.IChangeListener;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.dom.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SingleSelectionModel;

import java.awt.*;
import java.util.ArrayList;

class DrawArea implements IChangeListener {
    private boolean down = false;
    private boolean pressed = false;
    private int code = 0;
    protected final GeomFactory builder;

    public static final CssColor WHITE = CssColor.make("WHITE");
    private static int instanceCount = 0;

    private com.actelion.research.share.gui.editor.actions.Action action;
    private Model model;
    Canvas canvas;
    private FocusPanel panel;
    private RootPanel container;
    private boolean focus = false;

    DrawArea(Model m) {
        model = m;
        builder = model.getGeomFactory();
        model.addChangeListener(this);

        instanceCount++;

    }

    private void setupDropHandler() {
        canvas.addDomHandler(new DragOverHandler() {
            @Override
            public void onDragOver(DragOverEvent event) {
                canvas.addStyleName("dropping");
            }
        }, DragOverEvent.getType());

        canvas.addDomHandler(new DragLeaveHandler() {
            @Override
            public void onDragLeave(DragLeaveEvent event) {
                canvas.removeStyleName("dropping");
            }
        }, DragLeaveEvent.getType());

        canvas.addDomHandler(new DropHandler() {
            @Override
            public void onDrop(DropEvent event) {
                try {
                    String idcode = event.getData("text");
                    StereoMolecule mol = new StereoMolecule();
                    IDCodeParser parser = new IDCodeParser();
                    parser.parse(mol, idcode);
                    model.setNewMolecule();
                    // todo maybe change to something like:
                    // model.addMolecule(mol, event.getNativeEvent().getClientX(),
                    // event.getNativeEvent().getClientY());
                    model.addMolecule(mol, 0, 0);
                    event.preventDefault();
                } catch (Exception e) {
                    Log.console("Cannot drop molecule: " + e);
                }
                // Do something with dropLabel and dragging
            }
        }, DropEvent.getType());
    }

    public static native void copy(String text)
    /*-{
        var textArea = document.createElement("textarea");
        textArea.value = text;
        textArea.id="copyItem";
        document.body.appendChild(textArea);
        textArea.select();
        document.execCommand("Copy");
        textArea.remove();
    }-*/;

    public Element createElement(Element parent, int left, int top, int width, int height) {
        String drawAreaID = "drawarea" + instanceCount;
        DivElement drawAreaContainer = Document.get().createDivElement();
        drawAreaContainer.setId(drawAreaID);
        drawAreaContainer.setAttribute("style",
                "position:absolute;" + "left:" + left + "px; " + "width:" + width + "px;" + "height:" + height + "px;");

        parent.appendChild(drawAreaContainer);
        canvas = Canvas.createIfSupported();
        canvas.getCanvasElement().setAttribute("style", "outline: none;");
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
        setDrawSize(width, height);
        panel = new FocusPanel();
        panel.add(canvas);
        container = RootPanel.get(drawAreaID);
        container.add(panel);

        setupDropHandler();

        return drawAreaContainer;
    }

    public void draw(com.actelion.research.share.gui.editor.actions.Action a) {
        action = a;
        draw(canvas);
    }

    public void draw() {
        draw(canvas);
    }

    private void draw(Canvas canvas) {
        long fg = builder.getForegroundColor();
        Context2d context2d = canvas.getContext2d();
        int w = (int) model.getDisplaySize().getWidth();
        int h = (int) model.getDisplaySize().getHeight();

        int displayMode = model.getDisplayMode();

        drawBackground(context2d, w, h);

        if (isReaction()) {
            model.analyzeFragmentMembership();
            if (model.needsLayout())
                model.cleanReaction(true);
        }

        AbstractExtendedDepictor depictor = createDepictor();
        depictor.setDisplayMode(displayMode);

        if (model.needsLayout()) {
            depictor.updateCoords(null, new java.awt.geom.Rectangle2D.Double(0, 0, (float) w, (float) h),
                    AbstractDepictor.cModeInflateToMaxAVBL);
        }
        model.needsLayout(false);

        depictor.paint(context2d);
        // Let the actions draw if needed e.g. NewChainAction
        if (action != null) {
            GraphicsContext ctx = new GraphicsContext(context2d);
            ctx.save();
            ctx.setStroke(fg);
            ctx.setFill(fg);
            action.paint(ctx);
            ctx.restore();
        }
    }

    private void drawBackground(Context2d context2d, int w, int h) {
        context2d.clearRect(0, 0, w, h);
        context2d.setFillStyle(WHITE);
        context2d.fillRect(0, 0, w, h);
    }

    @Override
    public void onChange() {
        draw(canvas);
    }

    public void requestLayout() {
        draw(canvas);
    }

    public void setOnMouseClicked(ClickHandler handler, DoubleClickHandler dbl) {
        canvas.addClickHandler(handler);
        canvas.addDoubleClickHandler(dbl);
    }

    public void setOnMouseDragged(MouseMoveHandler handler) {
        canvas.addMouseMoveHandler(handler);
    }

    public void setOnMouseMoved(MouseMoveHandler handler) {
        canvas.addMouseMoveHandler(handler);
    }

    public void setOnMouseOut(MouseOutHandler h) {
        canvas.addMouseOutHandler(h);
    }

    public void setOnMousePressed(MouseDownHandler h) {
        canvas.addMouseDownHandler(h);
    }

    public void setOnMouseReleased(MouseUpHandler handler) {
        canvas.addMouseUpHandler(handler);
    }

    protected boolean isMarkush() {
        int mode = model.getMode();
        return (mode & Model.MODE_MARKUSH_STRUCTURE) != 0;

    }

    private boolean isReaction() {
        int mode = model.getMode();
        return (mode & Model.MODE_REACTION) != 0;
    }

    private AbstractExtendedDepictor createDepictor() {
        AbstractExtendedDepictor mDepictor;
        Context2d ctx = canvas.getContext2d();
        if (isReaction())
            mDepictor = new MoleculeDrawDepictor(ctx, new Reaction(model.getFragments(), model.getReactantCount()),
                    model.getDrawingObjects(), false, builder.getDrawConfig());
        else if (isMarkush()) {
            mDepictor = new MoleculeDrawDepictor(ctx, model.getFragments(), model.getMarkushCount(), null,
                    builder.getDrawConfig());
        } else {
            mDepictor = new MoleculeDrawDepictor(ctx, model.getMolecule(), model.getDrawingObjects(),
                    builder.getDrawConfig());
        }
        return mDepictor;
    }

    private boolean isValidKey(int kc) {
        return ((kc >= KeyCodes.KEY_A && kc <= KeyCodes.KEY_Z) || (kc >= KeyCodes.KEY_ZERO && kc <= KeyCodes.KEY_NINE)
                || (kc == KeyCodes.KEY_DELETE) || (kc == KeyCodes.KEY_ENTER) || (kc == KeyCodes.KEY_BACKSPACE));

    }

    private boolean copyMolecule() {
        StereoMolecule molecule = model.getMolecule();
        MolfileV3Creator c = new MolfileV3Creator(molecule);
        copy(c.getMolfile());
        return true;
    }

    boolean meta = false;

    public void setOnKeyPressed(final ACTKeyEventHandler handler) {
        canvas.addKeyDownHandler(new KeyDownHandler() {
            @Override
            public void onKeyDown(KeyDownEvent event) {
                down = true;
                meta = event.isMetaKeyDown() || event.isControlKeyDown();
                code = event.getNativeKeyCode();
                if (!meta && isValidKey(code)) {
                    event.preventDefault();
                } else if (meta) {
                    if (code == KeyCodes.KEY_C) {
                        copyMolecule();
                        event.preventDefault();
                    }
                }
            }
        });
        canvas.addKeyUpHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                code = event.getNativeKeyCode();
                if (!meta && isValidKey(code)) {
                    event.preventDefault();
                    handler.onKey(new ACTKeyEvent(code, event, // .isShiftKeyDown(),
                            pressed ? (ACTKeyEvent.LETTER | ACTKeyEvent.DIGIT) : 0));
                }
                down = pressed = false;
            }
        });

        canvas.addKeyPressHandler(new KeyPressHandler() {
            @Override
            public void onKeyPress(KeyPressEvent event) {
                pressed = true;
                code = event.getCharCode();
                if (isValidKey(code)) {
                    event.preventDefault();
                }
            }
        });
    }

    public void requestFocus() {
        canvas.setFocus(true);
    }

    public GraphicsContext getDrawContext() {
        return new GraphicsContext(canvas.getContext2d());
    }

    public void setCursor(Style.Cursor c) {
        canvas.getCanvasElement().getStyle().setCursor(c);
    }

    public void setSize(int width, int height) {
        container.setWidth(width + "px");
        container.setHeight(height + "px");
        panel.setWidth(width + "px");
        panel.setHeight(height + "px");
        setDrawSize(width, height);
        model.needsLayout(true);
        draw();
    }

    private void setDrawSize(int width, int height) {
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
        canvas.setWidth(width + "px");
        canvas.setHeight(height + "px");
        model.setDisplaySize(new Dimension(width, height));
    }

    public boolean hasFocus() {
        return focus;
    }
}

class ContextHandler implements ContextMenuHandler {
    class MenuItem {
        String name;

        MenuItem(String n) {
            name = n;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    class MenuCell extends AbstractCell<MenuItem> {

        @Override
        public void render(com.google.gwt.cell.client.Cell.Context context, MenuItem value, SafeHtmlBuilder sb) {
            SafeHtml html = SafeHtmlUtils.fromSafeConstant("<div class='cell'> " + value + "</div>");
            sb.append(html);
        }

    }

    private SingleSelectionModel<MenuItem> selectionModel = new SingleSelectionModel<MenuItem>();
    private MenuCell contextMenuItem = new MenuCell();
    private CellList<MenuItem> contextMenuItems = new CellList<MenuItem>(contextMenuItem);
    private MenuItem copy = new MenuItem("Copy");
    private MenuItem paste = new MenuItem("Paste");
    final PopupPanel contextMenu = new PopupPanel(true);

    ContextHandler() {
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
    public void onContextMenu(ContextMenuEvent event) {
        event.preventDefault();
        event.stopPropagation();
    }

}
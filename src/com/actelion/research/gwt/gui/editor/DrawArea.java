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

import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.chem.IDCodeParser;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.reaction.Reaction;
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

class DrawArea implements IChangeListener
{
    private boolean down = false;
    private boolean pressed = false;
    private int code = 0;
//    protected static final GeomFactory builder = GeomFactory.getGeomFactory();
    protected final GeomFactory builder;

    public static final CssColor WHITE = CssColor.make("WHITE");
    private static int instanceCount = 0;

    private com.actelion.research.share.gui.editor.actions.Action action;
    private Model model;
    Canvas canvas;
    private FocusPanel panel;
    private RootPanel container;

    DrawArea(Model m)
    {
        model = m;
        builder = model.getGeomFactory();
        model.addChangeListener(this);

        instanceCount++;



    }

    private void setupDropHandler() {
        Log.console("Init d&d");
        canvas.addDomHandler(new DragOverHandler()
        {
            @Override
            public void onDragOver(DragOverEvent event)
            {
                canvas.addStyleName("dropping");
            }
        }, DragOverEvent.getType());

        canvas.addDomHandler(new DragLeaveHandler()
        {
            @Override
            public void onDragLeave(DragLeaveEvent event)
            {
                canvas.removeStyleName("dropping");
            }
        }, DragLeaveEvent.getType());

        canvas.addDomHandler(new DropHandler()
        {
            @Override
            public void onDrop(DropEvent event)
            {
                try {
                    String idcode = event.getData("text");
                    Log.console("onDrop " + idcode);
                    StereoMolecule mol = new StereoMolecule();
                    IDCodeParser parser = new IDCodeParser();
                    parser.parse(mol,idcode);
                    model.setNewMolecule();
                    model.addMolecule(mol);
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
        console.log(addPasteHandler + text);
        var copyEvent = new ClipboardEvent('copy', { dataType: 'text/plain', data: 'Data to be copied' } );

        $doc.dispatchEvent(copyEvent);
    }-*/;

    public Element createElement(Element parent, int left, int top, int width, int height)
    {
        String drawAreaID = "drawarea" + instanceCount;
        DivElement drawAreaContainer = Document.get().createDivElement();
        drawAreaContainer.setId(drawAreaID);
        drawAreaContainer.setAttribute("style",
                "position:absolute;" +
                        "left:" + left + "px; " +
                        "width:" + width + "px;" +
                        "height:" + height + "px;");
//        "position:relative;float:right;width:" + width + "px;height:" + height + "px;");

        parent.appendChild(drawAreaContainer);
        canvas = Canvas.createIfSupported();
        canvas.getCanvasElement().setAttribute("style", "outline: none;");

        setDrawSize(width, height);
        panel = new FocusPanel();
        panel.add(canvas);
        container = RootPanel.get(drawAreaID);
        container.add(panel);

        setupDropHandler();

        return drawAreaContainer;
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

//        AbstractDepictor depictor;
//        depictor = new GWTDepictor(context2d, model.getMolecule());
        AbstractExtendedDepictor depictor = createDepictor();
        depictor.setDisplayMode(displayMode);

        if (model.needsLayout()) {
            depictor.updateCoords(null,
                    new java.awt.geom.Rectangle2D.Double(0, 0, (float) w, (float) h), AbstractDepictor.cModeInflateToMaxAVBL);
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
    public void onChange()
    {
//        Log.console("onChange");
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

//        Removed for LPatiny
//        canvas.addDomHandler(new ContextHandler(), ContextMenuEvent.getType());
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

    protected boolean isMarkush()
    {
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
            mDepictor = new MoleculeDrawDepictor(ctx,new Reaction(model.getFragments(), model.getReactantCount()), model.getDrawingObjects(), false,builder.getDrawConfig());
        else if (isMarkush()) {
            mDepictor = new MoleculeDrawDepictor(ctx,model.getFragments(), model.getMarkushCount(), null,builder.getDrawConfig());
        } else {
            mDepictor = new MoleculeDrawDepictor(ctx,model.getMolecule(), model.getDrawingObjects(),builder.getDrawConfig());
        }
        return mDepictor;
    }

    private boolean isValidKey(int kc)
    {
        return  (  (kc >= KeyCodes.KEY_A && kc <= KeyCodes.KEY_Z)
                || (kc >= KeyCodes.KEY_ZERO && kc <= KeyCodes.KEY_NINE)
                || (kc == KeyCodes.KEY_DELETE)
                || (kc == KeyCodes.KEY_ENTER)
                || (kc == KeyCodes.KEY_BACKSPACE)
                ) ;

    }

    public void setOnKeyPressed(final ACTKeyEventHandler handler)
    {

        canvas.addKeyDownHandler(new KeyDownHandler()
        {
            @Override
            public void onKeyDown(KeyDownEvent event)
            {
                down = true;
                code = event.getNativeKeyCode();
                if (isValidKey(code)) {
                    event.preventDefault();
                }
            }
        });

        canvas.addKeyUpHandler(new KeyUpHandler()
        {
            @Override
            public void onKeyUp(KeyUpEvent event)
            {
                code = event.getNativeKeyCode();
                if (isValidKey(code)){
                    event.preventDefault();
                    handler.onKey(new ACTKeyEvent(
                            code,
                            event,//.isShiftKeyDown(),
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
                if (isValidKey(code)) {
                    event.preventDefault();
                }
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

    public void setSize(int width, int height)
    {
//        Log.console("Setting panel size to " + width);
        container.setWidth(width + "px");
        container.setHeight(height + "px");
        panel.setWidth(width + "px");
        panel.setHeight(height + "px");
        setDrawSize(width, height);
        model.needsLayout(true);
        draw();
    }

    private void setDrawSize(int width, int height)
    {
//        Log.console("Setting canvas size to " + width);
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
        canvas.setWidth(width + "px");
        canvas.setHeight(height + "px");
        model.setDisplaySize(new Dimension(width, height));
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
                if (selectionModel.getSelectedObject() == addPasteHandler) {
                    addPasteHandler("test addPasteHandler");
                }
                // changed the context menu selection
                contextMenu.hide();

            }
        });
*/
    }

//    public static native void addPasteHandler(String text)
//    /*-{
//        console.log(text);
//        var copyEvent = new ClipboardEvent('addPasteHandler', { dataType: 'text/plain', data: 'Data to be copied' } );
//
//        $doc.dispatchEvent(copyEvent);
//    }-*/;

/*
function CLIPBOARD_CLASS(canvas_id, autoresize) {
    var _self = this;
    var canvas = document.getElementById(canvas_id);
    var ctx = document.getElementById(canvas_id).getContext("2d");
    var ctrl_pressed = false;
    var reading_dom = false;
    var text_top = 15;
    var pasteCatcher;
    var paste_mode;

    //handlers
    document.addEventListener('keydown', function (e) {
        _self.on_keyboard_action(e);
    }, false); //firefox fix
    document.addEventListener('keyup', function (e) {
        _self.on_keyboardup_action(e);
    }, false); //firefox fix
    document.addEventListener('paste', function (e) {
        _self.paste_auto(e);
    }, false); //official paste handler

    //constructor - prepare
    this.init = function () {
        //if using auto
        if (window.Clipboard)
            return true;

        pasteCatcher = document.createElement("div");
        pasteCatcher.setAttribute("id", "paste_ff");
        pasteCatcher.setAttribute("contenteditable", "");
        pasteCatcher.style.cssText = 'opacity:0;position:fixed;top:0px;left:0px;';
        pasteCatcher.style.marginLeft = "-20px";
        pasteCatcher.style.width = "10px";
        document.body.appendChild(pasteCatcher);
        document.getElementById('paste_ff').addEventListener('DOMSubtreeModified', function () {
            if (paste_mode == 'auto' || ctrl_pressed == false)
                return true;
            //if paste handle failed - capture pasted object manually
            if (pasteCatcher.children.length == 1) {
                if (pasteCatcher.firstElementChild.src != undefined) {
                    //image
                    _self.paste_createImage(pasteCatcher.firstElementChild.src);
                }
            }
            //register cleanup after some time.
            setTimeout(function () {
                pasteCatcher.innerHTML = '';
            }, 20);
        }, false);
    }();
    //default paste action
    this.paste_auto = function (e) {
        paste_mode = '';
        pasteCatcher.innerHTML = '';
        var plain_text_used = false;
        if (e.clipboardData) {
            var items = e.clipboardData.items;
            if (items) {
                paste_mode = 'auto';
                //access data directly
                for (var i = 0; i < items.length; i++) {
                    if (items[i].type.indexOf("image") !== -1) {
                        //image
                        var blob = items[i].getAsFile();
                        var URLObj = window.URL || window.webkitURL;
                        var source = URLObj.createObjectURL(blob);
                        this.paste_createImage(source);
                    }
                }
                e.preventDefault();
            }
            else {
                //wait for DOMSubtreeModified event
                //https://bugzilla.mozilla.org/show_bug.cgi?id=891247
            }
        }
    };
    //on keyboard press -
    this.on_keyboard_action = function (event) {
        k = event.keyCode;
        //ctrl
        if (k == 17 || event.metaKey || event.ctrlKey) {
            if (ctrl_pressed == false)
                ctrl_pressed = true;
        }
        //c
        if (k == 86) {
            if (document.activeElement != undefined && document.activeElement.type == 'text') {
                //let user paste into some input
                return false;
            }

            if (ctrl_pressed == true && !window.Clipboard)
                pasteCatcher.focus();
        }
    };
    //on kaybord release
    this.on_keyboardup_action = function (event) {
        k = event.keyCode;
        //ctrl
        if (k == 17 || event.metaKey || event.ctrlKey || event.key == 'Meta')
            ctrl_pressed = false;
    };
    //draw image
    this.paste_createImage = function (source) {
        var pastedImage = new Image();
        pastedImage.onload = function () {
            if(autoresize == true){
                //resize canvas
                canvas.width = pastedImage.width;
                canvas.height = pastedImage.height;
            }
            else{
                //clear canvas
                ctx.clearRect(0, 0, canvas.width, canvas.height);
            }
            ctx.drawImage(pastedImage, 0, 0);
        };
        pastedImage.src = source;
    };
}
 */
}
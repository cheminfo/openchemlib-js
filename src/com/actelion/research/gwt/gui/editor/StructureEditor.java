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

import com.actelion.research.chem.*;
import com.actelion.research.gwt.gui.viewer.GWTDepictor;
import com.actelion.research.gwt.gui.viewer.Log;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.actions.Action;
import com.actelion.research.share.gui.editor.geom.ICursor;
import com.actelion.research.share.gui.editor.io.IKeyEvent;
import com.actelion.research.share.gui.editor.listeners.IChangeListener;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLImageElement;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import jsinterop.annotations.*;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Project:
 * User: rufenec
 * Date: 7/1/2014
 * Time: 10:05 AM
 */
@JsType(namespace = "OCL")
public class StructureEditor implements IChangeListener//,Exportable
{
    static int TEXTHEIGHT = 20;
    static int TOOLBARWIDTH = 45;

    private boolean drag = false;
    private Point2D mousePoint = null;
    private Model model;
    private ToolBar<Element> toolBar;
    private DrawArea drawPane;
    private InputElement idcodeTextElement;
    private LabelElement fragmentStatus;
    private Element container = null;
    private Boolean viewOnly = false;
    private static List<StructureEditor> map = new ArrayList<StructureEditor>();

    private int scale = 1;
    static {
        initObserver();
    }

    @JsIgnore
    public StructureEditor()
    {
        this("editor");
    }

//    private native NodeList querySelector(String selectors)/*-{
//        return $doc.querySelectorAll(selectors);
//    }-*/;

    @JsIgnore
    public StructureEditor(String id)
    {
        this(id,false,1);
    }

    public StructureEditor(String id, boolean useSVG, int scale)
    {
        this.scale = scale;
        container = Document.get().getElementById(id);

        if (container != null) {
            model = new GWTEditorModel(new GWTGeomFactory(new GWTDrawConfig()),0);
            String vo = container.getAttribute("view-only");
            viewOnly = Boolean.parseBoolean(vo);

            // Todo: Implement the following options:

            String se = container.getAttribute("ignore-stereo-errors");
            boolean ignoreStereoErrors = Boolean.parseBoolean(se);

            String st = container.getAttribute("no-stereo-text");
            boolean noStereoText = Boolean.parseBoolean(st);

            String sf = container.getAttribute("show-fragment-indicator");
            final boolean showFragmentIndicator = Boolean.parseBoolean(sf);

            // End Todo

            String si = container.getAttribute("show-idcode");
            boolean showIDCode = Boolean.parseBoolean(si);

            String isfragment = container.getAttribute("is-fragment");
            boolean isFragment = Boolean.parseBoolean(isfragment);


            String style = container.getAttribute("style");
            style += ";overflow:hidden;position:relative;";
            container.setAttribute("style",style);

            final int width = container.getClientWidth();
            final int height = container.getClientHeight();
            String idcode = container.getAttribute("data-idcode");

            final int toolBarWidth = getToolbarWidth();
            if (useSVG)
                toolBar = new SVGToolBarImpl(model,scale);
            else
                toolBar = new ToolBarImpl(model);
            Element toolBarElement = toolBar.createElement(container, toolBarWidth, height - TEXTHEIGHT - 5);
            if (!viewOnly)
                container.appendChild(toolBarElement);

            drawPane = new DrawArea(model);
            Element drawAreaElement = drawPane.createElement(container, toolBarWidth,0,(width - toolBarWidth), height - TEXTHEIGHT - 5);
            container.appendChild(drawAreaElement);

            int idcodewidth = showFragmentIndicator ? width - 50 : width;
            if (showFragmentIndicator) {
                fragmentStatus = Document.get().createLabelElement();
                fragmentStatus.setId(id + "-fragment-element");
                setElementSizePos(fragmentStatus, width - 50, height - TEXTHEIGHT - 5, TEXTHEIGHT, 50);
                String t = fragmentStatus.getAttribute("style");
                fragmentStatus.setAttribute("style", t + "text-align:center;");
                fragmentStatus.setInnerText(model.isFragment() ? "Q" : "");
                container.appendChild(fragmentStatus);
            }
            if (showIDCode) {
                idcodeTextElement = Document.get().createTextInputElement();
                idcodeTextElement.setId(id + "-idcode-element");
                setElementSizePos(idcodeTextElement, 0, height - TEXTHEIGHT - 5, TEXTHEIGHT, idcodewidth);
                container.appendChild(idcodeTextElement);
            }

            int displayMode = model.getDisplayMode();
            if (ignoreStereoErrors)
                displayMode |= AbstractDepictor.cDModeNoStereoProblem;

            if (noStereoText)
                displayMode |= AbstractDepictor.cDModeSuppressChiralText;

            model.setDisplayMode(displayMode);
            model.addChangeListener(this);
            StereoMolecule mol = createMolecule(idcode, isFragment/*, (width - toolBarWidth), height - TEXTHEIGHT - 5*/);
            model.setValue(mol, true);

            setUpHandlers();
            setupMouseHandlers();

            // Respond to resizing
            com.google.gwt.user.client.Window.addResizeHandler(new ResizeHandler()
            {
                public void onResize(ResizeEvent ev)
                {
                    int w = container.getClientWidth();
                    int h = container.getClientHeight();
                    int idcodewidth = showFragmentIndicator ? w - 30 : h;
                    if (width != w || height != h) {
                        drawPane.setSize(w - toolBarWidth, h - TEXTHEIGHT - 5);
                        if (idcodeTextElement != null)
                            setElementSizePos(idcodeTextElement, 0, h - TEXTHEIGHT - 5, TEXTHEIGHT, idcodewidth);
                        if (fragmentStatus != null)
                            setElementSizePos(fragmentStatus, w-30, h - TEXTHEIGHT - 5, TEXTHEIGHT, 30);
                    }
                }
            });
        }
        addPasteHandler(this,"onMouseClicked ");
    }

    public static StructureEditor createEditor(String id)
    {
        return new StructureEditor(id);
    }

    public static StructureEditor createSVGEditor(String id, int scale)
    {
        return new StructureEditor(id,true,scale);
    }

    private native static void initObserver() /*-{
        $wnd.edit$observer = new MutationObserver(function (mutations) {
            console.log("Mutation");
            mutations.forEach(function (mutation) {
                @com.actelion.research.gwt.gui.editor.StructureEditor::notify(Ljava/lang/Object;)(mutation.target);
            });
        });
    }-*/;


    public static void notify(Object o)
    {
        Log.console("Notify from mutation");
        for (StructureEditor e : map) {
            e.drawPane.draw();
            int w = e.container.getClientWidth();
            int h = e.container.getClientHeight();
            final int toolBarWidth = e.getToolbarWidth();
            e.drawPane.setSize(w - toolBarWidth, h - TEXTHEIGHT - 5);
        }

    }

    private int getToolbarWidth()
    {
        return viewOnly ? 0 : TOOLBARWIDTH *scale;
    }

    private void setElementSizePos(Element el, int x, int y, int h, int w)
    {
        el.setAttribute("style",
                "position:absolute;" +
                        "left:" + x + "px; " +
                        "top:" + y + "px;" +
                        "width:" + w + "px;" +
                        "height:" + h + "px;");

//        idcodeTextElement.setAttribute("style", "position:relative;float:left;width:" + (w - 5) + "px;height:" + TEXTHEIGHT + "px;");
    }



    public String getIDCode()
    {
        return model.getIDCode();
    }

    public void setIDCode(String idCode)
    {
        try {
            StereoMolecule mol = new StereoMolecule();
            IDCodeParser p = new IDCodeParser();
            if (idCode != null) {
                String[] parts = idCode.split(" ");
                if (parts.length > 1) {
                    p.parse(mol, parts[0], parts[1]);
                } else
                    p.parse(mol, idCode);
                model.setValue(mol, true);
            }
        } catch (Exception e) {
            Log.println("error setting idcode data " + e);
        } finally {
        }
    }

    public void setFragment(boolean set)
    {
        model.setFragment(set);
    }

    public boolean isFragment()
    {
        return model.isFragment();
    }

    public String getMolFile()
    {
        return model.getMolFile(false);
    }

    public void setMolFile(String molFile)
    {
        model.setMolFile(molFile);
    }

    public String getMolFileV3()
    {
        return model.getMolFile(true);
    }

    public String getSmiles()
    {
        return model.getSmiles();
    }

    public void setSmiles(String smiles)
    {
        model.setSmiles(smiles);
    }


    public void setAtomHightlightCallback(final JavaScriptObject atomHightlightCallback)
    {
        if (atomHightlightCallback != null) {
            model.registerAtomHighlightCallback(new Model.AtomHighlightCallback()
            {
                @Override
                public void onHighlight(int atom, boolean selected)
                {
                    callFuncIZ(atomHightlightCallback, atom, selected);
                }
            });
        } else
            model.registerAtomHighlightCallback(null);
    }

    public void setBondHightlightCallback(final JavaScriptObject bondHightlightCallback)
    {
        if (bondHightlightCallback != null) {
            model.registerBondHighlightCallback(new Model.BondHighlightCallback()
            {
                @Override
                public void onHighlight(int bond, boolean selected)
                {
                    callFuncIZ(bondHightlightCallback, bond, selected);
                }
            });
        } else
            model.registerBondHighlightCallback(null);
    }


    public void setChangeListenerCallback(final JavaScriptObject cb)
    {
        if (cb != null) {
            model.addChangeListener(new IChangeListener()
            {
                @Override
                public void onChange()
                {
                    String idcode = model.getIDCode();
                    callFuncS(cb, idcode);

                }
            });
        }
    }

    private StereoMolecule createMolecule(String idcode, boolean fragment/*, int width, int height*/)
    {
        StereoMolecule mol = new StereoMolecule();
        mol.setFragment(fragment);
        if (idcode != null && idcode.trim().length() > 0) {
            IDCodeParser p = new IDCodeParser();
            String[] elements = idcode.split(" ");
            if (elements == null || elements.length == 1)
                p.parse(mol, idcode);
            else
                p.parse(mol, elements[0], elements[1]);

            mol.setStereoBondsFromParity();
/*
            GWTDepictor depictor = new GWTDepictor(mol);
            depictor.updateCoords(null,
                    new java.awt.geom.Rectangle2D.Double(0, 0, (float) width, (float) height),
                    GWTDepictor.cModeInflateToMaxAVBL
            );
*/

        }
        return mol;
    }


    private void setUpHandlers()
    {
    }

    private Context2d getContext2d()
    {
        return drawPane.canvas.getContext2d();
    }

    private void setupMouseHandlers()
    {
        drawPane.setOnMouseClicked(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                onMouseClicked(event, false);
            }
        }, new DoubleClickHandler()
        {
            @Override
            public void onDoubleClick(DoubleClickEvent event)
            {
                onMouseClicked(event, true);

            }
        });

        drawPane.setOnMouseDragged(new MouseMoveHandler()
        {
            @Override
            public void onMouseMove(MouseMoveEvent event)
            {
                if (drag)
                    onMouseDragged(event);

            }
        });
        drawPane.setOnMouseMoved(new MouseMoveHandler()
        {
            @Override
            public void onMouseMove(MouseMoveEvent event)
            {
                boolean moved = mousePoint == null ? false : true;
//                        mousePoint.distance(event.getX(), event.getY()) > 1;
                if (!drag && moved) {
                    onMouseMoved(event);
                }
                mousePoint = new Point2D.Double(event.getX(), event.getY());
            }
        });
        drawPane.setOnMouseOut(new MouseOutHandler()
        {
            @Override
            public void onMouseOut(MouseOutEvent event)
            {
                mousePoint = null;
            }
        });
        drawPane.setOnMousePressed(new MouseDownHandler()
        {
            @Override
            public void onMouseDown(MouseDownEvent event)
            {

                drag = true;
                onMousePressed(event);
            }
        });
        drawPane.setOnMouseReleased(new MouseUpHandler()
        {
            @Override
            public void onMouseUp(MouseUpEvent event)
            {
                drag = false;
                onMouseReleased(event);
                mousePoint = null;
            }
        });
        drawPane.setOnKeyPressed(new ACTKeyEventHandler()
        {
            @Override
            public void onKey(IKeyEvent event)
            {
                onKeyPressed(event);
            }
        });


/*
        drawPane.setOnKeyReleased(new ACTKeyEventHandler()
        {
            @Override
            public void onKey(IKeyEvent event)
            {
                onKeyPressed(event);
            }
        });
*/

    }

    private void onKeyPressed(IKeyEvent keyEvent)
    {
        Action a = toolBar.getCurrentAction();
        if (a != null) {
            if (a.onKeyPressed(keyEvent)) {
                model.changed();
            } else {
                handleKeyEvent(keyEvent);
            }
        }
    }

    private void handleKeyEvent(IKeyEvent keyEvent)
    {
    }

    private void onMouseMoved(MouseEvent evt)
    {
        if (!rightClick) {
            Action a = toolBar.getCurrentAction();
            if (a != null && !a.isCommand()) {
                //System.out.println("Mouse move.."+evt);
                if (a.onMouseMove(new ACTMouseEvent(evt), false)) {
//                    drawPane.draw();
//                    model.notifyChange();
                    drawPane.draw(a);
//                    a.paint(drawPane.getDrawContext());
                    drawPane.requestFocus();
                }
            }
        }
    }

    private void onMouseDragged(MouseEvent evt)
    {
        if (!rightClick) {
            Action a = toolBar.getCurrentAction();
            if (a != null && !a.isCommand()) {
                if (a.onMouseMove(new ACTMouseEvent(evt)
                        , true)) {
                    drawPane.draw(a);
//                    a.paint(drawPane.getDrawContext());
                }
            }
        }
    }

    private void onMouseDragRelease(MouseEvent evt)
    {
        if (!rightClick) {
            Action a = toolBar.getCurrentAction();
            if (a != null && !a.isCommand()) {
                //            a.onMouseUp(new Point2D(evt.getX(),evt.getY()));
            }
        }
    }

    public boolean onPasteString(String s)
    {
        Log.console("Pasted String: " + s);
        IDCodeParser p  = new IDCodeParser(true);
        try {
            StereoMolecule mol = new StereoMolecule();
            p.parse(mol,s);
            Log.console("Molecule has " + mol.getAllAtoms() + " atoms");
            model.addMolecule(mol);
            return true;
        } catch (Exception e) {
            Log.console("Parse exception " + e);
        }
        return false;
    }

    public boolean onPasteImage(Object s)
    {
        Log.console("Pasted Image: " + s);
        return false;
    }

    public void onPaste(DataTransfer s)
    {
        Log.console("Pasted Data: " + s);
        Log.console("Image: " + s.getData("image/png"));
        Log.console("Text: " + s.getData("text/plain"));
    }

        public static native void addPasteHandler(StructureEditor self, String text)
    /*-{
        console.log(text + " " + $wnd.Clipboard);
        $doc.addEventListener('paste', function (e) {
            if (e.clipboardData) {
//                self.@com.actelion.research.gwt.gui.editor.StructureEditor::onPaste(Lcom/google/gwt/dom/client/DataTransfer;)(e.clipboardData);
                var items = e.clipboardData.items;
                var done = false;
                if (items) {
                    //access data directly
                    for (var i = 0; !done && i < items.length; i++) {
                        console.log("Item is " + items[i].type);
                        if (items[i].type.indexOf("image") !== -1) {
                            //image
                            var blob = items[i].getAsFile();
                            var URLObj = window.URL || window.webkitURL;
                            var source = URLObj.createObjectURL(blob);

                            var pastedImage = new Image();
                            pastedImage.onload = function () {
                                if(self.@com.actelion.research.gwt.gui.editor.StructureEditor::onPasteImage(Ljava/lang/Object;)(pastedImage)) {
                                    done = true;
                                }
                            };
                            pastedImage.src = source;
                        } else if (items[i].type.indexOf("text/plain") !== -1) {
                            items[i].getAsString(function (s){
                               if (self.@com.actelion.research.gwt.gui.editor.StructureEditor::onPasteString(Ljava/lang/String;)(s)) {
                                   done = true;
                               }
                            });

                        }
                    }
                    e.preventDefault();
                }
            }

        }, false); //official paste handler




//        vaddPasteHandlerconsole.log("Copy Event " + copyEvent);
//        $doc.dispatchEvent(copyEvent);
    }-*/;

    private void onMouseClicked(MouseEvent evt, boolean dbl)
    {
        if (!rightClick) {
            Action a = toolBar.getCurrentAction();
            if (a != null && !a.isCommand()) {
                if (dbl && a.onDoubleClick(new ACTMouseEvent(evt))) {
                    drawPane.draw(a);
//                    a.paint(drawPane.getDrawContext());
                }
            }
        }

    }

    private void onMouseReleased(MouseEvent evt)
    {
        if (!rightClick) {
            Action a = toolBar.getCurrentAction();
            if (a != null && !a.isCommand()) {
                if (a.onMouseUp(new ACTMouseEvent(evt))) {
                    drawPane.draw();
                    model.changed();
                }
            }
        }
        setCursor(ICursor.DEFAULT);
        rightClick = false;
    }

    private void onMousePressed(MouseEvent evt)
    {

        if (evt.getNativeButton() == NativeEvent.BUTTON_RIGHT) {
//            Log.console("Thow right clicks!!!");
            rightClick = true;
//            contextMenu.show(this, evt.getScreenX(), evt.getScreenY());
        }
        if (!rightClick) {
            Action a = toolBar.getCurrentAction();
            if (a != null && !a.isCommand()) {
                a.onMouseDown(new ACTMouseEvent(evt));
                setCursor(a.getCursor());
            }
//            evt.consume();
        }

    }

    private boolean rightClick = false;


    private void setCursor(int c)
    {
        switch (c) {
            case ICursor.SE_RESIZE:
                drawPane.setCursor(Style.Cursor.SE_RESIZE);
                break;
            case ICursor.CROSSHAIR:
                drawPane.setCursor(Style.Cursor.CROSSHAIR);
                break;
            case ICursor.DEFAULT:
            default:
                drawPane.setCursor(Style.Cursor.DEFAULT);
                break;
        }
    }

    private void doAction(Action a)
    {
        toolBar.doAction(a);
        drawPane.requestLayout();
    }


    @Override
    public void onChange()
    {
        if (idcodeTextElement != null) {
            String idcode = model.getIDCode();
            idcodeTextElement.setValue(idcode);
        }
        if (fragmentStatus != null) {
            fragmentStatus.setInnerText(model.isFragment() ? "Q" : "");
        }
    }


    private native void callFuncIZ(JavaScriptObject func, int param, boolean b) /*-{
        func(param, b);
    }-*/;

    private native void callFuncS(JavaScriptObject func, String f) /*-{
        func(f);
    }-*/;


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


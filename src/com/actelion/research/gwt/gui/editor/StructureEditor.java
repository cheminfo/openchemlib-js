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

import com.actelion.research.chem.*;
import com.actelion.research.gwt.gui.viewer.GWTDepictor;
import com.actelion.research.gwt.gui.viewer.Log;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.actions.Action;
import com.actelion.research.share.gui.editor.geom.ICursor;
import com.actelion.research.share.gui.editor.io.IKeyEvent;
import com.actelion.research.share.gui.editor.listeners.IChangeListener;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

//import org.timepedia.exporter.client.Export;
//import org.timepedia.exporter.client.Exportable;

/**
 * Project:
 * User: rufenec
 * Date: 7/1/2014
 * Time: 10:05 AM
 */
@JsType
@JsNamespace("OCL")
@JsExport
public class StructureEditor implements IChangeListener//,Exportable
{
    static int TEXTHEIGHT = 20;
    static int TOOLBARWIDTH = 45;
    static int TOOLBARHEIGHT = 360;

    private boolean drag = false;
    private Point2D mousePoint = null;
    private Model model;
    private ToolBar<Element> toolBar;
    private DrawArea drawPane;
    private InputElement idcodeTextElement;
    private Element container = null;
    private Boolean viewOnly = false;
    private static List<StructureEditor> map = new ArrayList<StructureEditor>();

    static {
        initObserver();
    }

    @JsNoExport
    public StructureEditor()
    {
        this("editor");
    }

//    private native NodeList querySelector(String selectors)/*-{
//        return $doc.querySelectorAll(selectors);
//    }-*/;

    @JsExport
    public StructureEditor(String id)
    {
        container = Document.get().getElementById(id);
        if (container != null) {
            model = new GWTEditorModel(0);
            String vo = container.getAttribute("view-only");
            viewOnly = Boolean.parseBoolean(vo);

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
            toolBar = new ToolBarImpl(model);
            Element toolBarElement = toolBar.createElement(container, toolBarWidth, height - TEXTHEIGHT - 5);
            if (!viewOnly)
                container.appendChild(toolBarElement);

            drawPane = new DrawArea(model);
            Element drawAreaElement = drawPane.createElement(container, toolBarWidth,0,(width - toolBarWidth), height - TEXTHEIGHT - 5);
            container.appendChild(drawAreaElement);

            if (showIDCode) {
                idcodeTextElement = Document.get().createTextInputElement();
                idcodeTextElement.setId(id + "-idcode-element");
                setIDCodeTextPanelSizeAndPosition(0,height - TEXTHEIGHT - 5, TEXTHEIGHT,width);
                container.appendChild(idcodeTextElement);
            }
            model.addChangeListener(this);
            StereoMolecule mol = createMolecule(idcode, isFragment, (width - toolBarWidth), height - TEXTHEIGHT - 5);
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
                    if (width != w || height != h) {
                        drawPane.setSize(w - toolBarWidth, h - TEXTHEIGHT - 5);
                        setIDCodeTextPanelSizeAndPosition(0,h - TEXTHEIGHT - 5, TEXTHEIGHT,width);
                    }
                }
            });
        }
    }


//    private native void observeDataChange(Element el) /*-{
//        var config = {childList: true}
//        $wnd.edit$observer.observe(el, config);
//    }-*/;


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
        return viewOnly ? 0 : TOOLBARWIDTH;
    }

    private void setIDCodeTextPanelSizeAndPosition(int x, int y, int h, int w)
    {
        idcodeTextElement.setAttribute("style",
                "position:absolute;" +
                        "left:" + x + "px; " +
                        "top:" + y + "px;" +
                        "width:" + w + "px;" +
                        "height:" + h + "px;");

//        idcodeTextElement.setAttribute("style", "position:relative;float:left;width:" + (w - 5) + "px;height:" + TEXTHEIGHT + "px;");
    }

    @JsExport
    public static StructureEditor createEditor(String id)
    {
        return new StructureEditor(id);
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

    private StereoMolecule createMolecule(String idcode, boolean fragment, int width, int height)
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
            GWTDepictor depictor = new GWTDepictor(getContext2d(), mol);
            depictor.updateCoords(null,
                    new java.awt.geom.Rectangle2D.Float(0, 0, (float) width, (float) height),
                    GWTDepictor.cModeInflateToMaxAVBL
            );

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
                boolean moved = mousePoint == null ? false :
                        mousePoint.distance(event.getX(), event.getY()) > 1;
//                if (mousePoint != null)
//                System.out.println("DrawPane on MouseMove: " + mousePoint.distance(event.getX(),event.getY()));
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

    private void onMouseClicked(MouseEvent evt, boolean dbl)
    {
        System.out.println("onMouseClicked " + evt);
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
            Log.console("Thow right clicks!!!");
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
    }


    private native void callFuncIZ(JavaScriptObject func, int param, boolean b) /*-{
        func(param, b);
    }-*/;

    private native void callFuncS(JavaScriptObject func, String f) /*-{
        func(f);
    }-*/;

}


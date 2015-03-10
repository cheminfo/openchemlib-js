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

import com.actelion.research.chem.IDCodeParser;
import com.actelion.research.chem.StereoMolecule;
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
import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.*;
import com.google.gwt.event.dom.client.*;

import java.awt.geom.Point2D;

//import org.timepedia.exporter.client.Export;
//import org.timepedia.exporter.client.Exportable;

/**
 * Project:
 * User: rufenec
 * Date: 7/1/2014
 * Time: 10:05 AM
 */
//@Export
//@ExportPackage("actchem")
@JsType
@JsNamespace("$wnd.actchem")
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
    private InputElement idcodeTextElement ;


    public StructureEditor()
    {
        this("editor");
    }

   @JsExport
    public StructureEditor(String id)
    {
//        Log.println("StructureEditor ctor(): " + id);
        Element parent = Document.get().getElementById(id);
        if (parent != null) {
            model = new GWTEditorModel(0);
            int width = parent.getClientWidth();
            int height = parent.getClientHeight();
            String idcode = parent.getAttribute("data-idcode");

            toolBar = new ToolBarImpl(model);
            Element toolBarElement = toolBar.create(parent, TOOLBARWIDTH, height-TEXTHEIGHT-5);
            parent.appendChild(toolBarElement);
            drawPane = new DrawArea(model);
            Element drawAreaElement = drawPane.create(parent, (width - TOOLBARWIDTH), height-TEXTHEIGHT-5);
            parent.appendChild(drawAreaElement);
            idcodeTextElement = Document.get().createTextInputElement();
            idcodeTextElement.setId(id + "-idcode-element");
            idcodeTextElement.setAttribute("style", "position:relative;float:left;width:" + (width - 5) + "px;height:" + TEXTHEIGHT + "px;");
            parent.appendChild(idcodeTextElement);

            model.addChangeListener(this);
            System.out.println("Idcode is : " + idcode);
            StereoMolecule mol = createMolecule(idcode, (width - TOOLBARWIDTH), height-TEXTHEIGHT-5);
            //mol.setFragment(false);
            model.setValue(mol,true);
            setUpHandlers();
            setupMouseHandlers();
        }
    }

    @JsExport
    public static StructureEditor createEditor(String id)
    {
        Log.println("StructureEditor::createEdior() " + id);
        return new StructureEditor(id);
    }

    @JsProperty
    public String getIDCode()
    {
        return model.getIDCode();
    }

    @JsProperty
    public void setIDCode(String idCode)
    {
        try {
            StereoMolecule mol = new StereoMolecule();
            IDCodeParser p = new IDCodeParser();
            if (idCode != null) {
                String[] parts = idCode.split(" ");
                if (parts.length > 1) {
                    p.parse(mol, parts[0],parts[1]);
                } else
                    p.parse(mol, idCode);
                model.setValue(mol, true);
            }
        } catch (Exception e) {
            Log.println("error setting idcode data " + e);
        } finally {
        }
    }

    @JsProperty
    public void setFragment(boolean set)
    {
        model.setFragment(set);
    }

    @JsProperty
    public boolean isFragment()
    {
        return model.isFragment();
    }

    @JsProperty
    public String getMolFile()
    {
        return model.getMolFile(false);
    }

    @JsProperty
    public void setMolFile(String molFile)
    {
        model.setMolFile(molFile);
    }

    @JsProperty
    public String getMolFileV3()
    {
        return model.getMolFile(true);
    }

    @JsProperty
    public String getSmiles()
    {
        return model.getSmiles();
    }

    @JsProperty
    public void setSmiles(String smiles)
    {
        model.setSmiles(smiles);
    }



    @JsProperty
    public void setAtomHightlightCallback(final JavaScriptObject atomHightlightCallback)
    {
        if (atomHightlightCallback != null) {
            model.registerAtomHighlightCallback(new Model.AtomHighlightCallback()
            {
                @Override
                public void onHighlight(int atom, boolean selected)
                {
                    callFunc(atomHightlightCallback,atom,selected);
                }
            });
        } else
            model.registerAtomHighlightCallback(null);
    }

    @JsProperty
    public void setBondHightlightCallback(final JavaScriptObject bondHightlightCallback)
    {
        if (bondHightlightCallback != null) {
            model.registerBondHighlightCallback(new Model.BondHighlightCallback()
            {
                @Override
                public void onHighlight(int bond, boolean selected)
                {
                    callFunc(bondHightlightCallback, bond, selected);
                }
            });
        } else
            model.registerBondHighlightCallback(null);
    }

    private StereoMolecule createMolecule(String idcode, int width, int height)
    {
        StereoMolecule mol = new StereoMolecule();
        if (idcode != null && idcode.length() > 0) {
            IDCodeParser p = new IDCodeParser();
            String[] elements = idcode.split(" ");
            if (elements == null || elements.length == 1)
                p.parse(mol, idcode);
            else
                p.parse(mol,elements[0], elements[1]);


//            new CoordinateInventor().invent(mol, true, true);
            mol.setStereoBondsFromParity();
            GWTDepictor depictor = new GWTDepictor(getContext2d(), mol);
            depictor.updateCoords(null,
                    new java.awt.geom.Rectangle2D.Float(0, 0,(float) width, (float) height),
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
                    mousePoint.distance(event.getX(),event.getY()) > 1;
//                if (mousePoint != null)
//                System.out.println("DrawPane on MouseMove: " + mousePoint.distance(event.getX(),event.getY()));
                if (!drag && moved ) {
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
//        drawPane.setOnMouseDragReleased(new EventHandler<MouseEvent>()
//        {
//            @Override
//            public void handle(MouseEvent mouseEvent)
//            {
//                onMouseDragRelease(mouseEvent);
//            }
//        });
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


//            @Override
//            public void onKeyUp(KeyUpEvent event)
//            {
//                System.out.println("Key Up Handler " + event.getNativeKeyCode());
//                onKeyPressed(event);
//            }

            @Override
            public void onKey(IKeyEvent event)
            {
//                System.out.println("ACTEvent " + event);
                onKeyPressed(event);
            }
        });

        //drawPane.requestFocus();
    }

    private void onKeyPressed(IKeyEvent keyEvent)
    {
        Action a = toolBar.getCurrentAction();
        if (a != null) {
            if (a.onKeyPressed(keyEvent)) {
                model.notifyChange();
//                drawPane.draw();
              a.paint(drawPane.getDrawContext());
            } else {
                handleKeyEvent(keyEvent);
            }
        }
    }

    private void handleKeyEvent(IKeyEvent keyEvent)
    {
//        System.out.println("Handle KeyEvent ion SE " + keyEvent);
//        StereoMolecule mol = model.getSelectedMolecule();
//        if (keyEvent.getCode().equals(KeyCode.DELETE)) {
//            model.pushUndo();
//            if (mol.deleteSelectedAtoms()) {
//                drawPane.draw();
//                model.notifyChange();
//            }
//        }
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
                    model.notifyChange();
//                    model.valueInvalidated();
                }
            }
//            drawPane.requestFocus();
        }
        setCursor(ICursor.DEFAULT);
        rightClick = false;
    }

    private void onMousePressed(MouseEvent evt)
    {

        if (evt.getNativeButton() == NativeEvent.BUTTON_RIGHT) {
            System.err.println("Thow right clicks!!!");
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
//            System.out.println("Structure Editor onChange");
            String idcode = model.getIDCode();
//            System.out.println("Setting idcode to " + idcode);
            idcodeTextElement.setValue(idcode);
        }
    }


    private native void callFunc(JavaScriptObject func, int param, boolean b) /*-{
        func(param,b);
    }-*/;

}


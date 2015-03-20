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




















package com.actelion.research.gwt.gui.viewer;


import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.chem.IDCodeParser;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.gwt.core.Molecule;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsType;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

@JsType
@JsNamespace("$wnd.actchem")
@JsExport
public class StructureView
{
    @JsExport
    public static void showStructures(String cssClass)
    {
        NodeList<Element> list = Document.get().getElementsByTagName("canvas");
        for (int i = 0; i < list.getLength(); i++) {
            CanvasElement ce = null;
            Element el = list.getItem(i);
            if (el instanceof CanvasElement) {
                ce = (CanvasElement) el;
                String names = el.getClassName();
                if (names != null) {
                    String[] clss = names.split(" ");
                    for (String className : clss) {
                        if (cssClass.equals(className)) {
                            StructureElement structureElement = new StructureElement(ce);
                            structureElement.draw();
                        }
                    }
                }
            }
        }
    }

    @JsExport
    public static void renderStructure(String id)
    {
        CanvasElement ce = null;
        Element el = Document.get().getElementById(id);
        if (el instanceof CanvasElement) {
            ce = (CanvasElement) el;
            StructureElement structureElement = new StructureElement(ce);
            structureElement.draw();
        }
    }

    @JsExport
    public static String getIDCode(String id)
    {
        Element el = Document.get().getElementById(id);
        if (el instanceof CanvasElement) {
            return el.getAttribute("data-idcode");
        }
        return null;
    }
    
    @JsExport
    public static void drawStructure(String id, String idcode, String coordinates, JavaScriptObject options)
    {
    	drawStructure(id, idcode, coordinates, getDisplayMode(options));
    }
    
    @JsExport
    public static void drawMolecule(CanvasElement el, Molecule mol, JavaScriptObject options)
    {
    	drawMolecule(el, mol, getDisplayMode(options));
    }
    
    private static native int getDisplayMode(JavaScriptObject options)/*-{
    	options = options || {};
    	var displayMode = 0;
    	if (options.suppressChiralText) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeSuppressChiralText;
    	if (options.suppressESR) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeSuppressESR;
    	if (options.suppressCIPParity) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeSuppressCIPParity;
    	if (options.showMapping) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeShowMapping;
    	if (options.showAtomNumber) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeAtomNo;
    	if (options.showBondNumber) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeBondNo;
    	if (options.inflateToMaxAVBL) displayMode |= @com.actelion.research.chem.AbstractDepictor::cModeInflateToMaxAVBL;
    	if (options.inflateToHighResAVBL) displayMode |= @com.actelion.research.chem.AbstractDepictor::cModeInflateToHighResAVBL;
    	if (options.noTabus) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeNoTabus;
    	if (options.highlightQueryFeatures) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeHiliteAllQueryFeatures;
    	if (options.noStereoProblem) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeNoStereoProblem;
    	return displayMode;
    }-*/;

    private static void drawStructure(String id, String idcode, String coordinates, int displayMode)
    {
        Element el = Document.get().getElementById(id);
        if (el instanceof CanvasElement) {
            CanvasElement ce = (CanvasElement) el;
            StructureElement.drawIDCode(ce,idcode,coordinates, displayMode);
        }
    }
    
    private static void drawStructure(CanvasElement el, String idcode, String coordinates, int displayMode)
    {
    	StructureElement.drawIDCode(el,idcode,coordinates, displayMode);
    }
    
    private static void drawMolecule(CanvasElement el, Molecule mol, int displayMode)
    {
    	StructureElement.drawMolecule(el, mol, displayMode);
    }

}

class StructureElement
{
    private static Map<Object, StructureElement> map = new HashMap<Object, StructureElement>();
    private Canvas canvas;

    static {
        initObserver();
    }

    public StructureElement(CanvasElement el)
    {
        canvas = Canvas.wrap(el);
        map.put(el, this);
        observeDataChange(el);
    }

    private native static void initObserver() /*-{
        $wnd.struct$observer = new MutationObserver(function (mutations)
        {
            mutations.forEach(function (mutation) {
                @com.actelion.research.gwt.gui.viewer.StructureElement::notify(Ljava/lang/Object;)(mutation.target);
            });
        });
    }-*/;


    private native void observeDataChange(Element el) /*-{
        var config = {attributes: true, attributeOldValue: true, attributeFilter: ['data-idcode']}
        $wnd.struct$observer.observe(el, config);
    }-*/;

    public static void notify(Object o)
    {
        Object p = map.get(o);
        if (p instanceof StructureElement) {
            StructureElement se = (StructureElement) p;
            se.draw();
        }
    }

    private static void draw(CanvasElement el, String idcode, String coordinates)
    {
        Canvas canvas = Canvas.wrap(el);
        if (idcode != null && idcode.length() > 0) {
            String combined = idcode + (coordinates != null ? " " + coordinates : "");
            Context2d ctx = canvas.getContext2d();
            drawMolecule(ctx, combined,
                canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
        }
    }
    
    private static void drawMolecule(Context2d ctx, String idcode, int width, int height)
    {
    	drawMolecule(ctx, idcode, width, height, 0);
    }

    private static void drawMolecule(Context2d ctx, String idcode, int width, int height, int displayMode)
    {
        if (idcode != null && idcode.trim().length() > 0) {
            StereoMolecule mol = new StereoMolecule();
            IDCodeParser parser = new IDCodeParser();
            String[] elements = idcode.split(" ");
            if (elements == null || elements.length == 1)
                parser.parse(mol, idcode);
            else
                parser.parse(mol, elements[0], elements[1]);

            AbstractDepictor depictor = new GWTDepictor(ctx, mol, displayMode);
            depictor.validateView(null,
            new Rectangle2D.Float(0, 0, (float) width, (float) height), AbstractDepictor.cModeInflateToMaxAVBL);
            ctx.clearRect(0, 0, width, height);
            depictor.paint(ctx);
        } else {
            ctx.clearRect(0, 0, width, height);
        }
    }
    
    private static void drawMolecule(Context2d ctx, StereoMolecule mol, int width, int height, int displayMode)
    {
    	AbstractDepictor depictor = new GWTDepictor(ctx, mol, displayMode);
    	depictor.validateView(null, new Rectangle2D.Float(0, 0, (float) width, (float) height), AbstractDepictor.cModeInflateToMaxAVBL);
    	ctx.clearRect(0, 0, width, height);
    	depictor.paint(ctx);
    }


    public void draw()
    {
        Element el = canvas.getCanvasElement();
        String idcode = el.getAttribute("data-idcode");
        Context2d ctx = canvas.getContext2d();
        drawMolecule(ctx, idcode,
            canvas.getCoordinateSpaceWidth(),
            canvas.getCoordinateSpaceHeight());
    }
    
    public static void drawIDCode(CanvasElement el, String idcode, String coordinates)
    {
    	drawIDCode(el, idcode, coordinates, 0);
    }

    public static void drawIDCode(CanvasElement el, String idcode, String coordinates, int displayMode)
    {
        Canvas canvas = Canvas.wrap(el);
        if (idcode != null && idcode.length() > 0) {
            String combined = idcode + (coordinates != null ? " " + coordinates : "");
            Context2d ctx = canvas.getContext2d();
            drawMolecule(ctx, combined,
                canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight(), displayMode);
        }
    }
    
    public static void drawMolecule(CanvasElement el, Molecule mol, int displayMode)
    {
    	Canvas canvas = Canvas.wrap(el);
    	Context2d ctx = canvas.getContext2d();
    	drawMolecule(ctx, mol.getStereoMolecule(),
                canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight(), displayMode);
    }

}

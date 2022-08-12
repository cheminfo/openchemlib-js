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

package com.actelion.research.gwt.gui.viewer;

import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.chem.Molecule;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.gui.generic.GenericPolygon;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.TextMetrics;

import java.awt.*;

/**
 * Project: User: rufenec Date: 6/26/2014 Time: 5:46 PM
 */
public class GWTDepictor extends AbstractDepictor {

    static class ColorMap {
        ColorMap(int m, CssColor c) {
            molcol = m;
            color = c;
        }

        int molcol;
        CssColor color;
    }

    static ColorMap MOLECULECOLORS[] = { new ColorMap(Molecule.cAtomColorNone, CssColor.make("BLACK")),
            new ColorMap(Molecule.cAtomColorBlue, CssColor.make("BLUE")),
            new ColorMap(Molecule.cAtomColorRed, CssColor.make("RED")),
            new ColorMap(Molecule.cAtomColorGreen, CssColor.make("GREEN")),
            new ColorMap(Molecule.cAtomColorMagenta, CssColor.make("MAGENTA")),
            new ColorMap(Molecule.cAtomColorOrange, CssColor.make("ORANGE")),
            new ColorMap(Molecule.cAtomColorDarkGreen, CssColor.make(0, 160, 0)),
            new ColorMap(Molecule.cAtomColorDarkRed, CssColor.make(160, 0, 0)) };

    private Context2d ctx = null;
    private CssColor currentColor = CssColor.make("BLACK");

    private int textSize = 8;
    private String currentFont = textSize + "px Helvetica";
    private double lineWidth;

    public GWTDepictor(StereoMolecule mol) {
        this(mol, 0);
    }

    public GWTDepictor(StereoMolecule mol, int displayMode) {
        super(mol, displayMode);
    }

    @Override
    public void paint(Object g) {
        if (g instanceof Context2d) {
            ctx = (Context2d) g;
        }
        super.paint(g);
    }

    @Override
    protected void drawBlackLine(DepictorLine theLine) {
        ctx.beginPath();
        ctx.setStrokeStyle(currentColor);
        ctx.moveTo(theLine.x1, theLine.y1);
        ctx.lineTo(theLine.x2, theLine.y2);
        ctx.stroke();
    }

    @Override
    protected void drawDottedLine(DepictorLine theLine) {
        ctx.beginPath();
        ctx.setStrokeStyle(currentColor);
        ctx.setLineCap(Context2d.LineCap.ROUND);
        ctx.moveTo(theLine.x1, theLine.y1);
        ctx.lineTo(theLine.x2, theLine.y2);
        ctx.stroke();
    }

    @Override
    protected void drawPolygon(GenericPolygon p) {
        ctx.setStrokeStyle(currentColor);
        ctx.setFillStyle(currentColor);
        ctx.beginPath();
        ctx.moveTo(p.getX(0), p.getY(0));
        for (int i = 1; i < p.getSize(); i++) {
            ctx.lineTo(p.getX(i), p.getY(i));
        }
        ctx.closePath();
        ctx.fill();
    }

    @Override
    protected void drawString(String theString, double x, double y) {
        ctx.setFont(currentFont);
        ctx.setTextAlign(Context2d.TextAlign.CENTER);
        ctx.setTextBaseline(Context2d.TextBaseline.MIDDLE);
        ctx.setFillStyle(currentColor);
        ctx.fillText(theString, x, y);
    }

    @Override
    protected void fillCircle(double x, double y, double d) {
        ctx.setFillStyle(currentColor);
        ctx.beginPath();
        ctx.arc(x + d / 2, y + d / 2, d / 2, (double) 0, Math.PI * 2);
        ctx.fill();
    }

    @Override
    protected double getStringWidth(String theString) {
        if (ctx != null) {
            TextMetrics tm = ctx.measureText(theString);
            return (double) tm.getWidth();
        }
        return 8;
    }

    @Override
    protected int getTextSize() {
        return (int) textSize;
    }

    @Override
    protected void setTextSize(int theSize) {
        textSize = theSize;
        currentFont = theSize + "px Helvetica";
    }

    @Override
    protected void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    @Override
    protected double getLineWidth() {
        return lineWidth;
    }

    @Override
    protected void setRGB(int rgb) {
        currentColor = CssColor.make((rgb&0xff), (rgb&0xff00)>>8, (rgb&0xff0000)>>16);
    }

}

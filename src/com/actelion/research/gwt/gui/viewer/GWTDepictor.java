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






















package com.actelion.research.gwt.gui.viewer;


import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.chem.Molecule;
import com.actelion.research.chem.StereoMolecule;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.TextMetrics;

import java.awt.*;

/**
 * Project:
 * User: rufenec
 * Date: 6/26/2014
 * Time: 5:46 PM
 */
public class GWTDepictor extends AbstractDepictor
{

    static class ColorMap
    {
        ColorMap(int m, CssColor c)
        {
            molcol = m;
            color = c;
        }

        int molcol;
        CssColor color;
    }

    static ColorMap MOLECULECOLORS[] = {
        new ColorMap(Molecule.cAtomColorNone, CssColor.make("BLACK")),
        new ColorMap(Molecule.cAtomColorBlue, CssColor.make("BLUE")),
        new ColorMap(Molecule.cAtomColorRed, CssColor.make("RED")),
        new ColorMap(Molecule.cAtomColorGreen, CssColor.make("GREEN")),
        new ColorMap(Molecule.cAtomColorMagenta, CssColor.make("MAGENTA")),
        new ColorMap(Molecule.cAtomColorOrange, CssColor.make("ORANGE")),
        new ColorMap(Molecule.cAtomColorDarkGreen, CssColor.make(0, 160, 0)),
        new ColorMap(Molecule.cAtomColorDarkRed, CssColor.make(160, 0, 0))
    };

    private Context2d ctx = null;
    private CssColor currentColor = CssColor.make("BLACK");

    private int textSize = 8;
    private String currentFont = textSize + "px Helvetica";
    private float lineWidth;

    public GWTDepictor(Context2d ctx, StereoMolecule mol)
    {
        this(ctx, mol, 0);
    }
    
    public GWTDepictor(Context2d ctx, StereoMolecule mol, int displayMode)
    {
    	super(mol, displayMode);
    	this.ctx = ctx;
    }


    @Override
    protected void drawBlackLine(DepictorLine theLine)
    {
        ctx.beginPath();
        ctx.setStrokeStyle(currentColor);
        ctx.moveTo(theLine.x1, theLine.y1);
        ctx.lineTo(theLine.x2, theLine.y2);
        ctx.stroke();
    }

    @Override
    protected void drawDottedLine(DepictorLine theLine)
    {
        ctx.beginPath();
        ctx.setStrokeStyle(currentColor);
        ctx.setLineCap(Context2d.LineCap.ROUND);
        ctx.moveTo(theLine.x1, theLine.y1);
        ctx.lineTo(theLine.x2, theLine.y2);
        ctx.stroke();
    }

    @Override
    protected void drawPolygon(float[] px, float[] py, int count)
    {
        ctx.setStrokeStyle(currentColor);
        ctx.setFillStyle(currentColor);
        ctx.beginPath();
        ctx.moveTo(px[0], py[0]);
        for (int i = 1; i < count; i++) {
            ctx.lineTo(px[i], py[i]);
        }
        ctx.closePath();
        ctx.fill();
    }

    @Override
    protected void drawString(String theString, float x, float y)
    {
        ctx.setFont(currentFont);
        ctx.setTextAlign(Context2d.TextAlign.CENTER);
        ctx.setTextBaseline(Context2d.TextBaseline.MIDDLE);
        ctx.setFillStyle(currentColor);
        ctx.fillText(theString, x, y);
    }

    @Override
    protected void fillCircle(float x, float y, float r)
    {
        ctx.setFillStyle(currentColor);
        ctx.beginPath();
        ctx.arc(
            (double) x, (double) y,
            (double) r, (double) 0,
             Math.PI*2);
        ctx.fill();
    }

    @Override
    protected float getStringWidth(String theString)
    {
        if (ctx != null) {
            TextMetrics tm = ctx.measureText(theString);
            return (float) tm.getWidth();
        }
        return 8;
    }


    @Override
    protected int getTextSize()
    {
        return (int) textSize;
    }

    @Override
    protected void setTextSize(int theSize)
    {
        textSize = theSize;
        currentFont = theSize + "px Helvetica";
    }

    @Override
    protected void setLineWidth(float lineWidth)
    {
        this.lineWidth = lineWidth;
        ctx.setLineWidth(lineWidth);
        ctx.setLineCap(Context2d.LineCap.ROUND);
        ctx.setLineJoin(Context2d.LineJoin.MITER);
    }


    @Override
    protected float getLineWidth()
    {
        return lineWidth;
    }

    @Override
    public void setColor(int theColor)
    {
        currentColor = getColor(theColor);
    }


    @Override
    protected void setColor(Color theColor)
    {
        currentColor = CssColor.make(theColor.getRed(), theColor.getGreen(), theColor.getBlue());
    }

    protected int getColor()
    {
        for (ColorMap cm : MOLECULECOLORS) {
            if (currentColor.equals(cm.color)) {
                return cm.molcol;
            }
        }
        return Molecule.cAtomColorNone;
    }


    private CssColor getColor(int i)
    {
        for (ColorMap cm : MOLECULECOLORS) {
            if (cm.molcol == i) {
                return cm.color;
            }
        }
        return MOLECULECOLORS[0].color;
    }


//    private ColorMap getColor(Color i)
//    {
//        CssColor t = CssColor.make(i.getRed(), i.getBlue(), i.getGreen());
//        for (ColorMap cm : MOLECULECOLORS) {
//            if (cm.color.equals(t)) {
//                return cm;
//            }
//        }
//        return MOLECULECOLORS[0];
//    }


}

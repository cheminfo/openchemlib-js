/*
* Copyright (c) 1997 - 2016
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

package com.actelion.research.chem;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class SVGDepictor extends AbstractDepictor
{
    public static final int DEFAULT_ELEM_WIDTH = 8;

    private static final String FONTNAME = "Helvetica";
    private static int instanceCnt = 0;

    private double lineWidth = 1;
    private int textSize = 10;
    private int width = 400;
    private int height = 400;

    private String currentColor = "black";
    private final java.util.List<String> bonds = new ArrayList<String>();
    private final java.util.List<String> atoms = new ArrayList<String>();

    private String id;
    private StringBuffer buffer = new StringBuffer();

    private Font currentFont = new Font(FONTNAME, Font.PLAIN, 12);
    private Graphics2D graphics;

    public SVGDepictor(StereoMolecule mol, String id)
    {
        this(mol, 0, id);
    }

    public SVGDepictor(StereoMolecule mol, int displayMode, String id)
    {
        super(mol, displayMode);
        this.id = id;
        instanceCnt++;
    }

    public static final String makeColor(int r, int g, int b)
    {
        return "rgb(" + r + "," + g + "," + b + ")";
    }

    public String getId()
    {
        return id != null ? id : ("mol" + instanceCnt);
    }

    private static double round(double number) {
        return new BigDecimal(number).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
    }

    private void write(String s)
    {
        buffer.append("  ");
        buffer.append(s);
        buffer.append("\n");
    }

    @Override
    protected void drawBlackLine(DepictorLine theLine)
    {
        String s = "<line " +
                "x1=\"" + round(theLine.x1) + "\" " +
                "y1=\"" + round(theLine.y1) + "\" " +
                "x2=\"" + round(theLine.x2) + "\" " +
                "y2=\"" + round(theLine.y2) + "\" " +
                "stroke=\"" + currentColor + "\" " +
                "stroke-width=\"" + lineWidth + "\" />";
        write(s);
    }

    @Override
    protected void drawDottedLine(DepictorLine theLine)
    {
        String s = "<line stroke-dasharray=\"3, 3\" " +
                "x1=\"" + round(theLine.x1) + "\" " +
                "y1=\"" + round(theLine.y1) + "\" " +
                "x2=\"" + round(theLine.x2) + "\" " +
                "y2=\"" + round(theLine.y2) + "\" " +
                "stroke=\"" + currentColor + "\" " +
                "stroke-width:" + lineWidth + "\" />";

        write(s);
    }

    @Override
    protected void drawString(String theString, double x, double y)
    {

        double strWidth = getStringWidth(theString);
        String s = "<text " +
                "x=\"" + round(x - strWidth / 2.0) + "\" " +
                "y=\"" + round(y + textSize / 3.0) + "\" " +
                "font-family=\" " + currentFont.getName() + "\" " +
                "font-size=\"" + currentFont.getSize() + "\" " +
                "fill=\"" + currentColor + "\">" + theString +
                "</text>";
        write(s);
    }

    @Override
    protected void drawPolygon(double[] x, double[] y, int count)
    {
        StringBuilder s = new StringBuilder("<polygon points=\"");
        for (int i = 0; i < count; i++) {
            s.append(round(x[i]));
            s.append(",");
            s.append(round(y[i]));
            s.append(" ");
        }
        s.append("\" " +
                "fill=\"" + currentColor + "\" " +
                "stroke=\"" + currentColor + "\" " +
                "stroke-width=\"1\" />");
        write(s.toString());
    }

    @Override
    protected void fillCircle(double x, double y, double d)
    {
        String s = "<circle " +
                "cx=\"" + round(x + d / 2.0) + "\" " +
                "cy=\"" + round(y + d / 2.0) + "\" " +
                "r=\"" + round(d / 2.0) + "\" " +
                "fill=\"" + currentColor + "\" />";
        write(s);
    }
    
    @Override
    protected double getStringWidth(String theString)
    {
        float ret = (float)currentFont.getStringBounds(theString,graphics.getFontRenderContext()).getWidth();
        return ret;
    }

    @Override
    protected void setTextSize(int theSize)
    {
        if (textSize != theSize) {
            textSize = theSize;
            currentFont = new Font(FONTNAME, Font.PLAIN, theSize);
        }
    }

    @Override
    protected int getTextSize()
    {
        return textSize;
    }

    @Override
    protected double getLineWidth()
    {
        return lineWidth;
    }

    @Override
    protected void setLineWidth(double width)
    {
        lineWidth = round(Math.max(width, 1));
    }

    @Override
    protected void setColor(Color theColor)
    {
        currentColor = makeColor(theColor.getRed(), theColor.getGreen(), theColor.getBlue());
    }

    @Override
    protected void onDrawBond(int bond, double x1, double y1, double x2, double y2)
    {
        String s = "<line " +
                "id=\"" + getId() + ":Bond:" + bond + "\" " +
                "class=\"event\" " +	// class to respond to the mouse event
                "x1=\"" + round(x1) + "\" " +
                "y1=\"" + round(y1) + "\" " +
                "x2=\"" + round(x2) + "\" " +
                "y2=\"" + round(y2) + "\" " +
                "stroke-width=\"" + DEFAULT_ELEM_WIDTH + "\" " +
                "opacity=\"0\" />";
        bonds.add(s);
    }

    @Override
    protected void onDrawAtom(int atom, String symbol, double x, double y)
    {
        String s = "<circle " +
                "id=\"" + getId() + ":Atom:" + atom + "\" " +
                "class=\"event\" " + // class to respond to the mouse event
                "cx=\"" + round(x) + "\" " +
                "cy=\"" + round(y) + "\" " +
                "r=\"" + DEFAULT_ELEM_WIDTH + "\" " +
                "opacity=\"0\" />";
        atoms.add(s);
    }


    @Override
    public String toString()
    {
        String header =
                "<svg " +
                        "id=\"" + getId() + "\" " +
                        "xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" " +
                        "width=\"" + width + "px\" " +
                        "height=\"" + height + "px\" " +
                        "viewBox=\"0 0 " + width + " " + height + "\">\n";

        String footer = "</svg>";

        String style = "<style>" +
                " #" + getId() +
                " { pointer-events:none; }" +	// Disable Mouse events on the root element so they get passed to the childs
                " #" + getId() + " .event " +
                " { pointer-events:all; }" +	// Enable Mouse events for elements possessing the class "event"
                " line { stroke-linecap:round; }" +
                " polygon { stroke-linejoin:round; }" +
                " </style>\n";
        header += "  ";
        header += style;

        // Append the (invisible) bond lines
        for (String b : bonds) {
            write(b);
        }

        // Append the (invisible) atom circles
        for (String a : atoms) {
            write(a);
        }

        return header + buffer.toString() + footer;
    }

    @Override
    public DepictorTransformation simpleValidateView(Rectangle2D.Double viewRect, int mode)
    {

        width = (int) Math.round(viewRect.getWidth());
        height = (int) Math.round(viewRect.getHeight());
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics = img.createGraphics();

        return super.simpleValidateView(viewRect, mode);
    }


}

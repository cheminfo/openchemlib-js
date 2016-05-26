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

package java.awt;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

public class Font
{
    public static final int BOLD        = 1;
    public static final int PLAIN       = 0;
    public static final int ITALIC      = 2;
    private String name;
    private int type ;
    private int size;
    static Object canvas =  null;

    public Font(String name, int type, int size)
    {
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public String getName()
    {
        return name;
    }
    public int getSize()
    {
        return size;
    }

//    public GlyphVector createGlyphVector(java.awt.font.FontRenderContext frc, String str)
//    {
//
//        return new GlyphVector();
//    }

    public Rectangle2D getStringBounds(String theString, FontRenderContext fontRenderContext)
    {
        double width = getStringWidth(theString);
        return new Rectangle.Double(0, 0, width, 0);
    }

    private native double getStringWidth(String text) /*-{
        var canvas = @java.awt.Font::canvas;
        if (!canvas) {
            canvas = $doc.createElement("canvas");
            @java.awt.Font::canvas = canvas;
        }
        var f = "" + this.@java.awt.Font::size + "px " + this.@java.awt.Font::name;
        var ctx = canvas.getContext("2d");
        ctx.font = f;
        var text = ctx.measureText(text); // TextMetrics object
        return text.width;
    }-*/;
}

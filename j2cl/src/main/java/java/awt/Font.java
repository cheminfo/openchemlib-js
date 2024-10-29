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

package java.awt;

import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Rectangle2D;

import jsinterop.annotations.JsMethod;

public class Font {
  public static final int PLAIN = 0;
  public static final int BOLD = 1;
  public static final int ITALIC = 2;
  private String name;
  private int size;

  private static final double[] helveticaSizes = { 5.55, 15, 15, 15, 15, 15, 15, 15, 15, 5.56, 5.56, 5.56, 5.56, 5.56,
      15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 15, 5.56, 5.56, 7.1, 11.12, 11.12, 17.78,
      13.34, 3.82, 6.66, 6.66, 7.78, 11.68, 5.56, 6.66, 5.56, 5.56, 11.12, 11.12, 11.12, 11.12, 11.12, 11.12, 11.12,
      11.12, 11.12, 11.12, 5.56, 5.56, 11.68, 11.68, 11.68, 11.12, 20.3, 13.34, 13.34, 14.44, 14.44, 13.34, 12.22,
      15.56, 14.44, 5.56, 10, 13.34, 11.12, 16.66, 14.44, 15.56, 13.34, 15.56, 14.44, 13.34, 12.22, 14.44, 13.34, 18.88,
      13.34, 13.34, 12.22, 5.56, 5.56, 5.56, 9.38, 11.12, 6.66, 11.12, 11.12, 10, 11.12, 11.12, 5.56, 11.12, 11.12,
      4.44, 4.44, 10, 4.44, 16.66, 11.12, 11.12, 11.12, 11.12, 6.66, 10, 5.56, 11.12, 10, 14.44, 10, 10, 10, 6.68, 5.2,
      6.68, 11.68, 10.5, };

  static Object canvas = null;

  public Font(String name, int type, int size) {
    this.name = name;
    this.size = size;
  }

  public String getName() {
    return name;
  }

  public int getSize() {
    return size;
  }

  public Rectangle2D getStringBounds(String theString, FontRenderContext fontRenderContext) {
    double width = getStringWidth(theString);
    return new Rectangle.Double(0, 0, width, 0);
  }

  private double getCharWidth(char ch) {
    double width;
    if (ch < 128) {
      width = helveticaSizes[ch];
    } else {
      width = 5.56;
    }
    return width * size / 20;
  }

  private double getStringWidth(String text) {
    if (name.equals("Helvetica")) {
      double total = 0;
      for (int i = 0, n = text.length(); i < n; i++) {
        char ch = text.charAt(i);
        total += getCharWidth(ch);
      }
      return total;
    } else {
      return getStringWidthCanvas(text);
    }
  }

  @JsMethod
  private native double getStringWidthCanvas(String text) /*-{
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

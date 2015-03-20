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





package java.awt;

public abstract class Graphics
{

    protected Graphics()
    {
    }

    public abstract Graphics create();

    public Graphics create(int x, int y, int width, int height)
    {
        Graphics g = create();
        if (g == null) return null;
        g.translate(x, y);
        g.clipRect(0, 0, width, height);
        return g;
    }

    public abstract void translate(int x, int y);

    public abstract java.awt.Color getColor();

    public abstract void setColor(java.awt.Color c);

    public abstract void setPaintMode();

    public abstract void setXORMode(java.awt.Color c1);

    public abstract Font getFont();

    public abstract void setFont(Font font);
//    public FontMetrics getFontMetrics() {
//	return getFontMetrics(getFont());
//    }
//    public abstract FontMetrics getFontMetrics(Font f);

    public abstract Rectangle getClipBounds();

    public abstract void clipRect(int x, int y, int width, int height);

    public abstract void setClip(int x, int y, int width, int height);
//    public abstract Shape getClip();

    public abstract void setClip(Shape clip);

//    public abstract void copyArea(int x, int y, int width, int height,
//                                  int dx, int dy);

    public abstract void drawLine(int x1, int y1, int x2, int y2);

    public abstract void fillRect(int x, int y, int width, int height);

/*
    public void drawRect(int x, int y, int width, int height)
    {
        if ((width < 0) || (height < 0)) {
            return;
        }

        if (height == 0 || width == 0) {
            drawLine(x, y, x + width, y + height);
        } else {
            drawLine(x, y, x + width - 1, y);
            drawLine(x + width, y, x + width, y + height - 1);
            drawLine(x + width, y + height, x + 1, y + height);
            drawLine(x, y + height, x, y + 1);
        }
    }
*/

//    public abstract void clearRect(int x, int y, int width, int height);

//    public abstract void drawRoundRect(int x, int y, int width, int height,
//                                       int arcWidth, int arcHeight);
//
//    public abstract void fillRoundRect(int x, int y, int width, int height,
//                                       int arcWidth, int arcHeight);

/*
    public void draw3DRect(int x, int y, int width, int height,
                           boolean raised)
    {
        java.awt.Color c = getColor();
        java.awt.Color brighter = c.brighter();
        java.awt.Color darker = c.darker();

        setColor(raised ? brighter : darker);
        drawLine(x, y, x, y + height);
        drawLine(x + 1, y, x + width - 1, y);
        setColor(raised ? darker : brighter);
        drawLine(x + 1, y + height, x + width, y + height);
        drawLine(x + width, y, x + width, y + height - 1);
        setColor(c);
    }


    public void fill3DRect(int x, int y, int width, int height,
                           boolean raised)
    {
        java.awt.Color c = getColor();
        java.awt.Color brighter = c.brighter();
        java.awt.Color darker = c.darker();

        if (!raised) {
            setColor(darker);
        }
        fillRect(x + 1, y + 1, width - 2, height - 2);
        setColor(raised ? brighter : darker);
        drawLine(x, y, x, y + height - 1);
        drawLine(x + 1, y, x + width - 2, y);
        setColor(raised ? darker : brighter);
        drawLine(x + 1, y + height - 1, x + width - 1, y + height - 1);
        drawLine(x + width - 1, y, x + width - 1, y + height - 2);
        setColor(c);
    }
*/

//    public abstract void drawOval(int x, int y, int width, int height);
//
//    public abstract void fillOval(int x, int y, int width, int height);
//
//    public abstract void drawArc(int x, int y, int width, int height,
//                                 int startAngle, int arcAngle);
//
//    public abstract void fillArc(int x, int y, int width, int height,
//                                 int startAngle, int arcAngle);
//
//    public abstract void drawPolyline(int xPoints[], int yPoints[],
//                                      int nPoints);

    public abstract void drawPolygon(int xPoints[], int yPoints[],
                                     int nPoints);
//    public void drawPolygon(Polygon p) {
//	drawPolygon(p.xpoints, p.ypoints, p.npoints);
    //   }

    public abstract void fillPolygon(int xPoints[], int yPoints[],
                                     int nPoints);

//    public void fillPolygon(Polygon p) {
//	fillPolygon(p.xpoints, p.ypoints, p.npoints);
//    }

    public abstract void drawString(String str, int x, int y);

//   public abstract void drawString(AttributedCharacterIterator iterator,
//                                    int x, int y);
//


    public void drawChars(char data[], int offset, int length, int x, int y)
    {
        drawString(new String(data, offset, length), x, y);
    }


//    public void drawBytes(byte data[], int offset, int length, int x, int y) {
//	drawString(new String(data, 0, offset, length), x, y);
//    }


//    public abstract boolean drawImage(Image img, int x, int y,
//				      ImageObserver observer);
//

//    public abstract boolean drawImage(Image img, int x, int y,
//				      int width, int height,
//				      ImageObserver observer);
//

//    public abstract boolean drawImage(Image img, int x, int y,
//				      java.awt.Color bgcolor,
//				      ImageObserver observer);

//    public abstract boolean drawImage(Image img, int x, int y,
//				      int width, int height,
//				      java.awt.Color bgcolor,
//				      ImageObserver observer);

//    public abstract boolean drawImage(Image img,
//				      int dx1, int dy1, int dx2, int dy2,
//				      int sx1, int sy1, int sx2, int sy2,
//				      ImageObserver observer);


//    public abstract boolean drawImage(Image img,
//				      int dx1, int dy1, int dx2, int dy2,
//				      int sx1, int sy1, int sx2, int sy2,
//				      Color bgcolor,
//				      ImageObserver observer);


    public abstract void dispose();

    public void finalize()
    {
        dispose();
    }

//    public String toString() {
//	return getClass().getName() + "[font=" + getFont() + ",color=" + getColor() + "]";
//    }

    @Deprecated
    public Rectangle getClipRect()
    {
        return getClipBounds();
    }


/*
    public boolean hitClip(int x, int y, int width, int height)
    {
        // Note, this implementation is not very efficient.
        // Subclasses should override this method and calculate
        // the results more directly.
        Rectangle clipRect = getClipBounds();
        if (clipRect == null) {
            return true;
        }
        return clipRect.intersects(x, y, width, height);
    }
*/


/*    public Rectangle getClipBounds(Rectangle r)
    {
        // Note, this implementation is not very efficient.
        // Subclasses should override this method and avoid
        // the allocation overhead of getClipBounds().
        Rectangle clipRect = getClipBounds();
        if (clipRect != null) {
            r.x = clipRect.x;
            r.y = clipRect.y;
            r.width = clipRect.width;
            r.height = clipRect.height;
        } else if (r == null) {
            throw new NullPointerException("null rectangle parameter");
        }
        return r;
    }*/
}

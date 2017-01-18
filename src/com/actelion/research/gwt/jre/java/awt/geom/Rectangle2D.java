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

package java.awt.geom;

/**
 *  * JDK Class Emulation for GWT
 */

public abstract class Rectangle2D extends RectangularShape
{
    public static final int OUT_LEFT = 1;
    public static final int OUT_TOP = 2;
    public static final int OUT_RIGHT = 4;
    public static final int OUT_BOTTOM = 8;

    public static class Float extends Rectangle2D
    {
        public float x;
        public float y;
        public float width;
        public float height;
        public Float() {
        }

        public double getX() {
            return (double) x;
        }
        public double getY() {
            return (double) y;
        }
        public double getWidth() {
            return (double) width;
        }
        public double getHeight() {
            return (double) height;
        }
        public boolean isEmpty() {
            return (width <= 0.0f) || (height <= 0.0f);
        }
        public void setRect(double x, double y, double w, double h) {
            this.x = (float) x;
            this.y = (float) y;
            this.width = (float) w;
            this.height = (float) h;
        }

        public Rectangle2D createUnion(Rectangle2D r) {
            Rectangle2D dest;
            if (r instanceof Float) {
                dest = new Rectangle2D.Float();
            } else {
                dest = new Rectangle2D.Double();
            }
            Rectangle2D.union(this, r, dest);
            return dest;
        }

    }

    public static class Double extends Rectangle2D {

        public double x;

        public double y;

        public double width;

        public double height;
        public Double() {
        }
        public Double(double x, double y, double w, double h) {
            setRect(x, y, w, h);
        }
        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
        public double getWidth() {
            return width;
        }
        public double getHeight() {
            return height;
        }
        public boolean isEmpty() {
            return (width <= 0.0) || (height <= 0.0);
        }
        public void setRect(double x, double y, double w, double h) {
            this.x = x;
            this.y = y;
            this.width = w;
            this.height = h;
        }
        public Rectangle2D createUnion(Rectangle2D r) {
            Rectangle2D dest = new Rectangle2D.Double();
            Rectangle2D.union(this, r, dest);
            return dest;
        }

    }
    protected Rectangle2D() {
    }
    public abstract void setRect(double x, double y, double w, double h);


    public void setFrame(double x, double y, double w, double h) {
        setRect(x, y, w, h);
    }
    public boolean contains(double x, double y) {
        double x0 = getX();
        double y0 = getY();
        return (x >= x0 &&
                y >= y0 &&
                x < x0 + getWidth() &&
                y < y0 + getHeight());
    }
    public boolean intersects(double x, double y, double w, double h) {
        if (isEmpty() || w <= 0 || h <= 0) {
            return false;
        }
        double x0 = getX();
        double y0 = getY();
        return (x + w > x0 &&
                y + h > y0 &&
                x < x0 + getWidth() &&
                y < y0 + getHeight());
    }

    public boolean contains(double x, double y, double w, double h) {
        if (isEmpty() || w <= 0 || h <= 0) {
            return false;
        }
        double x0 = getX();
        double y0 = getY();
        return (x >= x0 &&
                y >= y0 &&
                (x + w) <= x0 + getWidth() &&
                (y + h) <= y0 + getHeight());
    }


    public static void intersect(Rectangle2D src1,
                                 Rectangle2D src2,
                                 Rectangle2D dest) {
        double x1 = Math.max(src1.getMinX(), src2.getMinX());
        double y1 = Math.max(src1.getMinY(), src2.getMinY());
        double x2 = Math.min(src1.getMaxX(), src2.getMaxX());
        double y2 = Math.min(src1.getMaxY(), src2.getMaxY());
        dest.setFrame(x1, y1, x2-x1, y2-y1);
    }
    public abstract Rectangle2D createUnion(Rectangle2D r);
    public static void union(Rectangle2D src1,
                             Rectangle2D src2,
                             Rectangle2D dest) {
        double x1 = Math.min(src1.getMinX(), src2.getMinX());
        double y1 = Math.min(src1.getMinY(), src2.getMinY());
        double x2 = Math.max(src1.getMaxX(), src2.getMaxX());
        double y2 = Math.max(src1.getMaxY(), src2.getMaxY());
        dest.setFrameFromDiagonal(x1, y1, x2, y2);
    }

    public void add(double newx, double newy) {
        double x1 = Math.min(getMinX(), newx);
        double x2 = Math.max(getMaxX(), newx);
        double y1 = Math.min(getMinY(), newy);
        double y2 = Math.max(getMaxY(), newy);
        setRect(x1, y1, x2 - x1, y2 - y1);
    }

    public void add(java.awt.geom.Point2D pt) {
        add(pt.getX(), pt.getY());
    }

    public void add(Rectangle2D r) {
        double x1 = Math.min(getMinX(), r.getMinX());
        double x2 = Math.max(getMaxX(), r.getMaxX());
        double y1 = Math.min(getMinY(), r.getMinY());
        double y2 = Math.max(getMaxY(), r.getMaxY());
        setRect(x1, y1, x2 - x1, y2 - y1);
    }

    public int hashCode() {
        long bits = java.lang.Double.doubleToLongBits(getX());
        bits += java.lang.Double.doubleToLongBits(getY()) * 37;
        bits += java.lang.Double.doubleToLongBits(getWidth()) * 43;
        bits += java.lang.Double.doubleToLongBits(getHeight()) * 47;
        return (((int) bits) ^ ((int) (bits >> 32)));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Rectangle2D) {
            Rectangle2D r2d = (Rectangle2D) obj;
            return ((getX() == r2d.getX()) &&
                    (getY() == r2d.getY()) &&
                    (getWidth() == r2d.getWidth()) &&
                    (getHeight() == r2d.getHeight()));
        }
        return false;
    }
}

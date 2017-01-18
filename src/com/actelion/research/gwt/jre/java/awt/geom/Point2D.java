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

import java.io.Serializable;

/**
 *  * JDK Class Emulation for GWT
 */

public abstract class Point2D {

    public static class Float extends Point2D
    {
        public float x;
        public float y;
        public Float(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return (double) x;
        }
        public double getY() {
            return (double) y;
        }
    }

    public static class Double extends Point2D implements Serializable {
        public double x;
        public double y;
        public Double() {
        }
        public Double(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

    }

    protected Point2D() {
    }
    public abstract double getX();
    public abstract double getY();

    public static double distance(double x1, double y1,
                                  double x2, double y2)
    {
        x1 -= x2;
        y1 -= y2;
        return Math.sqrt(x1 * x1 + y1 * y1);
    }

    public double distance(double px, double py) {
        px -= getX();
        py -= getY();
        return Math.sqrt(px * px + py * py);
    }
    public double distance(Point2D pt) {
        double px = pt.getX() - this.getX();
        double py = pt.getY() - this.getY();
        return Math.sqrt(px * px + py * py);
    }

    public int hashCode() {
        double sum = getX() + getY();
        return (int)sum * 37;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof Point2D) {
            Point2D p2d = (Point2D) obj;
            return (getX() == p2d.getX()) && (getY() == p2d.getY());
        }
        return super.equals(obj);
    }
}

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
 * * JDK Class Emulation for GWT
 */
public abstract class Line2D {
  public static class Float extends Line2D {
    public float x1;
    public float y1;
    public float x2;
    public float y2;

    public Float() {
    }

    public Float(float x1, float y1, float x2, float y2) {
      setLine(x1, y1, x2, y2);
    }

    public double getX1() {
      return (double) x1;
    }

    public double getY1() {
      return (double) y1;
    }

    public Point2D getP1() {
      return new Point2D.Float(x1, y1);
    }

    public double getX2() {
      return (double) x2;
    }

    public double getY2() {
      return (double) y2;
    }

    public Point2D getP2() {
      return new Point2D.Float(x2, y2);
    }

    public void setLine(float x1, float y1, float x2, float y2) {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
    }

  }

  public static class Double extends Line2D implements Serializable {
    public double x1;
    public double y1;
    public double x2;
    public double y2;

    public Double() {
    }

    public Double(double x1, double y1, double x2, double y2) {
      setLine(x1, y1, x2, y2);
    }

    public Double(Point2D p1, Point2D p2) {
      setLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public double getX1() {
      return x1;
    }

    public double getY1() {
      return y1;
    }

    public Point2D getP1() {
      return new Point2D.Double(x1, y1);
    }

    public double getX2() {
      return x2;
    }

    public double getY2() {
      return y2;
    }

    public Point2D getP2() {
      return new Point2D.Double(x2, y2);
    }

    public void setLine(double x1, double y1, double x2, double y2) {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
    }
  }

  public abstract double getX1();

  /**
   * Returns the Y coordinate of the start point in double precision.
   * 
   * @return the Y coordinate of the start point of this {@code Line2D} object.
   * @since 1.2
   */
  public abstract double getY1();

  /**
   * Returns the start <code>Point2D</code> of this <code>Line2D</code>.
   * 
   * @return the start <code>Point2D</code> of this <code>Line2D</code>.
   * @since 1.2
   */
  public abstract Point2D getP1();

  public abstract double getX2();

  public abstract double getY2();

  public static double ptSegDistSq(double x1, double y1, double x2, double y2, double px, double py) {
    // Adjust vectors relative to x1,y1
    // x2,y2 becomes relative vector from x1,y1 to end of segment
    x2 -= x1;
    y2 -= y1;
    // px,py becomes relative vector from x1,y1 to test point
    px -= x1;
    py -= y1;
    double dotprod = px * x2 + py * y2;
    double projlenSq;
    if (dotprod <= 0.0) {
      // px,py is on the side of x1,y1 away from x2,y2
      // distance to segment is length of px,py vector
      // "length of its (clipped) projection" is now 0.0
      projlenSq = 0.0;
    } else {
      // switch to backwards vectors relative to x2,y2
      // x2,y2 are already the negative of x1,y1=>x2,y2
      // to get px,py to be the negative of px,py=>x2,y2
      // the dot product of two negated vectors is the same
      // as the dot product of the two normal vectors
      px = x2 - px;
      py = y2 - py;
      dotprod = px * x2 + py * y2;
      if (dotprod <= 0.0) {
        // px,py is on the side of x2,y2 away from x1,y1
        // distance to segment is length of (backwards) px,py vector
        // "length of its (clipped) projection" is now 0.0
        projlenSq = 0.0;
      } else {
        // px,py is between x1,y1 and x2,y2
        // dotprod is the length of the px,py vector
        // projected on the x2,y2=>x1,y1 vector times the
        // length of the x2,y2=>x1,y1 vector
        projlenSq = dotprod * dotprod / (x2 * x2 + y2 * y2);
      }
    }
    // Distance to line is now the length of the relative point
    // vector minus the length of its projection onto the line
    // (which is zero if the projection falls outside the range
    // of the line segment).
    double lenSq = px * px + py * py - projlenSq;
    if (lenSq < 0) {
      lenSq = 0;
    }
    return lenSq;
  }

  public static double ptSegDist(double x1, double y1, double x2, double y2, double px, double py) {
    return Math.sqrt(ptSegDistSq(x1, y1, x2, y2, px, py));
  }

  public double ptSegDist(double px, double py) {
    return ptSegDist(getX1(), getY1(), getX2(), getY2(), px, py);
  }

}

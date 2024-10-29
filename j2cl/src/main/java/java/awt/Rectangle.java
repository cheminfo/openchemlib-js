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

import java.awt.geom.Rectangle2D;

public class Rectangle extends Rectangle2D {

  public int x;

  public int y;

  public int width;

  public int height;

  public Rectangle() {
    this(0, 0, 0, 0);
  }

  public Rectangle(Rectangle r) {
    this(r.x, r.y, r.width, r.height);
  }

  public Rectangle(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
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

  public void setRect(double x, double y, double width, double height) {
    this.x = (int) x;
    this.y = (int) y;
    this.width = (int) width;
    this.height = (int) height;
  }

  public Rectangle intersection(Rectangle r) {
    int currentX = this.x;
    int currentY = this.y;
    int otherX = r.x;
    int otherY = r.y;
    long newWidth = currentX;
    newWidth += this.width;
    long newHeight = currentY;
    newHeight += this.height;
    long otherTmpX = otherX;
    otherTmpX += r.width;
    long otherTmpY = otherY;
    otherTmpY += r.height;
    if (currentX < otherX)
      currentX = otherX;
    if (currentY < otherY)
      currentY = otherY;
    if (newWidth > otherTmpX)
      newWidth = otherTmpX;
    if (newHeight > otherTmpY)
      newHeight = otherTmpY;
    newWidth -= currentX;
    newHeight -= currentY;
    if (newWidth < Integer.MIN_VALUE)
      newWidth = Integer.MIN_VALUE;
    if (newHeight < Integer.MIN_VALUE)
      newHeight = Integer.MIN_VALUE;
    return new Rectangle(currentX, currentY, (int) newWidth, (int) newHeight);
  }

  public Rectangle union(Rectangle r) {
    long currrentWidth = this.width;
    long currentHeight = this.height;
    if ((currrentWidth | currentHeight) < 0) {
      return new Rectangle(r);
    }
    long otherWidth = r.width;
    long otherHeight = r.height;
    if ((otherWidth | otherHeight) < 0) {
      return new Rectangle(this);
    }
    int currentX = this.x;
    int currentY = this.y;
    currrentWidth += currentX;
    currentHeight += currentY;
    int otherX = r.x;
    int otherY = r.y;
    otherWidth += otherX;
    otherHeight += otherY;
    if (currentX > otherX)
      currentX = otherX;
    if (currentY > otherY)
      currentY = otherY;
    if (currrentWidth < otherWidth)
      currrentWidth = otherWidth;
    if (currentHeight < otherHeight)
      currentHeight = otherHeight;
    currrentWidth -= currentX;
    currentHeight -= currentY;
    if (currrentWidth > Integer.MAX_VALUE)
      currrentWidth = Integer.MAX_VALUE;
    if (currentHeight > Integer.MAX_VALUE)
      currentHeight = Integer.MAX_VALUE;
    return new Rectangle(currentX, currentY, (int) currrentWidth, (int) currentHeight);
  }

  public boolean isEmpty() {
    return (width <= 0) || (height <= 0);
  }

  public int outcode(double x, double y) {
    int out = 0;
    if (this.width <= 0) {
      out |= OUT_LEFT | OUT_RIGHT;
    } else if (x < this.x) {
      out |= OUT_LEFT;
    } else if (x > this.x + (double) this.width) {
      out |= OUT_RIGHT;
    }
    if (this.height <= 0) {
      out |= OUT_TOP | OUT_BOTTOM;
    } else if (y < this.y) {
      out |= OUT_TOP;
    } else if (y > this.y + (double) this.height) {
      out |= OUT_BOTTOM;
    }
    return out;
  }

  public Rectangle2D createIntersection(Rectangle2D r) {
    if (r instanceof Rectangle) {
      return intersection((Rectangle) r);
    }
    Rectangle2D dest = new Rectangle2D.Double();
    Rectangle2D.intersect(this, r, dest);
    return dest;
  }

  public Rectangle2D createUnion(Rectangle2D r) {
    if (r instanceof Rectangle) {
      return union((Rectangle) r);
    }
    Rectangle2D dest = new Rectangle2D.Double();
    Rectangle2D.union(this, r, dest);
    return dest;
  }
}

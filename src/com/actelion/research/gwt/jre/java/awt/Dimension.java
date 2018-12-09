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

import java.awt.geom.Dimension2D;
import java.beans.Transient;

/**
 * Emulation for GWT.
 */
public class Dimension extends Dimension2D implements java.io.Serializable {

  public int width;
  public int height;

  public Dimension() {
    this(0, 0);
  }

  public Dimension(Dimension d) {
    this(d.width, d.height);
  }

  public Dimension(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public double getWidth() {
    return width;
  }

  public double getHeight() {
    return height;
  }

  public void setSize(double width, double height) {
    this.width = (int) Math.ceil(width);
    this.height = (int) Math.ceil(height);
  }

  public Dimension getSize() {
    return new Dimension(width, height);
  }

  public void setSize(Dimension d) {
    setSize(d.width, d.height);
  }

  public void setSize(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj instanceof Dimension) {
      Dimension d = (Dimension) obj;
      return (width == d.width) && (height == d.height);
    }
    return false;
  }

  public int hashCode() {
    int sum = width + height;
    return sum * 37;
  }
}

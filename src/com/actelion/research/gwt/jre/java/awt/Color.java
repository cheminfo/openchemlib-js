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
/**
 *  * JDK Class Emulation for GWT
 */

public class Color {

    public final static Color white = new Color(255, 255, 255);
    public final static Color WHITE = white;
    public final static Color gray = new Color(128, 128, 128);
    public final static Color black = new Color(0, 0, 0);

    private int rgbValue;
    private float realRGBValues[] = null;
    private float alphaValue = 0.0f;


    public Color(int red, int green, int blue) {
        this(red, green, blue, 255);
    }

    public Color(int red, int green, int blue, int alpha) {
        rgbValue = ((alpha & 0xFF) << 24) | ((red & 0xFF) << 16) | ((green & 0xFF) << 8) | ((blue & 0xFF) << 0);
    }


    public Color(int rgb) {
        rgbValue = 0xff000000 | rgb;
    }


    public Color(float red, float green, float blue, float alpha) {
        this((int) (red * 255 + 0.5), (int) (green * 255 + 0.5), (int) (blue * 255 + 0.5), (int) (alpha * 255 + 0.5));
        realRGBValues = new float[3];
        realRGBValues[0] = red;
        realRGBValues[1] = green;
        realRGBValues[2] = blue;
        alphaValue = alpha;
    }

    public int getRed() {
        return (rgbValue >> 16) & 0xFF;
    }

    public int getGreen() {
        return (rgbValue >> 8) & 0xFF;
    }

    public int getBlue() {
        return (rgbValue >> 0) & 0xFF;
    }

    public int getAlpha() {
        return (rgbValue >> 24) & 0xff;
    }

    private static float[] RGBtoHSV(float r, float g, float b, float[] hsbvals) {
        float hue = 0, saturation = 0, brightness = 0;
        if (hsbvals == null) {
            hsbvals = new float[3];
        }

        float min, max, delta;
        min = Math.min(r, Math.min(g, b));
        max = Math.max(r, Math.max(g, b));
        brightness = max;
        delta = max - min;

        if (delta == 0) {
            brightness = min;
            hue = 0;
            hsbvals[0] = hue;
            hsbvals[1] = saturation;
            hsbvals[2] = brightness;
            return hsbvals;
        }

        if (max != 0)
            saturation = delta / max;
        else {
            saturation = 0;
            hue = 0;
            hsbvals[0] = hue ;
            hsbvals[1] = saturation;
            hsbvals[2] = brightness;
            return hsbvals;
        }
        if (r == max)
            hue = (g - b) / delta;
        else if (g == max)
            hue = 2 + (b - r) / delta;
        else
            hue = 4 + (r - g) / delta;
        hue *= 60;
        if (hue < 0)
            hue += 360;
        hsbvals[0] = hue / 360.0f;
        hsbvals[1] = saturation;
        hsbvals[2] = brightness;
        return hsbvals;
    }


    public static float[] RGBtoHSB(int r, int g, int b, float[] hsbvals) {
        return RGBtoHSV(r/255.0f,g/255.0f,b/255.0f,hsbvals);
    }

    public float[] getRGBComponents(float[] compArray) {
        float[] f;
        if (compArray == null) {
            f = new float[4];
        } else {
            f = compArray;
        }
        if (realRGBValues == null) {
            f[0] = ((float) getRed()) / 255f;
            f[1] = ((float) getGreen()) / 255f;
            f[2] = ((float) getBlue()) / 255f;
            f[3] = ((float) getAlpha()) / 255f;
        } else {
            f[0] = realRGBValues[0];
            f[1] = realRGBValues[1];
            f[2] = realRGBValues[2];
            f[3] = alphaValue;
        }
        return f;
    }


}

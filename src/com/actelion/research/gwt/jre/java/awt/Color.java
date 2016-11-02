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

public class Color {

    /**
     * The color white.  In the default sRGB space.
     */
    public final static Color white = new Color(255, 255, 255);

    /**
     * The color white.  In the default sRGB space.
     *
     * @since 1.4
     */
    public final static Color WHITE = white;


    /**
     * The color gray.  In the default sRGB space.
     */
    public final static Color gray = new Color(128, 128, 128);


    /**
     * The color black.  In the default sRGB space.
     */
    public final static Color black = new Color(0, 0, 0);


    /**
     * The color rgbValue.
     *
     * @serial
     * @see #getRGB
     */
    int rgbValue;
    private float frgbvalue[] = null;
    private float falpha = 0.0f;


    /**
     * Creates an opaque sRGB color with the specified red, green,
     * and blue values in the range (0 - 255).
     * The actual color used in rendering depends
     * on finding the best match given the color space
     * available for a given output device.
     * Alpha is defaulted to 255.
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>
     *                                  or <code>b</code> are outside of the range
     *                                  0 to 255, inclusive
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB
     */
    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    /**
     * Creates an sRGB color with the specified red, green, blue, and alpha
     * values in the range (0 - 255).
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     * @param a the alpha component
     * @throws IllegalArgumentException if <code>r</code>, <code>g</code>,
     *                                  <code>b</code> or <code>a</code> are outside of the range
     *                                  0 to 255, inclusive
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getAlpha
     * @see #getRGB
     */
    public Color(int r, int g, int b, int a) {
        rgbValue = ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                ((b & 0xFF) << 0);
    }


    public Color(int rgb) {
        rgbValue = 0xff000000 | rgb;
    }


    public Color(float r, float g, float b, float a) {
        this((int) (r * 255 + 0.5), (int) (g * 255 + 0.5), (int) (b * 255 + 0.5), (int) (a * 255 + 0.5));
        frgbvalue = new float[3];
        frgbvalue[0] = r;
        frgbvalue[1] = g;
        frgbvalue[2] = b;
        falpha = a;
    }

    /**
     * Returns the red component in the range 0-255 in the default sRGB
     * space.
     *
     * @return the red component.
     * @see #getRGB
     */
    public int getRed() {
        return (getRGB() >> 16) & 0xFF;
    }

    /**
     * Returns the green component in the range 0-255 in the default sRGB
     * space.
     *
     * @return the green component.
     * @see #getRGB
     */
    public int getGreen() {
        return (getRGB() >> 8) & 0xFF;
    }

    /**
     * Returns the blue component in the range 0-255 in the default sRGB
     * space.
     *
     * @return the blue component.
     * @see #getRGB
     */
    public int getBlue() {
        return (getRGB() >> 0) & 0xFF;
    }

    /**
     * Returns the alpha component in the range 0-255.
     *
     * @return the alpha component.
     * @see #getRGB
     */
    public int getAlpha() {
        return (getRGB() >> 24) & 0xff;
    }

    public int getRGB() {
        return rgbValue;
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

//        if (max < 0.01f)
//        System.out.printf("Max = %f\n",max);
        if (max != 0)
            saturation = delta / max;
        else {
            // r = g = b = 0
            saturation = 0;
            hue = 0;
            hsbvals[0] = hue ;/// 360.0f;
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
        if (frgbvalue == null) {
            f[0] = ((float) getRed()) / 255f;
            f[1] = ((float) getGreen()) / 255f;
            f[2] = ((float) getBlue()) / 255f;
            f[3] = ((float) getAlpha()) / 255f;
        } else {
            f[0] = frgbvalue[0];
            f[1] = frgbvalue[1];
            f[2] = frgbvalue[2];
            f[3] = falpha;
        }
        return f;
    }


}

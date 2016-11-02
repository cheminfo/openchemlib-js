package java.awt.image;

import java.awt.*;
/**
 *  * JDK Class Emulation for GWT
 */
/**
 * Created by rufenec on 20/03/15.
 */
public class BufferedImage
{
    public static final int TYPE_CUSTOM = 0;
    public static final int TYPE_INT_RGB = 1;
    public static final int TYPE_INT_ARGB = 2;
    public static final int TYPE_INT_ARGB_PRE = 3;

    public BufferedImage(int width, int height, int typeIntArgb)
    {

    }

    public Graphics2D createGraphics()
    {
        return new Graphics2D();
    }
}

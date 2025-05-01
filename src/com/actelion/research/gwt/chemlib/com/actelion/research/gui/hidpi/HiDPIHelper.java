package com.actelion.research.gui.hidpi;

import com.actelion.research.gui.LookAndFeelHelper;
import com.actelion.research.gui.generic.GenericImage;
import com.actelion.research.util.ColorHelper;

import java.awt.Color;

public class HiDPIHelper {
	private static float sUIScaleFactor = -1f;

	public static int scale(float value) {
		return Math.round(getUIScaleFactor() * value);
	}

	public static float getUIScaleFactor() {
		if (sUIScaleFactor == -1) {
			sUIScaleFactor = getDevicePixelRatio();
		}
		return sUIScaleFactor;
	}

	private static native float getDevicePixelRatio() /*-{
		return globalThis.devicePixelRatio || 1;
	}-*/;

	public static void disableImage(GenericImage image) {
		Color gray = ColorHelper.brighter(new Color(238, 238, 238), 0.8f);
		int grayRGB = 0x00FFFFFF & gray.getRGB();

		for (int x=0; x<image.getWidth(); x++) {
			for (int y=0; y<image.getHeight(); y++) {
				int argb = image.getRGB(x, y);
				image.setRGB(x, y, (0xFF000000 & argb) + grayRGB);
			}
		}
	}
}

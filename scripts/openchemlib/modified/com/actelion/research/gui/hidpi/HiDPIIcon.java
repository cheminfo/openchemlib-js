package com.actelion.research.gui.hidpi;

import com.actelion.research.gui.LookAndFeelHelper;
import com.actelion.research.gui.generic.GenericImage;
import com.actelion.research.gui.swing.SwingImage;
import com.actelion.research.util.Platform;

import javax.swing.*;
import java.awt.*;

public class HiDPIIcon {
	private static final int[] ICON_SPOT_COLOR = {   // original spot colors used in icon images (bright L&F)
			0x00503CB4, 0x00000000 };
	private static final int[] DARK_LAF_SPOT_COLOR = {   // default replacement spot colors for dark L&F)
			0x00B4A0FF, 0x00E0E0E0 };

	private static int[] sSpotColor = null;

	public static void adaptForLookAndFeel(GenericImage image) {
		if (sSpotColor != null)
			replaceSpotColors(image, sSpotColor);
		else if (LookAndFeelHelper.isDarkLookAndFeel())
			replaceSpotColors(image, DARK_LAF_SPOT_COLOR);
	}

	private static void replaceSpotColors(GenericImage image, int[] altRGB) {
		for (int x=0; x<image.getWidth(); x++) {
			for (int y=0; y<image.getHeight(); y++) {
				int argb = image.getRGB(x, y);
				int rgb = argb & 0x00FFFFFF;
				for (int i=0; i<ICON_SPOT_COLOR.length; i++) {
					if (rgb == ICON_SPOT_COLOR[i]) {
						image.setRGB(x, y, (0xFF000000 & argb) + altRGB[i]);
						break;
					}
				}
			}
		}
	}
}

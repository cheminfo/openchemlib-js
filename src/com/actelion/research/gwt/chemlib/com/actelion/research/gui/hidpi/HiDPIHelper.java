package com.actelion.research.gui.hidpi;

import com.actelion.research.gui.generic.GenericImage;

public class HiDPIHelper {
	public static int scale(float value) {
		return Math.round(getUIScaleFactor() * value);
	}

	public static float getUIScaleFactor() {
		return 1.0f;
	}

	public static void adaptForLookAndFeel(GenericImage image) {
		return;
	}

	public static void disableImage(GenericImage image) {
		return;
	}
}

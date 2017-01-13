package com.actelion.research.gwt.minimal;

import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.chem.prediction.ParameterizedStringList;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class Util {
	public static JavaScriptObject convertParameterizedStringList(ParameterizedStringList list) {
		int size = list.getSize();
		JsArray<JavaScriptObject> array = newJsArray(size);
		for (int i = 0; i < size; i++) {
			array.set(i, getParameterizedString(list.getStringAt(i), list.getStringTypeAt(i)));
		}
		return array;
	}

	public static native JsArray<JavaScriptObject> newJsArray(int length) /*-{
		return new Array(length);
	}-*/;

	private static native JavaScriptObject getParameterizedString(String string, int stringType) /*-{
		return {
			type: stringType,
			value: string
		};
	}-*/;

	public static native int getDisplayMode(JavaScriptObject options)/*-{
		if (!options) return 0;
		var displayMode = 0;

		if (options.inflateToMaxAVBL) displayMode |= @com.actelion.research.chem.AbstractDepictor::cModeInflateToMaxAVBL;
		if (options.inflateToHighResAVBL) displayMode |= @com.actelion.research.chem.AbstractDepictor::cModeInflateToHighResAVBL;
		if (options.chiralTextBelowMolecule) displayMode |= @com.actelion.research.chem.AbstractDepictor::cModeChiralTextBelowMolecule;
		if (options.chiralTextAboveMolecule) displayMode |= @com.actelion.research.chem.AbstractDepictor::cModeChiralTextAboveMolecule;
		if (options.chiralTextOnFrameTop) displayMode |= @com.actelion.research.chem.AbstractDepictor::cModeChiralTextOnFrameTop;
		if (options.chiralTextOnFrameBottom) displayMode |= @com.actelion.research.chem.AbstractDepictor::cModeChiralTextOnFrameBottom;

		if (options.noTabus) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeNoTabus;
		if (options.showAtomNumber) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeAtomNo;
		if (options.showBondNumber) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeBondNo;
		if (options.highlightQueryFeatures) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeHiliteAllQueryFeatures;
		if (options.showMapping) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeShowMapping;
		if (options.suppressChiralText) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeSuppressChiralText;
		if (options.suppressCIPParity) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeSuppressCIPParity;
		if (options.suppressESR) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeSuppressESR;

		if (options.showSymmetrySimple) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeShowSymmetrySimple;
		if (options.showSymmetryDiastereotopic) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeShowSymmetryDiastereotopic;
		if (options.showSymmetryEnantiotopic) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeShowSymmetryEnantiotopic;
		if (options.noImplicitAtomLabelColors) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeNoImplicitAtomLabelColors;
		if (options.noStereoProblem) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeNoStereoProblem;

		return displayMode;
	}-*/;
}

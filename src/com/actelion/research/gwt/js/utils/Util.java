package com.actelion.research.gwt.js.utils;

import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.chem.SSSearcherWithIndex;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.prediction.ParameterizedStringList;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import java.util.ArrayList;

public class Util {
  private static SSSearcherWithIndex searcher = null;

  public static int[] createSSSearcherIndex(StereoMolecule molecule) {
    if (searcher == null) {
      searcher = new SSSearcherWithIndex();
    }
    return searcher.createIndex(molecule);
  }

  public static JavaScriptObject convertParameterizedStringList(ParameterizedStringList list) {
    int size = list.getSize();
    JsArray<JavaScriptObject> array = newJsArray(size);
    for (int i = 0; i < size; i++) {
      array.set(i, getParameterizedString(list.getStringAt(i), list.getStringTypeAt(i)));
    }
    return array;
  }

  public static JavaScriptObject convertIntArrayArrayList(ArrayList<int[]> list) {
    int size = list.size();
    JsArray<JavaScriptObject> array = newJsArray(size);
    for (int i = 0; i < size; i++) {
      array.set(i, convertIntArray(list.get(i)));
    }
    return array;
  }

  public static native JavaScriptObject convertIntArray(int[] source)
  /*-{
    return Array.from(source);
  }-*/;

  public static native JsArray<JavaScriptObject> newJsArray(int length)
  /*-{
  	return new Array(length);
  }-*/;

  private static native JavaScriptObject getParameterizedString(String string, int stringType)
  /*-{
  	return {
  		type: stringType,
  		value: string
  	};
  }-*/;

  public static native int getDepictorViewMode(JavaScriptObject options)
    /*-{
        options = options || {};

    	var viewMode = 0;

        if (options.maxAVBL) {
          if (!Number.isInteger(options.maxAVBL) || options.maxAVBL < 0 || options.maxAVBL > 0xFFFF) {
            throw new Error('maxAVBL must be an integer between 0 and 65535');
          }
          viewMode |= options.maxAVBL;
        }

    	if (options.inflateToMaxAVBL === true || (options.inflateToMaxAVBL === undefined && options.inflateToHighResAVBL !== true)) viewMode |= @com.actelion.research.chem.AbstractDepictor::cModeInflateToMaxAVBL;
    	if (options.inflateToHighResAVBL) viewMode |= @com.actelion.research.chem.AbstractDepictor::cModeInflateToHighResAVBL;
    	if (options.chiralTextBelowMolecule !== false) viewMode |= @com.actelion.research.chem.AbstractDepictor::cModeChiralTextBelowMolecule;
    	if (options.chiralTextAboveMolecule) viewMode |= @com.actelion.research.chem.AbstractDepictor::cModeChiralTextAboveMolecule;
    	if (options.chiralTextOnFrameTop) viewMode |= @com.actelion.research.chem.AbstractDepictor::cModeChiralTextOnFrameTop;
    	if (options.chiralTextOnFrameBottom) viewMode |= @com.actelion.research.chem.AbstractDepictor::cModeChiralTextOnFrameBottom;

    	return viewMode;
    }-*/;

  public static native int getDepictorDisplayMode(JavaScriptObject options)
  /*-{
  	if (!options) return 0;
  	var displayMode = 0;

  	if (options.noTabus) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeNoTabus;
  	if (options.showAtomNumber) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeAtomNo;
  	if (options.showBondNumber) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeBondNo;
  	if (options.highlightQueryFeatures) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeHiliteAllQueryFeatures;
  	if (options.showMapping) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeShowMapping;
  	if (options.suppressChiralText) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeSuppressChiralText;
  	if (options.suppressCIPParity) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeSuppressCIPParity;
  	if (options.suppressESR) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeSuppressESR;
  	if (options.noCarbonLabelWithCustomLabel) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeNoCarbonLabelWithCustomLabel;
  
  	if (options.showSymmetryAny) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeShowSymmetryAny;
  	if (options.showSymmetrySimple) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeShowSymmetrySimple;
  	if (options.showSymmetryStereoHeterotopicity) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeShowSymmetryStereoHeterotopicity;
  	if (options.noImplicitAtomLabelColors) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeNoImplicitAtomLabelColors;
  	if (options.noStereoProblem) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeNoStereoProblem;
  	if (options.noColorOnESRAndCIP) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeNoColorOnESRAndCIP;
  	if (options.noImplicitHydrogen) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeNoImplicitHydrogen;
  	if (options.drawBondsInGray) displayMode |= @com.actelion.research.chem.AbstractDepictor::cDModeDrawBondsInGray;
  
  	return displayMode;
  }-*/;
}

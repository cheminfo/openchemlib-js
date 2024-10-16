package com.actelion.research.gwt.core;

import com.actelion.research.chem.reaction.Reaction;
import com.actelion.research.chem.reaction.ReactionEncoder;
import com.actelion.research.gwt.minimal.JSReaction;
import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

@JsType(name = "ReactionEncoder")
public class JSReactionEncoder {

  public static native String encode(JSReaction reaction, JavaScriptObject options) /*-{
    options ??= {keepAbsoluteCoordinates: false, mode: ReactionEncoder.COMBINED_MODES.INCLUDE_DEFAULT};
    options.keepAbsoluteCoordinates ??= false;
    options.keepAbsoluteCoordinates = Boolean(options.keepAbsoluteCoordinates);

    if ('sortByIDCode' in options) {
      options.sortByIDCode ??= false;
      options.sortByIDCode = Boolean(options.sortByIDCode);

      return @com.actelion.research.gwt.core.generic.JSReactionEncoder::encode(
        Lcom.actelion.research.gwt.minimal.JSReaction;ZZ
      )(reaction, options.keepAbsoluteCoordinates, options.sortByIDCode)
    }

    options.mode ??= ReactionEncoder.COMBINED_MODES.INCLUDE_DEFAULT;
    options.mode = Math.trunc(options.mode);
    return @com.actelion.research.gwt.core.generic.JSReactionEncoder::encode(
      Lcom.actelion.research.gwt.minimal.JSReaction;ZI
    )(reaction, options.keepAbsoluteCoordinates, options.mode);
  }-*/;

  private static String encode(JSReaction reaction, boolean keepAbsoluteCoordinates, boolean sortByIDCode) {
    return String.join(
            ReactionEncoder.OBJECT_DELIMITER_STRING,
            ReactionEncoder.encode(
                    reaction.getReaction(),
                    keepAbsoluteCoordinates,
                    sortByIDCode
            )
    );
  }

  private static String encode(JSReaction reaction, boolean keepAbsoluteCoordinates, int mode) {
    return ReactionEncoder.encode(
            reaction.getReaction(),
            keepAbsoluteCoordinates,
            mode
    );
  }

  public static native JSReaction decode(String reaction, JavaScriptObject options) /*-{
    options ??= {mode: ReactionEncoder.COMBINED_MODES.INCLUDE_DEFAULT};

    if ('ensureCoordinates' in options) {
      options.ensureCoordinates ??= false;
      options.ensureCoordinates = Boolean(options.ensureCoordinates);
      return decode(String s, boolean ensureCoordinates);
    }

    options.mode ??= ReactionEncoder.COMBINED_MODES.INCLUDE_DEFAULT;
    options.mode = Math.trunc(options.mode);
    return decode(String s, int includeOptions, Reaction rxn)
  }-*/;

  private static JSReaction decode(String strReaction, boolean ensureCoordinates) {
    Reaction reaction = ReactionEncoder.decode(strReaction, ensureCoordinates, null);
    if (reaction == null) return null;

    return new JSReaction(reaction);
  }

  private static JSReaction decode(String strReaction, int mode) {
    Reaction reaction = ReactionEncoder.decode(strReaction, mode, null);
    if (reaction == null) return null;

    return new JSReaction(reaction);
  }

//  private static class Modes extends JavaScriptObject {
//    public final int MAPPING = 0b0001;
//    public final int COORDS = 0b0010;
//    public final int DRAWING_OBJECTS = 0b0100;
//    public final int CATALYSTS = 0b1000;
//
//    protected Modes() {}
//
//    public native void init() /*-{
//      Object.assign(this, {
//        MAPPING: 0b0001,
//        COORDS: 0b0010,
//        DRAWING_OBJECTS: 0b0100,
//        CATALYSTS: 0b1000,
//      });
//    }-*/;
//  }
//
//  private static class CombinedModes extends JavaScriptObject {
//    public final int ALL = 0b1111;
//    public final int RXN_CODE_ONLY = 0b0000;
//    public final int DEFAULT = 0b0011; // MAPPING | COORDS
//
//    protected CombinedModes() {}
//
//    public native void init() /*-{
//      Object.assign(this, {
//        ALL: 0b1111,
//        RXN_CODE_ONLY: 0b0000,
//        DEFAULT: 0b0011,
//      });
//    }-*/;
//  }

//  static final Modes MODES;
//  static final CombinedModes COMBINED_MODES;
//
//  static {
//    MODES = (Modes) JavaScriptObject.createObject().cast();
//    MODES.init();
//
//    COMBINED_MODES = (CombinedModes) JavaScriptObject.createObject().cast();
//    COMBINED_MODES.init();
//  }

}

package com.actelion.research.gwt.core;

import com.actelion.research.chem.reaction.Reaction;
import com.actelion.research.chem.reaction.ReactionEncoder;
import com.actelion.research.gwt.minimal.JSReaction;
import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

@JsType(name = "ReactionEncoder")
public class JSReactionEncoder {
  public static final int INCLUDE_MAPPING = ReactionEncoder.INCLUDE_MAPPING;
  public static final int INCLUDE_COORDS = ReactionEncoder.INCLUDE_COORDS;
  public static final int INCLUDE_DRAWING_OBJECTS = ReactionEncoder.INCLUDE_DRAWING_OBJECTS;
  public static final int INCLUDE_CATALYSTS = ReactionEncoder.INCLUDE_CATALYSTS;
  public static final int RETAIN_REACTANT_AND_PRODUCT_ORDER = ReactionEncoder.RETAIN_REACTANT_AND_PRODUCT_ORDER;

  public static final int INCLUDE_ALL = ReactionEncoder.INCLUDE_ALL;
  public static final int INCLUDE_RXN_CODE_ONLY = ReactionEncoder.INCLUDE_RXN_CODE_ONLY;
  public static final int INCLUDE_DEFAULT = ReactionEncoder.INCLUDE_DEFAULT;

  public static final String MOLECULE_DELIMITER = ReactionEncoder.MOLECULE_DELIMITER_STRING;
  public static final String OBJECT_DELIMITER = ReactionEncoder.OBJECT_DELIMITER_STRING;
  public static final String PRODUCT_IDENTIFIER = String.valueOf(ReactionEncoder.PRODUCT_IDENTIFIER);
  public static final String CATALYST_DELIMITER = String.valueOf(ReactionEncoder.CATALYST_DELIMITER);

  public static native String encode(JSReaction reaction, JavaScriptObject options) /*-{
    if (!options) options = {keepAbsoluteCoordinates: false, mode: @com.actelion.research.gwt.core.JSReactionEncoder::INCLUDE_DEFAULT};
    options = Object.assign({}, options);
    options.keepAbsoluteCoordinates = Boolean(options.keepAbsoluteCoordinates);

    if ('sortByIDCode' in options) {
      options.sortByIDCode = Boolean(options.sortByIDCode);

      return @com.actelion.research.gwt.core.JSReactionEncoder::encodeSort(Lcom/actelion/research/gwt/minimal/JSReaction;ZZ)(reaction, options.keepAbsoluteCoordinates, options.sortByIDCode)
    }

    if (typeof options.mode !== 'number') options.mode = @com.actelion.research.gwt.core.JSReactionEncoder::INCLUDE_DEFAULT;
    options.mode = Math.trunc(options.mode);
    return @com.actelion.research.gwt.core.JSReactionEncoder::encodeMode(Lcom/actelion/research/gwt/minimal/JSReaction;ZI)(reaction, options.keepAbsoluteCoordinates, options.mode);
  }-*/;

  private static String encodeSort(JSReaction reaction, boolean keepAbsoluteCoordinates, boolean sortByIDCode) {
    return String.join(
            OBJECT_DELIMITER,
            ReactionEncoder.encode(
                    reaction.getReaction(),
                    keepAbsoluteCoordinates,
                    sortByIDCode
            )
    );
  }

  private static String encodeMode(JSReaction reaction, boolean keepAbsoluteCoordinates, int mode) {
    return ReactionEncoder.encode(
            reaction.getReaction(),
            keepAbsoluteCoordinates,
            mode
    );
  }

  public static native JSReaction decode(String reaction, JavaScriptObject options) /*-{
    if (!options) options = {mode: @com.actelion.research.gwt.core.JSReactionEncoder::INCLUDE_DEFAULT};
    options = Object.assign({}, options);

    if ('ensureCoordinates' in options) {
      options.ensureCoordinates = Boolean(options.ensureCoordinates);
      return @com.actelion.research.gwt.core.JSReactionEncoder::decodeCoords(Ljava/lang/String;Z)(reaction, options.ensureCoordinates);
    }

    if (typeof options.mode !== 'number') options.mode = @com.actelion.research.gwt.core.JSReactionEncoder::INCLUDE_DEFAULT;
    options.mode = Math.trunc(options.mode);
    return @com.actelion.research.gwt.core.JSReactionEncoder::decodeMode(Ljava/lang/String;I)(reaction, options.mode);
  }-*/;

  private static JSReaction decodeCoords(String strReaction, boolean ensureCoordinates) {
    Reaction reaction = ReactionEncoder.decode(strReaction, ensureCoordinates, null);
    if (reaction == null) return null;

    return new JSReaction(reaction);
  }

  private static JSReaction decodeMode(String strReaction, int mode) {
    Reaction reaction = ReactionEncoder.decode(strReaction, mode, null);
    if (reaction == null) return null;

    return new JSReaction(reaction);
  }

}

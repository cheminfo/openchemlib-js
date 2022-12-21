package com.actelion.research.gwt.core;

import com.actelion.research.chem.*;
import com.actelion.research.chem.io.*;
import com.actelion.research.chem.reaction.*;
import com.actelion.research.gwt.minimal.JSReaction;
import jsinterop.annotations.*;

@JsType(name = "ReactionEncoder")
public class JSReactionEncoder {

  public static String encode(JSReaction reaction) {
    return ReactionEncoder.encode(reaction.getReaction(), false, 0);
  }

  public static JSReaction decode(String s) {
    return new JSReaction(ReactionEncoder.decode(s, true, null));
  }

}

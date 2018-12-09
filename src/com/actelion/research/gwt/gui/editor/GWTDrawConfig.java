package com.actelion.research.gwt.gui.editor;

import com.actelion.research.share.gui.DrawConfig;

/**
 * Created by rufenec on 8/29/16.
 */
public class GWTDrawConfig extends DrawConfig {

  @Override
  public long getHighLightColor() {
    return createColor(.3, 0.4, 1, .4);
  }

  @Override
  public long getMapToolColor() {
    return createColor(1, 0, 0, 1);
  }

  @Override
  public long getSelectionColor() {
    return createColor(1, 0, 0, 1);
  }

  @Override
  public long getForegroundColor() {
    return createColor(0, 0, 0, 1);
  }

  @Override
  public long getBackgroundColor() {
    return createColor(1, 1, 1, 1);
  }

}

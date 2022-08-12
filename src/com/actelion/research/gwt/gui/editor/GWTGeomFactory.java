package com.actelion.research.gwt.gui.editor;

import com.actelion.research.chem.ChemistryHelper;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.gui.generic.GenericRectangle;
import com.actelion.research.gwt.gui.editor.actions.dialogs.AtomPropertiesDialog;
import com.actelion.research.gwt.gui.editor.actions.dialogs.AtomQueryFeaturesDialog;
import com.actelion.research.gwt.gui.editor.actions.dialogs.BondQueryFeaturesDialog;
import com.actelion.research.share.gui.DrawConfig;
import com.actelion.research.share.gui.editor.chem.IArrow;
import com.actelion.research.share.gui.editor.dialogs.IAtomPropertiesDialog;
import com.actelion.research.share.gui.editor.dialogs.IAtomQueryFeaturesDialog;
import com.actelion.research.share.gui.editor.dialogs.IBondQueryFeaturesDialog;
import com.actelion.research.share.gui.editor.geom.GeomFactory;
import com.actelion.research.share.gui.editor.io.IKeyCode;


/**
 * Created by rufenec on 25/11/15.
 */
public class GWTGeomFactory extends GeomFactory {
  public GWTGeomFactory(DrawConfig cfg) {
    super(cfg);
  }

  public IArrow createArrow(GenericRectangle r) {
    return null;
  }

  public IAtomQueryFeaturesDialog createAtomQueryFeatureDialog(StereoMolecule mol, int atom, boolean includeReactionHints) {
    return new AtomQueryFeaturesDialog(mol, atom, includeReactionHints);
  }

  public IBondQueryFeaturesDialog createBondFeaturesDialog(StereoMolecule mol, int bond) {
    return new BondQueryFeaturesDialog(mol, bond);
  }

  public IAtomPropertiesDialog createAtomPropertiesDialog(StereoMolecule m, int atom) {
    return new AtomPropertiesDialog(null, m, atom);
  }

  @Override
  public GenericRectangle getBoundingRect(StereoMolecule m) {
    return ChemistryHelper.getBoundingRect(m);
  }

  public IKeyCode getDeleteKey() {
    return ACTKeyCode.DELETE;
  }

  public IKeyCode getEscapeKey() {
    return ACTKeyCode.ESCAPE;
  }

  public IKeyCode getBackSpaceKey() {
    return ACTKeyCode.BACK_SPACE;
  }

  public IKeyCode getEnterKey() {
    return ACTKeyCode.ENTER;
  }
}

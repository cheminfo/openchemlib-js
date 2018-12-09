/*

Copyright (c) 2015-2017, cheminfo

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.
    * Neither the name of {{ project }} nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package com.actelion.research.gwt.gui.editor.actions.dialogs;

import com.actelion.research.chem.ExtendedMolecule;
import com.actelion.research.chem.Molecule;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.share.gui.editor.dialogs.IAtomQueryFeaturesDialog;
import com.actelion.research.share.gui.editor.dialogs.IBondQueryFeaturesDialog;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

/**
 * Project: User: rufenec Date: 7/4/2014 Time: 11:18 AM
 */
public class BondQueryFeaturesDialog extends TDialog implements IBondQueryFeaturesDialog {
  private static final String BUTTON_WIDTH = "75px";
  static final String CLIENT_WIDTH = "300px";
  static final String CLIENT_HEIGHT = "100px";
  private ExtendedMolecule mMol;
  private int mBond, mFirstSpanItem;
  private CheckBox mCBSingle, mCBDouble, mCBTriple, mCBDelocalized, mCBIsBridge, mCBMatchStereo;
  private ComboBox mComboBoxRing, mComboBoxRingSize, mComboBoxMinAtoms, mComboBoxMaxAtoms;

  public BondQueryFeaturesDialog(StereoMolecule mol, int bond) {
    super(null, "Query Bond Properties");
    mMol = mol;
    mBond = bond;
  }

  @Override
  protected void buildGUI(RootPanel root) {
    FlexTable grid = new FlexTable();

    Label land = new Label("and");
    Label latoms = new Label("atoms");

    Button cancel = new Button("Cancel");
    Button ok = new Button("OK");

    mCBSingle = new CheckBox("Single");
    mCBDouble = new CheckBox("Double");
    mCBTriple = new CheckBox("Triple");
    mCBDelocalized = new CheckBox("Delocalized");

    mComboBoxMaxAtoms = new ComboBox();
    mComboBoxRing = new ComboBox();
    mComboBoxMinAtoms = new ComboBox();
    mComboBoxRingSize = new ComboBox();

    mCBMatchStereo = new CheckBox("Match Stereo Configuration");
    mCBIsBridge = new CheckBox("Is atom bridge between");

    grid.setCellPadding(0);

    mComboBoxRing.addItem("any ring state");
    mComboBoxRing.addItem("is ring bond");
    mComboBoxRing.addItem("is chain bond");

    mComboBoxRingSize.addItem("any ring size");
    mComboBoxRingSize.addItem("is in 3-membered ring");
    mComboBoxRingSize.addItem("is in 4-membered ring");
    mComboBoxRingSize.addItem("is in 5-membered ring");
    mComboBoxRingSize.addItem("is in 6-membered ring");
    mComboBoxRingSize.addItem("is in 7-membered ring");

    cancel.setWidth(BUTTON_WIDTH);
    ok.setWidth(BUTTON_WIDTH);

    grid.setWidget(0, 0, mCBSingle);
    grid.setWidget(1, 0, mCBDouble);
    grid.setWidget(2, 0, mCBTriple);
    grid.setWidget(3, 0, mCBDelocalized);
    grid.setWidget(4, 0, mComboBoxRing);

    grid.getFlexCellFormatter().setColSpan(1, 2, 4);
    grid.setWidget(1, 2, mCBIsBridge);
    grid.setWidget(2, 2, mComboBoxMinAtoms);
    grid.setWidget(2, 3, land);
    grid.setWidget(2, 4, mComboBoxMaxAtoms);
    grid.setWidget(2, 5, latoms);

    grid.getFlexCellFormatter().setColSpan(4, 2, 4);
    grid.setWidget(4, 2, mComboBoxRingSize);
    grid.getFlexCellFormatter().setColSpan(5, 0, 4);
    grid.setWidget(5, 0, mCBMatchStereo);
    grid.setWidget(7, 4, cancel);
    grid.setWidget(7, 5, ok);
    grid.setWidth(CLIENT_WIDTH);
    grid.setHeight(CLIENT_HEIGHT);

    setWidget(grid);
    setWidth(CLIENT_WIDTH);
    setHeight(CLIENT_HEIGHT);

    int itemCount = (1 << Molecule.cBondQFBridgeMinBits);
    for (int i = 0; i < itemCount; i++) {
      mComboBoxMinAtoms.addItem(Integer.toString(i));
    }

    populateComboBoxMaxAtoms(0);
    setupButtonHandlers(cancel, ok);
    setupControlHandlers();

  }

  @Override
  protected void onInitialUpdate() {
    mCBMatchStereo.setEnabled((mMol.getBondQueryFeatures(mBond) & Molecule.cBondQFMatchStereo) != 0);
    mMol.ensureHelperArrays(Molecule.cHelperRings);
    setInitialStates();
  }

  private void setupControlHandlers() {
    mComboBoxMinAtoms.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        int minAtoms = mComboBoxMinAtoms.getSelectedIndex();
        if (mFirstSpanItem != minAtoms) {
          int maxAtoms = mFirstSpanItem + mComboBoxMaxAtoms.getSelectedIndex();
          int itemCount = populateComboBoxMaxAtoms(minAtoms);
          if (maxAtoms < minAtoms)
            mComboBoxMaxAtoms.setSelectedIndex(0);
          else if (maxAtoms < minAtoms + itemCount)
            mComboBoxMaxAtoms.setSelectedIndex(maxAtoms - minAtoms);
          else
            mComboBoxMaxAtoms.setSelectedIndex(itemCount - 1);

          mFirstSpanItem = minAtoms;
        }
      }
    });

    mCBIsBridge.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        enableItems();
      }
    });
  }

  private void setupButtonHandlers(Button cancel, Button ok) {
    ok.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        setQueryFeatures();
        onOK();
      }
    });

    cancel.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        onCancel();
      }
    });
  }

  private void setInitialStates() {
    int queryFeatures = mMol.getBondQueryFeatures(mBond);
    int bondType = (mMol.getBondType(mBond) == Molecule.cBondTypeDelocalized || mMol.isDelocalizedBond(mBond)) ? 0
        : mMol.getBondOrder(mBond);

    if ((queryFeatures & Molecule.cBondQFSingle) != 0 || bondType == 1)
      mCBSingle.setValue(true);
    if ((queryFeatures & Molecule.cBondQFDouble) != 0 || bondType == 2)
      mCBDouble.setValue(true);
    if ((queryFeatures & Molecule.cBondQFTriple) != 0 || bondType == 3)
      mCBTriple.setValue(true);
    if ((queryFeatures & Molecule.cBondQFDelocalized) != 0 || bondType == 0)
      mCBDelocalized.setValue(true);
    if ((queryFeatures & Molecule.cBondQFMatchStereo) != 0)
      mCBMatchStereo.setValue(true);

    mComboBoxRing.setSelectedIndex(0);
    if ((queryFeatures & Molecule.cBondQFRing) != 0)
      mComboBoxRing.setSelectedIndex(1);
    else if ((queryFeatures & Molecule.cBondQFNotRing) != 0)
      mComboBoxRing.setSelectedIndex(2);

    int ringSize = (queryFeatures & Molecule.cBondQFRingSize) >> Molecule.cBondQFRingSizeShift;
    mComboBoxRingSize.setSelectedIndex((ringSize == 0) ? 0 : ringSize - 2);

    mComboBoxMinAtoms.setSelectedIndex(0);
    mComboBoxMaxAtoms.setSelectedIndex(0);
    if ((queryFeatures & Molecule.cBondQFBridge) != 0) {
      mCBIsBridge.setValue(true);
      int minAtoms = (queryFeatures & Molecule.cBondQFBridgeMin) >> Molecule.cBondQFBridgeMinShift;
      int atomSpan = (queryFeatures & Molecule.cBondQFBridgeSpan) >> Molecule.cBondQFBridgeSpanShift;
      mComboBoxMinAtoms.setSelectedIndex(minAtoms);
      populateComboBoxMaxAtoms(minAtoms);
      mComboBoxMaxAtoms.setSelectedIndex(atomSpan);
    }

    enableItems();
  }

  private int populateComboBoxMaxAtoms(int minAtoms) {
    mComboBoxMaxAtoms.clear();
    ;
    int itemCount = (1 << Molecule.cBondQFBridgeSpanBits);
    for (int i = 0; i < itemCount; i++) {
      mComboBoxMaxAtoms.addItem("" + (minAtoms + i));
    }
    return itemCount;
  }

  private void enableItems() {
    boolean bridgeIsSelected = mCBIsBridge.getValue();
    mCBSingle.setEnabled(!bridgeIsSelected);
    mCBDouble.setEnabled(!bridgeIsSelected);
    mCBTriple.setEnabled(!bridgeIsSelected);
    mCBDelocalized.setEnabled(!bridgeIsSelected);
    mCBMatchStereo.setEnabled(!(bridgeIsSelected && mMol.getBondOrder(mBond) == 2 // exclude BINAP-type stereo bonds
                                                                                  // for now
        && mMol.getBondParity(mBond) != Molecule.cBondParityNone
        && mMol.getBondParity(mBond) != Molecule.cBondParityUnknown));
    mComboBoxRing.setEnabled(!bridgeIsSelected);
    mComboBoxRingSize.setEnabled(!bridgeIsSelected);
    mComboBoxMinAtoms.setEnabled(bridgeIsSelected);
    mComboBoxMaxAtoms.setEnabled(bridgeIsSelected);
  }

  private void setQueryFeatures() {
    if (isSelectedBond(mBond)) {
      for (int bond = 0; bond < mMol.getAllBonds(); bond++) {
        if (isSelectedBond(bond))
          setQueryFeatures(bond);
      }
    } else {
      setQueryFeatures(mBond);
    }
  }

  private void setQueryFeatures(int bond) {
    int queryFeatures = 0;

    if (mCBIsBridge.getValue()) {
      int minAtoms = mComboBoxMinAtoms.getSelectedIndex();
      int atomSpan = mComboBoxMaxAtoms.getSelectedIndex();
      queryFeatures |= (minAtoms << Molecule.cBondQFBridgeMinShift);
      queryFeatures |= (atomSpan << Molecule.cBondQFBridgeSpanShift);
      queryFeatures &= ~Molecule.cBondQFBondTypes;
    } else {
      int bondType = -1;
      if (mCBSingle.getValue()) {
        mMol.setBondType(bond, Molecule.cBondTypeSingle);
        bondType = 1;
      } else if (mCBDouble.getValue()) {
        mMol.setBondType(bond, Molecule.cBondTypeDouble);
        bondType = 2;
      } else if (mCBTriple.getValue()) {
        mMol.setBondType(bond, Molecule.cBondTypeTriple);
        bondType = 3;
      } else if (mCBDelocalized.getValue()) {
        if (!mMol.isDelocalizedBond(bond))
          mMol.setBondType(bond, Molecule.cBondTypeDelocalized);
        bondType = 0;
      }

      if (mCBSingle.getValue() && bondType != 1)
        queryFeatures |= Molecule.cBondQFSingle;
      if (mCBDouble.getValue() && bondType != 2)
        queryFeatures |= Molecule.cBondQFDouble;
      if (mCBTriple.getValue() && bondType != 3)
        queryFeatures |= Molecule.cBondQFTriple;
      if (mCBDelocalized.getValue() && bondType != 0)
        queryFeatures |= Molecule.cBondQFDelocalized;
      if (mCBMatchStereo.getValue())
        queryFeatures |= Molecule.cBondQFMatchStereo;

      if (!mMol.isRingBond(bond)) {
        if (mComboBoxRing.getSelectedIndex() == 1)
          queryFeatures |= Molecule.cBondQFRing;
        else if (mComboBoxRing.getSelectedIndex() == 2)
          queryFeatures |= Molecule.cBondQFNotRing;
      }
    }

    if (mComboBoxRingSize.getSelectedIndex() != 0)
      queryFeatures |= ((mComboBoxRingSize.getSelectedIndex() + 2) << Molecule.cBondQFRingSizeShift);

    mMol.setBondQueryFeature(bond, Molecule.cBondQFAllFeatures, false);
    mMol.setBondQueryFeature(bond, queryFeatures, true);
  }

  private boolean isSelectedBond(int bond) {
    return mMol.isSelectedAtom(mMol.getBondAtom(0, bond)) && mMol.isSelectedAtom(mMol.getBondAtom(1, bond));
  }
}

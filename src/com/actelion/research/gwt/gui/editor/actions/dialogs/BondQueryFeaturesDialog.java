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
  static final String CLIENT_WIDTH = "350px";
  static final String CLIENT_HEIGHT = "100px";
  private ExtendedMolecule mMol;
  private int mBond, mFirstSpanItem;
  private CheckBox mCBSingle, mCBDouble, mCBTriple, mCBDelocalized, mCBMetalLigand, mCBIsBridge, mCBMatchStereo;
  private ComboBox mComboBoxRing, mComboBoxRingSize, mComboBoxMinAtoms, mComboBoxMaxAtoms;

  public BondQueryFeaturesDialog(StereoMolecule mol, int bond) {
    super(null,
        (mol.isSelectedAtom(mol.getBondAtom(0, bond)) && mol.isSelectedAtom(mol.getBondAtom(1, bond)))
            ? "Multiple Bond Properties"
            : "Bond Properties");
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
    mCBMetalLigand = new CheckBox("Coordinate (0-order)");

    mComboBoxRing = new ComboBox();
    mComboBoxRing.addItem("any ring state");
    mComboBoxRing.addItem("is not in a ring");
    mComboBoxRing.addItem("is any ring bond");
    mComboBoxRing.addItem("is non-aromatic ring bond");
    mComboBoxRing.addItem("is aromatic bond");

    mComboBoxRingSize = new ComboBox();
    mComboBoxRingSize.addItem("any ring size");
    mComboBoxRingSize.addItem("is in 3-membered ring");
    mComboBoxRingSize.addItem("is in 4-membered ring");
    mComboBoxRingSize.addItem("is in 5-membered ring");
    mComboBoxRingSize.addItem("is in 6-membered ring");
    mComboBoxRingSize.addItem("is in 7-membered ring");

    mCBMatchStereo = new CheckBox("Match Stereo Configuration");
    mCBIsBridge = new CheckBox("Is atom bridge between");

    mComboBoxMinAtoms = new ComboBox();
    int itemCount = (1 << Molecule.cBondQFBridgeMinBits);
    for (int i = 0; i < itemCount; i++) {
      mComboBoxMinAtoms.addItem(Integer.toString(i));
    }

    mComboBoxMaxAtoms = new ComboBox();
    populateComboBoxMaxAtoms(0);

    grid.setCellPadding(0);

    cancel.setWidth(BUTTON_WIDTH);
    ok.setWidth(BUTTON_WIDTH);

    grid.setWidget(0, 0, mCBSingle);
    grid.setWidget(1, 0, mCBDouble);
    grid.setWidget(2, 0, mCBTriple);
    grid.setWidget(3, 0, mCBDelocalized);
    grid.setWidget(4, 0, mCBMetalLigand);
    grid.setWidget(5, 0, mComboBoxRing);

    grid.getFlexCellFormatter().setColSpan(1, 2, 4);
    grid.setWidget(1, 2, mCBIsBridge);
    grid.setWidget(2, 2, mComboBoxMinAtoms);
    grid.setWidget(2, 3, land);
    grid.setWidget(2, 4, mComboBoxMaxAtoms);
    grid.setWidget(2, 5, latoms);

    grid.getFlexCellFormatter().setColSpan(5, 2, 4);
    grid.setWidget(5, 2, mComboBoxRingSize);
    grid.getFlexCellFormatter().setColSpan(6, 0, 4);
    grid.setWidget(6, 0, mCBMatchStereo);
    grid.setWidget(7, 4, cancel);
    grid.setWidget(7, 5, ok);
    grid.setWidth(CLIENT_WIDTH);
    grid.setHeight(CLIENT_HEIGHT);

    setWidget(grid);
    setWidth(CLIENT_WIDTH);
    setHeight(CLIENT_HEIGHT);

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
    int bondOrder = (mMol.getBondType(mBond) == Molecule.cBondTypeDelocalized || mMol.isDelocalizedBond(mBond)) ? 4
        : mMol.getBondOrder(mBond);

    if ((queryFeatures & Molecule.cBondQFSingle) != 0 || bondOrder == 1)
      mCBSingle.setValue(true);
    if ((queryFeatures & Molecule.cBondQFDouble) != 0 || bondOrder == 2)
      mCBDouble.setValue(true);
    if ((queryFeatures & Molecule.cBondQFTriple) != 0 || bondOrder == 3)
      mCBTriple.setValue(true);
    if ((queryFeatures & Molecule.cBondQFDelocalized) != 0 || bondOrder == 4)
      mCBDelocalized.setValue(true);
    if ((queryFeatures & Molecule.cBondQFMetalLigand) != 0 || bondOrder == 0)
      mCBMetalLigand.setValue(true);
    if ((queryFeatures & Molecule.cBondQFMatchStereo) != 0)
      mCBMatchStereo.setValue(true);

    mComboBoxRing.setSelectedIndex(0);

    int ringState = queryFeatures & Molecule.cBondQFRingState;
    int aromState = queryFeatures & Molecule.cBondQFAromState;
    if (ringState == Molecule.cBondQFNotRing)
      mComboBoxRing.setSelectedIndex(1);
    else if (aromState == Molecule.cBondQFAromatic)
      mComboBoxRing.setSelectedIndex(4);
    else if (ringState == Molecule.cBondQFRing) {
      if (aromState == 0)
        mComboBoxRing.setSelectedIndex(2);
      else if (aromState == Molecule.cBondQFNotAromatic)
        mComboBoxRing.setSelectedIndex(3);
    }

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
    int itemCount = (1 << Molecule.cBondQFBridgeSpanBits);
    for (int i = 0; i < itemCount; i++)
      mComboBoxMaxAtoms.addItem("" + (minAtoms + i));
    return itemCount;
  }

  private void enableItems() {
    boolean bridgeIsSelected = mCBIsBridge.getValue();
    mCBSingle.setEnabled(!bridgeIsSelected);
    mCBDouble.setEnabled(!bridgeIsSelected);
    mCBTriple.setEnabled(!bridgeIsSelected);
    mCBDelocalized.setEnabled(!bridgeIsSelected);
    mCBMetalLigand.setEnabled(!bridgeIsSelected);
    mCBMatchStereo.setEnabled(!bridgeIsSelected && mMol.getBondOrder(mBond) == 2 // exclude BINAP-type stereo bonds for
                                                                                 // now
        && mMol.getBondParity(mBond) != Molecule.cBondParityNone
        && mMol.getBondParity(mBond) != Molecule.cBondParityUnknown);
    mComboBoxRing.setEnabled(!bridgeIsSelected);
    mComboBoxRingSize.setEnabled(!bridgeIsSelected && mComboBoxRing.getSelectedIndex() != 1);
    mComboBoxMinAtoms.setEnabled(bridgeIsSelected);
    mComboBoxMaxAtoms.setEnabled(bridgeIsSelected);
  }

  private void setQueryFeatures() {
    if (isSelectedBond(mBond)) {
      for (int bond = 0; bond < mMol.getAllBonds(); bond++)
        if (isSelectedBond(bond))
          setQueryFeatures(bond);
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
      int bondOrder = -1;
      if (mCBSingle.getValue()) {
        mMol.setBondType(bond, Molecule.cBondTypeSingle);
        bondOrder = 1;
      } else if (mCBDouble.getValue()) {
        mMol.setBondType(bond, Molecule.cBondTypeDouble);
        bondOrder = 2;
      } else if (mCBTriple.getValue()) {
        mMol.setBondType(bond, Molecule.cBondTypeTriple);
        bondOrder = 3;
      } else if (mCBDelocalized.getValue()) {
        if (!mMol.isDelocalizedBond(bond))
          mMol.setBondType(bond, Molecule.cBondTypeDelocalized);
        bondOrder = 4;
      } else if (mCBMetalLigand.getValue()) {
        mMol.setBondType(bond, Molecule.cBondTypeMetalLigand);
        bondOrder = 0;
      }

      if (mCBSingle.getValue() && bondOrder != 1)
        queryFeatures |= Molecule.cBondQFSingle;
      if (mCBDouble.getValue() && bondOrder != 2)
        queryFeatures |= Molecule.cBondQFDouble;
      if (mCBTriple.getValue() && bondOrder != 3)
        queryFeatures |= Molecule.cBondQFTriple;
      if (mCBDelocalized.getValue() && bondOrder != 4)
        queryFeatures |= Molecule.cBondQFDelocalized;
      if (mCBMetalLigand.getValue() && bondOrder != 0)
        queryFeatures |= Molecule.cBondQFMetalLigand;
      if (mCBMatchStereo.getValue())
        queryFeatures |= Molecule.cBondQFMatchStereo;

      if (mComboBoxRing.getSelectedIndex() != 0) {
        if (mComboBoxRing.getSelectedIndex() == 1) {
          if (!mMol.isRingBond(bond))
            queryFeatures |= Molecule.cBondQFNotRing;
        } else if (mComboBoxRing.getSelectedIndex() == 2) {
          if (!mMol.isRingBond(bond))
            queryFeatures |= Molecule.cBondQFRing;
        } else if (mComboBoxRing.getSelectedIndex() == 3) {
          if (!mMol.isAromaticBond(bond))
            queryFeatures |= Molecule.cBondQFNotAromatic | Molecule.cBondQFRing;
        } else if (mComboBoxRing.getSelectedIndex() == 4) {
          if (!mMol.isAromaticBond(bond))
            queryFeatures |= Molecule.cBondQFAromatic;
        }
      }
    }

    if (mComboBoxRingSize.getSelectedIndex() != 0) {
      int ringSize = mComboBoxRingSize.getSelectedIndex() + 2;
      int implicitSize = mMol.getBondRingSize(bond);
      if (ringSize != implicitSize)
        queryFeatures |= (ringSize << Molecule.cBondQFRingSizeShift);
    }

    mMol.setBondQueryFeature(bond, Molecule.cBondQFAllFeatures, false);
    mMol.setBondQueryFeature(bond, queryFeatures, true);
  }

  private boolean isSelectedBond(int bond) {
    return mMol.isSelectedAtom(mMol.getBondAtom(0, bond)) && mMol.isSelectedAtom(mMol.getBondAtom(1, bond));
  }
}

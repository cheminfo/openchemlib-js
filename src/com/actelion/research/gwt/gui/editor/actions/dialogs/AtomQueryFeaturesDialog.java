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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.*;

import java.util.Arrays;

/**
 * Project: User: rufenec Date: 7/4/2014 Time: 10:50 AM
 */
public class AtomQueryFeaturesDialog extends TDialog implements IAtomQueryFeaturesDialog {
  private static final String CB_WIDTH = "200px";
  private ExtendedMolecule mMol;
  private int mAtom;

  private CheckBox mCBAny, mCBBlocked, mCBSubstituted, mCBMatchStereo, mCBExcludeGroup;
  private ComboBox mChoiceArom, mChoiceRingState, mChoiceRingSize, mChoiceCharge, mChoiceNeighbours, mChoiceHydrogen,
      mChoicePi;
  private TextBox mTFAtomList;
  private Label mLabelAtomList;
  private Button cancel, ok;

  public AtomQueryFeaturesDialog(StereoMolecule mol, int atom) {
    super(null, (mol.isSelectedAtom(atom)) ? "Multiple Atom Properties" : "Atom Properties");
    mMol = mol;
    mAtom = atom;
  }

  protected void buildGUI(RootPanel root) {

    Grid grid = new Grid(15, 2);
    grid.setCellPadding(0);

    HorizontalPanel panel = new HorizontalPanel();

    mCBAny = new CheckBox("any atomic number");
    grid.setWidget(0, 0, mCBAny);

    mLabelAtomList = new Label("allowed atoms:");
    mLabelAtomList.setWidth("100px");
    mTFAtomList = new TextBox();
    mTFAtomList.setWidth("80px");

    panel.setWidth(CB_WIDTH);
    panel.add(mLabelAtomList);
    panel.add(mTFAtomList);
    grid.setWidget(2, 0, panel);

    mChoiceArom = new ComboBox();
    mChoiceArom.setWidth(CB_WIDTH);
    mChoiceArom.addItem("any aromatic state");
    mChoiceArom.addItem("is aromatic");
    mChoiceArom.addItem("is not aromatic");
    grid.setWidget(3, 0, mChoiceArom);

    mChoiceRingState = new ComboBox();
    mChoiceRingState.setWidth(CB_WIDTH);
    mChoiceRingState.addItem("any ring state");
    mChoiceRingState.addItem("is chain atom");
    mChoiceRingState.addItem("is any ring atom");
    mChoiceRingState.addItem("has 2 ring bonds");
    mChoiceRingState.addItem("has 3 ring bonds");
    mChoiceRingState.addItem("has >3 ring bonds");
    grid.setWidget(4, 0, mChoiceRingState);

    mChoiceRingSize = new ComboBox();
    mChoiceRingSize.setWidth(CB_WIDTH);
    mChoiceRingSize.addItem("any ring size");
    mChoiceRingSize.addItem("is in 3-membered ring");
    mChoiceRingSize.addItem("is in 4-membered ring");
    mChoiceRingSize.addItem("is in 5-membered ring");
    mChoiceRingSize.addItem("is in 6-membered ring");
    mChoiceRingSize.addItem("is in 7-membered ring");
    grid.setWidget(5, 0, mChoiceRingSize);

    mChoiceCharge = new ComboBox();
    mChoiceCharge.setWidth(CB_WIDTH);
    mChoiceCharge.addItem("any atom charge");
    mChoiceCharge.addItem("has no charge");
    mChoiceCharge.addItem("has negative charge");
    mChoiceCharge.addItem("has positive charge");
    grid.setWidget(6, 0, mChoiceCharge);

    mChoiceNeighbours = new ComboBox();
    mChoiceNeighbours.setWidth(CB_WIDTH);
    mChoiceNeighbours.addItem("any non-H neighbour count");
    mChoiceNeighbours.addItem("has exactly 1 neighbour");
    mChoiceNeighbours.addItem("has exactly 2 neighbours");
    mChoiceNeighbours.addItem("has exactly 3 neighbours");
    mChoiceNeighbours.addItem("has less than 3 neighbours");
    mChoiceNeighbours.addItem("has less than 4 neighbours");
    mChoiceNeighbours.addItem("has more than 1 neighbour");
    mChoiceNeighbours.addItem("has more than 2 neighbours");
    mChoiceNeighbours.addItem("has more than 3 neighbours");
    grid.setWidget(7, 0, mChoiceNeighbours);

    mChoiceHydrogen = new ComboBox();
    mChoiceHydrogen.setWidth(CB_WIDTH);
    mChoiceHydrogen.addItem("any hydrogen count");
    mChoiceHydrogen.addItem("no hydrogen");
    mChoiceHydrogen.addItem("exactly 1 hydrogen");
    mChoiceHydrogen.addItem("exactly 2 hydrogens");
    mChoiceHydrogen.addItem("at least 1 hydrogen");
    mChoiceHydrogen.addItem("at least 2 hydrogens");
    mChoiceHydrogen.addItem("at least 3 hydrogens");
    mChoiceHydrogen.addItem("less than 2 hydrogens");
    mChoiceHydrogen.addItem("less than 3 hydrogens");
    grid.setWidget(8, 0, mChoiceHydrogen);

    mChoicePi = new ComboBox();
    mChoicePi.setWidth(CB_WIDTH);
    mChoicePi.addItem("any pi electron count");
    mChoicePi.addItem("no pi electrons");
    mChoicePi.addItem("exactly 1 pi electron");
    mChoicePi.addItem("exactly 2 pi electrons");
    mChoicePi.addItem("at least 1 pi electron");
    grid.setWidget(9, 0, mChoicePi);

    mCBBlocked = new CheckBox("prohibit further substitution");
    grid.setWidget(10, 0, mCBBlocked);

    mCBSubstituted = new CheckBox("require further substitution");
    grid.setWidget(11, 0, mCBSubstituted);

    mCBMatchStereo = new CheckBox("match stereo center");
    grid.setWidget(12, 0, mCBMatchStereo);

    mCBExcludeGroup = new CheckBox("is part of exclude group");
    grid.setWidget(13, 0, mCBExcludeGroup);

    HorizontalPanel buttonPanel = new HorizontalPanel();
    buttonPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);

    cancel = new Button("Cancel");
    ok = new Button("OK");
    buttonPanel.add(cancel);
    buttonPanel.add(ok);
    grid.setWidget(14, 1, buttonPanel);

    grid.setWidth("300px");
    grid.setHeight("200px");

    setWidget(grid);
    setWidth("300px");
    setHeight("200px");

    setupHandlers();
  }

  private void setupHandlers() {
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
    mCBAny.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        if (event.getValue())
          mLabelAtomList.setText("excluded atoms:");
        else
          mLabelAtomList.setText("allowed atoms:");
      }
    });

    mCBBlocked.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
          mCBSubstituted.setValue(false);
          mChoiceNeighbours.setSelectedIndex(0);
        }
      }
    });

    mCBSubstituted.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
      @Override
      public void onValueChange(ValueChangeEvent<Boolean> event) {
        mCBBlocked.setValue(false);

      }
    });
  }

  protected void onInitialUpdate() {
    setInitialStates();
  }

  private void setInitialStates() {
    int queryFeatures = mMol.getAtomQueryFeatures(mAtom);

    if ((queryFeatures & Molecule.cAtomQFAny) != 0) {
      mCBAny.setValue(true);
      mLabelAtomList.setText("excluded atoms:");
    } else
      mLabelAtomList.setText("allowed atoms:");

    mTFAtomList.setText(mMol.getAtomList(mAtom) == null ? "" : mMol.getAtomListString(mAtom));

    if ((queryFeatures & Molecule.cAtomQFAromatic) != 0)
      mChoiceArom.setSelectedIndex(1);
    else if ((queryFeatures & Molecule.cAtomQFNotAromatic) != 0)
      mChoiceArom.setSelectedIndex(2);

    int ringState = queryFeatures & Molecule.cAtomQFRingState;
    switch (ringState) {
    case Molecule.cAtomQFNot2RingBonds | Molecule.cAtomQFNot3RingBonds | Molecule.cAtomQFNot4RingBonds:
      mChoiceRingState.setSelectedIndex(1);
      break;
    case Molecule.cAtomQFNotChain:
      mChoiceRingState.setSelectedIndex(2);
      break;
    case Molecule.cAtomQFNotChain | Molecule.cAtomQFNot3RingBonds | Molecule.cAtomQFNot4RingBonds:
      mChoiceRingState.setSelectedIndex(3);
      break;
    case Molecule.cAtomQFNotChain | Molecule.cAtomQFNot2RingBonds | Molecule.cAtomQFNot4RingBonds:
      mChoiceRingState.setSelectedIndex(4);
      break;
    case Molecule.cAtomQFNotChain | Molecule.cAtomQFNot2RingBonds | Molecule.cAtomQFNot3RingBonds:
      mChoiceRingState.setSelectedIndex(5);
      break;
    }

    int ringSize = (queryFeatures & Molecule.cAtomQFRingSize) >> Molecule.cAtomQFRingSizeShift;
    mChoiceRingSize.setSelectedIndex((ringSize == 0) ? 0 : ringSize - 2);

    int neighbourFeatures = queryFeatures & Molecule.cAtomQFNeighbours;
    switch (neighbourFeatures) {
    case Molecule.cAtomQFNeighbours & ~Molecule.cAtomQFNot1Neighbour:
      mChoiceNeighbours.setSelectedIndex(1);
      break;
    case Molecule.cAtomQFNeighbours & ~Molecule.cAtomQFNot2Neighbours:
      mChoiceNeighbours.setSelectedIndex(2);
      break;
    case Molecule.cAtomQFNeighbours & ~Molecule.cAtomQFNot3Neighbours:
      mChoiceNeighbours.setSelectedIndex(3);
      break;
    case Molecule.cAtomQFNot3Neighbours | Molecule.cAtomQFNot4Neighbours:
      mChoiceNeighbours.setSelectedIndex(4);
      break;
    case Molecule.cAtomQFNot4Neighbours:
      mChoiceNeighbours.setSelectedIndex(5);
      break;
    case Molecule.cAtomQFNot0Neighbours | Molecule.cAtomQFNot1Neighbour:
      mChoiceNeighbours.setSelectedIndex(6);
      break;
    case Molecule.cAtomQFNot0Neighbours | Molecule.cAtomQFNot1Neighbour | Molecule.cAtomQFNot2Neighbours:
      mChoiceNeighbours.setSelectedIndex(7);
      break;
    case Molecule.cAtomQFNeighbours & ~Molecule.cAtomQFNot4Neighbours:
      mChoiceNeighbours.setSelectedIndex(8);
      break;
    }

    int chargeFeatures = queryFeatures & Molecule.cAtomQFCharge;
    switch (chargeFeatures) {
    case Molecule.cAtomQFNotChargeNeg | Molecule.cAtomQFNotChargePos:
      mChoiceCharge.setSelectedIndex(1);
      break;
    case Molecule.cAtomQFNotCharge0 | Molecule.cAtomQFNotChargePos:
      mChoiceCharge.setSelectedIndex(2);
      break;
    case Molecule.cAtomQFNotCharge0 | Molecule.cAtomQFNotChargeNeg:
      mChoiceCharge.setSelectedIndex(3);
      break;
    }

    int hydrogenFeatures = queryFeatures & Molecule.cAtomQFHydrogen;
    switch (hydrogenFeatures) {
    case Molecule.cAtomQFNot1Hydrogen | Molecule.cAtomQFNot2Hydrogen | Molecule.cAtomQFNot3Hydrogen:
      mChoiceHydrogen.setSelectedIndex(1);
      break;
    case Molecule.cAtomQFNot0Hydrogen | Molecule.cAtomQFNot2Hydrogen | Molecule.cAtomQFNot3Hydrogen:
      mChoiceHydrogen.setSelectedIndex(2);
      break;
    case Molecule.cAtomQFNot0Hydrogen | Molecule.cAtomQFNot1Hydrogen | Molecule.cAtomQFNot3Hydrogen:
      mChoiceHydrogen.setSelectedIndex(3);
      break;
    case Molecule.cAtomQFNot0Hydrogen:
      mChoiceHydrogen.setSelectedIndex(4);
      break;
    case Molecule.cAtomQFNot0Hydrogen | Molecule.cAtomQFNot1Hydrogen:
      mChoiceHydrogen.setSelectedIndex(5);
      break;
    case Molecule.cAtomQFNot0Hydrogen | Molecule.cAtomQFNot1Hydrogen | Molecule.cAtomQFNot2Hydrogen:
      mChoiceHydrogen.setSelectedIndex(6);
      break;
    case Molecule.cAtomQFNot2Hydrogen | Molecule.cAtomQFNot3Hydrogen:
      mChoiceHydrogen.setSelectedIndex(7);
      break;
    case Molecule.cAtomQFNot3Hydrogen:
      mChoiceHydrogen.setSelectedIndex(8);
      break;
    }

    int piFeatures = queryFeatures & Molecule.cAtomQFPiElectrons;
    switch (piFeatures) {
    case Molecule.cAtomQFNot1PiElectron | Molecule.cAtomQFNot2PiElectrons:
      mChoicePi.setSelectedIndex(1);
      break;
    case Molecule.cAtomQFNot0PiElectrons | Molecule.cAtomQFNot2PiElectrons:
      mChoicePi.setSelectedIndex(2);
      break;
    case Molecule.cAtomQFNot0PiElectrons | Molecule.cAtomQFNot1PiElectron:
      mChoicePi.setSelectedIndex(3);
      break;
    case Molecule.cAtomQFNot0PiElectrons:
      mChoicePi.setSelectedIndex(4);
      break;
    }

    if ((queryFeatures & Molecule.cAtomQFNoMoreNeighbours) != 0)
      mCBBlocked.setValue(true);

    if ((queryFeatures & Molecule.cAtomQFMoreNeighbours) != 0)
      mCBSubstituted.setValue(true);

    if ((queryFeatures & Molecule.cAtomQFMatchStereo) != 0)
      mCBMatchStereo.setValue(true);

    if ((queryFeatures & Molecule.cAtomQFExcludeGroup) != 0)
      mCBExcludeGroup.setValue(true);
  }

  private void setQueryFeatures() {
    int[] atomList = createAtomList();
    if (mMol.isSelectedAtom(mAtom)) {
      for (int atom = 0; atom < mMol.getAllAtoms(); atom++)
        if (mMol.isSelectedAtom(atom))
          setQueryFeatures(atom, atomList);
    } else {
      setQueryFeatures(mAtom, atomList);
    }
  }

  private void setQueryFeatures(int atom, int[] atomList) {
    int queryFeatures = 0;

    if (mCBAny.getValue()) {
      queryFeatures |= Molecule.cAtomQFAny;
      mMol.setAtomList(atom, atomList, true);
    } else
      mMol.setAtomList(atom, atomList, false);

    if (!mMol.isAromaticAtom(atom)) {
      if (mChoiceArom.getSelectedIndex() == 1)
        queryFeatures |= Molecule.cAtomQFAromatic;
      else if (mChoiceArom.getSelectedIndex() == 2)
        queryFeatures |= Molecule.cAtomQFNotAromatic;
    }

    int ringBonds = 0;
    for (int i = 0; i < mMol.getConnAtoms(atom); i++)
      if (mMol.isRingBond(mMol.getConnBond(atom, i)))
        ringBonds++;
    switch (mChoiceRingState.getSelectedIndex()) {
    case 1:
      if (ringBonds == 0)
        queryFeatures |= (Molecule.cAtomQFNot2RingBonds | Molecule.cAtomQFNot3RingBonds
            | Molecule.cAtomQFNot4RingBonds);
      break;
    case 2:
      queryFeatures |= Molecule.cAtomQFNotChain;
      break;
    case 3:
      if (ringBonds < 3)
        queryFeatures |= (Molecule.cAtomQFNotChain | Molecule.cAtomQFNot3RingBonds | Molecule.cAtomQFNot4RingBonds);
      break;
    case 4:
      if (ringBonds < 4)
        queryFeatures |= (Molecule.cAtomQFNotChain | Molecule.cAtomQFNot2RingBonds | Molecule.cAtomQFNot4RingBonds);
      break;
    case 5:
      queryFeatures |= (Molecule.cAtomQFNotChain | Molecule.cAtomQFNot2RingBonds | Molecule.cAtomQFNot3RingBonds);
      break;
    }

    if (mChoiceRingSize.getSelectedIndex() != 0)
      queryFeatures |= ((mChoiceRingSize.getSelectedIndex() + 2) << Molecule.cAtomQFRingSizeShift);

    switch (mChoiceCharge.getSelectedIndex()) {
    case 1:
      queryFeatures |= (Molecule.cAtomQFCharge & ~Molecule.cAtomQFNotCharge0);
      break;
    case 2:
      queryFeatures |= (Molecule.cAtomQFCharge & ~Molecule.cAtomQFNotChargeNeg);
      break;
    case 3:
      queryFeatures |= (Molecule.cAtomQFCharge & ~Molecule.cAtomQFNotChargePos);
      break;
    }

    switch (mChoiceNeighbours.getSelectedIndex()) {
    case 1:
      if (mMol.getConnAtoms(atom) == 1)
        queryFeatures |= Molecule.cAtomQFNoMoreNeighbours;
      else if (mMol.getConnAtoms(atom) < 1)
        queryFeatures |= (Molecule.cAtomQFNeighbours & ~Molecule.cAtomQFNot1Neighbour);
      break;
    case 2:
      if (mMol.getConnAtoms(atom) == 2)
        queryFeatures |= Molecule.cAtomQFNoMoreNeighbours;
      else if (mMol.getConnAtoms(atom) < 2)
        queryFeatures |= (Molecule.cAtomQFNeighbours & ~Molecule.cAtomQFNot2Neighbours);
      break;
    case 3:
      if (mMol.getConnAtoms(atom) == 3)
        queryFeatures |= Molecule.cAtomQFNoMoreNeighbours;
      else if (mMol.getConnAtoms(atom) < 3)
        queryFeatures |= (Molecule.cAtomQFNeighbours & ~Molecule.cAtomQFNot3Neighbours);
      break;
    case 4: // less than 3 non-H neighbours
      if (mMol.getConnAtoms(atom) == 2)
        queryFeatures |= Molecule.cAtomQFNoMoreNeighbours;
      else if (mMol.getConnAtoms(atom) < 2)
        queryFeatures |= (Molecule.cAtomQFNot3Neighbours | Molecule.cAtomQFNot4Neighbours);
      break;
    case 5: // less than 4 non-H neighbours
      if (mMol.getConnAtoms(atom) == 3)
        queryFeatures |= Molecule.cAtomQFNoMoreNeighbours;
      else if (mMol.getConnAtoms(atom) < 3)
        queryFeatures |= Molecule.cAtomQFNot4Neighbours;
      break;
    case 6: // more than 1 non-H neighbour
      if (mMol.getConnAtoms(atom) == 1)
        queryFeatures |= Molecule.cAtomQFMoreNeighbours;
      else if (mMol.getConnAtoms(atom) < 1)
        queryFeatures |= (Molecule.cAtomQFNot0Neighbours | Molecule.cAtomQFNot1Neighbour);
      break;
    case 7: // more than 2 non-H neighbours
      if (mMol.getConnAtoms(atom) == 2)
        queryFeatures |= Molecule.cAtomQFMoreNeighbours;
      else if (mMol.getConnAtoms(atom) < 2)
        queryFeatures |= (Molecule.cAtomQFNot0Neighbours | Molecule.cAtomQFNot1Neighbour
            | Molecule.cAtomQFNot2Neighbours);
      break;
    case 8: // more than 3 non-H neighbours
      if (mMol.getConnAtoms(atom) == 3)
        queryFeatures |= Molecule.cAtomQFMoreNeighbours;
      else if (mMol.getConnAtoms(atom) < 3)
        queryFeatures |= (Molecule.cAtomQFNeighbours & ~Molecule.cAtomQFNot4Neighbours);
      break;
    }

    switch (mChoiceHydrogen.getSelectedIndex()) {
    case 1: // no hydrogens
      queryFeatures |= (Molecule.cAtomQFNot1Hydrogen | Molecule.cAtomQFNot2Hydrogen | Molecule.cAtomQFNot3Hydrogen);
      break;
    case 2: // exactly 1 hydrogen
      queryFeatures |= (Molecule.cAtomQFNot0Hydrogen | Molecule.cAtomQFNot2Hydrogen | Molecule.cAtomQFNot3Hydrogen);
      break;
    case 3: // exactly 2 hydrogen
      queryFeatures |= (Molecule.cAtomQFNot0Hydrogen | Molecule.cAtomQFNot1Hydrogen | Molecule.cAtomQFNot3Hydrogen);
      break;
    case 4: // at least 1 hydrogen
      queryFeatures |= Molecule.cAtomQFNot0Hydrogen;
      break;
    case 5: // at least 2 hydrogens
      queryFeatures |= (Molecule.cAtomQFNot0Hydrogen | Molecule.cAtomQFNot1Hydrogen);
      break;
    case 6: // at least 3 hydrogens
      queryFeatures |= (Molecule.cAtomQFNot0Hydrogen | Molecule.cAtomQFNot1Hydrogen | Molecule.cAtomQFNot2Hydrogen);
      break;
    case 7: // less than 2 hydrogens
      queryFeatures |= (Molecule.cAtomQFNot2Hydrogen | Molecule.cAtomQFNot3Hydrogen);
      break;
    case 8: // less than 3 hydrogens
      queryFeatures |= (Molecule.cAtomQFNot3Hydrogen);
      break;
    }

    switch (mChoicePi.getSelectedIndex()) {
    case 1: // no pi electrons
      queryFeatures |= (Molecule.cAtomQFNot1PiElectron | Molecule.cAtomQFNot2PiElectrons);
      break;
    case 2: // exactly 1 pi electron
      queryFeatures |= (Molecule.cAtomQFNot0PiElectrons | Molecule.cAtomQFNot2PiElectrons);
      break;
    case 3: // exactly 2 pi electrons
      queryFeatures |= (Molecule.cAtomQFNot0PiElectrons | Molecule.cAtomQFNot1PiElectron);
      break;
    case 4: // at least 1 pi electron
      queryFeatures |= Molecule.cAtomQFNot0PiElectrons;
      break;
    }

    if (mCBBlocked.getValue() && (mMol.getFreeValence(atom) > 0 || (mMol.getAtomCharge(atom) == 0
        && (mMol.getAtomicNo(atom) == 5 || mMol.isNitrogenFamily(atom) || mMol.isChalcogene(atom)))))
      queryFeatures |= Molecule.cAtomQFNoMoreNeighbours;

    if (mCBSubstituted.getValue() && (mMol.getFreeValence(atom) > 0 || (mMol.getAtomCharge(atom) == 0
        && (mMol.getAtomicNo(atom) == 5 || mMol.isNitrogenFamily(atom) || mMol.isChalcogene(atom)))))
      queryFeatures |= Molecule.cAtomQFMoreNeighbours;

    if (mCBMatchStereo.getValue())
      queryFeatures |= Molecule.cAtomQFMatchStereo;

    if (mCBExcludeGroup.getValue())
      queryFeatures |= Molecule.cAtomQFExcludeGroup;

    mMol.setAtomQueryFeature(atom, 0xFFFFFFFF, false);
    mMol.setAtomQueryFeature(atom, queryFeatures, true);
  }

  private int[] createAtomList() {
    String listString = mTFAtomList.getText();
    if (listString.length() == 0)
      return null;

    int[] list = null;
    int delimiterIndex;
    do {
      String label;
      delimiterIndex = listString.indexOf(',');
      if (delimiterIndex == -1)
        label = listString;
      else {
        label = listString.substring(0, delimiterIndex);
        if (delimiterIndex == listString.length() - 1)
          listString = "";
        else
          listString = listString.substring(delimiterIndex + 1);
      }

      int atomicNo = Molecule.getAtomicNoFromLabel(label);
      if (atomicNo != 0) {
        if (atomicNo == 1) {
          // TODO use a GWT dialog?
          // JOptionPane.showMessageDialog(mParentFrame, "'H' cannot be part of an atom
          // list and is removed.");
        } else if (list == null) {
          list = new int[1];
          list[0] = atomicNo;
        } else {
          boolean found = false;
          for (int i = 0; i < list.length; i++) {
            if (atomicNo == list[i]) {
              found = true;
              break;
            }
          }
          if (!found) {
            int[] newList = new int[list.length + 1];
            for (int i = 0; i < list.length; i++)
              newList[i] = list[i];
            newList[list.length] = atomicNo;
            list = newList;
          }
        }
      }
    } while (delimiterIndex != -1);

    if (list != null)
      Arrays.sort(list);

    return list;
  }
}

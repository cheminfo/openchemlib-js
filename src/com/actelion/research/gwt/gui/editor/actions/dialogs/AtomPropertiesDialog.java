/*
* Copyright (c) 1997 - 2015
* Actelion Pharmaceuticals Ltd.
* Gewerbestrasse 16
* CH-4123 Allschwil, Switzerland
*
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions are met:
*
* 1. Redistributions of source code must retain the above copyright notice, this
*    list of conditions and the following disclaimer.
* 2. Redistributions in binary form must reproduce the above copyright notice,
*    this list of conditions and the following disclaimer in the documentation
*    and/or other materials provided with the distribution.
* 3. Neither the name of the the copyright holder nor the
*    names of its contributors may be used to endorse or promote products
*    derived from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
* ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
* (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*/















package com.actelion.research.gwt.gui.editor.actions.dialogs;

import com.actelion.research.chem.Molecule;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.gwt.gui.editor.Window;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.actions.ChangeAtomPropertiesAction;
import com.actelion.research.share.gui.editor.dialogs.IAtomPropertiesDialog;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.*;

/**
 * Project:
 * User: rufenec
 * Date: 7/4/2014
 * Time: 12:36 PM
 */
public class AtomPropertiesDialog extends TDialog implements IAtomPropertiesDialog
{
    private int atomicNo = 0;
    private int mass = 0;
    private int valence = -1;
    private int state = 0;

    private TextBox atomLabel;
    private TextBox atomMass;
    private TextBox atomValence;
    private ComboBox atomRadicalState;

    StereoMolecule molecule;
    int theAtom;
    public AtomPropertiesDialog(Window stage, StereoMolecule mol,int atom)
    {
        super(stage,"Atom Properties");
        molecule = mol;
        theAtom = atom;
    }

    public int getAtomicNo()
    {
        return atomicNo;
    }

    private int getMass()
    {
        return mass;
    }

    private int getValence()
    {
        return valence;
    }

    private int getState()
    {
        return state;
    }

    private void setAtomicNo(int atomicNo)
    {
        this.atomicNo = atomicNo;
    }

    public void setMass(int mass)
    {
        this.mass = mass;
    }

    public void setValence(int valence)
    {
        this.valence = valence;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    @Override
    protected void buildGUI(RootPanel root)
    {
        Grid grid = new Grid(5,4);
        grid.setCellPadding(0);

        grid.setWidget(0,0,new Label("Atom Label"));
        atomLabel = new TextBox();
        grid.setWidget(0,2,atomLabel);

        grid.setWidget(1, 0, new Label("Atom Mass"));
        atomMass = new TextBox();
        grid.setWidget(1,2,atomMass);

        grid.setWidget(2, 0, new Label("Atom Valence"));
        atomValence = new TextBox();
        grid.setWidget(2,2,atomValence);

        grid.setWidget(3, 0, new Label("Radical State"));
        atomRadicalState = new ComboBox();
        atomRadicalState.addItem("None");
        atomRadicalState.addItem("One electron (duplet)");
        atomRadicalState.addItem("Two electrons (triplet)");
        atomRadicalState.addItem("Two electrons (singulet)");
        grid.setWidget(3,2,atomRadicalState);

        Grid buttons = new Grid(1,2);
        buttons.setCellPadding(0);
        Button cancel = new Button("Cancel");
        buttons.setWidget(0,0,cancel);

        Button ok = new Button("OK");
        buttons.setWidget(0,1,ok);

        grid.setWidget(4,2,buttons);
        setWidget(grid);


        ok.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                handleOK();
                onOK();
            }
        });
//
        cancel.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                onCancel();

            }
        });


    }
    @Override
    protected void onInitialUpdate()
    {
        String symbol = molecule.getAtomLabel(theAtom);
        atomLabel.setText(symbol);
        int val = molecule.getAtomAbnormalValence(theAtom);
        if (val >= 0) {
            atomValence.setText(Integer.toString(val));
        }
        int mass = molecule.getAtomMass(theAtom);
        if (mass > 0) {
            atomMass.setText(Integer.toString(mass));
        }
        int rad = molecule.getAtomRadical(theAtom);
        switch(rad) {
            case Molecule.cAtomRadicalStateNone:
                atomRadicalState.setSelectedIndex(0);
                break;
            case Molecule.cAtomRadicalStateS:
                atomRadicalState.setSelectedIndex(3);
                break;
            case Molecule.cAtomRadicalStateD:
                atomRadicalState.setSelectedIndex(1);
                break;
            case Molecule.cAtomRadicalStateT:
                atomRadicalState.setSelectedIndex(2);
                break;
            default:
                atomRadicalState.setSelectedIndex(0);
                break;
        }

    }

    private void handleOK()
    {
        boolean ok = true;
        String symbol = atomLabel.getText();
        setAtomicNo(Molecule.getAtomicNoFromLabel(symbol));
        if (getAtomicNo() != 0 || symbol.equals("?")) {
            setState(getRadicalState());
            if (!ChangeAtomPropertiesAction.isEmptyString(atomValence.getText())) {
                try {
                    setValence(Integer.parseInt(atomValence.getText()));
                    if (getValence() < 0 || getValence() > 15) {
                        ok = false;
                    }
                } catch (NumberFormatException ignored) {
                    ok = false;
                }
            }
            if (!ChangeAtomPropertiesAction.isEmptyString(atomMass.getText())) {
                try {
                    setMass(Integer.parseInt(atomMass.getText()));
                    if (getMass() < Molecule.cRoundedMass[getAtomicNo()] - 18 || getMass() > Molecule.cRoundedMass[getAtomicNo()] + 12) {
                        ok = false;
                    }
                } catch (NumberFormatException ignored) {
                    ok = false;
                }
            }
            if (ok) {
                int atomNo = getAtomicNo();
                int mass = getMass();
                int valence = getValence();
                int radical = getState();
                molecule.changeAtom(theAtom, atomNo, mass, valence, radical);
            }
        }
    }

    private int getRadicalState()
      {
          int index = atomRadicalState.getSelectedIndex();
          int radical = index == 1 ? Molecule.cAtomRadicalStateD :
              index == 2 ? Molecule.cAtomRadicalStateT :
                  index == 3 ? Molecule.cAtomRadicalStateS : 0;
          return radical;

      }
}

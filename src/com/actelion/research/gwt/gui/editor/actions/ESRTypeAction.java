/*

Copyright (c) 2015-2016, cheminfo

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


















package com.actelion.research.gwt.gui.editor.actions;

import com.actelion.research.chem.Molecule;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.gwt.gui.editor.ButtonPressListener;
import com.actelion.research.gwt.gui.editor.ImageHolder;
import com.actelion.research.gwt.gui.editor.ToolBar;
import com.actelion.research.gwt.gui.viewer.GraphicsContext;
import com.actelion.research.gwt.gui.editor.Window;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.actions.BondHighlightAction;
import com.actelion.research.share.gui.editor.io.IMouseEvent;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;

import java.awt.geom.Point2D;

/**
 * Project:
 * User: rufenec
 * Date: 4/2/13
 * Time: 2:08 PM
 */

class ESRPane extends PopupPanel
{

    private boolean loaded = false;
    private Canvas canvas;
    private Model model;

    private final Image ESR_BUTTON_UP = new Image(ImageHolder.ESRBUTTONUP64);
    private final Image ESR_BUTTON_DOWN = new Image(ImageHolder.ESRBUTTONDOWN64);

    public ESRPane(Model m)
    {
        super(true);
        this.model = m;

        ESR_BUTTON_UP.addLoadHandler(new LoadHandler()
        {
            @Override
            public void onLoad(LoadEvent event)
            {
                if (loaded)
                    requestLayout();
                loaded = true;
            }
        });

        ESR_BUTTON_DOWN.addLoadHandler(new LoadHandler()
        {
            @Override
            public void onLoad(LoadEvent event)
            {
                if (loaded)
                    requestLayout();
                loaded = true;
            }
        });

        int width = ESR_BUTTON_UP.getWidth();
        int height = ESR_BUTTON_UP.getHeight();
        canvas = Canvas.createIfSupported();
        canvas.setCoordinateSpaceWidth(width);
        canvas.setCoordinateSpaceHeight(height);
        canvas.setWidth(width + "px");
        canvas.setHeight(height + "px");
        System.out.println("Canvas " + canvas + " " + width);
        setWidget(canvas);

        canvas.addMouseDownHandler(new MouseDownHandler()
        {
            @Override
            public void onMouseDown(MouseDownEvent event)
            {
                int row = (int)(event.getY()/ (ToolBar.ESR_IMAGE_HEIGHT/ ToolBar.ESR_IMAGE_ROWS));
//                System.out.println("Mouse event at " + event.getX() + "," + event.getY() + " row " + row);
                if (row >= 0 && row < 3) {
                    model.setESRType(model.esrTypeFromRow(row));

                }
                requestLayout();
            }
        });

        canvas.addMouseUpHandler(new MouseUpHandler()
        {
            @Override
            public void onMouseUp(MouseUpEvent event)
            {
                hide();
                requestLayout();
            }
        });

        // This class allows us to modify the pane.
        // This might be necessary for instance with z-index
        // if the editor is placed in an app which modifies the z-index e.g. angular/bootstrap etc.
        getElement().setClassName("ESRPane");
    }

    void requestLayout()
    {
//        System.out.println("requestLayout()");
        draw(canvas);
    }

    private void draw(Canvas toolBar)
    {
        Context2d context2d = toolBar.getContext2d();
//        context2d.drawImage(ImageElement.as(ESR_BUTTON_UP.getElement()), 0, 0);
        drawButtons(new GraphicsContext(context2d));
    }

    private void drawButtons(GraphicsContext ctx)
    {
        ctx.drawImage(ESR_BUTTON_UP, 0, 0);
        int selectedRow = model.rowFromESRType(model.getESRType());
        double dx = ToolBar.ESR_IMAGE_WIDTH;
        double dy = ToolBar.ESR_IMAGE_HEIGHT / ToolBar.ESR_IMAGE_ROWS;
        int y = (int) (ToolBar.ESR_IMAGE_HEIGHT / ToolBar.ESR_IMAGE_ROWS * selectedRow);
        int x = 0;
        ctx.drawImage(ESR_BUTTON_DOWN, x, y, dx, dy, x, y, dx, dy);
    }

}

public class ESRTypeAction extends BondHighlightAction implements ButtonPressListener
{

    private volatile ESRPane popup = null;

    public ESRTypeAction(Model model)
    {
        super(model);
    }

    @Override
    public boolean onMouseUp(IMouseEvent evt)
    {
        model.pushUndo();
        int theBond = model.getSelectedBond();
        System.out.println("ESRType Action up : " + theBond + " " + model.getESRType());
        if (theBond != -1) {
            setESRInfo(theBond, model.getESRType());
            return true;
        }
        return false;
    }

    @Override
    public void onButtonPressed(Window parent, Point2D pt)
    {
        createPopup(parent, pt, 0);
    }


    //
    @Override
    public void onButtonReleased(Window parent, Point2D pt)
    {
    }


    private synchronized void createPopup(final Window parent, Point2D pt, int row)
    {

        if (popup == null || !popup.isShowing()) {
            popup = new ESRPane(model);
            popup.setPopupPositionAndShow(new PopupPanel.PositionCallback()
            {
                public void setPosition(int offsetWidth, int offsetHeight)
                {
                    int left = parent.getNative().getAbsoluteLeft() + (int) ToolBar.IMAGE_WIDTH;
                    int top = parent.getNative().getAbsoluteTop() + (int) (ToolBar.IMAGE_HEIGHT / ToolBar.ROWS * 3);
                    popup.setPopupPosition(left, top);
                    popup.requestLayout();
                }
            });
        }
    }

    private int getESRAtom(int stereoBond)
    {
        StereoMolecule mMol = model.getMolecule();
        if (mMol != null) {
            int atom = mMol.getBondAtom(0, stereoBond);
            if (mMol.getAtomParity(atom) != Molecule.cAtomParityNone) {
                return (mMol.isAtomParityPseudo(atom)
                        || (mMol.getAtomParity(atom) != Molecule.cAtomParity1
                        && mMol.getAtomParity(atom) != Molecule.cAtomParity2)) ? -1 : atom;
            }
            if (mMol.getAtomPi(atom) == 1) {
                for (int i = 0; i < mMol.getConnAtoms(atom); i++) {
                    if (mMol.getConnBondOrder(atom, i) == 2) {
                        int connAtom = mMol.getConnAtom(atom, i);
                        if (mMol.getAtomPi(connAtom) == 2
                                && (mMol.getAtomParity(connAtom) == Molecule.cAtomParity1
                                || mMol.getAtomParity(connAtom) == Molecule.cAtomParity2))
                            return connAtom;
                    }
                }
            }
        }
        return -1;
    }

    private int getESRBond(int stereoBond)
    {
        int bond = -1;
        StereoMolecule mMol = model.getMolecule();
        if (mMol != null) {
            bond = mMol.findBINAPChiralityBond(mMol.getBondAtom(0, stereoBond));
            if (bond != -1
                    && mMol.getBondParity(bond) != Molecule.cBondParityEor1
                    && mMol.getBondParity(bond) != Molecule.cBondParityZor2)
                bond = -1;
        }
        return bond;
    }


    private void setESRInfo(int stereoBond, int type)
    {
        int group = -1;
        StereoMolecule mMol = model.getMolecule();
        if (mMol != null) {
            int atom = getESRAtom(stereoBond);
            int bond = (atom == -1) ? getESRBond(stereoBond) : -1;

            // if type requires group information (type==And or type==Or)
            if (type != Molecule.cESRTypeAbs) {
                int maxGroup = -1;
                for (int i = 0; i < mMol.getAtoms(); i++) {
                    if (i != atom
                            && mMol.getAtomESRType(i) == type
                            && (!mMol.isSelectedBond(stereoBond) || !mMol.isSelectedAtom(i))) {
                        int grp = mMol.getAtomESRGroup(i);
                        if (maxGroup < grp)
                            maxGroup = grp;
                    }
                }
                for (int i = 0; i < mMol.getBonds(); i++) {
                    if (i != bond
                            && mMol.getBondESRType(i) == type
                            && (!mMol.isSelectedBond(stereoBond) || !mMol.isSelectedBond(i))) {
                        int grp = mMol.getBondESRGroup(i);
                        if (maxGroup < grp)
                            maxGroup = grp;
                    }
                }

                if ((atom == -1 ? (bond != -1 ? mMol.getBondESRType(bond) : 0) : mMol.getAtomESRType(atom)) != type) {
                    group = Math.min(maxGroup + 1, Molecule.cESRMaxGroups - 1);
                } else {
                    group = (atom == -1) ? mMol.getBondESRGroup(bond) : mMol.getAtomESRGroup(atom);
                    if (mMol.isSelectedBond(stereoBond)) {
                        boolean selectedShareOneGroup = true;
                        for (int i = 0; i < mMol.getAtoms(); i++) {
                            if (i != atom && mMol.isSelectedAtom(i)
                                    && mMol.getAtomESRType(i) == type
                                    && mMol.getAtomESRGroup(i) != group) {
                                selectedShareOneGroup = false;
                                break;
                            }
                        }
                        for (int i = 0; i < mMol.getBonds(); i++) {
                            if (i != bond && mMol.isSelectedBond(i)
                                    && mMol.getBondESRType(i) == type
                                    && mMol.getBondESRGroup(i) != group) {
                                selectedShareOneGroup = false;
                                break;
                            }
                        }
                        if (selectedShareOneGroup) {
                            if (group <= maxGroup) {
                                group++;
                                if (group == Molecule.cESRMaxGroups)
                                    group = 0;
                            } else {
                                group = 0;
                            }
                        }
                    } else {
                        if (group <= maxGroup) {
                            group++;
                            if (group == Molecule.cESRMaxGroups)
                                group = 0;
                        } else {
                            group = 0;
                        }
                    }
                }
            }

            if (mMol.isSelectedBond(stereoBond)) {
                for (int i = 0; i < mMol.getBonds(); i++) {
                    if (mMol.isSelectedBond(i) && mMol.isStereoBond(i)) {
                        int a = getESRAtom(i);
                        int b = getESRBond(i);
                        if (a != -1)
                            mMol.setAtomESR(a, type, group);
                        else if (b != -1)
                            mMol.setBondESR(b, type, group);
                    }
                }
            } else {
                if (atom != -1)
                    mMol.setAtomESR(atom, type, group);
                else if (bond != -1)
                    mMol.setBondESR(bond, type, group);
            }
        }
    }


}

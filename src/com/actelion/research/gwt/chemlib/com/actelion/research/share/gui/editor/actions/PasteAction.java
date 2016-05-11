/*
 * Project: DD_jfx
 * @(#)PasteAction.java
 *
 * Copyright (c) 1997- 2015
 * Actelion Pharmaceuticals Ltd.
 * Gewerbestrasse 16
 * CH-4123 Allschwil, Switzerland
 *
 * All Rights Reserved.
 *
 * This software is the proprietary information of Actelion Pharmaceuticals, Ltd.
 * Use is subject to license terms.
 *
 * Author: Christian Rufener
 */

package com.actelion.research.share.gui.editor.actions;

import com.actelion.research.share.gui.editor.Model;


/**
 * Project:
 * User: rufenec
 * Date: 5/16/13
 * Time: 3:39 PM
 */
public abstract class PasteAction extends CommandAction
{
    java.awt.Dimension bounds;
//    ClipboardHandler mClipboardHandler =new ClipboardHandler();

    public PasteAction(Model model,java.awt.Dimension bounds)
    {
        super(model);
        this.bounds = bounds;
    }
    @Override
    public void onCommand()
    {
//        ClipboardHandler handler =new ClipboardHandler();
//        StereoMolecule mol = handler.pasteMolecule();
//        if (mol != null) {
////                model.addMolecule(mol, new Rectangle2D(0, 0,bounds.getWidth(), bounds.getHeight()));
//        }
        paste();
    }

    private void paste()
    {
        int mMode = model.getMode();

        if ((mMode & Model.MODE_REACTION) != 0) {
            if (pasteReaction()) {
                return;
            }
        }

        pasteMolecule();
    }

    public abstract boolean pasteMolecule();
    public abstract boolean pasteReaction();


/*
    private boolean pasteReaction()
    {
        boolean ret = false;
//        if (mClipboardHandler != null) {
//            Reaction rxn = mClipboardHandler.pasteReaction();
//            if (rxn != null) {
//                StereoMolecule mMol = model.getMolecule();
//                for (int i = 0; i < rxn.getMolecules(); i++) {
//                    rxn.getMolecule(i).setFragment(mMol.isFragment());
//                }
//                model.setReaction(rxn);
//                ret = true;
//            }
//        }
        return ret;
    }
*/

/*
    private boolean pasteMolecule()
    {
        boolean ret = false;
//        if (mClipboardHandler != null) {
//            StereoMolecule mol = mClipboardHandler.pasteMolecule();
//            if (mol != null && mol.getAllAtoms() != 0) {
//                StereoMolecule mMol = model.getMolecule();
//                if (mMol.getAllAtoms() == 0) {
//                    boolean isFragment = mMol.isFragment();
//                    mol.copyMolecule(mMol);
//                    mMol.setFragment(isFragment);
//                    model.notifyChange();
////                    moleculeChanged(true);
//                } else {
//                    int avbl = (int) mMol.getAverageBondLength();
//                    Depictor d = new Depictor(mol);
//                    System.err.println("Implement pasteMolecule()");
////                    d.updateCoords(this.getGraphics(), new Rectangle2D.Float(0, 0,
////                            this.getWidth(),
////                            this.getHeight()),
////                        AbstractDepictor.cModeInflateToMaxAVBL + avbl
////                    );
//                    int originalAtoms = mMol.getAllAtoms();
//                    mMol.addMolecule(mol);
//                    for (int atom = 0; atom < mMol.getAllAtoms(); atom++) {
//                        mMol.setAtomSelection(atom, atom >= originalAtoms);
//                    }
//                    model.notifyChange();
////                    moleculeChanged(true);
//                }
//            }
//            ret = true;
//        }
        return ret;
    }
*/

}



/*
 * Project: DD_jfx
 * @(#)CopyAction.java
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

import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.chem.IArrow;

/**
 * Project:
 * User: rufenec
 * Date: 5/16/13
 * Time: 3:31 PM
 */
public abstract class CopyAction extends CommandAction
{
    public CopyAction(Model model)
    {
        super(model);
    }

    @Override
    public void onCommand()
    {
//        ClipboardHandler handler = new ClipboardHandler();
//        handler.copyMolecule(model.getHighlightedMolecule());
        copy();
    }


    private void copy()
    {
        int mMode = model.getMode();

        boolean isReaction = ((mMode & Model.MODE_REACTION) != 0);
        boolean selectionFound = false;
        boolean isBothSideSelection = false;
        boolean isOnProductSide = false;
        IArrow arrow = null;

//        StereoMolecule mMol = model.getMolecule();
//        for (int atom = 0; atom < mMol.getAllAtoms(); atom++) {
//            if (mMol.isSelectedAtom(atom)) {
//                if (!selectionFound) {
//                    selectionFound = true;
//                    if (!isReaction) {
//                        break;
//                    }
//
//                    arrow = (IArrow) model.getDrawingObjectList().get(0);
//                    isOnProductSide = arrow.isOnProductSide(mMol.getAtomX(atom), mMol.getAtomY(atom));
//                } else {
//                    if (isOnProductSide != arrow.isOnProductSide(mMol.getAtomX(atom), mMol.getAtomY(atom))) {
//                        isBothSideSelection = true;
//                        break;
//                    }
//                }
//            }
//        }

        if (isReaction) {
            if (isBothSideSelection) {
                copyReaction(true);
            } else if (selectionFound) {
                copyMolecule(true);
            } else {
                copyReaction(false);
            }
        } else {
            copyMolecule(selectionFound);
        }
    }

    public abstract boolean copyReaction(boolean selectionOnly);
    public abstract boolean copyMolecule(boolean selectionOnly);

//    private boolean copyReaction(boolean selectionOnly)
//    {
////       ClipboardHandler mClipboardHandler = new ClipboardHandler();
////        Reaction rx = selectionOnly ? getSelectedReaction() : model.getReaction();
////        if (rx != null && mClipboardHandler != null) {
////            return mClipboardHandler.copyReaction(rx);
////        }
////
//        return false;
//    }

//    private Reaction getSelectedReaction()
//    {
//        Reaction rxn = new Reaction();
//        StereoMolecule[] mFragment = model.getFragments();
//        int mReactantCount = model.getReactantCount();
//        for (int i = 0; i < mFragment.length; i++) {
//            StereoMolecule selectedMol = getSelectedCopy(mFragment[i]);
//            if (selectedMol != null) {
//                if (i < mReactantCount) {
//                    rxn.addReactant(selectedMol);
//                } else {
//                    rxn.addProduct(selectedMol);
//                }
//            }
//        }
//        return rxn;
//    }

//    private boolean copyMolecule(boolean selectionOnly)
//    {
////        ClipboardHandler mClipboardHandler = new ClipboardHandler();
////        StereoMolecule mMol = model.getMolecule();
////        if (mMol.getAllAtoms() != 0 && mClipboardHandler != null) {
////            return mClipboardHandler.copyMolecule(selectionOnly ? getSelectedCopy(mMol) : mMol);
////        }
////
//        return false;
//    }

/*    private StereoMolecule getSelectedCopy(StereoMolecule sourceMol)
     {
         int atomCount = 0;
         for (int atom = 0; atom < sourceMol.getAllAtoms(); atom++) {
             if (sourceMol.isSelectedAtom(atom)) {
                 atomCount++;
             }
         }

         if (atomCount == 0) {
             return null;
         }

         int bondCount = 0;
         for (int bond = 0; bond < sourceMol.getAllBonds(); bond++) {
             if (sourceMol.isSelectedBond(bond)) {
                 bondCount++;
             }
         }

         boolean[] includeAtom = new boolean[sourceMol.getAllAtoms()];
         for (int atom = 0; atom < sourceMol.getAllAtoms(); atom++) {
             includeAtom[atom] = sourceMol.isSelectedAtom(atom);
         }

         StereoMolecule destMol = new StereoMolecule(atomCount, bondCount);
         sourceMol.copyMoleculeByAtoms(destMol, includeAtom, false, null);
         return destMol;
     }*/
}

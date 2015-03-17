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






package com.actelion.research.gwt.gui.editor;

import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.chem.CoordinateInventor;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.gwt.gui.viewer.GWTDepictor;
import com.actelion.research.gwt.gui.viewer.Log;
import com.actelion.research.share.gui.editor.Model;

import java.awt.*;

public class GWTEditorModel extends Model
{
    public GWTEditorModel(int mode)
    {
        super(mode);
    }

    @Override
    public void cleanMolecule(boolean selectedOnly)
    {
        cleanupMultiFragmentCoordinates(selectedOnly);
    }

    @Override
    protected AbstractDepictor createDepictor(StereoMolecule mol)
    {
        return new GWTDepictor(null, mol);
    }

    @Override
    public void cleanReaction()
    {

    }

    @Override
    public void analyzeReaction()
    {

    }
    private void cleanupMultiFragmentCoordinates(boolean selectedOnly)
    {

//        int mc = getMols().length;
//        for (int fragment = 0; fragment < mc; fragment++) {
//            StereoMolecule mol = getMols()[fragment];
        StereoMolecule mol = getMolecule();
        {
            if (mol != null) {
                new CoordinateInventor(selectedOnly ? CoordinateInventor.MODE_KEEP_MARKED_ATOM_COORDS : 0).invent(mol);
                mol.setStereoBondsFromParity();
            }
        }
        Dimension dim = getDisplaySize();
        GWTDepictor depictor = new GWTDepictor(null, mol);
        depictor.updateCoords((Graphics)null,
                new java.awt.geom.Rectangle2D.Float(0, 0,
                        (float) dim.getWidth(), (float) dim.getHeight()), GWTDepictor.cModeInflateToMaxAVBL
        );
        setValue(mol,true);
    }
}

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

package com.actelion.research.gwt.gui.editor;

import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.coords.CoordinateInventor;
import com.actelion.research.chem.reaction.Reaction;
import com.actelion.research.gwt.gui.viewer.GWTDepictor;
import com.actelion.research.share.gui.editor.Model;
import com.actelion.research.share.gui.editor.chem.AbstractExtendedDepictor;
import com.actelion.research.share.gui.editor.geom.GeomFactory;

import java.awt.*;

public class GWTEditorModel extends Model
{
/*
    static {
        GeomFactory.registerFactory(new GWTGeomFactory());
    }
*/

    public GWTEditorModel(GeomFactory factory, int mode)
    {
        super(factory,mode);
    }

    @Override
    public void cleanMolecule(boolean selectedOnly)
    {
        cleanupMultiFragmentCoordinates(selectedOnly);
    }

    @Override
    protected AbstractExtendedDepictor createExtendedDepictor() {
        return null;
    }

    @Override
    protected AbstractDepictor createDepictor(StereoMolecule mol)
    {
        return new GWTDepictor( mol);
    }



    @Override
    public void analyzeReaction()
    {

    }

    @Override
    public boolean copyMolecule(boolean selected)
    {
        return false;
    }

    @Override
    public boolean copyReaction(boolean selected)
    {
        return false;
    }

    @Override
    public StereoMolecule pasteMolecule()
    {
        return null;
    }

    @Override
    public Reaction pasteReaction()
    {
        return null;
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
        GWTDepictor depictor = new GWTDepictor(mol);
        depictor.updateCoords((Graphics)null,
                new java.awt.geom.Rectangle2D.Double(0, 0,
                        (float) dim.getWidth(), (float) dim.getHeight()), GWTDepictor.cModeInflateToMaxAVBL
        );
        setValue(mol,true);
    }
}

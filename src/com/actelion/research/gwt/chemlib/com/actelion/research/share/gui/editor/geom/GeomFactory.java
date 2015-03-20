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




package com.actelion.research.share.gui.editor.geom;


import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.gwt.gui.editor.ACTKeyCode;
import com.actelion.research.gwt.gui.editor.actions.dialogs.AtomPropertiesDialog;
import com.actelion.research.gwt.gui.editor.actions.dialogs.AtomQueryFeaturesDialog;
import com.actelion.research.gwt.gui.editor.actions.dialogs.BondQueryFeaturesDialog;
import com.actelion.research.share.gui.Polygon;
import com.actelion.research.share.gui.editor.chem.IArrow;
import com.actelion.research.share.gui.editor.dialogs.IAtomPropertiesDialog;
import com.actelion.research.share.gui.editor.dialogs.IAtomQueryFeaturesDialog;
import com.actelion.research.share.gui.editor.dialogs.IBondQueryFeaturesDialog;
import com.actelion.research.share.gui.editor.io.IKeyCode;

import java.awt.geom.Rectangle2D;

/**
 * Project:
 * User: rufenec
 * Date: 11/24/2014
 * Time: 3:15 PM
 */
public abstract class GeomFactory
{
    static GeomFactory factory = null;

    static class ThisFactory extends GeomFactory
    {
        public IArrow createArrow(Rectangle2D r)
        {
            return null;
        }
        public IAtomQueryFeaturesDialog createAtomQueryFeatureDialog(StereoMolecule mol, int atom)
        {
            return new AtomQueryFeaturesDialog(mol,atom);
        }
        public IBondQueryFeaturesDialog createBondFeaturesDialog(StereoMolecule mol, int bond)
        {
            return new BondQueryFeaturesDialog(mol,bond);
        }
        public IAtomPropertiesDialog createAtomPropertiesDialog(StereoMolecule m, int atom)
        {
            return new AtomPropertiesDialog(null,m,atom);
        }
        @Override
         public Rectangle2D getBoundingRect(StereoMolecule m)
         {
                 double xmax = Double.MIN_VALUE;
                 double ymax = Double.MIN_VALUE;
                 double xmin = Double.MAX_VALUE;
                 double ymin = Double.MAX_VALUE;

                 if (m == null)
                     return null;

                 int na = m.getAllAtoms();
                 double bl = m.getAverageBondLength();
                 for (int i = 0; i < na; i++) {
                     xmax = Math.max(xmax, m.getAtomX(i));
                     xmin = Math.min(xmin, m.getAtomX(i));
                     ymax = Math.max(ymax, m.getAtomY(i));
                     ymin = Math.min(ymin, m.getAtomY(i));
                 }

                 return (na > 0) ? new Rectangle2D.Double(xmin, ymin, Math.max(xmax - xmin, bl), Math.max(ymax - ymin, bl)) : null;
         }
        public IKeyCode getDeleteKey()
        {
            return ACTKeyCode.DELETE;
        }

        public IKeyCode getEscapeKey()
        {
            return ACTKeyCode.ESCAPE;
        }
        public IKeyCode getBackSpaceKey()
        {
            return ACTKeyCode.BACK_SPACE;
        }

        public IKeyCode getEnterKey()
        {
            return ACTKeyCode.ENTER;
        }

    }
    public static synchronized GeomFactory getGeomFactory()
    {
        if (factory == null) {
            factory = new ThisFactory(){

            };
        }
        return factory;
    }

    public final long createColor(double r, double g, double b, double alpha)
    {
        long col = (long)(r * 255) << 24 | (long)(g * 255) << 16 | (long)(b * 255) << 8 | (long)(alpha*255);
        return col;
    }


    public final IPolygon createPolygon()
    {
        return new Polygon();
    }

//    public abstract Point2D createPoint(double x, double y);
    public abstract IArrow createArrow(Rectangle2D r);
    public abstract IAtomQueryFeaturesDialog createAtomQueryFeatureDialog(StereoMolecule mol, int atom);
    public abstract IBondQueryFeaturesDialog createBondFeaturesDialog(StereoMolecule mol, int bond);
    public abstract IAtomPropertiesDialog createAtomPropertiesDialog(StereoMolecule m, int atom);
    public abstract Rectangle2D getBoundingRect(StereoMolecule m);
    public abstract IKeyCode getDeleteKey();
    public abstract IKeyCode getEscapeKey();
    public abstract IKeyCode getBackSpaceKey();
    public abstract IKeyCode getEnterKey();
}

/*
 * Project: DD_jfx
 * @(#)GeomFactory.java
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

package com.actelion.research.share.gui.editor.geom;


import com.actelion.research.chem.StereoMolecule;
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

    public static synchronized void registerFactory(GeomFactory f)
    {
        factory = f;
    }
    public static synchronized GeomFactory getGeomFactory()
    {
        if (factory == null) {
            throw new RuntimeException("No Factory regstered!");
/*
            String nm = System.getProperty("com.actelion.research.geomfactory");
            try {
                Class cls = null;
                try {
                    cls = Class.forName(nm);
                } catch (ClassNotFoundException e) {
                    ClassLoader cl = ClassLoader.getSystemClassLoader();
                    if (cl != null) {
                        try {
                            cls = cl.loadClass(nm);
                        } catch (ClassNotFoundException ee) {
                            throw new Error("GeomFactory not found: " + nm);
                        }
                    }
                }
                factory = (GeomFactory) cls.newInstance();
            } catch (InstantiationException e) {
                throw new Error("GeomFactory not found: " + e);
            } catch (IllegalAccessException e) {
                throw new Error("GeomFactory not found: " + e);
            }
*/
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
    public abstract IArrow createArrow(Rectangle2D r);
    public abstract IAtomQueryFeaturesDialog createAtomQueryFeatureDialog(StereoMolecule mol, int atom);
    public abstract IBondQueryFeaturesDialog createBondFeaturesDialog(StereoMolecule mol, int bond);
    public abstract IAtomPropertiesDialog createAtomPropertiesDialog(StereoMolecule m, int atom);
    public abstract Rectangle2D getBoundingRect(StereoMolecule m);
    public abstract IKeyCode getDeleteKey();
    public abstract IKeyCode getEscapeKey();
    public abstract IKeyCode getBackSpaceKey();
    public abstract IKeyCode getEnterKey();

    public abstract long getHighLightColor();
    public abstract long getMapToolColor();
    public abstract long getSelectionColor();
    public abstract long getForegroundColor();
    public abstract long getBackgroundColor();

}

package com.actelion.research.gwt.gui.editor;

import com.actelion.research.chem.ChemistryHelper;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.gwt.gui.editor.actions.dialogs.AtomPropertiesDialog;
import com.actelion.research.gwt.gui.editor.actions.dialogs.AtomQueryFeaturesDialog;
import com.actelion.research.gwt.gui.editor.actions.dialogs.BondQueryFeaturesDialog;
import com.actelion.research.share.gui.editor.chem.IArrow;
import com.actelion.research.share.gui.editor.dialogs.IAtomPropertiesDialog;
import com.actelion.research.share.gui.editor.dialogs.IAtomQueryFeaturesDialog;
import com.actelion.research.share.gui.editor.dialogs.IBondQueryFeaturesDialog;
import com.actelion.research.share.gui.editor.geom.GeomFactory;
import com.actelion.research.share.gui.editor.io.IKeyCode;

import java.awt.geom.Rectangle2D;

/**
 * Created by rufenec on 25/11/15.
 */
public class GWTGeomFactory extends GeomFactory
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
        return ChemistryHelper.getBoundingRect(m);
/*
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
*/
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


    public long getHighLightColor(){
        return createColor(.3,0.4,1,.4);
    }
    public long getMapToolColor(){
        return createColor(1,0,0,1);
    }
    public long getSelectionColor(){
        return createColor(1,0,0,1);
    }
    public long getForegroundColor(){
        return createColor(0,0,0,1);
    }
    public long getBackgroundColor(){
        return createColor(1,1,1,1);
    }

}

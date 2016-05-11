/*
 * Project: DD_jfx
 * @(#)ZoomRotateAction.java
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
import com.actelion.research.share.gui.editor.geom.ICursor;
import com.actelion.research.share.gui.editor.geom.IDrawContext;
import com.actelion.research.share.gui.editor.io.IMouseEvent;

import java.awt.geom.Point2D;

/**
 * Project:
 * User: rufenec
 * Date: 4/28/2014
 * Time: 12:39 PM
 */
public class ZoomRotateAction extends DrawAction
{
    private java.awt.geom.Point2D origin = null;

    public ZoomRotateAction(Model m)
    {
        super(m);
    }

    @Override
    public  void onActionEnter()
    {
        model.pushUndo();
    }

    @Override
    public boolean onMouseDown(IMouseEvent ev)
    {
        origin = new Point2D.Double(ev.getX(), ev.getY());
//        for (StereoMolecule mol : model.getMols()) {
        StereoMolecule mol = model.getMolecule();
        mol.zoomAndRotateInit((float) origin.getX(), (float) origin.getY());
        return false;
    }

    @Override
    public boolean onMouseUp(IMouseEvent ev)
    {
        return true;
    }

    @Override
    public boolean onMouseMove(IMouseEvent ev, boolean drag)
    {
        if (drag) {
            boolean selectedOnly = false;
            if (model.getSelectedAtom() != -1) {
                selectedOnly = true;
            }
            java.awt.geom.Point2D pt = new Point2D.Double(ev.getX(), ev.getY());
            float magnification = (Math.abs(pt.getY() - origin.getY()) < 20 ? 1.0f : (float) Math.exp((pt.getY() - origin.getY()) / 100f));
            float angleChange = (Math.abs(pt.getX() - origin.getX()) < 20 ? 0.0f : (float) (pt.getX() - origin.getX()) / 50.0f);
//            for (StereoMolecule mol : model.getMols()) {
            StereoMolecule mol = model.getMolecule();
          {
                mol.zoomAndRotate(magnification, angleChange, selectedOnly);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean paint(IDrawContext ctx)
    {
        return false;
    }

    @Override
    public int getCursor()
    {
        return ICursor.TOOL_ZOOMCURSOR;
    }


}

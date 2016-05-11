/*
 * Project: Mercury.5
 * @(#)MolDrawDepictor2.java
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

package com.actelion.research.gwt.gui.editor;

import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.chem.DepictorTransformation;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.reaction.Reaction;
import com.actelion.research.gwt.gui.viewer.GWTDepictor;
import com.actelion.research.gwt.gui.viewer.Log;
import com.actelion.research.share.gui.editor.chem.AbstractExtendedDepictor;
import com.actelion.research.share.gui.editor.chem.IDepictor;
import com.actelion.research.share.gui.editor.chem.IDrawingObject;
import com.actelion.research.share.gui.editor.geom.IDrawContext;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MoleculeDrawDepictor extends AbstractExtendedDepictor<Context2d, CssColor> implements IDepictor<Context2d> {


    Context2d ctx;
    public MoleculeDrawDepictor(Context2d ctx,StereoMolecule mol, java.util.List<IDrawingObject> drawingObjectList) {
        super(mol, drawingObjectList, true);
        this.ctx = ctx;
    }

    public MoleculeDrawDepictor(Context2d ctx,StereoMolecule[] mol, java.util.List<IDrawingObject> drawingObjectList) {
        super(mol, drawingObjectList, true);
        this.ctx = ctx;
    }

    /**
     * Use this constructor for markush structures. The first fragments in the list
     * are the Markush core structures (typically only one), decorated with R1,R2,R3,...
     * The remaining fragments need to contain one atom with atomicNo=0 each, that
     * indicates the attachment point. They also may contain Rn atoms.
     * Any of the fragments may contain query features.
     *
     * @param mol
     * @param markushCoreCount
     * @param drawingObjectList
     */
    public MoleculeDrawDepictor(Context2d ctx,StereoMolecule[] mol, int markushCoreCount, java.util.List<IDrawingObject> drawingObjectList) {
        super(mol, markushCoreCount, drawingObjectList, true);
        this.ctx = ctx;
    }

    public MoleculeDrawDepictor(Context2d ctx,Reaction reaction, java.util.List<IDrawingObject> drawingObjectList, boolean layoutReaction) {
        super(reaction, drawingObjectList, layoutReaction, true);
        this.ctx = ctx;
    }

    @Override
    public AbstractDepictor createDepictor(StereoMolecule stereoMolecule, boolean mUseGraphics2D) {
        if (mUseGraphics2D)
            return new GWTDepictor(stereoMolecule);
        else
            return new GWTDepictor(stereoMolecule);
    }



    @Override
    public void paintFragmentNumbers(Context2d g) {
        if (mFragmentNoColor != null && mMolecule != null) {
            double averageBondLength = calculateAverageBondLength();
            g.save();
//            FillStrokeStyle oldStroke = g.getStrokeStyle();
//            FillStrokeStyle oldFill = g.getFillStyle();
            g.setStrokeStyle(mFragmentNoColor);
            g.setFillStyle(mFragmentNoColor);
            //Font font = Font.font("Helvetica", FontWeight.BOLD, (int) (1.6 * averageBondLength));
            int size = (int) (1.6 * averageBondLength);
            String font = "bold " + size + "px Helvetica";
            g.setFont(font);
//            com.sun.javafx.tk.FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(font);
            for (int i = 0; i < mMolecule.length; i++) {
                if (mMolecule[i].getAllAtoms() != 0) {
                    Point cog = new Point();
                    for (int atom = 0; atom < mMolecule[i].getAllAtoms(); atom++) {
                        cog.x += mMolecule[i].getAtomX(atom);
                        cog.y += mMolecule[i].getAtomY(atom);
                    }
                    cog.x /= mMolecule[i].getAllAtoms();
                    cog.y /= mMolecule[i].getAllAtoms();
                    cog.x = (int) mDepictor[i].getTransformation().transformX(cog.x);
                    cog.y = (int) mDepictor[i].getTransformation().transformY(cog.y);

                    String str = (mReactantOrCoreCount == -1) ? "F" + (i + 1)
                            : (i < mReactantOrCoreCount) ? "" + (char) ('A' + i)
                            : (mIsMarkushStructure) ? "R" + (i + 1 - mReactantOrCoreCount)
                            : "P" + (i + 1 - mReactantOrCoreCount);
                    drawString(g,str,cog.x,cog.y);
//                    int width = (int) getStringWidth(str);
//                    g.fillText(str, cog.x - width / 2, cog.y + (int) (0.3 * fm.getLineHeight()));
                }
            }
            g.restore();
        }
    }


    private void drawString(Context2d ctx,String theString, double x, double y)
    {
        ctx.setTextAlign(Context2d.TextAlign.CENTER);
        ctx.setTextBaseline(Context2d.TextBaseline.MIDDLE);
        ctx.fillText(theString, x, y);
    }
/*    private double getStringWidth(String theString)
    {
        if (ctx != null) {
            TextMetrics tm = ctx.measureText(theString);
            return (double) tm.getWidth();
        }
        return 8;
    }*/

    @Override
    public void paintStructures(Context2d g) {
        if (mDepictor != null) {
            for (int i = 0; i < mDepictor.length; i++) {
                mDepictor[i].setDisplayMode(mDisplayMode);
                mDepictor[i].paint(g);
            }
        }
    }

    @Override
    public void paintDrawingObjects(Context2d g) {
        if (mDrawingObjectList != null) {
            for (int i = 0; i < mDrawingObjectList.size(); i++) {
                IDrawingObject object = mDrawingObjectList.get(i);
                object.draw(new com.actelion.research.gwt.gui.viewer.GraphicsContext(ctx), mTransformation);
            }
        }
    }

    @Override
    public DepictorTransformation updateCoords(IDrawContext<Context2d> g, Rectangle2D.Double rc, int mode) {
        return super.updateCoords(g != null ? g.getNative() : null, rc, mode);
    }


    @Override
    public DepictorTransformation simpleValidateView(Rectangle2D.Double viewRect, int mode) {
        return validateView(null, viewRect, mode);
    }

    @Override
    public void paint(IDrawContext<Context2d> ctx) {
        this.paint(ctx.getNative());
    }

    @Override
    public void setFragmentNoColor(long color) {
        CssColor c = CssColor.make((int)(color >> 24 & 0xFF),(int)(color >> 16 & 0xFF),(int)(color >> 8 & 0xFF));
    }
}

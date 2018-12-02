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
import com.actelion.research.chem.DepictorTransformation;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.reaction.Reaction;
import com.actelion.research.gwt.gui.viewer.GWTDepictor;
import com.actelion.research.share.gui.DrawConfig;
import com.actelion.research.share.gui.editor.chem.AbstractExtendedDepictor;
import com.actelion.research.share.gui.editor.chem.IDepictor;
import com.actelion.research.share.gui.editor.chem.IDrawingObject;
import com.actelion.research.share.gui.editor.geom.IDrawContext;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class MoleculeDrawDepictor extends AbstractExtendedDepictor<Context2d, CssColor>
        implements IDepictor<Context2d> {

    Context2d ctx;

    public MoleculeDrawDepictor(Context2d ctx, StereoMolecule mol, java.util.List<IDrawingObject> drawingObjectList,
            DrawConfig cfg) {
        super(mol, drawingObjectList, true, cfg);
        this.ctx = ctx;
    }

    public MoleculeDrawDepictor(Context2d ctx, StereoMolecule[] mol, java.util.List<IDrawingObject> drawingObjectList,
            DrawConfig cfg) {
        super(mol, drawingObjectList, true, cfg);
        this.ctx = ctx;
    }

    /**
     * Use this constructor for markush structures. The first fragments in the list
     * are the Markush core structures (typically only one), decorated with
     * R1,R2,R3,... The remaining fragments need to contain one atom with atomicNo=0
     * each, that indicates the attachment point. They also may contain Rn atoms.
     * Any of the fragments may contain query features.
     *
     * @param mol
     * @param markushCoreCount
     * @param drawingObjectList
     */
    public MoleculeDrawDepictor(Context2d ctx, StereoMolecule[] mol, int markushCoreCount,
            java.util.List<IDrawingObject> drawingObjectList, DrawConfig cfg) {
        super(mol, markushCoreCount, drawingObjectList, true, cfg);
        this.ctx = ctx;
    }

    public MoleculeDrawDepictor(Context2d ctx, Reaction reaction, java.util.List<IDrawingObject> drawingObjectList,
            boolean layoutReaction, DrawConfig cfg) {
        super(reaction, drawingObjectList, layoutReaction, true, cfg);
        this.ctx = ctx;
    }

    @Override
    public AbstractDepictor createDepictor(StereoMolecule stereoMolecule, boolean mUseGraphics2D, DrawConfig cfg) {
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
            g.setStrokeStyle(mFragmentNoColor);
            g.setFillStyle(mFragmentNoColor);
            int size = (int) (1.6 * averageBondLength);
            String font = "bold " + size + "px Helvetica";
            g.setFont(font);
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
                    drawString(g, str, cog.x, cog.y);
                }
            }
            g.restore();
        }
    }

    private void drawString(Context2d ctx, String theString, double x, double y) {
        ctx.setTextAlign(Context2d.TextAlign.CENTER);
        ctx.setTextBaseline(Context2d.TextBaseline.MIDDLE);
        ctx.fillText(theString, x, y);
    }

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
        CssColor c = CssColor.make((int) (color >> 24 & 0xFF), (int) (color >> 16 & 0xFF), (int) (color >> 8 & 0xFF));
    }
}

/*
 * Project: GWTEditor
 * @(#)DepictorTransformation.java
 *
 * Copyright (c) 1997- 2014
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
/*
    This is a special version which excludes the refs to AbstractDrawingObject
 */
package com.actelion.research.chem;

import com.actelion.research.chem.AbstractDepictor;
import com.actelion.research.chem.Molecule;

import java.awt.geom.*;

public class DepictorTransformation {
    private float mOffsetX,mOffsetY,mScaling;

    public DepictorTransformation() {
        clear();
        }

    public DepictorTransformation(DepictorTransformation t) {
        mScaling = t.mScaling;
        mOffsetX = t.mOffsetX;
        mOffsetY = t.mOffsetY;
        }

	public DepictorTransformation(float scaling, float offsetX, float offsetY) {
        mScaling = scaling;
        mOffsetX = offsetX;
        mOffsetY = offsetY;
        }

    public DepictorTransformation(Rectangle2D.Float bounds,
                                  Rectangle2D.Float view,
                                  float averageBondLength,
                                  int mode) {
            // calculates transformation needed to transfer bounds into view considering mode.
            // averageBondLength may be 0 if (mode & cModeInflateToMaxAVBL) == 0.
        clear();

        if (view != null) {
            if ((mode & AbstractDepictor.cModeInflateToAVBL) == 0) {
                // check if bounds fit in view. If not then center and reduce if needed
                if (!view.contains(bounds)) {
                    if ((bounds.width > view.width) || (bounds.height > view.height)) {
                    	float hScaling = view.width / bounds.width;
                    	float vScaling = view.height / bounds.height;
                        mScaling = Math.min(hScaling, vScaling);
                        }

                    if (bounds.x*mScaling < view.x)
                        mOffsetX = view.x - bounds.x*mScaling;
                    else if ((bounds.x+bounds.width)*mScaling > view.x+view.width)
                        mOffsetX = view.x+view.width - (bounds.x+bounds.width)*mScaling;

                    if (bounds.y*mScaling < view.y)
                        mOffsetY = view.y - bounds.y*mScaling;
                    else if ((bounds.y+bounds.height)*mScaling > view.y+view.height)
                        mOffsetY = view.y+view.height - (bounds.y+bounds.height)*mScaling;

// for keeping all stuff centered do the following:
//					mOffsetX = view.x+view.width/2.0 - mScaling*(bounds.x+bounds.width/2.0);
//					mOffsetY = view.y+view.height/2.0 - mScaling*(bounds.y+bounds.height/2.0);
                    }
                }
            else {
                // inflate to maximum bond length or maximum that fits
            	float hScaling = view.width / bounds.width;
            	float vScaling = view.height / bounds.height;

            	float maxAVBL = mode & AbstractDepictor.cModeMaxBondLength;
            	if (maxAVBL == 0)
            		maxAVBL = AbstractDepictor.cOptAvBondLen;
            	else if ((mode & AbstractDepictor.cModeInflateToHighResAVBL) != 0)
            		maxAVBL /= 256;

            	float bScaling = maxAVBL / averageBondLength;

                mScaling = Math.min(bScaling, Math.min(hScaling, vScaling));

                mOffsetX = view.x+view.width/2.0f - mScaling*(bounds.x+bounds.width/2.0f);
                mOffsetY = view.y+view.height/2.0f - mScaling*(bounds.y+bounds.height/2.0f);
                }
            }
        else if ((mode & AbstractDepictor.cModeInflateToMaxAVBL) != 0) {
        	float maxAVBL = ((mode & AbstractDepictor.cModeMaxBondLength) != 0) ?
                               mode & AbstractDepictor.cModeMaxBondLength : AbstractDepictor.cOptAvBondLen;
            mScaling = maxAVBL / averageBondLength;
            }
        }

    public void clear() {
        mOffsetX = 0.0f;
        mOffsetY = 0.0f;
        mScaling = 1.0f;
        }

    public float transformX(float x) {
        return x*mScaling+mOffsetX;
        }

    public float transformY(float y) {
        return y*mScaling+mOffsetY;
        }

    public float getScaling() {
        return mScaling;
        }

    public float getOffsetX()
    {
        return mOffsetX;
    }

    public float getOffsetY()
    {
        return mOffsetY;
    }

    public void move(float dx, float dy) {
        mOffsetX += dx;
        mOffsetY += dy;
        }
    public void setScaling(float scale) {
        mScaling = scale;
        }

    public boolean isVoidTransformation() {
        return (mScaling == 1.0 && mOffsetX == 0.0 && mOffsetY == 0.0);
        }

    public void applyTo(DepictorTransformation t) {
        t.mScaling *= mScaling;
        t.mOffsetX = t.mOffsetX * mScaling + mOffsetX;
        t.mOffsetY = t.mOffsetY * mScaling + mOffsetY;
        }

    public void applyTo(Point2D.Float p) {
        p.x = p.x * mScaling + mOffsetX;
        p.y = p.y * mScaling + mOffsetY;
        }

    public void applyTo(Rectangle2D.Float r) {
        r.x = r.x * mScaling + mOffsetX;
        r.y = r.y * mScaling + mOffsetY;
        r.width *= mScaling;
        r.height *= mScaling;
        }

    public void applyTo(Molecule m) {
        m.scaleCoords(mScaling);
        m.translateCoords(mOffsetX, mOffsetY);
        }

//    public void applyTo(AbstractDrawingObject o) {
//        o.scale(mScaling);
//        o.move(mOffsetX, mOffsetY);
//        }

    public DepictorTransformation getInverseTransformation() {
        DepictorTransformation t = new DepictorTransformation();
        t.mScaling = 1.0f / mScaling;
        t.mOffsetX = -mOffsetX / mScaling;
        t.mOffsetY = -mOffsetY / mScaling;
        return t;
        }

    public String toString() {
        return "DepictorTransformation Offset: " + mOffsetX +"," + mOffsetY + " Scaling: " + mScaling;
        }
    }

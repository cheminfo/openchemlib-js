/*
 * Project: DD_jfx
 * @(#)Arrow.java
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

package com.actelion.research.share.gui;

import com.actelion.research.chem.DepictorTransformation;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.share.gui.editor.chem.IDrawingObject;
import com.actelion.research.share.gui.editor.geom.GeomFactory;
import com.actelion.research.share.gui.editor.geom.IDrawContext;

import java.awt.geom.Rectangle2D;

public class Arrow implements IDrawingObject
{

    protected static final GeomFactory builder = GeomFactory.getGeomFactory();

     /*
                                      *
                                        **
                                          ***
                                            ****
                                              *****
                                                ******
     ***************************************************
                                                ******
                                              *****
                                            ****
                                          ***
                                        **
                                      *
      */


    protected Rectangle2D rect = null;
    private boolean selected = false;

    public Arrow(double x, double y, double w, double h)
    {
        rect = new Rectangle2D.Double(x, y, w, h);
    }

//    public Arrow(Rectangle2D r)
//    {
//        rect = r;
//    }

    @Override
    public void setSelected(boolean b) {
        selected = b;
    }

    @Override
    public boolean isSelected()
    {
        return selected;
    }

    @Override
    public void move(float dx, float dy) {
        rect.setRect(rect.getX()+dx,rect.getY()+dy,rect.getWidth(),rect.getHeight());
    }

    @Override
    public Rectangle2D getBoundingRect() {
        return rect;
    }

    @Override
    public void setRect(float x, float y, float w, float h) {
        rect = new Rectangle2D.Double(x, y, w, h);
    }

    @Override
    public void scale(float scaling) {
        rect.setRect(rect.getX()*scaling,rect.getY()*scaling,rect.getWidth()*scaling,rect.getHeight()*scaling);
    }

    @Override
    public void draw(IDrawContext ctx,DepictorTransformation t)
    {
//        float x1 = (t == null) ? mPoint[0].x : t.transformX(mPoint[0].x);
//        float y1 = (t == null) ? mPoint[0].y : t.transformY(mPoint[0].y);
//        float x2 = (t == null) ? mPoint[1].x : t.transformX(mPoint[1].x);
//        float y2 = (t == null) ? mPoint[1].y : t.transformY(mPoint[1].y);


        double dx =        t == null ? (rect.getMinX()) :   t.transformX((float)rect.getMinX()) ;
        double dy =        t == null ? (rect.getMinY()) :   t.transformY((float)rect.getMinY()) ;
        double dwidth =    t == null ? rect.getWidth() :    t.transformX((float)rect.getWidth());
        double dheight =   t == null ? rect.getHeight() :   t.transformY((float)rect.getHeight()) ;
        double arrowEndX = dx + dwidth;
        double arrowEndY = dy + (dheight / 2);
        double xOffset = dwidth/15;
        double[] px = {
            (arrowEndX - (xOffset)),
            arrowEndX,
            (arrowEndX - (dwidth / 5))
        };
        double[] py = {
            arrowEndY,
            arrowEndY,
            (arrowEndY - (dwidth / 10))
        };

        if (selected) {
            ctx.setStroke(builder.getHighLightColor());
            ctx.setFill(builder.getHighLightColor());
        }
        if ((arrowEndX - dx) >= 5) {
            ctx.drawLine((int) dx, (int) arrowEndY, (int) arrowEndX-xOffset, (int) arrowEndY);
        }
        ctx.fillPolygon(px, py, 3);
        //ctx.drawLine(arrowEndX,arrowEndY,arrowEndX,arrowEndX-10);
        py[2] = (int) (arrowEndY + (dwidth / 10));
        ctx.fillPolygon(px, py, 3);

//        System.out.printf("Drawing Arrow %s\n",selected);
/*
        if (selected) {
            int width = 5;
            ctx.save();
            ctx.setLineWidth(width);
            ctx.setStroke(builder.getHighLightColor());
            ctx.drawLine((int) dx, (int) arrowEndY, (int) (arrowEndX - (dwidth / 5)), (int) arrowEndY);
            ctx.restore();
        }
*/
    }

    public boolean isOnProductSide(float x, float y) {
//        System.out.printf("ISOnProductSide %f %f %s\n",x,y,rect);
        return x > rect.getX() + rect.getWidth()/2;
/*
        double dx = rect.getWidth();
        double dy = rect.getHeight();
        double mx = (rect.getX() + rect.getWidth()) / 2.0f;
        double my = (rect.getY() + rect.getHeight()) / 2.0f;

        if (dx == 0.0)
            return (dy < 0.0) ^ (y > my);

        if (dy == 0.0)
            return (dx < 0.0) ^ (x > mx);

        double m = -dx/dy;	// m of orthogonal line through S

        double sx = (rect.getX() + m*m*x - m*y + m*rect.getY()) / (1 + m*m);
        return (dx < 0.0) ^ (sx > mx);
*/
    }

}

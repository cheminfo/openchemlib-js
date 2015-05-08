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






















package com.actelion.research.gwt.gui.viewer;

import com.actelion.research.share.gui.editor.geom.IDrawContext;
import com.actelion.research.share.gui.editor.geom.IPolygon;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.canvas.dom.client.TextMetrics;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

public class GraphicsContext implements IDrawContext<Context2d>
{
    Context2d ctx;
    private int textSize = 10;

    public GraphicsContext(Context2d ctx)
    {
        this.ctx = ctx;
    }

    public Context2d getContext()
    {
        return ctx;
    }

    private String makeRGBA(long color)
    {
        String s =  "rgba(" +
            (int) ((color & 0xFF000000l) >> 24) + "," +
            (int) ((color & 0x00FF0000l) >> 16) + "," +
            (int) ((color & 0x0000FF00l) >> 8) + "," +
            (double) ((color & 0x000000FFl) / 255.0) + ")";
//        Log.println("makeRGBA " + s);
        return s;
    }

    private CssColor createColor(long color)
    {
        return CssColor.make(
            (int) ((color & 0xFF000000l) >> 24),
            (int) ((color & 0x00FF0000l) >> 16),
            (int) ((color & 0x0000FF00l) >> 8));
    }

    @Override
    public Context2d getNative()
    {
        return ctx;
    }

    @Override
    public void drawLine(double x, double y, double x1, double y1)
    {
        ctx.setLineCap(Context2d.LineCap.ROUND);
        ctx.setLineJoin(Context2d.LineJoin.MITER);
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.lineTo(x1, y1);
        ctx.stroke();
    }


    @Override
    public void drawDashedLine(double srcx, double srcy, double targetx, double targety, int[] dashPattern)
    {
        double dx = targetx - srcx;
        double dy = targety - srcy;
        double angle = Math.atan2(dy, dx);
        double x = srcx;
        double y = srcy;
        int idx = 0;
        boolean draw = true;

        ctx.beginPath();
        ctx.moveTo(srcx, srcy);

        while (!((dx < 0 ? x <= targetx : x >= targetx) && (dy < 0 ? y <= targety : y >= targety))) {
            double dashLength = dashPattern[idx++ % dashPattern.length];
            double nx = x + (Math.cos(angle) * dashLength);
            x = dx < 0 ? Math.max(targetx, nx) : Math.min(targetx, nx);
            double ny = y + (Math.sin(angle) * dashLength);
            y = dy < 0 ? Math.max(targety, ny) : Math.min(targety, ny);
            if (draw) {
                ctx.lineTo(x, y);
            } else {
                ctx.moveTo(x, y);
            }
            draw = !draw;
        }

        ctx.closePath();
        ctx.stroke();

    }

    @Override
    public void drawPolygon(IPolygon polygon)
    {
        ctx.beginPath();
        java.awt.geom.Point2D pt = polygon.get(0);
        ctx.moveTo(pt.getX(), pt.getY());
        for (int i = 1; i < polygon.size(); i++) {
            pt = polygon.get(i);
            ctx.lineTo(pt.getX(), pt.getY());
        }
        ctx.closePath();
        ctx.stroke();
    }

//    @Override
//    public void drawArrow(double[] px, double[] py, boolean selected)
//    {
////        ctx.save();
////        if (isSelected()) {
////            ctx.setFill(Color.RED);
////            ctx.fillRect(x - 2, arrowEndY - 2, 4, 4);
////            ctx.fillRect(arrowEndX - 2, arrowEndY - 2, 4, 4);
////        }
////        ctx.setStroke(Color.BLACK);
////        ctx.setFill(Color.BLACK);
////        if ((arrowEndX - x) >= 5) {
////            drawLine(ctx, (int) x, (int) arrowEndY, (int) arrowEndX, (int) arrowEndY);
////        }
////        fillPolygon(ctx, px, py, 3);
////        py[2] = (int) (arrowEndY + (arrowHeadOffset / 10));
////        fillPolygon(ctx, px, py, 3);
////
////        ctx.restore();
//
//    }

    @Override
    public java.awt.Dimension getBounds(String s)
    {
        TextMetrics tm = ctx.measureText(s);
        tm.getWidth();
//        String f =ctx.getFont();
        java.awt.Dimension bounds = new java.awt.Dimension((int) tm.getWidth(),textSize);
        return bounds;
    }

    @Override
    public void setFont(String name, double size,boolean bold)
    {
        textSize = (int)size;
        String fna = " " + (int)textSize + "px";
        ctx.setFont(fna);
    }

    @Override
    public void setFill(long color)
    {
        ctx.setFillStyle(makeRGBA(color));
//        ctx.setFill(createColor(color));
    }

    @Override
    public void fillText(String str, double x, double y)
    {
        ctx.fillText(str, x, y);
    }

    @Override
    public void save()
    {
        ctx.save();
    }

    @Override
    public void restore()
    {
        ctx.restore();
    }

    @Override
    public void drawRect(double x, double y, double width, double height)
    {
        ctx.strokeRect(x, y, width, height);
    }

    @Override
    public void drawText(String s, double x, double y, boolean centerHorz, boolean centerVert)
    {
        ctx.setTextAlign(centerHorz ? "CENTER" : "LEFT");
        ctx.setTextBaseline(centerVert ? "CENTER" : "TOP");
        ctx.strokeText(s, x, y);

    }

    @Override
    public void clearRect(double x, double y, double w, double h)
    {
        ctx.clearRect(x, y, w, h);
    }

    @Override
    public void setStroke(long color)
    {
//        String style =
//            "rgba(" +
//                (int) (color.getRed() * 255) + "," +
//                (int) (color.getGreen() * 255) + "," +
//                (int) (color.getBlue() * 255) + "," +
//                color.getOpacity() + ")";
//        ctx.setStrokeStyle(style);
        ctx.setStrokeStyle(makeRGBA(color));
    }

    @Override
    public void fillElipse(double x, double y, double rx, double ry)
    {
        ctx.beginPath();
//        ctx.moveTo(x, y); // A1
        ctx.arc(x+rx/2,y+ry/2,rx/2,0,Math.PI*2,true);

//        ctx.beginPath();
//
//        ctx.moveTo(x, y - ry / 2); // A1
//        ctx.bezierCurveTo(
//            x + rx / 2, y - ry / 2, // C1
//            x + rx / 2, y + ry / 2, // C2
//            x, y + ry / 2); // A2
//        ctx.bezierCurveTo(
//            x - rx / 2, y + ry / 2, // C3
//            x - rx / 2, y - ry / 2, // C4
//            x, y - ry / 2); // A1
        ctx.closePath();
        ctx.fill();
    }

    @Override
    public void fillRect(double x, double y, double w, double h)
    {
        ctx.fillRect(x, y, w, h);
    }

    @Override
    public void strokeLine(double x, double y, double x1, double y1)
    {
        ctx.beginPath();
        ctx.moveTo(x, y);
        ctx.lineTo(x1, y1);
        ctx.closePath();
        ctx.stroke();
//        ctx.strokeLine(x, y, x1, y1);
    }

    @Override
    public void fillPolygon(double[] px, double[] py, int num)
    {
        if (num > 2) {
            ctx.beginPath();
            ctx.moveTo(px[0],py[0]);
            for (int i = 1 ; i < num; i++) {
                ctx.lineTo(px[i],py[i]);
            }
            ctx.lineTo(px[0], py[0]);
            ctx.closePath();
            ctx.fill();
        }
    }

    @Override
    public void setLineWidth(double i)
    {
        ctx.setLineWidth(i);

    }
//
//    public void save()
//    {
//        ctx.save();
//    }
//
//    public void setFill(Color fill)
//    {
//        String style =
//            "rgba(" +
//                (int) (fill.getRed() * 255) + "," +
//                (int) (fill.getGreen() * 255) + "," +
//                (int) (fill.getBlue() * 255) + "," +
//                fill.getOpacity() + ")";
//        ctx.setFillStyle(style);
////            CssColor.make((int)fill.getRed()*255,(int)fill.getGreen()*255,(int)fill.getBlue()*255,(int)fill.getOpacity())
////        );
//    }
//
//    public void fillArc(double x, double y, double width, double height, int start, int end, ArcType type)
//    {
//        ctx.beginPath();
//        ctx.arc(
//            (double) x + width / 2, (double) y + height / 2,
//            (double) (width + height) / 4, (double) 0,
//            Math.PI * 2);
//        ctx.closePath();
//        ctx.fill();
//
//    }
//
//    public void restore()
//    {
//        ctx.restore();
//    }
//
//    public void beginPath()
//    {
//        ctx.beginPath();
//
//    }
//
//    public void moveTo(double x1, double y1)
//    {
//        ctx.moveTo(x1, y1);
//    }
//
//    public void lineTo(double x2, double y2)
//    {
//        ctx.lineTo(x2, y2);
//    }
//
//    public void stroke()
//    {
//        ctx.stroke();
//
//
//    }
//
//    public void setLineWidth(int lineWidth)
//    {
//        ctx.setLineWidth(lineWidth);
//    }
//
//    public void setStroke(Color color)
//    {
//        String style =
//            "rgba(" +
//                (int) (color.getRed() * 255) + "," +
//                (int) (color.getGreen() * 255) + "," +
//                (int) (color.getBlue() * 255) + "," +
//                color.getOpacity() + ")";
//        ctx.setStrokeStyle(style);
////        ctx.setStrokeStyle(
////            CssColor.make((int) color.getRed() * 255, (int) color.getGreen() * 255, (int) color.getBlue() * 255)
////
////        );
//    }
//
//    public void setLineCap(StrokeLineCap cap)
//    {
//        Context2d.LineCap c = Context2d.LineCap.ROUND;
//        switch (cap) {
//            case BUTT:
//                c = Context2d.LineCap.BUTT;
//                break;
//            case ROUND:
//                c = Context2d.LineCap.ROUND;
//                break;
//            case SQUARE:
//                c = Context2d.LineCap.SQUARE;
//                break;
//        }
//        ctx.setLineCap(c);
//    }
//
//    public void setLineJoin(StrokeLineJoin cap)
//    {
//        Context2d.LineJoin c = Context2d.LineJoin.MITER;
//        switch (cap) {
//            case BEVEL:
//                c = Context2d.LineJoin.BEVEL;
//                break;
//            case MITER:
//                c = Context2d.LineJoin.MITER;
//                break;
//            case ROUND:
//                c = Context2d.LineJoin.ROUND;
//        }
//        ctx.setLineJoin(c);
//    }
//
//    public void closePath()
//    {
//        ctx.closePath();
//        ;
//
//    }
//
//    /**
//     * Draws a scaled subset of an image.
//     *
//     * @param image an {@link com.google.gwt.dom.client.CanvasElement} object
//     * @param sx    the x coordinate of the upper-left corner of the source rectangle
//     * @param sy    the y coordinate of the upper-left corner of the source rectangle
//     * @param sw    the width of the source rectangle
//     * @param sh    the width of the source rectangle
//     * @param dx    the x coordinate of the upper-left corner of the destination rectangle
//     * @param dy    the y coordinate of the upper-left corner of the destination rectangle
//     * @param dw    the width of the destination rectangle
//     * @param dh    the height of the destination rectangle
//     */
    public final void drawImage(Image image, double sx, double sy, double sw, double sh,
                                double dx, double dy, double dw, double dh)
    {
        ctx.drawImage(ImageElement.as(image.getElement()), sx, sy, sw, sh, dx, dy, dw, dh);
    }

    public void drawImage(Image image, int x, int y)
    {
        ctx.drawImage(ImageElement.as(image.getElement()), x, y);
    }
//
//    public void strokeLine(double x, double y, double x1, double y1)
//    {
//        ctx.beginPath();
//        ctx.moveTo(x, y);
//        ctx.lineTo(x1, y1);
//        ctx.stroke();
//    }
//
//    public void setTextSize(int textSize)
//    {
//        ctx.setFont("regular " + textSize + "px Helvetica");
//    }
//
//    public void fillText(String s, double x, double y)
//    {
//        ctx.fillText(s,x,y);
//    }
//

}

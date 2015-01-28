/*
 * Copyright (c) 1997- 2015
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
 */

package java.awt.geom;


import java.awt.Shape;

public abstract class RectangularShape implements Shape
{

    /**
     * This is an abstract class that cannot be instantiated directly.
     *
     * @see java.awt.geom.Arc2D
     * @see java.awt.geom.Ellipse2D
     * @see java.awt.geom.Rectangle2D
     * @see java.awt.geom.RoundRectangle2D
     * @since 1.2
     */
    protected RectangularShape() {
    }

    /**
     * Returns the X coordinate of the upper-left corner of
     * the framing rectangle in <code>double</code> precision.
     * @return the X coordinate of the upper-left corner of
     * the framing rectangle.
     * @since 1.2
     */
    public abstract double getX();

    /**
     * Returns the Y coordinate of the upper-left corner of
     * the framing rectangle in <code>double</code> precision.
     * @return the Y coordinate of the upper-left corner of
     * the framing rectangle.
     * @since 1.2
     */
    public abstract double getY();

    /**
     * Returns the width of the framing rectangle in
     * <code>double</code> precision.
     * @return the width of the framing rectangle.
     * @since 1.2
     */
    public abstract double getWidth();

    /**
     * Returns the height of the framing rectangle
     * in <code>double</code> precision.
     * @return the height of the framing rectangle.
     * @since 1.2
     */
    public abstract double getHeight();

    /**
     * Returns the smallest X coordinate of the framing
     * rectangle of the <code>Shape</code> in <code>double</code>
     * precision.
     * @return the smallest X coordinate of the framing
     *          rectangle of the <code>Shape</code>.
     * @since 1.2
     */
    public double getMinX() {
        return getX();
    }

    /**
     * Returns the smallest Y coordinate of the framing
     * rectangle of the <code>Shape</code> in <code>double</code>
     * precision.
     * @return the smallest Y coordinate of the framing
     *          rectangle of the <code>Shape</code>.
     * @since 1.2
     */
    public double getMinY() {
        return getY();
    }

    /**
     * Returns the largest X coordinate of the framing
     * rectangle of the <code>Shape</code> in <code>double</code>
     * precision.
     * @return the largest X coordinate of the framing
     *          rectangle of the <code>Shape</code>.
     * @since 1.2
     */
    public double getMaxX() {
        return getX() + getWidth();
    }

    /**
     * Returns the largest Y coordinate of the framing
     * rectangle of the <code>Shape</code> in <code>double</code>
     * precision.
     * @return the largest Y coordinate of the framing
     *          rectangle of the <code>Shape</code>.
     * @since 1.2
     */
    public double getMaxY() {
        return getY() + getHeight();
    }

    /**
     * Returns the X coordinate of the center of the framing
     * rectangle of the <code>Shape</code> in <code>double</code>
     * precision.
     * @return the X coordinate of the center of the framing rectangle
     *          of the <code>Shape</code>.
     * @since 1.2
     */
    public double getCenterX() {
        return getX() + getWidth() / 2.0;
    }

    /**
     * Returns the Y coordinate of the center of the framing
     * rectangle of the <code>Shape</code> in <code>double</code>
     * precision.
     * @return the Y coordinate of the center of the framing rectangle
     *          of the <code>Shape</code>.
     * @since 1.2
     */
    public double getCenterY() {
        return getY() + getHeight() / 2.0;
    }

    /**
     * Returns the framing {@link java.awt.geom.Rectangle2D}
     * that defines the overall shape of this object.
     * @return a <code>Rectangle2D</code>, specified in
     * <code>double</code> coordinates.
     * @see #setFrame(double, double, double, double)
     * @see #setFrame(java.awt.geom.Point2D, java.awt.geom.Dimension2D)
     * @see #setFrame(java.awt.geom.Rectangle2D)
     * @since 1.2
     */
    public java.awt.geom.Rectangle2D getFrame() {
        return new java.awt.geom.Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Determines whether the <code>RectangularShape</code> is empty.
     * When the <code>RectangularShape</code> is empty, it encloses no
     * area.
     * @return <code>true</code> if the <code>RectangularShape</code> is empty;
     *          <code>false</code> otherwise.
     * @since 1.2
     */
    public abstract boolean isEmpty();

    /**
     * Sets the location and size of the framing rectangle of this
     * <code>Shape</code> to the specified rectangular values.
     *
     * @param x the X coordinate of the upper-left corner of the
     *          specified rectangular shape
     * @param y the Y coordinate of the upper-left corner of the
     *          specified rectangular shape
     * @param w the width of the specified rectangular shape
     * @param h the height of the specified rectangular shape
     * @see #getFrame
     * @since 1.2
     */
    public abstract void setFrame(double x, double y, double w, double h);

    /**
     * Sets the location and size of the framing rectangle of this
     * <code>Shape</code> to the specified {@link java.awt.geom.Point2D} and
     * {@link java.awt.geom.Dimension2D}, respectively.  The framing rectangle is used
     * by the subclasses of <code>RectangularShape</code> to define
     * their geometry.
     * @param loc the specified <code>Point2D</code>
     * @param size the specified <code>Dimension2D</code>
     * @see #getFrame
     * @since 1.2
     */
//    public void setFrame(java.awt.geom.Point2D loc, Dimension2D size) {
//        setFrame(loc.getX(), loc.getY(), size.getWidth(), size.getHeight());
//    }

    /**
     * Sets the framing rectangle of this <code>Shape</code> to
     * be the specified <code>Rectangle2D</code>.  The framing rectangle is
     * used by the subclasses of <code>RectangularShape</code> to define
     * their geometry.
     * @param r the specified <code>Rectangle2D</code>
     * @see #getFrame
     * @since 1.2
     */
//    public void setFrame(java.awt.geom.Rectangle2D r) {
//        setFrame(r.getX(), r.getY(), r.getWidth(), r.getHeight());
//    }

    /**
     * Sets the diagonal of the framing rectangle of this <code>Shape</code>
     * based on the two specified coordinates.  The framing rectangle is
     * used by the subclasses of <code>RectangularShape</code> to define
     * their geometry.
     *
     * @param x1 the X coordinate of the start point of the specified diagonal
     * @param y1 the Y coordinate of the start point of the specified diagonal
     * @param x2 the X coordinate of the end point of the specified diagonal
     * @param y2 the Y coordinate of the end point of the specified diagonal
     * @since 1.2
     */
    public void setFrameFromDiagonal(double x1, double y1,
                                     double x2, double y2) {
        if (x2 < x1) {
            double t = x1;
            x1 = x2;
            x2 = t;
        }
        if (y2 < y1) {
            double t = y1;
            y1 = y2;
            y2 = t;
        }
        setFrame(x1, y1, x2 - x1, y2 - y1);
    }

    /**
     * Sets the diagonal of the framing rectangle of this <code>Shape</code>
     * based on two specified <code>Point2D</code> objects.  The framing
     * rectangle is used by the subclasses of <code>RectangularShape</code>
     * to define their geometry.
     *
     * @param p1 the start <code>Point2D</code> of the specified diagonal
     * @param p2 the end <code>Point2D</code> of the specified diagonal
     * @since 1.2
     */
    public void setFrameFromDiagonal(java.awt.geom.Point2D p1, java.awt.geom.Point2D p2) {
        setFrameFromDiagonal(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /**
     * Sets the framing rectangle of this <code>Shape</code>
     * based on the specified center point coordinates and corner point
     * coordinates.  The framing rectangle is used by the subclasses of
     * <code>RectangularShape</code> to define their geometry.
     *
     * @param centerX the X coordinate of the specified center point
     * @param centerY the Y coordinate of the specified center point
     * @param cornerX the X coordinate of the specified corner point
     * @param cornerY the Y coordinate of the specified corner point
     * @since 1.2
     */
    public void setFrameFromCenter(double centerX, double centerY,
                                   double cornerX, double cornerY) {
        double halfW = Math.abs(cornerX - centerX);
        double halfH = Math.abs(cornerY - centerY);
        setFrame(centerX - halfW, centerY - halfH, halfW * 2.0, halfH * 2.0);
    }

    /**
     * Sets the framing rectangle of this <code>Shape</code> based on a
     * specified center <code>Point2D</code> and corner
     * <code>Point2D</code>.  The framing rectangle is used by the subclasses
     * of <code>RectangularShape</code> to define their geometry.
     * @param center the specified center <code>Point2D</code>
     * @param corner the specified corner <code>Point2D</code>
     * @since 1.2
     */
    public void setFrameFromCenter(java.awt.geom.Point2D center, java.awt.geom.Point2D corner) {
        setFrameFromCenter(center.getX(), center.getY(),
                           corner.getX(), corner.getY());
    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
//    public boolean contains(java.awt.geom.Point2D p) {
//        return contains(p.getX(), p.getY());
//    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
//    public boolean intersects(java.awt.geom.Rectangle2D r) {
//        return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
//    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
    public boolean contains(Rectangle2D r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * {@inheritDoc}
     * @since 1.2
     */
    public java.awt.Rectangle getBounds() {
        double width = getWidth();
        double height = getHeight();
        if (width < 0 || height < 0) {
            return new java.awt.Rectangle();
        }
        double x = getX();
        double y = getY();
        double x1 = Math.floor(x);
        double y1 = Math.floor(y);
        double x2 = Math.ceil(x + width);
        double y2 = Math.ceil(y + height);
        return new java.awt.Rectangle((int) x1, (int) y1,
                                      (int) (x2 - x1), (int) (y2 - y1));
    }

    /**
     * Returns an iterator object that iterates along the
     * <code>Shape</code> object's boundary and provides access to a
     * flattened view of the outline of the <code>Shape</code>
     * object's geometry.
     * <p>
     * Only SEG_MOVETO, SEG_LINETO, and SEG_CLOSE point types will
     * be returned by the iterator.
     * <p>
     * The amount of subdivision of the curved segments is controlled
     * by the <code>flatness</code> parameter, which specifies the
     * maximum distance that any point on the unflattened transformed
     * curve can deviate from the returned flattened path segments.
     * An optional {@link AffineTransform} can
     * be specified so that the coordinates returned in the iteration are
     * transformed accordingly.
     * @param at an optional <code>AffineTransform</code> to be applied to the
     *          coordinates as they are returned in the iteration,
     *          or <code>null</code> if untransformed coordinates are desired.
     * @param flatness the maximum distance that the line segments used to
     *          approximate the curved segments are allowed to deviate
     *          from any point on the original curve
     * @return a <code>PathIterator</code> object that provides access to
     *          the <code>Shape</code> object's flattened geometry.
     * @since 1.2
     */
//    public PathIterator getPathIterator(AffineTransform at, double flatness) {
//        return new FlatteningPathIterator(getPathIterator(at), flatness);
//    }

    /**
     * Creates a new object of the same class and with the same
     * contents as this object.
     * @return     a clone of this instance.
     * @exception  OutOfMemoryError            if there is not enough memory.
     * @see        java.lang.Cloneable
     * @since      1.2
     */
//    public Object clone() {
//        try {
//            return super.clone();
//        } catch (CloneNotSupportedException e) {
//            // this shouldn't happen, since we are Cloneable
//            throw new InternalError();
//        }
//    }
}

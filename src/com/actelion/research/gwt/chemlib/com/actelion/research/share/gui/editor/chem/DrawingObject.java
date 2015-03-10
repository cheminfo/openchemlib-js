package com.actelion.research.share.gui.editor.chem;


import com.actelion.research.share.gui.editor.geom.IDrawContext;

/**
 * Project:
 * User: rufenec
 * Date: 5/16/13
 * Time: 12:31 PM
 */

public abstract class DrawingObject
        implements Cloneable,IDrawingObject
{

//    Object o;
    private boolean selected = false;

    public final boolean isSelected()
    {
        return selected;
    }
    public final void setSelected(boolean set)
    {
        selected = set;
    }

    private boolean layouted =false;
    public boolean isLayouted()
    {
        return layouted;
    }

    public void setLayouted(boolean layouted)
    {
        this.layouted = layouted;
    }

    public abstract void paint(IDrawContext ctx);
//    public abstract boolean pointWouldSelect(Point2D pt);
	public abstract java.awt.geom.Rectangle2D getBoundingRect();
    public abstract void move(float dx, float dy);
   	public abstract void scale(float f);

    public Object clone()
    {
        try {
            return super.clone();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }


////	protected static final String DESCRIPTOR_START = "<DrawingObject";
////	protected static final String DESCRIPTOR_END = "></DrawingObject>";
////	protected static final String DESCRIPTOR_TYPE = " type=\"";
////
////    protected static final Color SELECTION_COLOR = UIManager.getColor("TextArea.selectionBackground");
//
//	protected Point2D[] mPoint;
//	protected boolean			mIsSelected,mProtectedFromDeletion;
//
//	protected float			mTransformationReferenceX,mTransformationReferenceY;
//	protected float			mTransformationValue1[];
//	protected float			mTransformationValue2[];
//
//	abstract public void draw(Canvas g, DepictorTransformation t);
//	abstract public void draw2D(Canvas g, DepictorTransformation t);
//	abstract public void hilite(Canvas g);
//	abstract public void clearHiliting();
//
//	/**
//	 * Checks, whether this drawing object contains the point at x,y
//	 * @param x
//	 * @param y
//	 * @return
//	 */
//	abstract public boolean contains(float x, float y);
//
//	abstract public boolean checkHiliting(float x, float y);
//	abstract public AbstractDrawingObject clone();
//
//	/**
//	 * Creates a string encoding all properties specific to this drawing object.
//	 * Its type is not part of the descriptor detail. The detail must start, but not
//	 * end with a space. Example: ' size="12" x="50.0" y="80.0"'
//	 * @return concatenated property list, each property with a leading space
//	 */
////	abstract public String getDescriptorDetail();
////	abstract public String getTypeString();
//
//
////	public static AbstractDrawingObject instantiate(String descriptor) {
////		return DrawingObjectFactory.createObject(descriptor);
////		}
//

//	public void move(float dx, float dy) {
//		if (mPoint != null) {
//			for (int i=0; i<mPoint.length; i++) {
//				mPoint[i].x += dx;
//				mPoint[i].y += dy;
//				}
//			}
//		}
//
//	public void scale(float f) {
//		if (mPoint != null) {
//			for (int i=0; i<mPoint.length; i++) {
//				mPoint[i].x *= f;
//				mPoint[i].y *= f;
//				}
//			}
//		}
//
//	public boolean isDeletable() {
//		return !mProtectedFromDeletion;
//		}
//
//	public void setDeletable(boolean d) {
//		mProtectedFromDeletion = !d;
//		}
//
//	public boolean isSelected() {
//		return mIsSelected;
//		}
//
//	public void setSelected(boolean s) {
//		mIsSelected = s;
//		}
//
//	public Geom.Rectangle2D getBoundingRect() {
//		if (mPoint == null)
//			return null;
//
//		Geom.Rectangle2D bounds = new Geom.Rectangle2D();
//		bounds.x = mPoint[0].x;
//		bounds.y = mPoint[0].y;
//
//		for (int i=1; i<mPoint.length; i++) {
//			if (bounds.x > mPoint[i].x) {
//				bounds.width += bounds.x - mPoint[i].x;
//				bounds.x = mPoint[i].x;
//				}
//			else if (bounds.width < mPoint[i].x - bounds.x) {
//				bounds.width = mPoint[i].x - bounds.x;
//				}
//			if (bounds.y > mPoint[i].y) {
//				bounds.height += bounds.y - mPoint[i].y;
//				bounds.y = mPoint[i].y;
//				}
//			else if (bounds.height < mPoint[i].y - bounds.y) {
//				bounds.height = mPoint[i].y - bounds.y;
//				}
//			}
//
//		return bounds;
//		}
//
//	public boolean isSurroundedBy(Shape shape) {
//		if (mPoint == null)
//			return false;
//
//		for (int i=0; i<mPoint.length; i++)
//			if (!shape.contains(mPoint[i]))
//				return false;
//
//		return true;
//		}
//
//	public void translateInit(float x, float y) {
//		mTransformationReferenceX = x;
//		mTransformationReferenceY = y;
//		if (mPoint != null) {
//			int pointCount = mPoint.length;
//			mTransformationValue1 = new float[pointCount];
//			mTransformationValue2 = new float[pointCount];
//			for (int i=0; i<pointCount; i++) {
//				mTransformationValue1[i] = mPoint[i].x;
//				mTransformationValue2[i] = mPoint[i].y;
//				}
//			}
//		}
//
//	public void translate(float x, float y) {
//			// overwrite this if only hilited parts of the object shall be moved
//		if (mPoint != null) {
//			for (int i=0; i<mPoint.length; i++) {
//				mPoint[i].x = mTransformationValue1[i] + x - mTransformationReferenceX;
//				mPoint[i].y = mTransformationValue2[i] + y - mTransformationReferenceY;
//				}
//			}
//		}
//
//	public void zoomAndRotateInit(float x, float y) {
//		mTransformationReferenceX = x;
//		mTransformationReferenceY = y;
//		if (mPoint != null) {
//			int pointCount = mPoint.length;
//			mTransformationValue1 = new float[pointCount];
//			mTransformationValue2 = new float[pointCount];
//			for (int i=0; i<pointCount; i++) {
//				float dx = x - mPoint[i].x;
//				float dy = y - mPoint[i].y;
//				mTransformationValue2[i] = (float)Math.sqrt(dx*dx+dy*dy);	// distance to center of gravity
//				mTransformationValue1[i] = Molecule.getAngle(x,y,mPoint[i].x,mPoint[i].y);
//				}
//			}
//		}
//
//	public void zoomAndRotate(float zoom,float angle) {
//		if (mPoint != null) {
//			for (int i=0; i<mPoint.length; i++) {
//				float newDistance = mTransformationValue2[i] * zoom;
//				float newAngle = mTransformationValue1[i] - angle;
//				mPoint[i].x = mTransformationReferenceX + newDistance*(float)Math.sin(newAngle);
//				mPoint[i].y = mTransformationReferenceY + newDistance*(float)Math.cos(newAngle);
//				}
//			}
//		}
//
////	public String getDescriptor() {
////		return DESCRIPTOR_START+" type=\""+getTypeString()+"\" "+getDescriptorDetail()+" "+DESCRIPTOR_END;
////		}
//
////	public String toString() {
////		StringBuffer objectString = new StringBuffer();
////        objectString.append(getDescriptor());
////		return objectString.toString();
////		}
//
}


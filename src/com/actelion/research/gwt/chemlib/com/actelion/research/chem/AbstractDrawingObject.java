/*
 * Project: GWTEditor
 * @(#)AbstractDrawingObject.java
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

package com.actelion.research.chem;

public class AbstractDrawingObject
{
    public static AbstractDrawingObject instantiate(String substring)
    {
        return null;
    }

    public String getDescriptor()
    {
        return "";
    }

    public Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }
}

/*
 * Project: GWTEditor
 * @(#)Log.java
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

package com.actelion.research.gwt.gui.viewer;

import com.google.gwt.core.shared.GWT;

/**
 * Project:
 * User: rufenec
 * Date: 7/15/2014
 * Time: 3:48 PM
 */
public class Log
{
    public static native void console(String text)
    /*-{
        console.log(text);
    }-*/;

    public static void println(String s)
    {
        System.out.println(s);
        GWT.log(s);
        console(s);
    }
}

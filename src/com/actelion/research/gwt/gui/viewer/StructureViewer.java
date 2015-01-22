/*
 * Project: GWTEditor
 * @(#)StructureViewer.java
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

import com.google.gwt.core.client.EntryPoint;
//import org.timepedia.exporter.client.ExporterUtil;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class StructureViewer implements EntryPoint
{
    /**
     * This is the entry point method.
     */
    public void onModuleLoad()
    {
//        ExporterUtil.exportAll();
//        GWT.create(StructureView.class);
        onLoadImpl();
    }

    private native void onLoadImpl() /*-{
       if ($wnd.structureViewLoaded && typeof $wnd.structureViewLoaded == 'function') $wnd.structureViewLoaded();
     }-*/;

}

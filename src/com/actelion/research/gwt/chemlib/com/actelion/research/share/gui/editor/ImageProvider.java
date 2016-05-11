/*
 * Project: DD_jfx
 * @(#)ImageProviderImpl.java
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

package com.actelion.research.share.gui.editor;

/**
 * Created by rufenec on 08/05/15.
 */
public abstract class ImageProvider<T>
{
    public abstract T getESRImage(boolean up);
    public abstract T getButtonImage(boolean up);
    public abstract int getESRImageRows();

}


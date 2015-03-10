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




package com.actelion.research.gwt.gui.editor.actions.dialogs;

import com.actelion.research.share.gui.DialogResult;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.actelion.research.gwt.gui.editor.Window;

/**
 * Project:
 * User: rufenec
 * Date: 7/4/2014
 * Time: 10:47 AM
 */
public abstract class TDialog extends DialogBox
{


    Window parent;
    String title;
    protected volatile DialogResult status = DialogResult.IDCANCEL;

    public TDialog(Window parent,String title)
    {
        this.parent = parent;
        this.title = title;
        setText(title);
          // Enable animation.
        setAnimationEnabled(true);
          // Enable glass background.
        setGlassEnabled(false);
        buildGUI(RootPanel.get());
    }

    protected abstract void buildGUI(RootPanel root);

    protected void onInitialUpdate()
    {

    }

    public void onClose(DialogResult res)
    {

    }

    public DialogResult doModal()
    {
        System.out.println("before doModal (center)");
        center();
        onInitialUpdate();
        show();
        System.out.println("doModal");
        return DialogResult.IDOK;
    }



    public void onOK()
    {
        status = DialogResult.IDOK;
        onClose(status);
        hide();
    }

    public void onCancel()
    {
        status = DialogResult.IDCANCEL;
        onClose(status);
        hide();
    }

}

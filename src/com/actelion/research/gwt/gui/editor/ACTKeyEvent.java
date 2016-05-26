/*

Copyright (c) 2015-2016, cheminfo

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.
    * Neither the name of {{ project }} nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package com.actelion.research.gwt.gui.editor;


import com.actelion.research.gwt.gui.viewer.Console;
import com.actelion.research.gwt.gui.viewer.Log;
import com.actelion.research.share.gui.editor.io.IKeyCode;
import com.actelion.research.share.gui.editor.io.IKeyEvent;
import com.google.gwt.event.dom.client.KeyEvent;


public class ACTKeyEvent<T extends KeyEvent> implements IKeyEvent
{

    public static final int FUNCTION = 1;
    public static final int NAVIGATION = 1 << 1;
    public static final int ARROW = 1 << 2;
    public static final int MODIFIER = 1 << 3;
    public static final int LETTER = 1 << 4;
    public static final int DIGIT = 1 << 5;
    public static final int KEYPAD = 1 << 6;
    public static final int WHITESPACE = 1 << 7;
    public static final int MEDIA = 1 << 8;

    T evt;
    int code;
    boolean shift;
    int mask = 0;

    public ACTKeyEvent(int code,T evt,int mask)
    {
        this.evt = evt;
        this.code = (code >= 'A' && code <='Z' && !shift) ? code + 32 : code;
        this.shift = evt.isShiftKeyDown();// shift;
        this.mask = mask;
    }

    public IKeyCode getCode()
    {

        ACTKeyCode c = new ACTKeyCode(code  ,"",mask);
//        System.out.println("Returning code: " + code);
        return c;
    }

    public String getText()
    {
        String s = "" + (char)(code);
//        System.out.println("getText " + s);
        return s;
    }

    @Override
    public boolean isControlDown() {
        return evt.isControlKeyDown();// .isControlDown();
    }

    @Override
    public boolean isShiftDown() {
        return evt.isShiftKeyDown();
    }


    @Override
    public boolean isAltDown() {
        return evt.isAltKeyDown();
    }


    public String toString()
    {
        return ("ACT Key Event " + code + " " + shift + " " + mask);
    }
}


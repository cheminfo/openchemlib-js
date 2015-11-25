/*

Copyright (c) 2015, cheminfo

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

package com.actelion.research.gwt.gui.viewer;

import com.actelion.research.chem.*;
import com.google.gwt.core.client.js.JsExport;
import com.google.gwt.core.client.js.JsNamespace;
import com.google.gwt.core.client.js.JsType;

import java.awt.geom.Rectangle2D;

/**
 * Created by rufenec on 20/03/15.
 */
@JsType
@JsNamespace("OCL")
@JsExport
public class SVGRenderer
{
    @JsExport
    public static String renderMolecule(String idCode,int width,int height)
    {
        try {
            StereoMolecule mol = new StereoMolecule();
            IDCodeParser p = new IDCodeParser(true);
            if (idCode != null) {
                String[] parts = idCode.split(" ");
                if (parts.length > 1) {
                    p.parse(mol, parts[0], parts[1]);
                } else
                    p.parse(mol, idCode);
                Canonizer can = new Canonizer(mol);
                String n = can.getIDCode();
                String c = can.getEncodedCoordinates();
                p.parse(mol, n,c);
                SVGDepictor depictor =new SVGDepictor(mol,null);
                depictor.validateView(null, new Rectangle2D.Float(0, 0, width, height), AbstractDepictor.cModeInflateToMaxAVBL);
                depictor.paint(null);
                return depictor.toString();
            }
        } catch (Exception e) {
            Log.println("error setting idcode data " + e);
        } finally {
        }
        return null;
    }
}

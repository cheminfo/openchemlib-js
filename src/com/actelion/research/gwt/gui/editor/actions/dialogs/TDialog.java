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

import com.actelion.research.gwt.gui.viewer.Log;
import com.actelion.research.share.gui.DialogResult;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
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

    private static final String cornerIE6 =
            "iVBORw0KGgoAAAANSUhEUgAAABoAAABDCAMAAAC1FeXDAAAAAXNSR0IArs4c6QAAADxQTFRFAAAAvb29vr6/psvwzMzMvNLnvtPnsbGxksHww9nuwtz1yuH13t/i3uDi0OT28vX68/X69Pb6////u7u7tq5AQwAAAAF0Uk5TAEDm2GYAAAABYktHRACIBR1IAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH2QEQDiMAIKvDAwAAAMxJREFUOMvV1GETgiAMBuChK0jUIv7/fw0XUjK2us4++H7bPTdvTgQAZgo80yhy2Syo3BQSWYWMQgheJqUL2jMRQfNNPpH8wPmbCa1CRiEUyMt0YrTaxXIi8+cknMBSknACQ0EEToCUnxal7VB85SPSul3b+iimhBG+sqVYwnYY7zkasTEOQCX/X29K2W595tHEKYQpRnbmU0+gLH318CEUq2i85Yw1ddeSjv964qU3rNUg3mxLn3R6k+1BKY7SmLB3OT0jV1KTe8se9AD3aUjt61de2QAAAABJRU5ErkJggg==";
    private static final String corner =
            "iVBORw0KGgoAAAANSUhEUgAAABoAAABDCAYAAACCyxXxAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH2AQPDhkFfq7y6QAABAFJREFUWMPtmF9IW1ccxz83CcGmxbnoYrJZMknWEJ1MGlrbSpUWM1YfzMtenOwxzKe97rG4N1/3VObjsHsYDO5gLXIdxQxdpyhiiNLNKNJBrW3Rxj91lyR3D57Yu9D8uzeRMfzC4ebe+z33k985v/O7lyMt/nUAIAFDQATo5kjTgAyMA1o6ayEnmyWb+3kH6AKaxflT4HdgGKCzxfG6j4DcFk2vT0XzAyMZDU3fCVCAdsCj6+MRUB8Q1j/MIiLJh+h1GxjaXJwgnYX0UTCjb4DoYe3AD7IsR/WgCKUVAXi+NMHzpQmAjwtA9LCLgF+W5aHcKHSXAcr3NJfR54yAIcvytgVj8lToec8isquUpjEpi0jhUpKrARoHRop4RoTHlGyAJh62WmzBViMiAF7sZ9vUjOYU1yxqRnO+2M+25Xd4df6m4Yik1KE23njWMqi/YbdKzY1npZupQ+39+jppKBdVOmswomd72dH6OmmwkKG+Thp8tpcdBS5vu2+QzhgbRctbZ6RQKZPwfPT25oPLGYMR2exWqb2USXj8AE1bD1o4HzY0R+XqoqmsUzNaopSpHE9J0MtX2nwpU+plas006J1zlq9Sh9r3hQy7+4c/7q/PeqtSGbaXfxlPt3wonWtsduWSQ81oib1U6o/dtVmfeAsvAE8AQ9HZYkmV/quf/JyYuf/u7uO4H9AnsF8cF4BdYF8cjWXdveUD+q/dGkvM3I8W8K2KYxyYNVXr7i0f0H7t1ph4qL4tieH6FXhoeI56fPbjmrL3dxpv6M2LcWNekapSvcvVRkOv4awrVwqw4N2ZWs37ZKtNRCc2dKegU5Bp2TbmFamjo4N4PK7UFtTQy8Zj8B5V6NqBAPrbHCRmjit0bUBBdU5bXwSHw1HUuGK/dFxUvfCtoYj6+vqKmiYnJ6szdOXIuzOlO/sPF9WKXxO61/ppCfq/1bpqrZOSoHA4LIndjVxmnUh6L5wUaLXmoEgkgqIoN4ArQKu4vA48DIfDn30ztYPpoqooyhgQAEK+DwIOl8sNwNbWZmvyz0fNiqLEUOcerdgvRc1GFACuX+3+9+euy+XG5XI7fpueum56HSmKchcI5UP0EvdCQXXurpkFe8XnDzg0DYo1nz/gEPNnDGS12pxNLjea2IMp1JpcbqxWm9PwHDkvdDWsbacBokAIGOD17uET4CdgHhhzXuiCpGoqGXL715159z3AF8Ci+BPDZuYoWgCiV6fwRM2AQiUgeljIDGigAv+AGZCnAr/HTGWQAGJJdZij/dSgbojmgRVgusdnv2O6qMaS6nd5gJxCogVjSbW7x2f/3PDQxZLq1wUg+cCg8Bqeo9YysykEtH7Z22AYFKzAHzS7jsqV4XX0DwYjSXoAIPcbAAAAAElFTkSuQmCC";
    private static final String hborderIE6 =
            "iVBORw0KGgoAAAANSUhEUgAAACAAAAhtCAMAAADEjd0TAAAAAXNSR0IArs4c6QAAAH5QTFRFLUp2ME17NFWHOFySPGKeO2uuQWusPW6yRXK1Q3S7SHnASXvFksHwvby9lcPxmcXxxMTEpMzzq9D0zMzMt9b2utn2vtv319fXxd/43t7e1d/2zOL40OT22eP33ub35ubm5Or45+357+/v7fD38fHx8vT59vf2+Pn8+vv9/////Tw4xQAAAAlwSFlzAAALEgAACxIB0t1+/AAAAAd0SU1FB9sCGQ4zNUu6gSAAAAHKSURBVHja7d1HbtwwAEBRpvfiOInjNLe43f+CWeQAH4gGBGf8/votKIqgtBE1fkYD+Ac+RuMkGqfReBeN+2gGyEGuMFHX0RJg+724ixrcRuMmanAZ7QCcRTNALloAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAICNIL+1nwDySIHt4DjaAchzD7YDixYAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA/hus8Bfyq2gJcBHtBziP9gP8jvYD/IqWAN8jAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACAeeBH1OBb1OBrNL5E43PU4FO0A3AUNfgQNXgfjbcRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADwUcB/tANxFDW6jGeA6mgGuoiXARdTgPAIAAACAwwH5HnUZAQ8LnEUHAk6jJcBJNLJH0XgcjSfReBqN59F4GY1X0XgdjTfRDJCDnAByJlcAuR5eREuAZ1GD3MT+RDNADvI4WuIqDgPk82Iz+AvBNphaM1gwGgAAAABJRU5ErkJggg==";
    private static final String hborder =
            "iVBORw0KGgoAAAANSUhEUgAAACAAAAuOCAYAAABSa16QAAAAAXNSR0IArs4c6QAAAAlwSFlzAAALEgAACxIB0t1+/AAAAAd0SU1FB9sDCQ8rHZCvpZsAAAddSURBVHja7drxaRRBFAfg2dtJNBsRG7AEwTosyGJsxWtBsQAJHAj5+46wXEJmxjJ+gfmugf34vTfvzS63/Pm3jxL8HUr4BwAAEAfU06+fxyiglPI7DfgbBWzb9iMJWMYYltHkc+B4PGYB+74bRJMPoqf9xSDKHsPsJiilhp+f34Z19GwGtY84oIcBLQ0YbfYE0oDW0yXo6QTa7An0dAItPgnTJRht9mUUvo+4FQMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAlPrw8DB5AoflEAasacAhDFjXNQuoacAhnkCtk/cAwJqeA8th8knoQgIAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQByznp5eRBNQxsgnU8PNLLdMnMMJNoAk14RvoASUIRxBfx5rQHNCEb+A+oAQuJCahJvRioglNwtlL4DshAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQH2uWUNtI5zAaw8DWh+TJ/CaTqC12RPQA47ha4uXwDGcfBm5kgEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAxAHLGGMkATX8/FLDz38DCfTpE9AD8QS6HtADeiCfgCbUhHpg8gS8GQEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAOT/SXW5XPQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAED0V1trkycwxgAAmBzQe1cCAIC5Afl1fL1eo4Dly7fv0RrUdb3JlqC+/xAGvLsPA27DCdzcfUyX4C4LKOlTsNbbLGBJJ1DGEgaU+Dqe/lacLkHp7oTpEhTvhgaREjiG6QTyX8lauAR98s/1AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABxwPL4+DiSgLptmx6YG1D3fc+eglLK1zTgUxRwPp+HUwAAABDdBafTKb4LPkcTKKU8pxO4TwL+A3SZiJzgbXvZAAAAAElFTkSuQmCC";
    private static final String vborderIE6 =
            "iVBORw0KGgoAAAANSUhEUgAAAC0AAAAgBAMAAAB0qux3AAAAAXNSR0IArs4c6QAAAB5QTFRFu7u7xsbG1dXV4ODg0OT25+fn7+/v8fHx9PT0////5hugPAAAAAlwSFlzAAALEgAACxIB0t1+/AAAAAd0SU1FB9sCGQ4tO3hDk/gAAAAlSURBVCjPY3ABAkdlzpkThMzCy8tLk8qBoEipgWFUfFR8BIgDALvT/0EuDVlsAAAAAElFTkSuQmCC";
    private static final String vborder =
            "iVBORw0KGgoAAAANSUhEUgAAAC0AAAAgCAYAAACGhPFEAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAABx0RVh0U29mdHdhcmUAQWRvYmUgRmlyZXdvcmtzIENTNXG14zYAAACkSURBVFiF7dI7CgIxFIXhM5bpLNOYHWQV02d/bsElmC0oktomIKScygtW2gmCGGTwDML52rx+LhlOF7ujox72GcARwNk5tx3H8WU95wwze+5JKe1aa5Nz7u19Zgbv/bqUMoUQes+j1ooY4wbADcB11T3xG91BfbJU9CyK/oK+x19YKnqYc1iTZlE0i6JZFM2iaBZFsyiaRdEsimZRNIuiWRTN8gCbWiVKRnlovQAAAABJRU5ErkJggg==";
    Window parent;
    String title;
    protected volatile DialogResult status = DialogResult.IDCANCEL;

    public TDialog(Window parent, String title)
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
        setStyles();
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

    private native NodeList querySelector(String selectors)/*-{
        return $doc.querySelectorAll(selectors);
    }-*/;


    private void setStyle(String clz, String value)
    {
        NodeList<Element> list;
        list = querySelector(clz);
        for (int i = 0; i < list.getLength(); i++) {
            Element n = list.getItem(i);
            n.setAttribute("style", value);
        }
    }

    private void setStyles()
    {

        setStyle(".gwt-Button ", "padding: 4px");
        setStyle(".gwt-ComboBox > option ", "padding: 2px");
        setStyle(".gwt-ComboBox, .gwt-TextBox, .gwt-Button ", "font: 12px sans-serif");
        setStyle(".gwt-Label ", "font: 12px sans-serif");

        setStyle(".gwt-DialogBox .dialogTopLeft ",
                "background: url(data:image/png;base64," + corner + ") no-repeat -13px 0px;" +
                        "-background: url(data:image/png;base64," + cornerIE6 + ") no-repeat -13px 0px");

        setStyle(".gwt-DialogBox .dialogTopRight ",
                "background: url(data:image/png;base64," + corner + ") no-repeat -18px 0px;" +
                        "-background: url(data:image/png;base64," + cornerIE6 + ") no-repeat -18px 0px");

        setStyle(".gwt-DialogBox .dialogBottomLeft ",
                "background: url(data:image/png;base64," + corner + ") no-repeat 0px -15px;" +
                        "-background: url(data:image/png;base64," + cornerIE6 + ") no-repeat 0px -15px;");

        setStyle(".gwt-DialogBox .dialogBottomRight ",
                "background: url(data:image/png;base64," + corner + ") no-repeat -5px -15px;" +
                        "-background: url(data:image/png;base64," + cornerIE6 + ") no-repeat -5px -15px");

        setStyle(".gwt-DialogBox .dialogBottomLeftInner ", "width: 5px;height: 8px;overflow: hidden");

        setStyle(".gwt-DialogBox .dialogBottomRightInner ", "width: 5px;height: 8px;zoom: 1;overflow: hidden");

        setStyle(".gwt-DialogBox .dialogContent ", "font: 12px sans-serif");

        setStyle(".gwt-DialogBox .dialogTopLeftInner ", "width: 5px;overflow: hidden;zoom: 1");
        setStyle(".gwt-DialogBox .dialogTopRightInner ", "width: 8px;zoom: 1;overflow: hidden");
        setStyle(".gwt-DialogBox .dialogTopCenter ", "font: 16px sans-serif;padding: 2px;background: #D0E4F6");
        setStyle(".gwt-DialogBox .dialogMiddleCenter ", "background: #cdcdcd");
        setStyle(".gwt-DialogBox .dialogBottomCenter ",
                "background: url(data:image/png;base64," + hborder + ") repeat-x 0px -4px;" +
                        "-background: url(data:image/png;base64," + hborderIE6 + ") repeat-x 0px -4px");

        setStyle(".gwt-DialogBox .dialogMiddleLeft ",
                "background: url(data:image/png;base64," + vborder + ") repeat-y");
        setStyle(".gwt-DialogBox .dialogMiddleRight ",
                "background: url(data:image/png;base64," + vborder + ") repeat-y -4px 0px;" +
                        "-background: url(data:image/png;base64," + vborderIE6 + ") repeat-y -4px 0px;");
    }
}

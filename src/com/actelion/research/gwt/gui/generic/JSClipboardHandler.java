package com.actelion.research.gwt.gui.generic;

import java.util.ArrayList;

import com.actelion.research.chem.MolfileParser;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.reaction.Reaction;
import com.actelion.research.gui.clipboard.IClipboardHandler;
import com.google.gwt.core.client.JavaScriptObject;

public class JSClipboardHandler implements IClipboardHandler {
	private JavaScriptObject mJsHandler;

  public JSClipboardHandler(JavaScriptObject jsHandler) {
    mJsHandler = jsHandler;
  }

  private JavaScriptObject getJsHandler() {
    return mJsHandler;
  }

  public native StereoMolecule pasteMolecule()
  /*-{
    var handler = this.@com.actelion.research.gwt.gui.generic.JSClipboardHandler::getJsHandler()();
    var jsMolecule = handler.pasteMolecule();
    if (jsMolecule) {
      return jsMolecule.@com.actelion.research.gwt.minimal.JSMolecule::getStereoMolecule()();
    } else {
      return null;
    }
  }-*/;

  public StereoMolecule pasteMolecule(boolean prefer2D, int smartsMode) {
    Object test = null;
    System.out.println("test: " + test.toString());
    return null;
  }

  public ArrayList<StereoMolecule> pasteMolecules() {
    Object test = null;
    System.out.println("test: " + test.toString());
    return null;
  }

  public Reaction pasteReaction() {
    Object test = null;
    System.out.println("test: " + test.toString());
    return null;
  }

  public boolean copyMolecule(String molfile) {
    Object test = null;
    System.out.println("test: " + test.toString());
	  StereoMolecule m = new StereoMolecule();
	  MolfileParser p = new MolfileParser();
	  p.parse(m, molfile);
    return copyMolecule(m);
	}

  public native boolean copyMolecule(StereoMolecule mol)
  /*-{
    var handler = this.@com.actelion.research.gwt.gui.generic.JSClipboardHandler::getJsHandler()();
    var jsMolecule = @com.actelion.research.gwt.minimal.JSMolecule::new(Lcom/actelion/research/chem/StereoMolecule;)(mol);
    return handler.copyMolecule(jsMolecule);
  }-*/;

  public boolean copyReaction(Reaction r) {
    Object test = null;
    System.out.println("test: " + test.toString());
    return true;
  }

  public boolean copyReaction(String ctab) {
    Object test = null;
    System.out.println("test: " + test.toString());
    return true;
  }

  public boolean copyImage(java.awt.Image img) {
    Object test = null;
    System.out.println("test: " + test.toString());
    return true;
  }

  public java.awt.Image pasteImage() {
    Object test = null;
    System.out.println("test: " + test.toString());
    return null;
  }
}

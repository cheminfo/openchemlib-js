package com.actelion.research.gwt.gui.generic;

import com.actelion.research.chem.Canonizer;
import com.actelion.research.chem.MolfileParser;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.reaction.Reaction;
import com.actelion.research.chem.reaction.ReactionEncoder;
import com.actelion.research.gui.clipboard.IClipboardHandler;
import com.google.gwt.core.client.JavaScriptObject;

import java.util.ArrayList;

public class JSClipboardHandler implements IClipboardHandler {
  private final JavaScriptObject mJsHandler;

  public JSClipboardHandler(JavaScriptObject jsHandler) {
    mJsHandler = jsHandler;
  }

  private JavaScriptObject getJsHandler() {
    return mJsHandler;
  }

  public native boolean putStringJS(String data)
  /*-{
    var clipboard = this.@com.actelion.research.gwt.gui.generic.JSClipboardHandler::getJsHandler()();
    return clipboard.putString(data);
  }-*/;

  // Can't be implemented here
  // Clipboard is not accessible with sync API
  public StereoMolecule pasteMolecule() {
    return null;
  }

  // Not used, No implementation needed yet
  public StereoMolecule pasteMolecule(boolean prefer2D, int smartsMode) {
    return null;
  }

  // Not used, No implementation needed yet
  public ArrayList<StereoMolecule> pasteMolecules() {
    return null;
  }

  // Can't be implemented here
  // Clipboard is not accessible with sync API
  public Reaction pasteReaction() {
    return null;
  }

  // Not used, No implementation needed yet
  public boolean copyMolecule(String molfile) {
    StereoMolecule m = new StereoMolecule();
    MolfileParser p = new MolfileParser();
    p.parse(m, molfile);
    return copyMolecule(m);
  }

  public boolean copyMolecule(StereoMolecule mol) {
    final Canonizer canonizer = new Canonizer(mol);
    final String idCode = canonizer.getIDCode();
    final String coordinates = canonizer.getEncodedCoordinates(true);

    final String serializedData = idCode + " " + coordinates;
    return putStringJS(serializedData);
  }

  public boolean copyReaction(Reaction r) {
    final String[] data = ReactionEncoder.encode(r, true);
    final String serializedData = String.join(ReactionEncoder.OBJECT_DELIMITER_STRING, data);

    return putStringJS(serializedData);
  }

  // Not used yet, no implementation needed
  public boolean copyReaction(String ctab) {
    return true;
  }

  // Not used yet, no implementation needed
  public boolean copyImage(java.awt.Image img) {
    return true;
  }

  // Not used yet, no implementation needed
  public java.awt.Image pasteImage() {
    return null;
  }
}

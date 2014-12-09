package org.cheminfo.actelion.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.RootPanel;

public class ActelionDev implements EntryPoint {

	@Override
	public void onModuleLoad() {
		
		Molecule mol;
		try {
			mol = Molecule.fromSmiles("CCCO");
			GWT.log("OK");
			Element el = RootPanel.get("actContent").getElement();
			el.setInnerHTML(mol.toMolfile());
		} catch (Exception e) {
		}
		
	}

}
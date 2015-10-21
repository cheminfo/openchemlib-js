package com.actelion.research.gwt.core;

import com.actelion.research.chem.prediction.DruglikenessPredictor;
import com.google.gwt.core.client.js.*;

@JsType
@JsNamespace("OCL")
@JsExport("DruglikenessPredictor")
public class JSDruglikenessPredictor {
	private static Services services = Services.getInstance();

	public static double DRUGLIKENESS_UNKNOWN = DruglikenessPredictor.cDruglikenessUnknown;

	public static double assessDruglikeness(JSMolecule molecule) {
		return services.getDruglikenessPredictor().assessDruglikeness(molecule.getStereoMolecule(), services.getThreadMaster());
	}
}

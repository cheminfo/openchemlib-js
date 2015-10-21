package com.actelion.research.gwt.core;

import com.actelion.research.chem.prediction.ToxicityPredictor;
import com.google.gwt.core.client.js.*;

@JsType
@JsNamespace("OCL")
@JsExport("ToxicityPredictor")
public class JSToxicityPredictor {
	private static Services services = Services.getInstance();

	public static int RISK_UNKNOWN = ToxicityPredictor.cUnknownRisk;
	public static int RISK_NO = ToxicityPredictor.cNoRisk;
	public static int RISK_LOW = ToxicityPredictor.cLowRisk;
	public static int RISK_HIGH = ToxicityPredictor.cHighRisk;

	public static int TYPE_MUTAGENIC = ToxicityPredictor.cRiskTypeMutagenic;
	public static int TYPE_TUMORIGENIC = ToxicityPredictor.cRiskTypeTumorigenic;
	public static int TYPE_IRRITANT = ToxicityPredictor.cRiskTypeIrritant;
	public static int TYPE_REPRODUCTIVE_EFFECTIVE = ToxicityPredictor.cRiskTypeReproductiveEffective;

	public static int assessRisk(JSMolecule molecule, int riskType) {
		return services.getToxicityPredictor().assessRisk(molecule.getStereoMolecule(), riskType, services.getThreadMaster());
	}
}

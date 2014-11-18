package org.cheminfo.actelion.test;

import org.cheminfo.actelion.client.Molecule;

public class BigmolTest {
		
	public static void test() throws Exception {
		
		String idcode = "f\\VIpA~`@@@A`A@hM@XOADLaTN`LC`|@PbLQrIQjKPzDpVFpNIq^OqALHQAHiCHyOIeJiuIhmGhCDXSJYKMY[OXGBxwAxoMyHE`jDpeDXgDDheTneLmd\\`TbbTrnUjkUzhtfftNet^kuAlMQnLIcLy`lelmuimmkl}d\\Sf\\Ki][o]Gl|Wa|oc|o}`ZCpYBhWBDTbTZcL]c\\_bBRRrQRj]SzXsfZrvUr^WrAXKQ^KI]JYPjeRju^km[k}XZcVZKUZ[[[G\\{W^zOSzPF`|GpyGh{FxtfTvfLyg\\gBtWdbbbTRTfJbvRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVu@d@PRFpnCHEDXsAx_GDdbTJet~`lUf\\kc|@TbtQRZWraRjMUZ{PFPvflsVFtNqqn]w^grAHMa|HqVNIiKiCLywIEXoebjuNmMyh]Sn}okCD\\SrYs^]UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUTr`BPDHBhBxBdBlDBDRDZBfDvDnDaBIDYDEDMB}DcDsD{BWDOD_DPbhdxdddlbBdRdJdFbvdnd~dqbYdEdUd]bcdsdkdgbOd_d@THRxTdTtT|RRTJTZTVRnT~TaTiRETUTMTCRsTkT{TwR_T@tPtXrdtttltbrJtZtFtNr~tatqtyrUtMt]tSrkt{tgtor@LPLHLDJtLlL|LrJZLFLVL^JaLqLiLeJML]LCLKJ{LgLwLJPlHlXlTjll|lbljjFlVlNlAjqlilyluj]lClSl[jglwlol`ZH\\X\\D\\LZ|\\b\\r\\zZV\\N\\^\\@@";
		
		Molecule mol1 = Molecule.fromIDCode(idcode);
		
		System.out.println(mol1.toSmiles());
		
		
	}

	public static void main(String[] args) throws Exception {
		test();	}

}

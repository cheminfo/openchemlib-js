package org.cheminfo.actelion.test;

import org.cheminfo.actelion.client.Molecule;

public class BigmolTest {
		
	public static void test() throws Exception {
		
	//	String idcode = "f\\VIpA~`@@@A`A@hM@XOADLaTN`LC`|@PbLQrIQjKPzDpVFpNIq^OqALHQAHiCHyOIeJiuIhmGhCDXSJYKMY[OXGBxwAxoMyHE`jDpeDXgDDheTneLmd\\`TbbTrnUjkUzhtfftNet^kuAlMQnLIcLy`lelmuimmkl}d\\Sf\\Ki][o]Gl|Wa|oc|o}`ZCpYBhWBDTbTZcL]c\\_bBRRrQRj]SzXsfZrvUr^WrAXKQ^KI]JYPjeRju^km[k}XZcVZKUZ[[[G\\{W^zOSzPF`|GpyGh{FxtfTvfLyg\\gBtWdbbbTRTfJbvRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVRtfTRrVdrbVu@d@PRFpnCHEDXsAx_GDdbTJet~`lUf\\kc|@TbtQRZWraRjMUZ{PFPvflsVFtNqqn]w^grAHMa|HqVNIiKiCLywIEXoebjuNmMyh]Sn}okCD\\SrYs^]UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUTr`BPDHBhBxBdBlDBDRDZBfDvDnDaBIDYDEDMB}DcDsD{BWDOD_DPbhdxdddlbBdRdJdFbvdnd~dqbYdEdUd]bcdsdkdgbOd_d@THRxTdTtT|RRTJTZTVRnT~TaTiRETUTMTCRsTkT{TwR_T@tPtXrdtttltbrJtZtFtNr~tatqtyrUtMt]tSrkt{tgtor@LPLHLDJtLlL|LrJZLFLVL^JaLqLiLeJML]LCLKJ{LgLwLJPlHlXlTjll|lbljjFlVlNlAjqlilyluj]lClSl[jglwlol`ZH\\X\\D\\LZ|\\b\\r\\zZV\\N\\^\\@@";
	//	String idcode = "gmhMHx@@D@bpDj]@RKUV`rXnVjuzkPG@ptaXeQYln@bTlwHhdmLhhlhhdhjhhheDd]ihlhhdhdTeEIhfhhXmDhdh\\hcBE@iT^@jWKMv|aVjfjZijfjZBZBJZ`hFbjjfjjZjjfZii``@@";
		
		String idcode = "f]ZOWp^A@@F@HCAxF`l@PrMPfNpNGpaNIeIhSCYoOxH`ddjdlme|lUroTFntAfLIkMu`\\ce]kc]ge}_lBXWBdSbB^Rf^r^[raPju\\[s]@x@adOaRAQZKqAFHIGIcFYkGXgJxPnDhbetgeReTzbu^guQiMioLEbmMklsh}wc|@UCDZbLVRj_RVXKq[KmWjcZ[KP@DP@Hl`Py`@{@G~BDBDFTHrQIJJIQJJYSZJZJKQIQQJYQRqZJIZJIQIQEJNJYJJ[QIKQQJIKSJJKQJJKQReSVKSPjqQJXq[YJISJFFHiISJJKJKIJKQIIQJYIQIPkIQPj[QIIQFIIJJJZNIRqIQZJIFKQIQJFZIQISJJZJZJIZIJJKSJJTqQKSIQEQQSIQFIIZYKYJJIuSIQl]c\\GajHrzBqV]p~_r~OswAxdfetmg\\ggJkTjmTzwVjjjZYijjjijiijVjjfjijZifBfBhJhBjFj@ZZjfJjjjfffjjiiijZjfjZfjfjjfjjZfjfjZjjZjjZfjijijfijjjZjfii`jj@fj`FiXjfjfjfBfjBjhZZjfij@jBJyA@U@TPbPQPePThbhShghTdgdbTRtV\\S\\`|PBTrUZazUVcvgNb^`~dAdaVacQgQTqdIPYdYUYcyPece`@@";
		String idcoord = "!B@WtmAeIyhK~oRtFT`XrQ^uEKPUR^_zBetmAoz}etm^eHFk[eRARuyKWe~`eIyhIRAoz}KP[~oRuzkuRAeHFTgdm^UIyhHm^UIybHmAUIyxaf^Poz}T`ZMvde]ktmAoz}T`ZAWVjJpd{~`eIyhJMq[w~Tgdm^RtETgg~`eIykSeRAoz}T`XmAoy]KWiRAoz}kw~oUIyhIR^RuyKWdm^_zBTgeR^UIyKWeRAbtFkuRAoz}KWk~oRuzT`[~o_z}T`YR^_zBKWdm^UIyTgg~HbtEKWg~HbtETgg~`eIyllTvHoz}T`YRAbtFKP[~oRtFkvrvhK~hIR^RuyhHmARuyT`[~oRtFktmAoy]KWiR^ZtyTodm^RuygtmAjtFLggmvRuy`Jm^_z}KP[~oUIyKWeRAoz}KPXm^RtFKWdmAozBKWg~oRuyTgg~oRtFktm^RuyTgdmAeIyKWeRAoz}KPYR^_zBKWeRAbtFKPYR^RuyhHm^_z}T`XmAbtFxmeR^_zBKWdm^SHFgtmAbtFdcdop_zBKWg~oUKzKPXm^UIyKWeRAoz}KPYRARuy_zG~WUIzKPYR^_zBKWeRAbtFKPYR^_zBKWg~`buyhJrvhwZTgeIHcXbkuRAeIyhK~oUHFKWgbQ_zBLck~oUHFKP[~HbuykXYR^gy}Tgg~`buyT`XmAeHFkw~`eIyKWeR^_z}T`Xm^eHFkuR^_zBbHmAUIyT`[~oUHFKPXmAgz}T`[~oUIyKWg~`eIyhHmA_zBTgeR^UHFktmAbtFT`YRAoy]KWiRAnIfgcw~`eIyTgdm^_zBTgfAHfjupcG~oUHFKWiRAoz}T`ZmQbuyTgg~`oxbKPUR^_zBhK~`btFkuRAbtFkuRAeHFT`XmAhwEllTmAUIyhK~`cY]RUw~WRuzT`[~oUHFKWdm^oxbKPTmAjuygvmA`";
		Molecule mol1 = Molecule.fromIDCode(idcode, idcoord);
		
		System.out.println(mol1.getIDCode());
		System.out.println(mol1.toMolfile());
		
	}
	

	public static void main(String[] args) throws Exception {
		test();
	}

}

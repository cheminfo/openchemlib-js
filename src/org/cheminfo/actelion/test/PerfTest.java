package org.cheminfo.actelion.test;

import org.cheminfo.actelion.client.Molecule;

public class PerfTest {
		
	public static void testIndex() throws Exception {
		
		String smiles = "c1c(Cl)ccc(C)c1";
		
		Molecule mol1 = Molecule.fromSmiles(smiles);
		
		long time = System.currentTimeMillis();
		
		for(int i = 0; i < 100; i++) {
			int[] idx = mol1.getIndex();
		}
		
		System.out.println(System.currentTimeMillis()-time);
		
		
	}

	public static void main(String[] args) throws Exception {
		testIndex();	}

}

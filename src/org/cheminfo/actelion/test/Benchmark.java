package org.cheminfo.actelion.test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.cheminfo.actelion.client.*;

public class Benchmark {
	
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static Molecule[] parseSDF(String data) {
		Molecule[] molecules = new Molecule[10000];
		SDFileParser parser = new SDFileParser(data, null);
		
		int i = 0;
		while(parser.next()) {
			molecules[i++] = parser.getMolecule();
		}
		return molecules;
	}

	public static void main(String[] args) throws Exception {
		String data = readFile("data/10k.sdf", StandardCharsets.UTF_8);
		Molecule[] molecules = parseSDF(data);
		
		long time = System.currentTimeMillis();
		for(int k = 0; k < 100; k++) {
			Molecule query = Molecule.fromSmiles("CC(=O)CNC");
			query.setFragment(true);
			SSSearch searcher = new SSSearch();
			searcher.setFragment(query);
			ArrayList<Molecule> result = new ArrayList<Molecule>();
			for (int i = 0; i < 10000; i++) {
				searcher.setMolecule(molecules[i]);
				if (searcher.isFragmentInMolecule()) {
					result.add(molecules[i]);
				}
			}
		}
		System.out.println(System.currentTimeMillis()-time);
		
	}
	
}

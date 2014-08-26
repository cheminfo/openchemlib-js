package org.cheminfo.actelion.test;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.io.SDFileParser;

import org.cheminfo.actelion.client.Molecule;

public class SDFParserTest {
	
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static ArrayList<Molecule> parseString(String data) throws IOException {
		ArrayList<Molecule> list = new ArrayList<Molecule>();
		SDFileParser parser = new SDFileParser(new StringReader(data));
		
		while(parser.next()) {
			addMolecule(list, parser.getMolecule(), parser.getMoleculeName());
		}
		return list;
	}
	
	public static void addMolecule(ArrayList<Molecule> array, StereoMolecule mol, String name) {
		array.add(new Molecule(mol));
	}

	public static void main(String[] args) throws Exception {
		String data = readFile("data/10k.sdf", StandardCharsets.UTF_8);
		long time = System.currentTimeMillis();
		for(int i = 0; i < 10; i++) {
			parseString(data);
		}
		System.out.println((System.currentTimeMillis()-time)/10);
	}
	
}

// to run this code: bash buildOpenChemLib && java -classpath build com.Test

package com;

import java.io.IOException;
import java.util.TreeMap;

import com.actelion.research.chem.Canonizer;
import com.actelion.research.chem.CanonizerUtil;
import com.actelion.research.chem.SmilesParser;
import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.contrib.HydrogenHandler;
import com.actelion.research.chem.contrib.DiastereotopicAtomID;
import com.actelion.research.chem.io.SDFileParser;
import java.io.File;


public class Test {

  public static void main(String[] args) throws Exception {

    SmilesParser sp = new SmilesParser();
    StereoMolecule mol = new StereoMolecule();
    sp.parse(mol, "CC");

    System.out.println(mol.getIDCode());
    mol.stripStereoInformation();
    System.out.println(mol.getIDCode());
    System.out.println(CanonizerUtil.getIDCode(mol, CanonizerUtil.IDCODE_TYPE.NORMAL, false));
    System.out.println(CanonizerUtil.getIDCode(mol, CanonizerUtil.IDCODE_TYPE.NOSTEREO, false));
    System.out
        .println(CanonizerUtil.getIDCode(mol, CanonizerUtil.IDCODE_TYPE.NOSTEREO_TAUTOMER, false));

  }
}

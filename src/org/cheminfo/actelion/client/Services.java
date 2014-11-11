package org.cheminfo.actelion.client;

import com.actelion.research.chem.CoordinateInventor;
import com.actelion.research.chem.IDCodeParser;
import com.actelion.research.chem.MolfileParser;
import com.actelion.research.chem.SSSearcherWithIndex;
import com.actelion.research.chem.SmilesCreator;
import com.actelion.research.chem.SmilesParser;

public class Services {

	private static Services instance = null;
	
	private SmilesParser smilesParser = null;
	private SmilesCreator smilesCreator = null;
	private MolfileParser molfileParser = null;
	private IDCodeParser idcodeParser = null;
	private IDCodeParser idcodeParserWithCoordinates = null;
	private CoordinateInventor coordinateInventor = null;
	private SSSearcherWithIndex sSSearcherWithIndex = null;
	
	private Services() {}
	
	public static Services getInstance() {
		if (instance == null) {
			instance = new Services();
		}
		return instance;
	}
	
	public SmilesParser getSmilesParser() {
		if (smilesParser == null) {
			smilesParser = new SmilesParser();
		}
		return smilesParser;
	}
	
	public SmilesCreator getSmilesCreator() {
		if(smilesCreator == null) {
			smilesCreator = new SmilesCreator();
		}
		return smilesCreator;
	}
	
	public MolfileParser getMolfileParser() {
		if(molfileParser == null) {
			molfileParser = new MolfileParser();
		}
		return molfileParser;
	}
	
	public IDCodeParser getIDCodeParser(boolean ensureCoordinates) {
		if(ensureCoordinates) {
			if(idcodeParserWithCoordinates == null) {
				idcodeParserWithCoordinates = new IDCodeParser(true);
			}
			return idcodeParserWithCoordinates;
		} else {
			if(idcodeParser == null) {
				idcodeParser = new IDCodeParser(false);
			}
			return idcodeParser;
		}
	}
	
	public CoordinateInventor getCoordinateInventor() {
		if(coordinateInventor == null) {
			coordinateInventor = new CoordinateInventor();
		}
		return coordinateInventor;
	}
	
	public SSSearcherWithIndex getSSSearcherWithIndex() {
		if(sSSearcherWithIndex == null) {
			sSSearcherWithIndex = new SSSearcherWithIndex();
		}
		return sSSearcherWithIndex;
	}
	
}

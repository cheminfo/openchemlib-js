/*
 * Copyright (c) 1997 - 2016
 * Actelion Pharmaceuticals Ltd.
 * Gewerbestrasse 16
 * CH-4123 Allschwil, Switzerland
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the the copyright holder nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * @author Modest v. Korff
 */

package com.actelion.research.chem.io.pdb.parser;

import com.actelion.research.chem.Molecule;
import com.actelion.research.chem.Molecule3D;
import com.actelion.research.chem.io.pdb.converter.AminoAcids;
import com.actelion.research.chem.io.pdb.converter.BondsCalculator;
import com.actelion.research.chem.io.pdb.converter.BondOrderCalculator;

import java.util.List;

/**
 * Residue handles a group of atoms, e.g. a Ligand Molecule or an AminoAcid residue
 *
 * Created by korffmo1 on 11.04.18.
 */
public class Residue {
	public static double BOND_CUTOFF_SQ = 3.24;
    private final List<AtomRecord> mAtomRecordList;
	private boolean mIsTerminal;
	private final boolean mAddBonds,mIncludeAltLocs;
    private final Molecule3D mMol;

	/**
	 * @param records
	 * @param addBonds
	 * @param includeAltLocations
	 */
    public Residue(List<AtomRecord> records, boolean addBonds, boolean includeAltLocations) {
        mAtomRecordList = records;
		mAddBonds = addBonds;
		mIncludeAltLocs = includeAltLocations;
        mMol = constructFragment();

		for(AtomRecord record : mAtomRecordList) {
			if(record.isTerminalC()) {
				mIsTerminal = true;
				break;
			}
		}
	}

	public boolean isTerminal() {
		return mIsTerminal;
	}

    private Molecule3D constructFragment() {
		AtomRecord ar = mAtomRecordList.get(0);
		Molecule3D fragment = AminoAcids.createResidue(getResname(), mAtomRecordList);
		if (fragment == null)
			fragment = constructFragmentFromGeometry();
    	fragment.ensureHelperArrays(Molecule.cHelperNeighbours);
		// We use '1' as Model-No.
		fragment.setName(ar.getResName()+"_1_"+ar.getChainID()+"_"+ar.getResNum());
    	return fragment;
    }

	private Molecule3D constructFragmentFromGeometry() {
		Molecule3D fragment = new Molecule3D();
		String altLoc = null;
		for(AtomRecord record : mAtomRecordList) {
			if (!mIncludeAltLocs) {
				if (altLoc == null)
					altLoc = record.getAltLoc();
				else if (!altLoc.equals(record.getAltLoc()))
					continue;
			}
			int atomicNo = record.getAtomicNo();
			int atom = fragment.addAtom(atomicNo);
			fragment.setAtomName(atom, record.getAtomName());
			fragment.setAtomAmino(atom, record.getResName());
			fragment.setAtomSequence(atom,record.getSerialId());
			fragment.setResSequence(atom, record.getResNum());
			fragment.setAtomAmino(atom, record.getResName());
			fragment.setAtomChainId(atom, record.getChainID());
			fragment.setAtomX(atom,record.getX());
			fragment.setAtomY(atom,record.getY());
			fragment.setAtomZ(atom,record.getZ());
		}

		if (mAddBonds) {
			try {
				if (StructureAssembler.USE_NEW_BONDORDER_CALCULATOR) {
					BondsCalculator.createBonds(fragment, true,null);
					new BondOrderCalculator(fragment).calculateBondOrders();
				}
				else {
					BondsCalculator.createBonds(fragment, true,null);
					BondsCalculator.calculateBondOrders(fragment,true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return fragment;
	}
	
	public int getResnum() {
		return mAtomRecordList.get(0).getResNum();
	}
	
	public Molecule3D getMolecule() {
		return mMol;
	}
	
	public String getResname() {
		return mAtomRecordList.get(0).getResName();
	}
	
	public String getChainID() {
		return mAtomRecordList.get(0).getChainID();
	}
	
	public String getInsertionCode() {
		return mAtomRecordList.get(0).getInsertionCode();
	}
	
	/*
	 * check if two residues are connected by a peptide bond, this is the residue with the (supposedly) terminal nitrogen,
	 * resC should contain the terminal carbon
	 *
	public boolean areBonded(Residue resN) { 
		AtomRecord recordC = null; 
		AtomRecord recordN = null;
		boolean areBonded = false;
		for(AtomRecord rec : this.mAtomRecordList) {
			if (rec.getAtomName().equals("C")) {
				recordN = rec;
				break;
			}
		}
		
		for(AtomRecord rec : resN.mAtomRecordList) {
			if (rec.getAtomName().equals("N")) {
				recordC = rec;
				break;
			}
		}
		if(recordN!=null && recordC!=null) {
			double dx = recordN.getX()-recordC.getX();
			double dy = recordN.getY()-recordC.getY();
			double dz = recordN.getZ()-recordC.getZ();
			double distSq = dx*dx + dy*dy + dz*dz;
			if(distSq<BOND_CUTOFF_SQ)
				areBonded=true;
		}
		return areBonded;
	}*/
}

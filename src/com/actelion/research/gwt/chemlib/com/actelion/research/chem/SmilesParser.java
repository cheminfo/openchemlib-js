/*
 * Copyright 2014 Actelion Pharmaceuticals Ltd., Gewerbestrasse 16, CH-4123 Allschwil, Switzerland
 *
 * This file is part of DataWarrior.
 * 
 * DataWarrior is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * DataWarrior is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with DataWarrior.
 * If not, see http://www.gnu.org/licenses/.
 *
 * @author Thomas Sander
 */

package com.actelion.research.chem;

import java.util.TreeMap;


public class SmilesParser {
	private static final int MAX_BRACKET_LEVELS = 64;
	private static final int MAX_RE_CONNECTIONS = 64;
	private static final int MAX_AROMATIC_RING_SIZE = 15;
	private StereoMolecule mMol;
	private boolean[] mIsAromaticBond;

	/**
	 * Parses the given smiles into the molecule, creates proper atom coordinates
	 * to reflect correct double bond geometries and translates tetrahedral and allene
	 * parities into up/down-bonds.
	 * @param mol
	 * @param smiles
	 * @throws Exception
	 */
	public void parse(StereoMolecule mol, String smiles) throws Exception {
		parse(mol, smiles.getBytes(), true, true);
		}

	public void parse(StereoMolecule mol, byte[] smiles) throws Exception {
		parse(mol, smiles, true, true);
		}

	public void parse(StereoMolecule mol, byte[] smiles, boolean createCoordinates, boolean readStereoFeatures) throws Exception {
		mMol = mol;
		mMol.deleteMolecule();

		TreeMap<Integer,THParity> parityMap = null;

		int[] baseAtom = new int[MAX_BRACKET_LEVELS];
		baseAtom[0] = -1;

		int[] reconnection = new int[MAX_RE_CONNECTIONS];
		for (int i=0; i<MAX_RE_CONNECTIONS; i++)
			reconnection[i] = -1;

		int position = 0;
		int atomMass = 0;
		int fromAtom = -1;
		boolean squareBracketOpen = false;
		boolean percentFound = false;
		boolean smartsFeatureFound = false;
		int bracketLevel = 0;
		int smilesLength = smiles.length;
		int bondType = Molecule.cBondTypeSingle;

		while (position < smilesLength) {
			char theChar = (char)smiles[position++];

			if (Character.isLetter(theChar) || theChar == '*') {
				int atomicNo = 0;
				int explicitHydrogens = -1;
				boolean isWildCard = false;
				if (squareBracketOpen) {
					if (theChar == 'R' && Character.isDigit(smiles[position])) {
						int noOfDigits = Character.isDigit(smiles[position+1]) ? 2 : 1;
						atomicNo = Molecule.getAtomicNoFromLabel(new String(smiles, position-1, 1+noOfDigits));
						position += noOfDigits;
						}
					else {
						int labelLength = Character.isLowerCase(smiles[position]) ? 2 : 1;
						atomicNo = Molecule.getAtomicNoFromLabel(new String(smiles, position-1, labelLength));
						position += labelLength-1;
						explicitHydrogens = 0;
						}

					if (smiles[position] == 'H') {
						position++;
						explicitHydrogens = 1;
						if (Character.isDigit(smiles[position])) {
							explicitHydrogens = smiles[position] - '0';
							position++;
							}
						}
					}
				else if (theChar == '*') {
					atomicNo = 6;
					isWildCard = true;
					}
				else {
					switch (Character.toUpperCase(theChar)) {
					case 'B':
						if (position < smilesLength && smiles[position] == 'r') {
							atomicNo = 35;
							position++;
							}
						else
							atomicNo = 5;
						break;
					case 'C':
						if (position < smilesLength && smiles[position] == 'l') {
							atomicNo = 17;
							position++;
							}
						else
							atomicNo = 6;
						break;
					case 'F':
						atomicNo = 9;
						break;
					case 'I':
						atomicNo = 53;
						break;
					case 'N':
						atomicNo = 7;
						break;
					case 'O':
						atomicNo = 8;
						break;
					case 'P':
						atomicNo = 15;
						break;
					case 'S':
						atomicNo = 16;
						break;
						}
					}

				if (atomicNo == 0)
					throw new Exception("SmilesParser: unknown element label found");

				THParity parity = (parityMap == null) ? null : parityMap.get(baseAtom[bracketLevel]);
				if (parity != null)
					parity.addNeighbor(position, atomicNo==1 && atomMass==0);

				int atom = mMol.addAtom(atomicNo);	// this may be a hydrogen, if defined as [H]
				if (isWildCard) {
					smartsFeatureFound = true;
					mMol.setAtomQueryFeature(atom, Molecule.cAtomQFAny, true);
					}
				else {	// mark aromatic atoms
					mMol.setAtomMarker(atom, Character.isLowerCase(theChar));
					}

				// put explicitHydrogen into atomCustomLabel to keep atom-relation when hydrogens move to end of atom list in handleHydrogen()
				if (explicitHydrogens != -1) {
					byte[] bytes = new byte[1];
					bytes[0] = (byte)explicitHydrogens;
					mMol.setAtomCustomLabel(atom, bytes);
					}

				fromAtom = baseAtom[bracketLevel];
				if (baseAtom[bracketLevel] != -1) {
					mMol.addBond(atom, baseAtom[bracketLevel], bondType);
					}
				bondType = Molecule.cBondTypeSingle;
				baseAtom[bracketLevel] = atom;
				if (atomMass != 0) {
					mMol.setAtomMass(atom, atomMass);
					atomMass = 0;
					}

				continue;
				}

			if (theChar == '=') {
				bondType = Molecule.cBondTypeDouble;
				continue;
				}

			if (theChar == '#') {
				bondType = Molecule.cBondTypeTriple;
				continue;
				}

			if (Character.isDigit(theChar)) {
				int number = theChar - '0';
				if (squareBracketOpen) {
					while (position < smilesLength
					 && Character.isDigit(smiles[position])) {
						number = 10 * number + smiles[position] - '0';
						position++;
						}
					atomMass = number;
					}
				else {
					if (percentFound
					 && position < smilesLength
					 && Character.isDigit(smiles[position])) {
						number = 10 * number + smiles[position] - '0';
						position++;
						}
					percentFound = false;
					if (number >= MAX_RE_CONNECTIONS)
						throw new Exception("SmilesParser: Reconnection number out of range");
					if (reconnection[number] == -1) {
						reconnection[number] = baseAtom[bracketLevel];
						}
					else {
						THParity parity = (parityMap == null) ? null : parityMap.get(baseAtom[bracketLevel]);
						if (parity != null)
							parity.addNeighbor(position, false);

						mMol.addBond(baseAtom[bracketLevel], reconnection[number], bondType);
						reconnection[number] = -1;	// for number re-usage
						}
					bondType = Molecule.cBondTypeSingle;
					}
				continue;
				}

			if (theChar == '+') {
				if (!squareBracketOpen)
					throw new Exception("SmilesParser: '+' found outside brackets");
				int charge = 1;
				while (smiles[position] == '+') {
					charge++;
					position++;
					}
				if (charge == 1 && Character.isDigit(smiles[position])) {
					charge = smiles[position] - '0';
					position++;
					}
				mMol.setAtomCharge(baseAtom[bracketLevel], charge);
				continue;
				}

			if (theChar == '-') {
				if (!squareBracketOpen)
					continue;	// single bond

				int charge = -1;
				while (smiles[position] == '-') {
					charge--;
					position++;
					}
				if (charge == -1 && Character.isDigit(smiles[position])) {
					charge = '0' - smiles[position];
					position++;
					}
				mMol.setAtomCharge(baseAtom[bracketLevel], charge);
				continue;
				}

			if (theChar == '(') {
				if (baseAtom[bracketLevel] == -1)
					throw new Exception("Smiles with leading parenthesis are not supported");
				baseAtom[bracketLevel+1] = baseAtom[bracketLevel];
				bracketLevel++;
				continue;
				}

			if (theChar == ')') {
				bracketLevel--;
				continue;
				}

			if (theChar == '[') {
				if (squareBracketOpen)
					throw new Exception("SmilesParser: nested square brackets found");
				squareBracketOpen = true;
				continue;
				}

			if (theChar == ']') {
				if (!squareBracketOpen)
					throw new Exception("SmilesParser: closing bracket without opening one");
				squareBracketOpen = false;
				continue;
				}

			if (theChar == '%') {
				percentFound = true;
				continue;
				}

			if (theChar == '.') {
				if (bracketLevel != 0)
					throw new Exception("SmilesParser: '.' found within brackets");
				baseAtom[0] = -1;
//				for (int i=0; i<reconnection.length; i++)	we allow reconnections between fragments separated by '.'
//					reconnection[i] = -1;
				continue;
				}

			if (theChar == ':') {
				if (!squareBracketOpen) {
					bondType = Molecule.cBondTypeDelocalized;
					continue;
					}

				int mapNo = 0;
				while (Character.isDigit(smiles[position])) {
					mapNo = 10 * mapNo + smiles[position] - '0';
					position++;
					}
				mMol.setAtomMapNo(baseAtom[bracketLevel], mapNo, false);
				continue;
				}

			if (theChar == '/') {
				if (readStereoFeatures)
					bondType = Molecule.cBondTypeUp;
				continue;	// encode slash temporarily in bondType
				}
			if (theChar == '\\') {
				if (readStereoFeatures)
					bondType = Molecule.cBondTypeDown;
				continue;	// encode backslash temporarily in bondType
				}

			if (theChar == '@') {
				boolean isClockwise = false;
				if (smiles[position] == '@') {
					isClockwise = true;
					position++;
					}

				if (readStereoFeatures) {
					if (parityMap == null)
						parityMap = new TreeMap<Integer,THParity>();
	
					THParity parity = new THParity(baseAtom[bracketLevel], fromAtom, isClockwise);
					parityMap.put(baseAtom[bracketLevel], parity);
					}

				if (smiles[position] == 'H') {
					position++;
					}
				
				continue;
				}

			throw new Exception("SmilesParser: unexpected character found: '"+theChar+"'");
			}

		// If the number of explicitly defined hydrogens conflicts with the occupied and default valence, then set an abnormal valence.
		mMol.setHydrogenProtection(true);	// We may have a fragment. Therefore, prevent conversion of explicit H into a query feature.
		mMol.ensureHelperArrays(Molecule.cHelperNeighbours);
		for (int atom=0; atom<mMol.getAllAtoms(); atom++) {
			if (mol.getAtomCustomLabel(atom) != null) {	// if we have the exact number of hydrogens
				if (!mMol.isMarkedAtom(atom)) {	// don't correct aromatic atoms
					int explicitHydrogen = mMol.getAtomCustomLabelBytes(atom)[0];
					if (Molecule.cAtomValence[mMol.getAtomicNo(atom)] != null
					 && mMol.getFreeValence(atom) != explicitHydrogen)
						mMol.setAtomAbnormalValence(atom, mMol.getOccupiedValence(atom)+explicitHydrogen);
					}
				}
			}

		correctValenceExceededNitrogen();	// convert pyridine oxides and nitro into polar structures with valid nitrogen valences

		locateAromaticDoubleBonds();

		mMol.removeAtomCustomLabels();
		mMol.setHydrogenProtection(false);

		if (readStereoFeatures) {
			if (resolveStereoBonds())
				mMol.setParitiesValid(0);
			}

		if (createCoordinates || readStereoFeatures) {
			new CoordinateInventor().invent(mMol);

			if (readStereoFeatures) {
				if (parityMap != null) {
					for (THParity parity:parityMap.values())
						mMol.setAtomParity(parity.mCentralAtom, parity.calculateParity(), false);

					mMol.setParitiesValid(0);
					}

				mMol.setStereoBondsFromParity();
				mMol.setUnknownParitiesToExplicitlyUnknown();
				}
			}

		if (smartsFeatureFound)
			mMol.setFragment(true);
		}


	private void locateAromaticDoubleBonds() throws Exception {
		mMol.ensureHelperArrays(Molecule.cHelperNeighbours);
		mIsAromaticBond = new boolean[mMol.getBonds()];

		// all explicitly defined aromatic bonds are taken
		for (int bond=0; bond<mMol.getBonds(); bond++) {
			if (mMol.getBondType(bond) == Molecule.cBondTypeDelocalized) {
				mMol.setBondType(bond, Molecule.cBondTypeSingle);
				mIsAromaticBond[bond] = true;
				}
			}

			// assume all bonds of small rings to be aromatic if the ring consists of aromatic atoms only
		RingCollection ringSet = new RingCollection(mMol, RingCollection.MODE_SMALL_AND_LARGE_RINGS);
		boolean[] isAromaticRing = new boolean[ringSet.getSize()];
		for (int ring=0; ring<ringSet.getSize(); ring++) {
			int[] ringAtom = ringSet.getRingAtoms(ring);
			isAromaticRing[ring] = true;
			for (int i=0; i<ringAtom.length; i++) {
				if (!mMol.isMarkedAtom(ringAtom[i])) {
					isAromaticRing[ring] = false;
					break;
					}
				}
			if (isAromaticRing[ring]) {
				int[] ringBond = ringSet.getRingBonds(ring);
				for (int i=0; i<ringBond.length; i++)
					mIsAromaticBond[ringBond[i]] = true;
				}
			}

			// if ring bonds with two aromaticity markers are left, check whether
			// these are a member of a large ring that has all atoms marked as aromatic.
			// If yes then assume all of its bonds aromatic.
		for (int bond=0; bond<mMol.getBonds(); bond++) {
			if (!mIsAromaticBond[bond]
			 && ringSet.getBondRingSize(bond) != 0
			 && mMol.isMarkedAtom(mMol.getBondAtom(0, bond))
			 && mMol.isMarkedAtom(mMol.getBondAtom(1, bond))) {
				addLargeAromaticRing(bond);
				}
			}

		mMol.ensureHelperArrays(Molecule.cHelperRings);	// to accomodate for the structure changes

			// Some Smiles contain 'aromatic' rings with atoms not being compatible
			// with a PI-bond. These include: tertiary non-charged nitrogen, [nH],
			// sulfur, non-charged oxygen, charged carbon, etc...
			// All these atoms and attached bonds are marked as handled to avoid
			// attached bonds to be promoted (changed to double bond) later.
		for (int ring=0; ring<ringSet.getSize(); ring++) {
			if (isAromaticRing[ring]) {
				int[] ringAtom = ringSet.getRingAtoms(ring);
				for (int i=0; i<ringAtom.length; i++) {
					if (!qualifiesForPi(ringAtom[i])) {
						mMol.setAtomMarker(ringAtom[i], false);// mark: atom aromaticity handled
						for (int j=0; j<mMol.getConnAtoms(ringAtom[i]); j++)
							mIsAromaticBond[mMol.getConnBond(ringAtom[i], j)] = false;
						}
					}
				}
			}
		promoteObviousBonds();

		// promote fully delocalized 6-membered rings
		for (int ring=0; ring<ringSet.getSize(); ring++) {
			if (isAromaticRing[ring] && ringSet.getRingSize(ring) == 6) {
				int[] ringBond = ringSet.getRingAtoms(ring);
				boolean isFullyDelocalized = true;
				for (int bond:ringBond) {
					if (!mIsAromaticBond[bond]) {
						isFullyDelocalized = false;
						break;
						}
					}
				if (isFullyDelocalized) {
					promoteBond(ringBond[0]);
					promoteBond(ringBond[2]);
					promoteBond(ringBond[4]);
					}
				}
			}
		promoteObviousBonds();

			// handle remaining annelated rings (naphtalines, azulenes, etc.) starting from bridge heads (qualifyingNo=5)
			// and then handle and simple rings (qualifyingNo=4)
		boolean qualifyingBondFound;
		for (int qualifyingNo=5; qualifyingNo>=4; qualifyingNo--) {
			do {
				qualifyingBondFound = false;
				for (int bond=0; bond<mMol.getBonds(); bond++) {
					if (mIsAromaticBond[bond]) {
						int aromaticConnBonds = 0;
						for (int i=0; i<2; i++) {
							int bondAtom = mMol.getBondAtom(i, bond);
							for (int j=0; j<mMol.getConnAtoms(bondAtom); j++)
								if (mIsAromaticBond[mMol.getConnBond(bondAtom, j)])
									aromaticConnBonds++;
							}

						if (aromaticConnBonds == qualifyingNo) {
							promoteBond(bond);
							promoteObviousBonds();
							qualifyingBondFound = true;
							break;
							}
						}
					}
				} while (qualifyingBondFound);
			}

		for (int bond=0; bond<mMol.getBonds(); bond++)
			if (mIsAromaticBond[bond])
				throw new Exception("Assignment of aromatic double bonds failed");
		for (int atom=0; atom<mMol.getAtoms(); atom++)
			if (mMol.isMarkedAtom(atom))
				throw new Exception("Assignment of aromatic double bonds failed");
		}


	private void addLargeAromaticRing(int bond) {
		int[] graphLevel = new int[mMol.getAtoms()];
		int graphAtom[] = new int[mMol.getAtoms()];
		int graphBond[] = new int[mMol.getAtoms()];
		int graphParent[] = new int[mMol.getAtoms()];

		int atom1 = mMol.getBondAtom(0, bond);
		int atom2 = mMol.getBondAtom(1, bond);
		graphAtom[0] = atom1;
		graphAtom[1] = atom2;
		graphBond[0] = -1;
		graphBond[1] = bond;
		graphLevel[atom1] = 1;
		graphLevel[atom2] = 2;
		graphParent[atom1] = -1;
		graphParent[atom2] = atom1;

		int current = 1;
		int highest = 1;
		while (current <= highest && graphLevel[graphAtom[current]] < MAX_AROMATIC_RING_SIZE) {
			int parent = graphAtom[current];
			for (int i=0; i<mMol.getConnAtoms(parent); i++) {
				int candidate = mMol.getConnAtom(parent, i);
				if (candidate != graphParent[parent]) {
					int candidateBond = mMol.getConnBond(parent, i);
					if (candidate == atom1) {	// ring closure
						graphBond[0] = candidateBond;
						for (int j=0; j<=highest; j++)
							mIsAromaticBond[graphBond[i]] = true;
						return;
						}
	
					if (mMol.isMarkedAtom(candidate)
					 && graphLevel[candidate] == 0) {
						highest++;
						graphAtom[highest] = candidate;
						graphBond[highest] = candidateBond;
						graphLevel[candidate] = graphLevel[parent]+1;
						graphParent[candidate] = parent;
						}
					}
				}
			current++;
			}
		return;
		}


	private boolean qualifiesForPi(int atom) {
		if ((mMol.getAtomicNo(atom) == 16 && mMol.getAtomCharge(atom) <= 0)
		 || (mMol.getAtomicNo(atom) == 6 && mMol.getAtomCharge(atom) != 0)
		 || !mMol.isMarkedAtom(atom))	// already marked as hetero-atom of another ring
			return false;

		int explicitHydrogens = (mMol.getAtomCustomLabel(atom) == null) ? 0 : mMol.getAtomCustomLabelBytes(atom)[0];
		if (mMol.getFreeValence(atom) - explicitHydrogens < 1)
			return false;

		if (mMol.getAtomicNo(atom) != 5
		 && mMol.getAtomicNo(atom) != 6
		 && mMol.getAtomicNo(atom) != 7
		 && mMol.getAtomicNo(atom) != 8
		 && mMol.getAtomicNo(atom) != 15	// P
		 && mMol.getAtomicNo(atom) != 16	// S
		 && mMol.getAtomicNo(atom) != 33	// As
		 && mMol.getAtomicNo(atom) != 34)	// Se
			return false;

		return true;
		}


	private void promoteBond(int bond) {
		if (mMol.getBondType(bond) == Molecule.cBondTypeSingle)
			mMol.setBondType(bond, Molecule.cBondTypeDouble);

		for (int i=0; i<2; i++) {
			int bondAtom = mMol.getBondAtom(i, bond);
			mMol.setAtomMarker(bondAtom, false);
			for (int j=0; j<mMol.getConnAtoms(bondAtom); j++)
				mIsAromaticBond[mMol.getConnBond(bondAtom, j)] = false;
			}
		}


	private void promoteObviousBonds() {
			// handle bond orders of aromatic bonds along the chains attached to 5- or 7-membered ring
		boolean terminalAromaticBondFound;
		do {
			terminalAromaticBondFound = false;
			for (int bond=0; bond<mMol.getBonds(); bond++) {
				if (mIsAromaticBond[bond]) {
					boolean isTerminalAromaticBond = false;
					for (int i=0; i<2; i++) {
						boolean aromaticNeighbourFound = false;
						int bondAtom = mMol.getBondAtom(i, bond);
						for (int j=0; j<mMol.getConnAtoms(bondAtom); j++) {
							if (bond != mMol.getConnBond(bondAtom, j)
							 && mIsAromaticBond[mMol.getConnBond(bondAtom, j)]) {
								aromaticNeighbourFound = true;
								break;
								}
							}
						if (!aromaticNeighbourFound) {
							isTerminalAromaticBond = true;
							break;
							}
						}

					if (isTerminalAromaticBond) {
						terminalAromaticBondFound = true;
						promoteBond(bond);
						}
					}
				}
			} while (terminalAromaticBondFound);
		}

	/**
	 * This corrects N=O double bonds where the nitrogen has an exceeded valence
	 * by converting to a single bond and introducing separated charges.
	 * (e.g. pyridinoxides and nitro groups)
	 */
	private void correctValenceExceededNitrogen() {
		for (int atom=0; atom<mMol.getAtoms(); atom++) {
			if (mMol.getAtomicNo(atom) == 7
			 && mMol.getAtomCharge(atom) == 0
			 && mMol.getOccupiedValence(atom) > 3
			 && mMol.getAtomPi(atom) > 0) {
				for (int i=0; i<mMol.getConnAtoms(atom); i++) {
					int connAtom = mMol.getConnAtom(atom, i);
					int connBond = mMol.getConnBond(atom, i);
					if ((mMol.getBondOrder(connBond) > 1)
					 && mMol.isElectronegative(connAtom)) {
						if (mMol.getBondType(connBond) == Molecule.cBondTypeTriple)
							mMol.setBondType(connBond, Molecule.cBondTypeDouble);
						else
							mMol.setBondType(connBond, Molecule.cBondTypeSingle);
	
						mMol.setAtomCharge(atom, mMol.getAtomCharge(atom) + 1);
						mMol.setAtomCharge(connAtom, mMol.getAtomCharge(connAtom) - 1);
						break;
						}
					}
				}
			}
		}

	private boolean resolveStereoBonds() {
		mMol.ensureHelperArrays(Molecule.cHelperRings);

		boolean paritiesFound = false;
		int[] refAtom = new int[2];
		int[] refBond = new int[2];
		int[] otherAtom = new int[2];
		for (int bond=0; bond<mMol.getBonds(); bond++) {
			if (!mMol.isSmallRingBond(bond)
			 && mMol.getBondType(bond) == Molecule.cBondTypeDouble) {
				for (int i=0; i<2; i++) {
					refAtom[i] = -1;
					otherAtom[i] = -1;
					int atom = mMol.getBondAtom(i, bond);
					for (int j=0; j<mMol.getConnAtoms(atom); j++) {
						int connBond = mMol.getConnBond(atom, j);
						if (connBond != bond) {
							if (mMol.getBondType(connBond) == Molecule.cBondTypeUp
							 || mMol.getBondType(connBond) == Molecule.cBondTypeDown) {
								refAtom[i] = mMol.getConnAtom(atom, j);
								refBond[i] = connBond;
								}
							else {
								otherAtom[i] = mMol.getConnAtom(atom, j);
								}
							}
						}
					if (refAtom[i] == -1)
						break;
					}
				if (refAtom[0] != -1 && refAtom[1] != -1) {
					boolean isZ = mMol.getBondType(refBond[0]) != mMol.getBondType(refBond[1]);
					boolean inversion = false;
					for (int i=0; i<2; i++) {
						if (otherAtom[i] != -1
						 && otherAtom[i] < refAtom[i])
							inversion = !inversion;
						}

					mMol.setBondParity(bond, isZ ^ inversion ? Molecule.cBondParityZor2
															 : Molecule.cBondParityEor1, false);
					paritiesFound = true;
					}
				}
			}

		// convert temporary stereo bonds back to plain single bonds
		for (int bond=0; bond<mMol.getBonds(); bond++)
			if (mMol.getBondType(bond) == Molecule.cBondTypeUp
			 || mMol.getBondType(bond) == Molecule.cBondTypeDown)
				mMol.setBondType(bond, Molecule.cBondTypeSingle);

		return paritiesFound;
		}

	private class THParity {
		int mCentralAtom,mFromAtom,mNeighborCount;
		int[] mNeighborPosition;
		boolean[] mNeighborIsHydrogen;
		boolean mIsClockwise,mError;

		/**
		 * Instantiates a new parity object during smiles traversal.
		 * @param centralAtom index of atoms processed
		 * @param fromAtom index of parent atom of centralAtom (-1 if centralAtom is first atom in smiles)
		 * @param isClockwise true if central atom is marked with @@ rather than @
		 */
		public THParity(int centralAtom, int fromAtom, boolean isClockwise) {
			mCentralAtom = centralAtom;
			mFromAtom = fromAtom;
			mIsClockwise = isClockwise;
			mNeighborCount = 0;
			mNeighborIsHydrogen = new boolean[4];
			mNeighborPosition = new int[4];
			}

		/**
		 * Adds a currently traversed neighbor or ring closure to parity object,
		 * which belongs to the neighbor's parent atom.
		 * In case of a ring closure the bond closure digit's position in the smiles
		 * rather than the neighbor's position is the relevant position used for parity
		 * determination.
		 * @param atom
		 * @param position
		 */
		public void addNeighbor(int position, boolean isHydrogen) {
			if (mNeighborCount == 4
			 || (mNeighborCount == 3 && mFromAtom != -1)) {
				mError = true;
				return;
				}

			mNeighborIsHydrogen[mNeighborCount] = isHydrogen;
			mNeighborPosition[mNeighborCount] = position;
			mNeighborCount++;
			}

		public int calculateParity() {
			if (mError)
				return Molecule.cAtomParityUnknown;

			boolean hasExplicitHydrogen = (mFromAtom != -1 && mMol.getAtomicNo(mFromAtom) == 1 && mMol.getAtomMass(mFromAtom) == 0);
			for (int i=0; i<mNeighborCount; i++) {
				if (mNeighborIsHydrogen[i]) {
					if (hasExplicitHydrogen)
						return Molecule.cAtomParityUnknown;
					hasExplicitHydrogen = true;
					}
				}

			int nonHydrogenNeighborCount = mNeighborCount + ((mFromAtom != -1) ? 1 : 0) - (hasExplicitHydrogen ? 1 : 0);
			if (nonHydrogenNeighborCount < 3 || nonHydrogenNeighborCount > 4)
				return Molecule.cAtomParityUnknown;

			boolean hasImplicitHydrogen = (nonHydrogenNeighborCount == 3 && !hasExplicitHydrogen);

			// hydrogens are moved to the end of the atom list. If the hydrogen passes an odd number of
			// neighbor atoms on its way to the list end, we are effectively inverting the atom order.
			boolean isHydrogenTraversalInversion = false;

			if (hasExplicitHydrogen) {
				for (int i=0; i<mNeighborCount; i++) {
					if (mNeighborIsHydrogen[i]) {
						isHydrogenTraversalInversion = (((mNeighborCount - i) & 1) == 0);
						break;
						}
					}
				}
			else if (hasImplicitHydrogen) {
				if (mFromAtom == -1)
					// If centralAtom is first atom in smiles then the implicit hydrogen is from-atom by convention:
					// It passes an ODD number of atoms when travelling to the end of the neighbor list.
					isHydrogenTraversalInversion = true;
				else
					// If there is a from-atom then the implicit hydrogen is the central atom's first neighbor by convention:
					// It passes an EVEN number of atoms (2) when travelling to the end of the neighbor list.
					isHydrogenTraversalInversion = false;
				}

			if (mFromAtom == -1 && !hasImplicitHydrogen) {
				// If we have no implicit hydrogen and the central atom is the first atom in the smiles,
				// then we assume that we have to take the first neighbor as from-atom (not described in Daylight theory manual).
				// Assumption: take the first neighbor as front atom, i.e. skip it when comparing positions
				for (int i=1; i<mNeighborCount; i++)
					mNeighborPosition[i-1] = mNeighborPosition[i];
				mNeighborCount--;
				}

			boolean isInverseOrder = ((mNeighborPosition[0] > mNeighborPosition[1]) && (mNeighborPosition[1] > mNeighborPosition[2]))
								  || ((mNeighborPosition[1] > mNeighborPosition[2]) && (mNeighborPosition[2] > mNeighborPosition[0]))
								  || ((mNeighborPosition[2] > mNeighborPosition[0]) && (mNeighborPosition[0] > mNeighborPosition[1]));
			return (mIsClockwise ^ isInverseOrder ^ isHydrogenTraversalInversion) ? Molecule.cAtomParity1 : Molecule.cAtomParity2;
			}
		}

	public static void main(String[] args) {
		System.out.println("Alanine test:");
		final String[] alanine = {	"N[C@@]([H])(C)C(=O)O",	"N[C@]([H])(C)C(=O)O",
									"N[C@@H](C)C(=O)O",		"N[C@H](C)C(=O)O",
									"N[C@H](C(=O)O)C",		"N[C@@H](C(=O)O)C",
									"[H][C@](N)(C)C(=O)O",	"[H][C@@](N)(C)C(=O)O",
									"[C@H](N)(C)C(=O)O",	"[C@@H](N)(C)C(=O)O" };

		StereoMolecule mol = new StereoMolecule();
		for (String smiles:alanine) {
			try {
				new SmilesParser().parse(mol, smiles);
				mol.ensureHelperArrays(Molecule.cHelperCIP);
				for (int atom=0; atom<mol.getAtoms(); atom++)
					if (mol.isAtomStereoCenter(atom))
						System.out.println("atom:"+atom+" parity:"+mol.getAtomParity(atom)+" CIP:"+mol.getAtomCIPParity(atom));
				}
			catch (Exception e) {
				System.out.println("Exception: "+e.getMessage());
				}
			}

		System.out.println("Test for butane:");
		final String[] butane = {	"CCCC", "C1C.CC1", "[CH3][CH2][CH2][CH3]", "C-C-C-C","C12.C1.CC2" };
		for (String smiles:butane) {
			try {
				new SmilesParser().parse(mol, smiles);
				System.out.println(new MolecularFormula(mol).getFormula());
				}
			catch (Exception e) {
				System.out.println("Exception: "+e.getMessage());
				}
			}
		
	
		System.out.println("Test for NaCl:");
		final String[] nacl = {	"[Na+][Cl-]", "[Na+]-[Cl-]", "[Na+]1.[Cl-]1" };
		for (String smiles:nacl) {
			try {
				new SmilesParser().parse(mol, smiles);
				System.out.println(new MolecularFormula(mol).getFormula());
				}
			catch (Exception e) {
				System.out.println("Exception: "+e.getMessage());
				}
			}
		
		System.out.println("Test for aromatic:");
		final String[] benzene = {	"c1ccccc1", "C1=C-C=C-C=C1", "C1:C:C:C:C:C:1",
									"c1ccncc1", "c1cncc1", "c1cncn1", "[nH]1cccc1", "N1C=C-C=C1", "[H]n1cccc1"};
		for (String smiles:benzene) {
			try {
				new SmilesParser().parse(mol, smiles);
				System.out.println(new MolecularFormula(mol).getFormula());
				}
			catch (Exception e) {
				System.out.println("Exception: "+e.getMessage());
				}
			}

		System.out.println("Test for isotopes:");
		final String[] isotopes = {	"[13CH4]", "[35ClH]", "[35Cl-]" };
		for (String smiles:isotopes) {
			try {
				new SmilesParser().parse(mol, smiles);
				System.out.println(new MolecularFormula(mol).getFormula());
				}
			catch (Exception e) {
				System.out.println("Exception: "+e.getMessage());
				}
			}
		}
	}
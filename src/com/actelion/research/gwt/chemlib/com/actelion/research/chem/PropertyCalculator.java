package com.actelion.research.chem;

import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.prediction.CLogPPredictor;
import com.actelion.research.chem.prediction.PolarSurfaceAreaPredictor;
import com.actelion.research.chem.prediction.SolubilityPredictor;


public class PropertyCalculator {
	private StereoMolecule mMolecule;

	public PropertyCalculator(StereoMolecule mol) {
		mMolecule = mol;
		}

	public int getAcceptorCount() {
		int count = 0;
		for (int atom=0; atom<mMolecule.getAllAtoms(); atom++)
			if (mMolecule.getAtomicNo(atom) == 7 || mMolecule.getAtomicNo(atom) == 8)
				count++;
		return count;
		}

	public int getDonorCount() {
		int count = 0;
		for (int atom=0; atom<mMolecule.getAllAtoms(); atom++)
			if ((mMolecule.getAtomicNo(atom) == 7 || mMolecule.getAtomicNo(atom) == 8)
			 && mMolecule.getAllHydrogens(atom) > 0)
				count++;
		return count;
		}

	public double getLogP() {
		try {
			return new CLogPPredictor().assessCLogP(mMolecule);
			}
		catch (Exception e) {
			e.printStackTrace();
			return CLogPPredictor.cCLogPUnknown;
			}
		}

	public double getLogS() {
		return new SolubilityPredictor().assessSolubility(mMolecule);
		}

	public double getPolarSurfaceArea() {
		return new PolarSurfaceAreaPredictor().assessPSA(mMolecule);
		}

	public int getRotatableBondCount() {
		return mMolecule.getRotatableBondCount();
		}

	public int getStereoCenterCount() {
		return mMolecule.getStereoCenterCount();
		}
	}

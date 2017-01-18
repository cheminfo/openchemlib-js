/*

Copyright (c) 2015-2017, cheminfo

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.
    * Neither the name of {{ project }} nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package com.actelion.research.chem;

import com.actelion.research.chem.prediction.CLogPPredictor;
import com.actelion.research.chem.prediction.ParameterizedStringList;
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
	public ParameterizedStringList getLogPDetail() {
		return new CLogPPredictor().getDetail(mMolecule);
	}

	public double getLogS() {
		return new SolubilityPredictor().assessSolubility(mMolecule);
		}
	public ParameterizedStringList getLogSDetail() {
		return new SolubilityPredictor().getDetail(mMolecule);
	}


	public double getPolarSurfaceArea() {
		return new PolarSurfaceAreaPredictor().assessPSA(mMolecule);
		}
	public ParameterizedStringList getPolarSurfaceAreaDetail() {
		return new PolarSurfaceAreaPredictor().getDetail(mMolecule);
	}

	public int getRotatableBondCount() {
		return mMolecule.getRotatableBondCount();
		}

	public int getStereoCenterCount() {
		return mMolecule.getStereoCenterCount();
		}
	}

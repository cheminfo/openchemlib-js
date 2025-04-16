package com.actelion.research.gwt.core;

import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.Canonizer;
import com.actelion.research.gwt.minimal.JSMolecule;
import jsinterop.annotations.*;

@JsType(name = "Canonizer")
public class JSCanonizer {
    
    public static final int CREATE_SYMMETRY_RANK = 1;
    public static final int CONSIDER_STEREOHETEROTOPICITY = 2;
    public static final int ENCODE_ATOM_CUSTOM_LABELS = 8;
    public static final int ENCODE_ATOM_SELECTION = 16;
    public static final int ASSIGN_PARITIES_TO_TETRAHEDRAL_N = 32;
    public static final int COORDS_ARE_3D = 64;
    public static final int CREATE_PSEUDO_STEREO_GROUPS = 128;
    public static final int DISTINGUISH_RACEMIC_OR_GROUPS = 256;
    public static final int TIE_BREAK_FREE_VALENCE_ATOMS = 512;
    public static final int ENCODE_ATOM_CUSTOM_LABELS_WITHOUT_RANKING = 1024;
    public static final int NEGLECT_ANY_STEREO_INFORMATION = 2048;

    private Canonizer canonizer;

    public JSCanonizer(JSMolecule mol, @JsOptional Integer mode) {
        if (mode == null) {
            canonizer = new Canonizer(mol.getStereoMolecule());
        } else {
            canonizer = new Canonizer(mol.getStereoMolecule(), mode);
        }
    }

    public boolean hasCIPParityDistinctionProblem() {
        return canonizer.hasCIPParityDistinctionProblem();
    }

    public JSMolecule getCanMolecule(@JsOptional Boolean includeExplicitHydrogen) {
        StereoMolecule mol;
        if (includeExplicitHydrogen == null) {
            mol = canonizer.getCanMolecule();
        } else {
            mol = canonizer.getCanMolecule(includeExplicitHydrogen);
        }
        return new JSMolecule(mol);
    }

    public String getIDCode() {
        return canonizer.getIDCode();
    }

    public int[] getFinalRank() {
        return canonizer.getFinalRank();
    }

    public int getSymmetryRank(int atom) {
        return canonizer.getSymmetryRank(atom);
    }

    public int[] getSymmetryRanks() {
        return canonizer.getSymmetryRanks();
    }

    public void invalidateCoordinates() {
        canonizer.invalidateCoordinates();
    }

    public String getEncodedCoordinates(@JsOptional Boolean keepPositionAndScale) {
        if (keepPositionAndScale == null) {
            return canonizer.getEncodedCoordinates();
        } else {
            return canonizer.getEncodedCoordinates(keepPositionAndScale);
        }
    }

    public String getEncodedMapping() {
        return canonizer.getEncodedMapping();
    }

    public boolean normalizeEnantiomer() {
        return canonizer.normalizeEnantiomer();
    }

    public void setParities() {
        canonizer.setParities();
    }

    public int[] getGraphAtoms() {
        return canonizer.getGraphAtoms();
    }

    public int[] getGraphIndexes() {
        return canonizer.getGraphIndexes();
    }
}

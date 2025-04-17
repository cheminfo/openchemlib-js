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

    public JSCanonizer(JSMolecule mol, int mode) {
        canonizer = new Canonizer(mol.getStereoMolecule(), mode);
    }

    @JsIgnore
    public JSCanonizer(JSMolecule mol) {
        this(mol, 0);
    }

    public boolean hasCIPParityDistinctionProblem() {
        return canonizer.hasCIPParityDistinctionProblem();
    }

    public JSMolecule getCanMolecule(boolean includeExplicitHydrogen) {
        StereoMolecule mol = canonizer.getCanMolecule(includeExplicitHydrogen);
        return new JSMolecule(mol);
    }

    @JsIgnore
    public JSMolecule getCanMolecule() {
        return getCanMolecule(false);
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

    public String getEncodedCoordinates(boolean keepPositionAndScale) {
        return canonizer.getEncodedCoordinates(keepPositionAndScale);
    }

    @JsIgnore
    public String getEncodedCoordinates() {
        return canonizer.getEncodedCoordinates();
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

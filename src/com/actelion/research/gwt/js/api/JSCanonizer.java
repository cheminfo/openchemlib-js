package com.actelion.research.gwt.js.api;

import com.actelion.research.chem.StereoMolecule;
import com.actelion.research.chem.Canonizer;
import com.actelion.research.gwt.js.api.JSMolecule;
import com.google.gwt.core.client.JavaScriptObject;
import jsinterop.annotations.*;

@JsType(name = "Canonizer")
public class JSCanonizer {
    private Canonizer canonizer;

    public JSCanonizer(JSMolecule mol, JavaScriptObject options) {
        canonizer = new Canonizer(mol.getStereoMolecule(), getMode(options));
    }

    private native int getMode(JavaScriptObject options) /*-{
        options = options || {};
        var mode = 0;
        if (options.createSymmetryRank) mode |= @com.actelion.research.chem.Canonizer::CREATE_SYMMETRY_RANK;
        if (options.considerStereoheterotopicity) mode |= @com.actelion.research.chem.Canonizer::CONSIDER_STEREOHETEROTOPICITY;
        if (options.encodeAtomCustomLabels) mode |= @com.actelion.research.chem.Canonizer::ENCODE_ATOM_CUSTOM_LABELS;
        if (options.encodeAtomSelection) mode |= @com.actelion.research.chem.Canonizer::ENCODE_ATOM_SELECTION;
        if (options.assignParitiesToTetrahedralN) mode |= @com.actelion.research.chem.Canonizer::ASSIGN_PARITIES_TO_TETRAHEDRAL_N;
        if (options.coordsAre3d) mode |= @com.actelion.research.chem.Canonizer::COORDS_ARE_3D;
        if (options.createPseudoStereoGroups) mode |= @com.actelion.research.chem.Canonizer::CREATE_PSEUDO_STEREO_GROUPS;
        if (options.distinguishRacemicOrGroups) mode |= @com.actelion.research.chem.Canonizer::DISTINGUISH_RACEMIC_OR_GROUPS;
        if (options.tieBreakFreeValenceAtoms) mode |= @com.actelion.research.chem.Canonizer::TIE_BREAK_FREE_VALENCE_ATOMS;
        if (options.encodeAtomCustomLabelsWithoutRanking) mode |= @com.actelion.research.chem.Canonizer::ENCODE_ATOM_CUSTOM_LABELS_WITHOUT_RANKING;
        if (options.neglectAnyStereoInformation) mode |= @com.actelion.research.chem.Canonizer::NEGLECT_ANY_STEREO_INFORMATION;
        return mode;
    }-*/;

    public boolean hasCIPParityDistinctionProblem() {
        return canonizer.hasCIPParityDistinctionProblem();
    }

    public JSMolecule getCanMolecule(boolean includeExplicitHydrogen) {
        StereoMolecule mol = canonizer.getCanMolecule(includeExplicitHydrogen);
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

    public String getEncodedCoordinates(boolean keepPositionAndScale) {
        return canonizer.getEncodedCoordinates(keepPositionAndScale);
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

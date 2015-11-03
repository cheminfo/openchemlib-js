/*
 * Project: DD_core
 * @(#)DrugScore.java
 *
 * Copyright (c) 1997- 2015
 * Actelion Pharmaceuticals Ltd.
 * Gewerbestrasse 16
 * CH-4123 Allschwil, Switzerland
 *
 * All Rights Reserved.
 *
 * This software is the proprietary information of Actelion Pharmaceuticals, Ltd.
 * Use is subject to license terms.
 *
 * Author: Christian Rufener
 */

package com.actelion.research.chem.prediction;

/**
 * Created by rufenec on 26/10/15.
 */
public class DrugScoreCalculator
{
    public static double calculate(double cLogPScore,double solubilityScore,double molweightScore,double drugLikenessScore,int[] toxRisks)
    {
        double drugScore = (0.5+cLogPScore/2)
                * (0.5+solubilityScore/2)
                * (0.5+molweightScore/2)
                * (0.5+drugLikenessScore/2);

        for (int i=0; toxRisks != null && i<toxRisks.length; i++) {
            if (toxRisks[i] == ToxicityPredictor.cLowRisk)
                drugScore *= 0.80;
            else if (toxRisks[i] == ToxicityPredictor.cHighRisk)
                drugScore *= 0.60;
        }
        return drugScore;
    }
}

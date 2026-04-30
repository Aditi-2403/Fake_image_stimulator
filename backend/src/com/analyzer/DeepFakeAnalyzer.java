package com.analyzer;

import java.util.Random;

/**
 * Specifically looks for AI-generated artifacts in the image.
 * 
 */
public class DeepFakeAnalyzer implements Analyzer {
    private int riskScore;
    private Random random = new Random();

    @Override
    public void analyze() throws InterruptedException {
        Thread.sleep(1000);
        // Range: 10 to 95
        this.riskScore = 10 + random.nextInt(86);
    }

    @Override
    public int getRiskContribution() {
        return riskScore;
    }

    @Override
    public String getDescription() {
        return "Analyzing pixel inconsistencies in the frequency domain.";
    }
}

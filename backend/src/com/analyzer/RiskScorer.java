package com.analyzer;

/**
 * RiskScorer aggregates results and determines the final risk level.
 * UPDATED: Uses weighted average for better accuracy simulation.
 */
public class RiskScorer extends ImageProcessor {
    private int totalScore;

    public RiskScorer() {
        super("Risk Scoring");
    }

    /**
     * Weighted calculation: DeepFake (70%) and Metadata (30%)
     */
    public void calculateWeighted(int deepFakeScore, int metadataScore) {
        double weightedSum = (deepFakeScore * 0.7) + (metadataScore * 0.3);
        this.totalScore = (int) Math.min(100, Math.round(weightedSum));
    }

    @Override
    public void process() throws InterruptedException {
        simulateDelay(1000);
        System.out.println("Weighted risk calculation: " + totalScore + "%");
    }

    public String getRiskLevel() {
        if (totalScore < 35)
            return "Low";
        if (totalScore < 70)
            return "Medium";
        return "High";
    }

    public int getConfidence() {
        return totalScore;
    }

    // Deprecated but kept for compatibility if needed
    public void calculateTotal(int... scores) {
        int sum = 0;
        for (int s : scores)
            sum += s;
        this.totalScore = sum / scores.length;
    }
}

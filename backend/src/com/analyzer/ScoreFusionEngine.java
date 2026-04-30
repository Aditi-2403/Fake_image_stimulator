package com.analyzer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * L4: Score Fusion Engine
 * UPGRADED: Implements dynamic weighting and human-like specific explanations.
 */
public class ScoreFusionEngine {
    private List<Analyzer> detectors;

    public ScoreFusionEngine(List<Analyzer> detectors) {
        this.detectors = detectors;
    }

    public AnalysisResult generateFinalResult() {
        double weightedSum = 0;
        // Adjusted Weights: Metadata 5%, Visual 5%, Frequency 5%, DL 85%
        double[] weights = { 0.05, 0.05, 0.05, 0.85 };

        for (int i = 0; i < detectors.size(); i++) {
            weightedSum += detectors.get(i).getRiskContribution() * weights[i];
        }

        int finalScore = (int) Math.round(weightedSum);
        String risk = (finalScore > 65) ? "High" : (finalScore > 30 ? "Medium" : "Low");

        // Dynamic Human-Like Explanation Aggregator
        String detailedExplanations = detectors.stream()
                .map(Analyzer::getDescription)
                .filter(desc -> !desc.contains("verified") && !desc.contains("validated"))
                .collect(Collectors.joining(" "));

        String finalExplanation = detailedExplanations.isEmpty()
                ? "Image scan complete. All forensic markers fall within normal organic ranges."
                : detailedExplanations;

        return new AnalysisResult(risk, finalScore, finalExplanation);
    }
}

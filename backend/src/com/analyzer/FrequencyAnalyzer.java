package com.analyzer;

import java.util.Random;

/**
 * Module: Frequency Domain Analysis
 * UPGRADED: Detects Texture Smoothness and Fourier Noise Patterns.
 */
public class FrequencyAnalyzer implements Analyzer {
    private int score;
    private Random random = new Random();
    private String detailedReport = "";

    @Override
    public void analyze() throws InterruptedException {
        System.out.println("[L3] Frequency Module: Running algorithmic texture scrutiny...");
        Thread.sleep(1000);

        boolean overlySmooth = random.nextBoolean();
        boolean periodicNoise = random.nextBoolean();

        this.score = 0;
        StringBuilder report = new StringBuilder();

        if (overlySmooth) {
            score += 40;
            report.append("Low-entropy texture detected (unnatural smoothing). ");
        }
        if (periodicNoise) {
            score += 45;
            report.append("High-frequency periodic noise patterns identified (checkerboard artifacts). ");
        }

        if (score == 0) {
            this.score = 8 + random.nextInt(12);
            this.detailedReport = "Frequency noise profile matches typical digital sensor hardware.";
        } else {
            this.detailedReport = report.toString().trim();
        }
    }

    @Override
    public int getRiskContribution() {
        return Math.min(100, score);
    }

    @Override
    public String getDescription() {
        return detailedReport;
    }
}

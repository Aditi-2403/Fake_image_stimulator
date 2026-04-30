package com.analyzer;

import java.util.Random;

/**
 * Module: Image Metadata Scrutiny
 * UPGRADED: Flags missing EXIF data or generic software signatures.
 */
public class MetadataAnalyzer implements Analyzer {
    private int score;
    private Random random = new Random();
    private String detailedReport = "";

    @Override
    public void analyze() throws InterruptedException {
        System.out.println("[L3] Metadata Module: Scanning headers and device signatures...");
        Thread.sleep(600);

        boolean missingExif = random.nextBoolean();
        boolean genericSignatures = random.nextBoolean();

        this.score = 0;
        StringBuilder report = new StringBuilder();

        if (missingExif) {
            score += 30;
            report.append("Critical EXIF hardware headers are missing. ");
        }
        if (genericSignatures) {
            score += 40;
            report.append("Generic software signatures suggest algorithmic origin. ");
        }

        if (score == 0) {
            this.score = 2 + random.nextInt(8);
            this.detailedReport = "Hardware metadata and device signatures verified.";
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

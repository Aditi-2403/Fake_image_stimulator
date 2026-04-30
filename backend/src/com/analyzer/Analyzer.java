package com.analyzer;

/**
 * Interface for different analysis strategies.
 * Demonstrates Polymorphism.
 */
public interface Analyzer {
    void analyze() throws InterruptedException;

    int getRiskContribution();

    String getDescription();
}

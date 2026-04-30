package com.analyzer;

/**
 * Preprocessor handles initial image cleanup and resizing.
 */
public class Preprocessor extends ImageProcessor {
    public Preprocessor() {
        super("Preprocessing");
    }

    @Override
    public void process() throws InterruptedException {
        simulateDelay(1000); // Simulate 1 second work
        System.out.println("Preprocessing complete: Noise reduction applied.");
    }
}

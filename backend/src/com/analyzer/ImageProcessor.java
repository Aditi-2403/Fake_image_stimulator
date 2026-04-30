package com.analyzer;

/**
 * Base class for all image processing components.
 * Demonstrates the concept of Encapsulation and Inheritance.
 */
public abstract class ImageProcessor {
    protected String name;

    public ImageProcessor(String name) {
        this.name = name;
    }

    // Abstract method to be implemented by subclasses
    public abstract void process() throws InterruptedException;

    protected void simulateDelay(int ms) throws InterruptedException {
        System.out.println("[Backend] Running: " + name + "...");
        Thread.sleep(ms);
    }
}

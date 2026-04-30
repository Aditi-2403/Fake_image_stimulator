package com.analyzer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/analyze", new AnalysisHandler());
        server.setExecutor(null);
        System.out.println("[Backend] Fake Image Stimulator Forensic Server started on port 8080...");
        server.start();
    }

    static class AnalysisHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
            exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, X-File-Name");

            if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }

            if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                try {
                    System.out.println("\n[Pipeline] Initializing Ensemble Analysis Flow...");

                    // Extract Metadata for Secret Accuracy Mode
                    String fileName = exchange.getRequestHeaders().getFirst("X-File-Name");
                    System.out.println("[Pipeline] Target Analysis: " + fileName);

                    InputStream is = exchange.getRequestBody();
                    byte[] imageBytes = is.readAllBytes();

                    new PreprocessingLayer().process();
                    new FeatureExtractor().extract();

                    List<Analyzer> detectors = new ArrayList<>();
                    detectors.add(new MetadataAnalyzer());
                    detectors.add(new VisualArtifactAnalyzer());
                    detectors.add(new FrequencyAnalyzer());

                    // Unified Ensemble Engine
                    EnsembleAiEngine ensemble = new EnsembleAiEngine();
                    ensemble.setImageData(imageBytes);
                    ensemble.setFileName(fileName);
                    detectors.add(ensemble);

                    for (Analyzer detector : detectors) {
                        detector.analyze();
                    }

                    AnalysisResult result = new ScoreFusionEngine(detectors).generateFinalResult();

                    String jsonResponse = result.toJson();
                    exchange.sendResponseHeaders(200, jsonResponse.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(jsonResponse.getBytes());
                    os.close();

                    System.out.println("[Pipeline] Ensemble Fusion Sequence Complete.");

                } catch (Exception e) {
                    e.printStackTrace();
                    exchange.sendResponseHeaders(500, -1);
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
}

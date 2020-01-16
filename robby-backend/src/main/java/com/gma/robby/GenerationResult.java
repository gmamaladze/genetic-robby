package com.gma.robby;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;

public class GenerationResult {
    private double avgScore;
    private long generationId;
    private GenomeResult[] genomeResults;

    public GenerationResult(long generationId, GenomeResult[] genomeResults) {
        this.generationId = generationId;
        this.genomeResults = genomeResults;
        this.avgScore =
                Arrays.stream(genomeResults).mapToDouble(GenomeResult::getFitness)
                        .average()
                        .orElse(Double.NaN);
    }

    @Override
    public String toString() {
        return String.format("Generation: %5d  Score: %10.4f ", generationId, avgScore);
    }

    public double getAvgScore() {
        return avgScore;
    }

    public long getGenerationId() {
        return generationId;
    }

    @JsonIgnore
    public GenomeResult[] getGenomeResults() {
        return genomeResults;
    }

    @SuppressWarnings({"unused", "Used by deserializer."})
    public Genome getBestGenome() {
        return genomeResults[genomeResults.length - 1].getGenome();
    }
}

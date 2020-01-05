import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class GenerationResult {
    private double avgScore;
    private long generationId;
    private double maxScore;
    private DNA[] survivors;

    public GenerationResult(long generationId, double maxScore, double avgScore, DNA[] survivors) {
        this.generationId = generationId;
        this.maxScore = maxScore;
        this.avgScore = avgScore;
        this.survivors = survivors;
    }

    @Override
    public String toString() {
        return String.format("GenerationResult{ " +
                "generationId=%5d " +
                ", maxScore=%10.4f " +
                ", avgScore=%10.4f " +
                ", survivorsCount=%4d " +
                '}', generationId, maxScore, avgScore, survivors.length);
    }

    public double getAvgScore() {
        return avgScore;
    }

    public long getGenerationId() {
        return generationId;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public DNA[] getSurvivors() {
        return survivors;
    }
}

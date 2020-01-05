import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter implements AutoCloseable {

    private final SequenceWriter seqWriter;

    public ResultWriter(String directoryName) throws IOException {
        File file = new File(directoryName + "/summary.json");
        FileWriter fileWriter = new FileWriter(file, true);
        ObjectMapper mapper = new ObjectMapper();
        seqWriter = mapper.writer().writeValuesAsArray(fileWriter);
    }

    public void write(GenerationResult generationResult) throws IOException {
        SummaryEntry summaryEntry = new SummaryEntry(
                generationResult.getGenerationId(),
                generationResult.getMaxScore(),
                generationResult.getAvgScore());
        seqWriter.write(summaryEntry);
    }

    @Override
    public void close() throws IOException {
        seqWriter.close();
    }

    static class SummaryEntry {

        public final long generationId;
        public final double maxScore;
        public final double avgScore;

        public SummaryEntry(long generationId, double maxScore, double avgScore) {

            this.generationId = generationId;
            this.maxScore = maxScore;
            this.avgScore = avgScore;
        }
    }

    static class FullEntry {

    }
}

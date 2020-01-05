import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultWriter implements AutoCloseable {

    private final SequenceWriter seqWriter;
    private final ObjectMapper mapper;
    private String dirName;

    public ResultWriter(String directoryName) throws IOException {
        dirName = directoryName;
        File file = new File(directoryName + "/summary.json");
        FileWriter fileWriter = new FileWriter(file, true);
        mapper = new ObjectMapper();
        seqWriter = mapper.writer().writeValuesAsArray(fileWriter);
    }

    public void write(GenResult genResult) throws IOException {
        writeSummary(genResult);
        writeFull(genResult);
    }

    private void writeFull(GenResult genResult) throws IOException {
        String fileName = String.format(this.dirName + "/gen-%04d.json", genResult.getGenerationId());
        this.mapper.writeValue(new File(fileName), genResult);
    }

    private void writeSummary(GenResult genResult) throws IOException {
        SummaryEntry summaryEntry = new SummaryEntry(
                genResult.getGenerationId(),
                genResult.getMaxScore(),
                genResult.getAvgScore());
        this.seqWriter.write(summaryEntry);
    }

    @Override
    public void close() throws IOException {
        this.seqWriter.close();
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
}

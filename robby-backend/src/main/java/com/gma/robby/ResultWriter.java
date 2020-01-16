package com.gma.robby;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

    public void write(GenerationResult generationResult) throws IOException {
        writeSummary(generationResult);
        writeFull(generationResult);
    }

    private void writeFull(GenerationResult generationResult) throws IOException {
        if (generationResult.getGenerationId() % 100 != 0) return;

        String fileName = this.dirName + "/gen-latest.json";
        File genFile = new File(fileName);
        this.mapper.writeValue(genFile, generationResult);

        String fileNameLatest = String.format(this.dirName + "/gen-%06d.json", generationResult.getGenerationId());
        Path from = genFile.toPath();
        Path to = Paths.get(fileNameLatest);
        Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
    }

    private void writeSummary(GenerationResult generationResult) throws IOException {
        SummaryEntry summaryEntry = new SummaryEntry(
                generationResult.getGenerationId(),
                generationResult.getAvgScore());
        this.seqWriter.write(summaryEntry);
    }

    @Override
    public void close() throws IOException {
        this.seqWriter.close();
    }

    static class SummaryEntry {

        public final long generationId;
        public final double avgScore;

        public SummaryEntry(long generationId, double avgScore) {

            this.generationId = generationId;
            this.avgScore = avgScore;
        }
    }
}

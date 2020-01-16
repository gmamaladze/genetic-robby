package com.gma.robby;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Configuration {

    private double mutationProbability = 0.005;
    private double survivorQuota = 0.03;
    private double genPoolQuota = 0.9;
    private int populationSize = 300;
    private long numberOfBoards = 200;
    private double newBoardQuota = 0.02;
    private int boardWidth = 10;
    private int boardHeight = 10;
    private int numberOfCans = 50;
    private int numberOfMoves = 200;
    private long numberOfGenerations = 100000;
    private int numberOfThreads = 4;
    private boolean debug = false;

    public static Configuration getDefault() {
        return new Configuration();
    }

    public void save(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(fileName), this);
    }

    public static Configuration load(String fileName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(fileName), Configuration.class);
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public double getSurvivorQuota() {
        return survivorQuota;
    }

    public void setSurvivorQuota(double survivorQuota) {
        this.survivorQuota = survivorQuota;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public long getNumberOfBoards() {
        return numberOfBoards;
    }

    public void setNumberOfBoards(long numberOfBoards) {
        this.numberOfBoards = numberOfBoards;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public int getNumberOfCans() {
        return numberOfCans;
    }

    public void setNumberOfCans(int numberOfCans) {
        this.numberOfCans = numberOfCans;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    public long getNumberOfGenerations() {
        return numberOfGenerations;
    }

    public void setNumberOfGenerations(long numberOfGenerations) {
        this.numberOfGenerations = numberOfGenerations;
    }

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public String toString() {
        return "com.gma.robby.Configuration{" +
                "mutationProbability=" + mutationProbability +
                ", survivorQuota=" + survivorQuota +
                ", populationSize=" + populationSize +
                ", numberOfBoards=" + numberOfBoards +
                ", boardWidth=" + boardWidth +
                ", boardHeight=" + boardHeight +
                ", numberOfCans=" + numberOfCans +
                ", numberOfMoves=" + numberOfMoves +
                ", numberOfGenerations=" + numberOfGenerations +
                ", numberOfThreads=" + numberOfThreads +
                '}';
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {

        this.debug = debug;
    }

    public double getGenPoolQuota() {
        return genPoolQuota;
    }

    public void setGenPoolQuota(double genPoolQuota) {
        this.genPoolQuota = genPoolQuota;
    }

    public double getNewBoardQuota() {
        return newBoardQuota;
    }

    public void setNewBoardQuota(double newBoardQuota) {
        this.newBoardQuota = newBoardQuota;
    }
}

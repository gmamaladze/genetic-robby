package com.gma.robby;

public class SessionResult {
    private int score;
    private Genome Genome;

    public SessionResult(Genome Genome, int score) {

        this.Genome = Genome;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public Genome getGenome() {
        return Genome;
    }
}

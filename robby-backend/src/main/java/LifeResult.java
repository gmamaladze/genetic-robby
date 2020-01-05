public class LifeResult {
    private int score;
    private DNA DNA;

    public LifeResult(DNA DNA, int score) {

        this.DNA = DNA;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public DNA getDNA() {
        return DNA;
    }
}

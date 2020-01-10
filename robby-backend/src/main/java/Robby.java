public class Robby {
    private Position position;
    private int pointCount;
    private final Genome Genome;

    public Robby(Genome Genome) {
        this.Genome = Genome;
        this.position = new Position(0, 0);
        this.pointCount = 0;
    }

    public ActionResult move(Board board) {
        Situation situation = board.getSituationAt(this.position);
        //NOTE: Add memory to situation here
        Action action = Genome.getAction(situation);
        ActionResult result = action.perform(this.position, board);
        this.position = result.getPosition();
        this.pointCount += result.getRewardPointCount();
        return result;
    }

    public int getPointCount() {
        return pointCount;
    }

    public Genome getGenome() {
        return this.Genome;
    }
}

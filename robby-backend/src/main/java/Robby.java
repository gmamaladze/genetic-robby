public class Robby {
    private Position position;
    private int pointCount;
    private final DNA DNA;

    public Robby(DNA DNA) {
        this.DNA = DNA;
        this.position =  new Position(0,0);
        this.pointCount = 0;
    }

    public ActionResult move(Board board) {
        Situation situation = board.getSituationAt(this.position);
        //TODO: Add memory to situation here
        Action action = DNA.getAction(situation);
        ActionResult result = action.perform(this.position, board);
        this.position = result.getPosition();
        this.pointCount += result.getRewardPointCount();
        return result;
    }

    public int getPointCount() {
        return pointCount;
    }

    public DNA getDNA() {
        return this.DNA;
    }
}

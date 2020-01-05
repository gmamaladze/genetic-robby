public class BoardFast extends Board {

    private final int[][] situationCodes;
    private BoardSimple boardSimple;

    BoardFast(BoardSimple boardSimple) {
        this.boardSimple = boardSimple;
        this.situationCodes = new int[getWidth()][getHeight()];
        for (int i = 0; i < getWidth(); i++) {
            this.situationCodes[i] = new int[getHeight()];
            for (int j = 0; j < getHeight(); j++) {
                Position current = new Position(i, j);
                this.situationCodes[i][j] = this.boardSimple.getSituationAt(current).getCode();
            }
        }
    }

    @Override
    public int getWidth() {
        return this.boardSimple.getWidth();
    }

    @Override
    public int getHeight() {
        return this.boardSimple.getHeight();
    }

    @Override
    public boolean hasCan(Position position) {
        return this.boardSimple.hasCan(position);
    }

    @Override
    public boolean tryRemoveCan(Position position) {
        boolean ok = this.boardSimple.tryRemoveCan(position);
        if (!ok) return false;

        PositionOffset[] offsets = PositionOffset.values();
        for (PositionOffset offset : offsets) {
            Position current = offset.from(position);
            if (isWall(current)) continue;
            this.situationCodes[current.x][current.y] = this.boardSimple.getSituationAt(current).getCode();
        }
        return true;
    }

    @Override
    protected Board clone() {
        return new BoardFast(this.boardSimple);
    }

    @Override
    public Situation getSituationAt(Position position) {
        return new Situation(this.situationCodes[position.x][position.y]);
    }
}

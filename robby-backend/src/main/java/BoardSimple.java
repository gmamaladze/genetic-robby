public class BoardSimple extends Board {

    private final boolean[][] cans;

    BoardSimple(boolean[][] cans) {
        this.cans = cans;
    }

    @Override
    public int getWidth() {
        return this.cans.length;
    }

    @Override
    public int getHeight() {
        return this.cans[0].length;
    }


    @Override
    public Board clone() {
        return new BoardSimple(this.cans.clone());
    }


    @Override
    public boolean hasCan(Position position) {
        return cans[position.x][position.y];
    }

    @Override
    public boolean tryRemoveCan(Position position) {
        boolean hadCan = hasCan(position);
        if (!hadCan) return false;
        this.cans[position.x][position.y]=false;
        return true;
    }

    @Override
    public Situation getSituationAt(Position position) {
        Offset[] offsets = Offset.values();
        Content[] situation = new Content[offsets.length];
        for (int i = 0; i < offsets.length; i++) {
            Offset offset = offsets[i];
            Position currentPosition = offset.from(position);
            situation[i] = getContentAt(currentPosition);
        }
        return new Situation(situation);
    }

    private Content getContentAt(Position position) {
        return isWall(position)
                ? Content.WALL
                : hasCan(position)
                    ? Content.CAN
                    : Content.EMPTY;
    }
}

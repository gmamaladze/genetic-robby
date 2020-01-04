import java.util.Random;

public abstract class Board {

    static Board getRandom(int width, int height, int cansCount) {
        double probability = (double)cansCount / (width * height);
        int placedCansCount = 0;
        Random rnd = new Random(System.currentTimeMillis());
        boolean[][] cans = new boolean[width][height];
        while (true) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    boolean isCan = rnd.nextDouble() < probability;
                    cans[i][j] = isCan;
                    if (isCan) placedCansCount++;
                    if (placedCansCount==cansCount) {
                        //Note: select appropriate implementation here
                        return new BoardSimple(cans);
                    }
                }
            }
        }
    }

    @Override
    protected abstract Board clone();

    public abstract int getWidth();

    public abstract int getHeight();

    public boolean isWall(Position position) {
        return  position.x < 0 ||
                position.y < 0 ||
                position.x >= this.getWidth() ||
                position.y >= this.getHeight();
    }

    abstract boolean hasCan(Position position);

    public abstract boolean tryRemoveCan(Position position);

    public abstract Situation getSituationAt(Position position);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                char toPrint = hasCan(new Position(j, i)) ? 'x':' ';
                sb.append(toPrint);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}

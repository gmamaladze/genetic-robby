import java.util.Random;

public enum Action {

    MoveNorth   (0) {
        @Override
        public ActionResult perform(Position position, Board board) {
            return this.move(position, board, PositionOffset.NORTH);
        }
    },
    MoveSouth   (1) {
        @Override
        public ActionResult perform(Position position, Board board) {
            return this.move(position, board, PositionOffset.SOUTH);
        }
    },
    MoveEast    (2) {
        @Override
        public ActionResult perform(Position position, Board board) {
            return this.move(position, board, PositionOffset.EAST);
        }
    },
    MoveWest    (3) {
        @Override
        public ActionResult perform(Position position, Board board) {
            return this.move(position, board, PositionOffset.WEST);
        }
    },
    StayPut     (4) {
        @Override
        public ActionResult perform(Position position, Board board) {
            return new ActionResult(position, 0);
        }
    },
    PickUpCan   (5) {
        @Override
        public ActionResult perform(Position position, Board board) {
            boolean hadCan = board.tryRemoveCan(position);
            int reward = hadCan ? 10 : -1;
            return new ActionResult(position, reward);
        }
    },
    MoveRandom  (6) {
        @Override
        public ActionResult perform(Position position, Board board) {
            PositionOffset offset;
            do {
                offset = PositionOffset.values()[this.random.nextInt(PositionOffset.values().length)];
            } while (offset==PositionOffset.CURRENT);
            return move(position, board, offset);
        }
    };

    protected ActionResult move(Position position, Board board, PositionOffset offset) {
        Position newPosition = offset.from(position);
        boolean isWall = board.isWall(newPosition);
        int reward = isWall ? -5 : 0;
        newPosition = isWall ? position : newPosition;
        return new ActionResult(newPosition, reward);
    }

    private int code;
    protected final Random random;

    Action(int code) {

        this.code = code;
        this.random = new Random();
    }

    public int getCode() {
        return code;
    }

    public abstract ActionResult perform(Position position, Board board);
}


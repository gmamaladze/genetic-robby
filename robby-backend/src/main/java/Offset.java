public enum Offset {
    NORTH {
        @Override
        public Position from(Position position) {
            return new Position(position.x, position.y - 1);
        }
    },
    SOUTH {
        @Override
        public Position from(Position position) {
            return new Position(position.x, position.y + 1);
        }
    },
    EAST {
        @Override
        public Position from(Position position) {
            return new Position(position.x + 1, position.y);
        }
    },
    WEST {
        @Override
        public Position from(Position position) {
            return new Position(position.x - 1, position.y);
        }
    },
    CURRENT {
        @Override
        public Position from(Position position) {
            return position;
        }
    };

    public abstract Position from(Position position);
}

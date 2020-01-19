package com.gma.robby;

public enum Offset {
    NORTH {
        @Override
        public Position from(Position position) {
            return new Position(position.getX(), position.getY() - 1);
        }
    },
    SOUTH {
        @Override
        public Position from(Position position) {
            return new Position(position.getX(), position.getY() + 1);
        }
    },
    EAST {
        @Override
        public Position from(Position position) {
            return new Position(position.getX() + 1, position.getY());
        }
    },
    WEST {
        @Override
        public Position from(Position position) {
            return new Position(position.getX() - 1, position.getY());
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

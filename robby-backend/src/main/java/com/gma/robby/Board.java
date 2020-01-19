package com.gma.robby;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Board {

    private static final Random rnd = new Random(System.currentTimeMillis());
    private final Set<Position> cans;
    private final Point size;

    @JsonCreator
    public Board(Set<Position> cans, Point size) {
        this.cans = cans;
        this.size = size;
    }

    public static Board getRandom(int width, int height, int cansCount) {
        Set<Position> cans = new HashSet<>();
        double probability = (double) cansCount / (width * height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                boolean isCan = rnd.nextDouble() < probability;
                if (!isCan) continue;
                Position canPosition = new Position(i, j);
                cans.add(canPosition);
            }
        }
        Point size = new Point(width, height);
        return new Board(cans, size);
    }

    @SuppressWarnings("MethodDoesntCallSuperMethod")
    @Override
    public Board clone() {
        return new Board(new HashSet<>(this.cans), this.size);
    }

    public boolean isWall(Position position) {
        return position.getX() < 0 ||
                position.getY() < 0 ||
                position.getX() >= this.size.x ||
                position.getY() >= this.size.y;
    }

    public boolean hasCan(Position position) {
        return this.cans.contains(position);
    }

    public boolean tryRemoveCan(Position position) {
        return this.cans.remove(position);
    }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size.x; i++) {
            for (int j = 0; j < this.size.y; j++) {
                char toPrint = hasCan(new Position(j, i)) ? 'x' : ' ';
                sb.append(toPrint);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    private Content getContentAt(Position position) {
        return isWall(position)
                ? Content.WALL
                : (hasCan(position)
                ? Content.CAN
                : Content.EMPTY);
    }

    @SuppressWarnings("unused")
    public Set<Position> getCans() {
        return cans;
    }

    @SuppressWarnings("unused")
    public Point getSize() {
        return size;
    }
}

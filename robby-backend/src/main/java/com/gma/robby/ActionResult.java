package com.gma.robby;

public class ActionResult {
    private final Position position;
    private final int reward;

    public ActionResult(Position position, int reward) {

        this.position = position;
        this.reward = reward;
    }

    public Position getPosition() {
        return this.position;
    }

    public int getRewardPointCount() {
        return this.reward;
    }
}

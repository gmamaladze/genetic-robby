'use strict';

import {Universe} from "./universe.js";
import {Point} from "./point.js";
import {Robby} from "./robby";
import {Cans} from "./cans";

export {Game};

function Game(dnaArray, width = 10, height = 10, numberOfMoves = 200, canPositions) {
    this.size = new Point(width, height);
    Point.prototype.size = this.size;
    this.maxMoveCount = numberOfMoves;
    this.moveCount = 0;
    let startingPoint = new Point(0, 0);
    let robot = new Robby(startingPoint, dnaArray);

    let cans = new Cans(0);
    for (let i = 0; i < canPositions.length; i++) {
        let pos = canPositions[i];
        cans.placeCan(new Point(pos.x, pos.y));
    }

    this.universe = new Universe(robot, cans);
    return this;
}

function tick(draw = () => {
}) {
    if (this.isOver()) {
        throw "Game over"
    }
    let cells = this.universe.tick();
    draw(cells);
    this.moveCount++;
}

function moves() {
    return this.moveCount;
}

function score() {
    return this.universe.score();
}

function isOver() {
    return this.moveCount > this.maxMoveCount;
}

Game.prototype = {
    tick,
    isOver,
    moves,
    score
};
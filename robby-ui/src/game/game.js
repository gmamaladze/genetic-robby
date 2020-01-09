'use strict';

import {Universe} from "./universe.js";
import {Point} from "./point.js";
import {Robby} from "./robby";

export {Game};

function Game(dna, size = new Point(10, 10), numberOfMoves = 200) {
    this.size = size;
    this.maxMoveCount = numberOfMoves;
    this.moveCount = 0;
    Point.prototype.size = size;
    this.universe = new Universe(new Robby(new Point(), dna));
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
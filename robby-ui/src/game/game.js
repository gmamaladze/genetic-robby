'use strict';

import {Universe} from "./universe.js";
import {Point} from "./point.js";

export {Game};

function Game(size = new Point(10, 10), universe = new Universe(), numberOfMoves = 200) {
    this.size = size;
    this.maxMoveCount = numberOfMoves;
    this.moveCount = 0;
    Point.prototype.size = size;
    this.universe = universe;
    return this;
}

function tick(draw = () => {}) {
    if (this.isOver()) {
        throw "Game over"
    }
    let cells = this.universe.tick();
    draw(cells);
    this.moveCount++;
}

function isOver() {
    return this.moveCount > this.maxMoveCount;
}

Game.prototype = {
    tick,
    isOver
};
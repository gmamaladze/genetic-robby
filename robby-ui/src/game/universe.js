'use strict';

import {Robby} from "./robby.js";
import {Cans} from "./cans.js";
import {Cell} from "./cell";

export {
    Universe
};

function Universe(robby = new Robby(), cans = new Cans()) {
    this.robot = robby;
    this.cans = cans;
}

function tick() {
    this.robot.move(this.cans);
    return this.getCells();
}


function getCells() {
    let head = this.robot.head();
    let canPoints = this.cans.cans();
    let tracePoints = this.robot.trace();

    let canCells = canPoints.map(function (p) {
        return new Cell(p, "can")
    });

    let traceCells = tracePoints.map(function (p) {
        return new Cell(p, "trace")
    });

    return traceCells.concat(canCells).concat([new Cell(head, "robot")]);
}

function score() {
    return this.robot.score();
}

Universe.prototype = {
    tick,
    getCells,
    score
};
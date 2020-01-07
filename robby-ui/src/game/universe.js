'use strict';

import {Robby} from "./robby.js";
import {Cans} from "./cans.js";

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
    return [this.robot.getCells(), this.cans.getCells()].reduce((acc, cur) => acc.concat(cur), [])
}

Universe.prototype = {
    tick,
    getCells,
};
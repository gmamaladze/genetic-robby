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
    let position = this.robot.head();
    let situation = this.cans.situationAt(position);
    let action = this.robot.getAction(situation);
    let result = action.perform(position, action, this.cans);
    this.robot.apply(result);
    return this.getCells();
}


function getCells() {
    return [this.robot.getCells(), this.cans.getCells()].reduce((acc, cur) => acc.concat(cur), [])
}

Universe.prototype = {
    tick,
    getCells,
};
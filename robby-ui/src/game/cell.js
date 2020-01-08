'use strict';
import {Point} from "./point.js";

export {Cell};

export const BEING = {
    None:   "none",
    Robot:  "robot",
    Can:    "can",
    Trace:  "trace"
};

function Cell(point = new Point(0, 0), being = "") {
    this.point = point;
    this.being = being;
    return this;
}

function getId() {
    return this.being === BEING.Robot
        ? BEING.Robot
        : BEING.None + this.x + this.y;
}

Cell.prototype = {
    getId
};

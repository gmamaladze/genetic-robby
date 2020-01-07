'use strict';

import {Universe} from "./universe.js";

import {Robby} from "./robby.js";

import {Can} from "./can.js";

import {Point} from "./point.js";

import {DIRECTIONS} from "./directions.js";

export {Game};

function Game(size = new Point(20, 20), universe = new Universe()) {
    this.size = size;
    Point.prototype.size = size;
    this.universe = universe;
}


function tick(input, draw = () => {}) {
    let cells = this.universe.tick();
    draw(cells);
}

const SNAKE_CODE = 100;
const FOOD_CODE = -100;
const UNKNOWN_CODE = 0;


function encodeKind(being) {
    if (Robby.prototype.isPrototypeOf(being)) return SNAKE_CODE;
    if (Can.prototype.isPrototypeOf(being)) return FOOD_CODE;
    return UNKNOWN_CODE;
}

Game.prototype = {
    tick,
    hint
};
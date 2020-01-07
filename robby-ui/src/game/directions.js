'use strict';
import {Point} from "./point.js";

export const DIRECTIONS = {
    UP: new Point(0, -1),
    DOWN: new Point(0, 1),
    RIGHT: new Point(1, 0),
    LEFT: new Point(-1, 0),
    CURRENT: new Point(0, 0),

    getAll: function () {
        return [this.UP, this.RIGHT, this.DOWN, this.LEFT, this.CURRENT]
    },

    getRandom: function () {
        let all = [this.UP, this.RIGHT, this.DOWN, this.LEFT];
        let rndIndex = Math.floor((Math.random() * all.length));
        return all[rndIndex];
    },

    getFromTo: function (point1, point2) {
        let dx = point2.x - point1.x;
        let dy = point2.y - point1.y;
        return new Point(Math.sign(dx), Math.sign(dy));
    }
};
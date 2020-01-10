'use strict';
import {Point} from "./point.js";

export const DIRECTIONS = {
    North: new Point(0, -1),
    South: new Point(0, 1),
    East: new Point(1, 0),
    West: new Point(-1, 0),
    Current: new Point(0, 0),

    getRandom: function () {
        let rndIndex = Math.floor(Math.random() * DIRECTIONS.woCurrent.length);
        return DIRECTIONS.woCurrent[rndIndex];
    }
};

DIRECTIONS.woCurrent = [DIRECTIONS.North, DIRECTIONS.South, DIRECTIONS.East, DIRECTIONS.West];

DIRECTIONS.byCode = [
    DIRECTIONS.North,
    DIRECTIONS.South,
    DIRECTIONS.East,
    DIRECTIONS.West,
    DIRECTIONS.Current];

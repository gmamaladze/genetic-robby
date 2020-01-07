'use strict';
import {Point} from "./point.js";
import {DIRECTIONS} from "./directions.js";
import {CONTENT} from "./content";

export {Cans};

function Cans(numberOfCans) {
    this.positions = Point.getRandom(numberOfCans).reduce(function(map, point) {
        map[point] = point;
        return map;
    }, {});
}

function situationAt(point) {
    DIRECTIONS.getAll().map(function(direction) {
        let cur = point.add(direction);
        return cur.isWall()
            ? CONTENT.WALL
            : hasCan(point)
                ? CONTENT.CAN
                : CONTENT.EMPTY;
    })
}

function hasCan(point) {
    return this.positions.has(point);
}

function tryRemove(point) {
    return this.positions.delete(point);
}

function getCells() {
    let being = "can";
    return this.positions.keys().map(function (point) {
        return {point, being}
    });
}

Cans.prototype = {
    tryRemove,
    situationAt,
    getCells
};

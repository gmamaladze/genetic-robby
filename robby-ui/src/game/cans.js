'use strict';
import {Point} from "./point.js";
import {DIRECTIONS} from "./directions.js";

export {Cans};

function Cans(numberOfCans) {
    this.positions = Point.getRandom(numberOfCans).reduce(function(map, point) {
        map[point] = point;
        return map;
    }, {});
}

function situationAt(position) {
    DIRECTIONS.getAll().map(function(direction) {
        let cur = position.add(direction);
        cur.isWall
        return
    })
}

function tryRemove(point) {
    return this.positions.delete(point);
}

function getCells() {
    let being = this;
    return this.positions.keys().map(function (point) {
        return {point, being}
    });
}

Cans.prototype = {
    tryRemove,
    getCells
};

'use strict';
import {Point} from "./point.js";
import {DIRECTIONS} from "./directions.js";
import {CONTENT} from "./content";
import {Cell} from "./cell";

export {Cans};

function Cans(numberOfCans=50) {
    this.positions = new Map();
    while(this.positions.size<numberOfCans) {
        let randomPoint = new Point();
        this.positions.set(randomPoint, true);
    }
}

function situationAt(point) {
    let cans = this;
    return DIRECTIONS.getAll().map(function(direction) {
        let cur = point.add(direction);
        return cur.isWall()
            ? CONTENT.WALL
            : hasCan(cans, point)
                ? CONTENT.CAN
                : CONTENT.EMPTY;
    })
}

function hasCan(cans, point) {
    return cans.positions.has(point);
}

function tryRemove(point) {
    return this.positions.delete(point);
}

function getCells() {
    return [...this.positions.keys()].map(function (point, ) {
        return new Cell(point, "can");
    });
}

Cans.prototype = {
    tryRemove,
    situationAt,
    getCells
};

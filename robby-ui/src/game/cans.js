'use strict';
import {Point} from "./point.js";
import {DIRECTIONS} from "./directions.js";
import {CONTENT} from "./content";

export {Cans};

function Cans(numberOfCans=50) {
    let width = Point.prototype.size.x;
    let height = Point.prototype.size.y;
    let totalCells = width * height;
    let probability = numberOfCans / totalCells;
    this.positions = new Map();
    for (let i = 0; i < width; i++) {
        for (let j = 0; j < height; j++) {
            if (Math.random() > probability) continue;
            placeCan(this, new Point(i, j));
        }
    }
}

function situationAt(point) {
    let cans = this;
    return DIRECTIONS.getAll().map(function (direction) {
        let cur = point.add(direction);
        return cur.isWall()
            ? CONTENT.WALL
            : hasCan(cans, point)
                ? CONTENT.CAN
                : CONTENT.EMPTY;
    })
}

function placeCan(cans, point) {
    let canPlace = !hasCan(cans, point);
    if (!canPlace) return false;
    cans.positions.set(point.getHash(), point);
}

function hasCan(cans, point) {
    return cans.positions.has(point.getHash());
}

function tryRemove(point) {
    return this.positions.delete(point.getHash());
}

function cans() {
    return [...this.positions.values()];
}

Cans.prototype = {
    tryRemove,
    situationAt,
    cans
};

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
            this.placeCan(new Point(i, j));
        }
    }
}

function situationAt(point) {
    let cans = this;
    let situation = new Array(DIRECTIONS.byCode.length);
    for (let i = 0; i < situation.length; i++) {
        let direction = DIRECTIONS.byCode[i];
        let cur = point.add(direction);
        situation[i] = cur.isWall()
            ? CONTENT.WALL
            //NOTE: do not inline cas with this
            : cans.hasCan(point)
                ? CONTENT.CAN
                : CONTENT.EMPTY;
    }
    return situation;
}

function placeCan(point) {
    let canPlace = !this.hasCan(point);
    if (!canPlace) return false;
    this.positions.set(point.getHash(), point);
}

function hasCan(point) {
    return this.positions.has(point.getHash());
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
    hasCan,
    placeCan,
    cans
};

'use strict';
import {DIRECTIONS} from "./directions.js";
import {Point} from "./point.js";

export {Robby};

function Robby(position = new Point(0, 0)) {
    this._head = position;
    this._trace = [];
    this._score = 0;
    return this;
}

function score() {
    return this._score;
}

function head() {
    return this._head;
}

function move(direction) {
    let head = this.body[0].add(direction);
    this._trace.unshift(head);
    return this;
}

function getCells() {
    let being = this;
    return [this._head].concat(this._trace).map(function (point) {
        being = point.equals(this.head()) ? "robot" : "trace";
        return {point, being}
    });
}

Robby.prototype = {
    head,
    move,
    score,
    getCells,
    traceLength: 10
};

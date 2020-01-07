'use strict';
import {DIRECTIONS} from "./directions.js";
import {Point} from "./point.js";
import {ACTIONS} from "./action";
import {CONTENT} from "./content";

export {Robby};

function Robby(position = new Point(0, 0), dna) {
    this._head = position;
    this._trace = [];
    this._score = 0;
    this._dna = dna;
    return this;
}

function score() {
    return this._score;
}

function head() {
    return this._head;
}

function move(cans) {
    let situation = this.cans.situationAt(this._head);
    let action = this._dna.getAction(situation);
    let result = action.perform(this._head, action, cans);
    this._score+=result.reward;
    this._head=result.position;
    this._trace.unshift(this._head);
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

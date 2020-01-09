'use strict';
import {Point} from "./point.js";
import {Dna} from "./dna.js";
import {ACTIONS} from "./action";

export {Robby};

function Robby(position = new Point(0, 0), dnaArray) {
    this._head = position;
    this._trace = [];
    this._score = 0;
    this._dna = new Dna(dnaArray);
    return this;
}

function move(cans) {
    let situation = cans.situationAt(this._head);
    let action = this._dna.getAction(situation);
    let result = ACTIONS.perform(this._head, action, cans);
    this._score += result.reward;
    this._head = result.position;
    this._trace.unshift(this._head);
    return this;
}

function trace() {
    return this._trace;
}

function head() {
    return this._head;
}

function score() {
    return this._score;
}

Robby.prototype = {
    move,
    trace,
    head,
    score
};

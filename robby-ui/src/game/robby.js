'use strict';
import {Point} from "./point.js";
import {Dna} from "./dna.js";
import {ACTIONS} from "./action";
import {Cell} from "./cell";

export {Robby};

function Robby(position = new Point(0, 0), dna = new Dna()) {
    this._head = position;
    this._trace = [];
    this._score = 0;
    this._dna = dna;
    return this;
}

function move(cans) {
    let situation = cans.situationAt(this._head);
    let action = this._dna.getAction(situation);
    let result = ACTIONS.perform(this._head, action, cans);
    this._score+=result.reward;
    this._head=result.position;
    this._trace.unshift(this._head);
    return this;
}

function getCells() {
    return [new Cell(point, "robot")].concat(this._trace.map(function (point) {
        return new Cell(point, "trace");
    }));
}

Robby.prototype = {
    move,
    getCells,
    traceLength: 10
};

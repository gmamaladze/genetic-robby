'use strict';
import {DIRECTIONS} from "./directions.js";
import {ACTIONS} from "./action";
import {CONTENT} from "./content";

export {Dna};

function getRandomDna() {
    let dnaLength = Math.pow(CONTENT.byCode.length, DIRECTIONS.byCode.length);
    return new Array(dnaLength).fill(Math.floor((Math.random() * ACTIONS.byCode.length)));
}

function Dna(dnaArray = getRandomDna()) {
    this._dna = dnaArray;
    return this;
}

function getAction(situation) {
    let situationCode = encode(situation);
    let actionCode = this._dna[situationCode];
    return ACTIONS.byCode[actionCode];
}

function encode(contents) {
    let situationCode = 0;
    for (let positionIndex=0; positionIndex<DIRECTIONS.byCode.length; positionIndex++) {
        situationCode += Math.pow(CONTENT.byCode.length, positionIndex) * contents[positionIndex];
    }
    return situationCode;
}

Dna.prototype = {
    getAction,
    traceLength: 10
};

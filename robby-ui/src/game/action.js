'use strict';
import {DIRECTIONS} from "./directions.js";

export const ACTIONS = {
    moveNorth:  0,
    moveEast :  1,
    moveSouth:  2,
    moveWest:   3,
    stayPut:    4,
    pickUpCan:  5,
    moveRandom: 6,

    perform: function (point, action, cans) {
        switch (action) {
            case this.moveNorth:
                return this.move(point, DIRECTIONS.UP);
            case this.moveEast:
                return this.move(point, DIRECTIONS.RIGHT);
            case this.moveSouth:
                return this.move(point, DIRECTIONS.DOWN);
            case this.moveWest:
                return this.move(point, DIRECTIONS.LEFT);
            case this.stayPut:
                return {
                    'reward': 0,
                    'position': point
                };
            case this.pickUpCan:
                return {
                    'reward': cans.tryRemove(point) ? 10 : -1,
                    'position': point
                };
            case this.moveRandom:
                let direction = DIRECTIONS.getRandom();
                return this.move(point, direction);
            default:
                throw "Unexpected action " + action;
        }
    },

    move: function(point, direction) {
        let next = point.add(direction);
        let isWall = next.equals(point);
        return {
            'reward':   isWall ? -5 : 0,
            'position':    next
        };
    },
};

ACTIONS.byCode = [
    ACTIONS.moveNorth,
    ACTIONS.moveEast,
    ACTIONS.moveSouth,
    ACTIONS.moveWest,
    ACTIONS.stayPut,
    ACTIONS.pickUpCan,
    ACTIONS.moveRandom];

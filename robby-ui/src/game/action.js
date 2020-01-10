'use strict';
import {DIRECTIONS} from "./directions.js";

export const ACTIONS = {
    moveNorth: 0,
    moveSouth: 1,
    moveEast: 2,
    moveWest: 3,
    stayPut: 4,
    pickUpCan: 5,
    moveRandom: 6,

    perform: function (point, action, cans) {
        switch (action) {
            case this.moveNorth:
                return this.move(point, DIRECTIONS.North);
            case this.moveEast:
                return this.move(point, DIRECTIONS.East);
            case this.moveSouth:
                return this.move(point, DIRECTIONS.South);
            case this.moveWest:
                return this.move(point, DIRECTIONS.West);
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
        let isWall = next.isWall();

        return isWall
            ? {
                'reward': -5,
                'position': point
            }
            : {
                'reward': 0,
                'position': next
            }
    },
};

ACTIONS.byCode = [];
ACTIONS.byCode[ACTIONS.moveNorth] = ACTIONS.moveNorth;
ACTIONS.byCode[ACTIONS.moveSouth] = ACTIONS.moveSouth;
ACTIONS.byCode[ACTIONS.moveEast] = ACTIONS.moveEast;
ACTIONS.byCode[ACTIONS.moveWest] = ACTIONS.moveWest;
ACTIONS.byCode[ACTIONS.stayPut] = ACTIONS.stayPut;
ACTIONS.byCode[ACTIONS.pickUpCan] = ACTIONS.pickUpCan;
ACTIONS.byCode[ACTIONS.moveRandom] = ACTIONS.moveRandom;
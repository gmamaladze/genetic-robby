'use strict';

import {Robby} from "./robby.js";

import {Can} from "./can.js";

export {
    Universe
};

function Universe(robby = new Robby(), fd = new Can()) {
    this.robot = robby;
    this.food = fd;
}

function tick(direction) {
    this.snake.move(direction);
    let head = this.snake.head();
    var others = this.getAt(head);
    others.forEach(other => this.snake.eat(other));
    this.snake.trim();
    this.food.age++;
    if (this.food.age > 100) this.food.kill();
    if (!this.food.isAlive) this.food = new Can();
    return this.getCells();
}


function getCells() {
    return [this.snake.getCells(), this.food.getCells()].reduce((acc, cur) => acc.concat(cur), [])
}

function getAt(point) {
    return point.equals(this.food.head()) ? [this.food] : [];
}

Universe.prototype = {
    tick,
    getCells,
    getAt
};
'use strict';

export {Point};

function Point(x = this.rnd(this.size.x), y = this.rnd(this.size.y)) {
    this.x = x;
    this.y = y;
}

function equals(other) {
    if (!other) return;
    return other.x === this.x && other.y === this.y;
}

function getHash(size = this.size) {
    return this.x + this.y * size.x;
}

function add(point) {
    if (!point) {
        return this;
    }
    return new Point(this.x + point.x, this.y + point.y).overflow();
}

function distance(other) {
    return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
}

function overflow(size = this.size) {
    return new Point(this.x.mod(size.x), this.y.mod(size.y));
}

function isWall(point, size = this.size) {
    let test = point.overflow();
    return test.equals(point);
}

function getRandom(numberOfPoints) {
    return new Array(numberOfPoints).fill(null).map(() => new Point());
}


Point.prototype = {
    equals,
    add,
    getHash,
    isWall,
    distance,
    overflow,
    getRandom,
    size: new Point(20, 20),
    rnd: function (max) {
        return Math.floor(Math.random() * max)
    }
};


Number.prototype.mod = function (n) {
    return ((this % n) + n) % n;
};


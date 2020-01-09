'use strict';

export {Point};

function Point(x = 0, y = 0) {
    this.x = x;
    this.y = y;
}

function getHash() {
    return this.size.x * this.x + this.y;
}

function equals(other) {
    if (!other) return;
    return other.x === this.x && other.y === this.y;
}

function add(point) {
    return new Point(this.x + point.x, this.y + point.y);
}

function isWall(size = this.size) {
    return this.x < 0 || this.y < 0 || this.x >= size.x || this.y >= size.y;
}

Point.prototype = {
    equals,
    add,
    isWall,
    size: new Point(10, 10),
    getHash
};


Number.prototype.mod = function (n) {
    return ((this % n) + n) % n;
};


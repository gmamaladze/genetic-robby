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
function add(point) {
    let candidate = new Point(this.x + point.x, this.y + point.y);
    return candidate.isWall()
        ? point
        : candidate;
}

function isWall(size = this.size) {
    return this.x < 0 || this.y < 0 || this.x >= size.x || this.y >= size.y;
}

Point.prototype = {
    equals,
    add,
    isWall,
    size: new Point(20, 20),
    rnd: function (max) {
        return Math.floor(Math.random() * max)
    }
};


Number.prototype.mod = function (n) {
    return ((this % n) + n) % n;
};


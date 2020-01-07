'use strict';

export {Grid};

function Grid(element = 'body', pixel = 12, size = {x: 20, y: 20}) {
    /* global d3:true */
    this._color = {robot: "silver", can: "green", trace: "blue"};
    let grid = this;
    this.svg = d3.select(element)
        .append('svg')
        .attr('width', size.x * pixel)
        .attr('height', size.y * pixel)
        .on("mousemove", () => {
            grid.hasFocus = true;
        })
        .on("mouseout", () => {
            grid.hasFocus = false;
        });
    this.pixel = pixel;
}

const beingCode = {
    "robot" : 0,
    "trace" : 1,
    "can"   : 2
};

function draw(cells) {

    var blocks = this.svg
        .selectAll("rect")
        .data(cells, d => d.point.getHash() << 8 + beingCode[d.being]);

    blocks.enter()
        .append('rect')
        .attr('width', this.pixel)
        .attr('height', this.pixel)
        .attr('x', d => d.point.x * this.pixel)
        .attr('y', d => d.point.y * this.pixel)
        .style('fill', d => this._color[d.being])
        .merge(blocks)
        .attr('class', d => d.being);

    blocks.exit().remove();
}

Grid.prototype = {
    draw
};

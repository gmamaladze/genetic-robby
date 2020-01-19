'use strict';

export {Grid};

function Grid(element = 'body', title = '#title', pixel = 20, size = {x: 10, y: 10}) {
    /* global d3:true */
    this.title = title;
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

function draw(cells, text) {
    d3.select(this.title)
        .text(text);

    let blocks = this.svg
        .selectAll("rect")
        .data(cells, d => d.getId());

    blocks.enter()
        .append('rect')
        .merge(blocks)
        .attr('width', this.pixel)
        .attr('height', this.pixel)
        .attr('x', d => d.point.x * this.pixel)
        .attr('y', d => d.point.y * this.pixel)
        .attr('class', d => d.being);

    blocks.exit().remove();
}

Grid.prototype = {
    draw
};

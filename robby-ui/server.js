'use strict';

let connect = require('connect');
let serveStatic = require('serve-static');
var favicon = require('serve-favicon');
var path = require('path');
let port = process.env.PORT || 8081;

connect()
    .use(favicon(path.join('build/', 'img', 'favicon.ico')))
    .use(serveStatic('build/'))
    .listen(port, () => console.log(`Listening on http://localhost:${port}`));
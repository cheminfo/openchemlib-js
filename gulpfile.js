'use strict';

var child_process = require('child_process');
var gulp = require('gulp');
var path = require('path');
var fs = require('fs');
var exporter = require('gwt-api-exporter');

try {
    var config = require('./config.json');
} catch (e) {
    console.error('config.json not found. You can copy config.default.json to start from an example.');
    process.exit(1);
}

var classpath = ['src'];

if (config.classpath) {
    if (typeof config.classpath === 'string') {
        classpath.push(config.classpath);
    } else if (Array.isArray(config.classpath)) {
        classpath = classpath.concat(config.class);
    }
}

classpath.push(path.join(config.gwt, 'gwt-dev.jar'), path.join(config.gwt, 'gwt-user.jar'));

classpath = classpath.join(':');

gulp.task('compile:min', compile('min'));
gulp.task('compile:pretty', compile('pretty'));

gulp.task('build:pretty', ['compile:pretty'], build);
gulp.task('build:min', ['compile:min'], build);
gulp.task('export', build);

gulp.task('default', ['build:min']);

gulp.task('copy:openchemlib', copyOpenchemlib);

function build(done) {
    var warDir = path.join('war', config.war);
    var files = fs.readdirSync(warDir);
    var file;
    for (var i = 0; i < files.length; i++) {
        if (files[i].indexOf('.cache.js') > 0) {
            file = path.join(warDir, files[i]);
            break;
        }
    }
    if (!file) {
        throw new Error('Could not find GWT file');
    }
    exporter({
        input: file,
        output: 'lib.js',
        exports: config.exports,
        package: require('./package.json')
    }).then(function () {
        done();
    }, function (e) {
        console.error(e);
    })
}

function compile(mode) {
    var args = [
        '-Xmx512m',
        '-cp', classpath,
        'com.google.gwt.dev.Compiler',
        config.entrypoint,
        '-optimize', '9',
        '-XnocheckCasts',
        '-XnoclassMetadata',
        '-nocheckAssertions',
        '-XjsInteropMode', 'JS',
        '-style'
    ];
    if (mode === 'min') {
        args.push('OBF');
    } else {
        args.push('PRETTY');
    }
    return function () {
        child_process.execFileSync('java', args)
    }
}

function copyOpenchemlib() {
    var chemlibDir = config.openchemlib;
    // TODO copy java files from openchemlib
}

'use strict';

var child_process = require('child_process');
var gulp = require('gulp');
var path = require('path');
var fs = require('fs-extra');
var exporter = require('gwt-api-exporter');
var rimraf = require('rimraf');
var pack = require('./package.json');
var argv = require('minimist')(process.argv.slice(2));

var verbose = argv.v;

var modules = require('./modules.json');
if(argv.m) {
    for (var i = 0; i < modules.length; i++) {
        if(modules[i].name === argv.m) {
            modules = [modules[i]];
            break;
        }
    }
    if (modules.length !== 1) {
        throw new Error('module ' + argv.m + ' not found');
    }
}

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
    var prom = [];
    for (var k = 0; k < modules.length; k++) {
        var mod = modules[k];
        log('Exporting module ' + mod.name);
        var warDir = path.join('war', mod.war);
        var files = fs.readdirSync(warDir);
        var file;
        for (var i = 0; i < files.length; i++) {
            if (files[i].indexOf('.cache.js') > 0) {
                file = path.join(warDir, files[i]);
                break;
            }
        }
        if (!file) {
            throw new Error('Could not find GWT file for module ' + mod.name);
        }
        prom.push(exporter({
            input: file,
            output: 'dist/openchemlib-' + mod.name + '.js',
            exports: 'OCL',
            fake: mod.fake,
            'package': pack
        }));
    }
    Promise.all(prom).then(function () {
        done();
    }, function (e) {
        console.error(e);
        process.exit(1);
    })
}

function compile(mode) {
    return function () {
        var min = mode === 'min';
        for (var i = 0; i < modules.length; i++) {
            log('Compiling module ' + modules[i].name);
            var args = [
                '-Xmx512m',
                '-cp', classpath,
                'com.google.gwt.dev.Compiler',
                modules[i].entrypoint,
                '-logLevel', verbose ? 'DEBUG' : 'ERROR',
                min ? '-XnocheckCasts' : '-XcheckCasts',
                '-XnoclassMetadata',
                verbose ? '-draftCompile' : '-nodraftCompile',
                '-nocheckAssertions',
                '-generateJsInteropExports',
                '-optimize', min ? '9' : '0',
                '-style', min ? 'OBFUSCATED' : 'PRETTY',
      //          verbose ? '-failOnError' : '-nofailOnError'
            ];
            var result;
            try {
                result = child_process.execFileSync('java', args);
            } catch (e) {
                result = e.stdout;
                throw e;
            } finally {
                if (verbose) {
                    var name = 'compile-' + modules[i].name + '.log';
                    log('Compilation log written to ' + name);
                    fs.writeFileSync('./' + name, result);
                }
            }
        }
    }
}

function copyOpenchemlib() {
    const chemlibDir = path.join(config.openchemlib, 'main/java/com');
    const outDir = './src/com/actelion/research/gwt/chemlib/com';
    const modifiedDir = './openchemlib/modified/com';

    const chemlibClasses = require('./openchemlib/classes');

    rimraf.sync(outDir);

    fs.copySync(chemlibDir, outDir);

    const modified = chemlibClasses.modified;
    log('Copying ' + modified.length + ' modified classes');
    for (let i = 0; i < modified.length; i++) {
        fs.copySync(path.join(modifiedDir, modified[i]), path.join(outDir, modified[i]));
    }

    const changed = chemlibClasses.changed;
    for (let i = 0; i < changed.length; i++) {
        const file = changed[i][0];
        const callback = changed[i][1];
        const data = fs.readFileSync(path.join(outDir, file), 'utf8');
        const result = callback(data);
        fs.writeFileSync(path.join(outDir, file), result);
    }

    const removed = chemlibClasses.removed;
    for (const removedFile of removed) {
        fs.removeSync(path.join(outDir, removedFile));
    }
}

function log(value) {
    if (verbose) {
        console.log(value);
    }
}

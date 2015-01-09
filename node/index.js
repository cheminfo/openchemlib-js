var fs = require('fs'),
    program = require('commander'),
    _ = require('underscore');

program
    .version('0.0.0')
    .option('-i, --input <file>', 'Input file')
    .option('-o, --output [file]', 'Output file', 'output.js')
    .option('-e, --exports <path>', 'Exported path from GWT')
    .option('-p, --package [file]', 'Take information from a package.json file')
    .parse(process.argv);

if (!program.input || !program.exports) {
    console.error('Input and exports options are mandatory');
    process.exit(1);
}

var input;
try {
    input = fs.readFileSync(program.input).toString();
} catch (e) {
    console.error('Could not read input file (' + program.input + ').');
    process.exit(1);
}

var idx = input.indexOf('onScriptDownloaded');
if (idx === -1) {
    console.error('This is not a correct GWT obfuscated file.');
    process.exit(1);
}

var str2 = input.substring(idx + 19, input.length - 3); // remove wrapping function

var arr;
eval('arr = ' + str2);

var gwtString = arr.join(''); // Get pyramidal code

var missingFuncIdxReg = /[^\n\r]*(function [a-zA-Z]{1,2}\(\)\{})/;
gwtString = gwtString.replace(missingFuncIdxReg, '\n$1'); // Remove first line but keep last function definition

var exportsNames = program.exports.split('.');
var exportsStr = exportsNames.map(function (name) {
    return '["' + name + '"]';
}).join('');
var exportsName = '[' + exportsNames.map(function (name) {
        return '"' + name + '"';
    }).join(',') + ']';

var template = _.template(fs.readFileSync(__dirname + '/tpl.js').toString());

var pkg = program.package ? require(program.package) : null;

var final = template({
    gwtContent: '\n' + gwtString,
    exportsName: exportsStr,
    exportsPath: exportsName,
    version: pkg ? pkg.version : ''
});

if (pkg) {
    var commentStr = [
        '/**',
        ' * ' + pkg.name + ' - ' + pkg.description,
        ' * @version v' + pkg.version,
        ' * @date ' + (new Date()).toISOString(),
        ' * @link ' + pkg.homepage,
        ' * @license ' + pkg.license,
        '*/'
    ];
    final = commentStr.join('\n') + '\n' + final;
}

try {
    fs.writeFileSync(program.output, final);
    console.log('File ' + program.output + ' written.')
} catch (e) {
    console.error('Could not write output file (' + program.output + ').');
    process.exit(1);
}

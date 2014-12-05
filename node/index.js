var fs = require('fs');
var stdio = require('stdio');
var _ = require('underscore');

var ops = stdio.getopt({
    input: {key: 'i', args: 1, mandatory: true},
    output: {key: 'o', default: 'output.js', args: 1},
    exports: {key: 'e', default: 'GWT', args: 1},
    package: {key: 'p', args: 1}
});

var input;
try {
    input = fs.readFileSync(ops.input).toString();
} catch(e) {
    console.error('Could not read input file ('+ops.input+').');
    process.exit(1);
}

var idx = input.indexOf('onScriptDownloaded');
if (idx === -1) {
    console.error('This is not a correct GWT obfuscated file.');
    process.exit(1);
}

var str2 = input.substring(idx+19, input.length-3); // remove wrapping function

var arr;
eval('arr = ' + str2);

var gwtString = arr.join(''); // Get pyramidal code

var missingFuncIdxReg = /[^\n\r]*(function [a-zA-Z]{1,2}\(\)\{})/;
gwtString = gwtString.replace(missingFuncIdxReg, '\n$1'); // Remove first line but keep last function definition

var exportsName = ops.exports;

var template = _.template(fs.readFileSync(__dirname+'/tpl.js').toString());

var pkg = ops.package ? require(ops.package) : null;

var final = template({
    gwtContent: '\n'+gwtString,
    exportsName: exportsName,
    version: pkg ? pkg.version : ''
});

if(pkg) {
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
    fs.writeFileSync(ops.output, final);
    console.log('File '+ops.output+' written.')
} catch(e) {
    console.error('Could not write output file ('+ops.output+').');
    process.exit(1);
}
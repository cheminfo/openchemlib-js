var fs = require('fs');
var stdio = require('stdio');
var _ = require('underscore');

var ops = stdio.getopt({
    input: {key: 'i', args: 1, mandatory: true},
    output: {key: 'o', default: 'output.js', args: 1},
    exports: {key: 'e', default: 'GWT', args: 1}
});

var input;
try {
    input = fs.readFileSync(ops.input).toString();
} catch(e) {
    console.error("Could not read input file ("+ops.input+").");
    process.exit(1);
}

var contentWithoutHtml = input.replace(/<\/?[a-z]+>/gi,"").replace("<meta charset=\"UTF-8\" />","");
var contentWithoutComments = contentWithoutHtml.replace(/<!--/g,"").replace(/-->/g,"");

var exportsName = ops.exports;

var template = _.template(fs.readFileSync(__dirname+'/tpl.js').toString());

var final = template({
    gwtContent: '\n'+contentWithoutComments,
    exportsName: exportsName
});

try {
    fs.writeFileSync(ops.output, final);
    console.log("File "+ops.output+" written.")
} catch(e) {
    console.error("Could not write output file ("+ops.output+").");
    process.exit(1);
}
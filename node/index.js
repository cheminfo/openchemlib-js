var fs = require('fs');
var stdio = require('stdio');

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

var final =
    "function getExports(parent, navigator) {\r\n\r\n"
    +contentWithoutComments
    +"\r\n\r\ngwtOnLoad();\r\nreturn $wnd."+exportsName+";\r\n\r\n}\r\n\r\n(function(){\r\n\tif(typeof module !== \"undefined\" && module.exports) { \/\/ NodeJS\r\n\t\tvar fakeWindow = {\r\n\t\t\tsetTimeout: setTimeout,\r\n\t\t\tclearTimeout: clearTimeout\r\n\t\t};\r\n\t\tvar navigator = {\r\n\t\t\tuserAgent : \"webkit\"\r\n\t\t};\r\n\t\tfakeWindow.document = {\r\n\t\t\tcompatMode:\"CSS1Compat\"\r\n\t\t};\r\n\t\tmodule.exports = getExports(fakeWindow, navigator);\r\n\t} else { \/\/ Web browser\r\n\t\tvar setTimeout = window.setTimeout, clearTimeout = window.clearTimeout;\r\n\t\tvar fakeWindow = {\r\n\t\t\tsetTimeout: function(){\r\n\t\t\t\treturn setTimeout.apply(window, arguments);\r\n\t\t\t},\r\n\t\t\tclearTimeout: function() {\r\n\t\t\t\treturn clearTimeout.apply(window, arguments);\r\n\t\t\t}\r\n\t\t};\r\n\t\tfakeWindow.document = window.document;\r\n\t\tif(typeof define === \"function\" && define.amd) { \/\/ RequireJS\r\n\t\t\tdefine(function(){\r\n\t\t\t\treturn getExports(fakeWindow, window.navigator);\r\n\t\t\t});\r\n\t\t} else { \/\/ No module manager\r\n\t\t\twindow."+exportsName+" = getExports(fakeWindow, window.navigator);\r\n\t\t}\r\n\t}\r\n\r\n})();";

try {
    fs.writeFileSync(ops.output, final);
    console.log("File "+ops.output+" written.")
} catch(e) {
    console.error("Could not write output file ("+ops.output+").");
    process.exit(1);
}
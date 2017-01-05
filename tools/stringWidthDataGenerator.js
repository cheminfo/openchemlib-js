function getStringWidth(str, size = 20) {
    var canvas = document.createElement('canvas');
    var font = size + 'px Helvetica';
    var ctx = canvas.getContext('2d');
    ctx.font = font;
    var text = ctx.measureText(str);
    return text.width;
}

var data = {};

var a = 'a'.charCodeAt(0);
for (var i = a; i < a + 26; i++) {
    var str = String.fromCharCode(i);
    data[str] = getStringWidth(str);
}

var A = 'A'.charCodeAt(0);
for (var i = A; i < A + 26; i++) {
    var str = String.fromCharCode(i);
    data[str] = getStringWidth(str);
}

var z = '0'.charCodeAt(0);
for (var i = z; i < z + 10; i++) {
    var str = String.fromCharCode(i);
    data[str] = getStringWidth(str);
}

var additionalChars = [
    ' ',
    '\'',
    '"',
    '<',
    '>',
    '%',
    '#',
    '!',
    '?',
    '^',
    '$',
    '&',
    '|',
    '_',
    '-',
    '{',
    '}',
    '(',
    ')',
    '[',
    ']',
    '/',
    '\\',
    '+',
    '*',
    '.',
    '@',
    '=',
    '~'
];
for (var char of additionalChars) {
    data[char] = getStringWidth(char);
}

var result = [];
for (var char in data) {
    result[char.charCodeAt(0)] = data[char];
}

var pointer = 0;
var helveticaSizes = ['private static final double[] helveticaSizes = {'];
for (var i = 0; i < 8; i++) {
    var str = '\t';
    for (var j = 0; j < 16; j++) {
        var value = result[pointer++] || 5.55;
        value = Math.round(value * 100) / 100;
        str += value + ', ';
    }
    helveticaSizes.push(str);
}
helveticaSizes.push('};');
console.log(helveticaSizes.join('\n'));
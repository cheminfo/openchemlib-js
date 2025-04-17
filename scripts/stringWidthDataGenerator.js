function getStringWidth(str, size = 20) {
  var canvas = document.createElement('canvas');
  var font = `${size}px Helvetica`;
  var ctx = canvas.getContext('2d');
  ctx.font = font;
  var text = ctx.measureText(str);
  return text.width;
}

var result = new Array(128);
for (var c = 0; c < 128; c++) {
  result[c] = getStringWidth(String.fromCharCode(c));
}

var pointer = 0;
var helveticaSizes = ['private static final double[] helveticaSizes = {'];
for (var i = 0; i < 8; i++) {
  var str = '    ';
  for (var j = 0; j < 16; j++) {
    var value = result[pointer++] || 5.55;
    value = Math.round(value * 100) / 100;
    str += `${value}, `;
  }
  helveticaSizes.push(str);
}
helveticaSizes.push('};');
console.log(helveticaSizes.join('\n'));

'use strict';

const fs = require('node:fs');
const path = require('node:path');

fs.writeFileSync(
  path.join(__dirname, '../distesm/full.pretty.d.ts'),
  "export * from '../full.pretty';\n",
);

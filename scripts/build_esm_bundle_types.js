'use strict';

const fs = require('node:fs');
const path = require('node:path');

fs.writeFileSync(
  path.join(__dirname, '../distesm/full.d.ts'),
  "export * from '../full';\n",
);

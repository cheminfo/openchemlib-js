import fs from 'node:fs';
import path from 'node:path';

import { defineConfig } from 'vite';

const examples: string[] = fs.globSync('examples/**/*.html', {
  cwd: import.meta.dirname,
});

export default defineConfig({
  build: {
    outDir: 'distbuild',
    rollupOptions: {
      input: {
        main: path.join(import.meta.dirname, 'index.html'),
        ...Object.fromEntries(
          examples.map((example) => [
            example,
            path.join(import.meta.dirname, example),
          ]),
        ),
      },
    },
  },
});

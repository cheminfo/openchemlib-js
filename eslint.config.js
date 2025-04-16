import { defineConfig, globalIgnores } from 'eslint/config';
import cheminfo from 'eslint-config-cheminfo-typescript/base';
import unicorn from 'eslint-config-cheminfo-typescript/unicorn';
import globals from 'globals';

export default defineConfig(
  globalIgnores([
    'dist/**',
    'distbuild/**',
    'distold/**',
    'examples/**',
    'gwt/**',
    'lib/java/**',
    'scripts/stringWidthDataGenerator.js',
    'war/**',
  ]),
  ...cheminfo,
  ...unicorn,
  {
    files: ['lib/canvas_editor/**'],
    languageOptions: {
      globals: {
        ...globals.browser,
      },
    },
  },
  {
    files: ['lib/register_resources_nodejs.js'],
    languageOptions: {
      globals: {
        ...globals.node,
      },
    },
  },
  {
    files: ['benchmark/**'],
    rules: {
      'no-console': 'off',
    },
  },
  {
    files: ['scripts/**'],
    languageOptions: {
      globals: {
        ...globals.node,
      },
    },
    rules: {
      'unicorn/no-process-exit': 'off',
    },
  },
  {
    files: ['tests/**'],
    rules: {
      'no-console': 'off',
    },
  },
  {
    files: ['lib/index.d.ts'],
    rules: {
      '@typescript-eslint/naming-convention': 'off',
    },
  },
);

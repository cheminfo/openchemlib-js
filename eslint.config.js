import { defineConfig, globalIgnores } from 'eslint/config';
import cheminfo from 'eslint-config-cheminfo-typescript/base';
import unicorn from 'eslint-config-cheminfo-typescript/unicorn';
import vitest from 'eslint-config-cheminfo-typescript/vitest';
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
    'src/resources/**',
    'war/**',
  ]),
  cheminfo,
  unicorn,
  vitest,
  {
    rules: {
      'unicorn/filename-case': ['error', { case: 'snakeCase' }],
    },
  },
  {
    files: ['lib/canvas_editor/**'],
    languageOptions: {
      globals: {
        ...globals.browser,
      },
    },
  },
  {
    files: ['lib/register_resources.js'],
    languageOptions: {
      globals: {
        ...globals.nodeBuiltin,
      },
    },
  },
  {
    files: ['benchmark/**'],
    rules: {
      'no-console': 'off',
      'import/no-named-as-default-member': 'off',
    },
  },
  {
    files: ['scripts/**'],
    languageOptions: {
      globals: {
        ...globals.nodeBuiltin,
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

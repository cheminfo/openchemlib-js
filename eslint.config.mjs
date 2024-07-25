import cheminfo from 'eslint-config-cheminfo-typescript/base';
import unicorn from 'eslint-config-cheminfo-typescript/unicorn';
import globals from 'globals';

export default [
  {
    ignores: [
      'dist/**',
      'distbuild/**',
      'distesm/**',
      'distold/**',
      'examples/**',
      'gwt/**',
      'scripts/stringWidthDataGenerator.js',
      'war/**',
    ],
  },
  ...cheminfo,
  ...unicorn,
  {
    files: ['*.js', 'lib/**'],
    languageOptions: {
      sourceType: 'commonjs',
    },
  },
  {
    files: ['lib/**'],
    languageOptions: {
      sourceType: 'commonjs',
      globals: {
        ...globals.browser,
      },
    },
  },
  {
    files: ['__tests__/**'],
    languageOptions: {
      globals: {
        ...globals.node,
        ...globals.jest,
      },
      sourceType: 'commonjs',
    },
  },
  {
    files: ['benchmark/**'],
    languageOptions: {
      globals: {
        ...globals.node,
      },
    },
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
      sourceType: 'commonjs',
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
    files: ['types.d.ts'],
    rules: {
      '@typescript-eslint/naming-convention': 'off',
    },
  },
];

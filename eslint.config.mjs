import cheminfo from 'eslint-config-cheminfo/base';
import unicorn from 'eslint-config-cheminfo/unicorn';
import globals from 'globals';

export default [
  {
    ignores: [
      'dist/**',
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
    files: ['*.js'],
    languageOptions: {
      sourceType: 'commonjs',
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
];

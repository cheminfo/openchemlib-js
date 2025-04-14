'use strict';

const childProcess = require('node:child_process');
const fs = require('node:fs');
const os = require('node:os');
const path = require('node:path');

const exporter = require('gwt-api-exporter');
const yargs = require('yargs');

const pack = require('../package.json');

const argv = yargs
  .command(
    'copy:openchemlib',
    'Copy the required java files from the OpenChemLib project.',
  )
  .command('build', 'Compile and export')
  .command('compile', 'Execute the GWT compiler.')
  .command('export', 'Transform the GWT compiled files to a JavaScript module.')
  .demandCommand()
  .option('v', {
    alias: 'verbose',
    default: false,
    type: 'boolean',
  })
  .option('mode', {
    description: 'Compilation mode',
    choices: ['pretty', 'min'],
    default: 'pretty',
  })
  .option('s', {
    alias: 'suffix',
    description: 'Optional suffix to the exported filename',
  }).argv;

const { mode, verbose } = argv;

let suffix = '';
if (argv.suffix) {
  suffix = `.${argv.suffix}`;
}

let config;
try {
  config = require('../config.json');
} catch {
  // eslint-disable-next-line no-console
  console.error(
    'config.json not found. You can copy config.default.json to start from an example.',
  );
  process.exit(1);
}

const classpathList = ['src'];
classpathList.push(
  path.resolve(config.gwt, 'gwt-dev.jar'),
  path.resolve(config.gwt, 'gwt-user.jar'),
);

const sep = os.platform() === 'win32' ? ';' : ':';
const classpath = classpathList.join(sep);

run(argv._[0]);

function run(command) {
  if (command === 'copy:openchemlib') {
    copyOpenchemlib();
  } else if (command === 'compile') {
    compile(mode);
  } else if (command === 'export') {
    build().catch(handleCatch);
  } else if (command === 'build') {
    compile(mode);
    build().catch(handleCatch);
  }
}

function copyOpenchemlib() {
  const chemlibDir = path.join(__dirname, '../openchemlib/src/main/java/com');
  const outDir = path.join(
    __dirname,
    '../src/com/actelion/research/gwt/chemlib/com',
  );
  const modifiedDir = path.join(__dirname, './openchemlib/modified/com');

  const chemlibClasses = require('./openchemlib/classes');

  fs.rmSync(outDir, { recursive: true, force: true });
  fs.cpSync(chemlibDir, outDir, { recursive: true });

  copyAdditionalDir('org');
  copyAdditionalDir('info');

  const removed = chemlibClasses.removed;
  for (const removedFile of removed) {
    fs.rmSync(path.join(outDir, removedFile), { recursive: true });
  }

  const modified = chemlibClasses.modified;
  log(`Copying ${modified.length} modified classes`);
  for (let i = 0; i < modified.length; i++) {
    fs.cpSync(
      path.join(modifiedDir, modified[i]),
      path.join(outDir, modified[i]),
      { recursive: true },
    );
  }

  const changed = chemlibClasses.changed;
  for (const [file, transformers] of changed) {
    let data = fs.readFileSync(path.join(outDir, file), 'utf8');
    for (const transformer of transformers) {
      data = transformer(data);
    }
    fs.writeFileSync(path.join(outDir, file), data);
  }

  const generated = chemlibClasses.generated;
  for (const generatedFile of generated) {
    fs.writeFileSync(path.join(outDir, generatedFile[0]), generatedFile[1]());
  }
}

function copyAdditionalDir(name) {
  const sourceDir = path.join(__dirname, '../openchemlib/src/main/java', name);
  const destDir = path.join(
    __dirname,
    '../src/com/actelion/research/gwt/chemlib',
    name,
  );

  fs.rmSync(destDir, { recursive: true, force: true });
  fs.cpSync(sourceDir, destDir, { recursive: true });
}

function compile(mode) {
  let min = mode === 'min';
  let PATH = process.env.PATH;
  if (config.jdk) {
    PATH = `${path.resolve(config.jdk, 'bin')}${sep}${PATH}`;
  }
  log('Compiling module');
  let args = [
    '-Xmx2G',
    '-cp',
    classpath,
    'com.google.gwt.dev.Compiler',
    'com.actelion.research.gwt.Js',
    '-logLevel',
    verbose ? 'DEBUG' : 'ERROR',
    min ? '-XnocheckCasts' : '-XcheckCasts',
    '-XnoclassMetadata',
    verbose ? '-draftCompile' : '-nodraftCompile',
    '-nocheckAssertions',
    '-generateJsInteropExports',
    '-optimize',
    min ? '9' : '0',
    '-style',
    min ? 'OBFUSCATED' : 'PRETTY',
    '-sourceLevel',
    '17',
    '-failOnError',
  ];
  let result;
  try {
    result = childProcess.execFileSync('java', args, {
      maxBuffer: Infinity,
      env: { ...process.env, PATH },
    });
  } catch (error) {
    result = error.stdout;
    throw error;
  } finally {
    if (verbose) {
      let name = `compile${suffix}.log`;
      log(`Compilation log written to ${name}`);
      fs.writeFileSync(`./${name}`, result);
    }
  }
}

async function build() {
  let prom = [];
  fs.mkdirSync('dist', { recursive: true });
  log('Exporting module');
  let warDir = path.join('war', 'oclJs');
  let files = fs.readdirSync(warDir);
  let file;
  for (let i = 0; i < files.length; i++) {
    if (files[i].indexOf('.cache.js') > 0) {
      file = path.join(warDir, files[i]);
      break;
    }
  }
  if (!file) {
    throw new Error('Could not find GWT file for module oclJs');
  }
  prom.push(
    exporter({
      input: file,
      output: `dist/openchemlib${suffix}.js`,
      exports: 'OCL',
      fake: true,
      package: pack,
    }),
  );
  await Promise.all(prom);
}

function log(value) {
  if (verbose) {
    // eslint-disable-next-line no-console
    console.log(value);
  }
}

function handleCatch(err) {
  // eslint-disable-next-line no-console
  console.error(err);
  process.exit(1);
}

'use strict';

const childProcess = require('child_process');
const path = require('path');
const os = require('os');

const fs = require('fs-extra');
const rimraf = require('rimraf');
const exporter = require('gwt-api-exporter');
const yargs = require('yargs');

const pack = require('../package.json');

const extendMinimal = require('./extend/minimal');
const extendCore = require('./extend/core');
const extendFull = require('./extend/full');
let modules = require('./modules.json');

const argv = yargs
  .command(
    'copy:openchemlib',
    'Copy the required java files from the openchemlib project.',
  )
  .command('build', 'Compile and export')
  .command('compile', 'Execute the GWT compiler.')
  .command('export', 'Transform the GWT compiled files to JavaScript modules.')
  .demandCommand()
  .option('v', {
    alias: 'verbose',
    default: false,
    type: 'boolean',
  })
  .option('m', {
    alias: 'module',
    description: 'Compile only this module',
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

if (argv.m) {
  for (let i = 0; i < modules.length; i++) {
    if (modules[i].name === argv.m) {
      modules = [modules[i]];
      break;
    }
  }
  if (modules.length !== 1) {
    throw new Error(`module ${argv.m} not found`);
  }
}

let config;
try {
  config = require('../config.json');
} catch (e) {
  // eslint-disable-next-line no-console
  console.error(
    'config.json not found. You can copy config.default.json to start from an example.',
  );
  // eslint-disable-next-line no-process-exit
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
  const chemlibDir = path.resolve(config.openchemlib, 'main/java/com');
  const outDir = path.join(
    __dirname,
    '../src/com/actelion/research/gwt/chemlib/com',
  );
  const modifiedDir = path.join(__dirname, './openchemlib/modified/com');

  const chemlibClasses = require('./openchemlib/classes');

  if (fs.existsSync(outDir)) {
    rimraf.sync(outDir);
  }
  fs.copySync(chemlibDir, outDir);

  const openMolDir = path.resolve(config.openchemlib, 'main/java/org');
  const outOpenMolDir = path.join(
    __dirname,
    '../src/com/actelion/research/gwt/chemlib/org',
  );

  if (fs.existsSync(outOpenMolDir)) {
    rimraf.sync(outOpenMolDir);
  }
  fs.copySync(openMolDir, outOpenMolDir);

  const modified = chemlibClasses.modified;
  log(`Copying ${modified.length} modified classes`);
  for (let i = 0; i < modified.length; i++) {
    fs.copySync(
      path.join(modifiedDir, modified[i]),
      path.join(outDir, modified[i]),
    );
  }

  const changed = chemlibClasses.changed;
  for (let i = 0; i < changed.length; i++) {
    const file = changed[i][0];
    const callback = changed[i][1];
    const data = fs.readFileSync(path.join(outDir, file), 'utf8');
    const result = callback(data);
    fs.writeFileSync(path.join(outDir, file), result);
  }

  const removed = chemlibClasses.removed;
  for (const removedFile of removed) {
    fs.removeSync(path.join(outDir, removedFile));
  }

  const generated = chemlibClasses.generated;
  for (const generatedFile of generated) {
    fs.writeFileSync(
      path.join(outDir, generatedFile[0]),
      generatedFile[1]({
        config,
      }),
    );
  }
}

function compile(mode) {
  let min = mode === 'min';
  for (let i = 0; i < modules.length; i++) {
    log(`Compiling module ${modules[i].name}`);
    let args = [
      '-Xmx2G',
      '-cp',
      classpath,
      'com.google.gwt.dev.Compiler',
      modules[i].entrypoint,
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
      //          verbose ? '-failOnError' : '-nofailOnError'
    ];
    let result;
    try {
      result = childProcess.execFileSync('java', args, { maxBuffer: Infinity });
    } catch (e) {
      result = e.stdout;
      throw e;
    } finally {
      if (verbose) {
        let name = `compile-${modules[i].name}.log`;
        log(`Compilation log written to ${name}`);
        fs.writeFileSync(`./${name}`, result);
      }
    }
  }
}

function build() {
  let prom = [];
  for (let k = 0; k < modules.length; k++) {
    let mod = modules[k];
    log(`Exporting module ${mod.name}`);
    let warDir = path.join('war', mod.war);
    let files = fs.readdirSync(warDir);
    let file;
    for (let i = 0; i < files.length; i++) {
      if (files[i].indexOf('.cache.js') > 0) {
        file = path.join(warDir, files[i]);
        break;
      }
    }
    if (!file) {
      throw new Error(`Could not find GWT file for module ${mod.name}`);
    }
    prom.push(
      exporter({
        input: file,
        output: `dist/openchemlib-${mod.name}${suffix}.js`,
        exports: 'OCL',
        fake: mod.fake,
        package: pack,
        extendApi: createExtender(mod.name),
      }),
    );
  }
  return Promise.all(prom);
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
  // eslint-disable-next-line no-process-exit
  process.exit(1);
}

function createExtender(name) {
  const toPut = [addAndCallExtender(extendMinimal, 'extendMinimal')];
  if (name === 'core' || name === 'full') {
    toPut.push(addAndCallExtender(extendCore, 'extendCore'));
  }
  if (name === 'full') toPut.push(addAndCallExtender(extendFull, 'extendFull'));
  return `function extendApi(exports) {
    ${toPut.join('\n')}
  }`;
}

function addAndCallExtender(extender, name) {
  return `${extender.toString()}\n${name}(exports);`;
}

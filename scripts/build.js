import childProcess from 'node:child_process';
import fs from 'node:fs';
import os from 'node:os';
import path from 'node:path';

import exporter from 'gwt-api-exporter';
import yargs from 'yargs';

import pack from '../package.json' with { type: 'json' };

import * as chemlibClasses from './openchemlib/classes.js';

const argv = yargs(process.argv.slice(2))
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

const { mode, verbose, _ } = argv;

let suffix = '';
if (argv.suffix) {
  suffix = `.${argv.suffix}`;
}

let config;
try {
  const configJson = await import('../config.json', { with: { type: 'json' } });
  config = configJson.default;
} catch {
  // eslint-disable-next-line no-console
  console.error(
    'config.json not found. You can copy config.default.json to start from an example.',
  );
  process.exit(1);
}

const classpathList = [
  'src',
  path.resolve(config.gwt, 'gwt-dev.jar'),
  path.resolve(config.gwt, 'gwt-user.jar'),
];

const separator = os.platform() === 'win32' ? ';' : ':';
const classpath = classpathList.join(separator);

await run(_[0]);

async function run(command) {
  if (command === 'copy:openchemlib') {
    copyOpenchemlib();
  } else if (command === 'compile') {
    compile();
  } else if (command === 'export') {
    await build();
  } else if (command === 'build') {
    compile();
    await build();
  }
}

function copyOpenchemlib() {
  const chemlibDirector = path.join(
    import.meta.dirname,
    '../openchemlib/src/main/java/com',
  );
  const outDirectory = path.join(
    import.meta.dirname,
    '../src/com/actelion/research/gwt/chemlib/com',
  );
  const modifiedDirectory = path.join(
    import.meta.dirname,
    './openchemlib/modified/com',
  );

  const resourcesSourceDirectory = path.join(
    import.meta.dirname,
    '../openchemlib/src/main/resources',
  );
  const resourcesOutDirectory = path.join(
    import.meta.dirname,
    '../src/resources',
  );
  fs.rmSync(resourcesOutDirectory, { recursive: true, force: true });
  fs.cpSync(resourcesSourceDirectory, resourcesOutDirectory, {
    recursive: true,
  });

  fs.rmSync(outDirectory, { recursive: true, force: true });
  fs.cpSync(chemlibDirector, outDirectory, { recursive: true });

  copyAdditionalDirectory('org');
  copyAdditionalDirectory('info');

  for (const removedFile of chemlibClasses.removed) {
    fs.rmSync(path.join(outDirectory, removedFile), { recursive: true });
  }

  const modified = chemlibClasses.modified;
  log(`Copying ${modified.length} modified classes`);
  for (let i = 0; i < modified.length; i++) {
    fs.cpSync(
      path.join(modifiedDirectory, modified[i]),
      path.join(outDirectory, modified[i]),
      { recursive: true },
    );
  }

  for (const [file, transformers] of chemlibClasses.changed) {
    let data = fs.readFileSync(path.join(outDirectory, file), 'utf8');
    for (const transformer of transformers) {
      data = transformer(data);
    }
    fs.writeFileSync(path.join(outDirectory, file), data);
  }

  for (const generatedFile of chemlibClasses.generated) {
    fs.writeFileSync(
      path.join(outDirectory, generatedFile[0]),
      generatedFile[1](),
    );
  }
}

function copyAdditionalDirectory(name) {
  const sourceDirectory = path.join(
    import.meta.dirname,
    '../openchemlib/src/main/java',
    name,
  );
  const destinationDirectory = path.join(
    import.meta.dirname,
    '../src/com/actelion/research/gwt/chemlib',
    name,
  );

  fs.rmSync(destinationDirectory, { recursive: true, force: true });
  fs.cpSync(sourceDirectory, destinationDirectory, { recursive: true });
}

function compile() {
  let isMin = mode === 'min';
  let PATH = process.env.PATH;
  if (config.jdk) {
    PATH = `${path.resolve(config.jdk, 'bin')}${separator}${PATH}`;
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
    isMin ? '-XnocheckCasts' : '-XcheckCasts',
    '-XnoclassMetadata',
    verbose ? '-draftCompile' : '-nodraftCompile',
    '-nocheckAssertions',
    '-generateJsInteropExports',
    '-optimize',
    isMin ? '9' : '0',
    '-style',
    isMin ? 'OBFUSCATED' : 'PRETTY',
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
  fs.mkdirSync('lib/java', { recursive: true });
  log('Exporting module');
  let warDirectory = path.join('war', 'oclJs');
  let files = fs.readdirSync(warDirectory);
  let file;
  for (let i = 0; i < files.length; i++) {
    if (files[i].indexOf('.cache.js') > 0) {
      file = path.join(warDirectory, files[i]);
      break;
    }
  }
  if (!file) {
    throw new Error('Could not find GWT file for module oclJs');
  }
  prom.push(
    exporter({
      input: file,
      output: `lib/java/openchemlib${suffix}.js`,
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

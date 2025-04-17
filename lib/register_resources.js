export function addRegisterResources(OCL) {
  const _register = OCL.Resources._register.bind(OCL.Resources);
  delete OCL.Resources._register;

  OCL.Resources.register = function register(resources) {
    if (typeof resources === 'string') {
      resources = JSON.parse(resources);
    }
    const textEncoder = new TextEncoder();
    if (typeof resources !== 'object' || resources === null) {
      throw new TypeError('resources must be an object');
    }
    const resourcesTyped = {};
    for (const [key, value] of Object.entries(resources)) {
      if (typeof value !== 'string') {
        throw new TypeError(`resource ${key} must be a string`);
      }
      resourcesTyped[key] = textEncoder.encode(value);
    }
    _register(resourcesTyped);
  };

  OCL.Resources.registerFromUrl = async function registerFromUrl(
    url = getDefaultResourcesUrl(),
  ) {
    const request = await fetch(url);
    const resources = await request.json();
    _register(resources);
  };

  if (typeof process !== 'undefined') {
    OCL.Resources.registerFromNodejs = function registerFromNodejs(
      resourcesPath = getDefaultResourcesPath(),
    ) {
      const fs = process.getBuiltinModule('fs');
      const resources = fs.readFileSync(resourcesPath, 'ascii');
      OCL.Resources.register(resources);
    };

    function getDefaultResourcesPath() {
      const path = process.getBuiltinModule('path');
      let resourcesPath = path.join(import.meta.dirname, 'resources.json');
      if (import.meta.filename.endsWith('register_resources.js')) {
        // Special case for OCL Development.
        resourcesPath = path.join(
          import.meta.dirname,
          '../dist/resources.json',
        );
      }
      return resourcesPath;
    }
  } else {
    OCL.Resources.registerFromNodejs = function registerFromNodejs() {
      throw new Error(
        `Resources.registerFromNodejs can only be called from Node.js`,
      );
    };
  }
}

function getDefaultResourcesUrl() {
  return new URL('resources.json', import.meta.url).href;
}

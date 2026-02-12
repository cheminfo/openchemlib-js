import { rmSync } from 'node:fs';

const options = { recursive: true, force: true };

rmSync('dist', options);
rmSync('lib/java', options);
rmSync('gwt-unitCache', options);
rmSync('war', options);

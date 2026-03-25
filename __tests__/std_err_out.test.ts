import { expect, test, vi } from 'vitest';

import { System } from '../lib/index.debug.js';

test('should map Java System log to the console by default', () => {
  // using mockLog = vi.spyOn(console, 'log').mockImplementation(() => {
  //   // Do nothing.
  // });
  // using mockError = vi.spyOn(console, 'error').mockImplementation(() => {
  //   // Do nothing.
  // });

  // @ts-expect-error Only exported for debugging purposes.
  System.printDebugOut('TES\nT1消');
  // @ts-expect-error Only exported for debugging purposes.
  System.printDebugErr('TEST2');

  expect(mockLog).toHaveBeenCalledWith('TEST1');
  expect(mockError).toHaveBeenCalledWith('TEST2');
});

// test('should allow to customize standard error and output', () => {
//   const err = vi.fn();
//   const out = vi.fn();
//
//   System.setErr(err);
//   System.setOut(out);
// });

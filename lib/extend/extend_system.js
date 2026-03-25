export function extendSystem(System) {
  const { setOut: _setOut, setErr: _setErr } = System;

  System.setOut = function setOut(outCb) {
    if (typeof outCb !== 'function') {
      throw new TypeError('outCb must be a function');
    }
    _setOut(outCb);
  };

  System.setErr = function setErr(errCb) {
    if (typeof errCb !== 'function') {
      throw new TypeError('errCb must be a function');
    }
    _setErr(errCb);
  };

  System.setOut((b) => {
    console.trace(b);
  });

  System.setErr((b) => {
    console.trace(b);
  });
}

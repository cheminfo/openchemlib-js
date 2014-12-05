function getExports($wnd) {

    var $doc = {};
    var $gwt = {};
    var navigator = {
        userAgent: 'webkit'
    };

    function noop(){}

    var __gwtModuleFunction = noop;
    __gwtModuleFunction.__moduleStartupDone = noop;
    var $sendStats = noop;

    // Start GWT code <%= gwtContent %>
    // End GWT code

    var toReturn = $wnd['<%= exportsName %>'];

    toReturn.version = '<%= version %>';

    return toReturn;
}

(function loadFromGWT() {

    var fakeWindow = {
    };

    if (typeof module !== 'undefined' && module.exports) { // NodeJS
        var timers = require('timers');
        fakeWindow.setTimeout = timers.setTimeout;
        fakeWindow.clearTimeout = timers.clearTimeout;
        module.exports = getExports(fakeWindow);
    } else { // Browser
        fakeWindow.setTimeout = function () {
            return window.setTimeout.apply(window, arguments);
        };
        fakeWindow.clearTimeout = function () {
            return window.clearTimeout.apply(window, arguments);
        };
        if (typeof define === 'function' && define.amd) { // AMD
            define(function () {
                return getExports(fakeWindow);
            });
        } else { // Global
            window['<%= exportsName %>'] = getExports(fakeWindow);
        }
    }

})();
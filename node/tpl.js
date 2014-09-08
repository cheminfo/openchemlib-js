function getExports(parent, navigator) {

    //<%= gwtContent %>

    gwtOnLoad();
    return $wnd['<%= exportsName %>'];
}

(function loadFromGWT() {

    var navigator = {
        userAgent: 'webkit'
    };

    var fakeWindow = {
        document: {
            compatMode: 'CSS1Compat'
        }
    };

    if (typeof module !== 'undefined' && module.exports) { // NodeJS
        var timers = require('timers');
        fakeWindow.setTimeout = timers.setTimeout;
        fakeWindow.clearTimeout = timers.clearTimeout;
        module.exports = getExports(fakeWindow, navigator);
    } else { // Browser
        fakeWindow.setTimeout = function () {
            return window.setTimeout.apply(window, arguments);
        };
        fakeWindow.clearTimeout = function () {
            return window.clearTimeout.apply(window, arguments);
        };
        if (typeof define === 'function' && define.amd) { // AMD
            define(function () {
                return getExports(fakeWindow, navigator);
            });
        } else { // Global
            window['<%= exportsName %>'] = getExports(fakeWindow, navigator);
        }
    }

})();
(function () {
    'use strict';

    /* Filters */
    // define the filter which uses wijmo.Globalize.format to format the data.
    angular.module('app').filter('globalize', function () {
        return function (input, format) {
            return wijmo.Globalize.format(input, format);
        };
    });
})();